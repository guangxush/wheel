package service;

import java.io.Serializable;

/**
 * 基础操作类返回结果
 *
 * @author: guangxush
 * @create: 2021/10/30
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 4854591777926146555L;

    private boolean success = true;

    private Object object;

    private String errorCode;

    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
