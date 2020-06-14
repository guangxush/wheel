import java.lang.instrument.Instrumentation;

/**
 * @author: guangxush
 * @create: 2020/06/14
 */
public class AgentApplication {
    public static void premain(String arg, Instrumentation instrumentation) {
        System.err.println("agent startup , args is " + arg);
    }
}
