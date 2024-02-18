package base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * 基本的LFU缓存（Least Frequently Used）
 * 数组实现：在一段时间内,数据被使用频次最少的,优先被淘汰
 * LFU 是基于页面在一段时间内的使用频率来判断的。如果一个页面的使用频率较低，那么在任何给定的时间内，它更有可能被替换。
 * 这表明LFU算法倾向于保留最不经常使用的页面
 *
 * @author: guangxush
 * @create: 2023/12/17
 */
public class LFUCache {

    class Node {
        int val;
        int key;
        int count;
    }

    int minCount;
    int cap;
    Map<Integer, Node> m; // k: key, v: node
    Map<Integer, LinkedHashSet<Node>> countToNodes; // k: count, v: a linked hashset of all nodes that their keys are equal to count

    public LFUCache(int capacity) {
        minCount = 1;
        cap = capacity;
        countToNodes = new HashMap<Integer, LinkedHashSet<Node>>();
        m = new HashMap<Integer, Node>();
    }

    public int get(int key) {
        if (!m.containsKey(key)) {
            return -1;
        }
        Node cur = m.get(key);
        // remove key from current count (since we will inc count)
        countToNodes.get(cur.count).remove(cur);
        if (cur.count == minCount && countToNodes.get(cur.count).size() == 0) {
            minCount++; // nothing in the current minCount bucket
        }
        cur.count++;

        if (!countToNodes.containsKey(cur.count)) { // a new count
            countToNodes.put(cur.count, new LinkedHashSet<Node>());
        }
        countToNodes.get(cur.count).add(cur);
        return m.get(key).val;
    }

    public void put(int key, int value) {
        if (m.containsKey(key)) {
            m.get(key).val = value;
            get(key); // update key's count
            return;
        }

        if (m.size() >= cap) {
            Node nodeToRemove = countToNodes.get(minCount).iterator().next();
            countToNodes.get(minCount).remove(nodeToRemove);
            m.remove(nodeToRemove.key);
        }

        minCount = 1;
        Node cur = new Node();
        cur.val = value;
        cur.count = minCount;
        cur.key = key;
        m.put(key, cur);
        if (!countToNodes.containsKey(cur.count)) {
            countToNodes.put(cur.count, new LinkedHashSet<Node>());
        }
        countToNodes.get(cur.count).add(cur);
    }

}
