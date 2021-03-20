package check;

import check.model.BizCheckExecuteRequest;
import check.model.BizResult;

/**
 * 核对服务
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public interface CheckService {

    /**
     * 下游系统实现当前逻辑，反射的方法执行
     *
     * @param bizCheckExecuteRequest 免疫系统内部请求
     * @return 核对执行结果
     */
    BizResult checkEventInfo(BizCheckExecuteRequest bizCheckExecuteRequest);
}
