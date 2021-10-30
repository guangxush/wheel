package service;

/**
 * 处理器模板
 *
 * @author: guangxush
 * @create: 2021/10/30
 */
public class ProcessTemplate {

    public interface ITemplateCallback<T>{
        /**
         * 参数校验
         */
        void checkParams();

        /**
         * 执行业务
         *
         * @return
         * @throws Exception
         */
        T doInvoke() throws Exception;

        /**
         * 失败处理
         *
         * @param errorCode
         * @param errorMsg
         * @return
         */
        T doFailed(String errorCode, String errorMsg);
    }

    /**
     * 执行具体业务
     *
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> T invoke(ITemplateCallback<T> callback){
        try{
            // 1. 前置校验
            callback.checkParams();
            // 2. 处理业务
            return callback.doInvoke();
        }catch (Exception e){
            // 记录失败日志
            return callback.doFailed("错误码", "日志error明细");
        }catch (Throwable e){
            // 记录日志
            return callback.doFailed("系统异常", "traceId" + "机器IP" + "错误明细");
        }
    }
}
