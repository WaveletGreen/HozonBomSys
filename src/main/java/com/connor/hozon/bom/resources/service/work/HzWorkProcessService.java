package com.connor.hozon.bom.resources.service.work;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.ApplyMbomDataTOHzMaterielReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzProcessReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzWorkProcessByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.work.HzWorkProcedure;

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
    OperateResultMessageRespDTO addHzWorkProcess(AddHzProcessReqDTO reqDTO);

    /**
     * 编辑一条数据
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO updateHzWorkProcess(UpdateHzProcessReqDTO reqDTO);

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    OperateResultMessageRespDTO deleteHzWorkProcess(String puid);

    /**
     * 分页获取数据
     * @param query
     * @return
     */
    Page<HzWorkProcessRespDTO> findHzWorkProcessForPage(HzWorkProcessByPageQuery query);

    Page<HzWorkProcessRespDTO> findHzWorkProcessForPage2(HzWorkProcessByPageQuery query);

    HzWorkProcessRespDTO findHzWorkProcess(String materielId,String projectId);

    OperateResultMessageRespDTO applyMbomDataToHzMaterielOneKey(ApplyMbomDataTOHzMaterielReqDTO reqDTO);

    int doUpdateByBatch(Map<String,Object> map);

    int insertHzWorkProcedures(List<HzWorkProcedure> hzWorkProcedures);

    void initProcess(String projectId);
}
