/**
 * 保存json的数组
 * @author: guangxush
 * @create: 2019/04/07
 */
public class JsonArray {
    public char content;
    public JsonArray next;

    public JsonArray(char content, JsonArray next) {
        this.content = content;
        this.next = next;
    }
}
