package six;

import java.util.Date;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class TestIntermediary {
    public static void main(String[] args) {

        // 基本的代理类
        OuterClass outerClass = new OuterClass(1L, "Tom", 1, 23.5, new Date());
        // BeanUtil
        InnerClass innerClassOne = new InnerClass();
        CopyUtil.getClassByBeanUtil(innerClassOne, outerClass);
        GetObject.runObject(innerClassOne);

        // PropertyUtil
        InnerClass innerClassTwo = new InnerClass();
        CopyUtil.getClassByPropertyUtil(innerClassTwo, outerClass);
        GetObject.runObject(innerClassTwo);

        // 置空
        outerClass.setId(null);
        outerClass.setName(null);
        outerClass.setAge(null);
        outerClass.setSex(null);
        outerClass.setBirthDay(null);
        // BeanUtil
        CopyUtil.getClassByBeanUtil(innerClassOne, outerClass);
        GetObject.runObject(innerClassOne);

        // PropertyUtil
        CopyUtil.getClassByPropertyUtil(innerClassTwo, outerClass);
        GetObject.runObject(innerClassTwo);

    }
}
