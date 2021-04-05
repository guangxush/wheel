package thread;

import java.util.concurrent.Callable;

/**
 * @author: guangxush
 * @create: 2021/04/05
 */
public class TestCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "Test implements Callable<T>!";
    }
}
