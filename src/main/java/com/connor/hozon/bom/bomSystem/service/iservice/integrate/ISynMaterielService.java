package com.connor.hozon.bom.bomSystem.service.iservice.integrate;

import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import webservice.option.ActionFlagOption;

import java.util.List;

@Configuration
public interface ISynMaterielService {
    /**
     * 执行更新的时候传
     *
     * @param puids
     * @return
     */
    JSONObject updateByPuids(List<String> puids);

    JSONObject deleteByPuids(List<String> puids);

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject synAllByProjectPuid(String projectPuid);
}
