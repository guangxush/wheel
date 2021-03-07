package sendprize.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池管理器
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public final class ThreadPoolManager {

    /**
     * 日志句柄
     */
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolManager.class);

    /**
     * 默认拒绝策略
     */
    private static final RejectedExecutionHandler ABORT_POLICY = new ThreadPoolExecutor.AbortPolicy();

    /**
     * 系统核心线程池
     */
    private static final Map<String, ThreadPoolExecutor> THREAD_POOLS = new ConcurrentHashMap<String, ThreadPoolExecutor>();

    static{
        init();

        // 执行调度任务的线程池
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("SchedulerTask").build();
        ScheduledExecutorService scheduledExecutor = new ScheduledThreadPoolExecutor(1, threadFactory);
        scheduledExecutor.scheduleAtFixedRate(()->{
            for(Map.Entry<String, ThreadPoolExecutor> executor: THREAD_POOLS.entrySet()){
                logger.info("ThreadPoolName: {}, MaximumPoolSize: {}, PoolSize：{}, ActiveCount:{}, Capacity:{}, Queue:{}",
                        executor.getKey(),
                        executor.getValue().getMaximumPoolSize(),
                        executor.getValue().getPoolSize(),
                        executor.getValue().getActiveCount(),
                        executor.getValue().getQueue().remainingCapacity(),
                        executor.getValue().getQueue()
                        );
            }
        }, 10, 20000L, TimeUnit.MICROSECONDS);
    }

    /**
     * 初始化线程池
     */
    private static void init(){
        /**
         * 默认线程执行器
         */
        ThreadPoolExecutor threadPoolExecutor = applyThreadPool("defaultPoolExecutor", 10, 100, 800, ABORT_POLICY);
        THREAD_POOLS.put("defaultPoolExecutor", threadPoolExecutor);

        /**
         * 流程引擎线程执行器
         */
        ThreadPoolExecutor processEngineExecutor = applyThreadPool("processEngineExecutor", 10, 100, 800, ABORT_POLICY);
        THREAD_POOLS.put("processEngineExecutor", threadPoolExecutor);

    }

    /**
     * 申请线程池资源
     * @param name 线程池名称
     * @param coreSize 核心线程数
     * @param maxSize 最大线程数
     * @param queueSize 队列长度
     * @param handler 拒绝策略
     * @return
     */
    private static ThreadPoolExecutor applyThreadPool(String name, int coreSize, int maxSize, int queueSize, RejectedExecutionHandler handler){
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("线程池名字不能为空");
        }
        purge();

        String validName = name.trim();
        ThreadPoolExecutor threadPool = THREAD_POOLS.get(validName);
        if(null == threadPool){
            if(0==queueSize){
                THREAD_POOLS.put(validName, new ThreadPoolExecutor(coreSize, maxSize, 60, TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>(),Executors.defaultThreadFactory() , handler));
            }else{
                THREAD_POOLS.put(validName, new ThreadPoolExecutor(coreSize, maxSize, 60, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(queueSize), Executors.defaultThreadFactory() , handler));
            }
        }
        threadPool = THREAD_POOLS.get(validName);
        return threadPool;
    }

    /**
     * 移除所有被shutdown的线程池
     */
    private static void purge(){
        synchronized (THREAD_POOLS){
            for(String poolName: THREAD_POOLS.keySet()){
                ThreadPoolExecutor pool = THREAD_POOLS.get(poolName);
                if(pool.isShutdown()){
                    THREAD_POOLS.remove(poolName);
                }
            }
        }
    }

    public static ThreadPoolExecutor getThreadPool(String threadName){
        if(StringUtils.isBlank(threadName) || !THREAD_POOLS.containsKey(threadName)){
            return THREAD_POOLS.get("defaultPoolExecutor");
        }else{
            return THREAD_POOLS.get(threadName);
        }
    }
}
