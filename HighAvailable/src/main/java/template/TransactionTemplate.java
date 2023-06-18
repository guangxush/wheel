package template;

/**
 * 模拟数据库事务
 *
 * @author: guangxush
 * @create: 2023/06/18
 */
public class TransactionTemplate<T> {

    /**
     * 模拟数据库事务
     * @param action
     * @param <T>
     * @return
     */
    public <T> T execute(TransactionCallback<T> action){
        return null;
    }
}
