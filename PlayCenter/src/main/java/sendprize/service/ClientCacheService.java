package sendprize.service;


import sendprize.model.ServiceNode;

/**
 * 客户端服务缓存
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public interface ClientCacheService {

    /**
     * 获取服务节点
     * @param componentId
     * @return
     */
    ServiceNode getServiceNode(String componentId);
}
