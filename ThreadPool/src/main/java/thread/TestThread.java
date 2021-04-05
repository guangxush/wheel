package thread;

/**
 * 继承Thread类
 *
 * @author: guangxush
 * @create: 2021/04/05
 */
public class TestThread extends Thread{

    @Override
    public void run(){
        System.out.println("Test extends Thread!");
    }
}
