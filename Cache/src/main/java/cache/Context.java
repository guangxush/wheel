package cache;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author: guangxush
 * @create: 2020/05/24
 */
public class Context {
    /** key为ID，Object为对象 */
    private Map<String, Object> map = Maps.newHashMap();

    /**
     * 存放用户值对象
     * @param uid
     * @param object
     */
    public void putObject(String uid, Object object){
        map.put(uid, object);
    }

    /**
     * 获取用户对象
     * @param uid
     * @return
     */
    public Object getObject(String uid){
        return map.get(uid);
    }
}
