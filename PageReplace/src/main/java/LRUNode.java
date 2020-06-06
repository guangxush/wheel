/**
 * @author: guangxush
 * @create: 2019/04/16
 */
public class LRUNode {
    Integer key;
    Object value;
    LRUNode prev;
    LRUNode next;

    public LRUNode(Integer key, Object value) {
        this.key = key;
        this.value = value;
    }
}
