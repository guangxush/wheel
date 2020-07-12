import lexer.Lexer;
import parser.Parser;

import java.io.IOException;

/**
 * @author: guangxush
 * @create: 2020/06/21
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer();
        Parser parser = new Parser(lexer);
        parser.program();
        System.out.println("");
    }

//    {
//        int i;
//        int j;
//        float v;
//        float x;
//        float[100] a;
//        while (true) {
//            do i = i + 1; while (a[i] < v);
//            do j = j - 1; while (a[j] > v);
//            if (i >= j) break;
//            x = a[i];
//            a[i] = a[j];
//            a[j] = x;
//        }
//    }

}
