package one;

import java.util.HashMap;
import java.util.Map;

/**
 * 工厂方法
 *
 * @author: guangxush
 * @create: 2020/05/31
 */
public class Factory {
    private static Map<String, String> map = new HashMap<String, String>();

    static {
        map.clear();
        // 模拟注册中心，存放实现类
        map.put("one.Children", "one.Children");
        map.put("one.Student", "one.Student");
    }

    public static void main(String[] args) {
        run("one.Student");
    }

    private static void run(String clzName) {
        if (null == clzName || clzName.length() == 0 || "".equals(clzName)) {
            System.out.println("参数不能为空");
            return;
        }
        if (!map.containsKey(clzName)) {
            System.out.println("参数不合法");
            return;
        }
        try {
            Class cls = Class.forName(map.get(clzName));
            Parents parents = (Parents) cls.newInstance();
            System.out.println("执行方法" + clzName);
            parents.function();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
