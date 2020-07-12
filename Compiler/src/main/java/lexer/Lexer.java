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

    public Token scan() throws IOException {
        for (; ; readch()) {
            if (peek == ' ' || peek == '\t') {
                continue;
            } else if (peek == '\n') {
                line = line + 1;
            }else{
                break;
            }
        }
        switch (peek){
            case '&':
                if(readch('&')){
                    return Word.and;
                }else{
                    return new Token('&');
                }
            case '|':
                if(readch('|')){
                    return Word.or;
                }else{
                    return new Token('|');
                }
            case '=':
                if(readch('=')){
                    return Word.eq;
                }else{
                    return new Token('=');
                }
            case '!':
                if(readch('=')){
                    return Word.ne;
                }else{
                    return new Token('!');
                }
            case '<':
                if(readch('=')){
                    return Word.le;
                }else{
                    return new Token('<');
                }
            case '>':
                if(readch('=')){
                    return Word.ge;
                }else{
                    return new Token('=');
                }
        }
        if(Character.isDigit(peek)){
            int v = 0;
            do {
                v = 10 * v + Character.digit(peek, 10);
                readch();
            } while (Character.isDigit(peek));
            if (peek != '.') {
                // return new Num(v);
            }
            float x = v;
            float d = 10;
            for (; ; ) {
                readch();
                if (!Character.isDigit(peek)) {
                    break;
                }
                x = x + Character.digit(peek, 10) / d;
                d = d * 10;
            }
            return new Real(x);
        }
        if (Character.isLetter(peek)) {
            StringBuffer sb = new StringBuffer();
            do {
                sb.append(peek);
                readch();
            } while (Character.isLetterOrDigit(peek));
            String s = sb.toString();
            Word w = (Word) words.get(s);
            if (w != null) {
                return w;
            }
            w = new Word(s, Tag.ID);
            words.put(s, w);
            return w;
        }
        Token token = new Token(peek);
        peek = ' ';
        return token;
    }
}
