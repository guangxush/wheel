package service;

/**
 * @author: guangxush
 * @create: 2020/07/04
 */
public interface MyService {
    /**
     * 添加注解后的服务
     */
    String printLogger();

    /**
     * 不添加注解的服务
     */
    String printLoggerUnAnnotation();
}
