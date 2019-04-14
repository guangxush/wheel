import java.util.Map;

/**
 * @author: guangxush
 * @create: 2019/04/07
 */
public interface JSON {
    /**
     * Object -> String
     * @param objects
     * @return
     */
    String stringfy(Object[] objects);

    /**
     * String -> Map<String, Object>
     * @param str
     * @return
     */
    Map<String, Object> parse(String str);
}
