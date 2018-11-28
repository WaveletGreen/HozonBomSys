/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.vwo;

import com.connor.hozon.bom.bomSystem.controller.vwo.HzVWOProcessController;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDMBasicChangeDao;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDMDetailChangeDao;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDerivativeMaterielBasicDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainChangeDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgModelChangeDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgWithCfgChangeDao;
import com.connor.hozon.bom.bomSystem.dao.model.HzCfg0ModelDetailDao;
import com.connor.hozon.bom.bomSystem.dao.model.HzCfg0ModelRecordDao;
import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoOpiBomDao;
import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoOpiPmtDao;
import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoOpiProjDao;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoFeatureTableDto;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoFormListQueryBase;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoOptionUserDto;
import com.connor.hozon.bom.bomSystem.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.*;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.iservice.process.IProcess;
import com.connor.hozon.bom.bomSystem.option.TaskOptions;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.derivative.HzCfg0ModelFeatureService;
import com.connor.hozon.bom.bomSystem.service.process.FeatureProcessManager;
import com.connor.hozon.bom.bomSystem.service.process.InterruptionContainer;
import com.connor.hozon.bom.bomSystem.service.process.ModelColorProcessManager;
import com.connor.hozon.bom.bomSystem.service.process.ReleaseContainer;
import com.connor.hozon.bom.bomSystem.service.project.*;
import com.connor.hozon.bom.bomSystem.service.task.HzTasksService;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.controller.change.vwo.VWOUserGroupDTO;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
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
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.cfg.cfg0.HzCfg0OptionFamily;
import org.springframework.ui.Model;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.derivative.HzCfg0ModelFeature;
import sql.pojo.cfg.derivative.HzDMBasicChangeBean;
import sql.pojo.cfg.derivative.HzDMDetailChangeBean;
import sql.pojo.cfg.fullCfg.HzFullCfgMainChange;
import sql.pojo.cfg.fullCfg.HzFullCfgModelChange;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfgChange;
import sql.pojo.cfg.model.HzCfg0ModelRecord;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;
import sql.pojo.cfg.modelColor.HzCfg0ModelColorDetail;
import sql.pojo.cfg.modelColor.HzCmcrChange;
import sql.pojo.cfg.modelColor.HzCmcrDetailChange;
import sql.pojo.cfg.vwo.*;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.change.HzChangeOrderRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.*;
import sql.pojo.task.HzTasks;

import java.util.*;
import java.util.stream.Collectors;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * @Author: Fancyears·Maylos·Malvis
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

    @Autowired
    HzCfg0RecordDao hzCfg0RecordDao;
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
    HzBrandService hzBrandService;
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

    //衍生物料变更DAO层
    @Autowired
    HzDMBasicChangeDao hzDMBasicChangeDao;
    @Autowired
    HzDMDetailChangeDao hzDMDetailChangeDao;
    //车辆模型DAO
    @Autowired
    HzCfg0ModelFeatureService hzCfg0ModelFeatureService;
    //工厂DAO
    @Autowired
    HzFactoryDAO hzFactoryDAO;
    //超级物料
    @Autowired
    HzSuperMaterielService hzSuperMaterielService;

    @Autowired
    HzDerivativeMaterielBasicDao hzDerivativeMaterielBasicDao;


    /*****全配置BOM的DAO******/
    @Autowired
    HzFullCfgMainChangeDao hzFullCfgMainChangeDao;
    @Autowired
    HzFullCfgMainDao hzFullCfgMainDao;
    @Autowired
    HzFullCfgModelChangeDao hzFullCfgModelChangeDao;
    @Autowired
    HzFullCfgWithCfgChangeDao hzFullCfgWithCfgChangeDao;
    @Autowired
    HzCfg0ModelRecordDao hzCfg0ModelRecordDao;
    @Autowired
    HzCfg0ModelDetailDao hzCfg0ModelDetailDao;
    //2Y层DAO
    @Autowired
    HzBomLineRecordDaoImpl hzBomLineRecordDao;

    //变更人员关系dao
    @Autowired
    HzChangeDataRecordDAO hzChangeDataRecordDAO;

    //变更表单Dao层
    @Autowired
    HzChangeOrderDAO hzChangeOrderDAO;
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
    public JSONObject featureGetIntoVWO2(String projectUid, List<HzCfg0Record> beans, Long changeFromId) {
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
//                HzVwoInfo hzVwoInfo = generateVwoEntity(user, projectUid, result, 1);
//                List<HzFeatureChangeBean> hzFeatureChangeBeanListBefore = new ArrayList<HzFeatureChangeBean>();
                List<HzFeatureChangeBean> hzFeatureChangeBeanListAfter = new ArrayList<HzFeatureChangeBean>();
                List<HzCfg0Record> hzCfg0RecordList = new ArrayList<HzCfg0Record>();
                for (HzCfg0Record hzCfg0Record : localParams) {
//                    //特性ID
//                    String puid = hzCfg0Record.getPuid();
//                    //获取最近一次变更后的数据，作为本次变更的变更前数据
//                    HzFeatureChangeBean hzFeatureChangeBeanQuery = new HzFeatureChangeBean();
//                    hzFeatureChangeBeanQuery.setCfgPuid(puid);
//                    HzFeatureChangeBean hzFeatureChangeBeanBefor = iHzFeatureChangeService.doFindNewestChangeFromAfter(hzFeatureChangeBeanQuery);
////                    if(hzFeatureChangeBeanBefor!=null){
////                        hzFeatureChangeBeanListBefore.add(hzFeatureChangeBeanBefor);
////                    }
//                    if (hzFeatureChangeBeanBefor == null) {
//                        HzFeatureChangeBean hzFeatureChangeBeanAfter = new HzFeatureChangeBean();
//                        hzFeatureChangeBeanAfter.setVwoId(changeFromId);
//                        hzFeatureChangeBeanAfter.setCfg0MainItemPuid(hzCfg0Record.getpCfg0MainItemPuid());
//                        hzFeatureChangeBeanAfter.setFeatureValueName(hzCfg0Record.getpCfg0ObjectId());
//                        hzFeatureChangeBeanAfter.setCfgPuid(hzCfg0Record.getPuid());
//                        hzFeatureChangeBeanAfter.setCfgStatus(1);
//                        hzFeatureChangeBeanListAfter.add(hzFeatureChangeBeanAfter);
//                    }
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
                    hzFeatureChangeBeanAfter.setCfgStatus(hzCfg0Record.getCfgStatus());
                    //是否在流程中1
                    hzFeatureChangeBeanAfter.setCfgIsInProcess(1);
                    //当前的特性值的主键
                    hzFeatureChangeBeanAfter.setCfgPuid(hzCfg0Record.getPuid());
                    //流程发起的时间
                    hzFeatureChangeBeanAfter.setProcessStartDate(now);
                    //流程发起人
                    hzFeatureChangeBeanAfter.setProcessStarter(user.getLogin());
                    //流程状态
                    hzFeatureChangeBeanAfter.setProcessStatus(0);
                    //变更创建时间
                    hzFeatureChangeBeanAfter.setChangeCreateDate(now);
                    //vwo变更号ID
                    hzFeatureChangeBeanAfter.setVwoId(changeFromId);
                    hzFeatureChangeBeanListAfter.add(hzFeatureChangeBeanAfter);


                    //修改源的变更数据的状态
                    //vwoID
                    hzCfg0Record.setVwoId(changeFromId);
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
                //流程绑定人员
                HzChangeDataRecord hzChangeDataRecord = new HzChangeDataRecord();
                hzChangeDataRecord.setApplicantId(Long.valueOf(user.getId()));
                hzChangeDataRecord.setOrderId(changeFromId);
                hzChangeDataRecord.setTableName(ChangeTableNameEnum.HZ_CFG0_AFTER_CHANGE_RECORD.getTableName());
                int insertNum = hzChangeDataRecordDAO.insert(hzChangeDataRecord);
                if(insertNum<=0){
                    result.put("status", false);
                    result.put("msg", "绑定人员失败");
                    return result;
                }
            }
        } else {
            result.put("status", false);
            result.put("msg", "请至少选择1个特性值发起流程");
            return result;
        }
        return result;
    }

