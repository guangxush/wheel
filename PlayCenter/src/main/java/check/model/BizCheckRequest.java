package check.model;

import java.io.Serializable;

/**
 * 业务校验请求
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class BizCheckRequest implements Serializable {

    private static final long serialVersionUID = -2166764327626543151L;

    /**
     * 事件信息
     */
    private BizEvent bizEvent;

    /**
     * 路由请求
     */
    private RouteRequest routeRequest;

    /**
     * 请求标示
     */
    private String requestId;

    public BizEvent getBizEvent() {
        return bizEvent;
    }

    public void setBizEvent(BizEvent bizEvent) {
        this.bizEvent = bizEvent;
    }

    public RouteRequest getRouteRequest() {
        return routeRequest;
    }

    public void setRouteRequest(RouteRequest routeRequest) {
        this.routeRequest = routeRequest;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
