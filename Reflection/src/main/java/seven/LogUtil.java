package seven;

import org.apache.commons.logging.Log;

import static seven.LogConstant.C_PREFIX;
import static seven.LogConstant.C_SUFFIX;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class LogUtil {

    public static void info(final Log log, final String format, final Object... args){
        if(log.isInfoEnabled()){
            log.info(LogStringUtil.format(format, args));
        }
    }

    public static void error(final Log log, final Throwable e, final Object... args){
        log.error(getLogString(args), e);
    }

    public static String getLogString(final Object... objects){
        if(objects == null){
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for(final Object o: objects){
            sb.append(C_PREFIX);
            sb.append(o);
            sb.append(C_SUFFIX);
        }
        return sb.toString();
    }
}
