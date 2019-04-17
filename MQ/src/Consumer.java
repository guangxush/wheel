import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author: guangxush
 * @create: 2019/04/17
 */
public class Consumer implements Callable<Integer> {
    private final Queue queue;
    private final int SIZE;

    public Consumer(Queue queue, int size) {
        this.queue = queue;
        this.SIZE = size;
    }


    /**
     * 主动去消费数据
     * @return
     * @throws InterruptedException
     */
    public int consumer() throws InterruptedException{
        while(queue.isEmpty()){
            synchronized (queue){
                System.out.println("Queue is empty " + Thread.currentThread().getName()
                        + " is waiting , size: " + queue.size());
                queue.wait();
            }
        }
        synchronized (queue){
            queue.notify();
            return (int) queue.poll();
        }
    }

    @Override
    public Integer call(){
        while(true){
            try{
                System.out.println("Consumer:"+consumer());
                Thread.sleep(50);
            }catch(InterruptedException e){
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
