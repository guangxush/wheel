package team;

/**
 * 乐观锁操作模板抽象基类
 *
 * @author: guangxush
 * @create: 2021/03/14
 */
public abstract class AbstractOptimisticLockUpdate<M> {

    /**
     * 查询模型方法
     *
     * @return 查询结果
     */
    public abstract M query();

    /**
     * 模型数据变更
     *
     * @param oldModel 老模型
     * @return 变更后的对象
     */
    public abstract M changeData(M oldModel);

    /**
     * 更新模型
     *
     * @param model 新模型
     */
    public abstract void update(M model);
}
