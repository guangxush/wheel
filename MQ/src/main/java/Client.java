import java.util.concurrent.*;

/**
 * @author: guangxush
 * @create: 2019/04/17
 */
public class Client {
    public static void main(String[] args) {
        /**
         * 设置队列和队列长度
         */
        int size = 5;
        Queue queue = new Queue(size);

        /**
         * 生产者消费者
         */
        Producer producer = new Producer(queue, size);
        Consumer consumer = new Consumer(queue, size);

        /**
         * 创建线程池运行
         */
        Thread prodThead = new Thread(producer, "Producer");
        ThreadPoolExecutor exec = new ThreadPoolExecutor(2, 2,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());

        for (int i = 11; i < 100; i++) {
            producer.setContent(i);
            exec.execute(prodThead);
            if (i % 5 == 0) {
                exec.submit(consumer);
            }
        }
    }
}
