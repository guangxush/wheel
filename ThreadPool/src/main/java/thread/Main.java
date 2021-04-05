package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: guangxush
 * @create: 2021/04/05
 */
public class Main {
    public static void main(String[] args) {
        // 1.1创建TestThread对象
        TestThread testThread = new TestThread();
        // 1.2启动线程
        testThread.start();

        // 2.1创建TestRunnable对象
        TestRunnable testRunnable = new TestRunnable();
        // 2.2创建线程
        Thread thread = new Thread(testRunnable);
        // 2.3启动线程
        thread.start();

        // 3.1创建TestCallable对象
        TestCallable testCallable = new TestCallable();
        // 3.2创建FutureTask实例
        FutureTask<String> futureTask = new FutureTask<>(testCallable);
        // 3.3创建线程
        Thread threadCall = new Thread(futureTask);
        // 3.4 启动线程
        threadCall.start();
        // 3.5 获取执行结果
        try {
            String result = futureTask.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
