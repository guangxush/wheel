package base;

import java.util.Date;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
public class SuppressWarningTest {

    @SuppressWarnings(value={"deprecation"})
    public static void doSomething(){
        Date date = new Date(120, 8, 26);
        System.out.println(date);
    }

    public static void main(String[] args) {
        doSomething();
    }
}
