package six;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: guangxush
 * @create: 2020/06/14
 */
public class CopyUtil {

    @SuppressWarnings("unchecked")
    public static void getClassByBeanUtil(InnerClass dest, OuterClass source) {
        try {
            BeanUtils.copyProperties(dest, source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("BeanUtils result:" + dest);
    }

    public static void getClassByPropertyUtil(InnerClass dest, OuterClass source) {
        try {
            PropertyUtils.copyProperties(dest, source);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println("PropertyUtils result:" + dest);
    }
}
