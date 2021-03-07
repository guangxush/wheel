package card.model;

import java.util.Map;

/**
 * 转换规则
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class TransferRule {
    /**
     * 转换来源资产规则
     */
    private Map<String, Integer> formConfig;

    /**
     * 转换规则
     */
    private String transRule;

    /**
     * 来源类型
     */
    private String fromType;

    /**
     * 转换类型
     */
    private String toType;

    /**
     * 是否支持自动转换
     */
    private boolean isAutoSupport;

    /**
     * 复制
     * @return
     */
    public TransferRule copy(){
        TransferRule transferRule = new TransferRule();
        transferRule.setFromType(transRule);
        transferRule.setToType(toType);
        transferRule.setTransRule(transRule);
        return transferRule;
    }

    public String getTransRule() {
        return transRule;
    }

    public void setTransRule(String transRule) {
        this.transRule = transRule;
    }

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
    }

    public Map<String, Integer> getFormConfig() {
        return formConfig;
    }

    public void setFormConfig(Map<String, Integer> formConfig) {
        this.formConfig = formConfig;
    }

    public String getToType() {
        return toType;
    }

    public void setToType(String toType) {
        this.toType = toType;
    }

    public boolean isAutoSupport() {
        return isAutoSupport;
    }

    public void setAutoSupport(boolean autoSupport) {
        isAutoSupport = autoSupport;
    }
}
