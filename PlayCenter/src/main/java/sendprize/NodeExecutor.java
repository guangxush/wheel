package sendprize;

import sendprize.model.Node;
import sendprize.model.ProcessContext;

/**
 * 节点执行器
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public interface NodeExecutor {

    /**
     * 执行节点
     *
     * @param preNode 前置节点
     * @param node 当前节点
     * @param context 执行上下文
     */
    void execute(Node preNode, Node node, ProcessContext context);
}
