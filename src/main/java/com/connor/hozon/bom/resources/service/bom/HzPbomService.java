package com.connor.hozon.bom.resources.service.bom;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.dto.request.*;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzPbomByPageQuery;

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
     * @param ebomPuid
     * @return
     */
    OperateResultMessageRespDTO deleteHzPbomRecordByForeignId(String ebomPuid);
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


    /**
     * 分页获取pbom信息
     * @param query
     * @return
     */
    Page<HzPbomLineRespDTO> getHzPbomRecordPage(HzPbomByPageQuery query);

    /**
     * 获取一条pbom信息（包含部分ebom信息）
     * @param projectId
     * @param puid
     * @return
     */
    HzPbomLineRespDTO getHzPbomByPuid(String projectId,String puid);
}
