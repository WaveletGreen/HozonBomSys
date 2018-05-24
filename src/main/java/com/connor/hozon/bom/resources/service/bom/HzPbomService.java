package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.dto.response.HzPbomLineMaintainResponseDTO;
import sql.pojo.bom.HzPbomLineMaintainRecord;

import java.util.List;

/**
 * Created by haozt on 2018/5/24
 */
public interface HzPbomService {
    /**
     * 查询PBOM 维护 详细
     * @return
     */
    List<HzPbomLineMaintainResponseDTO> getHzPbomMaintainRecord();
}
