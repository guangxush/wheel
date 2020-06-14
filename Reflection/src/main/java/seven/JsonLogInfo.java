package seven;

import java.util.Date;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class JsonLogInfo extends LogInfo{
    private static final long serialVersionUID = -209779367166L;

    @Override
    public String printLog(){
        final StringBuilder sb = new StringBuilder();
        sb.append("JSON Log-----");
        sb.append(new Date());
        sb.append("-----JSON Log");
        return sb.toString();
    }
}
