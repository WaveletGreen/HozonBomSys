/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.service.integrate;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import net.sf.json.JSONObject;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface SynFeatureService {

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject synAllByProjectPuid(String projectPuid);

    /**
     * 添加特性值
     *
     * @param features
     * @return
     */
    JSONObject addFeature(List<HzFeatureValue> features) throws Exception;

    /**
     * 更新特性值
     *
     * @param features
     * @return
     */
    JSONObject updateFeature(List<HzFeatureValue> features) throws Exception;

    /**
     * 删除特性值
     *
     * @param features
     * @return
     */
    JSONObject deleteFeature(List<HzFeatureValue> features) throws Exception;

    /**
     * 先删除后添加ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject deleteThenAdd(String projectPuid);

}
