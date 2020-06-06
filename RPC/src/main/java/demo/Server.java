package demo;

import java.io.IOException;

/**
 * @author: guangxush
 * @create: 2019/05/18
 */
public interface Server {

    void start() throws IOException;

    void register(Class serviceInterface, Class impl);

    boolean isRunning();

    int getPort();

    void stop();
}
