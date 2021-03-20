package check.model;

import java.io.Serializable;

/**
 * 基础参数请求
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public abstract class BaseRequest implements Serializable {

    private static final long serialVersionUID = 7331363702859736409L;

    /**
     * 业务ID
     */
    private String bizNo;

    /**
     * 路由
     */
    private String routeScene;

    /**
     * 业务场景
     */
    private BusinessScene businessScene = new BusinessScene();


    public String getBizNo() {
        return bizNo;
    }

    public void setBizNo(String bizNo) {
        this.bizNo = bizNo;
    }

    public String getRouteScene() {
        return routeScene;
    }

    public void setRouteScene(String routeScene) {
        this.routeScene = routeScene;
    }

    public BusinessScene getBusinessScene() {
        return businessScene;
    }

    public void setBusinessScene(BusinessScene businessScene) {
        this.businessScene = businessScene;
    }
}
