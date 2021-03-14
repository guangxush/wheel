package team.model;

import java.io.Serializable;

/**
 * 服务操作结果
 *
 * @author: guangxush
 * @create: 2021/03/14
 */
public class ServiceResult<T> implements Serializable {

    private static final long serialVersionUID = 7981495326589917836L;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 是否可以重试
     */
    private boolean needRetry;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 返回结果
     */
    private T resultData;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isNeedRetry() {
        return needRetry;
    }

    public void setNeedRetry(boolean needRetry) {
        this.needRetry = needRetry;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
