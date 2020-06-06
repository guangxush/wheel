/**
 * @author: guangxush
 * @create: 2019/08/24
 */
public interface ThreadPool {
    void execute(Runnable runnable);

    void shutdown();

    int getInitSize();

    int getMaxSize();

    int getCoreSize();

    int getQueueSize();

    int getActiveCount();

    boolean isShutdown();
}
