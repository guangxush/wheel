package logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
public class AnnotationTest {
    public static void main(String[] args) throws Exception {

        // 新建Logger
        Logger logger = new Logger();
        // 获取Logger的Class实例
        Class<Logger> c = Logger.class;
        // 获取 printLogger() 方法的Method实例
        Method method = c.getMethod("printLogger", new Class[]{String.class, int.class});
        // 执行该方法
        method.invoke(logger, new Object[]{"Test", 2});
        iteratorAnnotations(method);


        // 获取方法的Method实例
        Method emptyMethod = c.getMethod("empty", new Class[]{});
        // 执行该方法
        emptyMethod.invoke(logger, new Object[]{});
        iteratorAnnotations(emptyMethod);
    }

    public static void iteratorAnnotations(Method method) {

        // 判断方法是否包含LoggerAnnotation注解
        if(method.isAnnotationPresent(LoggerAnnotation.class)){
            // 获取该方法的MyAnnotation注解实例
            LoggerAnnotation testAnnotation = method.getAnnotation(LoggerAnnotation.class);
            // 获取 myAnnotation的值，并打印出来
            String[] values = testAnnotation.value();
            for (String str:values){
                System.out.printf(str+", ");
            }
            System.out.println();
        }

        // 获取方法上的所有注解，并打印出来
        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation : annotations){
            System.out.println(annotation);
        }
    }
}
