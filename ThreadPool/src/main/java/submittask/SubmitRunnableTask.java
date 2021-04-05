package submittask;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 提交同步任务
 *
 * @author: guangxush
 * @create: 2020/11/29
 */
public class SubmitRunnableTask {
    /**
     * 构造一个并发数是5，阻塞队列是0，一满就抛异常的线程池
     */
    public static ExecutorService THREAD_POOL = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());

    public Random random = new Random(10);

    private PollingResult pollingResult = new PollingResult();

    public void execute() {
        THREAD_POOL.submit(() -> {
            // 提交带执行的任务
            pollingResult.executeTask(random.nextInt(1000));
        });
    }
}
