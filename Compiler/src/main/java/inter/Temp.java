package inter;

import lexer.Word;
import symbols.Type;

/**
 * @author: guangxush
 * @create: 2020/06/28
 */
public class Temp extends Expr {
    static int count = 0;
    int number = 0;

    public Temp(Type type) {
        super(Word.temp, type);
        number = ++count;
    }

    @Override
    public String toString() {
        return "t" + number;
    }
}
