package card.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 当前规则配置
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class MyActivityRule implements ActivityRule{

    /**
     * 活动标示
     */
    public static final String ACTIVITY_ID = "MY_ACTIVITY";

    /**
     * 规则引擎配置
     */
    private List<Rule> ruleEngines;

    /**
     * 转换规则静态配置
     */
    private Map<String, TransferRule> transferRuleMap;


    @Override
    public String getActivityId() {
        return ACTIVITY_ID;
    }

    /**
     * 构造函数
     */
    public MyActivityRule() {
        initRuleEngineConfig();
        initTransferRule();
    }

    @Override
    public Map<String, TransferRule> getTransferRuleMap() {
        return null;
    }

    @Override
    public List<Rule> getRuleEngines() {
        return null;
    }


    private void initRuleEngineConfig(){
        List<Rule> rules = new ArrayList<>();
        String bizId = "TEST";

        MyRule myRule = new MyRule();
        myRule.setBizId(bizId);
        myRule.setMemo("test");
        myRule.setPriority(1);
        myRule.setRuleId("TEST_1_0");
        rules.add(myRule);

        this.ruleEngines = rules;
    }

    private void initTransferRule(){
        TransferRule transferRule = new TransferRule();
        transferRule.setFromType("TEST1");
        transferRule.setToType("TEST2");
        transferRule.setTransRule("DIRECT");
        transferRuleMap.put("TEST_1_2", transferRule);
    }


}
