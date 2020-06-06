/**
 * @author: guangxush
 * @create: 2019/08/24
 */
public interface RunnableQueue {
    void offer(Runnable runnable);

    Runnable take() throws InterruptedException;

    int size();
}
