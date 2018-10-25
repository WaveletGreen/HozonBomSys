/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.vwo;

import com.connor.hozon.bom.bomSystem.controller.vwo.HzVWOProcessController;
import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoOpiBomDao;
import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoOpiPmtDao;
import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoOpiProjDao;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoFeatureTableDto;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoFormListQueryBase;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoOptionUserDto;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.*;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.iservice.process.IProcess;
import com.connor.hozon.bom.bomSystem.option.TaskOptions;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.process.FeatureProcessManager;
import com.connor.hozon.bom.bomSystem.service.process.InterruptionContainer;
import com.connor.hozon.bom.bomSystem.service.process.ModelColorProcessManager;
import com.connor.hozon.bom.bomSystem.service.process.ReleaseContainer;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import com.connor.hozon.bom.bomSystem.service.project.HzVehicleService;
import com.connor.hozon.bom.bomSystem.service.task.HzTasksService;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.controller.change.vwo.VWOUserGroupDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.sys.dao.OrgGroupDao;
import com.connor.hozon.bom.sys.entity.OrgGroup;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.service.OrgGroupService;
import com.connor.hozon.bom.sys.service.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.cfg0.HzCfg0OptionFamily;
import org.springframework.ui.Model;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;
import sql.pojo.cfg.modelColor.HzCfg0ModelColorDetail;
import sql.pojo.cfg.modelColor.HzCmcrChange;
import sql.pojo.cfg.modelColor.HzCmcrDetailChange;
import sql.pojo.cfg.vwo.*;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;
import sql.pojo.project.HzVehicleRecord;
import sql.pojo.task.HzTasks;

import java.util.*;
import java.util.stream.Collectors;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */

@Service("hzVWOManagerService")
public class HzVwoManagerService implements IHzVWOManagerService {
    /**
     * 特性服务
     */
    @Autowired
    HzCfg0Service hzCfg0Service;

    /**
     * vwo服务
     */
    @Autowired
    IHzVwoInfoService iHzVwoInfoService;
    /**
     * 特性变更表服务
     */
    @Autowired
    IHzFeatureChangeService iHzFeatureChangeService;

    /**
     * 成员所在组
     */
    @Autowired
    OrgGroupDao orgGroupDao;
    /**
     * 项目服务
     */
    @Autowired
    HzProjectLibsService hzProjectLibsService;
    /**
     * 车型服务
     */
    @Autowired
    HzVehicleService hzVehicleService;
    /**
     * 平台服务
     */
    @Autowired
    HzPlatformService hzPlatformService;
    @Autowired
    IHzVwoInfluenceDeptService iHzVwoInfluenceDeptService;
    @Autowired
    IHzVwoInfluenceUserService iHzVwoInfluenceUserService;
    @Autowired
    OrgGroupService orgGroupService;

    @Autowired
    UserService userService;
    @Autowired
    IHzVwoInformChangesService iHzVwoInformChangesService;

    @Autowired
    HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    IHzVwoExecuteService iHzVwoExecuteService;

    @Autowired
    HzCfg0ModelColorDao hzCfg0ModelColorDao;

    @Autowired
    HzColorModelDao hzColorModelDao;

    @Autowired
    HzVwoManagerService hzVwoManagerService;

    //变更主数据
    @Autowired
    HzCmcrChangeDao hzCmcrChangeDao;
    //变更从数据
    @Autowired
    HzCmcrDetailChangeDao hzCmcrDetailChangeDao;
    //特性
    @Autowired
    HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;

    @Autowired
    HzVwoOpiBomDao hzVwoOpiBomDao;
    @Autowired
    HzVwoOpiPmtDao hzVwoOpiPmtDao;
    @Autowired
    HzVwoOpiProjDao hzVwoOpiProjDao;

    @Autowired
    HzVwoInfoService hzVwoInfoService;

    @Autowired
    HzTasksService hzTasksService;

    @Autowired
    FeatureProcessManager featureProcessManager;
    @Autowired
    ModelColorProcessManager modelColorProcessManager;

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(HzVWOProcessController.class);

    /**
     * 特性进入vwo流程
     *
     * @param projectUid 项目UID
     * @param beans      一组特性列表
     * @return 操作消息
     */
    @Override
    public JSONObject featureGetIntoVWO(String projectUid, List<HzCfg0Record> beans) {
        User user = UserInfo.getUser();
        Date now = new Date();
        JSONObject result = new JSONObject();

        if (beans != null && beans.size() > 0) {
            for (HzCfg0Record hzCfg0Record : beans) {
                if (hzCfg0Record.getCfgIsInProcess() == 1) {
                    result.put("status", false);
                    result.put("msg", "已在VWO流程中，不允许重复发起VWO流程");
                    return result;
                }
            }
            List<String> puids = new ArrayList<>();
            beans.forEach(bean -> puids.add(bean.getPuid()));
            List<HzCfg0Record> lists = hzCfg0Service.doLoadListByPuids(puids);
            List<HzCfg0Record> localParams = lists.stream().filter(l -> l != null).collect(Collectors.toList());
            if (beans.size() != localParams.size()) {
                logger.error("搜索出的特性值总数与发起VWO流程的特性值的总数不一致，请检查数据核对数据是否被删除");
                result.put("status", false);
                result.put("msg", "搜索出的特性值总数与发起VWO流程的特性值的总数不一致，请检查数据核对数据是否被删除");
                return result;
            } else {
                //hzCfg0Service.doSetToProcess(localParams);
                System.out.println("总数一致");
                HzVwoInfo hzVwoInfo = generateVwoEntity(user, projectUid, result, 1);
                if (hzVwoInfo == null) {
                    return result;
                }
                HzFeatureChangeBean after = new HzFeatureChangeBean();
                HzFeatureChangeBean before = new HzFeatureChangeBean();
                HzCfg0Record record = new HzCfg0Record();
                Long afterId = -1L, beforeId = -1L;
                for (int i = 0; i < localParams.size(); i++) {

                    if (localParams.get(i).getCfgIsInProcess() != null && 1 == localParams.get(i).getCfgIsInProcess()) {
                        result.put("status", false);
                        result.put("msg", localParams.get(i).getpCfg0ObjectId() + "已在VWO变更流程中，不允许重复发起流程");
                        return result;
                    }

                    after.setCfgPuid(localParams.get(i).getPuid());
                    before.setCfgPuid(localParams.get(i).getPuid());
//                    after.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
//                    before.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");

                    after = iHzFeatureChangeService.doFindNewestChangeFromAfter(after);
                    before = iHzFeatureChangeService.doFindNewestChangeFromBefore(before);

                    try {
                        if (after == null) {
                            after = new HzFeatureChangeBean();
                            if ((afterId = iHzFeatureChangeService.insertByCfgAfter(localParams.get(i))) <= 0) {
                                logger.error("创建后自动同步变更后记录值失败，请联系管理员");
                                result.put("status", false);
                                result.put("msg", localParams.get(i).getpCfg0ObjectId() + "创建后自动同步变更后记录值失败，请联系管理员");
                                return result;
                            }
//                        after.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
                            /*这一段估计有bug*/
                            after.setId(afterId);
                            after = iHzFeatureChangeService.doSelectAfterByPk(after);
                        }

                        if (before == null) {
                            before = new HzFeatureChangeBean();
                            HzCfg0Record localRecord = new HzCfg0Record();
                            localRecord.setPuid(localParams.get(i).getPuid());
                            if ((beforeId = iHzFeatureChangeService.insertByCfgBefore(localRecord)) <= 0) {
                                logger.error("创建后自动同步变更前记录值失败，请联系管理员");
                                result.put("status", false);
                                result.put("msg", localParams.get(i).getpCfg0ObjectId() + "创建后自动同步变更前记录值失败，请联系管理员");
                                return result;
                            }
                            //before.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");
                            /*这一段估计有bug*/
                            before.setId(beforeId);
                            before = iHzFeatureChangeService.doSelectBeforeByPk(before);
                        }

                        ///如果变更前后数据不为空，则是第二次变更
                        /***
                         * 第二次变更，将变更后的数据同步到变更前中，然后再次插入数据库
                         */
//                    after.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
//                    before.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");


                        setVwo(after, hzVwoInfo.getId(), now, user);
                        setVwo(before, hzVwoInfo.getId(), now, user);

//                    before.setVwoId(hzVwoInfo.getId());
//                    before.setCfgIsInProcess(1);
//                    before.setProcessStartDate(now);
//                    before.setProcessStarter(user.getUserName());

                        if (!iHzFeatureChangeService.doUpdateAfterByPk(after)) {
                            logger.error("更新" + localParams.get(i).getpCfg0ObjectId() + "的更新后VWO号失败，请联系系统管理员");
                        }
                        if (!iHzFeatureChangeService.doUpdateBeforeByPk(before)) {
                            logger.error("更新" + localParams.get(i).getpCfg0ObjectId() + "的更新前VWO号失败，请联系系统管理员");
                        }

                        record.setPuid(localParams.get(i).getPuid());
                        record.setCfgIsInProcess(1);
                        record.setVwoId(hzVwoInfo.getId());
                        record.setCfgStatus(0);
                        if (!hzCfg0Service.doUpdate(record)) {
                            logger.error("更新" + record.getpCfg0ObjectId() + "VWO号失败，请联系系统管理员");
                        }
                    } catch (Exception e) {
                        logger.error("更新数据时发生异常:", e);
                    }
                }
            }
            result.put("status", true);
            result.put("msg", "发起VWO流程成功");
            return result;
        } else {
            result.put("status", false);
            result.put("msg", "请至少选择1个特性值发起流程");
            return result;
        }
    }

