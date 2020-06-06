import java.util.*;

/**
 * 最近最久未使用置换算法的思想用作缓存
 *
 * @author: guangxush
 * @create: 2019/04/16
 */
public class LRUCache {
    private HashMap<Integer, LRUNode> map;
    private int page_size;
    private LRUNode head;
    private LRUNode tail;

    private Object get(Integer key) {
        LRUNode node = map.get(key);
        if (node != null) {
            remove(node, false);
            setHead(node);
            return node.value;
        }
        return null;
    }

    private void set(Integer key, Object value) {
        LRUNode node = map.get(key);
        if (node != null) {
            node = map.get(key);
            node.value = value;
            remove(node, false);
        } else {
            node = new LRUNode(key, value);
            if (map.size() >= page_size) {
                remove(tail, true);
            }
            this.map.put(key, node);
        }
        setHead(node);
    }

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

    private void remove(LRUNode node, boolean flag) {
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
        if (flag) {
            map.remove(node.key);
        }
    }

    public LRUCache(int page_size) {
        this.page_size = page_size;
        this.map = new HashMap<>();
    }

}
