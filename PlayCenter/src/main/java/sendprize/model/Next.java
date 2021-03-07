package sendprize.model;

/**
 * 后续节点描述
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class Next extends BaseModel{
    /**
     * 后续执行结果
     */
    private String result;

    /**
     * 后续条件表达式
     */
    private String expression;

    /**
     * 后续节点代码
     */
    private String nextNode;

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

    public String getNextNode() {
        return nextNode;
    }

    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }
}
