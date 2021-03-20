package check;

import java.util.concurrent.Callable;

/**
 * 路由系统定制化
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public abstract class CheckRouteCallable<V> implements Callable<V> {

    /**
     * 执行回掉函数
     *
     * @return
     * @throws Exception
     */
    @Override
    public V call() throws Exception {
        return exec();
    }

    /**
     * 执行具体内容
     *
     * @return
     */
    public abstract V exec();
}
