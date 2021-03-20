package check;

import check.model.BizCheckRequest;
import check.model.BizResult;

/**
 * 路由引擎
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public interface RouteEngine {

    /**
     * 路由分发执行引擎
     *
     * @param bizCheckRequest
     * @return
     */
    BizResult bizRoute(BizCheckRequest bizCheckRequest);
}
