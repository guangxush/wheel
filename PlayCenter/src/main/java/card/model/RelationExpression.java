package card.model;

import java.io.Serializable;

/**
 * 条件表达式
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class RelationExpression implements Serializable {

    private static final long serialVersionUID = 5967198872011133823L;

    /**
     * key
     */
    private String key;

    /**
     * 操作符
     */
    private String expressionOp;

    /**
     * 值
     */
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExpressionOp() {
        return expressionOp;
    }

    public void setExpressionOp(String expressionOp) {
        this.expressionOp = expressionOp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
