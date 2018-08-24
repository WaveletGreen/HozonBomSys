package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzLouRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.query.HzLouaQuery;
import com.connor.hozon.bom.resources.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzPbomTreeQuery;
import sql.pojo.bom.HzPbomLineRecord;

import java.util.List;

/**
 * Created by haozt on 2018/5/24
 */
public interface HzPbomService {

    /**
     * 插入PBOM维护信息   获取当前登陆者的信息 需要进行权限判断
     * @param recordReqDTO
     * @return
     */
    OperateResultMessageRespDTO insertHzPbomRecord(AddHzPbomRecordReqDTO recordReqDTO);

    /**
     * PBOM维护 编辑 获取当前登录这信息 进行权限判断
     * @param recordReqDTO
     * @return
     */
    OperateResultMessageRespDTO updateHzPbomRecord(UpdateHzPbomRecordReqDTO recordReqDTO);

    /**
     * PBOM维护 删除  获取当前登录者的信息 进行权限判断
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO deleteHzPbomRecordByForeignId(DeleteHzPbomReqDTO reqDTO);
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
     * 分页获取pbom信息
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
    HzPbomLineRespDTO getHzPbomByPuid(String projectId,String puid);

    /**
     * 获取pbom结构树
     * @param query
     * @return
     */
    List<HzPbomLineRecord> getHzPbomLineTree(HzPbomTreeQuery query);

    /**
     * 创建工艺合件
     * @param recordReqDTO
     * @return
     */
    OperateResultMessageRespDTO andProcessCompose(AddHzPbomRecordReqDTO recordReqDTO);

    /**
     * 查询已删除记录
     * @param query
     * @return
     */
    Page<HzPbomLineRespDTO> getHzPbomRecycleByPage(HzBomRecycleByPageQuery query);

    /**
     * 删除记录恢复
     * @return
     */
    OperateResultMessageRespDTO RecoverDeletePbomRecord(String projectId,String puid);


    OperateResultMessageRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO);

    HzPbomLineRecord findParentUtil2Y(String projectId,String puid);


    HzLouRespDTO getHzLouInfoById(HzLouaQuery query);
}