import java.util.HashMap;
import java.util.Map;

/**
 * @author: guangxush
 * @create: 2019/04/07
 */
public class Main {
    public static void main(String[] args) {
        JSON jsonParse = new JSONImpl();
        String jsonV0_0 = "{\n" +
                "  \"key\":\"value\",\n" +
                "  \"value\":12e5,\n" +
                "  \"number\":12.5,\n" +
                "  \"int\":13,\n" +
                "  \"array\":[1,2,3],\n" +
                "  \"map\":{\"a\":1, \"b\":2}\n" +
                "}";
        Map<String, Object> map = jsonParse.parse(jsonV0_0);
        System.out.println(map.toString());
        Map<String, Integer> uid = new HashMap<>(2);
        uid.put("a", 1);
        uid.put("b", 2);
        Student student = new Student("zhangsan", 12, new int[]{1,2,3}, uid);
        String json0_1 = jsonParse.stringfy(new Object[]{student});
        System.out.println(json0_1);
        System.out.println(jsonParse.parse(json0_1).toString());
    }
}
class Student{
    private String name;
    private int age;
    private int[] projects;
    private Map<String, Integer> uid;

    public Student(String name, int age, int[] projects, Map<String, Integer> uid) {
        this.name = name;
        this.age = age;
        this.projects = projects;
        this.uid = uid;
    }
}