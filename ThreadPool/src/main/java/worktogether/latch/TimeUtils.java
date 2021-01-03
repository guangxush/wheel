package worktogether.latch;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: guangxush
 * @create: 2021/01/03
 */
public class TimeUtils {
    public static String getTimes() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        String formatStr = formatter.format(new Date());
        return "[" + formatStr + "]";
    }
}
