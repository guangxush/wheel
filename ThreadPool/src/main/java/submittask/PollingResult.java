package submittask;

import java.util.Random;

/**
 * 轮询任务结果
 *
 * @author: guangxush
 * @create: 2020/11/29
 */
public class PollingResult {

    public Random random = new Random();

    public void executeTask(int sleepTime) {
        try {
            int value = random.nextInt(10);
            while(true){
                // 模拟轮询过程，随机耗时1-5s
                Thread.sleep(sleepTime);
                if ( value % 2 == 0) {
                    // 当前随机数是偶数直接结束循环
                    break;
                }
                value = random.nextInt(10);
            }
            System.out.println("SubmitRunnableTask："+ value + "是偶数！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Integer executeTask(int sleepTime, boolean callback) {
        try {
            int value = random.nextInt(10);
            while(true){
                // 模拟轮询过程，随机耗时1-5s
                Thread.sleep(sleepTime);
                if ( value % 2 == 0) {
                    // 当前随机数是偶数直接结束循环
                    break;
                }
                value = random.nextInt(10);
            }
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
