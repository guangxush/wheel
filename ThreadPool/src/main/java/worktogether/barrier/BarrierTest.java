package worktogether.barrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: guangxush
 * @create: 2021/01/03
 */
public class BarrierTest {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        CyclicBarrier barrier = new CyclicBarrier(5);
        List<AutoPlay> autoPlayers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AutoPlay autoPlayer = new AutoPlay(barrier, i + 1 + "");
            autoPlayers.add(autoPlayer);
        }

        autoPlayers.stream().forEach(executor::execute);

        // 游戏结束
        executor.shutdown();
    }
}
