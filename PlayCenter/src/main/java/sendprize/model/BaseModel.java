package sendprize.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 基类
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class BaseModel implements Serializable {

    /**
     * 序列化ToString方法
     *
     * @return
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
