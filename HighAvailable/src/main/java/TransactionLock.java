import model.BudgetRecordDO;
import template.BudgetRecordMapper;
import template.TransactionTemplate;

/**
 * 悲观事务锁
 *
 * @author: guangxush
 * @create: 2023/06/18
 */
public class TransactionLock {

    /**
     * 数据库事务
     */
    private TransactionTemplate transactionTemplate;

    /**
     * DAL服务
     */
    private BudgetRecordMapper budgetRecordMapper;

    /**
     * 根据UUID, 悲观事务锁推进状态
     *
     * @param uuid
     * @return
     */
    public int updateStatusWithLock(String uuid) {
        transactionTemplate.execute(() -> {
            // 1. 锁查询 select *** where uuid = '' for update
            BudgetRecordDO budgetRecordDO = budgetRecordMapper.selectForUpdate(uuid);
            // 2. 判断状态
            if (budgetRecordDO == null || "FINISHED".equals(budgetRecordDO.getStatus())) {
                // logs
                return 0;
            }
            budgetRecordDO.setStatus("FINISHED");
            // 3. 更新
            return budgetRecordMapper.update(budgetRecordDO);
        });
        return 0;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
