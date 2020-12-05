package fsm;

/**
 * @author: guangxush
 * @create: 2020/12/05
 */
public class ThirdStatus implements Status {
    public String operator(StringBuffer stringBuffer) {
        return stringBuffer.append("3").toString();
    }
}
