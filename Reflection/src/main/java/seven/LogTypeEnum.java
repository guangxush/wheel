package seven;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public enum LogTypeEnum {
    /**服务日志 */
    SERVICE_LOG("服务日志", "SERVICE_LOG"),
    /**web日志 */
    CONTROLLER_LOG("web日志", "CONTROLLER_LOG");

    private String message;
    private String logName;

    private LogTypeEnum(final String message, final String logName){
        this.message = message;
        this.logName = logName;
    }

    public Log getLogger(){
        if(null != logName){
            return LogFactory.getLog(logName);
        }
        return null;
    }

    public String getMessage(){
        return message;
    }
}
