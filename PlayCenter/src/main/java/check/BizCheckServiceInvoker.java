package check;

import check.model.BizCheckExecuteRequest;
import check.model.BizResult;

/**
 * @author: guangxush
 * @create: 2021/03/20
 */
public class BizCheckServiceInvoker extends CheckRouteCallable<BizResult> {

    /**
     * 下游核对服务
     */
    private CheckService checkService;

    /**
     * 核对请求参数
     */
    private BizCheckExecuteRequest bizCheckExecuteRequest;

    /**
     * 路由配置
     */
    private RouteConfig routeConfig;

    /**
     * 下游执行系统
     */
    private String system;

    public BizCheckServiceInvoker(CheckService checkService, BizCheckExecuteRequest bizCheckExecuteRequest, RouteConfig routeConfig, String system) {
        this.checkService = checkService;
        this.bizCheckExecuteRequest = bizCheckExecuteRequest;
        this.routeConfig = routeConfig;
        this.system = system;
    }

    /**
     * 执行具体内容
     *
     * @return
     */
    @Override
    public BizResult exec() {
        return checkService.checkEventInfo(bizCheckExecuteRequest);
    }
}
