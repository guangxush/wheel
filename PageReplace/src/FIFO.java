import java.util.LinkedList;
import java.util.Queue;

/**
 * FIFO先进先出页面置换算法
 *
 * @author: guangxush
 * @create: 2019/04/16
 */
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
