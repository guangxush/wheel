package client;

/**
 * @author: guangxush
 * @create: 2021/10/30
 */
public class BaseQueryResult<T>  {

    /**
     * 是否成功
     */
    protected  boolean success = true;

    /**
     * 结果
     */
    protected T result;

    /**
     * 错误信息
     */
    protected  String errorCode;

    /**
     * 消息
     */
    protected String message;

    public BaseQueryResult(T result) {
        this.result = result;
        this.success = true;
    }

    public BaseQueryResult(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.success = false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
