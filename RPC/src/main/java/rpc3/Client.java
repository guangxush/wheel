package rpc3;

import common.IUserService;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public class Client {
    public static void main(String[] args)throws Exception {
        IUserService service = Stub.getStub();
        System.out.println(service.findUserById(123));
    }
}
