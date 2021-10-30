package service;

import java.util.List;

/**
 * 通用返回结果定义
 *
 * @author: guangxush
 * @create: 2021/10/30
 */
public class ResponseResult<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 返回结果
     */
    private T result;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 返回结果
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> returnResult(boolean result){
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setSuccess(result);
        return responseResult;
    }

    /**
     * 构建返回失败
     *
     * @param errorCode
     * @param errorMsg
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> fail(String errorCode, String errorMsg){
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setSuccess(false);
        responseResult.setErrorCode(errorCode);
        responseResult.setMessage(errorMsg);
        return responseResult;
    }

    /**
     * 构造返回结果
     */
    public static <T, V> ResponseResult<List<T>> returnResult(BaseResult baseResult){
        if(baseResult.isSuccess()){
            return returnResult(true);
        }
        return fail(baseResult.getErrorCode(), baseResult.getMessage());
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
