package card.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * action模型
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class MyAction implements Serializable {

    private static final long serialVersionUID = -2935186368219056456L;

    /**
     * 标示give或dontGive
     */
    private String command;

    /**
     * 不发放的奖品列表
     */
    private Set<String> dontGive;

    /**
     * 发放的概率列表
     */
    private List<PrizeProbability> givePrizes;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Set<String> getDontGive() {
        return dontGive;
    }

    public void setDontGive(Set<String> dontGive) {
        this.dontGive = dontGive;
    }

    public List<PrizeProbability> getGivePrizes() {
        return givePrizes;
    }

    public void setGivePrizes(List<PrizeProbability> givePrizes) {
        this.givePrizes = givePrizes;
    }
}
