package inter;

/**
 * @author: guangxush
 * @create: 2020/07/12
 */
public class Stmt extends Node {
    public Stmt() {

    }

    public static Stmt Null = new Stmt();

    public void gen(int b, int a) {
        // 参数是语句的开始标号和语句的下一条指令的标号
    }
    // 保存语句的下一条指令的标号
    int after = 0;
    // 用于break
    public static Stmt Enclosing = Stmt.Null;
}
