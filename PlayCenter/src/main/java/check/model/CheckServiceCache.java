package check.model;

import check.CheckService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 业务系统实现的checker服务缓存
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public class CheckServiceCache {

    /**
     * 下游系统缓存
     */
    private static Map<String, CheckService> checkServiceMap = new ConcurrentHashMap<>();

    /**
     * 根据UUID获取缓存服务
     * @param uniqueId
     * @return
     */
    public static CheckService getByUuid(String uniqueId){
        return checkServiceMap.get(uniqueId);
    }

    /**
     * 添加服务
     * @param uniqueId
     * @param checkService
     */
    public static void add(String uniqueId, CheckService checkService){
        checkServiceMap.put(uniqueId, checkService);
    }


}
