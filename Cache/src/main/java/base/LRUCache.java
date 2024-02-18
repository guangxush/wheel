package base;

import java.util.Arrays;

/**
 * LRU缓存（最近最久未使用-Least Recently Used）
 * LRU 是基于页面最后一次被使用的时间来判断的。如果一个页面自最后一次被使用时经过了足够长的时间，那么它将被替换掉，即使它在过去的一段时间内曾被频繁使用。这意味着LRU算法倾向于保留最近最少使用的页面。
 *
 * @author: guangxush
 * @create: 2024/02/18
 */
public class LRUCache {

    private final int capacity;

    private int[] cache;

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.cache = new int[capacity];
        // 初始化为-1
        Arrays.fill(cache, -1);
    }

    /**
     * 查找数据在数组中的索引
     *
     * @param data
     * @return
     */
    private int findDataIndex(int data){
        // 遍历数组，返回索引
        for(int i = 0; i < cache.length; i++){
            if(cache[i] == data) {
                return i;
            }
        }
        // 不存在返回-1
        return -1;
    }

    /**
     * 将数据移动到数组末尾
     *
     * @param dataIndex
     */
    private void moveToLast(int dataIndex){
        int data = cache[dataIndex];
        System.arraycopy(cache, dataIndex + 1, cache, dataIndex, cache.length - dataIndex -1);
        cache[cache.length - 1] = data;
    }

    /**
     * 将cache数组从索引1开始复制到索引0的位置，覆盖原来左侧的元素，复制个数为cache.length-1, 即整个数组长度
     */
    private void moveLeftOne(){
        System.arraycopy(cache, 1, cache, 0, cache.length - 1);
    }

    /**
     * 删除数组第一个元素
     */
    private void removeFirst(){
        // 将数组整体向左移动一位
        moveLeftOne();
        // 删除最后一个元素
        cache[cache.length -1] = -1;
    }

    /**
     * 遍历数组, 找到数组末尾, 放入元素
     * @param data
     */
    private void addToLast(int data){
        for(int i = 0; i < cache.length ; i++){
            if(cache[i] == -1){
                // 将元素放在数组末尾
                cache[i] = data;
                break;
            }
        }
    }

    /**
     * 访问数组, 对数组进行查找、移动、删除
     * @param data
     */
    private void accessData(int data){
        int dataIndex = findDataIndex(data);
        if(dataIndex != -1){
            // 查找存在，将数据移到数组末尾
            moveToLast(dataIndex);
        }else{
            // 数据不在缓存中
            if(cache[cache.length -1] != -1){
                // 说明缓存数据已满，删除最老的数据，在数组开头
                removeFirst();
            }
            // 将数据放在数组末尾
            addToLast(data);
        }
    }

    @Override
    public String toString(){
        return Arrays.toString(cache);
    }



    public static void main(String[] args) {
        LRUCache cache = new LRUCache(5);
        cache.accessData(1);
        cache.accessData(2);
        cache.accessData(3);
        cache.accessData(4);
        cache.accessData(5);

        // 测试输出
        System.out.println(cache);

        cache.accessData(3);
        System.out.println(cache);

        cache.accessData(6);
        System.out.println(cache);

    }
}
