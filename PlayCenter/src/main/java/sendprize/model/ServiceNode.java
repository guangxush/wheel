package sendprize.model;

/**
 * 组件接口
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public interface ServiceNode<X extends NodeConfig, Y extends NodeContext> {

    /**
     * 获取配置类名
     *
     * @return
     */
    Class<X> getNodeConfigType();

    /**
     * 组件接口
     *
     * @param node 当前节点
     * @param config 当前配置
     * @param context 当前上下文
     */
    default void execute(Node node, X config, Y context){

    }

    /**
     * 是否可回滚
     * @return
     */
    default boolean isRollBackable(){
        return false;
    }

    /**
     * 依赖的组件
     *
     * @return
     */
    default String[] dependComponents(){
        return new String[0];
    }
}
