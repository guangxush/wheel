package check.util;

import check.CheckRouteCallable;
import check.model.ConcurrentExecutionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发执行实现类
 * @author: guangxush
 * @create: 2021/03/20
 */
public class ConcurrentExecutionImpl implements ConcurrentExecution{

    /**
     * 线程执行器
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 核心线程池大小
     */
    private String corePoolSize;

    /**
     * 最大线程池大小
     */
    private String maximumPoolSize;

    /**
     * 阻塞任务队列
     */
    private String queueCapacity;


    /**
     * 批量并发执行
     *
     * @param tasks    任务
     * @param timeout  超时时间
     * @param interval 任务轮询次数
     * @return
     */
    @Override
    public <T> ConcurrentExecutionResult<T> execute(List<CheckRouteCallable<T>> tasks, long timeout, long interval) {
        List<Future<T>> futures = new ArrayList<>();
        for(CheckRouteCallable<T> task: tasks){
            // 放入线程池执行任务
            futures.add(threadPoolExecutor.submit(task));
        }
        // 校验任务是否执行完成
        ConcurrentExecutionResult<T> concurrentExecutionResult = new ConcurrentExecutionResult<>();
        List<Future<T>> refFutures = new ArrayList<>();
        for(Future<T> future: futures){
            refFutures.add(future);
        }
        concurrentExecutionResult.setFutures(refFutures);

        long endTime = System.currentTimeMillis() + timeout;
        while(System.currentTimeMillis() < endTime && futures.size() > 0){
            List<Future<T>> finishedFutures = new ArrayList<>();
            for(Future<T> future:futures){
                if(future.isDone()){
                    try{
                        concurrentExecutionResult.getResults().add(future.get(1, TimeUnit.MILLISECONDS));
                        finishedFutures.add(future);
                    }catch (Throwable e){
                        // 设置异常
                        concurrentExecutionResult.setStatus("error");
                        finishedFutures.add(future);
                    }
                }
            }
            // 提出掉以及完成的线程
            for(Future<T> future: finishedFutures){
                futures.remove(future);
            }
        }
        // 还有未完成的表示超时
        if(futures.size() != 0){
            concurrentExecutionResult.setStatus("timeout");
        }
        return concurrentExecutionResult;
    }
}
