package com.connor.hozon.bom.resources.service.bom;

import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.*;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzMbomLineRecord;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/6/20
 * @Description:
 */
public interface HzMbomService {
    /**
     * 分页获取mbom信息
     * @param query
     * @return
     */
    Page<HzMbomRecordRespDTO> findHzMbomForPage(HzMbomByPageQuery query);

    /**
     *查询颜色件信息
     * @param query
     * @return
     */
//    Page<HzMbomRecordRespDTO> queryMbomToColorPart(HzMbomByPageQuery query);  已废除@xulf
    /**
     * 获取一条MBOM
     * @param query
     * @return
     */
    HzMbomRecordRespDTO findHzMbomByPuid(HzMbomByIdQuery query);

    /**
     * 插入一条MBOM信息
     * @param reqDTO
     * @return
     */
    @Deprecated
    WriteResultRespDTO insertMbomRecord(AddMbomReqDTO reqDTO);

    /**
     * 编辑一条MBOM
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateMbomRecord(UpdateMbomReqDTO reqDTO);

    /**
     * 删除MBOM
     * @param
     * @return
     */
    WriteResultRespDTO deleteMbomRecord(DeleteHzMbomReqDTO reqDTO);

    /**
     * 获取MBOM树
     * @param query
     * @return
     */
    List<HzMbomLineRecord> getHzMbomTree(HzMbomTreeQuery query);

    Page<HzMbomRecordRespDTO> getHzMbomRecycleByPage(HzBomRecycleByPageQuery query);

    /**
     * 删除记录恢复
     * @return
     */
    WriteResultRespDTO recoverDeleteMbomRecord(String projectId, String puid);

    /**
     * 装车件类型
     * @return
     */
    List<String> loadingCarPartType();

    WriteResultRespDTO setCurrentBomToLou(SetLouReqDTO reqDTO);

    HzMbomLineRecord findParentBomUtil2Y(String projectId,String puid);


    HzLouRespDTO getHzLouInfoById(HzLouaQuery query);

    /**
     * 刷新按钮 更新MBOM数据
     * @param projectId
     * @return
     */
    WriteResultRespDTO refreshHzMbom(String projectId);

    /**
     * MBOM数据  到变更表单
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO dataToChangeOrder(AddDataToChangeOrderReqDTO reqDTO);

    /**
     * BOM数据撤销
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO backBomUtilLastValidState(BomBackReqDTO reqDTO);
}
