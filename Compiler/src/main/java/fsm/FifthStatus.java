package fsm;

/**
 * @author: guangxush
 * @create: 2020/12/05
 */
public class FifthStatus implements Status{
    public String operator(StringBuffer stringBuffer) {
        return stringBuffer.append("5").toString();
    }
}
