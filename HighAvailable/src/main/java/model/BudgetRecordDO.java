package model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 商品预算流水
 * @author: guangxush
 * @create: 2023/06/18
 */
public class BudgetRecordDO {

    /**
     * 数据库唯一ID
     */
    private String uuid;

    /**
     * 乐观锁版本
     */
    private int version;

    /**
     * 状态
     */
    private String status;

    /**
     * 库存数量
     */
    private long count;

    /**
     * 最大重试次数
     */
    private AtomicLong maxRetryCount = new AtomicLong(5);

    public BudgetRecordDO(String uuid, long count) {
        this.uuid = uuid;
        this.count = count;
    }

    public BudgetRecordDO() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AtomicLong getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(AtomicLong maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }
}
