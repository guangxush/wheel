package inter;

import symbols.Type;

/**
 * @author: guangxush
 * @create: 2020/07/12
 */
public class While extends Stmt {
    Expr expr;
    Stmt stmt;

    public While() {
        expr = null;
        stmt = null;
    }

    public void init(Expr x, Stmt s) {
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) {
            expr.error("boolean required in while");
        }
    }

    @Override
    public void gen(int b, int a) {
        after = a;
        // 保存符号a
        expr.jumping(0, a);
        // stmt标号
        int label = newLabel();
        emitLabel(label);
        stmt.gen(label, b);
        emit("goto L" + b);
    }
}
