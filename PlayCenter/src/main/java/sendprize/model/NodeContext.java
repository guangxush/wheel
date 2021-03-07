package sendprize.model;

import java.util.List;

/**
 * @author: guangxush
 * @create: 2021/03/07
 */
public interface NodeContext {

    /**
     * 待回滚节点
     *
     * @param nodeCode
     */
    void setRollbackNode(String nodeCode);

    /**
     * 获取待回滚的节点堆栈
     * @return
     */
    List<String> getRollbackNodes();

    /**
     * 打印堆栈信息
     * @return
     */
    String digestInfo();
}
