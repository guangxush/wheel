package team;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 乐观锁更新操作模板
 *
 * @author: guangxush
 * @create: 2021/03/14
 */
public class OptimisticLockUpdateTemplate {

    /**
     * 日志句柄
     */
    private static final Logger logger = LoggerFactory.getLogger(OptimisticLockUpdateTemplate.class);

    /**
     * 乐观锁更新模板方法
     *
     * @param operation 操作模板
     * @param <M>       模型对象
     * @return
     */
    public static <M> M tryUpdate(AbstractOptimisticLockUpdate<M> operation) {
        M model = operation.query();
        model = operation.changeData(model);
        operation.update(model);
        return model;
    }
}
