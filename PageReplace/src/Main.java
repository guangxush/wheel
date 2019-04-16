
/**
 * @author: guangxush
 * @create: 2019/04/16
 */
public class Main {
    public static void main(String[] args) {
        int page_size = 3;
        int[] array = new int[]{7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};
        FIFO fifo = new FIFO(page_size, array);
        System.out.println("FIFO's missing page interrupt is: "+fifo.fifoPageReplace());
        LRU lru = new LRU(page_size, array);
        //由于按照map输出的，所以顺序不一致
        System.out.println("LRUCache's missing page interrupt is: "+lru.lruPageReplace());
    }
}
