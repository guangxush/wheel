package sendprize.service;

import sendprize.model.ServiceNode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取服务客户端
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class ClientCacheServiceImpl implements ClientCacheService{

    private Map<String, ServiceNode> serviceNodeMap = new ConcurrentHashMap<>();

    @Override
    public ServiceNode getServiceNode(String componentId) {
        if(serviceNodeMap.containsKey(componentId)){
            return serviceNodeMap.get(componentId);
        }
        // 通过其他方式获取, 此处省略
        return new ServiceNode() {
            @Override
            public Class getNodeConfigType() {
                return null;
            }
        };
    }
}
