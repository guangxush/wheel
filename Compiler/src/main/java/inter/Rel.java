package inter;

import lexer.Token;
import symbols.Array;
import symbols.Type;

/**
 * 实现了 < <= == != >= >
 * @author: guangxush
 * @create: 2020/07/12
 */
public class Rel extends Logical {
    public Rel(Token token, Expr expr1, Expr expr2) {
        super(token, expr1, expr2);
    }

    @Override
    public Type check(Type p1, Type p2) {
        if (p1 instanceof Array || p2 instanceof Array) {
            return null;
        } else if (p1 == p2) {
            return Type.Bool;
        } else {
            return null;
        }
    }

    @Override
    public void jumping(int t, int f) {
        Expr a = expr1.reduce();
        Expr b = expr1.reduce();
        String test = a.toString() + " " + op.toString() + " " + b.toString();
        emitJumps(test, t, f);
    }
}
