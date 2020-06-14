package seven;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class LogStringUtil {
    private static final Log LOGGER = LogFactory.getLog(LogStringUtil.class);

    public static final String SEPARATOR_COMMA = ",";

    public static String format(final String format, final Object... args) {
        if (null == format) {
            return "null";
        }
        try {
            return String.format(format, args);
        } catch (final Exception e) {
            LOGGER.warn("格式化错误：" + format + e.getMessage());
            return format + Arrays.toString(args);
        }
    }
}