//    //配色方案进入VWO
//    public JSONObject getVWO(List<HzCfg0ModelColor> colors, String projectPuid, ArrayList<String> dynamicTitle, Long changeFromId) {
//        JSONObject result = new JSONObject();
//        User user = UserInfo.getUser();
//        //源主数据
//        List<HzCfg0ModelColor> hzCfg0ModelColors = hzCfg0ModelColorDao.selectByPuids(colors);
//        //变更后主数据
//        List<HzCmcrChange> hzCmcrChangesAfter = new ArrayList<HzCmcrChange>();
//        //变更后从数据
//        List<HzCmcrDetailChange> hzCmcrDetailChangesAfter = new ArrayList<HzCmcrDetailChange>();
//        //循环查看源主数据是否以发布流程,如已发布过则直接返回错误提示
//        for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
//            if (hzCfg0ModelColor.getCmcrStatus() != null && !"0".equals(hzCfg0ModelColor.getCmcrStatus())) {
//                result.put("status", false);
//                result.put("msg", hzCfg0ModelColor.getpDescOfColorfulModel() + "已发起了VWO流程");
//                return result;
//            }
//            hzCfg0ModelColor.setCmcrStatus("10");
//        }
//        //源从数据
//        List<HzCfg0ModelColorDetail> hzCfg0ModelColorDetails = hzColorModelDao.selectByModelColors(hzCfg0ModelColors);
//        //最新的Vwo实体类对象
//        HzVwoInfo hzVwoInfo = hzVwoManagerService.generateVwoEntity(user, projectPuid, result, 2);
//        hzVwoInfo.setVwoType(2);
//        //为源主数据添加VWO编码
//        for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
//            hzCfg0ModelColor.setCmcrVwoId(changeFromId);
//        }
//
//        //查询最近一次变更后主数据
//        List<HzCmcrChange> hzCmcrChangesLastAfterQuery = new ArrayList<HzCmcrChange>();
//        for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
//            HzCmcrChange hzCmcrChangeQuery = new HzCmcrChange();
//            hzCmcrChangeQuery.setCmcrSrcMainCfg(hzCfg0ModelColor.getpCfg0MainRecordOfMC());
//            hzCmcrChangeQuery.setCmcrSrcPuid(hzCfg0ModelColor.getPuid());
//            hzCmcrChangesLastAfterQuery.add(hzCmcrChangeQuery);
//        }
//        List<HzCmcrChange> hzCmcrChangesLastAfter = null;
//        try {
//            hzCmcrChangesLastAfter = hzCmcrChangeDao.selectLastAfter(hzCmcrChangesLastAfterQuery);
//        } catch (Exception e) {
//            result.put("status", false);
//            result.put("msg", e.getMessage());
//        }
//        //查询最近一次变更后从数据
//        List<HzCmcrDetailChange> hzCmcrDetailChangesQuery = new ArrayList<HzCmcrDetailChange>();
//        for (HzCfg0ModelColorDetail hzCfg0ModelColorDetail : hzCfg0ModelColorDetails) {
//            HzCmcrDetailChange hzCmcrDetailChange1Query = new HzCmcrDetailChange();
//            hzCmcrDetailChange1Query.setCmcrDetailSrcCfgMainUid(hzCfg0ModelColorDetail.getCfgMainUid());
//            hzCmcrDetailChange1Query.setCmcrDetailSrcPuid(hzCfg0ModelColorDetail.getPuid());
//            hzCmcrDetailChange1Query.setCmcrDetailSrcModelPuid(hzCfg0ModelColorDetail.getModelUid());
//
//            if (hzCfg0ModelColorDetail.getCfgUid() != null) {
//                HzCfg0OptionFamily hzCfg0OptionFamilyQuery = new HzCfg0OptionFamily();
//                hzCfg0OptionFamilyQuery.setPuid(hzCfg0ModelColorDetail.getCfgUid());
//                HzCfg0OptionFamily hzCfg0OptionFamily = hzCfg0OptionFamilyDao.selectByPrimaryKey(hzCfg0OptionFamilyQuery);
//                //特性代码
//                hzCmcrDetailChange1Query.setCmcrDetailCgFeatureCode(hzCfg0OptionFamily.getpOptionfamilyName());
//                //特性名
//                hzCmcrDetailChange1Query.setCmcrDetailCgFeatureName(hzCfg0OptionFamily.getpOptionfamilyDesc());
//            }
//
//            hzCmcrDetailChangesQuery.add(hzCmcrDetailChange1Query);
//        }
//        List<HzCmcrDetailChange> hzCmcrDetailChangesLastAfter = null;
//        try {
////            hzCmcrDetailChangesLastAfter = hzCmcrDetailChangeDao.selectLastAfter(hzCmcrDetailChangesQuery);
//            if(hzCmcrChangesLastAfter!=null&&hzCmcrChangesLastAfter.size()!=0){
//            hzCmcrDetailChangesLastAfter = hzCmcrDetailChangeDao.selectAfter(hzCmcrChangesLastAfter);
//
//            }
//        } catch (Exception e) {
//            result.put("status", false);
//            result.put("msg", e.getMessage());
//        }
//        //筛选出第一次变更的数据,并生成主数据
//        if (hzCmcrChangesLastAfter.size() != hzCmcrChangesLastAfterQuery.size()) {
//            for (HzCmcrChange hzCmcrChangeAfterQuery : hzCmcrChangesLastAfterQuery) {
//                boolean flag = false;
//                for (HzCmcrChange hzCmcrChange : hzCmcrChangesLastAfter) {
//                    if (hzCmcrChangeAfterQuery.getCmcrSrcPuid().equals(hzCmcrChange.getCmcrSrcPuid())) {
//                        flag = true;
//                        break;
//                    }
//                }
//                if (!flag) {
//                    HzCmcrChange hzCmcrChange = new HzCmcrChange();
//                    hzCmcrChange.setCmcrSrcPuid(hzCmcrChangeAfterQuery.getCmcrSrcPuid());
//                    hzCmcrChange.setCmcrSrcMainCfg(hzCmcrChangeAfterQuery.getCmcrSrcMainCfg());
//                    hzCmcrChange.setCmcrCgVwoId(changeFromId);
//                    //变更状态
//                    hzCmcrChange.setCmcrChangeStatus(1);
//                    hzCmcrChangesAfter.add(hzCmcrChange);
//                }
//            }
//        }
//        Map<String,Long> changeAfterId = new HashMap<>();
//        for(HzCmcrChange hzCmcrChange : hzCmcrChangesAfter){
//            changeAfterId.put(hzCmcrChange.getCmcrSrcPuid(),hzCmcrChange.getCmcrCgId());
//        }
//        //筛选出第一次变更的数据,并生成从数据
//        if (hzCmcrDetailChangesLastAfter.size() != hzCmcrDetailChangesQuery.size()) {
//            for (HzCmcrDetailChange hzCmcrDetailChangeQuery : hzCmcrDetailChangesQuery) {
//                boolean flag = false;
//                for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesLastAfter) {
//                    if (hzCmcrDetailChangeQuery.getCmcrDetailSrcPuid().equals(hzCmcrDetailChange.getCmcrDetailSrcPuid())) {
//                        flag = true;
//                        break;
//                    }
//                }
//                if (!flag) {
//                    HzCmcrDetailChange hzCmcrDetailChange = new HzCmcrDetailChange();
//                    //主数据ID
//                    hzCmcrDetailChange.setCmcrCgChangeId(changeAfterId.get(hzCmcrDetailChangeQuery.getCmcrDetailSrcModelPuid()));
//                    hzCmcrDetailChange.setCmcrDetailSrcPuid(hzCmcrDetailChangeQuery.getCmcrDetailSrcPuid());
//                    hzCmcrDetailChange.setCmcrDetailSrcCfgMainUid(hzCmcrDetailChangeQuery.getCmcrDetailSrcCfgMainUid());
//                    hzCmcrDetailChange.setCmcrDetailCgVwoId(changeFromId);
//                    hzCmcrDetailChange.setCmcrDetailSrcModelPuid(hzCmcrDetailChangeQuery.getCmcrDetailSrcModelPuid());
//                    hzCmcrDetailChange.setCmcrDetailCgFeatureCode(hzCmcrDetailChangeQuery.getCmcrDetailCgFeatureCode());
//                    hzCmcrDetailChange.setCmcrDetailCgFeatureName(hzCmcrDetailChangeQuery.getCmcrDetailCgFeatureName());
//                    hzCmcrDetailChange.setCmcrDetailCgTitle(hzCmcrDetailChange.getCmcrDetailCgFeatureName() + "<br>" + hzCmcrDetailChange.getCmcrDetailCgFeatureCode());
//                    if (dynamicTitle.contains(hzCmcrDetailChange.getCmcrDetailCgFeatureName() + "<br>" + hzCmcrDetailChange.getCmcrDetailCgFeatureCode())) {
//                        hzCmcrDetailChange.setCmcrDetailCgIsColorful(1);
//                    } else {
//                        hzCmcrDetailChange.setCmcrDetailCgIsColorful(0);
//                    }
//                    hzCmcrDetailChangesAfter.add(hzCmcrDetailChange);
//                }
//            }
//        }
////        //根据最近一次变更后主数据生成变更前主数据
////        for (HzCmcrChange hzCmcrChange : hzCmcrChangesLastAfter) {
////            hzCmcrChange.setCmcrCgVwoId(hzVwoInfo.getId());
////        }
////        //根据最近一次变更后从数据生成变更前从数据
////        for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesLastAfter) {
////            hzCmcrDetailChange.setCmcrDetailCgVwoId(hzVwoInfo.getId());
////        }
//
//
//        //根据源主数据生成变更后主数据
//
//        for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
//            HzCmcrChange hzCmcrChangeAfter = new HzCmcrChange();
//            //VWO号
//            hzCmcrChangeAfter.setCmcrCgVwoId(changeFromId);
//            //车身颜色代码
//            hzCmcrChangeAfter.setCmcrCgShellCode(hzCfg0ModelColor.getpModelShellOfColorfulModel());
//            //创建者
//            hzCmcrChangeAfter.setCmcrCgCreator(user.getLogin());
//            //主配置UID
//            hzCmcrChangeAfter.setCmcrSrcMainCfg(hzCfg0ModelColor.getpCfg0MainRecordOfMC());
//            //源数据UID
//            hzCmcrChangeAfter.setCmcrSrcPuid(hzCfg0ModelColor.getPuid());
//            //油漆车身总成
//            hzCmcrChangeAfter.setCmcrSrcModelShell(hzCfg0ModelColor.getpModelShellOfColorfulModel());
//            //颜色UID
//            hzCmcrChangeAfter.setCmcrSrcColorUid(hzCfg0ModelColor.getpColorUid());
//            //特性值的外键
//            hzCmcrChangeAfter.setCmcrSrcShellCfg0Puid(hzCfg0ModelColor.getpShellCfg0Puid());
//            //是否多色
//            hzCmcrChangeAfter.setCmcrSrcColorIsMultiply(hzCfg0ModelColor.getpColorIsMultiply());
//            //源创建时间
//            hzCmcrChangeAfter.setCmcrSrcCreateDate(hzCfg0ModelColor.getCmcrCreateDate());
//            //源创建人
//            hzCmcrChangeAfter.setCmcrSrcCreator(hzCfg0ModelColor.getCmcrCreator());
//            //源修改时间
//            hzCmcrChangeAfter.setCmcrSrcUpdateDate(hzCfg0ModelColor.getCmcrUpdateDate());
//            //源修改人
//            hzCmcrChangeAfter.setCmcrSrcUpdater(hzCfg0ModelColor.getCmcrUpdater());
//            //颜色车型代码
//            hzCmcrChangeAfter.setCmcrSrcCodeOfColorMod(hzCfg0ModelColor.getpCodeOfColorfulModel());
//            //颜色车型描述
//            hzCmcrChangeAfter.setCmcrSrcDescOfColorMod(hzCfg0ModelColor.getpDescOfColorfulModel());
//            //变更状态
//            hzCmcrChangeAfter.setCmcrChangeStatus(0);
//            hzCmcrChangesAfter.add(hzCmcrChangeAfter);
//        }
//
//        for(HzCmcrChange hzCmcrChange : hzCmcrChangesAfter){
//            changeAfterId.put(hzCmcrChange.getCmcrSrcPuid(),hzCmcrChange.getCmcrCgId());
//        }
//        //根据源从数据生成变更后从数据
//        for (HzCfg0ModelColorDetail hzCfg0ModelColorDetail : hzCfg0ModelColorDetails) {
//            HzCmcrDetailChange hzCmcrDetailChangeAfter = new HzCmcrDetailChange();
//            //主数据ID
//            hzCmcrDetailChangeAfter.setCmcrCgChangeId(changeAfterId.get(hzCfg0ModelColorDetail.getModelUid()));
//            //源数据PUID
//            hzCmcrDetailChangeAfter.setCmcrDetailSrcPuid(hzCfg0ModelColorDetail.getPuid());
//            //特性
//            hzCmcrDetailChangeAfter.setCmcrDetailSrcCfgUid(hzCfg0ModelColorDetail.getCfgUid());
//            //颜色外键
//            hzCmcrDetailChangeAfter.setCmcrDetailSrcColorUid(hzCfg0ModelColorDetail.getColorUid());
//            //主配置UID
//            hzCmcrDetailChangeAfter.setCmcrDetailSrcCfgMainUid(hzCfg0ModelColorDetail.getCfgMainUid());
//            //vwo主键
//            hzCmcrDetailChangeAfter.setCmcrDetailCgVwoId(changeFromId);
//            //源数据的创建时间
//            hzCmcrDetailChangeAfter.setCmcrDetailSrcCreateDate(hzCfg0ModelColorDetail.getCreateDate());
//            //源数据的修改时间
//            hzCmcrDetailChangeAfter.setCmcrDetailSrcModifyDate(hzCfg0ModelColorDetail.getModifyDate());
//            //源数据的创建者
//            hzCmcrDetailChangeAfter.setCmcrDetailCgCreator(hzCfg0ModelColorDetail.getCreator());
//            //源数据的修改者
//            hzCmcrDetailChangeAfter.setCmcrDetailCgUpdater(hzCfg0ModelColorDetail.getModifier());
//            //配色方案主数据PUID
//            hzCmcrDetailChangeAfter.setCmcrDetailSrcModelPuid(hzCfg0ModelColorDetail.getModelUid());
//            if (hzCfg0ModelColorDetail.getCfgUid() != null) {
//                HzCfg0OptionFamily hzCfg0OptionFamilyQuery = new HzCfg0OptionFamily();
//                hzCfg0OptionFamilyQuery.setPuid(hzCfg0ModelColorDetail.getCfgUid());
//                HzCfg0OptionFamily hzCfg0OptionFamily = hzCfg0OptionFamilyDao.selectByPrimaryKey(hzCfg0OptionFamilyQuery);
//                //特性代码
//                hzCmcrDetailChangeAfter.setCmcrDetailCgFeatureCode(hzCfg0OptionFamily.getpOptionfamilyName());
//                //特性名
//                hzCmcrDetailChangeAfter.setCmcrDetailCgFeatureName(hzCfg0OptionFamily.getpOptionfamilyDesc());
//
//                hzCmcrDetailChangeAfter.setCmcrDetailCgTitle(hzCmcrDetailChangeAfter.getCmcrDetailCgFeatureName() + "<br>" + hzCmcrDetailChangeAfter.getCmcrDetailCgFeatureCode());
//                if (dynamicTitle.contains(hzCmcrDetailChangeAfter.getCmcrDetailCgFeatureName() + "<br>" + hzCmcrDetailChangeAfter.getCmcrDetailCgFeatureCode())) {
//                    hzCmcrDetailChangeAfter.setCmcrDetailCgIsColorful(1);
//                } else {
//                    hzCmcrDetailChangeAfter.setCmcrDetailCgIsColorful(0);
//                }
//            }
//            //颜色代码
//            hzCmcrDetailChangeAfter.setCmcrDetailCgColorCode(hzCfg0ModelColorDetail.getpColorCode());
//            //颜色名称
//            hzCmcrDetailChangeAfter.setCmcrDetailCgColorName(hzCfg0ModelColorDetail.getpColorName());
//
//            hzCmcrDetailChangesAfter.add(hzCmcrDetailChangeAfter);
//        }
//
//
//        //跟新数据库
//        try {
//            //跟新源主数据
//            if (hzCfg0ModelColorDao.updateListData(hzCfg0ModelColors) <= 0) {
//                result.put("status", false);
//                result.put("msg", "变更源主数据失败");
//            }
//            //跟新变更后主数据
//            if (hzCmcrChangeDao.insertAfterList(hzCmcrChangesAfter) != hzCmcrChangesAfter.size()) {
//                result.put("status", false);
//                result.put("msg", "变更后主数据失败");
//            }
//            //跟新变更后从数据
//            if (hzCmcrDetailChangeDao.insertDetailAfterList(hzCmcrDetailChangesAfter) != hzCmcrDetailChangesAfter.size()) {
//                result.put("status", false);
//                result.put("msg", "变更后从数据失败");
//            }
////            //跟新变更前主数据
////            if (hzCmcrChangesLastAfter != null && hzCmcrChangesLastAfter.size() != 0) {
////                if (hzCmcrChangeDao.insertBeforeList(hzCmcrChangesLastAfter) != hzCmcrChangesLastAfter.size()) {
////                    result.put("status", false);
////                    result.put("msg", "跟变更前主数据失败");
////                }
////            }
////            //跟新变更前从数据
////            if (hzCmcrDetailChangesLastAfter != null && hzCmcrDetailChangesLastAfter.size() != 0) {
////                if (hzCmcrDetailChangeDao.insertDetailBeforeList(hzCmcrDetailChangesLastAfter) != hzCmcrDetailChangesLastAfter.size()) {
////                    result.put("status", false);
////                    result.put("msg", "跟变更前从数据失败");
////                }
////            }
//        } catch (Exception e) {
//            result.put("status", false);
//            result.put("msg", e.getMessage());
//        }
//        //新增VWO数据
//
//        if (result.get("status") == null) {
//            result.put("status", true);
//        }
//        return result;
//    }
//配色方案进入VWO
public JSONObject getVWO(List<HzCfg0ModelColor> colors, String projectPuid, ArrayList<String> dynamicTitle, Long changeFromId) {
    JSONObject result = new JSONObject();
    boolean firstFlag = false;

    User user = UserInfo.getUser();
    //源主数据
    List<HzCfg0ModelColor> hzCfg0ModelColors = hzCfg0ModelColorDao.selectByPuids(colors);
    //源从数据
    List<HzCfg0ModelColorDetail> hzCfg0ModelColorDetails = hzColorModelDao.selectByModelColors(hzCfg0ModelColors);
    //变更后主数据
    List<HzCmcrChange> hzCmcrChangesAfter = new ArrayList<HzCmcrChange>();
    //变更后从数据
    List<HzCmcrDetailChange> hzCmcrDetailChangesAfter = new ArrayList<HzCmcrDetailChange>();
    //循环查看源主数据是否以发布流程,如已发布过则直接返回错误提示
    for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
        if (hzCfg0ModelColor.getCmcrStatus() != null && !"0".equals(hzCfg0ModelColor.getCmcrStatus())&&!"2".equals(hzCfg0ModelColor.getCmcrStatus())) {
            result.put("status", false);
            result.put("msg", hzCfg0ModelColor.getpDescOfColorfulModel() + "已发起了VWO流程");
            return result;
        }

    }
    //为源主数据添加变更编码
    for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
        hzCfg0ModelColor.setCmcrVwoId(changeFromId);
    }
    //查询最近一次变更后主数据
    List<HzCmcrChange> hzCmcrChangesLastAfterQuery = new ArrayList<HzCmcrChange>();
    for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
        HzCmcrChange hzCmcrChangeQuery = new HzCmcrChange();
        hzCmcrChangeQuery.setCmcrSrcMainCfg(hzCfg0ModelColor.getpCfg0MainRecordOfMC());
        hzCmcrChangeQuery.setCmcrSrcPuid(hzCfg0ModelColor.getPuid());
        hzCmcrChangeQuery.setCmcrSrcStatus(Integer.valueOf(hzCfg0ModelColor.getCmcrStatus()));
        hzCmcrChangesLastAfterQuery.add(hzCmcrChangeQuery);
    }
    List<HzCmcrChange> hzCmcrChangesLastAfter = null;
    try {
        hzCmcrChangesLastAfter = hzCmcrChangeDao.selectLastAfter(hzCmcrChangesLastAfterQuery);
    } catch (Exception e) {
        result.put("status", false);
        result.put("msg", e.getMessage());
    }
    //根据源从数据生成变更后从数据
    List<HzCmcrDetailChange> hzCmcrDetailChangesQuery = new ArrayList<HzCmcrDetailChange>();
    for (HzCfg0ModelColorDetail hzCfg0ModelColorDetail : hzCfg0ModelColorDetails) {
        HzCmcrDetailChange hzCmcrDetailChange1Query = new HzCmcrDetailChange();
        hzCmcrDetailChange1Query.setCmcrDetailSrcCfgMainUid(hzCfg0ModelColorDetail.getCfgMainUid());
        hzCmcrDetailChange1Query.setCmcrDetailSrcPuid(hzCfg0ModelColorDetail.getPuid());
        hzCmcrDetailChange1Query.setCmcrDetailSrcModelPuid(hzCfg0ModelColorDetail.getModelUid());
        hzCmcrDetailChange1Query.setCmcrDetailSrcColorUid(hzCfg0ModelColorDetail.getColorUid());
        if (hzCfg0ModelColorDetail.getCfgUid() != null) {
            HzCfg0OptionFamily hzCfg0OptionFamilyQuery = new HzCfg0OptionFamily();
            hzCfg0OptionFamilyQuery.setPuid(hzCfg0ModelColorDetail.getCfgUid());
            HzCfg0OptionFamily hzCfg0OptionFamily = hzCfg0OptionFamilyDao.selectByPrimaryKey(hzCfg0OptionFamilyQuery);
            //特性代码
            hzCmcrDetailChange1Query.setCmcrDetailCgFeatureCode(hzCfg0OptionFamily.getpOptionfamilyName());
            //特性名
            hzCmcrDetailChange1Query.setCmcrDetailCgFeatureName(hzCfg0OptionFamily.getpOptionfamilyDesc());
        }

        hzCmcrDetailChangesQuery.add(hzCmcrDetailChange1Query);
    }
    //筛选出第一次变更的数据,并生成主从数据
