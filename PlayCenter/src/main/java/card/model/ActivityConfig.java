package card.model;

/**
 * 活动配置
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class ActivityConfig {

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 运行环境
     */
    private String env;

    /**
     * 规则配置
     */
    private ActivityRule activityRule = new MyActivityRule();

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public ActivityRule getActivityRule() {
        return activityRule;
    }

    public void setActivityRule(ActivityRule activityRule) {
        this.activityRule = activityRule;
    }
}
