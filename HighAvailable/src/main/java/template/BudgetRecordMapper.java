package template;

import model.BudgetRecordDO;

/**
 * @author: guangxush
 * @create: 2023/06/18
 */
public interface BudgetRecordMapper {

    /**
     * 行锁查询
     *
     * @param uuid 唯一ID
     * @return 查询结果
     */
    BudgetRecordDO selectForUpdate(String uuid);

    /**
     * 普通查询
     *
     * @param uuid 唯一ID
     * @return 查询结果
     */
    BudgetRecordDO select(String uuid);


    /**
     * 更新
     *
     * @param budgetRecordDO DO对象
     * @return >0 成功, -1 失败
     */
    int update(BudgetRecordDO budgetRecordDO);

    /**
     * 查询
     *
     * @param budgetRecordDO DO对象
     * @return >0 成功, -1 失败
     */
    int insert(BudgetRecordDO budgetRecordDO);
}
