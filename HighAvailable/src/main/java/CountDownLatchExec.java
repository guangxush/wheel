import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发执行
 *
 * @author: guangxush
 * @create: 2024/06/25
 */
public class CountDownLatchExec {

    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2,
            2,
            1,
            TimeUnit.MICROSECONDS,
            new ArrayBlockingQueue<Runnable>(2));

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(2);
        Runnable task1 = () ->{
            try {
                System.out.println("task1 exec start....");
                Thread.sleep(3L);
                System.out.println("task1 exec end....");
            }catch (InterruptedException exception){
                System.out.println("task1 exec error....");
            }finally {
                latch.countDown();
            }
        };

        Runnable task2 = () ->{
            try {
                System.out.println("task2 exec start....");
                Thread.sleep(2L);
                System.out.println("task2 exec end....");
            }catch (InterruptedException exception){
                System.out.println("task2 exec error....");
            }finally {
                latch.countDown();
            }
        };

        // 线程池提交任务
        executor.execute(task1);
        executor.execute(task2);

        // 并发获取结果
        try{
            boolean await = latch.await(2L, TimeUnit.SECONDS);
            if(await){
                System.out.println("exec success!");
            }
        }catch (InterruptedException exception){
            System.out.println("exec error!");
        }
    }
}
