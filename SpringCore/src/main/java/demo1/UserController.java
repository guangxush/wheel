package demo1;

/**
 * @author: guangxush
 * @create: 2020/09/25
 */
public class UserController {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
