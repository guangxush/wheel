package check.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 批量并发执行结果
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class ConcurrentExecutionResult<R> {

    /**
     * 并发执行结果状态 成功、超时、异常
     */
    private String status;

    /**
     * 结果汇总
     */
    private List<R> results = new ArrayList<>();

    /**
     * 所有任务
     */
    private List<Future<R>> futures = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<R> getResults() {
        return results;
    }

    public void setResults(List<R> results) {
        this.results = results;
    }

    public List<Future<R>> getFutures() {
        return futures;
    }

    public void setFutures(List<Future<R>> futures) {
        this.futures = futures;
    }
}