    /**
     * 特性进入vwo流程
     *
     * @param projectUid 项目UID
     * @param beans      一组特性列表
     * @return 操作消息
     */
    @Override
    public JSONObject featureGetIntoVWO2(String projectUid, List<HzCfg0Record> beans) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        result.put("msg", "发起VWO流程成功");
        Date now = new Date();
        User user = UserInfo.getUser();
        if (beans != null && beans.size() > 0) {
            for (HzCfg0Record hzCfg0Record : beans) {
                if (hzCfg0Record.getCfgIsInProcess() == 1) {
                    result.put("status", false);
                    result.put("msg", "已在VWO流程中，不允许重复发起VWO流程");
                    return result;
                }
            }
            List<String> puids = new ArrayList<>();
            beans.forEach(bean -> puids.add(bean.getPuid()));
            List<HzCfg0Record> lists = hzCfg0Service.doLoadListByPuids(puids);
            List<HzCfg0Record> localParams = lists.stream().filter(l -> l != null).collect(Collectors.toList());
            if (beans.size() != localParams.size()) {
                logger.error("搜索出的特性值总数与发起VWO流程的特性值的总数不一致，请检查数据核对数据是否被删除");
                result.put("status", false);
                result.put("msg", "搜索出的特性值总数与发起VWO流程的特性值的总数不一致，请检查数据核对数据是否被删除");
                return result;
            } else {
                HzVwoInfo hzVwoInfo = generateVwoEntity(user, projectUid, result, 1);
                if (hzVwoInfo == null) {
                    return result;
                }
//                List<HzFeatureChangeBean> hzFeatureChangeBeanListBefore = new ArrayList<HzFeatureChangeBean>();
                List<HzFeatureChangeBean> hzFeatureChangeBeanListAfter = new ArrayList<HzFeatureChangeBean>();
                List<HzCfg0Record> hzCfg0RecordList = new ArrayList<HzCfg0Record>();
                for (HzCfg0Record hzCfg0Record : localParams) {
                    //特性ID
                    String puid = hzCfg0Record.getPuid();
                    //获取最近一次变更后的数据，作为本次变更的变更前数据
                    HzFeatureChangeBean hzFeatureChangeBeanQuery = new HzFeatureChangeBean();
                    hzFeatureChangeBeanQuery.setCfgPuid(puid);
                    HzFeatureChangeBean hzFeatureChangeBeanBefor = iHzFeatureChangeService.doFindNewestChangeFromAfter(hzFeatureChangeBeanQuery);
//                    if(hzFeatureChangeBeanBefor!=null){
//                        hzFeatureChangeBeanListBefore.add(hzFeatureChangeBeanBefor);
//                    }
                    if (hzFeatureChangeBeanBefor == null) {
                        HzFeatureChangeBean hzFeatureChangeBeanAfter = new HzFeatureChangeBean();
                        hzFeatureChangeBeanAfter.setVwoId(hzVwoInfo.getId());
                        hzFeatureChangeBeanAfter.setCfg0MainItemPuid(hzCfg0Record.getpCfg0MainItemPuid());
                        hzFeatureChangeBeanAfter.setFeatureValueName(hzCfg0Record.getpCfg0ObjectId());
                        hzFeatureChangeBeanAfter.setCfgPuid(hzCfg0Record.getPuid());
                        hzFeatureChangeBeanListAfter.add(hzFeatureChangeBeanAfter);
                    }
                    //根据源数据生成变更后的数据
                    HzFeatureChangeBean hzFeatureChangeBeanAfter = new HzFeatureChangeBean();
                    //选项值的objectid
                    hzFeatureChangeBeanAfter.setFeatureValueName(hzCfg0Record.getpCfg0ObjectId());
                    //配置描述
                    hzFeatureChangeBeanAfter.setFeatureValueDesc(hzCfg0Record.getpCfg0Desc());
                    //选项族的名称，也是选项族的objectid
                    hzFeatureChangeBeanAfter.setFeatureName(hzCfg0Record.getpCfg0FamilyName());
                    //选项族的数据库puid
                    hzFeatureChangeBeanAfter.setFeaturePuid(hzCfg0Record.getpCfg0FamilyPuid());
                    //主配置的puid，用这个可以找到主配置的对象
                    hzFeatureChangeBeanAfter.setCfg0MainItemPuid(hzCfg0Record.getpCfg0MainItemPuid());
                    //特性英文名
                    hzFeatureChangeBeanAfter.setH9featureenname(hzCfg0Record.getpH9featureenname());
                    //族描述
                    hzFeatureChangeBeanAfter.setFeatureDesc(hzCfg0Record.getpCfg0FamilyDesc());
                    //相关性
                    hzFeatureChangeBeanAfter.setCfg0Relevance(hzCfg0Record.getpCfg0Relevance());
                    //创建人
                    hzFeatureChangeBeanAfter.setFeatureCreator(user.getLogin());
                    //创建日期
                    hzFeatureChangeBeanAfter.setFeatureCreateDate(now);
                    //最近一次修改者
                    hzFeatureChangeBeanAfter.setFeatureLastModifier(user.getLogin());
                    //最近一次修改日期
                    hzFeatureChangeBeanAfter.setFeatureLastModifyDate(now);
                    //特性是否已成功发送到SAP
                    hzFeatureChangeBeanAfter.setIsFeatureSent(hzCfg0Record.getIsFeatureSent());
                    //相关性是否已成功发送到SAP
                    hzFeatureChangeBeanAfter.setIsRelevanceSent(hzCfg0Record.getIsRelevanceSent());
                    //生效时间
                    hzFeatureChangeBeanAfter.setCfgEffectedDate(hzCfg0Record.getCfgEffectedDate());
                    //废止时间
                    hzFeatureChangeBeanAfter.setCfgAbolishDate(hzCfg0Record.getCfgAbolishDate());
                    //状态0
                    hzFeatureChangeBeanAfter.setCfgStatus(0);
                    //是否在流程中1
                    hzFeatureChangeBeanAfter.setCfgIsInProcess(1);
                    //当前的特性值的主键
                    hzFeatureChangeBeanAfter.setCfgPuid(hzCfg0Record.getPuid());
                    //流程发起的时间
                    hzFeatureChangeBeanAfter.setProcessStartDate(now);
                    //流程发起人
                    hzFeatureChangeBeanAfter.setProcessStarter(user.getLogin());
                    //流程状态
                    hzFeatureChangeBeanAfter.setProcessStatus(10);
                    //变更创建时间
                    hzFeatureChangeBeanAfter.setChangeCreateDate(now);
                    //vwo变更号ID
                    hzFeatureChangeBeanAfter.setVwoId(hzVwoInfo.getId());
                    hzFeatureChangeBeanListAfter.add(hzFeatureChangeBeanAfter);


                    //修改源的变更数据的状态
                    //vwoID
                    hzCfg0Record.setVwoId(hzVwoInfo.getId());
                    //是否在流程中
                    hzCfg0Record.setCfgIsInProcess(1);
                    //状态CFG_STATUS
                    hzCfg0Record.setCfgStatus(0);
                    hzCfg0RecordList.add(hzCfg0Record);
                }
//                //新增变更前数据
//                if(hzFeatureChangeBeanListBefore.size()>0||hzFeatureChangeBeanListBefore!=null){
//                    if(iHzFeatureChangeService.doInsertListBefore(hzFeatureChangeBeanListBefore)<=0){
//                        result.put("status", false);
//                        result.put("msg", "新增变更前数据失败");
//                        return result;
//                    }
//                }
                //新增变更后数据
                if (hzFeatureChangeBeanListAfter.size() > 0 || hzFeatureChangeBeanListAfter != null) {
                    if (iHzFeatureChangeService.doInsertListAfter(hzFeatureChangeBeanListAfter) <= 0) {
                        result.put("status", false);
                        result.put("msg", "新增变更后数据失败");
                        return result;
                    }
                }
                //修改源数据
                if (hzCfg0RecordList.size() > 0 || hzCfg0RecordList != null) {
                    if (hzCfg0Service.doupdateList(hzCfg0RecordList) <= 0) {
                        result.put("status", false);
                        result.put("msg", "修改源数据失败");
                        return result;
                    }
                }
            }
        } else {
            result.put("status", false);
            result.put("msg", "请至少选择1个特性值发起流程");
            return result;
        }
        return result;
    }

    //配色方案进入VWO
    public JSONObject getVWO(List<HzCfg0ModelColor> colors, String projectPuid) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        //源主数据
        List<HzCfg0ModelColor> hzCfg0ModelColors = hzCfg0ModelColorDao.selectByPuids(colors);
        //变更后主数据
        List<HzCmcrChange> hzCmcrChangesAfter = new ArrayList<HzCmcrChange>();
        //变更后从数据
        List<HzCmcrDetailChange> hzCmcrDetailChangesAfter = new ArrayList<HzCmcrDetailChange>();
        //循环查看源主数据是否以发布流程,如已发布过则直接返回错误提示
        for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
            if (hzCfg0ModelColor.getCmcrStatus() != null && !"0".equals(hzCfg0ModelColor.getCmcrStatus())) {
                result.put("status", false);
                result.put("msg", hzCfg0ModelColor.getpDescOfColorfulModel() + "已发起了VWO流程");
                return result;
            }
            hzCfg0ModelColor.setCmcrStatus("10");
        }
        //源从数据
        List<HzCfg0ModelColorDetail> hzCfg0ModelColorDetails = hzColorModelDao.selectByModelColors(hzCfg0ModelColors);
        //最新的Vwo实体类对象
        HzVwoInfo hzVwoInfo = hzVwoManagerService.generateVwoEntity(user, projectPuid, result, 2);
        hzVwoInfo.setVwoType(2);
        //为源主数据添加VWO编码
        for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
            hzCfg0ModelColor.setCmcrVwoId(hzVwoInfo.getId());
        }

        //查询最近一次变更后主数据
        List<HzCmcrChange> hzCmcrChangesLastAfterQuery = new ArrayList<HzCmcrChange>();
        for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
            HzCmcrChange hzCmcrChangeQuery = new HzCmcrChange();
            hzCmcrChangeQuery.setCmcrSrcMainCfg(hzCfg0ModelColor.getpCfg0MainRecordOfMC());
            hzCmcrChangeQuery.setCmcrSrcPuid(hzCfg0ModelColor.getPuid());
            hzCmcrChangesLastAfterQuery.add(hzCmcrChangeQuery);
        }
        List<HzCmcrChange> hzCmcrChangesLastAfter = null;
        try {
            hzCmcrChangesLastAfter = hzCmcrChangeDao.selectLastAfter(hzCmcrChangesLastAfterQuery);
        } catch (Exception e) {
            result.put("status", false);
            result.put("msg", e.getMessage());
        }
        //查询最近一次变更后从数据
        List<HzCmcrDetailChange> hzCmcrDetailChangesQuery = new ArrayList<HzCmcrDetailChange>();
        for (HzCfg0ModelColorDetail hzCfg0ModelColorDetail : hzCfg0ModelColorDetails) {
            HzCmcrDetailChange hzCmcrDetailChange1Query = new HzCmcrDetailChange();
            hzCmcrDetailChange1Query.setCmcrDetailSrcCfgMainUid(hzCfg0ModelColorDetail.getCfgMainUid());
            hzCmcrDetailChange1Query.setCmcrDetailSrcPuid(hzCfg0ModelColorDetail.getPuid());
            hzCmcrDetailChangesQuery.add(hzCmcrDetailChange1Query);
        }
        List<HzCmcrDetailChange> hzCmcrDetailChangesLastAfter = null;
        try {
            hzCmcrDetailChangesLastAfter = hzCmcrDetailChangeDao.selectLastAfter(hzCmcrDetailChangesQuery);
        } catch (Exception e) {
            result.put("status", false);
            result.put("msg", e.getMessage());
        }
        //筛选出第一次变更的数据,并生成主数据
        if (hzCmcrChangesLastAfter.size() != hzCmcrChangesLastAfterQuery.size()) {
            for (HzCmcrChange hzCmcrChangeAfterQuery : hzCmcrChangesLastAfterQuery) {
                boolean flag = false;
                for (HzCmcrChange hzCmcrChange : hzCmcrChangesLastAfter) {
                    if (hzCmcrChangeAfterQuery.getCmcrSrcPuid().equals(hzCmcrChange.getCmcrSrcPuid())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    HzCmcrChange hzCmcrChange = new HzCmcrChange();
                    hzCmcrChange.setCmcrSrcPuid(hzCmcrChangeAfterQuery.getCmcrSrcPuid());
                    hzCmcrChange.setCmcrSrcMainCfg(hzCmcrChangeAfterQuery.getCmcrSrcMainCfg());
                    hzCmcrChange.setCmcrCgVwoId(hzVwoInfo.getId());
                    hzCmcrChangesAfter.add(hzCmcrChange);
                }
            }
        }
        //筛选出第一次变更的数据,并生成从数据
        if (hzCmcrDetailChangesLastAfter.size() != hzCmcrDetailChangesQuery.size()) {
            for (HzCmcrDetailChange hzCmcrDetailChangeQuery : hzCmcrDetailChangesQuery) {
                boolean flag = false;
                for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesLastAfter) {
                    if (hzCmcrDetailChangeQuery.getCmcrDetailSrcPuid().equals(hzCmcrDetailChange.getCmcrDetailSrcPuid())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    HzCmcrDetailChange hzCmcrDetailChange = new HzCmcrDetailChange();
                    hzCmcrDetailChange.setCmcrDetailSrcPuid(hzCmcrDetailChangeQuery.getCmcrDetailSrcPuid());
                    hzCmcrDetailChange.setCmcrDetailSrcCfgMainUid(hzCmcrDetailChangeQuery.getCmcrDetailSrcCfgMainUid());
                    hzCmcrDetailChange.setCmcrDetailCgVwoId(hzVwoInfo.getId());
                    hzCmcrDetailChangesAfter.add(hzCmcrDetailChange);
                }
            }
        }
//        //根据最近一次变更后主数据生成变更前主数据
//        for (HzCmcrChange hzCmcrChange : hzCmcrChangesLastAfter) {
//            hzCmcrChange.setCmcrCgVwoId(hzVwoInfo.getId());
//        }
//        //根据最近一次变更后从数据生成变更前从数据
//        for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesLastAfter) {
//            hzCmcrDetailChange.setCmcrDetailCgVwoId(hzVwoInfo.getId());
//        }


        //根据源主数据生成变更后主数据

        for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
            HzCmcrChange hzCmcrChangeAfter = new HzCmcrChange();
            //VWO号
            hzCmcrChangeAfter.setCmcrCgVwoId(hzVwoInfo.getId());
            //车身颜色代码
            hzCmcrChangeAfter.setCmcrCgShellCode(hzCfg0ModelColor.getpModelShellOfColorfulModel());
            //创建者
            hzCmcrChangeAfter.setCmcrCgCreator(user.getLogin());
            //主配置UID
            hzCmcrChangeAfter.setCmcrSrcMainCfg(hzCfg0ModelColor.getpCfg0MainRecordOfMC());
            //源数据UID
            hzCmcrChangeAfter.setCmcrSrcPuid(hzCfg0ModelColor.getPuid());
            //油漆车身总成
            hzCmcrChangeAfter.setCmcrSrcModelShell(hzCfg0ModelColor.getpModelShellOfColorfulModel());
            //颜色UID
            hzCmcrChangeAfter.setCmcrSrcColorUid(hzCfg0ModelColor.getpColorUid());
            //特性值的外键
            hzCmcrChangeAfter.setCmcrSrcShellCfg0Puid(hzCfg0ModelColor.getpShellCfg0Puid());
            //是否多色
            hzCmcrChangeAfter.setCmcrSrcColorIsMultiply(hzCfg0ModelColor.getpColorIsMultiply());
            //源创建时间
            hzCmcrChangeAfter.setCmcrSrcCreateDate(hzCfg0ModelColor.getCmcrCreateDate());
            //源创建人
            hzCmcrChangeAfter.setCmcrSrcCreator(hzCfg0ModelColor.getCmcrCreator());
            //源修改时间
            hzCmcrChangeAfter.setCmcrSrcUpdateDate(hzCfg0ModelColor.getCmcrUpdateDate());
            //源修改人
            hzCmcrChangeAfter.setCmcrSrcUpdater(hzCfg0ModelColor.getCmcrUpdater());
            //颜色车型代码
            hzCmcrChangeAfter.setCmcrSrcCodeOfColorMod(hzCfg0ModelColor.getpCodeOfColorfulModel());
            //颜色车型描述
            hzCmcrChangeAfter.setCmcrSrcDescOfColorMod(hzCfg0ModelColor.getpDescOfColorfulModel());

            hzCmcrChangesAfter.add(hzCmcrChangeAfter);
        }
        //根据源从数据生成变更后从数据

        for (HzCfg0ModelColorDetail hzCfg0ModelColorDetail : hzCfg0ModelColorDetails) {
            HzCmcrDetailChange hzCmcrDetailChangeAfter = new HzCmcrDetailChange();
            //源数据PUID
            hzCmcrDetailChangeAfter.setCmcrDetailSrcPuid(hzCfg0ModelColorDetail.getPuid());
            //特性
            hzCmcrDetailChangeAfter.setCmcrDetailSrcCfgUid(hzCfg0ModelColorDetail.getCfgUid());
            //颜色外键
            hzCmcrDetailChangeAfter.setCmcrDetailSrcColorUid(hzCfg0ModelColorDetail.getColorUid());
            //主配置UID
            hzCmcrDetailChangeAfter.setCmcrDetailSrcCfgMainUid(hzCfg0ModelColorDetail.getCfgMainUid());
            //vwo主键
            hzCmcrDetailChangeAfter.setCmcrDetailCgVwoId(hzVwoInfo.getId());
            //源数据的创建时间
            hzCmcrDetailChangeAfter.setCmcrDetailSrcCreateDate(hzCfg0ModelColorDetail.getCreateDate());
            //源数据的修改时间
            hzCmcrDetailChangeAfter.setCmcrDetailSrcModifyDate(hzCfg0ModelColorDetail.getModifyDate());
            //源数据的创建者
            hzCmcrDetailChangeAfter.setCmcrDetailCgCreator(hzCfg0ModelColorDetail.getCreator());
            //源数据的修改者
            hzCmcrDetailChangeAfter.setCmcrDetailCgUpdater(hzCfg0ModelColorDetail.getModifier());
            //配色方案主数据PUID
            hzCmcrDetailChangeAfter.setCmcrDetailSrcModelPuid(hzCfg0ModelColorDetail.getModelUid());
            if (hzCfg0ModelColorDetail.getCfgUid() != null) {
                HzCfg0OptionFamily hzCfg0OptionFamilyQuery = new HzCfg0OptionFamily();
                hzCfg0OptionFamilyQuery.setPuid(hzCfg0ModelColorDetail.getCfgUid());
                HzCfg0OptionFamily hzCfg0OptionFamily = hzCfg0OptionFamilyDao.selectByPrimaryKey(hzCfg0OptionFamilyQuery);
                //特性代码
                hzCmcrDetailChangeAfter.setCmcrDetailCgFeatureCode(hzCfg0OptionFamily.getpOptionfamilyName());
                //特性名
                hzCmcrDetailChangeAfter.setCmcrDetailCgFeatureName(hzCfg0OptionFamily.getpOptionfamilyDesc());
            }
            //颜色代码
            hzCmcrDetailChangeAfter.setCmcrDetailCgColorCode(hzCfg0ModelColorDetail.getpColorCode());
            //颜色名称
            hzCmcrDetailChangeAfter.setCmcrDetailCgColorName(hzCfg0ModelColorDetail.getpColorName());

            hzCmcrDetailChangesAfter.add(hzCmcrDetailChangeAfter);
        }


        //跟新数据库
        try {
            //跟新变更后主数据
            if (hzCmcrChangeDao.insertAfterList(hzCmcrChangesAfter) != hzCmcrChangesAfter.size()) {
                result.put("status", false);
                result.put("msg", "跟变更后主数据失败");
            }
            //跟新变更后从数据
            if (hzCmcrDetailChangeDao.insertDetailAfterList(hzCmcrDetailChangesAfter) != hzCmcrDetailChangesAfter.size()) {
                result.put("status", false);
                result.put("msg", "跟变更后从数据失败");
            }
//            //跟新变更前主数据
//            if (hzCmcrChangesLastAfter != null && hzCmcrChangesLastAfter.size() != 0) {
//                if (hzCmcrChangeDao.insertBeforeList(hzCmcrChangesLastAfter) != hzCmcrChangesLastAfter.size()) {
//                    result.put("status", false);
//                    result.put("msg", "跟变更前主数据失败");
//                }
//            }
//            //跟新变更前从数据
//            if (hzCmcrDetailChangesLastAfter != null && hzCmcrDetailChangesLastAfter.size() != 0) {
//                if (hzCmcrDetailChangeDao.insertDetailBeforeList(hzCmcrDetailChangesLastAfter) != hzCmcrDetailChangesLastAfter.size()) {
//                    result.put("status", false);
//                    result.put("msg", "跟变更前从数据失败");
//                }
//            }
        } catch (Exception e) {
            result.put("status", false);
            result.put("msg", e.getMessage());
        }
        //跟新源主数据
        if (hzCfg0ModelColorDao.updateListData(hzCfg0ModelColors) <= 0) {
            result.put("status", false);
            result.put("msg", "跟新源主数据失败");
        }
        //新增VWO数据

        if (result.get("status") == null) {
            result.put("status", true);
        }
        return result;
    }
    //全配置BOM一级清单进入VWO


    //配置物料特性表进入VWO

    //相关性进入VWO

    /**
     * 产生一个最新的Vwo实体类对象
     *
     * @param user       申请Vwo的用户
     * @param projectUid 项目UID
     * @param result
     * @return
     */
    @Override
    public HzVwoInfo generateVwoEntity(User user, String projectUid, JSONObject result, Integer type) {
        Long id = -1L;
        HzVwoInfo hzVwoInfo;
        Date now = new Date();
        synchronized (iHzVwoInfoService) {
            /**
             * 生成VWO号码
             */
            hzVwoInfo = iHzVwoInfoService.generateVWONum();
            OrgGroup group = orgGroupDao.queryOrgGroupById(user.getGroupId());
            HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(projectUid);
            HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(project.getpProjectPertainToVehicle());
            HzPlatformRecord platform = hzPlatformService.doGetByPuid(vehicle.getpVehiclePertainToPlatform());

            hzVwoInfo.setVwoCreator(user.getUserName());
            hzVwoInfo.setProjectUid(projectUid);
            hzVwoInfo.setVwoCreateDate(now);
            hzVwoInfo.setVwoType(type);
            hzVwoInfo.setVwoStatus(10);
            hzVwoInfo.setProjectCode(project.getpProjectCode());
            hzVwoInfo.setVehicleCode(vehicle.getpVehicleCode());
            hzVwoInfo.setPlatformCode(platform.getpPlatformCode());

            if (null != group) {
                hzVwoInfo.setUserDeptName(group.getName());
            }
            try {
                if ((id = iHzVwoInfoService.doInsert(hzVwoInfo)) <= 0) {
                    logger.error("创建新的VWO号失败，请联系系统管理员");
                    result.put("status", false);
                    result.put("msg", "创建新的VWO号失败，请联系系统管理员");
                }
                hzVwoInfo.setId(id);
                return hzVwoInfo;
            } catch (Exception e) {
                logger.error("新增VWO号出错", e);
            }
        }
        return null;
    }

    /**
     * 获取VWO详情表单
     *
     * @param id      VWO主键
     * @param vwoType VWO类型,1是特性变更，2是配色方案变更，3是全配置BOM一级清单变更，4是配置物料特性表变更，5是相关性变更
     * @param model   spring model
     * @return
     */
    @Override
    public boolean getVwoFromList(Long id, Integer vwoType, Model model) {
        if (1 == vwoType) {
            List<HzFeatureChangeBean> after = iHzFeatureChangeService.doSelectAfterByVwoId(id);
            List<HzFeatureChangeBean> before = iHzFeatureChangeService.doSelectBeforeByVwoId(id);
            model.addAttribute("after", after);
            model.addAttribute("before", before);

        } else if (2 == vwoType) {

        } else if (3 == vwoType) {

        } else {
            model.addAttribute("msg", "没有对应的VWO变更");
            return false;
        }
        HzVwoInfo vwoInfo = iHzVwoInfoService.doSelectByPrimaryKey(id);
        HzVwoInfluenceDept influenceDept = iHzVwoInfluenceDeptService.doSelectByVwoId(id);
        HzVwoInfluenceUser influenceUser = iHzVwoInfluenceUserService.doSelectByVwoId(id);
        HzVwoOpiBom hzVwoOpiBom = hzVwoOpiBomDao.selectByVwoId(id);
        HzVwoOpiPmt hzVwoOpiPmt = hzVwoOpiPmtDao.selectByVwoId(id);
        HzVwoOpiProj hzVwoOpiProj = hzVwoOpiProjDao.selectByVwoId(id);
        HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(vwoInfo.getProjectUid());
        //影响部门
        if (influenceDept == null) {
            influenceDept = new HzVwoInfluenceDept();
            influenceDept.setVwoId(id);
            if (iHzVwoInfluenceDeptService.doInsert(influenceDept) <= 0) {
                logger.error("初始化VWO:" + id + "的影响部门数据失败");
                model.addAttribute("msg", "初始化VWO:" + id + "的影响部门数据失败");
                return false;
            }
        }
        //影响人员
        if (influenceUser == null) {
            influenceUser = new HzVwoInfluenceUser();
            influenceUser.setVwoId(id);
            if (iHzVwoInfluenceUserService.doInsert(influenceUser) <= 0) {
                logger.error("初始化VWO:" + id + "的影响人员数据失败");
                model.addAttribute("msg", "初始化VWO:" + id + "的影响人员数据失败");
                return false;
            }
        }

        if (hzVwoOpiBom == null) {
            hzVwoOpiBom = new HzVwoOpiBom();
            hzVwoOpiBom.setOpiVwoId(id);
            if (hzVwoOpiBomDao.insertSelective(hzVwoOpiBom) <= 0) {
                logger.error("初始化VWO:" + id + "的BOM经理意见数据失败");
                model.addAttribute("msg", "初始化VWO:" + id + "的BOM经理意见数据失败");
                return false;
            }
        }
        if (hzVwoOpiPmt == null) {
            hzVwoOpiPmt = new HzVwoOpiPmt();
            hzVwoOpiPmt.setOpiVwoId(id);
            if (hzVwoOpiPmtDao.insertSelective(hzVwoOpiPmt) <= 0) {
                logger.error("初始化VWO:" + id + "的专业PMT经理意见数据失败");
                model.addAttribute("msg", "初始化VWO:" + id + "的专业PMT经理意见数据失败");
                return false;
            }
        }
        if (hzVwoOpiProj == null) {
            hzVwoOpiProj = new HzVwoOpiProj();
            hzVwoOpiProj.setOpiVwoId(id);
            hzVwoOpiProj.setOpiProjMngUserName(project.getpProjectManager());
            hzVwoOpiProj.setOpiProjMngUserId(project.getProjectManagerId());
            if (hzVwoOpiProjDao.insertSelective(hzVwoOpiProj) <= 0) {
                logger.error("初始化VWO:" + id + "的项目经理意见数据失败");
                model.addAttribute("msg", "初始化VWO:" + id + "的项目经理意见数据失败");
                return false;
            }
        }


        model.addAttribute("vwoInfo", vwoInfo);
        model.addAttribute("href", "returnToVwoFromList");
        model.addAttribute("url", "getInformChangers");
        model.addAttribute("vwo", id);
        model.addAttribute("influenceDept", influenceDept);
        model.addAttribute("influenceUser", influenceUser);
        model.addAttribute("vwoType", vwoType);
        model.addAttribute("hzVwoOpiBom", hzVwoOpiBom);
        model.addAttribute("hzVwoOpiPmt", hzVwoOpiPmt);
        model.addAttribute("hzVwoOpiProj", hzVwoOpiProj);
        return true;
    }

    public HzVwoInfo getVwoInfo(Long id) {
        return iHzVwoInfoService.doSelectByPrimaryKey(id);
    }

    /**
     * 获取影响部门
     *
     * @param id
     * @return
     */
    public HzVwoInfluenceDept getInfluenceDept(Long id) {
        HzVwoInfluenceDept influenceDept = iHzVwoInfluenceDeptService.doSelectByVwoId(id);
        //影响部门
        if (influenceDept == null) {
            influenceDept = new HzVwoInfluenceDept();
            influenceDept.setVwoId(id);
            if (iHzVwoInfluenceDeptService.doInsert(influenceDept) <= 0) {
                logger.error("初始化VWO:" + id + "的影响部门数据失败");
            }
        }
        return influenceDept;
    }

    /**
     * 获取影响人员
     *
     * @param id
     * @return
     */
    public HzVwoInfluenceUser getInfluenceUser(Long id) {
        HzVwoInfluenceUser influenceUser = iHzVwoInfluenceUserService.doSelectByVwoId(id);
        //影响人员
        if (influenceUser == null) {
            influenceUser = new HzVwoInfluenceUser();
            influenceUser.setVwoId(id);
            if (iHzVwoInfluenceUserService.doInsert(influenceUser) <= 0) {
                logger.error("初始化VWO:" + id + "的影响人员数据失败");
            }
        }
        return influenceUser;
    }

    /**
     * 获取BOM经理意见
     *
     * @param id
     * @return
     */
    public HzVwoOpiBom getOpiOfBomMng(Long id) {
        HzVwoOpiBom hzVwoOpiBom = hzVwoOpiBomDao.selectByVwoId(id);
        if (hzVwoOpiBom == null) {
            User user = UserInfo.getUser();
            hzVwoOpiBom = new HzVwoOpiBom();
            hzVwoOpiBom.setOpiVwoId(id);
            Date date = new Date();
            hzVwoOpiBom.setOpiBomMngCreateDate(date);
            hzVwoOpiBom.setOpiBomMngCreator(user.getLogin());
            hzVwoOpiBom.setOpiBomMngUpdateDate(date);
            hzVwoOpiBom.setOpiBomMngUpdater(user.getLogin());
            if (hzVwoOpiBomDao.insertSelective(hzVwoOpiBom) <= 0) {
                logger.error("初始化VWO:" + id + "的BOM经理意见数据失败");
            }
        }
        return hzVwoOpiBom;
    }

    /**
     * 获取专业PMT经理意见
     *
     * @param id
     * @return
     */
    public HzVwoOpiPmt getOpiOfPmtMng(Long id) {
        HzVwoOpiPmt hzVwoOpiPmt = hzVwoOpiPmtDao.selectByVwoId(id);
        if (hzVwoOpiPmt == null) {
            User user = UserInfo.getUser();
            hzVwoOpiPmt = new HzVwoOpiPmt();
            hzVwoOpiPmt.setOpiVwoId(id);
            Date date = new Date();
            hzVwoOpiPmt.setOpiPmtMngCreateDate(date);
            hzVwoOpiPmt.setOpiPmtMngCreator(user.getLogin());
            hzVwoOpiPmt.setOpiPmtMngUpdateDate(date);
            hzVwoOpiPmt.setOpiPmtMngUpdater(user.getLogin());
            if (hzVwoOpiPmtDao.insertSelective(hzVwoOpiPmt) <= 0) {
                logger.error("初始化VWO:" + id + "的专业PMT经理意见数据失败");
            }
        }
        return hzVwoOpiPmt;
    }

    /**
     * 获取项目经理意见
     *
     * @param id
     * @return
     */
    public HzVwoOpiProj getOpiOfProjMng(Long id) {
        HzVwoOpiProj hzVwoOpiProj = hzVwoOpiProjDao.selectByVwoId(id);
        if (hzVwoOpiProj == null) {
            User user = UserInfo.getUser();
            hzVwoOpiProj = new HzVwoOpiProj();
            hzVwoOpiProj.setOpiVwoId(id);
            Date date = new Date();
            hzVwoOpiProj.setOpiProjMngCreateDate(date);
            hzVwoOpiProj.setOpiProjMngCreator(user.getLogin());
            hzVwoOpiProj.setOpiProjMngUpdateDate(date);
            hzVwoOpiProj.setOpiProjMngUpdater(user.getLogin());
            if (hzVwoOpiProjDao.insertSelective(hzVwoOpiProj) <= 0) {
                logger.error("初始化VWO:" + id + "的项目经理意见数据失败");
            }
        }
        return hzVwoOpiProj;
    }

    /**
     * 获取特性变更后数据
     *
     * @param id
     * @return
     */
    public List<HzFeatureChangeBean> getFeatureChangeAfter(Long id) {
        return iHzFeatureChangeService.doSelectAfterByVwoId(id);
    }

    /**
     * 获取特性变更前数据
     *
     * @param id
     * @return
     */
    public List<HzFeatureChangeBean> getFeatureChangeBefore(Long id) {
        return iHzFeatureChangeService.doSelectBeforeByVwoId(id);
    }

    @Override
    public JSONObject saveBomLeaderOpinion(HzVwoOpiBom hzVwoOpiBom, Integer vwoType, String projectUid) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        result.put("status", true);
        //为HzVwoOpiBom对象填充数据
        HzVwoOpiBom hzVwoOpiBomQuery = getOpiOfBomMng(hzVwoOpiBom.getOpiVwoId());
        hzVwoOpiBom.setId(hzVwoOpiBomQuery.getId());
        hzVwoOpiBom.setOpiBomMngUpdater(user.getLogin());
        hzVwoOpiBom.setOpiBomMngOptionDate(new Date());
        //修改BOM经理评估意见
        if (hzVwoOpiBomDao.updateByPrimaryKeySelective(hzVwoOpiBom) <= 0) {
            result.put("status", false);
            result.put("msg", "BOM经理评估保存失败");
            return result;
        }
        ;
        HzVwoInfo hzVwoInfo = new HzVwoInfo();
        hzVwoInfo.setId(hzVwoOpiBom.getOpiVwoId());
        if (hzVwoOpiBom.getOpiBomMngAgreement() == 1) {
            hzVwoInfo.setVwoStatus(102);
        } else {
            hzVwoInfo.setVwoStatus(899);
            interrupt(vwoType, projectUid, hzVwoOpiBom.getOpiVwoId());
        }
        //修改VWO流程状态
        if (!iHzVwoInfoService.doUpdateByPrimaryKey(hzVwoInfo)) {
            result.put("status", false);
            result.put("msg", "VWO状态修改失败");
            return result;
        }
        List<HzTasks> tasks = hzTasksService.doSelectUserTargetTaskByType(TaskOptions.FORM_TYPE_VWO, vwoType, hzVwoInfo.getId(), Long.valueOf(user.getId()), 1);
        if (tasks != null && tasks.size() == 1) {
            HzTasks task = tasks.get(0);
            HzTasks taskOfPmt = new HzTasks();
            task.setTaskStatus(999);
            taskOfPmt.setId((task.getId() + 1));
            taskOfPmt.setTaskStatus(1);
            if (!hzTasksService.doUpdateByPrimaryKeySelective(task)) {
                logger.error("更新BOM经理完成任务失败");
            }
            if (!hzTasksService.doUpdateByPrimaryKeySelective(taskOfPmt)) {
                logger.error("更新PMT经理执行任务状态失败");
            }
        }
        result.put("msg", "BOM经理评估成功");
        return result;
    }

