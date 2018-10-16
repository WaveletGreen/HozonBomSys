package com.connor.hozon.bom.bomSystem.service.vwo;

import com.connor.hozon.bom.bomSystem.controller.vwo.HzVWOProcessController;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzFeatureChangeService;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVWOManagerService;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVwoInfoService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import com.connor.hozon.bom.bomSystem.service.project.HzVehicleService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.dao.OrgGroupDao;
import com.connor.hozon.bom.sys.entity.OrgGroup;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.cfg0.HzCfg0OptionFamily;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;
import sql.pojo.cfg.modelColor.HzCfg0ModelColorDetail;
import sql.pojo.cfg.modelColor.HzCmcrChange;
import sql.pojo.cfg.modelColor.HzCmcrDetailChange;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;
import sql.pojo.cfg.vwo.HzVwoInfo;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;
import sql.pojo.project.HzVehicleRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
                HzVwoInfo hzVwoInfo = generateVwoEntity(user, projectUid, result);
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
        for(HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors){
            if(hzCfg0ModelColor.getCmcrVwoId()!=null){
                result.put("status",false);
                result.put("msg",hzCfg0ModelColor.getpDescOfColorfulModel()+"已发起了VWO流程");
                return  result;
            }
        }
        //源从数据
        List<HzCfg0ModelColorDetail> hzCfg0ModelColorDetails = hzColorModelDao.selectByModelColors(hzCfg0ModelColors);
        //最新的Vwo实体类对象
        HzVwoInfo hzVwoInfo = hzVwoManagerService.generateVwoEntity(user, projectPuid, result);
        //为源主数据添加VWO编码
        for(HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors){
            hzCfg0ModelColor.setCmcrVwoId(hzVwoInfo.getId());
        }
        //根据源主数据生成变更后主数据
        List<HzCmcrChange> hzCmcrChangesAfter = new ArrayList<HzCmcrChange>();
        for(HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors){
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
        for(HzCfg0ModelColorDetail hzCfg0ModelColorDetail : hzCfg0ModelColorDetails){
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
            if(hzCfg0ModelColorDetail.getCfgUid()!=null){
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
        for(HzCfg0ModelColor hzCfg0ModelColor : hzCfg0ModelColors){
            HzCmcrChange hzCmcrChangeQuery = new HzCmcrChange();
            hzCmcrChangeQuery.setCmcrSrcMainCfg(hzCfg0ModelColor.getpCfg0MainRecordOfMC());
            hzCmcrChangeQuery.setCmcrSrcPuid(hzCfg0ModelColor.getPuid());
            hzCmcrChangesLastAfterQuery.add(hzCmcrChangeQuery);
        }
        List<HzCmcrChange> hzCmcrChangesLastAfter = null;
        try {
            hzCmcrChangesLastAfter = hzCmcrChangeDao.selectLastAfter(hzCmcrChangesLastAfterQuery);
        }catch (Exception e){
            result.put("status",false);
            result.put("msg",e.getMessage());
        }
        //查询最近一次变更后从数据
        List<HzCmcrDetailChange> hzCmcrDetailChangesQuery = new ArrayList<HzCmcrDetailChange>();
        for(HzCfg0ModelColorDetail hzCfg0ModelColorDetail : hzCfg0ModelColorDetails){
            HzCmcrDetailChange hzCmcrDetailChange1Query = new HzCmcrDetailChange();
            hzCmcrDetailChange1Query.setCmcrDetailSrcCfgMainUid(hzCfg0ModelColorDetail.getCfgMainUid());
            hzCmcrDetailChange1Query.setCmcrDetailSrcPuid(hzCfg0ModelColorDetail.getPuid());
            hzCmcrDetailChangesQuery.add(hzCmcrDetailChange1Query);
        }
        List<HzCmcrDetailChange> hzCmcrDetailChangesLastAfter = null;
        try {
            hzCmcrDetailChangesLastAfter = hzCmcrDetailChangeDao.selectLastAfter(hzCmcrDetailChangesQuery);
        }catch (Exception e){
            result.put("status",false);
            result.put("msg",e.getMessage());
        }
        //根据最近一次变更后主数据生成变更前主数据
        for(HzCmcrChange hzCmcrChange : hzCmcrChangesLastAfter){
            hzCmcrChange.setCmcrCgVwoId(hzVwoInfo.getId());
        }
        //根据最近一次变更后从数据生成变更前从数据
        for(HzCmcrDetailChange hzCmcrDetailChange : hzCmcrDetailChangesLastAfter){
            hzCmcrDetailChange.setCmcrDetailCgVwoId(hzVwoInfo.getId());
        }


        //跟新数据库
        //跟新源主数据
        if(hzCfg0ModelColorDao.updateListData(hzCfg0ModelColors)<=0){
            result.put("status",false);
            result.put("msg","跟新源主数据失败");
        }
        try {
            //跟新变更后主数据
            if(hzCmcrChangeDao.insertAfterList(hzCmcrChangesAfter)!=hzCmcrChangesAfter.size()){
                result.put("status",false);
                result.put("msg","跟变更后主数据失败");
            }
            //跟新变更后从数据
            if(hzCmcrDetailChangeDao.insertDetailAfterList(hzCmcrDetailChangesAfter)!=hzCmcrDetailChangesAfter.size()){
                result.put("status",false);
                result.put("msg","跟变更后从数据失败");
            }
            //跟新变更前主数据
            if(hzCmcrChangesLastAfter!=null&&hzCmcrChangesLastAfter.size()!=0){
                if(hzCmcrChangeDao.insertBeforeList(hzCmcrChangesLastAfter)!=hzCmcrChangesLastAfter.size()){
                    result.put("status",false);
                    result.put("msg","跟变更前主数据失败");
                }
            }
            //跟新变更前从数据
            if(hzCmcrDetailChangesLastAfter!=null&&hzCmcrDetailChangesLastAfter.size()!=0){
                if(hzCmcrDetailChangeDao.insertDetailBeforeList(hzCmcrDetailChangesLastAfter)!=hzCmcrDetailChangesLastAfter.size()){
                    result.put("status",false);
                    result.put("msg","跟变更前从数据失败");
                }
            }
        }catch (Exception e){
            result.put("status",false);
            result.put("msg",e.getMessage());
        }
        if(result.get("status")==null){
            result.put("status",true);
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
    public HzVwoInfo generateVwoEntity(User user, String projectUid, JSONObject result) {
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
            hzVwoInfo.setVwoCreateDate(now);
            hzVwoInfo.setProjectUid(projectUid);
            hzVwoInfo.setVwoType(1);
            hzVwoInfo.setVwoStatus(1);
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
}
