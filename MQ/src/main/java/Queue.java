/**
 * 基于数组的方式实现Queue
 * @author: guangxush
 * @create: 2019/04/17
 */
public class Queue {
    private final Object[] items;
    /**
     * 队头
     */
    private volatile int head = 0;
    /**
     * 队尾
     */
    private volatile int tail = 0;

    /**
     * 初始化队列
     * @param capacity
     */
    public Queue(int capacity){
        this.items = new Object[capacity];
    }

    /**
     * 入队
     * @param item
     * @return
     */
    public synchronized boolean offer(Object item){
        if(head==(tail+1)%items.length){
            //队列已满
            return false;
        }
        //队尾插入元素
        items[tail] = item;
        //队尾往后移动一个元素
        tail = (tail+1)%items.length;
        return true;
    }

    /**
     * 获取队头元素
     * @return
     */
    public synchronized Object peek(){
        //队列为空
        if(head==tail){
            return null;
        }
        return items[head];
    }

    /**
     * 元素出队
     * @return
     */
    public synchronized Object poll(){
        if(head==tail){
            return null;
        }
        Object item = items[head];
        //head置为空
        items[head] = null;
        //head后移一个元素
        head = (head+1)%items.length;
        return item;
    }

    /**
     * 计算队列元素个数
     * @return
     */
    public int size(){
        //尝试三次如果每次获得的size一样，那么返回size
        int size = getSize();
        int try_count = 2;
        while(try_count--!=0){
            if(size == getSize()){
                continue;
            }else{
                break;
            }
        }
        if(try_count==0){
            return size;
        }else{
            //否则对队列加锁返回数目
            synchronized (items){
                return getSize();
            }
        }
    }

    private int getSize(){
        if(tail>=head){
            return tail-head;
        }else{
            return tail+items.length-head;
        }
    }

    /**
     * 队满
     * @return
     */
    public boolean isFull(){
        return head==(tail+1)%items.length;
    }

    /**
     * 队空
     * @return
     */
    public boolean isEmpty(){
        return head==tail;
    }
}
