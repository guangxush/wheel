package rpc2;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public class Client {
    public static void main(String[] args)throws Exception {
        Stub stub = new Stub();
        System.out.println(stub.findUserById(123));
    }
}
