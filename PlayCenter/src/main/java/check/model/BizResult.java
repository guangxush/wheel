package check.model;

/**
 * 业务结果
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class BizResult {

    /**
     * 请求返回结果
     */
    private boolean success = true;

    /**
     * 返回结果详情
     */
    private String msg = "";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
