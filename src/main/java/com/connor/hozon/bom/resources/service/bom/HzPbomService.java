package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.dto.request.InsertHzPbomMaintainRecordReqDTO;
import com.connor.hozon.bom.resources.dto.request.SearchPbomDetailReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineMaintainRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;

import java.util.List;

/**
 * Created by haozt on 2018/5/24
 */
public interface HzPbomService {
    /**
     * 查询PBOM 维护 详细
     * @return
     */
    List<HzPbomLineMaintainRespDTO> getHzPbomMaintainRecord();

    /**
     * PBOM在线维护 批量新增
     * @param recordInsertBatchReqDTO
     * @return
     */
    int insertPbomLineMaintainRecords(List<InsertHzPbomMaintainRecordReqDTO> recordInsertBatchReqDTO);

    /**
     * 获取PBOM信息
     * @return
     */
    List<HzPbomLineRespDTO> getHzPbomLineRecord();

    /**
     * 按条件搜索PBOM 在线维护信息
     * @param reqDTO
     * @return
     */
    List<HzPbomLineMaintainRespDTO> searchPbomLineMaintainRecord(SearchPbomDetailReqDTO reqDTO);

    /**
     * 按条件搜索PBOM 管理信息
     * @param reqDTO
     * @return
     */
    List<HzPbomLineRespDTO> searchPbomLineManageRecord(SearchPbomDetailReqDTO reqDTO);

}
