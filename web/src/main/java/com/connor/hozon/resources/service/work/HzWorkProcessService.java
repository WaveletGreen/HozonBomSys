package com.connor.hozon.resources.service.work;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.resources.domain.dto.request.*;
import com.connor.hozon.resources.domain.dto.response.HzWorkProcessRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.query.HzWorkProcessByPageQuery;
import com.connor.hozon.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkProcedure;

import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/7/5
 * @Description:
 */
public interface HzWorkProcessService {
    /**
     * 新增一条数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO addHzWorkProcess(AddHzProcessReqDTO reqDTO);

    /**
     * 编辑一条数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateHzWorkProcess(UpdateHzProcessReqDTO reqDTO);
    /**
     * 编辑一条数据2
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateHzWorkProcess2(UpdateHzProcessReqDTO reqDTO);

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    WriteResultRespDTO deleteHzWorkProcess(String puid);
    /**
     * 删除多条数据
     * @param datas
     * @return
     */
    WriteResultRespDTO deleteHzWorkProcesses(Map<String, List<String>> datas);

    /**
     * 分页获取数据
     * @param query
     * @return
     */
    Page<HzWorkProcessRespDTO> findHzWorkProcessForPage(HzWorkProcessByPageQuery query);

    Page<HzWorkProcessRespDTO> findHzWorkProcessForPage2(HzWorkProcessByPageQuery query);


    List<HzWorkProcessRespDTO> findHzWorkProcess(String materielId,String projectId);


    HzWorkProcessRespDTO findHzWorkProcess2(String materielId,String projectId, String procedureDesc);

    @Deprecated
    WriteResultRespDTO applyMbomDataToHzMaterielOneKey(ApplyMbomDataTOHzMaterielReqDTO reqDTO);

    int doUpdateByBatch(Map<String,Object> map);

    int insertHzWorkProcedures(List<HzWorkProcedure> hzWorkProcedures);

    void initProcess(String projectId);

    int deleteHzWorkProcessByMaterielIds(List<HzWorkProcedure> hzWorkProceduresDel);

    List<String> queryProcessDesc( List<String> puidList);

    List<HzWorkProcedure> queryProcedures(List<HzWorkProcedure> hzWorkProcedureList);

    /**
     * 工艺路线发起流程
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO dataToChangeOrder(AddDataToChangeOrderReqDTO reqDTO);

    /**
     * 数据撤销
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO backBomUtilLastValidState(BomBackReqDTO reqDTO);

    /**
     * 批量更新数据，选择性更新数据而不是全部更新
     * @param params
     */
    JSONObject updateList(List<HzWorkProcedure> params);
}
