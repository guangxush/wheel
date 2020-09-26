package demo2;

import java.util.stream.Stream;

/**
 * @author: guangxush
 * @create: 2020/09/25
 */
public class TestAutowired {

    public static void main(String[] args){
        UserController userController = new UserController();

        // 获取clazz对象
        Class<? extends UserController> clazz = userController.getClass();
        Stream.of(clazz.getDeclaredFields()).forEach(
                field -> {
                    // 判断属性是否有注解
                    Autowired annotation = field.getAnnotation(Autowired.class);
                    if(annotation != null){
                        field.setAccessible(true);
                        // 获取当前属性的类型，有了类型可以创建具体的对象
                        Class<?> type = field.getType();
                        // 创建对象
                        try {
                            // 类似于demo1中的new Service
                            Object instance = type.newInstance();
                            // 给属性赋值
                            field.set(userController, instance);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        System.out.println(userController.getUserService());
    }
}
