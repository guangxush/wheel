package three;


import javax.jnlp.BasicService;

/**
 * @author: guangxush
 * @create: 2020/06/06
 */
public class ServiceOne extends BaseService {

    @Override
    String run(String something) {
        return "ServiceOne "+ something;
    }
}
