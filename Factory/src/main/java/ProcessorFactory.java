import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: guangxush
 * @create: 2021/10/30
 */
@Component
public class ProcessorFactory implements ApplicationContextAware, InitializingBean{

    private static final Map<SupportEnum, BaseProcessor> processorMap = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    /**
     * 私有化构造函数，保证单例
     */
    private ProcessorFactory() {
    }

    /**
     * 按照类型获取相关的执行器
     *
     * @param supportEnum
     * @return
     */
    public static BaseProcessor getByType(SupportEnum supportEnum){
        if(supportEnum == null){
            return null;
        }
        BaseProcessor baseProcessor = processorMap.get(supportEnum);
        return baseProcessor;
    }

    /**
     * 启动之后将bean放入容器
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            processorMap.clear();
            List<BaseProcessor> processorList = new ArrayList<>();
            processorList.addAll(applicationContext.getBeansOfType(BaseProcessor.class).values());

            processorList.forEach(processor -> {
                processorMap.put(processor.supportProcessType(), processor);
            });
        }catch (Exception e){
            // 记录失败日志
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
