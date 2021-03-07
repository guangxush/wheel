package sendprize.model;

/**
 * 聚合节点描述
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class Join {
    /**
     * 聚合执行结果
     */
    private String result;

    /**
     * 聚合条件表达式
     */
    private String expression;

    /**
     * 聚合节点代码
     */
    private String joinNode;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getJoinNode() {
        return joinNode;
    }

    public void setJoinNode(String joinNode) {
        this.joinNode = joinNode;
    }
}
