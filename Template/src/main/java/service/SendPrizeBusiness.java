package service;

/**
 * @author: guangxush
 * @create: 2021/10/30
 */
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