//    private void updateTask(Long targetId, Integer targetType, int targetFormType, int userId, int status) {
//        Date now = new Date();
//        HzTasks task = new HzTasks();
//        task.setTaskUserId(Long.valueOf(userId));
//        task.setTaskFormType(targetFormType);
//        task.setTaskTargetType(targetType);
//        task.setTaskTargetId(targetId);
//        task.setTaskStatus(status);
//        task.setTaskUpdateDate(now);
//        if (hzTasksService.doUpdateTargetStatus(task)) {
//            logger.error("更新任务状态失败");
//        }
//    }

    @Override
    public JSONObject savePmtLeaderOpinion(HzVwoOpiPmt hzVwoOpiPmt, Integer vwoType, String projectUid) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        result.put("status", true);
        //为HzVwoOpiPmt对象填充数据
        HzVwoOpiPmt hzVwoOpiPmtQuery = getOpiOfPmtMng(hzVwoOpiPmt.getOpiVwoId());
        hzVwoOpiPmt.setId(hzVwoOpiPmtQuery.getId());
        hzVwoOpiPmt.setOpiPmtMngUpdater(user.getLogin());
        hzVwoOpiPmt.setOpiPmtMngOptionDate(new Date());
        //修改PTM经理评估意见
        if (hzVwoOpiPmtDao.updateByPrimaryKeySelective(hzVwoOpiPmt) <= 0) {
            result.put("status", false);
            result.put("msg", "PTM经理评估保存失败");
            return result;
        }
        HzVwoInfo hzVwoInfo = new HzVwoInfo();
        hzVwoInfo.setId(hzVwoOpiPmt.getOpiVwoId());
        if (hzVwoOpiPmt.getOpiPmtMngAgreement() == 1) {
            hzVwoInfo.setVwoStatus(103);
        } else {
            hzVwoInfo.setVwoStatus(899);
            interrupt(vwoType, projectUid, hzVwoOpiPmt.getOpiVwoId());
        }
        if (!iHzVwoInfoService.doUpdateByPrimaryKey(hzVwoInfo)) {
            result.put("status", false);
            result.put("msg", "VWO状态修改失败");
            return result;
        }
        List<HzTasks> tasks = hzTasksService.doSelectUserTargetTaskByType(TaskOptions.FORM_TYPE_VWO, vwoType, hzVwoInfo.getId(), Long.valueOf(user.getId()), 1);
        if (tasks != null && tasks.size() == 1) {
            HzTasks task = tasks.get(0);
            HzTasks taskOfProj = new HzTasks();
            task.setTaskStatus(999);
            taskOfProj.setId((task.getId() + 1));
            taskOfProj.setTaskStatus(1);
            if (!hzTasksService.doUpdateByPrimaryKeySelective(task)) {
                logger.error("更新PMT经理完成任务失败");
            }
            if (!hzTasksService.doUpdateByPrimaryKeySelective(taskOfProj)) {
                logger.error("更新项目经理执行任务状态失败");
            }
        }
