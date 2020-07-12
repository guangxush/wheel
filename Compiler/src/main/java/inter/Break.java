package inter;

/**
 * @author: guangxush
 * @create: 2020/07/12
 */
public class Break extends Stmt {
    Stmt stmt;

    public Break() {
        if (Stmt.Enclosing == Stmt.Null) {
            error("unenclosed break");
        }
        stmt = Stmt.Enclosing;
    }

    @Override
    public void gen(int b, int a) {
        emit("goto L" + stmt.after);
    }
}
