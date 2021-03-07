package card;

import card.model.BaseRequest;
import card.model.BaseResult;

/**
 * @author: guangxush
 * @create: 2021/03/07
 */
public interface Processor<Q extends BaseRequest, R extends BaseResult> {

    /**
     * 前置检查
     *
     * @param request 入参
     * @param result 结果
     */
    void preCheck(Q request, R result);

    /**
     * 执行处理器
     * @param request
     * @param result
     * @throws Exception
     */
    void execute(Q request, R result)throws Exception;

    /**
     * 获取处理器名称
     * @return
     */
    String getName();
}
