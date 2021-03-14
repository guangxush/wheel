package team;

import org.springframework.stereotype.Service;
import team.model.ServiceResult;
import team.model.TeamModel;
import team.model.TeamOperationRequest;

/**
 * 模拟战队操作DB方法
 *
 * @author: guangxush
 * @create: 2021/03/14
 */
@Service
public class TeamOperationRepository {

    /**
     * 查询战队信息
     *
     * @param id
     * @return
     */
    TeamModel queryTeamInfoById(String id) {
        return new TeamModel(id);
    }

    /**
     * 更新战队信息
     *
     * @param request
     * @return
     */
    ServiceResult<Void> updateTeamInfo(TeamOperationRequest request) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        try{
            // 不允许修改其他字段，将其他字段设置为NULL, 底层MyBatis会自动保留原来的值
            request.setExtInfo(null);
            // 直接调用本地update方法
            TeamModel teamModel = this.updateTeamModel(request);
            if(teamModel == null){
                serviceResult.setErrorCode("MODIFY_EXCEPTION");
            }else{
                serviceResult.setSuccess(true);
            }
        }catch (Exception e){
            serviceResult.setNeedRetry(true);
            serviceResult.setErrorCode("MODIFY_EXCEPTION");
            throw new RuntimeException("DB异常请重试");
        }
        return serviceResult;
    }

    /**
     * 更新战队信息
     * @param request
     * @return
     */
    TeamModel updateTeamModel(TeamOperationRequest request){
        return new TeamModel(request.getId());
    }
}
