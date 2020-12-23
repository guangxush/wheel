package ex1;

/**
 * @author: guangxush
 * @create: 2020/12/23
 */
public class StackError {
    public static void main(String[] args) {
        A();
    }
    public static void A(){
        A();// 死循环，栈帧不断入栈不出栈
        // StackOverflowError
        // 虚拟机栈大小固定 HotSpot
        // 启动一个线程 虚拟机栈 1M 如果启动1000个线程，但是只有800M，会出现OOM OutOfMemory
        // 做一些节约内存的处理
        // 1. -Xss256k 1000个线程=256M
        // 2. 递归调用改为非递归调用
    }
}
