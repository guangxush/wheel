package service;

/**
 * 异常检测
 *
 * @author: guangxush
 * @create: 2021/10/30
 */
public class CheckException extends RuntimeException{

    private String errorMsg;

    public CheckException(){
        super();
    }

    public CheckException(String errorMsg){
        super(errorMsg);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
