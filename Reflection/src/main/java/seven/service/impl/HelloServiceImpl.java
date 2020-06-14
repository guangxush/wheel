package seven.service.impl;

import seven.LogAnnotation;
import seven.LogTypeEnum;
import seven.service.HelloService;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class HelloServiceImpl implements HelloService {

    @LogAnnotation(bizName = "hello服务", logType = LogTypeEnum.SERVICE_LOG)
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