//    if (hzCmcrChangesLastAfter.size() != hzCmcrChangesLastAfterQuery.size()) {
//        firstFlag = true;
//        for (HzCmcrChange hzCmcrChangeAfterQuery : hzCmcrChangesLastAfterQuery) {
//            boolean flag = false;
//            for (HzCmcrChange hzCmcrChange : hzCmcrChangesLastAfter) {
//                if (hzCmcrChangeAfterQuery.getCmcrSrcPuid().equals(hzCmcrChange.getCmcrSrcPuid())) {
//                    flag = true;
//                    break;
//                }
//            }
//            if (!flag) {
//                HzCmcrChange hzCmcrChange = new HzCmcrChange();
//                hzCmcrChange.setCmcrSrcPuid(hzCmcrChangeAfterQuery.getCmcrSrcPuid());
//                hzCmcrChange.setCmcrSrcMainCfg(hzCmcrChangeAfterQuery.getCmcrSrcMainCfg());
//                hzCmcrChange.setCmcrCgVwoId(changeFromId);
//                //变更状态
//                hzCmcrChange.setCmcrChangeStatus(1);
//                hzCmcrChange.setCmcrSrcStatus(hzCmcrChangeAfterQuery.getCmcrSrcStatus());
//                hzCmcrChangesAfter.add(hzCmcrChange);
//
//                //生成变更从数据
//                for (HzCmcrDetailChange hzCmcrDetailChangeQuery : hzCmcrDetailChangesQuery) {
//                    HzCmcrDetailChange hzCmcrDetailChange = new HzCmcrDetailChange();
//                    hzCmcrDetailChange.setCmcrDetailSrcPuid(hzCmcrDetailChangeQuery.getCmcrDetailSrcPuid());
//                    hzCmcrDetailChange.setCmcrDetailSrcCfgMainUid(hzCmcrDetailChangeQuery.getCmcrDetailSrcCfgMainUid());
//                    hzCmcrDetailChange.setCmcrDetailCgVwoId(changeFromId);
//                    hzCmcrDetailChange.setCmcrDetailSrcModelPuid(hzCmcrDetailChangeQuery.getCmcrDetailSrcModelPuid());
//                    hzCmcrDetailChange.setCmcrDetailCgFeatureCode(hzCmcrDetailChangeQuery.getCmcrDetailCgFeatureCode());
//                    hzCmcrDetailChange.setCmcrDetailCgFeatureName(hzCmcrDetailChangeQuery.getCmcrDetailCgFeatureName());
//                    hzCmcrDetailChange.setCmcrDetailSrcColorUid(hzCmcrDetailChangeQuery.getCmcrDetailSrcColorUid());
//                    hzCmcrDetailChange.setCmcrDetailCgTitle(hzCmcrDetailChange.getCmcrDetailCgFeatureName() + "<br>" + hzCmcrDetailChange.getCmcrDetailCgFeatureCode());
//                    if (dynamicTitle.contains(hzCmcrDetailChange.getCmcrDetailCgFeatureName() + "<br>" + hzCmcrDetailChange.getCmcrDetailCgFeatureCode())) {
//                        hzCmcrDetailChange.setCmcrDetailCgIsColorful(1);
//                    } else {
//                        hzCmcrDetailChange.setCmcrDetailCgIsColorful(0);
//                    }
//                    hzCmcrDetailChangesAfter.add(hzCmcrDetailChange);
//                }
//            }
//        }
//    }
    //根据源主数据生成变更后主数据
    for (HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors) {
        HzCmcrChange hzCmcrChangeAfter = new HzCmcrChange();
        //VWO号
        hzCmcrChangeAfter.setCmcrCgVwoId(changeFromId);
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
        //变更状态
        hzCmcrChangeAfter.setCmcrChangeStatus(0);
        hzCmcrChangeAfter.setCmcrSrcStatus(Integer.valueOf(hzCfg0ModelColor.getCmcrStatus()));
        hzCfg0ModelColor.setCmcrStatus("10");
        hzCmcrChangesAfter.add(hzCmcrChangeAfter);
    }
    Map<String,Long> changeAfterId = new HashMap<>();
    for(HzCmcrChange hzCmcrChange : hzCmcrChangesAfter){
        if(hzCmcrChange.getCmcrChangeStatus()!=1) {
            changeAfterId.put(hzCmcrChange.getCmcrSrcPuid(), hzCmcrChange.getCmcrCgId());
        }
    }
    //根据源从数据生成变更后从数据
    for (HzCfg0ModelColorDetail hzCfg0ModelColorDetail : hzCfg0ModelColorDetails) {
        HzCmcrDetailChange hzCmcrDetailChangeAfter = new HzCmcrDetailChange();
        //主数据ID
        hzCmcrDetailChangeAfter.setCmcrCgChangeId(changeAfterId.get(hzCfg0ModelColorDetail.getModelUid()));
        //源数据PUID
        hzCmcrDetailChangeAfter.setCmcrDetailSrcPuid(hzCfg0ModelColorDetail.getPuid());
        //特性
        hzCmcrDetailChangeAfter.setCmcrDetailSrcCfgUid(hzCfg0ModelColorDetail.getCfgUid());
        //颜色外键
        hzCmcrDetailChangeAfter.setCmcrDetailSrcColorUid(hzCfg0ModelColorDetail.getColorUid());
        //主配置UID
        hzCmcrDetailChangeAfter.setCmcrDetailSrcCfgMainUid(hzCfg0ModelColorDetail.getCfgMainUid());
        //vwo主键
        hzCmcrDetailChangeAfter.setCmcrDetailCgVwoId(changeFromId);
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

            hzCmcrDetailChangeAfter.setCmcrDetailCgTitle(hzCmcrDetailChangeAfter.getCmcrDetailCgFeatureName() + "<br>" + hzCmcrDetailChangeAfter.getCmcrDetailCgFeatureCode());
            if (dynamicTitle.contains(hzCmcrDetailChangeAfter.getCmcrDetailCgFeatureName() + "<br>" + hzCmcrDetailChangeAfter.getCmcrDetailCgFeatureCode())) {
                hzCmcrDetailChangeAfter.setCmcrDetailCgIsColorful(1);
            } else {
                hzCmcrDetailChangeAfter.setCmcrDetailCgIsColorful(0);
            }
        }
        //颜色代码
        hzCmcrDetailChangeAfter.setCmcrDetailCgColorCode(hzCfg0ModelColorDetail.getpColorCode());
        //颜色名称
        hzCmcrDetailChangeAfter.setCmcrDetailCgColorName(hzCfg0ModelColorDetail.getpColorName());

        hzCmcrDetailChangesAfter.add(hzCmcrDetailChangeAfter);
    }


    //跟新数据库
    try {
        //跟新源主数据
        if (hzCfg0ModelColorDao.updateListData(hzCfg0ModelColors) <= 0) {
            result.put("status", false);
            result.put("msg", "变更源主数据失败");
        }
        //跟新变更后主数据
        if (hzCmcrChangeDao.insertAfterList(hzCmcrChangesAfter) != hzCmcrChangesAfter.size()) {
            result.put("status", false);
            result.put("msg", "变更后主数据失败");
        }
        //为变更后从数据加入变更主数据ID
        List<HzCmcrChange> hzCmcrChanges = new ArrayList<>();
        if(firstFlag){
            List<HzCmcrChange> hzCmcrChangesBefor2= hzCmcrChangeDao.doQueryCmcrChangeBefor(changeFromId);
            hzCmcrChanges.addAll(hzCmcrChangesBefor2);
        }
        List<HzCmcrChange> hzCmcrChangesAfter2 = hzCmcrChangeDao.doQueryCmcrChangeAfter(changeFromId);
        hzCmcrChanges.addAll(hzCmcrChangesAfter2);
        for(HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesAfter){
            for(HzCmcrChange hzCmcrChange :hzCmcrChanges){
                if(hzCmcrChange.getCmcrSrcPuid().equals(hzCmcrDetailChange.getCmcrDetailSrcModelPuid())&&hzCmcrChange.getCmcrChangeStatus()==0&&hzCmcrDetailChange.getCmcrDetailSrcCfgUid()!=null){
                        hzCmcrDetailChange.setCmcrCgChangeId(hzCmcrChange.getCmcrCgId());
                        break;
                }else if(hzCmcrChange.getCmcrSrcPuid().equals(hzCmcrDetailChange.getCmcrDetailSrcModelPuid())&&hzCmcrChange.getCmcrChangeStatus()==1&&hzCmcrDetailChange.getCmcrDetailSrcCfgUid()==null){
                        hzCmcrDetailChange.setCmcrCgChangeId(hzCmcrChange.getCmcrCgId());
                        break;
                }
            }
        }
        //跟新变更后从数据
        if (hzCmcrDetailChangeDao.insertDetailAfterList(hzCmcrDetailChangesAfter) != hzCmcrDetailChangesAfter.size()) {
            result.put("status", false);
            result.put("msg", "变更后从数据失败");
        }

        //流程绑定人员
        HzChangeDataRecord hzChangeDataRecord = new HzChangeDataRecord();
        hzChangeDataRecord.setApplicantId(Long.valueOf(user.getId()));
        hzChangeDataRecord.setOrderId(changeFromId);
        hzChangeDataRecord.setTableName(ChangeTableNameEnum.HZ_CMCR_AFTER_CHANGE.getTableName());
        int insertNum = hzChangeDataRecordDAO.insert(hzChangeDataRecord);
        if(insertNum<=0){
            result.put("status", false);
            result.put("msg", "绑定人员失败");
            return result;
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
            HzVwoInfo info = hzVwoInfoService.doSelectByPrimaryKey(id);
            HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(info.getProjectUid());
            User user = UserInfo.getUser();
            hzVwoOpiProj = new HzVwoOpiProj();
            hzVwoOpiProj.setOpiVwoId(id);
            Date date = new Date();
            hzVwoOpiProj.setOpiProjMngCreateDate(date);
            hzVwoOpiProj.setOpiProjMngCreator(user.getLogin());
            hzVwoOpiProj.setOpiProjMngUpdateDate(date);
            hzVwoOpiProj.setOpiProjMngUpdater(user.getLogin());
            hzVwoOpiProj.setOpiProjMngUserName(project.getpProjectManager());
            hzVwoOpiProj.setOpiProjMngUserId(project.getProjectManagerId());
            if (hzVwoOpiProjDao.insertSelective(hzVwoOpiProj) <= 0) {
                logger.error("初始化VWO:" + id + "的项目经理意见数据失败");
            }
        }

        if (hzVwoOpiProj.getOpiProjMngUserId() == null || !checkString(hzVwoOpiProj.getOpiProjMngUserName())) {
            HzVwoInfo info = hzVwoInfoService.doSelectByPrimaryKey(id);
            HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(info.getProjectUid());
            HzVwoOpiProj proj = new HzVwoOpiProj();
            proj.setId(hzVwoOpiProj.getId());
            proj.setOpiProjMngUserName(project.getpProjectManager());
            proj.setOpiProjMngUserId(project.getProjectManagerId());
            hzVwoOpiProj.setOpiProjMngUserName(project.getpProjectManager());
            hzVwoOpiProj.setOpiProjMngUserId(project.getProjectManagerId());
            if (hzVwoOpiProjDao.updateByPrimaryKeySelective(proj) <= 0) {
                logger.error("自动指派VWO:" + id + "的项目经理失败");
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
        if (hzVwoOpiBom.getOpiBomMngAgreement() != 1) {
            if (hzTasksService.doStopTask(TaskOptions.FORM_TYPE_VWO, vwoType, hzVwoInfo.getId())) {
                logger.error("终止任务失败");
            }
        } else {
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
        if (hzVwoOpiPmt.getOpiPmtMngAgreement() != 1) {
            if (hzTasksService.doStopTask(TaskOptions.FORM_TYPE_VWO, vwoType, hzVwoInfo.getId())) {
                logger.error("终止任务失败");
            }
        } else {
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
        if (hzVwoOpiProj.getOpiProjMngAgreement() != 1) {
            if (hzTasksService.doStopTask(TaskOptions.FORM_TYPE_VWO, vwoType, hzVwoInfo.getId())) {
                logger.error("终止任务失败");
            }
        } else {
            List<HzTasks> tasks = hzTasksService.doSelectUserTargetTaskByType(TaskOptions.FORM_TYPE_VWO, vwoType, hzVwoInfo.getId(), Long.valueOf(user.getId()), 1);
            if (tasks != null && tasks.size() == 1) {
                HzTasks task = tasks.get(0);
                task.setTaskStatus(999);
                if (!hzTasksService.doUpdateByPrimaryKeySelective(task)) {
                    logger.error("更新项目经理经理完成任务失败");
                }
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
        if (managerId == null || hzVwoOpiProj.getOpiProjMngUserId() == null || managerId != hzVwoOpiProj.getOpiProjMngUserId()) {
            result.put("msg", "请在主页项目中指派项目经理");
            result.put("status", false);
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
                List<HzFeatureChangeBean> bs = iHzFeatureChangeService.doQueryLastTwoChange(beans.get(i).getCfgPuid(), vwoId);
                if(bs==null||bs.size()<=0||bs.get(1)==null){
                    continue;
                }
                if(bs.get(0)==null){
                    bs.get(1).setHeadDesc("新增<br>"+bs.get(1).getFeatureValueName());
                    HzVwoFeatureTableDto after = new HzVwoFeatureTableDto(bs.get(1));
                    list.add(after);
                }else if(bs.get(1).getCfgStatus()==2){
                    bs.get(1).setHeadDesc("删除<br>"+bs.get(1).getFeatureValueName());
                    HzVwoFeatureTableDto before = new HzVwoFeatureTableDto(bs.get(1));
                    list.add(before);
                }else {
                    bs.get(0).setHeadDesc("变更前<br>"+ bs.get(1).getFeatureValueName());
                    bs.get(1).setHeadDesc("变更后<br>"+ bs.get(1).getFeatureValueName());
                    HzVwoFeatureTableDto before = new HzVwoFeatureTableDto(bs.get(0));
                    HzVwoFeatureTableDto after = new HzVwoFeatureTableDto(bs.get(1));
                    list.add(before);
                    list.add(after);
                }
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

//                if (!hzTasksService.doInsert(taskOfBom) || !hzTasksService.doInsert(taskOfPmt) || !hzTasksService.doInsert(taskOfProj)) {
//                    logger.error("通知任务失败");
//                }
                list.add(taskOfBom);
                list.add(taskOfPmt);
                list.add(taskOfProj);
//                这里批量插入插入只有2个，需要改进
                if (!hzTasksService.doInsertByBatch(list)) {
                    logger.error("通知任务失败");
                }

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }


        }
    }

    /**
     * 查询变更前后主数据
     *
     * @param cmcrSrcPuid
     * @param cmcrCgVwoId
     * @return
     */
    public List<HzCmcrChange> doQueryCmcrChangeBeforAndAfter(String cmcrSrcPuid, Long cmcrCgVwoId) {
        HzCmcrChange hzCmcrChange = new HzCmcrChange();
        hzCmcrChange.setCmcrSrcPuid(cmcrSrcPuid);
        hzCmcrChange.setCmcrCgVwoId(cmcrCgVwoId);
        return hzCmcrChangeDao.doQueryCmcrDetailChangeBeforAndAfter(hzCmcrChange);
    }

//    /**
//     * 查询变更前后从数据
//     *
//     * @param cmcrDetailSrcPuidList
//     * @param cmcrCgVwoId
//     * @return
//     */
//    public List<HzCmcrDetailChange> doQueryCmcrDetailChangBeforAndAfter(List<String> cmcrDetailSrcPuidList, Long cmcrCgVwoId) {
//        List<HzCmcrDetailChange> hzCmcrDetailChanges = new ArrayList<HzCmcrDetailChange>();
//        for (String cmcrDetailSrcPuid : cmcrDetailSrcPuidList) {
//            HzCmcrDetailChange hzCmcrDetailChange = new HzCmcrDetailChange();
//            hzCmcrDetailChange.setCmcrDetailSrcPuid(cmcrDetailSrcPuid);
//            hzCmcrDetailChange.setCmcrDetailCgVwoId(cmcrCgVwoId);
//            hzCmcrDetailChanges.add(hzCmcrDetailChange);
//        }
//        return hzCmcrDetailChangeDao.doQueryCmcrDetailChangBeforAndAfter(hzCmcrDetailChanges);
//    }
//
//    public void doQueryCmcrDetailChangBefor(Map<String, Object> map, Long vwoId) {
//        //无序变更前后数据
//        List<HzCmcrDetailChange> hzCmcrDetailChangeListBefor = new ArrayList<>();
//        List<HzCmcrDetailChange> hzCmcrDetailChangeListAfter = new ArrayList<HzCmcrDetailChange>();
//        Set<String> titleSet = new LinkedHashSet<>();
//
//
//        List<HzCmcrChange> hzCmcrChangeListBefor = null;
//        List<HzCmcrChange> hzCmcrChangeListAfter = null;
//        //查询变更前主数据（vwo号小于变更后的vwo号）
//        hzCmcrChangeListBefor = hzCmcrChangeDao.doQueryCmcrChangeBefor(vwoId);
//        if (hzCmcrChangeListBefor == null || hzCmcrChangeListBefor.size() == 0) {
////            //查询变更前主数据
////            hzCmcrChangeListBefor = hzCmcrChangeDao.doQueryCmcrChangeBeforFirst(vwoId);
////            //查询变更后主数据
////            hzCmcrChangeListAfter = hzCmcrChangeDao.doQueryCmcrChangeAfterFirst(vwoId);
//            return;
//        } else {
//            //查询变更后主数据
//            hzCmcrChangeListAfter = hzCmcrChangeDao.doQueryCmcrChangeAfter(vwoId);
//        }
//
//        for(int i=0;i<hzCmcrChangeListBefor.size();i++) {
//            List<HzCmcrDetailChange> beforChange = new ArrayList<>();
//            List<HzCmcrDetailChange> afterChange = new ArrayList<HzCmcrDetailChange>();
//            //查询变更前从数据（vwo号小于变更后的vwo号）
//            beforChange = hzCmcrDetailChangeDao.doQueryCmcrDetailChangBefor(hzCmcrChangeListBefor.get(i));
//            //查不到则是第一次变更，vwo号相同
//            if (beforChange == null || beforChange.size() == 0) {
//                //查询变更前从数据
//                beforChange = hzCmcrDetailChangeDao.doQueryCmcrDetailChangFirst(hzCmcrChangeListBefor.get(i));
//                //查询变更后从数据
//                afterChange = hzCmcrDetailChangeDao.doQueryCmcrDetailChangFirstAfter(hzCmcrChangeListAfter.get(i));
//            } else {
//                //查询变更后从数据
//                afterChange = hzCmcrDetailChangeDao.doQueryCmcrDetailChangAfter(hzCmcrChangeListAfter.get(i));
//            }
//            for(HzCmcrDetailChange hzCmcrDetailChange : beforChange){
//                hzCmcrDetailChangeListBefor.add(hzCmcrDetailChange);
//            }
//            for(HzCmcrDetailChange  hzCmcrDetailChange : afterChange){
//                hzCmcrDetailChangeListAfter.add(hzCmcrDetailChange);
//            }
//        }
//        //srcPuid为key的变更前从数据
//        Map<String, List<HzCmcrDetailChange>> srcPuidBeforMap = new HashMap<String, List<HzCmcrDetailChange>>();
//        //srcPuid为key的变更后从数据
//        Map<String, List<HzCmcrDetailChange>> srcPuidAfterMap = new HashMap<String, List<HzCmcrDetailChange>>();
//
//        for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangeListBefor) {
//            //将变更前所有title放入set
//            if(hzCmcrDetailChange.getCmcrDetailCgIsColorful()==null||hzCmcrDetailChange.getCmcrDetailCgIsColorful()==0) {
//                continue;
//            }
//            titleSet.add(hzCmcrDetailChange.getCmcrDetailCgTitle());
//            //根据源主数据将变更前从数据分开
//            if (srcPuidBeforMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()) == null) {
//                srcPuidBeforMap.put(hzCmcrDetailChange.getCmcrDetailSrcModelPuid(), new ArrayList<HzCmcrDetailChange>());
//            }
//            srcPuidBeforMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()).add(hzCmcrDetailChange);
//        }
//        for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangeListAfter) {
//            //将变更后所有title放入set
//            if(hzCmcrDetailChange.getCmcrDetailCgIsColorful()==null||hzCmcrDetailChange.getCmcrDetailCgIsColorful()==0) {
//                continue;
//            }
//            titleSet.add(hzCmcrDetailChange.getCmcrDetailCgTitle());
//            //根据源主数据将变更后从数据分开
//            if (srcPuidAfterMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()) == null) {
//                srcPuidAfterMap.put(hzCmcrDetailChange.getCmcrDetailSrcModelPuid(), new ArrayList<HzCmcrDetailChange>());
//            }
//            srcPuidAfterMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()).add(hzCmcrDetailChange);
//        }
//
//
//        //titel数据Map
////        List<Map<String, String>> result = new ArrayList<>();
//
////        Map<String, List<String>> titleMap = new LinkedHashMap<>();
//
////        titleMap.put("codeOfColorModel", new ArrayList<String>());
////        titleMap.put("descOfColorModel", new ArrayList<String>());
////        titleMap.put("modelShell", new ArrayList<String>());
////        for (int i = 0; i < hzCmcrChangeListBefor.size(); i++) {
////            titleMap.get("codeOfColorModel").add(hzCmcrChangeListBefor.get(i).getCmcrSrcCodeOfColorMod() == null ? "-" : hzCmcrChangeListBefor.get(i).getCmcrSrcCodeOfColorMod());
////            titleMap.get("descOfColorModel").add(hzCmcrChangeListBefor.get(i).getCmcrSrcDescOfColorMod() == null ? "-" : hzCmcrChangeListBefor.get(i).getCmcrSrcDescOfColorMod());
////            titleMap.get("modelShell").add(hzCmcrChangeListBefor.get(i).getCmcrCgShellCode() == null ? "-" : hzCmcrChangeListBefor.get(i).getCmcrCgShellCode());
////            titleMap.get("codeOfColorModel").add(hzCmcrChangeListAfter.get(i).getCmcrSrcCodeOfColorMod() == null ? "-" : hzCmcrChangeListAfter.get(i).getCmcrSrcCodeOfColorMod());
////            titleMap.get("descOfColorModel").add(hzCmcrChangeListAfter.get(i).getCmcrSrcDescOfColorMod() == null ? "-" : hzCmcrChangeListAfter.get(i).getCmcrSrcDescOfColorMod());
////            titleMap.get("modelShell").add(hzCmcrChangeListAfter.get(i).getCmcrCgShellCode() == null ? "-" : hzCmcrChangeListAfter.get(i).getCmcrCgShellCode());
////        }
////
////        Iterator<String> iterator = titleSet.iterator();
////        int titleNum = 0;
////        while (iterator.hasNext()) {
////            String titleKey = "s" + titleNum;
////            titleNum++;
////            String title = iterator.next();
////            titleMap.put(titleKey, new ArrayList<String>());
////            for (int j = 0; j < hzCmcrChangeListAfter.size(); j++) {
////                String srcPuid = hzCmcrChangeListBefor.get(j).getCmcrSrcPuid();
////                List<HzCmcrDetailChange> hzCmcrDetailChangesBefor = srcPuidBeforMap.get(srcPuid);
////                boolean beforTitleFlag = false;
////                for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesBefor) {
////                    if (title.equals(hzCmcrDetailChange.getCmcrDetailCgTitle())) {
////                        titleMap.get(titleKey).add(hzCmcrDetailChange.getColorCode() == null ? "-" : hzCmcrDetailChange.getColorCode());
////                        beforTitleFlag = true;
////                        break;
////                    }
////                }
////                if (!beforTitleFlag) {
////                    titleMap.get(titleKey).add("-");
////                }
////                boolean afterTitleFlag = false;
////                List<HzCmcrDetailChange> hzCmcrDetailChangesAfter = srcPuidAfterMap.get(srcPuid);
////                for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesAfter) {
////                    if (title.equals(hzCmcrDetailChange.getCmcrDetailCgTitle())) {
////                        titleMap.get(titleKey).add(hzCmcrDetailChange.getColorCode());
////                        afterTitleFlag = true;
////                        break;
////                    }
////                }
////                if (!afterTitleFlag) {
////                    titleMap.get(titleKey).add("-");
////                }
////            }
////        }
//
//        List<Map<String,String>> result = new ArrayList<>();
//        for(int i=0;i<hzCmcrChangeListAfter.size();i++){
//            //变更前行
//            Map<String,String> beforMap = new HashMap<>();
//            //变更后行
//            Map<String,String> afterMap = new HashMap<>();
//            //变更前主数据
//            beforMap.put("codeOfColorModel",hzCmcrChangeListBefor.get(i).getCmcrSrcCodeOfColorMod() == null ? "" : hzCmcrChangeListBefor.get(i).getCmcrSrcCodeOfColorMod());
//            beforMap.put("descOfColorModel",hzCmcrChangeListBefor.get(i).getCmcrSrcDescOfColorMod() == null ? "" : hzCmcrChangeListBefor.get(i).getCmcrSrcDescOfColorMod());
//            beforMap.put("modelShell",hzCmcrChangeListBefor.get(i).getCmcrCgShellCode() == null ? "" : hzCmcrChangeListBefor.get(i).getCmcrCgShellCode());
//            //变更后主数据
//            afterMap.put("codeOfColorModel",hzCmcrChangeListAfter.get(i).getCmcrSrcCodeOfColorMod() == null ? "-" : hzCmcrChangeListAfter.get(i).getCmcrSrcCodeOfColorMod());
//            afterMap.put("descOfColorModel",hzCmcrChangeListAfter.get(i).getCmcrSrcDescOfColorMod() == null ? "-" : hzCmcrChangeListAfter.get(i).getCmcrSrcDescOfColorMod());
//            afterMap.put("modelShell",hzCmcrChangeListAfter.get(i).getCmcrCgShellCode() == null ? "-" : hzCmcrChangeListAfter.get(i).getCmcrCgShellCode());
//            //根据变更前主数据id从srcPuidBeforMap中获取变更前从数据
//            String srcPuid = hzCmcrChangeListAfter.get(i).getCmcrSrcPuid();
//            List<HzCmcrDetailChange> hzCmcrDetailChangesBefor = srcPuidBeforMap.get(srcPuid);
//            List<HzCmcrDetailChange> hzCmcrDetailChangesAfter = srcPuidAfterMap.get(srcPuid);
//
//            Iterator<String> iterator = titleSet.iterator();
//            int titleNum = 0;
//            while (iterator.hasNext()){
//                String title = iterator.next();
//                boolean beforTitleFlag = false;
//                for(HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesBefor ){
//                    if (title.equals(hzCmcrDetailChange.getCmcrDetailCgTitle())) {
//                        beforMap.put("s" + titleNum, hzCmcrDetailChange.getColorCode() == null ? "-" : hzCmcrDetailChange.getColorCode());
//                        beforTitleFlag = true;
//                        break;
//                    }
//                }
//                if(!beforTitleFlag){
//                    beforMap.put("s" + titleNum,"-");
//                }
//
//                boolean afterTitleFlag = false;
//                for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesAfter) {
//                    if (title.equals(hzCmcrDetailChange.getCmcrDetailCgTitle())) {
//                        afterMap.put("s" + titleNum, hzCmcrDetailChange.getColorCode() == null ? "-" : hzCmcrDetailChange.getColorCode());
//                        afterTitleFlag = true;
//                        break;
//                    }
//                }
//                if(!afterTitleFlag){
//                    afterMap.put("s" + titleNum,"-");
//                }
//                titleNum++;
//            }
//            result.add(beforMap);
//            result.add(afterMap);
//        }
//        map.put("titleSet", titleSet);
//        map.put("result", result);
//    }

    /**
     * 查询变更前后从数据
     *
     * @param cmcrDetailSrcPuidList
     * @param cmcrCgVwoId
     * @return
     */
    public List<HzCmcrDetailChange> doQueryCmcrDetailChangBeforAndAfter(List<String> cmcrDetailSrcPuidList, Long cmcrCgVwoId) {
        List<HzCmcrDetailChange> hzCmcrDetailChanges = new ArrayList<HzCmcrDetailChange>();
        for (String cmcrDetailSrcPuid : cmcrDetailSrcPuidList) {
            HzCmcrDetailChange hzCmcrDetailChange = new HzCmcrDetailChange();
            hzCmcrDetailChange.setCmcrDetailSrcPuid(cmcrDetailSrcPuid);
            hzCmcrDetailChange.setCmcrDetailCgVwoId(cmcrCgVwoId);
            hzCmcrDetailChanges.add(hzCmcrDetailChange);
        }
        return hzCmcrDetailChangeDao.doQueryCmcrDetailChangBeforAndAfter(hzCmcrDetailChanges);
    }


    @Override
    public void doQueryCmcrDetailChangBefor2(Map<String, Object> map, Long vwoId){
        List<Map<String,String>> result = new ArrayList<>();
        //变更前后主数据
        List<HzCmcrChange> hzCmcrChangeListBefor = new ArrayList<>();
        List<HzCmcrChange> hzCmcrChangeListAfter = new ArrayList<>();
        //变更前后从数据
        List<HzCmcrDetailChange> hzCmcrDetailChangeListBefor = new ArrayList<>();
        List<HzCmcrDetailChange> hzCmcrDetailChangeListAfter = new ArrayList<HzCmcrDetailChange>();
        //列名SET
        Set<String> titleSet = new LinkedHashSet<>();

        //srcPuid为key的变更前从数据
        Map<String, List<HzCmcrDetailChange>> srcPuidBeforMap = new HashMap<String, List<HzCmcrDetailChange>>();
        //srcPuid为key的变更后从数据
        Map<String, List<HzCmcrDetailChange>> srcPuidAfterMap = new HashMap<String, List<HzCmcrDetailChange>>();


        //查询变更后主数据
        hzCmcrChangeListAfter = hzCmcrChangeDao.doQueryCmcrChangeAfter(vwoId);
        //查询变更前主数据（vwo号小于变更后的vwo号）
        if(hzCmcrChangeListAfter!=null){
            for(HzCmcrChange hzCmcrChange : hzCmcrChangeListAfter){
                HzCmcrChange  hzCmcrChange1 = hzCmcrChangeDao.doQueryCmcrChangeBeforByAfter(hzCmcrChange);
                if(hzCmcrChange1!=null){
                    hzCmcrChangeListBefor.add(hzCmcrChange1);
                }
            }
        }

        //查询变更前后从数据
        if(hzCmcrChangeListBefor==null||hzCmcrChangeListBefor.size()<=0){
            if(hzCmcrChangeListAfter==null||hzCmcrChangeListAfter.size()<=0){
                return;
            }else {
                hzCmcrDetailChangeListAfter = hzCmcrDetailChangeDao.doQueryCmcrDetailByMainChange(hzCmcrChangeListAfter);
            }
        }else {
            hzCmcrDetailChangeListBefor = hzCmcrDetailChangeDao.doQueryCmcrDetailByMainChange(hzCmcrChangeListBefor);
            hzCmcrDetailChangeListAfter = hzCmcrDetailChangeDao.doQueryCmcrDetailByMainChange(hzCmcrChangeListAfter);
        }


        for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangeListBefor) {
            //将变更前所有title放入set
            if(hzCmcrDetailChange.getCmcrDetailCgIsColorful()==null||hzCmcrDetailChange.getCmcrDetailCgIsColorful()==0) {
                continue;
            }
            titleSet.add(hzCmcrDetailChange.getCmcrDetailCgTitle());
            //根据源主数据将变更前从数据分开
            if (srcPuidBeforMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()) == null) {
                srcPuidBeforMap.put(hzCmcrDetailChange.getCmcrDetailSrcModelPuid(), new ArrayList<HzCmcrDetailChange>());
            }
            srcPuidBeforMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()).add(hzCmcrDetailChange);
        }

        for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangeListAfter) {
            //将变更后所有title放入set
            if(hzCmcrDetailChange.getCmcrDetailCgIsColorful()==null||hzCmcrDetailChange.getCmcrDetailCgIsColorful()==0) {
                continue;
            }
            titleSet.add(hzCmcrDetailChange.getCmcrDetailCgTitle());
            //根据源主数据将变更后从数据分开
            if (srcPuidAfterMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()) == null) {
                srcPuidAfterMap.put(hzCmcrDetailChange.getCmcrDetailSrcModelPuid(), new ArrayList<HzCmcrDetailChange>());
            }
            srcPuidAfterMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()).add(hzCmcrDetailChange);
        }

        List<HzCmcrChange> hzCmcrChangesUpdate = new ArrayList<>();
        List<HzCmcrChange> hzCmcrChangesDelete = new ArrayList<>();
        List<HzCmcrChange> hzCmcrChangesAdd = new ArrayList<>();


        //获取所有删除状态的变更数据
        Iterator<HzCmcrChange> iteratorUpdate = hzCmcrChangeListAfter.iterator();
        while (iteratorUpdate.hasNext()){
            HzCmcrChange hzCmcrChange = iteratorUpdate.next();
            if(hzCmcrChange.getCmcrSrcStatus()==2){
                hzCmcrChangesDelete.add(hzCmcrChange);
                iteratorUpdate.remove();
                continue;
            }
        }
        //获取所有新增状态的变更数据
        if(hzCmcrChangeListAfter!=null&&hzCmcrChangeListAfter.size()!=0){
            Iterator<HzCmcrChange> iteratorAdd = hzCmcrChangeListAfter.iterator();
            while (iteratorAdd.hasNext()){
                HzCmcrChange hzCmcrChangeAfter = iteratorAdd.next();
                boolean flag = true;
                for(HzCmcrChange hzCmcrChangeBefor : hzCmcrChangeListBefor){
                    if(hzCmcrChangeBefor.getCmcrSrcPuid().equals(hzCmcrChangeAfter.getCmcrSrcPuid())){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    hzCmcrChangesAdd.add(hzCmcrChangeAfter);
                    iteratorAdd.remove();
                }
            }
        }

        //剩下的为修改的
        for(HzCmcrChange hzCmcrChangeAfter : hzCmcrChangeListAfter){
            for(HzCmcrChange hzCmcrChangeBefor : hzCmcrChangeListBefor){
                if(hzCmcrChangeAfter.getCmcrSrcPuid().equals(hzCmcrChangeBefor.getCmcrSrcPuid())){
                    hzCmcrChangesUpdate.add(hzCmcrChangeBefor);
                    hzCmcrChangesUpdate.add(hzCmcrChangeAfter);
                    break;
                }
            }
        }
        //填充删除数据
        if(hzCmcrChangesDelete!=null&&hzCmcrChangesDelete.size()>0) {
            List<HzCmcrDetailChange> hzCmcrDetailChangesDelete = hzCmcrDetailChangeDao.doQueryCmcrDetailByMainChange(hzCmcrChangesDelete);
            for (HzCmcrChange hzCmcrChange : hzCmcrChangesDelete) {
                Map<String, String> deleteMap = new HashMap<>();
                setColorModelBasicDate(hzCmcrChange, deleteMap, "删除");
                setColorModelDetailDate(titleSet,hzCmcrDetailChangesDelete,hzCmcrChange,deleteMap);
                result.add(deleteMap);
            }
        }
        //填充新增
        if(hzCmcrChangesAdd!=null&&hzCmcrChangesAdd.size()>0) {
            for (HzCmcrChange hzCmcrChange : hzCmcrChangesAdd) {
                List<HzCmcrDetailChange> hzCmcrDetailChangesAdd = hzCmcrDetailChangeDao.doQueryCmcrDetailByMainChange(hzCmcrChangesAdd);
                Map<String, String> addMap = new HashMap<>();
                setColorModelBasicDate(hzCmcrChange, addMap, "新增");
                setColorModelDetailDate(titleSet, hzCmcrDetailChangesAdd, hzCmcrChange, addMap);
                result.add(addMap);
            }
        }
        //填充修改数据
        if(hzCmcrChangesUpdate!=null&&hzCmcrChangesUpdate.size()>0) {
            for (int i = 0; i < hzCmcrChangesUpdate.size(); i++) {
                List<HzCmcrDetailChange> hzCmcrDetailChangesUpdate = hzCmcrDetailChangeDao.doQueryCmcrDetailByMainChange(hzCmcrChangesUpdate);
                Map<String, String> beforMap = new HashMap<>();
                Map<String, String> afterMap = new HashMap<>();
                setColorModelBasicDate(hzCmcrChangesUpdate.get(i), beforMap, "修改前");
                setColorModelDetailDate(titleSet, hzCmcrDetailChangesUpdate, hzCmcrChangesUpdate.get(i), beforMap);
                setColorModelBasicDate(hzCmcrChangesUpdate.get(i + 1), afterMap, "修改后");
                setColorModelDetailDate(titleSet, hzCmcrDetailChangesUpdate, hzCmcrChangesUpdate.get(i+1), afterMap);
                result.add(beforMap);
                result.add(afterMap);
                i++;
            }
        }

        map.put("titleSet", titleSet);
        map.put("result", result);
    }



    public void doQueryCmcrDetailChangBefor(Map<String, Object> map, Long vwoId) {
        //变更前后主数据
        List<HzCmcrChange> hzCmcrChangeListBefor = null;
        List<HzCmcrChange> hzCmcrChangeListAfter = null;
        //变更前后从数据
        List<HzCmcrDetailChange> hzCmcrDetailChangeListBefor = new ArrayList<>();
        List<HzCmcrDetailChange> hzCmcrDetailChangeListAfter = new ArrayList<HzCmcrDetailChange>();

        Set<String> titleSet = new LinkedHashSet<>();

        //查询变更前主数据（vwo号小于变更后的vwo号）
        hzCmcrChangeListBefor = hzCmcrChangeDao.doQueryCmcrChangeBefor(vwoId);
        if (hzCmcrChangeListBefor == null || hzCmcrChangeListBefor.size() == 0) {
            return;
        } else {
            //查询变更后主数据
            hzCmcrChangeListAfter = hzCmcrChangeDao.doQueryCmcrChangeAfter(vwoId);
        }

        //查询变更前后从数据
        hzCmcrDetailChangeListBefor = hzCmcrDetailChangeDao.doQueryCmcrDetailByMainChange(hzCmcrChangeListBefor);
        hzCmcrDetailChangeListAfter = hzCmcrDetailChangeDao.doQueryCmcrDetailByMainChange(hzCmcrChangeListAfter);
//
        //srcPuid为key的变更前从数据
        Map<String, List<HzCmcrDetailChange>> srcPuidBeforMap = new HashMap<String, List<HzCmcrDetailChange>>();
        //srcPuid为key的变更后从数据
        Map<String, List<HzCmcrDetailChange>> srcPuidAfterMap = new HashMap<String, List<HzCmcrDetailChange>>();

        for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangeListBefor) {
            //将变更前所有title放入set
            if(hzCmcrDetailChange.getCmcrDetailCgIsColorful()==null||hzCmcrDetailChange.getCmcrDetailCgIsColorful()==0) {
                continue;
            }
            titleSet.add(hzCmcrDetailChange.getCmcrDetailCgTitle());
            //根据源主数据将变更前从数据分开
            if (srcPuidBeforMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()) == null) {
                srcPuidBeforMap.put(hzCmcrDetailChange.getCmcrDetailSrcModelPuid(), new ArrayList<HzCmcrDetailChange>());
            }
            srcPuidBeforMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()).add(hzCmcrDetailChange);
        }
        for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangeListAfter) {
            //将变更后所有title放入set
            if(hzCmcrDetailChange.getCmcrDetailCgIsColorful()==null||hzCmcrDetailChange.getCmcrDetailCgIsColorful()==0) {
                continue;
            }
            titleSet.add(hzCmcrDetailChange.getCmcrDetailCgTitle());
            //根据源主数据将变更后从数据分开
            if (srcPuidAfterMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()) == null) {
                srcPuidAfterMap.put(hzCmcrDetailChange.getCmcrDetailSrcModelPuid(), new ArrayList<HzCmcrDetailChange>());
            }
            srcPuidAfterMap.get(hzCmcrDetailChange.getCmcrDetailSrcModelPuid()).add(hzCmcrDetailChange);
        }

        List<Map<String,String>> result = new ArrayList<>();
        for(int i=0;i<hzCmcrChangeListAfter.size();i++){
            //变更前行
            Map<String,String> beforMap = new HashMap<>();
            //变更后行
            Map<String,String> afterMap = new HashMap<>();
            //变更前主数据
            beforMap.put("codeOfColorModel",hzCmcrChangeListBefor.get(i).getCmcrSrcCodeOfColorMod() == null ? "" : hzCmcrChangeListBefor.get(i).getCmcrSrcCodeOfColorMod());
            beforMap.put("descOfColorModel",hzCmcrChangeListBefor.get(i).getCmcrSrcDescOfColorMod() == null ? "" : hzCmcrChangeListBefor.get(i).getCmcrSrcDescOfColorMod());
            beforMap.put("modelShell",hzCmcrChangeListBefor.get(i).getCmcrCgShellCode() == null ? "" : hzCmcrChangeListBefor.get(i).getCmcrCgShellCode());
            beforMap.put("id",hzCmcrChangeListBefor.get(i).getCmcrCgId() == null ? "" : String.valueOf(hzCmcrChangeListBefor.get(i).getCmcrCgId()));
            //变更后主数据
            afterMap.put("codeOfColorModel",hzCmcrChangeListAfter.get(i).getCmcrSrcCodeOfColorMod() == null ? "-" : hzCmcrChangeListAfter.get(i).getCmcrSrcCodeOfColorMod());
            afterMap.put("descOfColorModel",hzCmcrChangeListAfter.get(i).getCmcrSrcDescOfColorMod() == null ? "-" : hzCmcrChangeListAfter.get(i).getCmcrSrcDescOfColorMod());
            afterMap.put("modelShell",hzCmcrChangeListAfter.get(i).getCmcrCgShellCode() == null ? "-" : hzCmcrChangeListAfter.get(i).getCmcrCgShellCode());
            afterMap.put("id",hzCmcrChangeListAfter.get(i).getCmcrCgId() == null ? "-" : String.valueOf(hzCmcrChangeListAfter.get(i).getCmcrCgId()));
            //根据变更前主数据id从srcPuidBeforMap中获取变更前从数据
            String srcPuid = hzCmcrChangeListAfter.get(i).getCmcrSrcPuid();
            List<HzCmcrDetailChange> hzCmcrDetailChangesBefor = srcPuidBeforMap.get(srcPuid);
            List<HzCmcrDetailChange> hzCmcrDetailChangesAfter = srcPuidAfterMap.get(srcPuid);

            Iterator<String> iterator = titleSet.iterator();
            int titleNum = 0;
            while (iterator.hasNext()){
                String title = iterator.next();
                boolean beforTitleFlag = false;
                for(HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesBefor ){
                    if (title.equals(hzCmcrDetailChange.getCmcrDetailCgTitle())) {
                        beforMap.put("s" + titleNum, hzCmcrDetailChange.getColorCode() == null ? "-" : hzCmcrDetailChange.getColorCode());
                        beforTitleFlag = true;
                        break;
                    }
                }
                if(!beforTitleFlag){
                    beforMap.put("s" + titleNum,"-");
                }

                boolean afterTitleFlag = false;
                for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesAfter) {
                    if (title.equals(hzCmcrDetailChange.getCmcrDetailCgTitle())) {
                        afterMap.put("s" + titleNum, hzCmcrDetailChange.getColorCode() == null ? "-" : hzCmcrDetailChange.getColorCode());
                        afterTitleFlag = true;
                        break;
                    }
                }
                if(!afterTitleFlag){
                    afterMap.put("s" + titleNum,"-");
                }
                titleNum++;
            }
            if(hzCmcrChangeListBefor.get(i).getCmcrSrcCodeOfColorMod() == null){
                afterMap.put("headDesc","新增<br>"+hzCmcrChangeListAfter.get(i).getCmcrSrcDescOfColorMod());
                result.add(afterMap);
            }else if(hzCmcrChangeListAfter.get(i).getCmcrSrcStatus() == 2){
                beforMap.put("headDesc","删除<br>"+hzCmcrChangeListBefor.get(i).getCmcrSrcDescOfColorMod());
                result.add(beforMap);
            }else {
                beforMap.put("headDesc","修改前<br>"+hzCmcrChangeListAfter.get(i).getCmcrSrcDescOfColorMod());
                afterMap.put("headDesc","修改后<br>"+hzCmcrChangeListBefor.get(i).getCmcrSrcDescOfColorMod());
                result.add(beforMap);
                result.add(afterMap);
            }

        }
        map.put("titleSet", titleSet);
        map.put("result", result);
    }

    /**************************衍生物料变更描述表单***************************/
    @Override
    public Map<String, Object> getMaterielFeatureTable(Long formId,String projectUid) {
        //总数据
        Map<String, Object> map = new HashMap<String, Object>();
        //列数据
        Set<String> titleSet = new LinkedHashSet<>();
        //行数据
        List<Map<String,String>> result = new ArrayList<>();

        //工厂MAP
        Map<String,HzFactory> factoryMap = new HashMap<>();
        //超级物料
        HzMaterielRecord superMateriel = hzSuperMaterielService.doSelectByProjectPuid(projectUid);


        //变更前主数据
        List<HzDMBasicChangeBean> hzDMBasicChangeBeansBefor = new ArrayList<>();
        //变更前从数据
        List<HzDMDetailChangeBean> hzDMDetailChangeBeansBefor = new ArrayList<>();
        //变更后主数据
        List<HzDMBasicChangeBean> hzDMBasicChangeBeansAfter = new ArrayList<>();
        //变更后从数据
        List<HzDMDetailChangeBean> hzDMDetailChangeBeansAfter = new ArrayList<>();

        /********查询变更前后主从数据*********/
        //根据变更表单id查询变更后主数据
            hzDMBasicChangeBeansAfter = hzDMBasicChangeDao.selectAfter(formId);
        if(hzDMBasicChangeBeansAfter==null||hzDMBasicChangeBeansAfter.size()<=0){
            return map;
        }

        //根据变更后数据查询最近一次变更主数据
        if(hzDMBasicChangeBeansAfter!=null&&hzDMBasicChangeBeansAfter.size()>0){
            for(HzDMBasicChangeBean hzDMBasicChangeBeanAfter : hzDMBasicChangeBeansAfter){
                HzDMBasicChangeBean hzDMBasicChangeBeanBefor = hzDMBasicChangeDao.selectBefor(hzDMBasicChangeBeanAfter);
                if(hzDMBasicChangeBeanBefor!=null) {
                    hzDMBasicChangeBeansBefor.add(hzDMBasicChangeBeanBefor);
                }
            }
        }
        if(hzDMBasicChangeBeansBefor!=null&&hzDMBasicChangeBeansBefor.size()!=0){
            //根据最近一次变更主数据查询变更从数据
            hzDMDetailChangeBeansBefor = hzDMDetailChangeDao.selectByBasic(hzDMBasicChangeBeansBefor);
        }

        //根据变更后主数据查询变更后从数据
        hzDMDetailChangeBeansAfter =hzDMDetailChangeDao.selectByBasic(hzDMBasicChangeBeansAfter);

        /******获取列*******/
        //变更前
        if(hzDMDetailChangeBeansBefor!=null&&hzDMDetailChangeBeansBefor.size()!=0){
            for(HzDMDetailChangeBean hzDMDetailChangeBean : hzDMDetailChangeBeansBefor){
                titleSet.add(hzDMDetailChangeBean.getTitle());
            }
        }
        //变更后
        if(hzDMDetailChangeBeansAfter==null||hzDMDetailChangeBeansAfter.size()<=0){
            return map;
        }
        for(HzDMDetailChangeBean hzDMDetailChangeBean : hzDMDetailChangeBeansAfter){
            titleSet.add(hzDMDetailChangeBean.getTitle());
        }



        /******整理变更前后主数据*******/
        //将所有删除数据过滤出来
        Iterator<HzDMBasicChangeBean> IteratorDelete = hzDMBasicChangeBeansAfter.iterator();
        List<HzDMBasicChangeBean> hzDMBasicChangeBeansDelete = new ArrayList<>();
        while (IteratorDelete.hasNext()){
            HzDMBasicChangeBean hzDMBasicChangeBean = IteratorDelete.next();
            if(hzDMBasicChangeBean.getDmbSrcStatus()!=null&&hzDMBasicChangeBean.getDmbSrcStatus()==2){
                hzDMBasicChangeBeansDelete.add(hzDMBasicChangeBean);
                IteratorDelete.remove();
            }
        }
        if(hzDMBasicChangeBeansDelete.size()>0){
            List<HzDMDetailChangeBean> hzDMDetailChangeBeansDelete = hzDMDetailChangeDao.selectByBasic(hzDMBasicChangeBeansDelete);
            for(HzDMBasicChangeBean hzDMBasicChangeBean : hzDMBasicChangeBeansDelete){
                Map<String,String> basicMap = new HashMap<>();
                setMaterielFeatureBasicDate(hzDMBasicChangeBean,basicMap,"删除",factoryMap,superMateriel);
                setMaterielFeatureDetailDate(titleSet,hzDMDetailChangeBeansDelete,hzDMBasicChangeBean,basicMap);
                result.add(basicMap);
            }
        }

        //将所有新增的数据过滤出来
        if(hzDMBasicChangeBeansAfter.size()>0) {
            Iterator<HzDMBasicChangeBean> IteratorAdd = hzDMBasicChangeBeansAfter.iterator();
            List<HzDMBasicChangeBean> hzDMBasicChangeBeansAdd = new ArrayList<>();
            while (IteratorAdd.hasNext()) {
                HzDMBasicChangeBean hzDMBasicChangeBeanAfter = IteratorAdd.next();
                boolean flag = true;
                if(hzDMBasicChangeBeansBefor==null||hzDMBasicChangeBeansBefor.size()==0){
                    flag = true;
                }else {
                    for (HzDMBasicChangeBean hzDMBasicChangeBeanBefor : hzDMBasicChangeBeansBefor) {
                        if (hzDMBasicChangeBeanAfter.getDmbSrcId().equals(hzDMBasicChangeBeanBefor.getDmbSrcId())) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    hzDMBasicChangeBeansAdd.add(hzDMBasicChangeBeanAfter);
                    IteratorAdd.remove();
                }
            }
            if(hzDMBasicChangeBeansAdd.size()>0) {
                List<HzDMDetailChangeBean> hzDMDetailChangeBeansAdd = hzDMDetailChangeDao.selectByBasic(hzDMBasicChangeBeansAdd);
                for (HzDMBasicChangeBean hzDMBasicChangeBean : hzDMBasicChangeBeansAdd) {
                    Map<String, String> basicMap = new HashMap<>();
                    setMaterielFeatureBasicDate(hzDMBasicChangeBean, basicMap, "新增", factoryMap, superMateriel);
                    setMaterielFeatureDetailDate(titleSet,hzDMDetailChangeBeansAdd,hzDMBasicChangeBean,basicMap);
                    result.add(basicMap);
                }
            }
        }
        //整理修改数据，将其变更前后放入一个list中
        if(hzDMBasicChangeBeansAfter.size()>0) {
            List<HzDMBasicChangeBean> hzDMBasicChangeBeansUpdate = new ArrayList<>();
            for(HzDMBasicChangeBean hzDMBasicChangeBeanBefor : hzDMBasicChangeBeansBefor){
                for(HzDMBasicChangeBean hzDMBasicChangeBeanAfter : hzDMBasicChangeBeansAfter){
                    if (hzDMBasicChangeBeanBefor.getDmbSrcId().equals(hzDMBasicChangeBeanAfter.getDmbSrcId())){
                        hzDMBasicChangeBeansUpdate.add(hzDMBasicChangeBeanBefor);
                        hzDMBasicChangeBeansUpdate.add(hzDMBasicChangeBeanAfter);
                        break;
                    }
                }
            }
            if(hzDMBasicChangeBeansUpdate.size()>0) {
                List<HzDMDetailChangeBean> hzDMDetailChangeBeansUpdate = hzDMDetailChangeDao.selectByBasic(hzDMBasicChangeBeansUpdate);
                for (int i = 0; i < hzDMBasicChangeBeansUpdate.size(); i++) {
                    Map<String, String> basicMapBefor = new HashMap<>();
                    Map<String, String> basicMapAfter = new HashMap<>();
                    setMaterielFeatureBasicDate(hzDMBasicChangeBeansUpdate.get(i), basicMapBefor, "变更前", factoryMap, superMateriel);
                    setMaterielFeatureDetailDate(titleSet,hzDMDetailChangeBeansUpdate,hzDMBasicChangeBeansUpdate.get(i),basicMapBefor);
                    setMaterielFeatureBasicDate(hzDMBasicChangeBeansUpdate.get(i + 1), basicMapAfter, "变更后", factoryMap, superMateriel);
                    setMaterielFeatureDetailDate(titleSet,hzDMDetailChangeBeansUpdate,hzDMBasicChangeBeansUpdate.get(i+1),basicMapAfter);
                    result.add(basicMapBefor);
                    result.add(basicMapAfter);
                    i++;
                }
            }
        }


//        for(int i=0;i<hzDMBasicChangeBeansAfter.size();i++){
//            Map<String,String> basicBefor = new HashMap<>();
//            Map<String,String> basicAfter= new HashMap<>();
//            if(hzDMBasicChangeBeansBefor.size()!=0&&hzDMBasicChangeBeansAfter!=null){
//                HzDMBasicChangeBean hzDMBasicChangeBeanBefor = hzDMBasicChangeBeansBefor.get(i);
//                HzCfg0ModelFeature hzCfg0ModelFeature = hzCfg0ModelFeatureService.doSelectByModelAndColorPuids(hzDMBasicChangeBeanBefor.getDmbModelUid(),hzDMBasicChangeBeanBefor.getDmbColorModelUid());
//                basicBefor.put("modeBasicDetail",hzCfg0ModelFeature.getpFeatureSingleVehicleCode());
//                if(factoryMap.get(hzCfg0ModelFeature.getFactoryCode())==null){
//                    HzFactory factory = hzFactoryDAO.findFactory(hzCfg0ModelFeature.getFactoryCode(), null);
//                    factoryMap.put(hzCfg0ModelFeature.getFactoryCode(),factory);
//                }
//                basicBefor.put("factory",factoryMap.get(hzCfg0ModelFeature.getFactoryCode()).getpFactoryCode());
//                basicBefor.put("modeBasicDetailDesc",hzCfg0ModelFeature.getpFeatureCnDesc());
//                if (superMateriel != null) {
//                    basicBefor.put("superMateriel", superMateriel.getpMaterielCode());
//                } else {
//                    basicBefor.put("superMateriel", "");
//                }
//            }else {
//                basicBefor.put("modeBasicDetail","-");
//                basicBefor.put("factory","-");
//                basicBefor.put("modeBasicDetailDesc","-");
//                basicBefor.put("superMateriel","-");
//            }
//            HzDMBasicChangeBean hzDMBasicChangeBeanAfter = hzDMBasicChangeBeansAfter.get(i);
//            HzCfg0ModelFeature hzCfg0ModelFeature = hzCfg0ModelFeatureService.doSelectByModelAndColorPuids(hzDMBasicChangeBeanAfter.getDmbModelUid(),hzDMBasicChangeBeanAfter.getDmbColorModelUid());
//            basicAfter.put("modeBasicDetail",hzCfg0ModelFeature.getpFeatureSingleVehicleCode());
//            if(factoryMap.get(hzCfg0ModelFeature.getFactoryCode())==null){
//                HzFactory factory = hzFactoryDAO.findFactory(hzCfg0ModelFeature.getFactoryCode(), null);
//                factoryMap.put(hzCfg0ModelFeature.getFactoryCode(),factory);
//            }
//            basicAfter.put("factory",factoryMap.get(hzCfg0ModelFeature.getFactoryCode()).getpFactoryCode());
//            basicAfter.put("modeBasicDetailDesc",hzCfg0ModelFeature.getpFeatureCnDesc());
//            if (superMateriel != null) {
//                basicAfter.put("superMateriel", superMateriel.getpMaterielCode());
//            } else {
//                basicAfter.put("superMateriel", "");
//            }
//            /**********整理变更前后从数据***********/
////            Iterator<String> iterator = titleSet.iterator();
////            int titleNum = 0;
////            while (iterator.hasNext()){
////                String title = iterator.next();
////                boolean beforTitleFlag = false;
////                if(hzDMDetailChangeBeansBefor!=null&&hzDMDetailChangeBeansBefor.size()!=0){
////                    for(HzDMDetailChangeBean hzDMDetailChangeBean : hzDMDetailChangeBeansBefor){
////                        if (title.equals(hzDMDetailChangeBean.getTitle())&&hzDMDetailChangeBean.getDmbChangeBasicId().equals(hzDMBasicChangeBeansBefor.get(i).getId())) {
////                            basicBefor.put("s" + titleNum, hzDMDetailChangeBean.getDmdFeatureValue() == null ? "-" : hzDMDetailChangeBean.getDmdFeatureValue());
////                            beforTitleFlag = true;
////                            break;
////                        }
////                    }
////                }
////                if(!beforTitleFlag){
////                    basicBefor.put("s" + titleNum,"-");
////                }
////
////                boolean afterTitleFlag = false;
////                for (HzDMDetailChangeBean hzDMDetailChangeBean : hzDMDetailChangeBeansAfter) {
////                    if (title.equals(hzDMDetailChangeBean.getTitle())&&hzDMDetailChangeBean.getDmbChangeBasicId().equals(hzDMBasicChangeBeansAfter.get(i).getId())) {
////                        basicAfter.put("s" + titleNum, hzDMDetailChangeBean.getDmdFeatureValue() == null ? "-" : hzDMDetailChangeBean.getDmdFeatureValue());
////                        afterTitleFlag = true;
////                        break;
////                    }
////                }
////                if(!afterTitleFlag){
////                    basicAfter.put("s" + titleNum,"-");
////                }
////                titleNum++;
////            }
//            if("-".equals(basicBefor.get("modeBasicDetail"))){
//                basicAfter.put("headDesc","新增<br>"+basicAfter.get("modeBasicDetail"));
//                result.add(basicAfter);
//            }else if("-".equals(basicAfter.get("modeBasicDetail"))){
//                basicBefor.put("headDesc","删除<br>"+basicBefor.get("modeBasicDetail"));
//                result.add(basicBefor);
//            }else {
//                basicBefor.put("headDesc","修改前<br>"+basicBefor.get("modeBasicDetail"));
//                basicAfter.put("headDesc","修改后<br>"+basicAfter.get("modeBasicDetail"));
//                result.add(basicBefor);
//                result.add(basicAfter);
//            }
//        }
        Iterator<String> iterator = titleSet.iterator();
        while (iterator.hasNext()){
            String title = iterator.next();
            if("车身颜色<br/>HZCSYS".equals(title)||"油漆车身总成<br/>HZYQCS".equals(title)){
                iterator.remove();
            }
        }
        map.put("titleSet",titleSet);
        map.put("result",result);
        return map;
    }

    @Override
    public Map<String, Object> getFullCfgTable(Integer orderChangeId, String projectUid) {
        //结果Map
        Map<String, Object> resultMap = new HashMap<>();
        //变更主数据MAP
        Map<String,String> mainMap = new HashMap<>();
        //车辆模型Mao
        Map<String,List<String>> modelmap = new HashMap<>();
        //2Y层数据MAP
        Map<String,List<String>> withCfgMap = new HashMap<>();
        //打点图数据MAP
        Map<String,String> pointMap = new HashMap<>();

        /******************查询变更后全配置主数据*****************/
        HzFullCfgMainChange hzFullCfgMainChangeAfter = null;
        //根据变更后主数据查询变更后的车辆模型
        List<HzFullCfgModelChange> hzFullCfgModelChangesAfter = null;
        //根据变更后主数据查询变更后的2Y
        List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChangesAfter = null;

        hzFullCfgMainChangeAfter = hzFullCfgMainChangeDao.selectByChangeId(orderChangeId);
        if(hzFullCfgMainChangeAfter==null){
            return resultMap;
        }
        hzFullCfgModelChangesAfter = hzFullCfgModelChangeDao.selectByMainId(hzFullCfgMainChangeAfter.getId());
        hzFullCfgWithCfgChangesAfter = hzFullCfgWithCfgChangeDao.selectByMainId(hzFullCfgMainChangeAfter.getId());
        /********初始化车型模型数据************/
        HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(projectUid);
        //车型
        HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(project.getpProjectPertainToVehicle());
        //平台
        HzPlatformRecord platform = hzPlatformService.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
        //品牌
        HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());
        /*********************查询变更前的主数据************************/
        HzFullCfgMainChange hzFullCfgMainChangeBefor =null;
        //查询变更前的车辆模型
        List<HzFullCfgModelChange> hzFullCfgModelChangesBefor = null;
        //查询变更前的2Y
        List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChangesBefor = null;

        hzFullCfgMainChangeBefor = hzFullCfgMainChangeDao.selectLast(hzFullCfgMainChangeAfter.getSrcMainId());
        if(hzFullCfgMainChangeBefor!=null){
            hzFullCfgModelChangesBefor = hzFullCfgModelChangeDao.selectByMainId(hzFullCfgMainChangeBefor.getId());
            hzFullCfgWithCfgChangesBefor = hzFullCfgWithCfgChangeDao.selectByMainId(hzFullCfgMainChangeBefor.getId());
        }

        /******************整理变更主数据***************/
        if(hzFullCfgMainChangeBefor!=null){
            mainMap.put("stage",hzFullCfgMainChangeAfter.getStageString()+"("+hzFullCfgMainChangeBefor.getStageString()+")");
            mainMap.put("version",hzFullCfgMainChangeAfter.getVersion()+"("+hzFullCfgMainChangeBefor.getVersion()+")");
            mainMap.put("effectiveDate",hzFullCfgMainChangeAfter.getEffectiveDate()==null?"":hzFullCfgMainChangeAfter.getEffectiveDate()+"("+hzFullCfgMainChangeBefor.getEffectiveDate()+")");
            mainMap.put("mainId",String.valueOf(hzFullCfgMainChangeAfter.getId()));
         /***********整理车辆模型数据**************/
            //查询变更前后存在的车辆模型
            List<HzCfg0ModelRecord> hzCfg0ModelRecordsBefor =hzCfg0ModelRecordDao.selectByFullCfgModel(hzFullCfgMainChangeBefor.getId());
            List<HzCfg0ModelRecord> hzCfg0ModelRecordsAfter =hzCfg0ModelRecordDao.selectByFullCfgModel(hzFullCfgMainChangeAfter.getId());
            //将变更前后的车辆模型一起存放在一个Map中
            Map<String,HzCfg0ModelRecord> beforAndAfterMap = new HashMap();
            for(HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecordsBefor){
                beforAndAfterMap.put(hzCfg0ModelRecord.getPuid(),hzCfg0ModelRecord );
            }
            for(HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecordsAfter){
                beforAndAfterMap.put(hzCfg0ModelRecord.getPuid(),hzCfg0ModelRecord);
            }


            modelmap.put("brand",new ArrayList<>());
            modelmap.put("platform",new ArrayList<>());
            modelmap.put("vehicle",new ArrayList<>());
            modelmap.put("modelVersion",new ArrayList<>());
            modelmap.put("modelShape",new ArrayList<>());
            modelmap.put("modelAnnouncement",new ArrayList<>());
            modelmap.put("modelCfgDesc",new ArrayList<>());
            modelmap.put("modelCfgMng",new ArrayList<>());
            modelmap.put("modelTrailNum",new ArrayList<>());
            modelmap.put("modelGoodsNum",new ArrayList<>());

            //车辆模型顺序List
            List<HzFullCfgModelChange> hzFullCfgModelChangesOrderBefor = new ArrayList<>();
            List<HzFullCfgModelChange> hzFullCfgModelChangesOrderAfter = new ArrayList<>();
            //往车辆模型中添加数据
            Set<String> beforAndAfterModelKeySet = beforAndAfterMap.keySet();
            for(String key : beforAndAfterModelKeySet){
                //车辆模型ID
                String modelPuid = beforAndAfterMap.get(key).getPuid();
                //找到变更前车辆模型对应的数据
                HzFullCfgModelChange hzFullCfgModelChangeBefor = null;
                for(HzFullCfgModelChange hzFullCfgModelChange : hzFullCfgModelChangesBefor){
                    if(modelPuid.equals(hzFullCfgModelChange.getModModelUid())){
                        hzFullCfgModelChangeBefor = hzFullCfgModelChange;
                        break;
                    }
                }
                //找到变更前车辆模型对应的数据
                HzFullCfgModelChange hzFullCfgModelChangeAfter = null;
                for(HzFullCfgModelChange hzFullCfgModelChange : hzFullCfgModelChangesAfter){
                    if(modelPuid.equals(hzFullCfgModelChange.getModModelUid())){
                        hzFullCfgModelChangeAfter = hzFullCfgModelChange;
                        break;
                    }
                }
                modelmap.get("brand").add(brand.getpBrandName());
                modelmap.get("platform").add(platform.getpPlatformName());
                modelmap.get("vehicle").add(vehicle.getpVehicleName());
                modelmap.get("modelVersion").add(hzFullCfgModelChangeAfter==null?"":hzFullCfgModelChangeAfter.getModelVersion()==null?"":hzFullCfgModelChangeAfter.getModelVersion()+"<br>("+(hzFullCfgModelChangeBefor==null?"":hzFullCfgModelChangeBefor.getModelVersion()==null?"":hzFullCfgModelChangeBefor.getModelVersion())+")");
                modelmap.get("modelShape").add(hzFullCfgModelChangeAfter==null?"":hzFullCfgModelChangeAfter.getModelShape()==null?"":hzFullCfgModelChangeAfter.getModelShape()+"<br>("+(hzFullCfgModelChangeBefor==null?"":hzFullCfgModelChangeBefor.getModelShape()==null?"":hzFullCfgModelChangeBefor.getModelShape())+")");
                modelmap.get("modelAnnouncement").add(hzFullCfgModelChangeAfter==null?"":hzFullCfgModelChangeAfter.getModelAnnouncement()==null?"":hzFullCfgModelChangeAfter.getModelAnnouncement()+"<br>("+(hzFullCfgModelChangeBefor==null?"":hzFullCfgModelChangeBefor.getModelAnnouncement()==null?"":hzFullCfgModelChangeBefor.getModelAnnouncement())+")");
                modelmap.get("modelCfgDesc").add(hzFullCfgModelChangeAfter==null?"":hzFullCfgModelChangeAfter.getModelCfgDesc()==null?"":hzFullCfgModelChangeAfter.getModelCfgDesc()+"<br>("+(hzFullCfgModelChangeBefor==null?"":hzFullCfgModelChangeBefor.getModelCfgDesc()==null?"":hzFullCfgModelChangeBefor.getModelCfgDesc())+")");
                modelmap.get("modelCfgMng").add(hzFullCfgModelChangeAfter==null?"":hzFullCfgModelChangeAfter.getModelCfgMng()==null?"":hzFullCfgModelChangeAfter.getModelCfgMng()+"<br>("+(hzFullCfgModelChangeBefor==null?"":hzFullCfgModelChangeBefor.getModelCfgMng()==null?"":hzFullCfgModelChangeBefor.getModelCfgMng())+")");
                modelmap.get("modelTrailNum").add(hzFullCfgModelChangeAfter==null?"":hzFullCfgModelChangeAfter.getModelTrailNum()==null?"":hzFullCfgModelChangeAfter.getModelTrailNum()+"<br>("+(hzFullCfgModelChangeBefor==null?"":hzFullCfgModelChangeBefor.getModelTrailNum()==null?"":hzFullCfgModelChangeBefor.getModelTrailNum())+")");
                modelmap.get("modelGoodsNum").add(hzFullCfgModelChangeAfter==null?"":hzFullCfgModelChangeAfter.getModelGoodsNum()==null?"":hzFullCfgModelChangeAfter.getModelGoodsNum()+"<br>("+(hzFullCfgModelChangeBefor==null?"":hzFullCfgModelChangeBefor.getModelGoodsNum()==null?"":hzFullCfgModelChangeBefor.getModelGoodsNum())+")");

                if(hzFullCfgModelChangeAfter==null){
                    HzFullCfgModelChange hzFullCfgModelChange = new HzFullCfgModelChange();
                    hzFullCfgModelChange.setModModelUid(hzFullCfgModelChangeBefor.getModModelUid());
                    hzFullCfgModelChangesOrderAfter.add(hzFullCfgModelChange);
                }else {
                    hzFullCfgModelChangesOrderAfter.add(hzFullCfgModelChangeAfter);
                }
                if(hzFullCfgModelChangeBefor==null){
                    HzFullCfgModelChange hzFullCfgModelChange = new HzFullCfgModelChange();
                    hzFullCfgModelChange.setModModelUid(hzFullCfgModelChangeAfter.getModModelUid());
                    hzFullCfgModelChangesOrderBefor.add(hzFullCfgModelChange);
                }else {
                    hzFullCfgModelChangesOrderBefor.add(hzFullCfgModelChangeBefor);
                }
            }

         /***********************整理2Y数据***************************/
            //将变更前后2Y的数据合并
            Map<String,HzFullCfgWithCfgChange> beforAndAfterWithCfgMap = new HashMap<>();
            for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChangesAfter){
                beforAndAfterWithCfgMap.put(hzFullCfgWithCfgChange.getCfgBomlineUid(),hzFullCfgWithCfgChange);
            }
            for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChangesBefor){
                beforAndAfterWithCfgMap.put(hzFullCfgWithCfgChange.getCfgBomlineUid(),hzFullCfgWithCfgChange);
            }
            //往withCfgMap中添加Key
            //操作类型
            withCfgMap.put("operationType",new ArrayList<>());
            //系统
            withCfgMap.put("pBomOfWhichDept",new ArrayList<>());
            //总成零件号
            withCfgMap.put("pBomLineId",new ArrayList<>());
            //总成零件名称
            withCfgMap.put("pBomLineName",new ArrayList<>());
            //总成零件英文名称
            withCfgMap.put("pH9featureenname",new ArrayList<>());
            //责任工程师
            withCfgMap.put("owningUser",new ArrayList<>());
            //配置描述
            withCfgMap.put("pCfg0Desc",new ArrayList<>());
            //配置代码
            withCfgMap.put("pCfg0ObjectId",new ArrayList<>());
            //是否颜色件
            withCfgMap.put("isColorPart",new ArrayList<>());
            //备注
            withCfgMap.put("comment",new ArrayList<>());

            //2Y层数据顺序List
            List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChangesOrderBefor = new ArrayList<>();
            List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChangesOrderAfter = new ArrayList<>();
            //往2Y层加数据
            Set<String> beforAndAfterWithCfgKeySet = beforAndAfterWithCfgMap.keySet();
            List<String> withCfgPuids = new ArrayList<>();
            withCfgPuids.addAll(beforAndAfterWithCfgKeySet);
            List<HzBomLineRecord> hzBomLineRecords = hzBomLineRecordDao.selectByPuids(withCfgPuids);
            Map<String,HzBomLineRecord> hzBomLineRecordMap =new HashMap<>();
            for(HzBomLineRecord hzBomLineRecord : hzBomLineRecords){
                hzBomLineRecordMap.put(hzBomLineRecord.getPuid(),hzBomLineRecord );
            }
            for(String key : beforAndAfterWithCfgKeySet){
                String cfgBomlineUid = beforAndAfterWithCfgMap.get(key).getCfgBomlineUid();
                //找到变更前2Y层对应的数据
                HzFullCfgWithCfgChange hzFullCfgWithCfgChangeBefor = null;
                for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChangesBefor){
                    if(cfgBomlineUid.equals(hzFullCfgWithCfgChange.getCfgBomlineUid())){
                        hzFullCfgWithCfgChangeBefor = hzFullCfgWithCfgChange;
                        break;
                    }
                }
                //找到变更后2Y层对应的数据
                HzFullCfgWithCfgChange hzFullCfgWithCfgChangeAfter = null;
                for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChangesAfter){
                    if(cfgBomlineUid.equals(hzFullCfgWithCfgChange .getCfgBomlineUid())){
                        hzFullCfgWithCfgChangeAfter = hzFullCfgWithCfgChange;
                    }
                }
                //操作类型
                String flOperationType = "";
                if(hzFullCfgWithCfgChangeAfter.getFlOperationType()!=null){
                    flOperationType += hzFullCfgWithCfgChangeAfter.getFlOperationTypeString();
                }else {
                    flOperationType += "-";
                }
                flOperationType += "<br>(";
                if(hzFullCfgWithCfgChangeBefor.getFlOperationType()!=null){
                    flOperationType += hzFullCfgWithCfgChangeBefor.getFlOperationTypeString();
                }else {
                    flOperationType += "-";
                }
                flOperationType += ")";
                withCfgMap.get("operationType").add(flOperationType);
                HzBomLineRecord hzBomLineRecord = hzBomLineRecordMap.get(hzFullCfgWithCfgChangeAfter.getCfgBomlineUid());
                withCfgMap.get("pBomOfWhichDept").add(hzBomLineRecord.getpBomOfWhichDept()==null?"":hzBomLineRecord.getpBomOfWhichDept());
                withCfgMap.get("pBomLineId").add(hzBomLineRecord.getLineID()==null?"":hzBomLineRecord.getLineID());
                withCfgMap.get("pBomLineName").add(hzBomLineRecord.getpBomLinePartName()==null?"":hzBomLineRecord.getpBomLinePartName());
                withCfgMap.get("pH9featureenname").add(hzBomLineRecord.getpBomLinePartEnName()==null?"":hzBomLineRecord.getpBomLinePartEnName());
                withCfgMap.get("owningUser").add(hzBomLineRecord.getpDutyEngineer()==null?"":hzBomLineRecord.getpDutyEngineer());
                withCfgMap.get("pCfg0Desc").add(hzFullCfgWithCfgChangeAfter.getCfgDesc()+"<br>("+hzFullCfgWithCfgChangeBefor.getCfgDesc()+")");
                withCfgMap.get("pCfg0ObjectId").add(hzFullCfgWithCfgChangeAfter.getCfgCode()+"<br>("+hzFullCfgWithCfgChangeBefor.getCfgCode()+")");
                withCfgMap.get("isColorPart").add(hzFullCfgWithCfgChangeAfter.getIsColor()+"<br>("+hzFullCfgWithCfgChangeBefor.getIsColor()+")");
                withCfgMap.get("comment").add(hzFullCfgWithCfgChangeAfter.getFlCommentString()+"<br>("+hzFullCfgWithCfgChangeBefor.getFlCommentString()+")");

                if(hzFullCfgWithCfgChangeAfter==null){
                    HzFullCfgWithCfgChange hzFullCfgWithCfgChange = new HzFullCfgWithCfgChange();
                    hzFullCfgWithCfgChange.setCfgBomlineUid(hzFullCfgWithCfgChangeBefor.getCfgBomlineUid());
                    hzFullCfgWithCfgChangesOrderAfter.add(hzFullCfgWithCfgChange);
                }else {
                    hzFullCfgWithCfgChangesOrderAfter.add(hzFullCfgWithCfgChangeAfter);
                }
                if(hzFullCfgWithCfgChangeBefor==null){
                    HzFullCfgWithCfgChange hzFullCfgWithCfgChange = new HzFullCfgWithCfgChange();
                    hzFullCfgWithCfgChange.setCfgBomlineUid(hzFullCfgWithCfgChangeAfter.getCfgBomlineUid());
                    hzFullCfgWithCfgChangesOrderBefor.add(hzFullCfgWithCfgChange);
                }else {
                    hzFullCfgWithCfgChangesOrderBefor.add(hzFullCfgWithCfgChangeBefor);
                }
            }

            /****************打点图填充数据******************/
            //生成一个车辆模型和2Y层的双层MAP数据,其结构为<modelUID<cfgUID，model对象>>
            Map<String,Map<String,String>> modelAndCfgMap = new HashMap<>();
            for(HzFullCfgModelChange hzFullCfgModelChange : hzFullCfgModelChangesOrderAfter){
                if(modelAndCfgMap.get(hzFullCfgModelChange.getModModelUid())==null){
                    modelAndCfgMap.put(hzFullCfgModelChange.getModModelUid(),new HashMap<>());
                }
                Map<String, String> cfgMap = modelAndCfgMap.get(hzFullCfgModelChange.getModModelUid());
                for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChangesOrderAfter){
                    for(HzFullCfgModelChange hzFullCfgModelChange1: hzFullCfgModelChangesAfter){
                        if(hzFullCfgModelChange1.getFlModelBomlineUid().equals(hzFullCfgWithCfgChange.getCfgBomlineUid())&&hzFullCfgModelChange1.getModModelUid().equals(hzFullCfgModelChange.getModModelUid())){
                            cfgMap.put(hzFullCfgModelChange1.getFlModelBomlineUid(),hzFullCfgModelChange1.getModPointType()==0?"-()":hzFullCfgModelChange1.getModPointType()==1?"○":"●");
                        }
                    }
                }
            }

            for(HzFullCfgModelChange hzFullCfgModelChange : hzFullCfgModelChangesOrderBefor){
                if(modelAndCfgMap.get(hzFullCfgModelChange.getModModelUid())==null){
                    modelAndCfgMap.put(hzFullCfgModelChange.getModModelUid(),new HashMap<>());
                }
                Map<String, String> cfgMap = modelAndCfgMap.get(hzFullCfgModelChange.getModModelUid());
                for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChangesOrderBefor){
                    for(HzFullCfgModelChange hzFullCfgModelChange1: hzFullCfgModelChangesAfter){
                        if(hzFullCfgModelChange1.getFlModelBomlineUid().equals(hzFullCfgWithCfgChange.getCfgBomlineUid())&&hzFullCfgModelChange1.getModModelUid().equals(hzFullCfgModelChange.getModModelUid())){
                            cfgMap.put(hzFullCfgModelChange1.getFlModelBomlineUid(),cfgMap.get(hzFullCfgModelChange1.getFlModelBomlineUid())+"("+(hzFullCfgModelChange1.getModPointType()==0?"-":hzFullCfgModelChange1.getModPointType()==1?"○":"●")+")");
                        }
                    }
                }
            }
