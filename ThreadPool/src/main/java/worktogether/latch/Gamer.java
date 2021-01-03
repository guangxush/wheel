package worktogether.latch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author: guangxush
 * @create: 2021/01/03
 */
public class Gamer implements Runnable {

    private CountDownLatch downLatch;
    private String name;

    public Gamer(CountDownLatch downLatch, String name) {
        this.downLatch = downLatch;
        this.name = name;
    }


    @Override
    public void run() {
        this.doPrepare();
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException ie) {
        }
        System.out.println(TimeUtils.getTimes() + this.name + "点击-->准备好了！");
        // 完成之后-1
        this.downLatch.countDown();
    }

    private void doPrepare() {
        System.out.println(TimeUtils.getTimes() + this.name + "进入游戏界面!");
    }
}