//        updateTask(hzVwoOpiPmt.getOpiVwoId(), vwoType, TaskOptions.FORM_TYPE_VWO, user.getId(), 999);
        result.put("msg", "PTM经理评估成功");
        return result;
    }

    @Override
    public JSONObject saveProjLeaderOpinion(HzVwoOpiProj hzVwoOpiProj, Integer vwoType, String projectUid) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        result.put("status", true);
        //为HzVwoOpiPmt对象填充数据
        HzVwoOpiProj hzVwoOpiProjQuery = getOpiOfProjMng(hzVwoOpiProj.getOpiVwoId());
        hzVwoOpiProj.setId(hzVwoOpiProjQuery.getId());
        hzVwoOpiProj.setOpiProjMngUpdater(user.getLogin());
        hzVwoOpiProj.setOpiProjMngOptionDate(new Date());
        //修改PTM经理评估意见
        if (hzVwoOpiProjDao.updateByPrimaryKeySelective(hzVwoOpiProj) <= 0) {
            result.put("status", false);
            result.put("msg", "项目经理评估保存失败");
            return result;
        }
        HzVwoInfo hzVwoInfo = new HzVwoInfo();
        hzVwoInfo.setId(hzVwoOpiProj.getOpiVwoId());
        if (hzVwoOpiProj.getOpiProjMngAgreement() == 1) {
            hzVwoInfo.setVwoStatus(999);
            release(vwoType, projectUid, hzVwoOpiProj.getOpiVwoId());
        } else {
            hzVwoInfo.setVwoStatus(899);
            interrupt(vwoType, projectUid, hzVwoOpiProj.getOpiVwoId());
        }
        if (!iHzVwoInfoService.doUpdateByPrimaryKey(hzVwoInfo)) {
            result.put("status", false);
            result.put("msg", "VWO状态修改失败");
            return result;
        }
        List<HzTasks> tasks = hzTasksService.doSelectUserTargetTaskByType(TaskOptions.FORM_TYPE_VWO, vwoType, hzVwoInfo.getId(), Long.valueOf(user.getId()), 1);
        if (tasks != null && tasks.size() == 1) {
            HzTasks task = tasks.get(0);
            task.setTaskStatus(999);
            if (!hzTasksService.doUpdateByPrimaryKeySelective(task)) {
                logger.error("更新项目经理经理完成任务失败");
            }
        }
