package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomLevelRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzLouRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.bom.resources.domain.query.HzLouaQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;
import java.util.Set;

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
     * 查询一条bom信息
     * @param puid
     * @param projectId
     * @return
     */
    HzEbomRespDTO fingEbomById(String puid,String projectId);

    HzEbomLevelRespDTO fingEbomLevelById(String puid, String projectId);

    int findIsHasByPuid(String puid, String projectId);

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
     * EBOM引用层级
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO testbomLevelChange(UpdateHzEbomLeveReqDTO reqDTO);

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
    OperateResultMessageRespDTO recoverDeleteEbomRecord(String projectId,String puid);


    /**
     * 向上查询找到2y层BOM
     * @param projectId
     * @param puid
     * @return
     */
    HzBomLineRecord findParentFor2Y(String projectId,String puid);

    OperateResultMessageRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO);


    HzLouRespDTO getHzLouInfoById(HzLouaQuery query);
}
