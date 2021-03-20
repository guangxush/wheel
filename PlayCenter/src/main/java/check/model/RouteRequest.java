package check.model;

import java.io.Serializable;

/**
 * 路由请求
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class RouteRequest implements Serializable {

    private static final long serialVersionUID = 5557887980261200428L;

    /**
     * 类型
     */
    private String type;

    /**
     * 消息信息
     */
    private String message;

    /**
     * 请求ID
     */
    private String requestId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