//            for(int i=0;i<hzFullCfgModelChanges.size()-1;i++){
//                String modeUid = "";
//                //数据类型，1为添加，2为删除，0为修改
//                Integer flag = 0;
//                Boolean beforFlag = false;
//                Boolean afterFlag = false;
//                if(hzFullCfgModelChanges.get(i)!=null){
//                    modeUid = hzFullCfgModelChanges.get(i).getModModelUid();
//                    afterFlag = true;
//                    if(modelAndCfgMap.get(modeUid)==null){
//                        modelAndCfgMap.put(modeUid,new HashMap<>());
//                    }
//                }
//                if(hzFullCfgModelChanges.get(i+1)!=null){
//                    modeUid = hzFullCfgModelChanges.get(i+1).getModModelUid();
//                    beforFlag = true;
//                    if(modelAndCfgMap.get(modeUid)==null){
//                        modelAndCfgMap.put(modeUid,new HashMap<>());
//                    }
//                }
//                if(beforFlag&&afterFlag){
//                    flag = 0;
//                }else if(beforFlag==true&&afterFlag==false){
//                    flag = 2;
//                }else if(beforFlag==false&&afterFlag==true){
//                    flag =1;
//                }
//                Map<String, String> cfgMap = modelAndCfgMap.get(hzFullCfgModelChanges.get(i).getModModelUid());
//                for(int j=0;j<hzFullCfgWithCfgChanges.size();j+=2){
//                    String pointAfter = "";
//                    if(afterFlag){
//                        for(HzFullCfgModelChange hzFullCfgModelChange1: hzFullCfgModelChangesAfter){
//                            if(hzFullCfgModelChange1.getFlModelBomlineUid().equals(hzFullCfgWithCfgChanges.get(j).getCfgBomlineUid())&&hzFullCfgModelChange1.getModModelUid().equals(hzFullCfgModelChanges.get(i).getModModelUid())){
//                                pointAfter += hzFullCfgModelChange1.getModPointType()==0?"-":hzFullCfgModelChange1.getModPointType()==1?"○":"●";
//                                break;
//                            }
//                        }
//                    }
//                    String pointBefor = "";
//                    if(beforFlag){
//                        for(HzFullCfgModelChange hzFullCfgModelChange1: hzFullCfgModelChangesBefor){
//                            if(hzFullCfgModelChange1.getFlModelBomlineUid().equals(hzFullCfgWithCfgChanges.get(j+1))&&hzFullCfgModelChange1.getModModelUid().equals(hzFullCfgModelChanges.get(i+1).getModModelUid())){
//                                pointBefor += hzFullCfgModelChange1.getModPointType()==0?"-":hzFullCfgModelChange1.getModPointType()==1?"○":"●";
//                                break;
//                            }
//                        }
//                    }
//                    cfgMap.put(hzFullCfgWithCfgChanges.get(j).getCfgBomlineUid(),pointAfter+"("+pointBefor+")");
//                }
//                i++;
//            }






