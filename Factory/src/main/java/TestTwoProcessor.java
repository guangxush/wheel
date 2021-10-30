import org.springframework.stereotype.Component;

/**
 * @author: guangxush
 * @create: 2021/10/30
 */
@Component
public class TestTwoProcessor extends BaseProcessor{
    /**
     * 支持的处理类型
     *
     * @return
     */
    @Override
    public SupportEnum supportProcessType() {
        return SupportEnum.TEST_TWO;
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
