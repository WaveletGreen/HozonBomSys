package com.connor.hozon.bom.bomSystem.service.iservice.integrate;

import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 同步相关性服务
 */
@Configuration
public interface ISynRelevanceService {
    /**
     * 一开始同步所有数据到ERP，不实现
     *
     * @param projectPuid
     * @return
     */
    JSONObject synAllByProjectPuid(String projectPuid);

    /**
     * 添加相关性
     *
     * @param relevance
     * @return
     */
    JSONObject addRelevance(List<HzRelevanceBean> relevance) throws Exception;

    /**
     * 更新相关性，只能更新描述
     *
     * @param relevancePuid
     * @return
     */
    JSONObject updateRelevance(String relevancePuid);

    /**
     * 删除相关性，传给SAP时设置ZKNART为3
     *
     * @param features
     * @return
     */
    JSONObject deleteRelevance(List<HzRelevanceBean> features) throws Exception;

}
