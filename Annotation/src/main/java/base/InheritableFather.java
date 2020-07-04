package base;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
@Inheritable
public class InheritableFather {
    public InheritableFather() {
        // InheritableBase是否具有 Inheritable Annotation
        System.out.println("InheritableFather:"+InheritableFather.class.isAnnotationPresent(Inheritable.class));
    }
}