//            for(HzFullCfgModelChange hzFullCfgModelChange : hzFullCfgModelChanges){
//                if(modelAndCfgMap.get(hzFullCfgModelChange.getModModelUid())==null){
//                    modelAndCfgMap.put(hzFullCfgModelChange.getModModelUid(),new HashMap<>());
//                }
//                Map<String, String> cfgMap = modelAndCfgMap.get(hzFullCfgModelChange.getModModelUid());
//                for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChanges){
//                    String pointAfter = "";
//                    for(HzFullCfgModelChange hzFullCfgModelChange1: hzFullCfgModelChangesAfter){
//                        if(hzFullCfgModelChange1.getFlModelBomlineUid().equals(hzFullCfgWithCfgChange.getCfgBomlineUid())&&hzFullCfgModelChange1.getFlModelBomlineUid().equals(hzFullCfgModelChange.getFlModelBomlineUid())){
//                            pointAfter += hzFullCfgModelChange1.getModPointType()==0?"-":hzFullCfgModelChange1.getModPointType()==1?"○":"●";
//                            break;
//                        }
//                    }
//                    String pointBefor = "";
//                    for(HzFullCfgModelChange hzFullCfgModelChange1: hzFullCfgModelChangesBefor){
//                        if(hzFullCfgModelChange1.getFlModelBomlineUid().equals(hzFullCfgWithCfgChange.getCfgBomlineUid())&&hzFullCfgModelChange1.getFlModelBomlineUid().equals(hzFullCfgModelChange.getFlModelBomlineUid())){
//                            pointBefor += hzFullCfgModelChange1.getModPointType()==0?"-":hzFullCfgModelChange1.getModPointType()==1?"○":"●";
//                            break;
//                        }
//                    }
//                    cfgMap.put(hzFullCfgWithCfgChange.getCfgBomlineUid(),pointAfter+"("+pointBefor+")");
//                }
//            }
            //遍历车型和2Y层从Map中获取打点图信息
            for(int i=0;i<hzFullCfgWithCfgChangesOrderAfter.size();i++){
                for(int j=0;j<hzFullCfgModelChangesOrderAfter.size();j++){
                    String key = "c"+i+"m"+j;
                    //变更前
                    String point = modelAndCfgMap.get(hzFullCfgModelChangesOrderAfter.get(j).getModModelUid()).get(hzFullCfgWithCfgChangesOrderAfter.get(i).getCfgBomlineUid());
                    //变更后
                    pointMap.put(key,point);
                }
            }
        }else {
                mainMap.put("stage",hzFullCfgMainChangeAfter.getStageString()+"()");
                mainMap.put("version",hzFullCfgMainChangeAfter.getVersion()+"()");
                mainMap.put("effectiveDate",hzFullCfgMainChangeAfter.getEffectiveDate()+"()");
                mainMap.put("mainId",String.valueOf(hzFullCfgMainChangeAfter.getId()));
                /***********整理车辆模型数据**************/
                //查询变更前存在的车辆模型
                List<HzCfg0ModelRecord> hzCfg0ModelRecordsAfter =hzCfg0ModelRecordDao.selectByFullCfgModel(hzFullCfgMainChangeAfter.getId());
                //将变更前后的车辆模型一起存放在一个Map中
                Map<String,HzCfg0ModelRecord> beforAndAfterMap = new HashMap();
                for(HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecordsAfter){
                    beforAndAfterMap.put(hzCfg0ModelRecord.getPuid(),hzCfg0ModelRecord);
                }

                modelmap.put("brand",new ArrayList<>());
                modelmap.put("platform",new ArrayList<>());
                modelmap.put("vehicle", new ArrayList<>());
                modelmap.put("modelVersion",new ArrayList<>());
                modelmap.put("modelShape",new ArrayList<>());
                modelmap.put("modelAnnouncement",new ArrayList<>());
                modelmap.put("modelCfgDesc",new ArrayList<>());
                modelmap.put("modelCfgMng",new ArrayList<>());
                modelmap.put("modelTrailNum",new ArrayList<>());
                modelmap.put("modelGoodsNum",new ArrayList<>());

                //车辆模型顺序List
                List<HzFullCfgModelChange> hzFullCfgModelChanges = new ArrayList<>();
                //往车辆模型中添加数据
                Set<String> beforAndAfterModelKeySet = beforAndAfterMap.keySet();
                for(String key : beforAndAfterModelKeySet){
                    //车辆模型ID
                    String modelPuid = beforAndAfterMap.get(key).getPuid();
                    //找到变更后车辆模型对应的数据
                    HzFullCfgModelChange hzFullCfgModelChangeAfter = null;
                    for(HzFullCfgModelChange hzFullCfgModelChange : hzFullCfgModelChangesAfter){
                        if(modelPuid.equals(hzFullCfgModelChange.getModModelUid())){
                            hzFullCfgModelChangeAfter = hzFullCfgModelChange;
                            break;
                        }
                    }
                    modelmap.get("brand").add(brand.getpBrandName());
                    modelmap.get("platform").add(platform.getpPlatformName());
                    modelmap.get("vehicle").add(vehicle.getpVehicleName());
                    modelmap.get("modelVersion").add(hzFullCfgModelChangeAfter.getModelVersion()==null?"":hzFullCfgModelChangeAfter.getModelVersion()+"<br>()");
                    modelmap.get("modelShape").add(hzFullCfgModelChangeAfter.getModelShape()==null?"":hzFullCfgModelChangeAfter.getModelShape()+"<br>()");
                    modelmap.get("modelAnnouncement").add(hzFullCfgModelChangeAfter.getModelAnnouncement()==null?"":hzFullCfgModelChangeAfter.getModelAnnouncement()+"<br>()");
                    modelmap.get("modelCfgDesc").add(hzFullCfgModelChangeAfter.getModelCfgDesc()==null?"":hzFullCfgModelChangeAfter.getModelCfgDesc()+"<br>()");
                    modelmap.get("modelCfgMng").add(hzFullCfgModelChangeAfter.getModelCfgMng()==null?"":hzFullCfgModelChangeAfter.getModelCfgMng()+"<br>()");
                    modelmap.get("modelTrailNum").add(hzFullCfgModelChangeAfter.getModelTrailNum()==null?"":hzFullCfgModelChangeAfter.getModelTrailNum()+"<br>()");
                    modelmap.get("modelGoodsNum").add(hzFullCfgModelChangeAfter.getModelGoodsNum()==null?"":hzFullCfgModelChangeAfter.getModelGoodsNum()+"<br>()");

                    hzFullCfgModelChanges.add(hzFullCfgModelChangeAfter);
                }

                /***********************整理2Y数据***************************/
                //将变更前后2Y的数据合并
                Map<String,HzFullCfgWithCfgChange> beforAndAfterWithCfgMap = new HashMap<>();
                for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChangesAfter){
                    beforAndAfterWithCfgMap.put(hzFullCfgWithCfgChange.getCfgBomlineUid(),hzFullCfgWithCfgChange);
                }
                //往withCfgMap中添加Key
                //操作类型
                withCfgMap.put("operationType",new ArrayList<>());
                //系统
                withCfgMap.put("pBomOfWhichDept",new ArrayList<>());
                //总成零件号
                withCfgMap.put("pBomLineId",new ArrayList<>());
                //总成零件名称
                withCfgMap.put("pBomLineName",new ArrayList<>());
                //总成零件英文名称
                withCfgMap.put("pH9featureenname",new ArrayList<>());
                //责任工程师
                withCfgMap.put("owningUser",new ArrayList<>());
                //配置描述
                withCfgMap.put("pCfg0Desc",new ArrayList<>());
                //配置代码
                withCfgMap.put("pCfg0ObjectId",new ArrayList<>());
                //是否颜色件
                withCfgMap.put("isColorPart",new ArrayList<>());
                //备注
                withCfgMap.put("comment",new ArrayList<>());

                //2Y层数据顺序List
                List<HzFullCfgWithCfgChange> hzFullCfgWithCfgChanges = new ArrayList<>();
                //往2Y层加数据
                Set<String> beforAndAfterWithCfgKeySet = beforAndAfterWithCfgMap.keySet();
                List<String> withCfgPuids = new ArrayList<>();
                withCfgPuids.addAll(beforAndAfterWithCfgKeySet);
                List<HzBomLineRecord> hzBomLineRecords = hzBomLineRecordDao.selectByPuids(withCfgPuids);
                Map<String,HzBomLineRecord> hzBomLineRecordMap =new HashMap<>();
                for(HzBomLineRecord hzBomLineRecord : hzBomLineRecords){
                    hzBomLineRecordMap.put(hzBomLineRecord.getPuid(),hzBomLineRecord );
                }
                for(String key : beforAndAfterWithCfgKeySet){
                    String cfgBomlineUid = beforAndAfterWithCfgMap.get(key).getCfgBomlineUid();
                    //找到变更后2Y层对应的数据
                    HzFullCfgWithCfgChange hzFullCfgWithCfgChangeAfter = null;
                    for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChangesAfter){
                        if(cfgBomlineUid.equals(hzFullCfgWithCfgChange .getCfgBomlineUid())){
                            hzFullCfgWithCfgChangeAfter = hzFullCfgWithCfgChange;
                        }
                    }
                    //操作类型
                    String flOperationType = "";
                    if(hzFullCfgWithCfgChangeAfter.getFlOperationType()!=null){
                        flOperationType += hzFullCfgWithCfgChangeAfter.getFlOperationTypeString();
                    }else {
                        flOperationType += "-";
                    }
                    flOperationType += "()";

                    withCfgMap.get("operationType").add(flOperationType);
                    HzBomLineRecord hzBomLineRecord = hzBomLineRecordMap.get(hzFullCfgWithCfgChangeAfter.getCfgBomlineUid());
                    withCfgMap.get("pBomOfWhichDept").add(hzBomLineRecord.getpBomOfWhichDept()==null?"":hzBomLineRecord.getpBomOfWhichDept());
                    withCfgMap.get("pBomLineId").add(hzBomLineRecord.getLineID()==null?"":hzBomLineRecord.getLineID());
                    withCfgMap.get("pBomLineName").add(hzBomLineRecord.getpBomLinePartName()==null?"":hzBomLineRecord.getpBomLinePartName());
                    withCfgMap.get("pH9featureenname").add(hzBomLineRecord.getpBomLinePartEnName()==null?"":hzBomLineRecord.getpBomLinePartEnName());
                    withCfgMap.get("owningUser").add(hzBomLineRecord.getpDutyEngineer()==null?"":hzBomLineRecord.getpDutyEngineer());
                    withCfgMap.get("pCfg0Desc").add(hzFullCfgWithCfgChangeAfter.getCfgDesc()+"<br>()");
                    withCfgMap.get("pCfg0ObjectId").add(hzFullCfgWithCfgChangeAfter.getCfgCode()+"<br>()");
                    withCfgMap.get("isColorPart").add(hzFullCfgWithCfgChangeAfter.getIsColor()+"<br>()");
                    withCfgMap.get("comment").add(hzFullCfgWithCfgChangeAfter.getFlCommentString()+"<br>()");

                    hzFullCfgWithCfgChanges.add(hzFullCfgWithCfgChangeAfter);
                }
            /****************打点图填充数据******************/
            //生成一个车辆模型和2Y层的双层MAP数据,其结构为<modelUID<BOMLineId，model对象>>
            Map<String,Map<String,String>> modelAndCfgMap = new HashMap<>();
            for(HzFullCfgModelChange hzFullCfgModelChange : hzFullCfgModelChanges){
                if(modelAndCfgMap.get(hzFullCfgModelChange.getModModelUid())==null){
                    modelAndCfgMap.put(hzFullCfgModelChange.getModModelUid(),new HashMap<>());
                }
                Map<String, String> cfgMap = modelAndCfgMap.get(hzFullCfgModelChange.getModModelUid());
                for(HzFullCfgWithCfgChange hzFullCfgWithCfgChange : hzFullCfgWithCfgChanges){
                    for(HzFullCfgModelChange hzFullCfgModelChange1: hzFullCfgModelChangesAfter){
                        if(hzFullCfgModelChange1.getFlModelBomlineUid().equals(hzFullCfgWithCfgChange.getCfgBomlineUid())&&hzFullCfgModelChange1.getModModelUid().equals(hzFullCfgModelChange.getModModelUid())){
                            cfgMap.put(hzFullCfgModelChange1.getFlModelBomlineUid(),hzFullCfgModelChange1.getModPointType()==0?"-()":hzFullCfgModelChange1.getModPointType()==1?"○()":"●()");
                        }
                    }
                }
            }
            //遍历车型和2Y层从Map中获取打点图信息
            for(int i=0;i<hzFullCfgWithCfgChanges.size();i++){
                for(int j=0;j<hzFullCfgModelChanges.size();j++){
                    String key = "c"+i+"m"+j;
                    //变更后
                    String point = modelAndCfgMap.get(hzFullCfgModelChanges.get(j).getModModelUid()).get(hzFullCfgWithCfgChanges.get(i).getCfgBomlineUid());
                    pointMap.put(key,point);
                }
            }
        }

        resultMap.put("mainResult",mainMap);
        resultMap.put("modelResult",modelmap);
        resultMap.put("withCfgResult",withCfgMap);
        resultMap.put("pointResult",pointMap);
        return resultMap;
    }

    /**
     * 删除特性变更数据
     * @param changeFeatureIds
     * @return
     */
    @Override
    public JSONObject deleteChangeFeature(List<Long> changeFeatureIds, Long orderId) {
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg","删除成功");
        List<HzFeatureChangeBean> hzFeatureChangeBeans1 = new ArrayList<>();
        for(Long changeFeatureId : changeFeatureIds){
            HzFeatureChangeBean hzFeatureChangeBean = new HzFeatureChangeBean();
            hzFeatureChangeBean.setVwoId(orderId);
            hzFeatureChangeBean.setId(changeFeatureId);
            hzFeatureChangeBeans1.add(hzFeatureChangeBean);
        }
//        if(hzCfg0RecordDao.updateByChangeId(changeFeatureIds)<=0?true:false){
//            result.put("status",false);
//            result.put("msg","修改源数据状态失败");
//            return result;
//        }
        try {
            hzCfg0RecordDao.updateStatusByChangeDate(hzFeatureChangeBeans1);
        }catch (Exception e){
            result.put("status",false);
            result.put("msg","修改源数据状态失败");
            return result;
        }
        if(iHzFeatureChangeService.doDeleteByPrimaryKeys(changeFeatureIds)<=0?true:false){
            result.put("status",false);
            result.put("msg","您选择的是变更前数据或删除变更数据失败");
            return result;
        }
        //查询是否还存在数据,如不存在删除关系表数据
        List<HzFeatureChangeBean> hzFeatureChangeBeans = iHzFeatureChangeService.doselectByChangeId(orderId);
        if(hzFeatureChangeBeans==null||hzFeatureChangeBeans.size()<=0){
            HzChangeDataRecord hzChangeDataRecord = new HzChangeDataRecord();
            hzChangeDataRecord.setTableName(ChangeTableNameEnum.HZ_CFG0_AFTER_CHANGE_RECORD.getTableName());
            hzChangeDataRecord.setOrderId(orderId);
            if(hzChangeDataRecordDAO.deleteByOrderIdAndTableName(hzChangeDataRecord)<=0?true:false){
                result.put("status",false);
                result.put("msg","删除关系表失败");
                return result;
            }
        }
        return result;
    }

    @Override
    public JSONObject deleteChangeColorModel(List<Long> changeColorModelIds,Long orderId) {
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg","修改成功");
        //根据变更数据修改源数据状态
        try {
            hzCfg0ModelColorDao.updateByChangeIds(changeColorModelIds);
        }catch (Exception e){
            result.put("status",false);
            result.put("msg","修改源数据失败");
            return result;
        }
        if(hzCmcrChangeDao.doDeleteIds(changeColorModelIds)<=0?true:false){
            result.put("status",false);
            result.put("msg","删除的为变更前数据或删除变更数据失败");
            return result;
        }
        return result;
    }

    @Override
    public JSONObject deleteChangeMaterielFeature(List<Long> changeMaterielFeatureIds,Long orderId) {
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg","修改成功");
        //根据变更数据修改源数据状态
        try {
            hzDerivativeMaterielBasicDao.updateByChangeIds(changeMaterielFeatureIds);
        }catch (Exception e){
            result.put("status",false);
            result.put("msg","修改源数据失败");
            return result;
        }
        if(hzDMBasicChangeDao.updateByChangeIds(changeMaterielFeatureIds)<=0?true:false){
            result.put("status",false);
            result.put("msg","删除变更数据失败,请确定数据是否存在变更前数据");
            return result;
        }
        //判断是否还有变更数据
        List<HzDMBasicChangeBean> hzDMBasicChangeBeans = hzDMBasicChangeDao.selectByFormid(orderId);
        if(hzDMBasicChangeBeans==null||hzDMBasicChangeBeans.size()<=0){
            HzChangeDataRecord hzChangeDataRecord = new HzChangeDataRecord();
            hzChangeDataRecord.setTableName(ChangeTableNameEnum.HZ_DM_BASIC_CHANGE.getTableName());
            hzChangeDataRecord.setOrderId(orderId);
            if(hzChangeDataRecordDAO.deleteByOrderIdAndTableName(hzChangeDataRecord)<=0?true:false){
                result.put("status",false);
                result.put("msg","删除关系表失败");
                return result;
            }
        }
        return result;
    }

    @Override
    public JSONObject deleteChangeBomAll(Long mainId,Long orderId) {
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg","删除成功");
        if(hzFullCfgMainDao.updateChangeByOrderId(orderId)<=0?true:false){
            result.put("status",false);
            result.put("msg","修改源数据失败");
            return result;
        }
        if(hzFullCfgMainChangeDao.deleteById(mainId)<=0?true:false){
            result.put("status",false);
            result.put("msg","删除失败");
            return result;
        }

        HzChangeDataRecord hzChangeDataRecord = new HzChangeDataRecord();
        hzChangeDataRecord.setTableName(ChangeTableNameEnum.HZ_FULL_CFG_MAIN_RECORD_CHANGE.getTableName());
        hzChangeDataRecord.setOrderId(orderId);
        if(hzChangeDataRecordDAO.deleteByOrderIdAndTableName(hzChangeDataRecord)<=0?true:false){
            result.put("status",false);
            result.put("msg","删除关系表失败");
            return result;
        }
        return result;
    }


    /**
     * 衍生物料展示填充主数据方法
     * @param hzDMBasicChangeBean  变更主数据
     * @param basicMap                填充结果集
     * @param headDesc              变更类型
     * @param factoryMap            工厂Map
     * @param superMateriel         超级物料
     */
    public void setMaterielFeatureBasicDate(HzDMBasicChangeBean hzDMBasicChangeBean, Map<String,String> basicMap, String headDesc, Map<String,HzFactory> factoryMap, HzMaterielRecord superMateriel){
        HzCfg0ModelFeature hzCfg0ModelFeature = hzCfg0ModelFeatureService.doSelectByModelAndColorPuids(hzDMBasicChangeBean.getDmbModelUid(),hzDMBasicChangeBean.getDmbColorModelUid());
        basicMap.put("modeBasicDetail",hzCfg0ModelFeature.getpFeatureSingleVehicleCode());
        if(factoryMap.get(hzCfg0ModelFeature.getFactoryCode())==null){
            HzFactory factory = hzFactoryDAO.findFactory(hzCfg0ModelFeature.getFactoryCode(), null);
            factoryMap.put(hzCfg0ModelFeature.getFactoryCode(),factory);
        }
        basicMap.put("factory",factoryMap.get(hzCfg0ModelFeature.getFactoryCode()).getpFactoryCode());
        basicMap.put("modeBasicDetailDesc",hzCfg0ModelFeature.getpFeatureCnDesc());
        if (superMateriel != null) {
            basicMap.put("superMateriel", superMateriel.getpMaterielCode());
        } else {
            basicMap.put("superMateriel", "");
        }
        basicMap.put("headDesc",headDesc+"<br>"+hzCfg0ModelFeature.getpFeatureSingleVehicleCode());
        basicMap.put("id",String.valueOf(hzDMBasicChangeBean.getId()));
    }

    /**
     * 衍生物料展示填充从数据方法
     * @param titleSet                  从数据总集
     * @param hzDMDetailChangeBeans     从数据集合
     * @param hzDMBasicChangeBeans      从数据关联的主数据
     * @param basicMap                  结果集
     */
    public void setMaterielFeatureDetailDate(Set<String> titleSet,List<HzDMDetailChangeBean> hzDMDetailChangeBeans,HzDMBasicChangeBean hzDMBasicChangeBeans, Map<String,String> basicMap){
        Iterator<String> iterator = titleSet.iterator();
        int titleNum = 2;
        while (iterator.hasNext()){
            String title = iterator.next();
            boolean beforTitleFlag = true;
            for(HzDMDetailChangeBean hzDMDetailChangeBean : hzDMDetailChangeBeans){
                if (title.equals(hzDMDetailChangeBean.getTitle())&&hzDMDetailChangeBean.getDmbChangeBasicId()==hzDMBasicChangeBeans.getId()) {
                    if("车身颜色<br/>HZCSYS".equals(title)){
                        basicMap.put("s0", hzDMDetailChangeBean.getDmdFeatureValue() == null ? "-" : hzDMDetailChangeBean.getDmdFeatureValue());
                        beforTitleFlag = false;
                        break;
                    }else if("油漆车身总成<br/>HZYQCS".equals(title)){
                        basicMap.put("s1", hzDMDetailChangeBean.getDmdFeatureValue() == null ? "-" : hzDMDetailChangeBean.getDmdFeatureValue());
                        beforTitleFlag = false;
                        break;
                    }else {
                        basicMap.put("s" + titleNum, hzDMDetailChangeBean.getDmdFeatureValue() == null ? "-" : hzDMDetailChangeBean.getDmdFeatureValue());
                        titleNum++;
                        beforTitleFlag = false;
                        break;
                    }
                }
            }
            if(beforTitleFlag){
                basicMap.put("s" + titleNum,"-");
            }
        }
    }

    public void setColorModelBasicDate(HzCmcrChange hzCmcrChange,Map<String,String> basicMap, String headDesc){
        basicMap.put("codeOfColorModel",hzCmcrChange.getCmcrSrcCodeOfColorMod() == null ? "" : hzCmcrChange.getCmcrSrcCodeOfColorMod());
        basicMap.put("descOfColorModel",hzCmcrChange.getCmcrSrcDescOfColorMod() == null ? "" : hzCmcrChange.getCmcrSrcDescOfColorMod());
        basicMap.put("modelShell",hzCmcrChange.getCmcrCgShellCode() == null ? "" : hzCmcrChange.getCmcrCgShellCode());
        basicMap.put("id",hzCmcrChange.getCmcrCgId() == null ? "" : String.valueOf(hzCmcrChange.getCmcrCgId()));
        basicMap.put("headDesc",headDesc+"<br>"+hzCmcrChange.getCmcrSrcDescOfColorMod());
    }

    public void setColorModelDetailDate(Set<String> titleSet,List<HzCmcrDetailChange> hzCmcrDetailChangeList,HzCmcrChange hzCmcrChange, Map<String,String> basicMap){
        Iterator<String> iterator = titleSet.iterator();
        int titleNum = 0;
        while (iterator.hasNext()){
            String title = iterator.next();
            boolean titleFlag = true;
            for(HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangeList){
                if(title.equals(hzCmcrDetailChange.getCmcrDetailCgTitle())&&hzCmcrChange.getCmcrCgId().equals(hzCmcrDetailChange.getCmcrCgChangeId())){
                    if (title.equals(hzCmcrDetailChange.getCmcrDetailCgTitle())) {
                        basicMap.put("s" + titleNum, hzCmcrDetailChange.getColorCode() == null ? "-" : hzCmcrDetailChange.getColorCode());
                        titleFlag = false;
                        break;
                    }
                }
            }
            if(titleFlag){
                basicMap.put("s" + titleNum,"-");
            }
            titleNum++;
        }
    }
}

