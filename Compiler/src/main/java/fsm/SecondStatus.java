package fsm;

/**
 * @author: guangxush
 * @create: 2020/12/05
 */
public class SecondStatus implements Status {
    public String operator(StringBuffer stringBuffer) {
        for (int i = 0; i < 5; i++) {
            stringBuffer.append("2");
        }
        return stringBuffer.toString();
    }
}
