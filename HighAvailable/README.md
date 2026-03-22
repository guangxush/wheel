# HighAvailable

> 这是 wheel/HighAvailable 模块的说明。
> 已保留原 README 内容，并添加快速导航与贡献说明。

## 模块亮点

- 适合快速上手，覆盖核心功能。
- 原始实现保留在文末。
- 单测可运行：`cd /workspaces/wheel/HighAvailable && mvn test`。

## 快速开始

```bash
cd /workspaces/wheel/HighAvailable
mvn test
```

## 贡献建议

- 可先阅读如下“原始 README 内容”。
- 需要功能补充时，建议先开 issue 讨论。
- 提交 PR 时请保持代码风格。

---

## 原始 README 内容（保留）

## 批量保存数据

1. 先把数据保存在本地队列
2. 定时把队列中的数据插入到DB(减少数据库连接次数和写入压力)

```java
    /**
     * 守护线程定时执行消费
     */
    private class BatchProcessBigDataThread implements Runnable{
        
        @Override
        public void run() {
            List<BigDataModel> bigDataModelList = new ArrayList<>(numBatch);
            while(true){
                try{
                    process(bigDataModelList);
                }catch (Throwable exception){
                    // logs
                }
            }
        }

        /**
         * 批量数据写入
         * @param bigDataModelList
         */
        private void process(List<BigDataModel> bigDataModelList){
            long waitTime = maxWaitTime;
            for(int i = 0; i < numBatch ; i++){
                BigDataModel bigDataModel = null;
                long start = System.currentTimeMillis();
                try{
                    // 获取数据
                    bigDataModel = bigDataModels.poll(waitTime, TimeUnit.MICROSECONDS);
                }catch (InterruptedException exception){
                    // logs
                }finally {
                    long cost = System.currentTimeMillis() - start;
                    waitTime -= cost;
                }
                if(bigDataModel != null){
                    bigDataModelList.add(bigDataModel);
                }
                // 等待时间<最小等待时间, 结束poll
                if(waitTime < singleWaitTime){
                    break;
                }
            }
            if(bigDataModelList.size() > 0){
                // 批量写入
                batchSaveModels(bigDataModelList);
                // 清空队列
                bigDataModelList.clear();
            }
        }
    }
```


## 乐观锁更新库存

```java
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
```


## 悲观锁更新状态


```java
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
```

---
