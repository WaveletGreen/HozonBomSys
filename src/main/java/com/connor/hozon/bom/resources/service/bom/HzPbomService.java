package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzPbomComposeRespDTO;
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
     * PBOM在线维护 编辑BOM信息
     * @param recordReqDTO
     * @return
     */
    int updatePbomLineMaintainRecord(UpdateHzPbomMaintainRecordReqDTO recordReqDTO);

    /**
     * PBOM 在线维护 删除BOM信息
     * @param foreignPuid
     * @return
     */
    int deletePbomLineMaintainByForeignId(String foreignPuid);

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

    /**
     * 插入PBOM维护信息   获取当前登陆者的信息 需要进行权限判断
     * @param recordReqDTO
     * @return
     */
    int insertHzPbomRecord(InsertHzPbomRecordReqDTO recordReqDTO);

    /**
     * PBOM维护 编辑 获取当前登录这信息 进行权限判断
     * @param recordReqDTO
     * @return
     */
    int updateHzPbomRecord(UpdateHzPbomRecordReqDTO recordReqDTO);

    /**
     * PBOM维护 删除  获取当前登录者的信息 进行权限判断
     * @param foreignPuid
     * @return
     */
    int deleteHzPbomRecordByForeignId(String foreignPuid);

    /**
     *  获取pbom全部信息
     * @return
     */
    JSONArray getHzPbomRecord(HzPbomProcessComposeReqDTO reqDTO);

    /**
     * 获取PBOM信息 工艺合件
     * @param reqDTO
     * @return
     */
    JSONArray getPbomForProcessCompose(HzPbomProcessComposeReqDTO reqDTO);


}
