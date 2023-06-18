import model.BigDataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 大数据处理器
 *
 * @author: guangxush
 * @create: 2023/06/18
 */
public class BigDataBatchProcess {

    /**
     * 最大等待时间
     */
    private long maxWaitTime = 1000;

    /**
     * 最大队列长度
     */
    private int capacity = 10000;

    /**
     * 单次等待时间 ms
     */
    private int singleWaitTime = 20;

    /**
     * 批次写入的最大数量
     */
    private int numBatch = 500;

    private ArrayBlockingQueue<BigDataModel> bigDataModels = null;

    /**
     * 启动初始化方法
     */
    public void init(){
        this.bigDataModels = new ArrayBlockingQueue<>(capacity);
        Thread processBigData = new Thread(new BatchProcessBigDataThread(), "batch-process-big-data-thread");
        processBigData.setDaemon(true);
        processBigData.start();
    }


    /**
     * 调用入口, 单次保存数据
     * @param bigDataModel
     */
    public void putSingleData(BigDataModel bigDataModel){
        bigDataModels.add(bigDataModel);
    }


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

    /**
     * 销毁方法
     */
    public void shutDown(){
        try{
            List<BigDataModel> bigDataModelList = new ArrayList<>(numBatch);
            while(true){
                try{
                    BigDataModel bigDataModel = bigDataModels.poll();
                    if(bigDataModel != null){
                        bigDataModelList.add(bigDataModel);
                    }else{
                        break;
                    }
                }catch (Throwable exception){
                    // logs
                }
            }
            if(bigDataModelList.size() > 0){
                batchSaveModels(bigDataModelList);
            }
            bigDataModelList.clear();
        }catch (Exception exception){
            // logs
        }
    }


    /**
     * 批量保存模型
     *
     * @param bigDataModelList
     */
    public void batchSaveModels(List<BigDataModel> bigDataModelList){
        // 1. 转换成DO对象

        // 2. 批量保存 batchInsert hbase
        // insert into table_01 values("", "", ""),("", "", "",),("", "", "",);
    }

}
