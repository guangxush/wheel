package check;

import check.model.BizCheckRequest;
import check.model.BizResult;

/**
 * 校验路由
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public interface CheckRoute {

    /**
     * 业务检查
     *
     * @param bizCheckRequest
     * @param routeConfig
     * @return
     */
    BizResult doBizCheck(BizCheckRequest bizCheckRequest, RouteConfig routeConfig);
}
