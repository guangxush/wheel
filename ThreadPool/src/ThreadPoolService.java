import java.util.concurrent.TimeUnit;

/**
 * @author: guangxush
 * @create: 2019/08/24
 * 自定义一个线程池
 */
public class ThreadPoolService {
    public static void main(String[] args) throws InterruptedException {
        final ThreadPool threadPool = new BasicThreadPool(2, 6, 4, 1000);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println(Thread.currentThread().getName() + " is running and clone.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        for (; ; ) {
            System.out.println("getActiveCount:" + threadPool.getActiveCount());
            System.out.println("getQueueSize:" + threadPool.getQueueSize());
            System.out.println("getCoreSize:" + threadPool.getCoreSize());
            System.out.println("getMaxSize:" + threadPool.getMaxSize());
            System.out.println("=============================");
//            TimeUnit.SECONDS.sleep(5);
//            TimeUnit.SECONDS.sleep(12);
//            threadPool.shutdown();
//            Thread.currentThread().join();
        }
    }
}
