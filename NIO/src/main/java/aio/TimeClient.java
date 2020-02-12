package aio;

/**
 * @author: guangxush
 * @create: 2020/02/12
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 默认值
            }
        }
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port),"AIO-AysncTimeClientHandler-001").start();
    }
}
