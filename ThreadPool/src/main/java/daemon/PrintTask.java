package daemon;

/**
 * 模拟打印
 *
 * @author: guangxush
 * @create: 2021/04/05
 */
public class PrintTask implements Runnable{

    /**
     * 打印任务
     */
    @Override
    public void run() {
        while(true){
            System.out.println("正在运行!");
        }
    }
}
