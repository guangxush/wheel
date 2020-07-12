package inter;

import symbols.Type;

/**
 * @author: guangxush
 * @create: 2020/07/12
 */
public class If extends Stmt {

    Expr expr;
    Stmt stmt;

    public If(Expr x, Stmt s) {
        expr = x;
        stmt = s;
        if (expr.type != Type.Bool) {
            expr.error("boolean required in if");
        }
    }

    @Override
    public void gen(int b, int a) {
        int label = newLabel(); // stmt的代码标号
        expr.jumping(0, a);
        //为真时控制流穿越，为假时转向a
        emitLabel(label);
        stmt.gen(label, a);
    }
}
