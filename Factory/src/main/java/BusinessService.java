/**
 * @author: guangxush
 * @create: 2021/10/30
 */
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
