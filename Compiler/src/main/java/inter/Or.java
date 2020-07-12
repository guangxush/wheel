package inter;

import lexer.Token;

/**
 * @author: guangxush
 * @create: 2020/07/12
 */
public class Or extends Logical {
    public Or(Token token, Expr expr1, Expr expr2) {
        super(token, expr1, expr2);
    }

    @Override
    public void jumping(int t, int f) {
        int label = t != 0 ? t : newLabel();
        expr1.jumping(label, 0);
        expr2.jumping(t, f);
        if (t == 0) {
            emitLabel(label);
        }
    }
}