//        updateTask(hzVwoOpiProj.getOpiVwoId(), vwoType, TaskOptions.FORM_TYPE_VWO, user.getId(), 999);
        result.put("msg", "项目经理评估成功");
        return result;
    }

    @Override
    public JSONObject launch(Integer type, String projectUid, Long vwoId, Long formId) {
        JSONObject result = new JSONObject();
        HzVwoOpiBom hzVwoOpiBom = hzVwoOpiBomDao.selectByVwoId(vwoId);
        if (hzVwoOpiBom.getOpiBomMngUserId() == null) {
            result.put("msg", "请指派BOM经理");
            result.put("status", false);
            return result;
        }
        HzVwoOpiPmt hzVwoOpiPmt = hzVwoOpiPmtDao.selectByVwoId(vwoId);
        if (hzVwoOpiPmt.getOpiPmtMngUserId() == null) {
            result.put("msg", "请指派PMT经理");
            result.put("status", false);
            return result;
        }
        HzVwoInfo vwoInfo = hzVwoInfoService.doSelectByPrimaryKey(vwoId);
        HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(vwoInfo.getProjectUid());
        Long managerId = project.getProjectManagerId();
        HzVwoOpiProj hzVwoOpiProj = hzVwoOpiProjDao.selectByVwoId(vwoId);
        if(managerId==null||hzVwoOpiProj.getOpiProjMngUserId()==null||managerId!=hzVwoOpiProj.getOpiProjMngUserId()){
            result.put("msg","请在主页项目中指派项目经理");
            result.put("status",false);
            return result;
        }
        boolean status = toLaunch(type, projectUid, vwoId, formId);
        if (status) {
            result.put("msg", "VWO发起成功");
        } else {
            result.put("msg", "VWO发起失败");
        }
        result.put("status", status);
        return result;
    }

    @Override
    public JSONObject saveOptionUser(HzVwoOptionUserDto hzVwoOptionUserDto) {
        String name = "";
        JSONObject result = new JSONObject();
        result.put("status", true);
        result.put("msg", "指派成功");
        result.put("type", hzVwoOptionUserDto.getSelectId());

        if (hzVwoOptionUserDto == null) {
            result.put("status", false);
        } else {

            switch (hzVwoOptionUserDto.getSelectId()) {
                case 1:
                    HzVwoOpiBom hzVwoOpiBom = new HzVwoOpiBom();
                    hzVwoOpiBom.setOpiBomMngUserId(hzVwoOptionUserDto.getSelectedUserId());
                    hzVwoOpiBom.setOpiVwoId(hzVwoOptionUserDto.getVwoId());
                    hzVwoOpiBom.setOpiBomMngUserName(hzVwoOptionUserDto.getOpiBomName());
                    if (hzVwoOpiBomDao.updateUserByVwoId(hzVwoOpiBom) <= 0) {
                        result.put("status", false);
                        result.put("msg", "指派成员失败");
                        return result;
                    }
                    result.put("name", hzVwoOpiBom.getOpiBomMngUserName());
                    break;
                case 2:
                    HzVwoOpiPmt hzVwoOpiPmt = new HzVwoOpiPmt();
                    hzVwoOpiPmt.setOpiPmtMngUserId(hzVwoOptionUserDto.getSelectedUserId());
                    hzVwoOpiPmt.setOpiVwoId(hzVwoOptionUserDto.getVwoId());
                    hzVwoOpiPmt.setOpiPmtMngUserName(hzVwoOptionUserDto.getOpiPmtName());
                    if (hzVwoOpiPmtDao.updateUserByVwoId(hzVwoOpiPmt) <= 0) {
                        result.put("status", false);
                        result.put("msg", "指派成员失败");
                        return result;
                    }
                    result.put("name", hzVwoOpiPmt.getOpiPmtMngUserName());
                    break;
                case 3:
                    HzVwoOpiProj hzVwoOpiProj = new HzVwoOpiProj();
                    hzVwoOpiProj.setOpiProjMngUserId(hzVwoOptionUserDto.getSelectedUserId());
                    hzVwoOpiProj.setOpiVwoId(hzVwoOptionUserDto.getVwoId());
                    hzVwoOpiProj.setOpiProjMngUserName(hzVwoOptionUserDto.getOpiProjName());
                    if (hzVwoOpiProjDao.updateUserByVwoId(hzVwoOpiProj) <= 0) {
                        result.put("status", false);
                        result.put("msg", "指派成员失败");
                        return result;
                    }
                    result.put("name", hzVwoOpiProj.getOpiProjMngUserName());
                    break;
                default:
                    result.put("status", false);
                    result.put("msg", "选项错误");
                    return result;
            }
        }
        return result;
    }

    /**
     * 保存影响人员
     *
     * @param influenceUser
     * @return
     */
    @Override
    public JSONObject saveInfluenceUser(HzVwoInfluenceUser influenceUser) {
        String name = "";
        JSONObject result = new JSONObject();
        if (influenceUser == null) {
            result.put("status", false);
        } else {
            HzVwoInfluenceUser fromDb = iHzVwoInfluenceUserService.doSelectByVwoId(influenceUser.getVwoId());
            if (fromDb == null) {
                result.put("status", false);
                result.put("msg", "没有找到影响人员原始数据");
                return result;
            }
            influenceUser.setId(fromDb.getId());
            switch (influenceUser.getSelectId()) {
                case 1:
                    influenceUser.setVwoProSectionChiefId(influenceUser.getSelectedUserId());
                    name = influenceUser.getVwoProSectionChiefName();
                    break;
                case 2:
                    influenceUser.setVwoChangeCoordinatorId(influenceUser.getSelectedUserId());
                    name = influenceUser.getVwoChangeCoordinatorName();
                    break;
                case 3:
                    influenceUser.setVwoVehicleBodyUserId(influenceUser.getSelectedUserId());
                    name = influenceUser.getVwoVehicleBodyUserName();
                    break;
                case 4:
                    influenceUser.setVwoChassisId(influenceUser.getSelectedUserId());
                    name = influenceUser.getVwoChassisName();
                    break;
                case 5:
                    influenceUser.setVwoElectricApplianceId(influenceUser.getSelectedUserId());
                    name = influenceUser.getVwoElectricApplianceName();
                    break;
                case 6:
                    influenceUser.setVwoIeoId(influenceUser.getSelectedUserId());
                    name = influenceUser.getVwoIeoName();
                    break;
                case 7:
                    influenceUser.setVwoProjectManagerId(influenceUser.getSelectedUserId());
                    name = influenceUser.getVwoProjectManagerName();
                    break;
                default:
                    result.put("status", false);
                    result.put("msg", "选项错误");
                    return result;
            }
            if (iHzVwoInfluenceUserService.doUpdateByPrimaryKeySelective(influenceUser) <= 0) {
                logger.error("更新影响人员数据出错");
                result.put("status", false);
                result.put("msg", "更新影响人员数据出错");
                return result;
            }
            result.put("msg", "更新影响人员数据成功");
            result.put("type", influenceUser.getSelectId());
            result.put("name", name);
            result.put("status", true);
        }
        return result;
    }

    /**
     * 获取人员和部门信息
     *
     * @return
     */
    @Override
    public List<VWOUserGroupDTO> getUserAndGroupOrg() {
        List<VWOUserGroupDTO> beans = new ArrayList<>();
        List<OrgGroup> groups = orgGroupService.loadAll();
        for (int i = 0; i < groups.size(); i++) {
            VWOUserGroupDTO dto = new VWOUserGroupDTO();
            dto.setUid(groups.get(i).getNode());
            dto.setpUid(groups.get(i).getParentNode());
            dto.setName(groups.get(i).getName());
            dto.setDbId(groups.get(i).getGroupId());
            beans.add(dto);
        }

        List<User> users = userService.loadAll();

        for (int i = 0; i < users.size(); i++) {
            for (int i1 = 0; i1 < beans.size(); i1++) {
                if (null == beans.get(i1).getDbId()) {
                    continue;
                }
                if (users.get(i).getGroupId() == beans.get(i1).getDbId()) {
                    VWOUserGroupDTO dto = new VWOUserGroupDTO();
                    dto.setName(users.get(i).getUserName());
                    dto.setUid(String.valueOf(users.get(i).getId()));
                    dto.setpUid(beans.get(i1).getUid());
                    beans.add(dto);
                }
            }
        }
        return beans;
    }

    /**
     * 保存影响部门
     *
     * @param dept
     * @return
     */
    @Override
    public JSONObject saveInfluenceDept(HzVwoInfluenceDept dept) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        if (dept == null || dept.getVwoId() == null) {
            result.put("status", false);
            result.put("msg", "请选择一个VWO表单进行操作");
            return result;
        } else {
            HzVwoInfluenceDept fromDb = iHzVwoInfluenceDeptService.doSelectByVwoId(dept.getVwoId());
            dept.setVwoId(fromDb.getVwoId());
            dept.setId(fromDb.getId());
            if (iHzVwoInfluenceDeptService.doUpdateByPrimaryKey(dept) <= 0) {
                logger.error("无法更新VWO数据,VWO ID为:" + dept.getId());
                result.put("status", false);
                result.put("msg", "无法更新VWO数据");
            }
            return result;
        }
    }

    /**
     * 保存通知变更人员
     *
     * @param change
     * @return
     */
    @Override
    public JSONObject saveInformChanger(HzVwoInformChanges change) {
        JSONObject result = new JSONObject();
        result.put("status", false);
        if (!checkString(change.getPartId())) {
            logger.error("没有选择正确的人员，可能选择了部门");
            result.put("msg", "没有选择正确的人员，可能选择了部门");
            return result;
        }
        if (change != null) {
            User user = new User();
            try {
                if (null == change.getPersonId()) {
                    logger.error("没有选择正确的人员，可能选择了部门");
                    result.put("status", false);
                    result.put("msg", "没有选择正确的人员");
                }
                user.setId(Math.toIntExact(change.getPersonId()));
            } catch (ArithmeticException e) {
                logger.error("没有选择正确的人员，可能选择了部门");
                result.put("status", false);
                result.put("msg", "没有选择正确的人员");
                return result;
            }
            user = userService.get(user);
            if (user == null) {
                result.put("status", false);
                result.put("msg", "没有找到用户:" + change.getPersonName());
                return result;
            }


            HzVwoInfo info = iHzVwoInfoService.doSelectByPrimaryKey(change.getVwoId());
            if (info == null) {
                result.put("status", false);
                result.put("msg", "没有找到VWO变更号");
                return result;
            }
            Map<String, Object> queryParam = new HashMap<>();
            queryParam.put("projectId", info.getProjectUid());
            queryParam.put("lineID", change.getPartId().trim());
            List<HzEPLManageRecord> itemList = hzEbomRecordDAO.findEbom(queryParam);
            if (itemList == null || itemList.size() <= 0) {
                result.put("status", false);
                result.put("msg", "没有找到零件,零件ID为:" + change.getPartId());
                return result;
            }

            change.setPersonDeptId(user.getGroupId());
            change.setPersonDeptName(user.getOrgGroup().getName());
            change.setVwoNum(info.getVwoNum());
            change.setPartPuid(itemList.get(0).getPuid());
            change.setPartName(itemList.get(0).getpBomLinePartName());
            if (iHzVwoInformChangesService.doInsert(change) <= 0) {
                result.put("status", false);
                result.put("msg", "更新数据失败，请联系系统管理员");
                logger.error("插入关联数据失败");
            }
        }
        result.put("status", true);
        result.put("msg", "更新数据成功");
        return result;
    }

    /**
     * 获取通知变更人员
     *
     * @param vwo
     * @param queryBase
     * @return
     */
    @Override
    public Map<String, Object> getInformChangers(Long vwo, QueryBase queryBase) {
        Map<String, Object> result = new HashMap<>();
        List<HzVwoInformChanges> list = iHzVwoInformChangesService.doSelectByVwoId(vwo);
        Long totalCount = iHzVwoInformChangesService.tellMeHowManyOfIt(vwo);
        result.put("totalCount", totalCount);
        result.put("result", list);
        return result;
    }

    /**
     * 分页查询VWO表单数量
     *
     * @param projectUid
     * @param queryBase
     * @return
     */
    @Override
    public Map<String, Object> queryByBase(String projectUid, HzVwoFormListQueryBase queryBase) {
        Map<String, Object> result = new HashMap<>();
        queryBase.setSort(HzVwoInfo.reflectToDBField(queryBase.getSort()));
        result.put("totalCount", iHzVwoInfoService.tellMeHowManyOfIt(projectUid));
        result.put("result", iHzVwoInfoService.doSelectListByProjectUid(queryBase, projectUid));
        return result;
    }

    /**
     * 获取用户详情
     *
     * @param dto
     * @return
     */
    @Override
    public Map<String, Object> getUserDetail(VWOUserGroupDTO dto) {
        Map<String, Object> result = new JSONObject();
        User user = new User();
        try {
            user.setId(Integer.parseInt(dto.getUid()));
        } catch (NumberFormatException e) {
            logger.error("解析用户ID错误", e);
        }
        user = userService.get(user);
        if (user == null) {
            result.put("status", false);
        } else {
            result.put("status", true);
            result.put("groupName", user.getOrgGroup().getName());
            result.put("personId", user.getId());
            result.put("personName", user.getUserName());
        }
        return result;
    }

    /**
     * 忘了
     *
     * @param changes
     * @return
     */
    @Override
    public JSONObject deleteVwoInfoChange(List<HzVwoInformChanges> changes) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        if (changes == null) {
            result.put("status", false);
            result.put("msg", "没有选择要删除的数据");
            return result;

        } else {
            for (int i = 0; i < changes.size(); i++) {
                if (iHzVwoInformChangesService.doDeleteByPrimaryKey(changes.get(i).getId()) <= 0) {
                    logger.error("删除ID为" + changes.get(i).getId() + "的关联数据失败");
                    result.put("status", false);
                    result.put("msg", "删除关联工程师:" + changes.get(i).getPersonName() + "&emsp;&emsp;关联零件号:" + changes.get(i).getPartId() + "数据失败");
                    return result;
                }
            }
        }
        result.put("msg", "删除关联数据成功");
        return result;
    }


    /**
     * 保存基本数据
     *
     * @param info
     * @return
     */
    @Override
    public JSONObject saveBasic(HzVwoInfo info) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        if (info == null || info.getId() == null) {
            result.put("status", false);
            result.put("msg", "请选择一个VWO表单进行操作");
            return result;
        } else {
            if (info.getVwoStatus() == 899) {
                result.put("status", false);
                result.put("msg", "该VWO已中断，不能保存");
                return result;
            } else if (info.getVwoStatus() == 999) {
                result.put("status", false);
                result.put("msg", "该VWO已发布，不能保存");
                return result;
            }
//            HzVwoInfo fromDb = iHzVwoInfoService.doSelectByPrimaryKey(info.getId());
//            fromDb.setVwoName(info.getVwoName());
//            fromDb.setContactPhoneNum(info.getContactPhoneNum());
//            fromDb.setVwoDemandFinishTime(info.getVwoDemandFinishTime());
////            fromDb.setStrVwoStartEffectiveTime(info.getStrVwoStartEffectiveTime());
////            fromDb.setStrVwoEndEffectiveTime(info.getStrVwoEndEffectiveTime());
//            fromDb.setVwoProjectStage(info.getVwoProjectStage());
//            fromDb.setVwoChangeReason(info.getVwoChangeReason());
//            fromDb.setVwoStartEffectiveTime(info.getVwoStartEffectiveTime());
//            fromDb.setVwoEndEffectiveTime(info.getVwoEndEffectiveTime());
//            fromDb.setVwoConnectedVwo(info.getVwoConnectedVwo());
//            fromDb.setVwoExpectExecuteTime(info.getVwoExpectExecuteTime());
//            fromDb.setVwoConnectedUserDept(info.getVwoCostBearingDept());
//            fromDb.setVwoChangeType(info.);
//            fromDb.setVwoConnectedUserDept();
//            fromDb.setVwoConnectedUserDept();
//            fromDb.setVwoConnectedUserDept();
            if (!iHzVwoInfoService.doUpdateByPrimaryKey(info)) {
                logger.error("无法更新VWO数据,VWO ID为:" + info.getId());
                result.put("status", false);
                result.put("msg", "无法更新VWO数据");
            }
            return result;
        }
    }

    /**
     * 根据VWO ID获取一组分发与实施对象，可能没有
     *
     * @param vwo
     * @return
     */
    @Override
    public Map<String, Object> getExecuteInfo(Long vwo) {
        Map<String, Object> result = new HashMap<>();
        List<HzVwoExecute> executes = iHzVwoExecuteService.doSelectByVwoId(vwo);
        if (executes != null) {
            result.put("totalCount", executes.size());
            result.put("result", executes);
        }
        return result;
    }

    /**
     * 保存分发与实施数据
     *
     * @param execute
     */
    @Override
    public JSONObject saveExecuteInfo(HzVwoExecute execute) {
        JSONObject result = new JSONObject();
        boolean success = false;
        if (null == execute) {
            result.put("status", success);
            result.put("msg", "参数错误");
            return result;
        }
        if (iHzVwoExecuteService.doInsert(execute)) {
            success = true;
            result.put("msg", "存储发布与实施数据成功");
        } else {
            result.put("msg", "存储发布与实施数据失败");
        }
        result.put("status", success);
        return result;
    }

    @Override
    public JSONObject deleteExecuteInfo(List<HzVwoExecute> executes) {
        JSONObject result = new JSONObject();
        boolean success = false;
        if (null == executes || executes.size() <= 0) {
            result.put("status", success);
            result.put("msg", "请至少选择一条数据进行删除");
            return result;
        }
        if (iHzVwoExecuteService.doDeleteByBatch(executes)) {
            success = true;
            result.put("msg", "删除发布与实施数据成功");
        } else {
            result.put("msg", "删除发布与实施数据失败");
        }
        result.put("status", success);
        return result;
    }

    @Override
    public Map<String, Object> getFeatureTable(Long vwoId) {
        Map<String, Object> result = new HashMap<>();
        List<HzFeatureChangeBean> beans = iHzFeatureChangeService.doSelectCfgUidsByVwoId(vwoId);
        List<HzVwoFeatureTableDto> list = new ArrayList<>();
        //临近的两个，前一个为变更前数据，后一个为变更后数据
        if (beans != null && !beans.isEmpty()) {
            for (int i = 0; i < beans.size(); i++) {
                List<HzFeatureChangeBean> bs = iHzFeatureChangeService.doQueryLastTwoChange(beans.get(i).getCfgPuid(),vwoId);
                bs.get(0).setHeadDesc(bs.get(1).getFeatureValueName()+"变更前");
                bs.get(1).setHeadDesc(bs.get(1).getFeatureValueName()+"变更后");
                HzVwoFeatureTableDto before = new HzVwoFeatureTableDto(bs.get(0));
                HzVwoFeatureTableDto after = new HzVwoFeatureTableDto(bs.get(1));
                list.add(before);
                list.add(after);
            }
        }
        result.put("totalCount", list.size());
        result.put("result", list);
        return result;
    }

    /**
     * 发布
     *
     * @param type
     * @param projectUid
     * @param vwoId
     * @return
     */
    @Override
    public JSONObject release(Integer type, String projectUid, Long vwoId) {
        JSONObject result = new JSONObject();
        boolean status = executeProcess(new ReleaseContainer(), type, projectUid, vwoId);
        if (status) {
            result.put("msg", "发布成功");
        } else {
            result.put("msg", "发布失败");
        }
        result.put("status", status);
        return result;
    }

    /**
     * 流程中断
     *
     * @param type
     * @param projectUid
     * @param vwoId
     * @return
     */
    @Override
    public JSONObject interrupt(Integer type, String projectUid, Long vwoId) {
        JSONObject result = new JSONObject();
        boolean status = executeProcess(new InterruptionContainer(), type, projectUid, vwoId);
        if (status) {
            result.put("msg", "流程终止成功");
        } else {
            result.put("msg", "流程终止失败");
        }
        result.put("status", status);
        return result;
    }


    private boolean executeProcess(IProcess container, Integer type, String projectUid, Long vwoId) {
        boolean status = false;
        switch (type) {
            case 1:
                container.setCallBackEntity(featureProcessManager);
                status = container.execute(vwoId, projectUid);
                break;
            case 2:
                container.setCallBackEntity(modelColorProcessManager);
                status = container.execute(vwoId, projectUid);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            default:
        }
        return status;
    }

    /**
     * 单独特性变更的bean
     *
     * @param bean
     * @param id
     * @param now
     * @param user
     */
    private void setVwo(HzFeatureChangeBean bean, Long id, Date now, User user) {
        bean.setVwoId(id);
        bean.setCfgIsInProcess(1);
        bean.setProcessStartDate(now);
        bean.setProcessStarter(user.getUserName());
        bean.setProcessStatus(1);
    }

    /**
     * 发起
     *
     * @param type
     * @param projectUid
     * @param vwoId
     * @return
     */
    public boolean toLaunch(Integer type, String projectUid, Long vwoId, Long formId) {
        User user = UserInfo.getUser();
        HzVwoInfo info = hzVwoInfoService.doSelectByPrimaryKey(vwoId);
        info.setVwoFinisher(user.getLogin());
        info.setVwoStatus(101);
        boolean vwoFlag = hzVwoInfoService.updateByVwoId(info);
        /**发起流程之后，通知到人员*/
        noticeUsers(info, type, user, formId);
        return vwoFlag;
    }

    /**
     * 通知到人，加入任务
     *
     * @param info
     * @param type
     */
    private void noticeUsers(HzVwoInfo info, Integer type, User user, long formId) {
        List<HzTasks> tasks = hzTasksService.doSelectUserVWOTaskByIds(info.getId(), null, type);
        if (tasks == null || tasks.isEmpty()) {
            HzVwoOpiBom bom = hzVwoOpiBomDao.selectByVwoId(info.getId());
            HzVwoOpiPmt pmt = hzVwoOpiPmtDao.selectByVwoId(info.getId());
            HzVwoOpiProj proj = hzVwoOpiProjDao.selectByVwoId(info.getId());
            Date now = new Date();
            HzTasks task = new HzTasks();

            task.setTaskTargetId(info.getId());
            task.setTaskTargetType(type);
            task.setTaskFormType(TaskOptions.FORM_TYPE_VWO);
            task.setTaskLauncher(user.getUserName());
            task.setTaskLauncherId(Long.valueOf(user.getId()));
            task.setTaskCreateDate(now);
            task.setTaskUpdateDate(now);
            task.setTaskFormId(formId);

            try {
                List<HzTasks> list = new ArrayList<>();
                HzTasks taskOfBom = task.clone();
                HzTasks taskOfPmt = task.clone();
                HzTasks taskOfProj = task.clone();

                taskOfBom.setTaskUserId(bom.getOpiBomMngUserId());
                taskOfBom.setTaskStatus(1);

                taskOfPmt.setTaskUserId(pmt.getOpiPmtMngUserId());
                taskOfPmt.setTaskStatus(800);

                taskOfProj.setTaskUserId(proj.getOpiProjMngUserId());
                taskOfProj.setTaskStatus(800);

                if (!hzTasksService.doInsert(taskOfBom) || !hzTasksService.doInsert(taskOfPmt) || !hzTasksService.doInsert(taskOfProj)) {
                    logger.error("通知任务失败");
                }
//                list.add(taskOfBom);
//                list.add(taskOfPmt);
//                list.add(taskOfProj);
                //这里批量插入插入只有2个，需要改进
//                if (!hzTasksService.doInsertByBatch(list)) {
//                    logger.error("通知任务失败");
//                }

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }


        }
    }
}
