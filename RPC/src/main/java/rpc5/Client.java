package rpc5;

import common.IUserService;
import common.User;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public class Client {
    public static void main(String[] args)throws Exception {
        // IUserService可以加入新的处理逻辑, User可以自由变动
        IUserService service = Stub.getStub();
        System.out.println((User)service.findUserById(123));
    }
}
