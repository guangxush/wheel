## 工厂模式处理器

### 定义一个处理器接口

```java
public abstract class BaseProcessor {

    /**
     * 支持的处理类型
     *
     * @return
     */
    public abstract SupportEnum supportProcessType();

    /**
     * 处理器实现类
     *
     * @param param
     */
    public abstract void process(String param);
}
```

### 给出具体的实现

```java
@Component
public class TestOneProcessor extends BaseProcessor{
    /**
     * 支持的处理类型
     *
     * @return
     */
    @Override
    public SupportEnum supportProcessType() {
        return SupportEnum.TEST_ONE;
    }

    /**
     * 处理器实现类
     *
     * @param param
     */
    @Override
    public void process(String param) {
        System.out.println(param);
    }
}
```

### 工厂类

```java
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
```

### 调用工厂实现具体方法

```java
public class BusinessService {

    /**
     * 处理器
     *
     * @param params
     * @param supportEnum
     */
    public void doExecute(String params, SupportEnum supportEnum){
        BaseProcessor processor = ProcessorFactory.getByType(supportEnum);
        if(processor != null){
            processor.process(params);
        }
    }
}
```



