package card.model;

/**
 * 发奖概率模型
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class PrizeProbability {

    /**
     * 奖品类型
     */
    private String prizeType;

    /**
     * 概率值
     */
    private int probability;

    /**
     * 活动ID
     */
    private String activityId;

    /**
     * 奖品数量上限
     */
    private Integer userUpperLimit;

    public PrizeProbability(String prizeType, int probability) {
        this.prizeType = prizeType;
        this.probability = probability;
    }

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Integer getUserUpperLimit() {
        return userUpperLimit;
    }

    public void setUserUpperLimit(Integer userUpperLimit) {
        this.userUpperLimit = userUpperLimit;
    }
}
