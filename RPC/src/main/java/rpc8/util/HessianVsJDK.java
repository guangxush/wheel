package rpc8.util;

import common.User;

import java.io.*;

/**
 * @author: guangxush
 * @create: 2020/06/09
 */
public class HessianVsJDK {
    public static void main(String[] args) throws Exception {
        User u = new User(1,"Sandy");
        System.out.println("Hessian:"+ HessianSDK.serialize(u).length);
        System.out.println("JDK:"+jdkSerialByte(u).length);
    }

    /**
     * 序列化一个对象（可以存储到一个文件也可以存储到字节数组）这里存储到自己数组
     */
    public static byte[] jdkSerialByte(Object obj)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 反序列化一个对象
     */
    public static Object jdkDeSerialByte(byte[] by)
    {
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(by));
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    /**
     * serialVersionUID作用： 
     *        序列化时为了保持版本的兼容性，即在版本升级时反序列化仍保持对象的唯一性。
     * 有两种生成方式：
     *        一个是默认的1L，比如：private static final long serialVersionUID = 1L;
     *        一个是根据类名、接口名、成员方法及属性等来生成一个64位的哈希字段，比如：
     *        private static final   long     serialVersionUID = xxxxL;
     *
     * 1、若继承的父类没有实现Serializable接口，但是又想让子类可序列化，子类实现Serializable接口，子类必须有可访问的无参构造方法，
     * 用于保存和恢复父类的public或protected或同包下的package字段的状态，否则在序列化或反序列化时会抛出RuntimeException异常，
     * 对于序列化后的子类，在进行反序列化时，理论上无法初始化父类中private（不可访问）对象变量的状态或值。
     *
     * 2、在对可序列化类中的属性进行序列化时，如果遇到不可序列化的对象变量，此时会针对不可序列化的类抛出NotSerializableException异常
     *
     * 3、对于可序列化的非数组类，强烈建议显示声明static型、long型、final型serialVersionUID字段用于标识当前序列化类的版本号，
     * 否则在跨操作系统、跨编译器之间进行序列化和反序列化时容易出现InvalidClassException异常
     *
     */
}
