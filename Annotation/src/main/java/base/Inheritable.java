package base;

import java.lang.annotation.*;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited //当注解 Inheritable 被 @Inherited 标注时，它具有继承性。否则，没有继承性。
@interface Inheritable {
}
