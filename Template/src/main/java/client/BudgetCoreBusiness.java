package client;

/**
 * 预算扣减
 *
 * @author: guangxush
 * @create: 2021/10/30
 */
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
