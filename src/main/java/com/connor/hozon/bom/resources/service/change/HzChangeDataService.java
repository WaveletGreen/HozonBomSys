package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.domain.dto.request.BomBackReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.*;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataQuery;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:变更表单数据
 */
public interface HzChangeDataService {

    /**
     * 获取变更表单变更数据 超链接
     * @param query
     * @return
     */
    List<HzChangeDataRespDTO> getChangeDataHyperRecord(HzChangeDataQuery query);

    /**
     * 获取超链接下的变更数据
     * @param query
     * @return
     */
    List<HzEbomRespDTO> getChangeDataRecordForEBOM(HzChangeDataQuery query);

    /**
     * 查询PBOM的变更清单信息
     * @param query
     * @return
     */
    List<HzPbomLineRespDTO> getChangeDataRecordForPBOM(HzChangeDataQuery query);

    /**
     * 查询MBOM的变更清单信息
     * @param query
     * @return
     */
    List<HzMbomRecordRespDTO> getChangeDataRecordForMBOM(HzChangeDataQuery query);

    /**
     * 查询物料数据变更单信息
     * @param query
     * @return
     */
    List<HzMaterielRespDTO> getChangeDataRecordForMateriel(HzChangeDataQuery query);

    /**
     * 查询工艺路线的变更数据
     * @param query
     * @return
     */
    List<HzWorkProcessRespDTO> getChangeDataRecordForWorkProcedure(HzChangeDataQuery query);

    /**
     * 删除EBOM变更表单数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteEBOMChangeDataDetail(BomBackReqDTO reqDTO);

    /**
     * 删除PBOM变更表单数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deletePBOMChangeDataDetail(BomBackReqDTO reqDTO);
    /**
     * 删除MBOM变更表单数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteMBOMChangeDataDetail(BomBackReqDTO reqDTO);
    /**
     * 删除财务MBOM变更表单数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteFinanceMBOMChangeDataDetail(BomBackReqDTO reqDTO);

    /**
     * 删除生产MBOM变更表单数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteProductMBOMChangeDataDetail(BomBackReqDTO reqDTO);
    /**
     * 删除物料变更表单数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteMaterielChangeDataDetail(BomBackReqDTO reqDTO);

    /**
     * 删除工艺路线变更表单数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteWorkProcedureChangeDataDetail(BomBackReqDTO reqDTO);

}
