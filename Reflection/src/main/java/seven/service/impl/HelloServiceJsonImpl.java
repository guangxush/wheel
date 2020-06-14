package seven.service.impl;

import seven.*;
import seven.service.HelloService;

/**
 * @author: guangxush
 * @create: 2020/06/07
 */
public class HelloServiceJsonImpl implements HelloService {

    /**
     * 会打印自定义日志进行
     * @param name
     * @return
     */
    @LogAnnotation(bizName = "helloJson服务", logType = LogTypeEnum.SERVICE_LOG, customerLogType = JsonLogInfo.class)
    @Override
    public String hello(String name) {
        return "json " + name;
    }
}
