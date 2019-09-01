## 页面置换算法

#### FIFO 
#### LRU

### 1. 为什么要有页面置换算法？
程序运行过程中，有时要访问的页面不在内存中，而需要将其调入内存。但是内存已经无空闲空间存储页面，为保证程序正常运行，系统必须从内存中调出一页程序或数据送到磁盘对换区，此时需要一定的算法来决定到低需要调出那个页面。通常将这种算法称为“页面置换算法”。

### 2. 介绍常见的两种页面置换算法

- 先进先出FIFO页面置换算法
原理：淘汰最先进入内存的页面，即选择在页面待的时间最长的页面淘汰
![image.png](https://github.com/guangxush/iTechHeart/blob/master/image/Wheel/FIFO.png)


- 最近最久未使用LRU置换算法
原理：选择最近且最久未被使用的页面进行淘汰
![image.png](https://github.com/guangxush/iTechHeart/blob/master/image/Wheel/LRU.png)

### 3. 具体实现

##### 3.1借助于LinkedHashMap实现LRU
 ```
import java.util.LinkedHashMap;
import java.util.Map;
 
public LRUCache<K, V> extends LinkedHashMap<K, V> {
  private int cacheSize;
 
  public LRUCache(int cacheSize) {
    super(3, 0.75, true);
    this.cacheSize = cacheSize;
  }
 
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return size() >= cacheSize;
  }
}

```
##### 3.2借助于LinkedHashMap实现FIFO
```
public class Cached<K, V> {
 
    private final int MAX_CACHE_SIZE;
    private final float DEFAULT_LOAD_FACTORY = 0.75f;

    LinkedHashMap<K, V> map;

    public Cached(int cacheSize) {
        MAX_CACHE_SIZE = cacheSize;
        int capacity = (int)Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTORY) + 1;
        /*
         * 第三个参数设置为true，代表linkedlist按访问顺序排序，可作为LRU缓存
         * 第三个参数设置为false，代表按插入顺序排序，可作为FIFO缓存
         */
        map = new LinkedHashMap<K, V>(capacity, DEFAULT_LOAD_FACTORY, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }
}
```

##### 3.3 基于队列实现FIFO
```
public class FIFO {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int[] array;
    private static int count = 0;
    private int page_size;

    public FIFO(int page_size, int[] array) {
        this.page_size = page_size;
        this.array = array;
    }

    public int fifoPageReplace() {
        if (page_size <= 0 || array == null || array.length == 0) {
            throw new IllegalArgumentException("The parameter is invalid!");
        }
        for (int element : array) {
            //页面中已经包含此元素
            if (queue.contains(element)) {
                System.out.println(queue.toString());
            }
            //页面大小未达到指定的值
            else if (queue.size() < page_size) {
                queue.offer(element);
                System.out.println(queue.toString());
            } else {
                //移出最早的元素,并加入新元素
                queue.poll();
                queue.offer(element);
                count++;
                System.out.println(queue.toString());
            }
        }
        return count;
    }

}
```
##### 3.4 基于Map和双向链表实现LRU
数据结构定义
```
public class LRUNode {
    Integer key;
    Object value;
    LRUNode prev;
    LRUNode next;
    public LRUNode(Integer key, Object value){
        this.key = key;
        this.value = value;
    }
}
```
具体实现
```
public class LRU {
    private HashMap<Integer, LRUNode> map;
    private LRUNode head;
    private LRUNode tail;

    private final int[] array;
    private static int count = 0;
    private int page_size;

    public LRU(int page_size, int[] array) {
        this.page_size = page_size;
        this.array = array;
        this.map = new HashMap<>();
    }

    public int lruPageReplace() {
        if (page_size <= 0 || array == null || array.length == 0) {
            throw new IllegalArgumentException("The parameter is invalid!");
        }
        for(int element:array){
            if(map.containsKey(element)){
                //已经存在的
                LRUNode node = map.get(element);
                remove(node);
                //放在头部
                setHead(node);
                print(map);
            }else if(map.size()<page_size){
                //元素个数少于page_size,直接放入
                LRUNode node = new LRUNode(element, null);
                map.put(element, node);
                //放在头部
                setHead(node);
                print(map);
            }else{
                //元素不存在，并且超出页面的大小，需要移出最久未使用的元素，加入新元素
                map.remove(tail.key);
                remove(tail);

                LRUNode node = new LRUNode(element, null);
                map.put(element, node);
                setHead(node);
                count++;
                print(map);
            }
        }
        return count;
    }

    private void print(HashMap<Integer, LRUNode> map){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i : map.keySet()){
            sb.append(i+", ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        sb.append("]");
        System.out.println(sb.toString());
    }

    private void setHead(LRUNode node){
        if(head!=null){
            node.next = head;
            head.prev = node;
        }
        head = node;
        if(tail==null){
            tail = node;
        }
    }

    private void remove(LRUNode node){
        if(node.prev!=null){
            node.prev.next = node.next;
        }else{
            head = node.next;
        }
        if(node.next!=null){
            node.next.prev=node.prev;
        }else{
            tail = node.prev;
        }
        node.next = null;
        node.prev = null;
    }
}
```
#### 4. 完整代码
[完整代码](https://github.com/guangxush/wheel/tree/master/PageReplace/src)
