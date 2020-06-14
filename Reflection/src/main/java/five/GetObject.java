package five;

import org.springframework.cglib.reflect.FastClass;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class GetObject {

    /**
     * 反射+动态代理的方式调用Parents里面的方法
     * @return
     */
    public static Object runObject(final Parents post, String methodName, Object[] parameters){
        try {
            Class<?> clazz = Class.forName(post.getClass().getName());
            //获取本类的所有方法，存放入数组
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if(methodName.equals(method.getName())){
                    System.out.println("方法名："+method.getName());
                    //获取本方法所有参数类型，存入数组
                    Class<?>[] getTypeParameters = method.getParameterTypes();
                    if(getTypeParameters.length==0){
                        System.out.println("此方法无参数");
                    }
                    for (Class<?> class1 : getTypeParameters) {
                        String parameterName = class1.getName();
                        System.out.println("参数类型："+parameterName);
                    }
                    FastClass fastClazz = FastClass.create(Parents.class);
                    int methodIndex = fastClazz.getIndex(method.getName(), getTypeParameters);
                    return fastClazz.invoke(methodIndex, post, parameters);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
