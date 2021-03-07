package sendprize.model;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 流程节点
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class Node extends BaseModel{

    /**
     * 节点编号
     */
    private String code;

    /**
     * 节点名
     */
    private String name;

    /**
     * 是否结束节点，默认false
     */
    private boolean endNode;

    /**
     * 是否异步，默认false
     */
    private boolean async;

    /**
     * 超时时间，秒
     */
    private int timeout;

    /**
     * 组件ID，关联beanId
     */
    private String componentId;

    /**
     * 配置ID
     */
    private String configId;

    /**
     * next节点列表
     */
    private List<Next> nextList = new ArrayList<Next>();

    /**
     * join节点列表
     */
    private List<Join> joinList = new ArrayList<Join>();

    /**
     * before节点列表
     */
    private List<String> before = new ArrayList<String>();

    public boolean isHandleException(){
        if(nextList != null){
            for(Next next: nextList){
                if(StringUtils.equals(next.getResult(), "failure")){
                    return true;
                }
            }
        }
        return false;
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

    public boolean isEndNode() {
        return endNode;
    }

    public void setEndNode(boolean endNode) {
        this.endNode = endNode;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public List<Next> getNextList() {
        return nextList;
    }

    public void setNextList(List<Next> nextList) {
        this.nextList = nextList;
    }

    public List<Join> getJoinList() {
        return joinList;
    }

    public void setJoinList(List<Join> joinList) {
        this.joinList = joinList;
    }

    public List<String> getBefore() {
        return before;
    }

    public void setBefore(List<String> before) {
        this.before = before;
    }
}
