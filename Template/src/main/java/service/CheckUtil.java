package service;

/**
 * 参数校验工具类
 *
 * @author: guangxush
 * @create: 2021/10/30
 */
public class CheckUtil {

    /**
     * 参数是否为空
     *
     * @param param
     * @param paramName
     */
    public static void checkParamNotBlank(String param, String paramName){
        if("".equals(param)){
            throw new CheckException(paramName + " is not allowed blank");
        }
    }
}
