package inter;

import lexer.Tag;
import lexer.Word;
import symbols.Type;

/**
 * @author: guangxush
 * @create: 2020/07/12
 */
public class Access extends Op {
    public Id array;
    public Expr index;

    public Access(Id array, Expr expr, Type type) {
        super(new Word("[]", Tag.INDEX), type);
        this.array = array;
        this.index = expr;
    }

    @Override
    public Expr gen() {
        return new Access(array, index.reduce(), type);
    }

    @Override
    public void jumping(int t, int f) {
        emitJumps(reduce().toString(), t, f);
    }

    @Override
    public String toString() {
        return array.toString() + "[" + index.toString() + "]";
    }
}
