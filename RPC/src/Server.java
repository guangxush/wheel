import java.io.IOException;

/**
 * @author: guangxush
 * @create: 2019/05/18
 */
public interface Server {

    public void start() throws IOException;

    public void register(Class serviceInterface, Class impl);

    public boolean isRunning();

    public int getPort();

    public void stop();
}
