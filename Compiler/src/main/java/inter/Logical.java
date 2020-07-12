package inter;

import lexer.Token;
import symbols.Type;

/**
 * @author: guangxush
 * @create: 2020/06/30
 */
public class Logical extends Expr {
    public Expr expr1, expr2;

    Logical(Token token, Expr expr1, Expr expr2) {
        super(token, null);
        this.expr1 = expr1;
        this.expr2 = expr2;
        type = check(expr1.type, expr2.type);
        if (type == null) {
            error("type error");
        }
    }

    public Type check(Type p1, Type p2) {
        if (p1 == Type.Bool && p2 == Type.Bool) {
            return Type.Bool;
        }
        return null;
    }

    @Override
    public Expr gen() {
        int f = newLabel();
        int a = newLabel();
        Temp temp = new Temp(type);
        this.jumping(0, f);
        emit(temp.toString() + " = true");
        emit("goto L" + a);
        emitLabel(f);
        emit(temp.toString() + " = false");
        emitLabel(a);
        return temp;
    }

    @Override
    public String toString() {
        return expr1.toString() + " " + op.toString() + " " + expr2.toString();
    }
}
