package four;

/**
 * @author: guangxush
 * @create: 2020/06/06
 */
public class ServiceTwo extends BaseService{

    @Override
    String run(String something) {
        return "ServiceTwo "+ something;
    }
}
