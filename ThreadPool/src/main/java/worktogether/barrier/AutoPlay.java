package worktogether.barrier;

import worktogether.latch.TimeUtils;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author: guangxush
 * @create: 2021/01/03
 */
public class AutoPlay implements Runnable {

    private CyclicBarrier cyclicBarrier;
    private String name;

    public AutoPlay(CyclicBarrier cyclicBarrier, String name) {
        this.name = name;
        this.cyclicBarrier = cyclicBarrier;
    }


    @Override
    public void run() {
        System.out.println(TimeUtils.getTimes() + name + "正在执行个人升级任务");
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
            System.out.println(TimeUtils.getTimes() + name + "已经完成个人升级任务");
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
            System.out.println(TimeUtils.getTimes() + name + "等待所有人集合，一起推塔!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
