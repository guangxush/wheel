package demo1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author: guangxush
 * @create: 2020/09/25
 */
public class TestSetInjection {
    public static void main(String[] args) throws Exception{
        UserController userController = new UserController();
        UserService userService = new UserService();
        System.out.println(userService);
        // 将userService注入到userController中

        // 1. 获取class对象
        Class<? extends UserController> clazz = userController.getClass();

        // 2. 获取属性
        Field userServiceField = clazz.getDeclaredField("userService");
        // 也可以不设置可见性
        userServiceField.setAccessible(true);

        // 3. 获取对应的set方法
        String name  = userServiceField.getName();
        String methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1,name.length());
        Method method = clazz.getMethod(methodName, UserService.class);

        // 4. 调用setUserService方法
        method.invoke(userController, userService);
        System.out.println(userController.getUserService());
    }
}
