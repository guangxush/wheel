package check.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 业务场景
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class BusinessScene implements Serializable {

    private static final long serialVersionUID = 3980327506134609014L;

    /**
     * 业务标示
     */
    private String bizProd;

    /**
     * 业务产品码
     */
    private String bizMode;

    /**
     * 业务标示字段
     */
    private Map<String, String> extInfo;

    public String getBizProd() {
        return bizProd;
    }

    public void setBizProd(String bizProd) {
        this.bizProd = bizProd;
    }

    public String getBizMode() {
        return bizMode;
    }

    public void setBizMode(String bizMode) {
        this.bizMode = bizMode;
    }

    public Map<String, String> getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }
}
