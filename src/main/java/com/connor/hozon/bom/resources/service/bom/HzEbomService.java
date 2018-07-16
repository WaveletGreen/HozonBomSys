package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzEbomTreeQuery;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomService {

    /**
     * 分页获取EBOM信息
     * @param query
     * @return
     */
    Page<HzEbomRespDTO> getHzEbomPage(HzEbomByPageQuery query);

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
    OperateResultMessageRespDTO addHzEbomRecord(AddHzEbomReqDTO reqDTO);

    /**
     * 更新EBOM
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO);

    /**
     * 删除EBOM
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO);

    /**
     * 找出当前bom的全部子bom  递归查找 bom结构树
     * @param
     * @return
     */
    List<HzEPLManageRecord> findCurrentBomChildren(HzEbomTreeQuery query);

    Page<HzEbomRespDTO> getHzEbomRecycleByPage(HzBomRecycleByPageQuery query);

    /**
     * 删除记录恢复
     * @return
     */
    OperateResultMessageRespDTO RecoverDeleteEbomRecord(String projectId,String puid);
}
