package demo;

/**
 * @author: guangxush
 * @create: 2019/05/18
 */
public class ServiceProducerImpl implements ServiceProducer{
    @Override
    public String sendData(String data) {
        return "I am service producer!!!, the data is "+ data;
    }
}
