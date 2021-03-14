package team.model;

import java.util.Date;

/**
 * 战队操作请求
 *
 * @author: guangxush
 * @create: 2021/03/14
 */
public class TeamOperationRequest {
    /**
     * 队伍ID
     */
    private String id;
    /**
     * 队伍名
     */
    private String name;

    /**
     * 队伍信息
     */
    private String uidList;

    /**
     * 队伍更新时间
     */
    private Date gmtModify;

    /**
     * 扩展字段
     */
    private String extInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getUidList() {
        return uidList;
    }

    public void setUidList(String uidList) {
        this.uidList = uidList;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
