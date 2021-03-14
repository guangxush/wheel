package team;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.model.ServiceResult;
import team.model.TeamModel;
import team.model.TeamOperationRequest;

import java.util.function.Function;

/**
 * @author: guangxush
 * @create: 2021/03/14
 */
@Service
public class TeamOperationServiceImpl implements TeamOperationService{

    /**
     * 日志句柄
     */
    private static final Logger logger = LoggerFactory.getLogger(OptimisticLockUpdateTemplate.class);

    /**
     * 战队信息操作服务
     */
    @Autowired
    private TeamOperationRepository teamOperationRepository;

    /**
     * 模板方法乐观锁更新
     *  @param teamModel
     * @param updateFunc
     * @return
     */
    @Override
    public TeamModel updateTeamInfo(TeamModel teamModel, Function<TeamModel, TeamModel> updateFunc) {
        OptimisticLockUpdateTemplate.tryUpdate(new AbstractOptimisticLockUpdate<TeamModel>() {

            @Override
            public TeamModel query() {
                return teamOperationRepository.queryTeamInfoById(teamModel.getId());
            }

            @Override
            public TeamModel changeData(TeamModel oldModel) {
                return updateFunc.apply(oldModel);
            }

            @Override
            public void update(TeamModel model) {
                TeamOperationRequest teamOperationRequest = buildTeamOperationRequest(teamModel);
                TeamOperationServiceImpl.this.updateTeamInfo(teamOperationRequest);
            }
        });
        return teamModel;
    }

    /**
     * 构建战队信息请求
     *
     * @param teamModel
     * @return
     */
    private TeamOperationRequest buildTeamOperationRequest(TeamModel teamModel){
        TeamOperationRequest teamOperationRequest = new TeamOperationRequest();
        teamOperationRequest.setId(teamModel.getId());
        teamOperationRequest.setName(teamModel.getName());
        // 设置为最新的UID列表
        teamOperationRequest.setUidList(teamModel.getUidList().toString());
        // 乐观锁更新时间
        teamOperationRequest.setGmtModify(teamModel.getGmtModify());
        return teamOperationRequest;
    }

    /**
     * 战队信息更新
     *
     * @param teamOperationRequest
     */
    @Override
    public void updateTeamInfo(TeamOperationRequest teamOperationRequest){
        ServiceResult<Void> result = teamOperationRepository.updateTeamInfo(teamOperationRequest);
        if(result.isSuccess()){
            optimisticLockHandle(result, teamOperationRequest, "MODIFY_EXCEPTION");
            if(result.isNeedRetry())
            {
                throw new RuntimeException("可重试异常");
            }else{
                throw new RuntimeException("不可重试异常");
            }
        }
    }

    private <T> void optimisticLockHandle(ServiceResult<T> result, TeamOperationRequest request, String errorCode){
        if(StringUtils.equals("MODIFY_EXCEPTION", errorCode)){
            logger.error(request.toString(), request.toString(), errorCode);
            throw new RuntimeException(result.getErrorMsg());
        }
    }
}
