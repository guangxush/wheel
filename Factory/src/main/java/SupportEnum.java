/**
 * 支持的枚举处理类型
 *
 * @author: guangxush
 * @create: 2021/10/30
 */
public enum SupportEnum {

    TEST_ONE("TEST_ONE", "test1"),

    TEST_TWO("TEST_TWO", "test2");

    /**
     * code
     */
    String code;

    /**
     * desc
     */
    String desc;

    SupportEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(java.lang.String desc) {
        this.desc = desc;
    }
}
