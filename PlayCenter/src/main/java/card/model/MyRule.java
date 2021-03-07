package card.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 运行规则
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class MyRule extends Rule{

    private static final long serialVersionUID = -9016253915585257886L;

    /**
     * 条件
     */
    private List<RelationRule> relationRules = new ArrayList<>();

    /**
     * 命令
     */
    private MyAction myAction;

    public MyRule() {
    }

    public MyRule(List<RelationRule> relationRules, MyAction myAction) {
        this.relationRules = relationRules;
        this.myAction = myAction;
    }

    public List<RelationRule> getRelationRules() {
        return relationRules;
    }

    public void setRelationRules(List<RelationRule> relationRules) {
        this.relationRules = relationRules;
    }

    public MyAction getMyAction() {
        return myAction;
    }

    public void setMyAction(MyAction myAction) {
        this.myAction = myAction;
    }
}
