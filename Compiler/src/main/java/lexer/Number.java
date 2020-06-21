package lexer;

/**
 * @author: guangxush
 * @create: 2020/06/21
 */
public class Number extends Token{
    public final int value;

    public Number(int tag, int value) {
        super(tag);
        this.value = value;
    }

    @Override
    public String toString() {
        return "Number{" +
                "value=" + value +
                '}';
    }
}
