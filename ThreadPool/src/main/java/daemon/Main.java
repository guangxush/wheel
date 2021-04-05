package daemon;

/**
 * 验证守护线程daemon
 *
 * @author: guangxush
 * @create: 2021/04/05
 */
public class Main {
    public static void main(String[] args) {
        // 创建任务
        PrintTask printTask = new PrintTask();
        // 创建线程
        Thread thread = new Thread(printTask);
        // 判断是否为后台线程 【注释打印语句，防止刷屏输出】
        System.out.println("设置前:"+ thread.isDaemon());
        // 设置线程为后台线程
        thread.setDaemon(true);
        // 判断是否为后台线程
        System.out.println("设置后:"+ thread.isDaemon());
        // 启动线程
        thread.start();
        try{
            //主线程休眠1秒
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
