package template;

/**
 * 事务回掉
 * @author: guangxush
 * @create: 2023/06/18
 */
public interface TransactionCallback<T> {

    T doInTransaction();
}
