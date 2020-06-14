package five;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class Children implements Parents {

    @Override
    public String hello(String name) {
        return "I'm " + name;
    }
}
