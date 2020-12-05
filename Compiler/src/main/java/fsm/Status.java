package fsm;

/**
 * @author: guangxush
 * @create: 2020/12/05
 */
public interface Status {
    /**
     * 执行操作
     * @param stringBuffer
     * @return
     */
    public String operator(StringBuffer stringBuffer);
}
