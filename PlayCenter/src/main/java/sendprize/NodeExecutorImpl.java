package sendprize;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import sendprize.model.*;
import sendprize.service.ClientCacheService;
import sendprize.service.PrizeRunnable;
import sendprize.util.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * 流程执行器
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class NodeExecutorImpl implements NodeExecutor{

    /**
     * 日志句柄
     */
    private static final Logger logger = LoggerFactory.getLogger(NodeExecutorImpl.class);

    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPoolExecutor = ThreadPoolManager.getThreadPool("processEngineExecutor");

    /**
     * EL表达式缓存
     */
    private Map<String, Expression> expressions = new ConcurrentHashMap<>();

    /**
     * 表达式解析器
     */
    private ExpressionParser parser = new SpelExpressionParser();

    /**
     * 服务上下文集合
     */
    private ClientCacheService clientCacheService;

    @Override
    public void execute(Node preNode, Node currentNode, ProcessContext context) {
        // 多个节点聚合
        if(this.joined(currentNode, context)){
            return;
        }

        // 执行节点
        if(currentNode.isAsync()){
            // 异步执行
            this.asyncExecute(preNode, currentNode, context);
        }else{
            // 同步执行
            this.syncExecute(preNode, currentNode, context);
        }
    }


    /**
     * 节点聚合
     *
     * @param currentNode 当前节点
     * @param context 流程上下文
     * @return 是否需要等待
     */
    private boolean joined(Node currentNode, ProcessContext context){
        List<Join> joins = currentNode.getJoinList();
        if(CollectionUtils.isEmpty(joins)){
            return true;
        }
        for(Join join:joins){
            String nodeCode = join.getJoinNode();
            String expected = join.getResult();
            String actual  = null;
            NodeResult nodeResult = context.getNodeResult(nodeCode);
            if(nodeResult != null){
                actual = nodeResult.getResult();
            }
            if(this.exeExpression(actual, expected, join.getExpression(), context)){
                return false;
            }
        }
        return true;
    }


    protected void asyncExecute(Node preNode, Node currentNode, ProcessContext context){
        threadPoolExecutor.execute(new PrizeRunnable() {
            @Override
            protected void doRun() {

            }
        });
    }

    /**
     * 同步处理
     *
     * @param preNode 上一个节点
     * @param currentNode 当前节点
     * @param context 流程上下文
     */
    protected void syncExecute(Node preNode, Node currentNode, ProcessContext context){
        String result = this.runCurrentNode(preNode, currentNode, context);
        if(!currentNode.isEndNode()){
            this.findNextNode(result, currentNode, context);
        }
    }

    /**
     * 执行当前节点
     * @param preNode
     * @param currentNode
     * @param context
     * @return
     */
    private String runCurrentNode(Node preNode, Node currentNode, ProcessContext context){
        long startTime = System.currentTimeMillis();

        // 获取运行组件
        ServiceNode serviceNode = this.getServiceNode(currentNode);

        // 组件执行
        try{
            // 1. 获取配置
            NodeConfig nodeConfig = this.buildNodeConfig(serviceNode, currentNode, context);
            serviceNode.execute(currentNode, nodeConfig, context.getNodeContext());

            // 2. 如果是结束节点直接返回
            if(currentNode.isEndNode()){
                context.processReturn();
            }

            // 3. 设置并返回成功结果
            String result = "success";
            context.setNodeResult(currentNode.getCode(), result);

            // 4. 设置需要回滚消息
            if(serviceNode.isRollBackable()){
                this.setRollbackInfo(currentNode, context);
            }

            // 5. 打印日志返回
            long elapseTime = System.currentTimeMillis() - startTime;
            logger.info("节点执行成功...省略关键信息， 耗时{}", elapseTime);
            return result;
        }catch (Throwable throwable){
            // 1. 设置并返回成功结果
            String result = "failure";
            long elapseTime = System.currentTimeMillis() - startTime;
            logger.info("节点执行失败...省略关键信息， 耗时{}", elapseTime);

            // 2. 后续流程进入异常节点
            if(currentNode.isHandleException()){
                return result;
            }

            // 3. 处理异常
            throw new IllegalArgumentException("不合法异常");
        }
    }

    /**
     * 构造节点配置
     * @param serviceNode
     * @param node
     * @param context
     * @return
     */
    public NodeConfig buildNodeConfig(ServiceNode serviceNode, Node node, ProcessContext context){
        String configId = node.getConfigId();
        NodeConfig nodeConfig = context.getNodeConfig(configId);
        Class<? extends NodeConfig> type = serviceNode.getNodeConfigType();
        try {
            nodeConfig = type.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        nodeConfig.convert(node, context.getProcessModel(), context.getNodeContext());
        context.setNodeConfig(configId, nodeConfig);
        return nodeConfig;
    }

    /**
     * 获取业务组件
     * @param currentNode
     * @return
     */
    private ServiceNode getServiceNode(Node currentNode){
        String componentId = currentNode.getComponentId();

        ServiceNode serviceNode = clientCacheService.getServiceNode(componentId);

        return serviceNode;
    }

    /**
     * 设置回滚节点
     *
     * @param node
     * @param context
     */
    private void setRollbackInfo(Node node, ProcessContext context){
        NodeContext nodeContext = context.getNodeContext();
        nodeContext.setRollbackNode(node.getCode());
    }


    /**
     * 执行EL表达式
     * @param actual
     * @param expected
     * @param expression
     * @param context
     * @return
     */
    public boolean exeExpression(String actual, String expected, String expression, ProcessContext context){
        if(!StringUtils.equals(expected, actual)){
            return false;
        }
        if(StringUtils.isBlank(expression)){
            return true;
        }
        // 执行表达式
        Expression exp = expressions.get(expression);
        if(exp == null){
            exp = parser.parseExpression(expression);
            if(exp == null){
                expressions.put(expression, exp);
            }else{
                return false;
            }
        }
        return (boolean) exp.getValue(context);
    }

    /**
     * 获取下一节点
     *
     * @param result
     * @param currentNode
     * @param context
     */
    private void findNextNode(String result, Node currentNode, ProcessContext context){
        List<Next> nextList = currentNode.getNextList();
        if(CollectionUtils.isEmpty(nextList)){
            return;
        }
        List<Node> nextNodes = new ArrayList<>(nextList.size());
        for(Next next: nextList){
            if(this.exeExpression(result, next.getResult(), next.getExpression(), context)){
                Node nextNode = context.getProcessTemplate().getNode(next.getNextNode());
                if(nextNode != null){
                    nextNodes.add(nextNode);
                }
            }
        }
        if(nextNodes.size() == 0){
            return;
        }else if(nextNodes.size() == 1){
            this.execute(currentNode, nextNodes.get(0), context);
        }else if(nextNodes.size() > 1){
            for(Node nextNode : nextNodes){
                this.asyncExecute(currentNode, nextNode, context);
            }
        }
    }
}
