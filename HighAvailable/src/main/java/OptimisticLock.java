import model.BudgetRecordDO;
import template.BudgetRecordMapper;

/**
 * 乐观锁使用
 *
 * @author: guangxush
 * @create: 2023/06/18
 */
public class OptimisticLock {


    /**
     * DAL服务
     */
    private BudgetRecordMapper budgetRecordMapper;

    /**
     * 根据UUID, 悲观事务锁推进状态
     *
     * @param uuid  唯一单号
     * @param count 需要累计的数量
     * @return 1更新成功, 0更新失败
     */
    public int insertOrUpdateCount(String uuid, int count) throws Exception {
        // 1. 查询是否存在, 不存在插入
        BudgetRecordDO budgetRecordDO = budgetRecordMapper.select(uuid);
        if (budgetRecordDO == null) {
            budgetRecordDO = new BudgetRecordDO(uuid, count);
            int result = budgetRecordMapper.insert(budgetRecordDO);
            // 如果UK插入被幂等, 重新执行计算
            if (result < 0 && budgetRecordDO.getMaxRetryCount().get() > 0) {
                // 重试count-1
                budgetRecordDO.getMaxRetryCount().getAndDecrement();
                insertOrUpdateCount(uuid, count);
            } else if (budgetRecordDO.getMaxRetryCount().get() < 0) {
                // 超过最大重试次数, 抛出异常
                throw new Exception("retry max error");
            } else {
                return 1;
            }
        }
        // 2. 存在直接更新
        budgetRecordDO.setVersion(budgetRecordDO.getVersion());
        budgetRecordDO.setCount(budgetRecordDO.getCount() + count);
        // update table_01 set count = "$count", version = version + 1 where version = "$version" and uuid = "$uuid"
        int result = budgetRecordMapper.update(budgetRecordDO);
        // 如果版本已经过期, 重新执行更新
        if (result < 0 && budgetRecordDO.getMaxRetryCount().get() > 0) {
            // 重试count-1
            budgetRecordDO.getMaxRetryCount().getAndDecrement();
            insertOrUpdateCount(uuid, count);
        } else if (budgetRecordDO.getMaxRetryCount().get() < 0) {
            // 超过最大重试次数, 抛出异常
            throw new Exception("retry max error");
        } else {
            return 1;
        }
        return 1;
    }
}
