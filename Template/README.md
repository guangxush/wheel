## 模板方法实现

### 调用内部接口的模板方法

#### 定义模板接口 (外部调用核心服务时使用)

```java
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
```

#### 实现模板编排

```java
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
```

#### 实际模板调用

```java
import service.BaseResult;
import service.CheckUtil;
import service.ProcessTemplate;
import service.ResponseResult;

public class SendPrizeBusiness {

    /**
     * 使用模板方法实现：方便对错误信息统一管理，对日志统一处理
     *
     * @param userId
     * @param amount
     * @return
     */
    public ResponseResult sendPrize(String userId, Integer amount) {
        return ProcessTemplate.invoke(new ProcessTemplate.ITemplateCallback<ResponseResult>() {
            @Override
            public void checkParams() {
                CheckUtil.checkParamNotBlank(userId, "userId");
            }

            @Override
            public ResponseResult doInvoke() throws Exception {
                // 业务逻辑返回领域模型结果
                BaseResult baseResult = new BaseResult();
                return ResponseResult.returnResult(baseResult);
            }

            @Override
            public ResponseResult doFailed(String errorCode, String errorMsg) {
                return ResponseResult.fail(errorCode, errorMsg);
            }
        });
    }
}
```

### 调用外部接口的模板方法

#### 定义模板方法

```java 
public class BudgetCoreBusiness {

    /**
     * 活动预算扣减
     *
     * @param campId
     * @param amount
     * @return
     */
    public BaseQueryResult<Boolean> budgetCore(String campId, int amount){
        String errorCode = "BD0001";
        String message = "budgetCore";
        return ClientTemplate.execute(errorCode, message, new ClientTemplate.Callback<Boolean, Boolean>() {

            /**
             * 执行业务
             *
             * @return
             * @throws Exception
             */
            @Override
            public Boolean doExecute() throws Exception {
                // 调用外部接口
                Boolean result = false;
                return result;
            }

            /**
             * 是否成功
             *
             * @param result
             * @return
             */
            @Override
            public boolean isSuccess(Boolean result) {
                return result;
            }

            /**
             * 处理成功
             *
             * @param result
             * @return
             */
            @Override
            public BaseQueryResult<Boolean> doSuccess(Boolean result) {
                return new BaseQueryResult<Boolean>(result);
            }

            /**
             * 处理失败
             *
             * @param errorCode
             * @param errorMsg
             * @return
             */
            @Override
            public BaseQueryResult<Boolean> doFailed(String errorCode, String errorMsg) {
                return new BaseQueryResult<Boolean>(errorCode, errorMsg);
            }
        },0 ,0);
    }
}
```

#### 调用外部接口

```java 
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
```