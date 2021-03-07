package sendprize.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 流程模版
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class ProcessTemplate {
    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 开始节点
     */
    private String startNodeCode;

    /**
     * 是否异步
     */
    private boolean async;

    /**
     * 节点列表
     */
    private List<Node> nodes = new ArrayList<Node>();

    /**
     * 节点映射列表
     */
    private transient Map<String, Node> nodesMaps = new ConcurrentHashMap<String, Node>();

    /**
     * 根据节点名获取当前节点
     *
     * @param nodeCode
     * @return
     */
    public Node getNode(String nodeCode){
        return this.nodesMaps.get(nodeCode);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartNodeCode() {
        return startNodeCode;
    }

    public void setStartNodeCode(String startNodeCode) {
        this.startNodeCode = startNodeCode;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Map<String, Node> getNodesMaps() {
        return nodesMaps;
    }

    public void setNodesMaps(Map<String, Node> nodesMaps) {
        this.nodesMaps = nodesMaps;
    }
}
