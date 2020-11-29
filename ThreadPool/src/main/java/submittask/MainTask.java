package submittask;

/**
 * @author: guangxush
 * @create: 2020/11/29
 */
public class MainTask {

    private static SubmitRunnableTask submitRunnableTask = new SubmitRunnableTask();

    private static SubmitCallableTask submitCallableTask = new SubmitCallableTask();

    public static void main(String[] args) {
        // 提交异步任务，这个不会抛异常
        for(int i=0;i<10;i++){
            submitCallableTask.execute();
        }
        // 同时提交10个任务，肯定会抛异常
        for(int i=0;i<10;i++){
            submitRunnableTask.execute();
        }
    }
}
