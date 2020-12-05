package fsm;

/**
 * @author: guangxush
 * @create: 2020/12/05
 */
public class Main {
    public static FirstStatus firstStatus = new FirstStatus();
    public static SecondStatus secondStatus = new SecondStatus();
    public static ThirdStatus thirdStatus = new ThirdStatus();
    public static FourthStatus fourthStatus = new FourthStatus();
    public static FifthStatus fifthStatus = new FifthStatus();

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("#");
        String first = firstStatus.operator(sb);
        String second = secondStatus.operator(new StringBuffer(first));
        String third = thirdStatus.operator(new StringBuffer(second));
        String fourth = fourthStatus.operator(new StringBuffer(third));
        String fifth = fifthStatus.operator(new StringBuffer(fourth));
        String end = fifth + "#";
        System.out.println(end);
    }
}
