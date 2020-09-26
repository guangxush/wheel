package demo2;

/**
 * @author: guangxush
 * @create: 2020/09/26
 */
public class UserController {
    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }
}
