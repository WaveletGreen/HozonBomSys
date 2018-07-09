package com.connor.hozon.bom.bomSystem.service.iservice.integrate;

import com.connor.hozon.bom.resources.dto.request.EditHzMaterielReqDTO;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public interface ISynBomService {
    /**
     * 执行更新的时候传，有可能时传多条，传多条也演变成传单挑数据传输，先找到父层，再找到子层，父+子一条一条传即可
     *
     * @param dtos
     * @return
     */
    JSONObject updateByUids(List<EditHzMaterielReqDTO> dtos);

    /**
     * 删除时候传
     *
     * @param dtos
     * @return
     */
    JSONObject deleteByUids(List<EditHzMaterielReqDTO> dtos);

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject synAllByProjectUid(String projectPuid);
}
