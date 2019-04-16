import java.util.HashMap;

/**
 * LRU最近最久未使用算法
 *
 * @author: guangxush
 * @create: 2019/04/16
 */
public class LRU {
    private HashMap<Integer, LRUNode> map;
    private LRUNode head;
    private LRUNode tail;

    private final int[] array;
    /**
     * 缺页次数
     */
    private static int count = 0;
    /**
     * 页框大小
     */
    private int page_size;

    public LRU(int page_size, int[] array) {
        this.page_size = page_size;
        this.array = array;
        this.map = new HashMap<>();
    }

    /**
     * LRU页面置换算法实现
     *
     * @return
     */
    public int lruPageReplace() {
        if (page_size <= 0 || array == null || array.length == 0) {
            throw new IllegalArgumentException("The parameter is invalid!");
        }
        for (int element : array) {
            if (map.containsKey(element)) {
                //已经有了的元素，取出来放在链表的头部
                LRUNode node = map.get(element);
                remove(node);
                //放在头部
                setHead(node);
                print(map);
            } else if (map.size() < page_size) {
                //元素个数少于page_size,直接放入map
                LRUNode node = new LRUNode(element, null);
                map.put(element, node);
                //node放在链表头部
                setHead(node);
                print(map);
            } else {
                //移出链表尾部最久未使用的元素
                map.remove(tail.key);
                remove(tail);

                //加入新元素放在map中，并放在链表的头部
                LRUNode node = new LRUNode(element, null);
                map.put(element, node);
                setHead(node);
                count++;
                print(map);
            }
        }
        return count;
    }

    /**
     * 打印当前内存中的页号
     *
     * @param map
     */
    private void print(HashMap<Integer, LRUNode> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i : map.keySet()) {
            sb.append(i + ", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        System.out.println(sb.toString());
    }

    /**
     * 设置链表的头部
     *
     * @param node
     */
    private void setHead(LRUNode node) {
        if (head != null) {
            node.next = head;
            head.prev = node;
        }
        head = node;
        if (tail == null) {
            tail = node;
        }
    }

    /**
     * 移出链表中的某一个元素
     *
     * @param node
     */
    private void remove(LRUNode node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        node.next = null;
        node.prev = null;
    }
}
