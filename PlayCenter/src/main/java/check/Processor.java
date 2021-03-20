package check;

import check.model.BizCheckRequest;

/**
 * 处理器入口
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class Processor {

    /**
     * 注入当前引擎
     */
    private RouteEngine routeEngine;

    /**
     * 根据外部请求执行check
     *
     * @param outRequest
     */
    public void processor(String outRequest){
        routeEngine.bizRoute(buildRequest(outRequest));
    }

    private BizCheckRequest buildRequest(String outRequest){
        // 省略构造方法
        return new BizCheckRequest();
    }
}
