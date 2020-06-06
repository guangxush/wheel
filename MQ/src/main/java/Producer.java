import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author: guangxush
 * @create: 2019/04/17
 */
public class Producer implements Runnable{
    private final Queue queue;
    private final int SIZE;
    private volatile int content = 0;

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public Producer(Queue queue, int size) {
        this.queue = queue;
        this.SIZE = size;
    }

    /**
     * 批量传输
     * @param array
     */
    public void producer(int[] array) {
        try{
            for(int i:array){
                producer(i);
            }
        }catch (InterruptedException e){
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * 单个传入元素
     * @param i
     * @throws InterruptedException
     */
    public void producer(int i)throws InterruptedException{
        while(queue.size()==SIZE){
            //队列已满，循环等待
            synchronized (queue){
                System.out.println("Queue is full "+Thread.currentThread().getName()+
                        " is waiting, size: "+queue.isEmpty());
                queue.wait();
            }
        }
        synchronized (queue){
            //元素放入队列，并通知其他
            queue.offer(i);
            queue.notify();
        }
    }

    @Override
    public void run() {
        try{
            producer(content);
            System.out.println("Produce the content: "+content);
        }catch (InterruptedException e){
            Logger.getLogger(Producer.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
