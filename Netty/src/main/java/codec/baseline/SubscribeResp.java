package codec.baseline;

import java.io.Serializable;

/**
 * @author: guangxush
 * @create: 2020/02/14
 */
public class SubscribeResp implements Serializable {

    /**
     * 默认的序列号ID
     */
    private static final long serialVersionUID = 1L;


    private int subReqID;

    private int respCode;

    private String desc;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getSubReqID() {
        return subReqID;
    }

    public void setSubReqID(int subReqID) {
        this.subReqID = subReqID;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SubscribeResp{" +
                "subReqID=" + subReqID +
                ", respCode=" + respCode +
                ", desc='" + desc + '\'' +
                '}';
    }
}
