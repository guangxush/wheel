package client;

import service.BaseResult;

/**
 * 调用外部接口时使用
 *
 * @author: guangxush
 * @create: 2021/10/30
 */
public class ClientTemplate {

    public interface Callback<T, K>{

        /**
         * 执行业务
         *
         * @return
         * @throws Exception
         */
        T doExecute() throws Exception;

        /**
         * 是否成功
         *
         * @param result
         * @return
         */
        boolean isSuccess(T result);

        /**
         * 处理成功
         * @param result
         * @return
         */
        BaseQueryResult<K> doSuccess(T result);

        /**
         * 处理失败
         *
         * @param errorCode
         * @param errorMsg
         * @return
         */
        BaseQueryResult<K> doFailed(String errorCode, String errorMsg);
    }

    /**
     * 执行默认不重试
     *
     * @param errorCode
     * @param message
     * @param callback
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> BaseQueryResult<K> execute(String errorCode, String message, ClientTemplate.Callback<T, K> callback){
        return execute(errorCode, message, callback);
    }


    /**
     * 执行，而且重试
     * @param errorCode
     * @param message
     * @param callback
     * @param current
     * @param retry
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T, K> BaseQueryResult<K> execute(String errorCode, String message, ClientTemplate.Callback<T, K> callback, int current, int retry){
        try{
            T result = callback.doExecute();

            if(result == null){
                return doFailed(callback, errorCode, message);
            }

            if(!callback.isSuccess(result)){
                if(retry <= current){
                    return doFailed(callback, errorCode, message);
                }else{
                    return execute(errorCode, message, callback, ++current, retry);
                }
            }

            return callback.doSuccess(result);
        }catch (Exception e){
            // 打印错误日志
            if(retry <= current){
                return doFailed(callback, errorCode, message);
            }else{
                return execute(errorCode, message, callback, ++current, retry);
            }
        }
    }


    /**
     * 失败处理
     *
     * @param callback
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static BaseQueryResult doFailed(Callback callback, String errorCode, String errorMsg){
        return callback.doFailed(errorCode, errorMsg);
    }
}
