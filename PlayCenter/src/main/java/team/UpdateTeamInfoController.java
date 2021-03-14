package team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.model.TeamModel;

import java.util.ArrayList;

/**
 * 更新团队信息接口
 *
 * @author: guangxush
 * @create: 2021/03/14
 */
@Service
public class UpdateTeamInfoController {

    @Autowired
    private TeamOperationService teamOperationService;

    public void updateTeamInfo(TeamModel teamModel){
        // 更新战队信息
        TeamModel lastTeamModel = teamOperationService.updateTeamInfo(teamModel, (old)->{
            if(old.getUidList() == null){
                old.setUidList(new ArrayList<>());
            }else{
                old.getUidList().add("0001");
            }
            return old;
        });
        return;
    }
}
