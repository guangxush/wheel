package team.model;

import java.util.Date;
import java.util.List;

/**
 * 队伍信息
 *
 * @author: guangxush
 * @create: 2021/03/14
 */
public class TeamModel {
    /**
     * 战队ID
     */
    private String id;

    /**
     * 战队名
     */
    private String name;

    /**
     * 队伍成员信息列表
     */
    private List<String> uidList;

    /**
     * 最后一次修改时间
     */
    private Date gmtModify;

    /**
     * 扩展字段
     */
    private String extInfo;

    public TeamModel(String id) {
        this.id = id;
    }

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

    public List<String> getUidList() {
        return uidList;
    }

    public void setUidList(List<String> uidList) {
        this.uidList = uidList;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(String extInfo) {
        this.extInfo = extInfo;
    }
}
