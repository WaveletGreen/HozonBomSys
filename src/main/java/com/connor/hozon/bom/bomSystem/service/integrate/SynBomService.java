package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynBomService;
import com.connor.hozon.bom.resources.dto.request.UpdateHzMaterielReqDTO;
import net.sf.json.JSONObject;

import java.util.List;

public class SynBomService implements ISynBomService {
    /**
     * 执行更新的时候传，有可能时传多条，传多条也演变成传单挑数据传输，先找到父层，再找到子层，父+子一条一条传即可
     *
     * @param dtos
     * @return
     */
    @Override
    public JSONObject updateByPuids(List<UpdateHzMaterielReqDTO> dtos) {
        return null;
    }

    /**
     * 删除时候传
     *
     * @param dtos
     * @return
     */
    @Override
    public JSONObject deleteByPuids(List<UpdateHzMaterielReqDTO> dtos) {
        return null;
    }

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    @Override
    public JSONObject synAllByProjectPuid(String projectPuid) {
        return null;
    }
}
