package symbols;

import inter.Id;
import lexer.Token;

import java.util.Hashtable;

/**
 * @author: guangxush
 * @create: 2020/06/28
 */
public class Env {
    private Hashtable hashtable;
    protected Env prev;

    public Env(Env prev) {
        this.hashtable = new Hashtable();
        this.prev = prev;
    }

    public void put(Token w, Id i) {
        hashtable.put(w, i);
    }

    public Id get(Token w) {
        for (Env env = this; env != null; env = env.prev) {
            Id found = (Id) env.hashtable.get(w);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
