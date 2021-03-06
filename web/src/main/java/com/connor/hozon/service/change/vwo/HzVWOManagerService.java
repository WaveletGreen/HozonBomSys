/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.vwo;

import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.dao.pojo.change.vwo.*;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCmcrChange;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCmcrDetailChange;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.dao.query.change.vwo.HzVwoFormListQuery;
import cn.net.connor.hozon.services.request.change.HzVwoOptionUserRequestDTO;
import com.connor.hozon.controller.bom.change.vwo.VWOUserGroupDTO;
import net.sf.json.JSONObject;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface HzVWOManagerService {
    /**
     * 特性进入vwo流程
     *
     * @param projectUid 项目UID
     * @param beans      一组特性列表
     * @return 操作消息
     */
    JSONObject featureGetIntoVWO(String projectUid, List<HzFeatureValue> beans);
    /**
     * 特性进入vwo流程
     *
     * @param projectUid 项目UID
     * @param beans      一组特性列表
     * @return 操作消息
     */
    JSONObject featureGetIntoVWO2(String projectUid, List<HzFeatureValue> beans, Long changeFromId);

    /**
     * 产生一个最新的Vwo实体类对象
     *
     * @param user       申请Vwo的用户
     * @param projectUid 项目UID
     * @param result     操作消息在生成器里同样有赋值
     * @return 操作消息
     */
    HzVwoInfo generateVwoEntity(User user, String projectUid, JSONObject result,Integer type);

    /**
     * 获取VWO详情表单
     *
     * @param id      VWO主键
     * @param vwoType VWO类型
     * @param model   spring model
     * @return
     */
    boolean getVwoFromList(Long id, Integer vwoType, Model model);

    /**
     * 保存影响人员
     *
     * @param influenceUser
     * @return
     */
    JSONObject saveInfluenceUser(HzVwoInfluenceUser influenceUser);

    /**
     * 获取人员和部门信息
     *
     * @return
     */
    List<VWOUserGroupDTO> getUserAndGroupOrg();

    /**
     * 保存影响部门
     *
     * @param dept
     * @return
     */
    JSONObject saveInfluenceDept(HzVwoInfluenceDept dept);

    /**
     * 保存通知变更人员
     *
     * @param change
     * @return
     */
    JSONObject saveInformChanger(HzVwoInformChanges change);

    /**
     * 获取通知变更人员
     *
     * @param vwo
     * @param queryBase
     * @return
     */
    Map<String, Object> getInformChangers(Long vwo, QueryBase queryBase);

    /**
     * 分页查询VWO表单数量
     *
     * @param projectUid
     * @param queryBase
     * @return
     */
    Map<String, Object> queryByBase(String projectUid, HzVwoFormListQuery queryBase);

    /**
     * 获取用户详情
     *
     * @param dto
     * @return
     */
    Map<String, Object> getUserDetail(VWOUserGroupDTO dto);

    /**
     * 忘了
     *
     * @param changes
     * @return
     */
    JSONObject deleteVwoInfoChange(List<HzVwoInformChanges> changes);

    /**
     * 保存基本数据
     *
     * @param info
     * @return
     */
    JSONObject saveBasic(HzVwoInfo info);

    /**
     * 根据VWO ID获取一组分发与实施对象，可能没有
     *
     * @param vwo
     * @return
     */
    Map<String, Object> getExecuteInfo(Long vwo);

    /**
     * 保存分发与实施数据
     *
     * @param execute
     */
    JSONObject saveExecuteInfo(HzVwoExecute execute);

    JSONObject deleteExecuteInfo(List<HzVwoExecute> executes);

    /**
     * 发布
     *
     * @param type
     * @param projectUid
     * @param vwoId
     * @return
     */
    JSONObject release(Integer type, String projectUid, Long vwoId);

    /**
     * 流程中断
     *
     * @param type
     * @param projectUid
     * @param vwoId
     * @return
     */
    JSONObject interrupt(Integer type, String projectUid, Long vwoId);


    /**
     * 获取VWO主数据
     * @param id
     * @return
     */

    HzVwoInfo getVwoInfo(Long id);

    /**
     * 获取影响部门
     *
     * @param id
     * @return
     */
    HzVwoInfluenceDept getInfluenceDept(Long id);


    /**
     * 获取影响人员
     *
     * @param id
     * @return
     */
     HzVwoInfluenceUser getInfluenceUser(Long id);

    /**
     * 获取BOM经理意见
     *
     * @param id
     * @return
     */
     HzVwoOpiBom getOpiOfBomMng(Long id);


    /**
     * 获取专业PMT经理意见
     *
     * @param id
     * @return
     */
    HzVwoOpiPmt getOpiOfPmtMng(Long id);

    /**
     * 获取项目经理意见
     * @param id
     * @return
     */
    HzVwoOpiProj getOpiOfProjMng(Long id);


    /**
     * 获取特性变更后数据
     *
     * @param id
     * @return
     */
    List<HzFeatureChangeBean> getFeatureChangeAfter(Long id);

    /**
     * 获取特性变更前数据
     *
     * @param id
     * @return
     */
    List<HzFeatureChangeBean> getFeatureChangeBefore(Long id);

    JSONObject saveBomLeaderOpinion(HzVwoOpiBom hzVwoOpiBom, Integer vwoType, String projectUid);

    JSONObject savePmtLeaderOpinion(HzVwoOpiPmt hzVwoOpiPmt, Integer vwoType, String projectUid);

    JSONObject saveProjLeaderOpinion(HzVwoOpiProj hzVwoOpiProj, Integer vwoType, String projectUid);

    JSONObject launch(Integer type, String projectUid, Long vwoId,Long formId);

    JSONObject saveOptionUser(HzVwoOptionUserRequestDTO hzVwoOptionUserRequestDTO);

    Map<String, Object> getFeatureTable(Long vwoId);

    List<HzCmcrChange> doQueryCmcrChangeBeforAndAfter(String cmcrSrcPuid, Long cmcrCgVwoId);

    List<HzCmcrDetailChange> doQueryCmcrDetailChangBeforAndAfter(List<String> cmcrDetailSrcPuidList, Long cmcrCgVwoId);

    void doQueryCmcrDetailChangBefor(Map<String,Object> map, Long vwoId);

    Map<String,Object> getMaterielFeatureTable(Long formId,String projectUid);

    Map<String,Object> getFullCfgTable(Integer orderChangeId, String projectUid);

    JSONObject deleteChangeFeature(List<Long> changeFeatureIds,Long orderId);

    JSONObject deleteChangeColorModel(List<Long> changeColorModelIds,Long orderId);

    JSONObject deleteChangeMaterielFeature(List<Long> changeMaterielFeatureIds,Long orderId);

    JSONObject deleteChangeBomAll(Long mainId,Long orderId);

    void doQueryCmcrDetailChangBefor2(Map<String,Object> map, Long vwoId);

    Map<String,Object> getRelevance(Long orderChangeId, String projectUid);

    JSONObject deleteChangeRelevance(Long orderChangeId);

    boolean featureToSap(Long vwoId);

    boolean derivativeMaterielToSap(Long orderId);

    boolean relevanceToSap(Long orderId);
}
