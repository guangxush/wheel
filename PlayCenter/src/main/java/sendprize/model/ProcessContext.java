package sendprize.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 流程上下文
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class ProcessContext {

    /**
     * 流程模板
     */
    private ProcessTemplate processTemplate;

    /**
     * 节点上下文
     */
    private NodeContext nodeContext;

    /**
     * 流程配置缓存
     */
    private Map<String, NodeConfig> nodeConfigs = new ConcurrentHashMap<String, NodeConfig>();

    /**
     * 流程节点结果
     */
    private Map<String, NodeResult> nodeResults = new ConcurrentHashMap<String, NodeResult>();

    /**
     * 流程模型
     */
    private ProcessModel processModel;


    /**
     * 同步等待计数器
     */
    private CountDownLatch countDownLatch;

    /**
     * 异常对象
     */
    private Throwable throwable;

    /**
     * 获取节点配置
     * @param configId
     * @return
     */
    public NodeConfig getNodeConfig(String configId){
        return nodeConfigs.get(configId);
    }

    /**
     * 设置节点配置
     * @param configId
     * @param nodeConfig
     */
    public void setNodeConfig(String configId, NodeConfig nodeConfig){
        this.nodeConfigs.put(configId, nodeConfig);
    }

    /**
     * 流程结束
     */
    public void processReturn(){
        if(countDownLatch != null){
            countDownLatch.countDown();
        }
    }

    /**
     * 设置节点结果
     *
     * @param nodeCode
     * @param result
     */
    public void setNodeResult(String nodeCode, String result){
        this.setNodeResult(nodeCode, result, null);
    }

    /**
     * 设置节点返回结果
     *
     * @param nodeCode
     * @param result
     * @param throwable
     */
    public void setNodeResult(String nodeCode, String result, Throwable throwable){
        NodeResult nodeResult = this.nodeResults.get(nodeCode);
        if(nodeResult == null){
            nodeResult = new NodeResult(result, throwable);
            this.nodeResults.put(nodeCode, nodeResult);
        }
    }

    /**
     * 获取节点结果
     * @param nodeCode
     * @return
     */
    public synchronized NodeResult getNodeResult(String nodeCode){
        return nodeResults.get(nodeCode);
    }

    public ProcessTemplate getProcessTemplate() {
        return processTemplate;
    }

    public void setProcessTemplate(ProcessTemplate processTemplate) {
        this.processTemplate = processTemplate;
    }

    public NodeContext getNodeContext() {
        return nodeContext;
    }

    public void setNodeContext(NodeContext nodeContext) {
        this.nodeContext = nodeContext;
    }

    public Map<String, NodeConfig> getNodeConfigs() {
        return nodeConfigs;
    }

    public void setNodeConfigs(Map<String, NodeConfig> nodeConfigs) {
        this.nodeConfigs = nodeConfigs;
    }

    public ProcessModel getProcessModel() {
        return processModel;
    }

    public void setProcessModel(ProcessModel processModel) {
        this.processModel = processModel;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Map<String, NodeResult> getNodeResults() {
        return nodeResults;
    }

    public void setNodeResults(Map<String, NodeResult> nodeResults) {
        this.nodeResults = nodeResults;
    }
}
