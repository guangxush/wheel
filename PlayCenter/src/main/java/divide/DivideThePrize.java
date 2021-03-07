package divide;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 抢红包算法
 *
 * @author: guangxush
 * @create: 2020/11/06
 */
public class DivideThePrize {
    public static void main(String[] args) {
        List<Integer> amountList = divideRedPackage(5000, 30);
        for (Integer amount : amountList) {
            System.out.println("抢到金额：" + new BigDecimal(amount).divide(new BigDecimal(100)));
        }
        System.out.println("--------------------------");
        RedPackage redPackage = new RedPackage(50, 30);
        for (int i = 0; i < 30; i++) {
            System.out.println("抢到金额：" + getRandomMoney(redPackage));
        }
        System.out.println("--------------------------");
        double[] simpleDivide = simpleDivide(5000, 30);
        for (int i = 0; i < 30; i++) {
            System.out.println("抢到金额：" + simpleDivide[i]);
        }
    }

    /**
     * 发红包算法，金额参数以分为单位
     * 剩余红包金额为M，剩余人数为N，那么有如下公式：
     * 每次抢到的金额 = 随机区间 （0， M / N X 2）
     *
     * @param totalAmount
     * @param totalPeopleNum
     * @return
     */
    public static List<Integer> divideRedPackage(Integer totalAmount, Integer totalPeopleNum) {
        List<Integer> amountList = new ArrayList<Integer>();
        Integer restAmount = totalAmount;
        Integer restPeopleNum = totalPeopleNum;
        Random random = new Random();
        for (int i = 0; i < totalPeopleNum - 1; i++) {
            //随机范围：[1，剩余人均金额的两倍)，左闭右开
            int amount = random.nextInt(restAmount / restPeopleNum * 2 - 1) + 1;
            restAmount -= amount;
            restPeopleNum--;
            amountList.add(amount);
        }
        amountList.add(restAmount);
        return amountList;
    }

    /**
     * 用对象进行封装，与上面的方法类似
     * @param redPackage
     * @return
     */
    public static double getRandomMoney(RedPackage redPackage) {
        if (redPackage.getRemainSize() == 1) {
            return (double) Math.round(redPackage.getRemainMoney() * 100) / 100;
        }
        Random random = new Random();
        double min = 0.01;
        double max = redPackage.getRemainMoney() / redPackage.getRemainSize() * 2;
        double money = random.nextDouble() * max;
        money = money < min ? 0.01 : money;
        money = Math.floor(money * 100) / 100;
        redPackage.setRemainSize(redPackage.getRemainSize() - 1);
        redPackage.setRemainMoney(redPackage.getRemainMoney() - money);
        return money;
    }

    public static double[] simpleDivide(Integer totalAmount, Integer totalPeopleNum){
        double[] divideArray = new double[totalPeopleNum];
        int sumOfRandom = 0;
        for(int i=0;i<totalPeopleNum;i++){
            int random=(int)(Math.random()*10);
            divideArray[i] = random;
            sumOfRandom += random;
        }

        for(int i=0;i<totalPeopleNum;i++){
            divideArray[i] = divideArray[i]/sumOfRandom*totalAmount;
        }
        return divideArray;
    }
}

class RedPackage {
    /**
     * 剩余的红包数量
     */
    private int remainSize;
    /**
     * 剩余的钱
     */
    private double remainMoney;

    public RedPackage(double remainMoney, int remainSize) {
        this.remainMoney = remainMoney;
        this.remainSize = remainSize;
    }

    public int getRemainSize() {
        return remainSize;
    }

    public void setRemainSize(int remainSize) {
        this.remainSize = remainSize;
    }

    public double getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(double remainMoney) {
        this.remainMoney = remainMoney;
    }
}