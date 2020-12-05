package fsm;

/**
 * @author: guangxush
 * @create: 2020/12/05
 */
public class FourthStatus implements Status{

    public String operator(StringBuffer stringBuffer) {
        for (int i = 0; i < 3; i++) {
            stringBuffer.append("4");
        }
        return stringBuffer.toString();
    }
}
