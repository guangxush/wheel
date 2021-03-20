package check;

/**
 * 路由配置
 *
 * @author: guangxush
 * @create: 2021/03/20
 */
public interface RouteConfig {

    /**
     * 获取抽样率
     *
     * @return
     */
    Integer getGlobalSampleRate();

    /**
     * 是否同步执行
     *
     * @return
     */
    boolean isSyncCheck();
}
