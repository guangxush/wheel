package card;

import card.model.BaseRequest;
import card.model.BaseResult;

/**
 * 处理池接口
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public interface ProcessorPool {

    /**
     * 处理池接口
     * @param request 请求
     * @param result 处理结果
     * @param processName 处理器名称
     */
    void execute(BaseRequest request, BaseResult result, String processName);
}
