package inter;

import lexer.Token;
import lexer.Word;
import symbols.Type;

/**
 * @author: guangxush
 * @create: 2020/06/28
 */
public class Id extends Expr {
    // 相对地址
    public int offset;

    public Id(Word id, Type type, int b) {
        super(id, type);
        this.offset = b;
    }
}
