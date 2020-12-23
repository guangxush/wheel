package ex4;

import java.util.LinkedList;
import java.util.List;

/**
 * VM参数：-XX:+PrintGCDetails
 *
 * @author: guangxush
 * @create: 2020/12/22
 */
public class TestStopTheWorld {
    public static void main(String[] args) {
        // 填充对象线程+打印线程同时进行
        FillListThread thread = new FillListThread(); // 造成FullGc, STW
        TimeThread timeThread = new TimeThread();// 时间打印线程
        thread.start();
        timeThread.start();// FullGc会让时间线程变得不规律
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
                    if(list.size()*512/1024/1024>=999){
                        list.clear();
                        System.out.println("list is clear");
                    }
                    byte[] b1;
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

    /**
     * 每隔100ms定时打印
     */
    public static class TimeThread extends Thread{
        public final static long startTime = System.currentTimeMillis();
        @Override
        public void run(){
            try{
                while(true){
                    long t = System.currentTimeMillis();
                    System.out.println(t/1000+"."+t%1000);
                    Thread.sleep(100);
                }
            }catch (Exception e){

            }
        }
    }
}
