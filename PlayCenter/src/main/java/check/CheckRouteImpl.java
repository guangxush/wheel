package check;

import check.model.BizCheckRequest;
import check.model.BizResult;
import check.model.RouteInfoResult;
import check.util.ConcurrentExecution;
import check.util.RouteServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 校验路由实现
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class CheckRouteImpl implements CheckRoute{

    /**
     * 注入并发执行器皿
     */
    private ConcurrentExecution concurrentExecution;

    /**
     * 业务检查
     *
     * @param bizCheckRequest
     * @param routeConfig
     * @return
     */
    @Override
    public BizResult doBizCheck(BizCheckRequest bizCheckRequest, RouteConfig routeConfig) {
        // 从DB获取配置信息，暂时先new
        Map<String, RouteInfoResult> routeInfoResultMap = new HashMap<>();

        // 获取Invoker
        List<BizCheckServiceInvoker> bizCheckServiceInvokers =  RouteServiceUtil.buildBizCheckServiceInvokers(routeInfoResultMap,routeConfig, bizCheckRequest);

        // 并发执行
        return RouteServiceUtil.executeBizCheckers(concurrentExecution, bizCheckServiceInvokers, routeConfig);
    }
}
