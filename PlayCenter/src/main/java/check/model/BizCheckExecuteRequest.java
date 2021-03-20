package check.model;

import java.io.Serializable;

/**
 * 需要校验的数据
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class BizCheckExecuteRequest implements Serializable {

    private static final long serialVersionUID = 3525416002118852603L;

    /**
     * 业务事件
     */
    private BizEvent bizEvent;

    /**
     * 路由信息
     */
    private RouteInfoResult routeInfoResult;

    public BizEvent getBizEvent() {
        return bizEvent;
    }

    public void setBizEvent(BizEvent bizEvent) {
        this.bizEvent = bizEvent;
    }

    public RouteInfoResult getRouteInfoResult() {
        return routeInfoResult;
    }

    public void setRouteInfoResult(RouteInfoResult routeInfoResult) {
        this.routeInfoResult = routeInfoResult;
    }
}
