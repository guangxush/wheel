package rpc6;

import common.IProductService;
import common.IUserService;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public class Client {
    public static void main(String[] args)throws Exception {
        IUserService service = (IUserService) Stub.getStub(IUserService.class);
        System.out.println(service.findUserById(123));
    }
}
