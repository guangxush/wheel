package common;

/**
 * @author: guangxush
 * @create: 2020/05/03
 */
public interface IUserService {
    /**
     * query user info
     * @param id
     * @return
     */
    User findUserById(Integer id);
}
