package data;

import java.lang.annotation.*;

/**
 * @author: guangxush
 * @create: 2020/08/09
 */
@Target({ElementType.TYPE}) //类注解
@Documented //将注解包含在Javadoc中
@Inherited //允许子类继承父类中的注解
@Retention(RetentionPolicy.RUNTIME) //VM将在运行期间保留注解，因此可以通过反射机制读取注解的信息
public @interface DataAnnotation {
    boolean value() default false;
}
