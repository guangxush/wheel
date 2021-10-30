### 模板方法实现

#### 定义模板接口

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
public class SendPrizeBusiness {

    /**
     * 使用模板方法实现：方便对错误信息统一管理，对日志统一处理
     *
     * @param userId
     * @param amount
     * @return
     */
    public ResponseResult sendPrize(String userId, Integer amount){
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

