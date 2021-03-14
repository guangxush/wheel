package team;

import team.model.TeamModel;
import team.model.TeamOperationRequest;

import java.util.function.Function;

/**
 * 队伍操作服务
 *
 * @author: guangxush
 * @create: 2021/03/14
 */
public interface TeamOperationService {

    /**
     * 乐观锁更新信息
     *  @param teamModel
     * @param updateFunc
     * @return
     */
    TeamModel updateTeamInfo(TeamModel teamModel, Function<TeamModel, TeamModel> updateFunc);

    /**
     * 乐观锁更新基本方法
     *
     * @param teamOperationRequest
     */
    void updateTeamInfo(TeamOperationRequest teamOperationRequest);
}
