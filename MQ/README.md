## 实现一个消息队列

### Kafka消息队列基本功能
#### 生产者发送数据到指定的队列
#### 消费者从指定的队列中取数据，并返回偏移量
#### 消费者组中只能有一个消费者读取指定的队列
#### 控制中心负责主从复制

### 自定义消息队列功能

#### 定义一个队列
```java
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

```
#### 定义生产者
```java
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

```

#### 定义消费者
```java
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

```

#### 定义客户端，使用消息队列

```java
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

```