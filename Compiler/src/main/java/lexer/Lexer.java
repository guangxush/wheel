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

    public Token scan() throws Exception {
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
            do{
                v = 10*v+Character.digit(peek,10);
                readch();
            }while (Character.isDigit(peek));
            if(peek != '.'){
               // return new Num(v);
            }
        }
        return null;
    }
}
