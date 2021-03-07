package card.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 条件列表
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class RelationRule implements Serializable {
    private static final long serialVersionUID = 7434526786655260500L;

    /**
     * 条件列表
     */
    private List<RelationExpression> expressions = new ArrayList<>();

    /**
     * 原始规则表达式
     */
    private String originExpression;

    /**
     * 添加关系表达式
     *
     * @param expression
     * @return
     */
    public boolean add(RelationExpression expression){
        return expressions.add(expression);
    }

    public List<RelationExpression> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<RelationExpression> expressions) {
        this.expressions = expressions;
    }

    public String getOriginExpression() {
        return originExpression;
    }

    public void setOriginExpression(String originExpression) {
        this.originExpression = originExpression;
    }

    @Override
    public String toString() {
        return "RelationRule{" +
                "expressions=" + expressions +
                ", originExpression='" + originExpression + '\'' +
                '}';
    }
}
