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
    }```