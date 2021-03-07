package card;

import card.model.BaseRequest;
import card.model.BaseResult;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理器池子
 *
 * @author: guangxush
 * @create: 2021/03/07
 */
public class ProcessorPoolImpl implements ProcessorPool{

    /**
     * 处理器池子
     */
    private final Map<String, Processor> pool = new HashMap<String, Processor>();

    @Override
    public void execute(BaseRequest request, BaseResult result, String processName) {
        // 1. 获取处理器
        Processor processor = pool.get(processName);

        // 2. 执行处理器

        // 2.1 前置校验是否可以执行
        processor.preCheck(request, result);

        // 2.2 执行业务逻辑
        try {
            processor.execute(request, result);
        } catch (Exception e) {
            // 根据不同的情况打印执行日志
            e.printStackTrace();
        }
    }


}
