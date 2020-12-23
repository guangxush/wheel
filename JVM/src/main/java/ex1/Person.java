package ex1;

/**
 * @author: guangxush
 * @create: 2020/12/23
 *
 * 栈帧组成：局部变量表、完成出口、操作数栈、动态链接
 * 局部变量表：存放8大基础类型+引用
 * 操作数栈：x=1--> 0:iconst_1（常量1压入操作数栈） 1:istore_1（出栈存放在局部变量表）
 * 方法出口：return 返回到原来方法的第几行
 * 程序计数器：时间片轮转 确保多线程执行 1 2 3记录字节码对应的行号
 * 动态连接：多态
 */
public class Person {
    public int work() throws Exception{
        int x = 1; // 存放在局部变量表
        int y = 2;
        int z = (x+y)*10;
        Object o = new Object();// 朝声夕死
        return z;
    }
    // 执行过程
    //public class ex1.Person {
    //  public ex1.Person();
    //    Code:
    //       0: aload_0
    //       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
    //       4: return
    //
    //  public int work() throws java.lang.Exception;
    //    Code:
    //       0: iconst_1
    //       1: istore_1
    //       2: iconst_2
    //       3: istore_2
    //       4: iload_1
    //       5: iload_2
    //       6: iadd
    //       7: bipush        10
    //       9: imul
    //      10: istore_3
    //      11: new           #2                  // class java/lang/Object
    //      14: dup
    //      15: invokespecial #1                  // Method java/lang/Object."<init>":()V
    //      18: astore        4
    //      20: iload_3
    //      21: ireturn
    //
    //  public static void main(java.lang.String[]) throws java.lang.Exception;
    //    Code:
    //       0: new           #3                  // class ex1/Person
    //       3: dup
    //       4: invokespecial #4                  // Method "<init>":()V
    //       7: astore_1
    //       8: aload_1
    //       9: invokevirtual #5                  // Method work:()I
    //      12: pop
    //      13: return
    //}

    public static void main(String[] args) throws Exception {
        Person person = new Person();
        person.work();
    }
    // 将文件编译之后找到对象的class文件执行反汇编 javap -c Person.class 可以看到字节码

}
