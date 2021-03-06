package com.connor.hozon.resources.service.bom;

import com.connor.hozon.resources.domain.dto.response.HzSingleVehiclesBomRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.query.HzSingleVehiclesBomByPageQuery;
import com.connor.hozon.resources.page.Page;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:
 */
public interface HzSingleVehiclesBomServices {

    /**
     * 解算产生单车BOM 单车BOM产生于超级MBOM
     * @param projectId
     * @return
     */
    WriteResultRespDTO analysisSingleVehicles(String projectId);

    /**
     * 解析产生全部项目的单车BOM
     */
    void analysisAllSingleVehicles();

    /**
     * 分页获取单车BOM
     * @param query
     * @return
     */
    Page<HzSingleVehiclesBomRespDTO> getHzSingleVehiclesBomByPage(HzSingleVehiclesBomByPageQuery query);

}
