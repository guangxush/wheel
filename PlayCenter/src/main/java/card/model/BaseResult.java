package card.model;

import java.io.Serializable;

/**
 * 所有返回结果基类
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class BaseResult implements Serializable {
    private static final long serialVersionUID = -26768247793951665L;

    /**
     * 是否成功
     */
    private boolean success = false;

    /**
     * 错误码
     */
    private String code = "test1";

    /**
     * 错误描述
     */
    private String desc = "";

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
