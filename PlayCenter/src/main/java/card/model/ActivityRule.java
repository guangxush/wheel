package card.model;

import java.util.List;
import java.util.Map;

/**
 * 活动规则
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public interface ActivityRule {

    /**
     * 获取活动ID
     * @return
     */
    String getActivityId();

    /**
     * 获取抓换规则
     * @return
     */
    Map<String, TransferRule> getTransferRuleMap();

    /**
     * 获取调控规则
     * @return
     */
    List<Rule> getRuleEngines();
}
