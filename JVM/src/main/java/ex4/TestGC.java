package ex4;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

/**
 * VM参数
 * -Xms:1000M -Xms1000M -XX:+PrintGCDetails
 * -XX:+PrintGCDetails -XX:+UserSerialGC
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDetails -XX:+UserConcMarkSweepGC
 * -XX:+PrintGCDetails -XX:+UserG1GC
 * 对象优先在Eden区分配，长期存活的对象会进入老年代
 * 运行程序，使用Java VisualJVM查看曲线
 *
 * @author: guangxush
 * @create: 2020/12/22
 */
public class TestGC {

    public static void main(String[] args) {
        // 填充对象线程+打印线程同时进行
        FillListThread thread = new FillListThread(); // 造成FullGc, STW
        thread.start();// FullGc
    }

    /**
     * 不停地往list中填充数据
     * 不断的填充堆，触发GC
     */
    public static class FillListThread extends Thread{
        List<byte[]> list = new LinkedList<byte[]>();

        @Override
        public void run(){
            try{
                while(true){
                    // 防止堆溢出，达到阈值直接清除
                    if(list.size()*512/1024/1024>=999){
                        list.clear();
                        System.out.println("list is clear");
                    }
                    byte[] b1;
                    // 不断往堆里面创建数组对象
                    for(int i=0;i<100;i++){
                        b1 = new byte[512];
                        list.add(b1);
                    }
                    Thread.sleep(1);
                }
            }catch (Exception e){

            }
        }
    }
}
