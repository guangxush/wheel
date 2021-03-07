package sendprize.model;

/**
 * @author: guangxush
 * @create: 2021/03/07
 */
public interface NodeConfig<X extends ProcessModel, Y extends NodeContext> {

    /**
     * 配置转换，模型转换为配置
     *
     * @param node
     * @param model
     * @param context
     */
    void convert(Node node, X model, Y context);
}
