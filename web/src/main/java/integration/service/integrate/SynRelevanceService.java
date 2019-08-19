/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.service.integrate;

import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasic;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface SynRelevanceService {
//    /**
//     * 一开始同步所有数据到ERP，不实现
//     *
//     * @param projectPuid
//     * @return
//     */
//    JSONObject synAllByProjectPuid(String projectPuid) throws Exception;
//
//    /**
//     * 添加相关性
//     *
//     * @param relevance
//     * @return
//     */
//    JSONObject addRelevance(List<HzRelevanceResponseDTO> relevance) throws Exception;
//
//    /**
//     * 更新相关性，只能更新描述
//     *
//     * @param relevancePuid
//     * @return
//     */
//    JSONObject updateRelevance(HzRelevanceResponseDTO relevancePuid) throws Exception;
//
//    /**
//     * 删除相关性，传给SAP时设置ZKNART为3
//     *
//     * @param features
//     * @return
//     */
//    JSONObject deleteRelevance(List<HzRelevanceResponseDTO> features) throws Exception;
//
//    /**
//     * 整理数据，将特性转化成相关性
//     *
//     * @param srcOfFeature      特性列表
//     * @param resultOfRelevance 相关性结果集
//     */
//    void sortData(List<HzFeatureValue> srcOfFeature, List<HzRelevanceResponseDTO> resultOfRelevance);

    JSONObject addRelevance(List<HzRelevanceBasic> relevance) throws Exception;

    JSONObject updateRelevance(List<HzRelevanceBasic> relevance) throws Exception;

    JSONObject deleteRelevance(List<HzRelevanceBasic> relevance) throws Exception;
}
