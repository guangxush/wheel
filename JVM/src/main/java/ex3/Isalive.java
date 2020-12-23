package ex3;

/**
 * 判读对象存活
 * VM Args: -XX:+PrintGC
 * @author: guangxush
 * @create: 2020/12/23
 */
public class Isalive {
    public Object instance = null;
    // 占据10M内存，便于分析GC
    private  byte[] bigSize = new byte[10*1024*1024];

    public static void main(String[] args) {
        Isalive objectA = new Isalive();// objectA 局部变量表 GCRoot
        Isalive objectB = new Isalive();// objectB 局部变量表
        // 相互引用
        objectA.instance = objectB;
        objectB.instance = objectA;
        // 切断可达性
        objectA = null;
        objectB = null;
        // 强制垃圾回收，能够回收是可达性算法，不能回收是引用计数法
        System.gc();
    }
    //[GC (System.gc())  23101K->616K(251392K), 0.0009143 secs]
    //[Full GC (System.gc())  616K->381K(251392K), 0.0060401 secs]
}
