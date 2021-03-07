package card.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有请求基类
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class BaseRequest implements Serializable {
    private static final long serialVersionUID = 2078724289379619808L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 扩展字段
     */
    private Map<String, String> extInfo = new HashMap<>();

    /**
     * 活动配置信息
     */
    private ActivityModel activityModel;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }

    public ActivityModel getActivityModel() {
        return activityModel;
    }

    public void setActivityModel(ActivityModel activityModel) {
        this.activityModel = activityModel;
    }
}
