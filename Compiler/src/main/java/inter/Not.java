package inter;

import lexer.Token;

/**
 * @author: guangxush
 * @create: 2020/07/12
 */
public class Not extends Logical {
    public Not(Token token, Expr expr2) {
        super(token, expr2, expr2);
    }

    @Override
    public void jumping(int t, int f) {
        expr2.jumping(f, t);
    }

    @Override
    public String toString() {
        return op.toString() + " " + expr2.toString();
    }
}
