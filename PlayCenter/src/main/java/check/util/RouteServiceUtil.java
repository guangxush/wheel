package check.util;

import check.BizCheckServiceInvoker;
import check.CheckRouteCallable;
import check.RouteConfig;
import check.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 理由服务工具类
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class RouteServiceUtil {

    /**
     * 构建反射请求
     *
     * @param routeInfoResultMap
     * @param routeConfig
     * @param bizCheckRequest
     * @return
     */
    public static List<BizCheckServiceInvoker> buildBizCheckServiceInvokers(Map<String, RouteInfoResult> routeInfoResultMap,
                                                                            RouteConfig routeConfig,
                                                                            BizCheckRequest bizCheckRequest) {
        List<BizCheckServiceInvoker> bizCheckServiceInvokers = new ArrayList<>();
        // 根据Map以及其他配置构造参数，这里省略...
        String checkSystem = routeInfoResultMap.get("test").getCheckSystem();
        // 省略构造过程
        BizCheckExecuteRequest bizCheckExecuteRequest = new BizCheckExecuteRequest();
        // 包含服务以及配置信息
        bizCheckServiceInvokers.add(new BizCheckServiceInvoker(CheckServiceCache.getByUuid(checkSystem),
                bizCheckExecuteRequest, routeConfig, checkSystem));
        return bizCheckServiceInvokers;
    }

    /**
     * 调用下游系统执行
     *
     * @param concurrentExecution
     * @param bizCheckServiceInvokers
     * @param routeConfig
     * @return
     */
    public static BizResult executeBizCheckers(ConcurrentExecution concurrentExecution,
                                               List<BizCheckServiceInvoker> bizCheckServiceInvokers,
                                               RouteConfig routeConfig) {
        // 配置获取
        List<CheckRouteCallable<BizResult>> checkRouteCallables = new ArrayList<>();
        checkRouteCallables.addAll(bizCheckServiceInvokers);
        concurrentExecution.execute(checkRouteCallables, 0, 1);
        // 异步检查
        return new BizResult();
    }

}
