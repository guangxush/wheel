package inter;

import lexer.Lexer;

/**
 * @author: guangxush
 * @create: 2020/06/28
 */
public class Node {
    int lexLine = 0;

    public Node() {
        this.lexLine = Lexer.line;
    }

    void error(String s) {
        throw new Error("near line" + lexLine + ": " + s);
    }

    static int labels = 0;

    public int newLabel() {
        return ++labels;
    }

    public void emitLabel(int i) {
        System.out.println("L" + i + ":");
    }

    public void emit(String s) {
        System.out.println("\t" + s);
    }
}
