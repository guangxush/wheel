package check;

import check.model.BizCheckRequest;
import check.model.BizResult;

/**
 * 路由引擎实现类
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class RouteEngineImpl implements RouteEngine {

    // 注入路由check
    private CheckRoute checkRoute;

    // 注入配置信息
    private RouteConfig routeConfig;

    /**
     * 路由分发执行引擎
     *
     * @param bizCheckRequest
     * @return
     */
    @Override
    public BizResult bizRoute(BizCheckRequest bizCheckRequest) {
        return bizRoute(bizCheckRequest, routeConfig);
    }

    /**
     * 路由执行
     *
     * @param bizCheckRequest
     * @param routeConfig
     * @return
     */
    private BizResult bizRoute(BizCheckRequest bizCheckRequest, RouteConfig routeConfig) {
        // 参数检查以及config检查判断是否需要继续路由

        // 调用具体的check执行
        BizResult bizResult = checkRoute.doBizCheck(bizCheckRequest, routeConfig);
        return bizResult;
    }
}
