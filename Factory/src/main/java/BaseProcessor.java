/**
 * @author: guangxush
 * @create: 2021/10/30
 */
public abstract class BaseProcessor {

    /**
     * 支持的处理类型
     *
     * @return
     */
    public abstract SupportEnum supportProcessType();

    /**
     * 处理器实现类
     *
     * @param param
     */
    public abstract void process(String param);
}
