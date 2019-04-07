/**
 * @author: guangxush
 * @create: 2019/04/07
 */
public class Main {
    public static void main(String[] args) {
        JSON jsonUtil = new JSONImpl();
        String jsonRes = jsonUtil.stringfy("hello");
        System.out.println(jsonRes);
    }
}
