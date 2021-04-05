package submittask;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 提交异步回掉任务
 *
 * @author: guangxush
 * @create: 2021/04/05
 */
public class SubmitCallableUnBlockTask {
    /**
     * 构造一个并发数是5，阻塞队列是0，一满就抛异常的线程池
     */
    public static ExecutorService THREAD_POOL = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());

    public Random random = new Random(10);

    private PollingResult pollingResult = new PollingResult();

    public void execute() {
        THREAD_POOL.submit(new Runnable() {
            @Override
            public void run(){
                // 放入回掉函数，让回掉函数返回结果
                pollingResult.executeTask(random.nextInt(1000), new Response() {
                    @Override
                    public void printSomething(String message) {
                        System.out.println(message);
                    }
                });
            }
        });
    }
}
