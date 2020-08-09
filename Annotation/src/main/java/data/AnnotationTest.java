package data;

import java.lang.reflect.Field;

/**
 * @author: guangxush
 * @create: 2020/08/09
 */
public class AnnotationTest {
    public static void main(String[] args) throws Exception {
        // 新建Student
        Student student = new Student();
        // 获取Student的Class实例
        Class<Student> studentClass = Student.class;
        iteratorAnnotations(studentClass);
    }

    public static void iteratorAnnotations(Class clazz) throws IllegalAccessException, InstantiationException {

        // 判断方法是否包含DataAnnotation注解
        if(clazz.isAnnotationPresent(DataAnnotation.class)){
            // 获取该方法的MyAnnotation注解实例
            DataAnnotation testAnnotation = (DataAnnotation) clazz.getAnnotation(DataAnnotation.class);
            // 获取 myAnnotation的值，并打印出来
            boolean value = testAnnotation.value();
            // 如果value为true，打印参数，否则直接返回
            if(value){
                Field[] fields = clazz.getDeclaredFields();
                for( Field field : fields){
                    // 设置可见性，便于打印变量
                    field.setAccessible(true);
                    System.out.println( field.getName() + ":" +field.get(clazz.newInstance()));
                }
            }
            System.out.println();
        }
    }
}
