package check.model;

/**
 * 路由信息结果
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class RouteInfoResult {

    /**
     * 路由场景
     */
    private String routeSceneId;

    /**
     * 执行系统
     */
    private String checkSystem;

    /**
     * 采样率
     */
    private int sampleRate = 0;

    /**
     * 延迟执行时间
     */
    private int delayTime = 0;

    public String getRouteSceneId() {
        return routeSceneId;
    }

    public void setRouteSceneId(String routeSceneId) {
        this.routeSceneId = routeSceneId;
    }

    public String getCheckSystem() {
        return checkSystem;
    }

    public void setCheckSystem(String checkSystem) {
        this.checkSystem = checkSystem;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }
}
