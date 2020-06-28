package inter;

import lexer.Token;
import symbols.Type;

/**
 * @author: guangxush
 * @create: 2020/06/28
 */
public class Id extends Expr {
    // 相对地址
    public int offset;

    public Id(Token op, Type type) {
        super(op, type);
    }
}
