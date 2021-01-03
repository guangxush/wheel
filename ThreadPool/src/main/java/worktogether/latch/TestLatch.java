package worktogether.latch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: guangxush
 * @create: 2021/01/03
 */
public class TestLatch {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(10);
        List<Gamer> gamers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Gamer gamer = new Gamer(latch, i + 1 + "");
            gamers.add(gamer);
        }

        GameCenter boss = new GameCenter(latch);
        executor.execute(boss);
        gamers.stream().forEach(executor::execute);

        // 游戏结束
        executor.shutdown();
    }
}
