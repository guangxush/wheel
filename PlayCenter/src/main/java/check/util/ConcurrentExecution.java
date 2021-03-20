package check.util;

import check.CheckRouteCallable;
import check.model.ConcurrentExecutionResult;

import java.util.List;

/**
 * 并发执行工具类
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public interface ConcurrentExecution {

    /**
     * 批量并发执行
     *
     * @param tasks 任务
     * @param timeout 超时时间
     * @param interval 任务轮询次数
     * @param <T>
     * @return
     */
    <T> ConcurrentExecutionResult<T> execute(List<CheckRouteCallable<T>> tasks, long timeout, long interval);
}
