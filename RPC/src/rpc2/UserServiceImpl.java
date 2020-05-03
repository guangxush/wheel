package rpc2;

import common.IUserService;
import common.User;

/**
 * 放在服务端的一个方法
 * @author: guangxush
 * @create: 2020/05/03
 */
public class UserServiceImpl implements IUserService {
    @Override
    public User findUserById(Integer id) {
        return new User(id, "Sandy");
    }
}
