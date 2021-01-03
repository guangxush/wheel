package worktogether.latch;

import java.util.concurrent.CountDownLatch;

/**
 * @author: guangxush
 * @create: 2021/01/03
 */
public class GameCenter implements Runnable {

    private CountDownLatch downLatch;

    public GameCenter(CountDownLatch downLatch) {
        this.downLatch = downLatch;
    }

    @Override
    public void run() {
        System.out.println(TimeUtils.getTimes() + "游戏中心正在等待所有玩家就位......");
        try {
            // 等待操作
            this.downLatch.await();
        } catch (InterruptedException e) {
        }
        System.out.println(TimeUtils.getTimes() + "所有玩家都已经准备完成，开始进入游戏！");
    }

}
