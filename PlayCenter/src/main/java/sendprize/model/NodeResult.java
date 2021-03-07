package sendprize.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 节点结果
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class NodeResult {
    /**
     * 节点结果
     */
    private String result;

    /**
     * 节点异常
     */
    private Throwable throwable;

    /**
     * 运行次数
     */
    private AtomicInteger runCount;

    public NodeResult(String result, Throwable throwable) {
        this.result = result;
        this.throwable = throwable;
        this.runCount = new AtomicInteger(0);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public AtomicInteger getRunCount() {
        return runCount;
    }

    public void setRunCount(AtomicInteger runCount) {
        this.runCount = runCount;
    }
}
