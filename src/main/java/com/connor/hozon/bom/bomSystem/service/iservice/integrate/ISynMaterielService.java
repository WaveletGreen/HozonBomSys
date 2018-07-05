package com.connor.hozon.bom.bomSystem.service.iservice.integrate;

import com.connor.hozon.bom.resources.dto.request.UpdateHzMaterielReqDTO;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import webservice.option.ActionFlagOption;

import java.util.List;

@Configuration
public interface ISynMaterielService {
    /**
     * 执行更新的时候传
     *
     * @param dtos
     * @return
     */
    JSONObject updateByPuids(List<UpdateHzMaterielReqDTO> dtos);

    /**
     * 删除时候传
     *
     * @param dtos
     * @return
     */
    JSONObject deleteByPuids(List<UpdateHzMaterielReqDTO> dtos);

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject synAllByProjectPuid(String projectPuid);
}
