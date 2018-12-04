package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.HzLouRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzLouaQuery;
import com.connor.hozon.bom.resources.domain.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzPbomTreeQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzPbomLineRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/5/24
 */
public interface HzPbomService {

    /**
     * PBOM维护 编辑 获取当前登录这信息 进行权限判断
     *
     * @param recordReqDTO
     * @return
     */
    WriteResultRespDTO updateHzPbomRecord(UpdateHzPbomRecordReqDTO recordReqDTO);

    /**
     * PBOM维护 删除  获取当前登录者的信息 进行权限判断
     *
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO deleteHzPbomRecordByForeignId(DeleteHzPbomReqDTO reqDTO);

    /**
     * 获取PBOM信息 工艺合件
     *
     * @param reqDTO
     * @return
     */
    JSONArray getPbomForProcessCompose(HzPbomProcessComposeReqDTO reqDTO);

    /**
     * 获取一条PBOM详情 通过零件号或者puid
     *
     * @param reqDTO
     * @return
     */
    JSONArray getPbomByLineId(HzPbomProcessComposeReqDTO reqDTO);


    /**
     * 分页获取pbom信息
     *
     * @param query
     * @return
     */
    Page<HzPbomLineRespDTO> getHzPbomRecordPage(HzPbomByPageQuery query);

    /**
     * 获取一条pbom信息
     * @param projectId
     * @param puid
     * @return
     */
    HzPbomLineRespDTO getHzPbomByPuid(String projectId, String puid);

    /**
     * 获取pbom结构树
     *
     * @param query
     * @return
     */
    List<HzPbomLineRecord> getHzPbomLineTree(HzPbomTreeQuery query);

    /**
     * 创建工艺合件
     *
     * @param recordReqDTO
     * @return
     */
    @Deprecated
    WriteResultRespDTO andProcessCompose(AddHzPbomRecordReqDTO recordReqDTO);

    /**
     * 查询已删除记录
     *
     * @param query
     * @return
     */
    Page<HzPbomLineRespDTO> getHzPbomRecycleByPage(HzBomRecycleByPageQuery query);

    /**
     * 删除记录恢复
     *
     * @return
     */
    WriteResultRespDTO recoverDeletePbomRecord(String projectId, String puid);


    WriteResultRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO);

    HzPbomLineRecord findParentUtil2Y(String projectId, String puid);


    HzLouRespDTO getHzLouInfoById(HzLouaQuery query);

    /**
     * 开始模拟合成工艺合件，不进行pBom数据的影响
     *
     * @param param
     * @return
     * @Author Fancyears·Maylos·Mayways
     */
    JSONObject simulateCraftingPart(Map<String, Object> param);

    JSONObject queryAccessories(String materielCode);

    JSONObject addAccessories(String puid, String materielCode, String projectId);

    /**
     * 真的生成工艺合件
     * @param param
     * @return
     */
    JSONObject doGenerateProcessCompose(Map<String, Object> param);


    /**
     * PBOM数据  到变更表单
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
