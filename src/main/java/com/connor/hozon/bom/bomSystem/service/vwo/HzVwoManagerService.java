package com.connor.hozon.bom.bomSystem.service.vwo;

import com.connor.hozon.bom.bomSystem.controller.vwo.HzVWOProcessController;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoFormListQueryBase;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.*;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.iservice.process.IProcess;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.process.FeatureProcessManager;
import com.connor.hozon.bom.bomSystem.service.process.InterruptionContainer;
import com.connor.hozon.bom.bomSystem.service.process.ModelColorProcessManager;
import com.connor.hozon.bom.bomSystem.service.process.ReleaseContainer;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import com.connor.hozon.bom.bomSystem.service.project.HzVehicleService;
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

import java.util.*;
import java.util.stream.Collectors;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
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


    //配色方案进入VWO
    public JSONObject getVWO(List<HzCfg0ModelColor> colors, String projectPuid) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        //源主数据
        List<HzCfg0ModelColor> hzCfg0ModelColors = hzCfg0ModelColorDao.selectByPuids(colors);
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
        //根据源主数据生成变更后主数据
        List<HzCmcrChange> hzCmcrChangesAfter = new ArrayList<HzCmcrChange>();
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
        List<HzCmcrDetailChange> hzCmcrDetailChangesAfter = new ArrayList<HzCmcrDetailChange>();
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
        //根据最近一次变更后主数据生成变更前主数据
        for (HzCmcrChange hzCmcrChange : hzCmcrChangesLastAfter) {
            hzCmcrChange.setCmcrCgVwoId(hzVwoInfo.getId());
        }
        //根据最近一次变更后从数据生成变更前从数据
        for (HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesLastAfter) {
            hzCmcrDetailChange.setCmcrDetailCgVwoId(hzVwoInfo.getId());
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
            //跟新变更前主数据
            if (hzCmcrChangesLastAfter != null && hzCmcrChangesLastAfter.size() != 0) {
                if (hzCmcrChangeDao.insertBeforeList(hzCmcrChangesLastAfter) != hzCmcrChangesLastAfter.size()) {
                    result.put("status", false);
                    result.put("msg", "跟变更前主数据失败");
                }
            }
            //跟新变更前从数据
            if (hzCmcrDetailChangesLastAfter != null && hzCmcrDetailChangesLastAfter.size() != 0) {
                if (hzCmcrDetailChangeDao.insertDetailBeforeList(hzCmcrDetailChangesLastAfter) != hzCmcrDetailChangesLastAfter.size()) {
                    result.put("status", false);
                    result.put("msg", "跟变更前从数据失败");
                }
            }
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
        model.addAttribute("vwoInfo", vwoInfo);
        model.addAttribute("href", "returnToVwoFromList");
        model.addAttribute("url", "getInformChangers");
        model.addAttribute("vwo", id);
        model.addAttribute("influenceDept", influenceDept);
        model.addAttribute("influenceUser", influenceUser);
        model.addAttribute("vwoType", vwoType);
        return true;
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
            result.put("msg", "发布成功");
        } else {
            result.put("msg", "发布失败");
        }
        result.put("status", status);
        return result;
    }

    @Autowired
    FeatureProcessManager featureProcessManager;
    @Autowired
    ModelColorProcessManager modelColorProcessManager;

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
}
