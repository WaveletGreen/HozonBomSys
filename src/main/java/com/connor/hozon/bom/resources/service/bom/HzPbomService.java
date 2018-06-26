package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.page.Page;

import java.util.List;

/**
 * Created by haozt on 2018/5/24
 */
public interface HzPbomService {

    /**
     * PBOM在线维护 批量新增
     * @param recordInsertBatchReqDTO
     * @return
     */
    int insertPbomLineMaintainRecords(List<InsertHzMbomMaintainRecordReqDTO> recordInsertBatchReqDTO);

    /**
     * PBOM在线维护 编辑BOM信息
     * @param recordReqDTO
     * @return
     */
    int updatePbomLineMaintainRecord(UpdateHzPbomMaintainRecordReqDTO recordReqDTO);


    /**
     * 获取PBOM信息
     * @return
     */
    List<HzPbomLineRespDTO> getHzPbomLineRecord(HzPbomProcessComposeReqDTO reqDTO);

    /**
     * 按条件搜索MBOM 在线维护信息 要迁移
     * @param reqDTO
     * @return
     */
    List<HzMbomRecordRespDTO> searchPbomLineMaintainRecord(SearchPbomDetailReqDTO reqDTO);

    /**
     * 按条件搜索PBOM 管理信息
     * @param reqDTO
     * @return
     */
    List<HzPbomLineRespDTO> searchPbomManageRecord(SearchPbomDetailReqDTO reqDTO);

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

    /**
     * 获取一条PBOM详情 通过零件号或者puid
     * @param reqDTO
     * @return
     */
    JSONArray getPbomByLineId(HzPbomProcessComposeReqDTO reqDTO);

    /**
     * 添加工艺合件信息到bom
     * @param reqDTO
     * @return
     */
    int addPbomProcessCompose(AddProcessComposeReqDTO reqDTO);


    Page<HzPbomLineRespDTO> getHzPbomRecordPage(FindForPageReqDTO reqDTO);

    /**
     * 获取一条pbom信息（包含部分ebom信息）
     * @param projectId
     * @param puid
     * @return
     */
    HzPbomLineRespDTO getHzPbomByPuid(String projectId,String puid);
}
