package lexer;


import symbols.Type;

import java.io.IOException;
import java.util.Hashtable;

/**
 * @author: guangxush
 * @create: 2020/06/21
 */
public class Lexer {
    public static int line = 1;
    char peek = ' ';
    Hashtable words = new Hashtable();

    void reserve(Word w) {
        words.put(w.lexeme, w);
    }

    public Lexer() {
        reserve(new Word("if", Tag.IF));
        reserve(new Word("else", Tag.ELSE));
        reserve(new Word("while", Tag.WHILE));
        reserve(new Word("do", Tag.DO));
        reserve(new Word("break", Tag.BREAK));
        reserve(Word.True);
        reserve(Word.False);
        reserve(Type.Int);
        reserve(Word.False);
    }

    void readch() throws IOException {
        peek = (char) System.in.read();
    }

    boolean readch(char c) throws IOException {
        readch();
        if (peek != c) {
            return false;
        }
        peek = ' ';
        return true;
    }
}
