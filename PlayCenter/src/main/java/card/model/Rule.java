package card.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 规则模型
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public abstract class Rule implements Serializable {

    private static final long serialVersionUID = -3279638186967590174L;

    /**
     * 业务ID
     */
    private String bizId = "defaultId";

    /**
     * 规则ID
     */
    private String ruleId = "defaultRuleId";

    /**
     * 版本号
     */
    private String version = "1.0.0";

    /**
     * 优先级
     */
    private int priority = 1;

    /**
     * 描述信息
     */
    private String memo;

    /**
     * 扩展信息
     */
    private Map<String, Object> extInfo;

    /**
     * 获取规则唯一标示
     * @return
     */
    public String getRuleUUID(){
        return bizId + "_" + ruleId + "_" + version;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Map<String, Object> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }
}
