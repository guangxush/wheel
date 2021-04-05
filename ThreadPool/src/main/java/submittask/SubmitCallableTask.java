package submittask;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 提交同步回调任务
 *
 * @author: guangxush
 * @create: 2020/11/29
 */
public class SubmitCallableTask {
    /**
     * 构造一个并发数是5，阻塞队列是0，一满就抛异常的线程池
     */
    public static ExecutorService THREAD_POOL = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue<>());

    public Random random = new Random(10);

    private PollingResult pollingResult = new PollingResult();

    public void execute() {
        Future<Integer> future = THREAD_POOL.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return pollingResult.executeTask(random.nextInt(1000), true);
            }
        });
        try {
            // get() 方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回
            System.out.println("SubmitCallableTask："+future.get() + "是偶数！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
