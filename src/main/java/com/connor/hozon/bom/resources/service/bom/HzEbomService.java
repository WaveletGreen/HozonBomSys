package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomService {

    /**
     * 分页获取EBOM信息
     * @param recordReqDTO
     * @return
     */
    Page<HzEbomRespDTO> getHzEbomPage(FindForPageReqDTO recordReqDTO);

    /**
     * 获取EBOM标题信息
     * @param projectId
     * @return
     */
    JSONArray getEbomTitle(String projectId);

    /**
     * 查询一条bom信息
     * @param puid
     * @param projectId
     * @return
     */
    HzEbomRespDTO fingEbomById(String puid,String projectId);

    /**
     * 新增EBOM
     * @param reqDTO
     * @return
     */
    int addHzEbomRecord(AddHzEbomReqDTO reqDTO);

    /**
     * 更新EBOM
     * @param reqDTO
     * @return
     */
    int updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO);

    /**
     * 删除EBOM
     * @param reqDTO
     * @return
     */
    int deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO);

    List<HzEPLManageRecord> findCurrentBomChildren(Map<String, Object> map);
}
