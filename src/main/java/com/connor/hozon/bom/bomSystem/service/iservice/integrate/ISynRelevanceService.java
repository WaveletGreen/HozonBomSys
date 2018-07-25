package com.connor.hozon.bom.bomSystem.service.iservice.integrate;

import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0Record;

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
    JSONObject synAllByProjectPuid(String projectPuid) throws Exception;

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
    JSONObject updateRelevance(HzRelevanceBean relevancePuid) throws Exception;

    /**
     * 删除相关性，传给SAP时设置ZKNART为3
     *
     * @param features
     * @return
     */
    JSONObject deleteRelevance(List<HzRelevanceBean> features) throws Exception;

    /**
     * 整理数据，将特性转化成相关性
     *
     * @param srcOfFeature      特性列表
     * @param resultOfRelevance 相关性结果集
     */
    void sortData(List<HzCfg0Record> srcOfFeature, List<HzRelevanceBean> resultOfRelevance);

}
