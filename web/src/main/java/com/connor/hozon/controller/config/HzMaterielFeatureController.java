/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.config;

import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.common.util.UUIDHelper;
import cn.net.connor.hozon.dao.dao.configuration.derivative.*;
import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureDao;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeDataRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.*;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeature;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.pojo.configuration.modelColor.HzCfg0ModelColor;
import cn.net.connor.hozon.dao.pojo.main.HzFactory;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.services.request.configuration.derivative.HzDerivativeRequestDTO;
import cn.net.connor.hozon.services.service.depository.project.impl.HzSuperMaterielServiceImpl;
import cn.net.connor.hozon.services.service.main.HzMainConfigService;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.dao.configuration.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.service.configuration.derivative.HzConfigModelFeatureService;
import com.connor.hozon.service.configuration.derivative.HzDerivativeService;
import com.connor.hozon.service.configuration.feature.impl.FeatureServiceImpl;
import com.connor.hozon.service.configuration.feature.impl.FeatureValueServiceImpl;
import com.connor.hozon.service.configuration.model.impl.HzCfg0ModelRecordServiceImpl;
import cn.net.connor.hozon.services.common.enumtype.ChangeTableNameEnum;
import com.connor.hozon.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.resources.mybatis.factory.HzFactoryDAO;
import integration.controller.ExtraIntegrateController;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static cn.net.connor.hozon.common.util.StringUtils.checkString;


/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 配置物料特性表
 * 配置管理controller的所有返回消息字段key都是msg
 * 配置管理controller的所有返回成功标志字段key都是status
 * 如发现不一致需要特殊处理
 * 已完成注释
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Controller
@RequestMapping("/materielV2")
@Slf4j
public class HzMaterielFeatureController extends ExtraIntegrateController {
    /*** 族层服务*/
    @Autowired
    private FeatureServiceImpl hzCfg0OptionFamilyService;
    /*** 颜色车型dao层*/
    @Autowired
    private HzCfg0ModelColorDao hzCfg0ModelColorDao;
    /*** 配置值服务层*/
    @Autowired
    private FeatureValueServiceImpl hzFeatureServiceImpl;
    /*** 超级物料服务层*/
    @Autowired
    private HzSuperMaterielServiceImpl hzSuperMaterielServiceImpl;
    /*** 车型模型服务层*/
    @Autowired
    private HzCfg0ModelRecordServiceImpl hzCfg0ModelRecordService;
    /*** 配置主模型服务层*/
    @Autowired
    private HzMainConfigService hzMainConfigService;
    /*** 衍生物料详情数据服务*/
    @Autowired
    private HzConfigModelFeatureService hzConfigModelFeatureService;
    /*** 工厂*/
    @Autowired
    private HzFactoryDAO hzFactoryDAO;
    /***配置物料特性服务层，已集成在一个新的服务上*/
    @Autowired
    private HzDerivativeService hzDerivativeService;
    /**衍生物料基本信息dao层*/
    @Autowired
    private HzDerivativeMaterielBasicDao hzDerivativeMaterielBasicDao;
    /**衍生物料详情dao层*/
    @Autowired
    private HzDerivativeMaterielDetailDao hzDerivativeMaterielDetailDao;
    /**主数据变更**/
    @Autowired
    private HzDerivativeMaterielBasicChangeDao hzDerivativeMaterielBasicChangeDao;
    /**从数据变更**/
    @Autowired
    private HzDerivativeMaterielDetailChangeDao hzDerivativeMaterielDetailChangeDao;
    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;
    @Autowired
    private HzFeatureDao hzFeatureDao;

    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;

    @Autowired
    private HzCfg0ModelGroupDao hzCfg0ModelGroupDao;

    /**
     * 根据项目的puid，获取到配置物料特性表的列设置，配置物料特性表title与配色方案一样动态产生
     *
     * @param projectPuid 项目puid
     * @return 列信息
     */
    @RequestMapping("/loadColumnByProjectPuid2")
    @ResponseBody
    public Map<String, Object> getColumn2(@RequestParam("projectPuid") String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        List<String> column = new ArrayList<>();
        List<String> _result;
        if ((_result = hzCfg0OptionFamilyService.getColumnNew2(projectPuid, "<br/>")) != null) {
            column.addAll(_result);
            //附加列信息
            // appendColumn(column);
            result.put("status", true);
            result.put("data", column);
        } else {
            result.put("status", false);
        }
        return result;
    }

    /**
     * 单独添加列信息
     *
     * @param column
     */
    private void appendColumn(List<String> column) {
        //添加中文描述
        column.add("中文描述");
        //添加单车配置吗
        column.add("单车配置码");
    }

    /**
     * 修改衍生物料基础数据
     *
     * @param projectUid         项目UID
     * @param puid               基本车型模型puid
     * @param puidOfModelFeature 衍生物料的详情数据，一开始时需要将衍生物料基础数据传到SAP(去你妈的SAP)，所以做了一个详情表
     *                           但是现在只能作为一个附加的表作为保留表存在数据库中，这个表已经不再存储衍生物料详情数据
     *                           其puidOfModelFeature字段对应上的时衍生物料主数据主键
     * @param page               申请页面，目前只能申请"model"页面，不能再申请"superMateriel"修改超级物料号页面
     *                           超级物料号可以直接从基本数据中继承并修改
     * @return 修改衍生物料基础数据页面
     */
    @RequestMapping("/modifyPage")
    public String modPage(@RequestParam String projectUid, @RequestParam String puid, @RequestParam String puidOfModelFeature, @RequestParam String page, Model model) {
        //啥也不做
        if (page == null || puid == null)
            ;
            //修改基本信息
        else if ("model".equals(page)) {
            HzCfg0ModelRecord modelRecord = hzCfg0ModelRecordService.doGetById(puid);
            HzCfg0ModelFeature hzCfg0ModelFeature;
            //判断是否为空
            if (puidOfModelFeature == null || "".equals(puidOfModelFeature))
                hzCfg0ModelFeature = new HzCfg0ModelFeature();
            else
                hzCfg0ModelFeature = hzConfigModelFeatureService.doSelectByPrimaryKeyWithFactoryCode(puidOfModelFeature);
            //没有找到
            if (hzCfg0ModelFeature == null)
                hzCfg0ModelFeature = new HzCfg0ModelFeature();

            HzMaterielRecord sm = hzSuperMaterielServiceImpl.doSelectByProjectPuid(projectUid);

            HzMainConfig hzMainConfig = hzMainConfigService.selectByProjectId(projectUid);
            String groupName = hzCfg0ModelGroupDao.selectGroupNameByMainUid(hzMainConfig.getId());

            model.addAttribute("groupName",groupName);
            model.addAttribute("entity", modelRecord);
            model.addAttribute("modelFeature", hzCfg0ModelFeature);
            model.addAttribute("projectUid", projectUid);
            model.addAttribute("superMateriel", sm == null ? "" : sm.getpMaterielCode());

            model.addAttribute("action", "./materielV2/saveModelBasic");
            return "cfg/materielFeature/updateModelBasic";
        }
        //修改超级物料，不再使用
        else if ("superMateriel".equals(page)) {
            HzMainConfig mainRecord = hzMainConfigService.doGetByPrimaryKey(puid);
            if (mainRecord != null) {
                HzMaterielRecord superMateriel = hzSuperMaterielServiceImpl.doSelectByProjectPuid(mainRecord.getProjectId());
                if (superMateriel == null) {
                    superMateriel = new HzMaterielRecord();
                }
                //设置项目puid
                if (superMateriel.getpPertainToProjectPuid() == null || "".equals(superMateriel.getpPertainToProjectPuid())) {
                    superMateriel.setpPertainToProjectPuid(mainRecord.getProjectId());
                }
                model.addAttribute("entity", superMateriel);
                model.addAttribute("action", "./materielV2/updateSuperMateriel");
                return "cfg/materielFeature/updateSuperMateriel";
            }
        }
        model.addAttribute("msg", "找不到选中行的详细数据，请联系管理员");
        return "errorWithEntity";
    }

    /**
     * 添加单个衍生物料数据
     * 在返回页面前，先检查是否选中了项目，并且项目下是否添加<strong>至少1个</strong>配色方案:N，<strong>至少1个基础车型:M</strong>
     * 衍生物料最大数量为配色方案个数=N*M
     *
     * @param projectUid 项目UID，主键
     * @return 添加单个衍生物料页面
     */
    @RequestMapping(value = "/composePage", method = RequestMethod.GET)
    public String composePage(@RequestParam String projectUid, Model model) {
        if (!checkString(projectUid)) {
            model.addAttribute("msg", "请选择一个项目再操作");
            return "errorWithEntity";
        }
        List<HzCfg0ModelColor> colorModels = hzCfg0ModelColorDao.selectAll(projectUid);
        if (colorModels == null || colorModels.size() <= 0) {
            model.addAttribute("msg", "没有配色方案，请至少配置一个配色方案");
            return "errorWithEntity";
        }
        List<HzCfg0ModelRecord> models = hzCfg0ModelRecordService.selectByProjectUid(projectUid);
        if (models == null || models.size() <= 0) {
            model.addAttribute("msg", "没有车型模型，请至少添加一个车型模型");
            return "errorWithEntity";
        }
        HzMaterielRecord sm = hzSuperMaterielServiceImpl.doSelectByProjectPuid(projectUid);
        model.addAttribute("colorModels", colorModels);
        model.addAttribute("models", models);
        model.addAttribute("projectUid", projectUid);
        model.addAttribute("sm", sm);
        model.addAttribute("action", "./materielV2/saveCompose");

        //获取项目主数据
        HzMainConfig hzMainConfig = hzMainConfigService.selectByProjectId(projectUid);
        //获取族
        String groupName = hzCfg0ModelGroupDao.selectGroupNameByMainUid(hzMainConfig.getId());
        model.addAttribute("modelGroup",groupName);
        return "cfg/materielFeature/add";
    }

    /**
     * 保存1条衍生物料数据
     *
     * @param hzDerivativeRequestDTO 前端接收的衍生物料数据
     * @return
     */
    @RequestMapping(value = "/saveCompose", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveCompose(@RequestBody HzDerivativeRequestDTO hzDerivativeRequestDTO) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        result.put("msg", "新增衍生物料成功");
        if (hzDerivativeRequestDTO == null) {
            result.put("status", false);
            return result;
        }

        hzDerivativeService.saveDerivative(hzDerivativeRequestDTO, result);
        //获取项目主数据
        HzMainConfig hzMainConfig = hzMainConfigService.selectByProjectId(hzDerivativeRequestDTO.getProjectUid());
        //获取族
        String groupName = hzCfg0ModelGroupDao.selectGroupNameByMainUid(hzMainConfig.getId());
        if(groupName==null){
            HzCfg0ModelGroup hzCfg0ModelGroup = new HzCfg0ModelGroup();
            hzCfg0ModelGroup.setGroupDesc(hzDerivativeRequestDTO.getModelGroup());
            hzCfg0ModelGroup.setGroupName(hzDerivativeRequestDTO.getModelGroup());
            hzCfg0ModelGroup.setMainUid(hzMainConfig.getId());
            hzCfg0ModelGroup.setId(UUIDHelper.generateUpperUid());
            int insertNum = hzCfg0ModelGroupDao.insert(hzCfg0ModelGroup);
            if(insertNum<=0){
                result.put("msg", "新增模型族失败");
                result.put("status", false);
                return result;
            }
        }
        return result;
    }

    /**
     * 删除衍生物料
     * <p>
     * 删除衍生物料实际上根据主键删除即可，所以传到该方法的应该包含某个衍生物料的主键
     *
     * @param delDtos 一组衍生物料数据，衍生物料数据中至少包含puidOfModelFeature字段，以该字段作为主键进行删除操作
     * @return
     */
    @RequestMapping(value = "/deleteCompose", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteCompose(@RequestBody List<HzComposeDelDto> delDtos) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        result.put("msg", "请至少选择一个衍生物料进行操作");
        if (delDtos == null || delDtos.size() <= 0) {
            result.put("status", false);
            return result;
        }
        hzDerivativeService.deleteDerivative(delDtos, result);
        return result;
    }

    @RequestMapping(value = "/deleteVehicleFake", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteVehicleFake(@RequestBody List<HzComposeDelDto> delDtos) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        result.put("msg", "删除成功");
        if (delDtos == null || delDtos.size() <= 0) {
            result.put("status", false);
            result.put("msg", "请至少选择一个衍生物料进行操作");
            return result;
        }
        List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans = hzDerivativeMaterielBasicChangeDao.selectLastById(delDtos);
        if(hzDerivativeMaterielBasicChangeBeans !=null&& hzDerivativeMaterielBasicChangeBeans.size()>0){
            List<HzComposeDelDto> hzComposeDelDtosUpdate = new ArrayList<>();
            List<HzComposeDelDto> hzComposeDelDtosDelete = new ArrayList<>();
            for(HzComposeDelDto hzComposeDelDto : delDtos){
                boolean flag = false;
                for(HzDerivativeMaterielBasicChangeBean hzDerivativeMaterielBasicChangeBean : hzDerivativeMaterielBasicChangeBeans){
                    if(hzDerivativeMaterielBasicChangeBean.getDmbSrcId().equals(hzComposeDelDto.getBasicId())){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    hzComposeDelDtosUpdate.add(hzComposeDelDto);
                }else {
                    hzComposeDelDtosDelete.add(hzComposeDelDto);
                }
            }
            if(hzComposeDelDtosUpdate!=null&&hzComposeDelDtosUpdate.size()>0){
                try {
                    hzDerivativeService.deleteVehicleFake(hzComposeDelDtosUpdate);
                }catch (Exception e){
                    result.put("status", false);
                    result.put("msg", "删除失败");
                    return result;
                }
            }
            if(hzComposeDelDtosDelete!=null&&hzComposeDelDtosDelete.size()>0){
                for(HzComposeDelDto hzComposeDelDto : hzComposeDelDtosDelete) {
                    if(!hzConfigModelFeatureService.doDeleteByPrimaryKey(hzComposeDelDto.getPuidOfModelFeature())){
                        result.put("status", false);
                        result.put("msg", "删除失败");
                        return result;
                    }
                }
            }
        }else {
            for(HzComposeDelDto hzComposeDelDto : delDtos) {
                if(!hzConfigModelFeatureService.doDeleteByPrimaryKey(hzComposeDelDto.getPuidOfModelFeature())){
                    result.put("status", false);
                    result.put("msg", "删除失败");
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 加载所有的衍生物料数据，这些衍生物料数据title需要配合{@link HzMaterielFeatureController#getColumn2(String)}进行使用
     * 只有定义了title的字段才能准确的由bootstrap table自动匹配到各个单元格中
     *
     * @param projectPuid 项目UID
     * @return
     */
    @RequestMapping(value = "/loadComposes", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadComposes(@RequestParam String projectPuid) {
        return hzDerivativeService.loadProjectsDerivatives(projectPuid, new QueryBase());
    }

    /**
     * 一键生成所有衍生物料按钮功能，因为一键生成功能有点繁琐，所以一键生成过程中可能出现验证不通过的情况，但是实际数据已保存到数据库中
     * 一旦发现数据验证不通过，则需要手动删除已经生成的衍生物料并从全配置BOM一级清单中重新配对配置-2Y-基础车型的打点图关系，直到成功为止
     *
     * @param projectId 当前项目的UID
     * @return
     */
    @RequestMapping("/saveCompose")
    @ResponseBody
    public JSONObject saveCompose(String projectId) {
        return hzDerivativeService.saveProjectDerivatives(projectId);
    }

    /**
     * 保存衍生物料基本信息
     *
     * @param projectUid    项目UID
     * @param superMateriel 超级物料号
     * @param modelFeature  衍生物料详情数据，该部分数据已经不再存储发送到SAP的数据，只存储了基本信息和工厂数据而已了
     * @return 保存成功与否标识和消息
     */
    @RequestMapping("/saveModelBasic")
    @ResponseBody
    public JSONObject saveModelBasic(
            @RequestParam String projectUid,
            @RequestParam String superMateriel,
            @RequestParam String modelGroup,
            @RequestBody HzCfg0ModelFeature modelFeature)
    {
        JSONObject _result = new JSONObject();
        Date now = new Date();
        User user = UserInfo.getUser();

        if (modelFeature.getPuid() == null || "".equals(modelFeature.getPuid())) {
            modelFeature.setPuid(UUID.randomUUID().toString());
            if (hzConfigModelFeatureService.doInsert(modelFeature)) {
                _result.put("status", true);
                _result.put("msg", "新增衍生物料基本数据成功");
            } else {
                _result.put("status", false);
                _result.put("msg", "新增衍生物料基本数据失败");
            }
        } else if (null != (hzConfigModelFeatureService.doSelectByPrimaryKey(modelFeature.getModelFeaturePuid()))) {
            modelFeature.setPuid(modelFeature.getModelFeaturePuid());
            modelFeature.setMaterielType("A001");
            HzFactory factory = hzFactoryDAO.findFactory("", checkString(modelFeature.getFactoryCode()) ? modelFeature.getFactoryCode() : "1001");
            HzFactory factory1001 = hzFactoryDAO.findFactory(null, "1001");
            if (factory == null) {
                log.warn("自动创建工厂" + modelFeature.getFactoryCode());
                factory = new HzFactory();
                factory.setpFactoryCode(modelFeature.getFactoryCode());
                factory.setPuid(UUIDHelper.generateUpperUid());
                factory.setpCreateTime(now);
                factory.setpCreateName(user.getUsername());
                factory.setpUpdateTime(now);
                factory.setpUpdateName(user.getUsername());
                factory.setpFactoryDesc("由系统进行自动创建");
                if (hzFactoryDAO.insert(factory) < 0) {
                    log.error("自动创建工厂" + modelFeature.getFactoryCode() + "失败");
                    _result.put("status", false);
                    _result.put("msg", "没有找到工厂" + modelFeature.getFactoryCode());
                    return _result;
                }
            }
            modelFeature.setFactoryCode(factory == null ? factory1001.getPuid() : factory.getPuid());
            if (hzConfigModelFeatureService.doUpdateByPrimaryKey(modelFeature)) {
                _result.put("status", true);
                _result.put("msg", "更新衍生物料基本数据成功");
            } else {
                _result.put("status", false);
                _result.put("msg", "更新衍生物料基本数据失败");
            }

            HzDerivativeMaterielBasic hzDerivativeMaterielBasic = new HzDerivativeMaterielBasic();
            hzDerivativeMaterielBasic.setDmbModelFeatureUid(modelFeature.getPuid());
            if(hzDerivativeMaterielBasicDao.updateStatusUpdate(hzDerivativeMaterielBasic)<=0){
                _result.put("status", true);
                _result.put("msg", "更新衍生物料状态失败");
            }
            /**
             * 更新超级物料
             */
            HzMaterielRecord sm = hzSuperMaterielServiceImpl.doSelectByProjectPuid(projectUid);
            if (checkString(superMateriel)) {
                if (null == sm) {
                    log.warn("没有超级物料号，进行申请");
                    sm = new HzMaterielRecord();
                    sm.setpMaterielCode(superMateriel);
                    sm.setPuid(UUIDHelper.generateUpperUid());
                    sm.setpFactoryPuid(factory == null ? factory1001.getPuid() : factory.getPuid());
                    sm.setpPertainToProjectPuid(projectUid);
                    if (!hzSuperMaterielServiceImpl.doInsertOne(sm)) {
                        log.error("存储超级物料号失败");
                    }
                }
                if (!superMateriel.equals(sm.getpMaterielCode())) {
                    log.warn("已有超级物料号，进行更新超级物料号");
                    sm.setpMaterielCode(superMateriel);
                    if (!hzSuperMaterielServiceImpl.doUpdateByPrimaryKey(sm)) {
                        log.error("更新超级物料号失败");
                    }
                }
            }

            /***************修改模型族*******************************/
            if(modelGroup!=null&&!"".equals(modelGroup)) {
                HzMainConfig hzMainConfig = hzMainConfigService.selectByProjectId(projectUid);
                HzCfg0ModelGroup hzCfg0ModelGroup = new HzCfg0ModelGroup();
                hzCfg0ModelGroup.setMainUid(hzMainConfig.getId());
                hzCfg0ModelGroup.setGroupName(modelGroup);
                hzCfg0ModelGroup.setGroupDesc(modelGroup);
                if (hzCfg0ModelGroupDao.updateByMainId(hzCfg0ModelGroup) <= 0) {
                    _result.put("status", false);
                    _result.put("msg", "更新模型族数据失败");
                    return _result;
                }
            }
            return _result;
        } else {
            _result.put("status", false);
            _result.put("msg", "没有找到衍生物料" + modelFeature.getMaterialCode());

        }
        return _result;
    }

    /**
     * 衍生物料变更
     * @param params
     * @return
     */
    @RequestMapping("/getVWO")
    @ResponseBody
    public JSONObject getVWO(@RequestBody Map<String, Object> params){
        JSONObject result = new JSONObject();
//        List<HashMap<String, String>> rows = (List<HashMap<String, String>>) params.get("rows");
        //基本信息basicId
        List<String> puids = (List<String>)params.get("puids");
        //变更表单Id
         Long changeFromId = Long.valueOf((String)params.get("changeFromId"));
        //查询主数据
        List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics = hzDerivativeMaterielBasicDao.selectByPuids(puids);
        //查询从数据
        List<HzDerivativeMaterielDetail> hzDerivativeMaterielDetails = hzDerivativeMaterielDetailDao.selectByBasics(hzDerivativeMaterielBasics);

        //根据主数据生成变更主数据
        List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans = new ArrayList<HzDerivativeMaterielBasicChangeBean>();
        for(HzDerivativeMaterielBasic hzDerivativeMaterielBasic : hzDerivativeMaterielBasics){
            HzDerivativeMaterielBasicChangeBean hzDerivativeMaterielBasicChangeBean = new HzDerivativeMaterielBasicChangeBean();
            hzDerivativeMaterielBasicChangeBean.setFormId(changeFromId);
            hzDerivativeMaterielBasicChangeBean.srcSetChange(hzDerivativeMaterielBasic,UserInfo.getUser().getLogin());
            hzDerivativeMaterielBasicChangeBean.setDmbChangeStatus(0);
            hzDerivativeMaterielBasicChangeBeans.add(hzDerivativeMaterielBasicChangeBean);
        }
        int basicInsertNum = hzDerivativeMaterielBasicChangeDao.insertList(hzDerivativeMaterielBasicChangeBeans);
        if(basicInsertNum< hzDerivativeMaterielBasicChangeBeans.size()){
            result.put("status",false);
            result.put("msg","跟新变更主数据失败");
            return result;
        }
        List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeanList = hzDerivativeMaterielBasicChangeDao.selectByFormid(changeFromId);
        Map<Long,Long> idMap = new HashMap<>();
        for(HzDerivativeMaterielBasicChangeBean hzDerivativeMaterielBasicChangeBean : hzDerivativeMaterielBasicChangeBeanList){
            idMap.put(hzDerivativeMaterielBasicChangeBean.getDmbSrcId(), hzDerivativeMaterielBasicChangeBean.getId());
        }
        //根据从数据生成变更从数据
        List<HzDerivativeMaterielDetailChangeBean> hzDerivativeMaterielDetailChangeBeans = new ArrayList<HzDerivativeMaterielDetailChangeBean>();
        List<HzFeature> familiesBefor = hzFeatureDao.selectByDM(hzDerivativeMaterielDetails);
        Map<String,HzFeature> map = new HashMap<>();
        for(HzFeature hzFeature : familiesBefor){
            map.put(hzFeature.getId(), hzFeature);
        }
        for(HzDerivativeMaterielDetail hzDerivativeMaterielDetail : hzDerivativeMaterielDetails){
            HzDerivativeMaterielDetailChangeBean hzDerivativeMaterielDetailChangeBean = new HzDerivativeMaterielDetailChangeBean();
            hzDerivativeMaterielDetailChangeBean.setFormId(changeFromId);
            hzDerivativeMaterielDetailChangeBean.srcSetChange(hzDerivativeMaterielDetail,UserInfo.getUser().getLogin());
            hzDerivativeMaterielDetailChangeBean.setDmbChangeBasicId(idMap.get(hzDerivativeMaterielDetail.getDmdDmbId()));
            HzFeature hzFeature = map.get(hzDerivativeMaterielDetail.getDmdCfg0FamilyUid());
            hzDerivativeMaterielDetailChangeBean.setTitle(hzFeature.getFeatureDesc() + "<br/>" + hzFeature.getFeatureCode());
            hzDerivativeMaterielDetailChangeBeans.add(hzDerivativeMaterielDetailChangeBean);
        }
        int detialnum = hzDerivativeMaterielDetailChangeDao.insertList(hzDerivativeMaterielDetailChangeBeans);
        if(detialnum< hzDerivativeMaterielDetailChangeBeans.size()){
            result.put("status",false);
            result.put("msg","跟新变更从数据失败");
            return result;
        }

        //修改源主数据
        for(HzDerivativeMaterielBasic hzDerivativeMaterielBasic : hzDerivativeMaterielBasics){
            hzDerivativeMaterielBasic.setDmbStatus(10);
            hzDerivativeMaterielBasic.setChangeOrderId(changeFromId);
        }
        int srcBasicUpdateNum = hzDerivativeMaterielBasicDao.updateByBasicList(hzDerivativeMaterielBasics);
        int srcBasicUpdateChangIdNum = hzDerivativeMaterielBasicDao.updateByBasicListChangId(hzDerivativeMaterielBasics);

        //流程绑定人员
        User user = UserInfo.getUser();
        HzChangeDataRecord hzChangeDataRecord = new HzChangeDataRecord();
        hzChangeDataRecord.setApplicantId(Long.valueOf(user.getId()));
        hzChangeDataRecord.setOrderId(changeFromId);
        hzChangeDataRecord.setTableName(ChangeTableNameEnum.HZ_DM_BASIC_CHANGE.getTableName());
        int insertNum = hzChangeDataRecordDAO.insert(hzChangeDataRecord);
        if(insertNum<=0){
            result.put("status", false);
            result.put("msg", "绑定人员失败");
            return result;
        }
        result.put("status",true);
        result.put("msg","发起VWO流程成功");
        return result;
    }

    /**
     * 跳转到选择变更表单页面
     * @param projectUid
     * @param puids
     * @param titles
     * @param model
     * @return
     */
    @RequestMapping("/setChangeFromPage")
    public String setChangeFromPage(String projectUid, String puids, String titles, Model model){
        List<String> puidList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        String[] puidArr = puids.split(",");
        String[] titleArr = titles.split(",");
        for(String puid : puidArr){
            puidList.add(puid);
        }
        for(String title : titleArr){
            titleList.add(title);
        }

        List<HzChangeOrderRecord> hzChangeOrderRecordList = hzChangeOrderDAO.findHzChangeOrderRecordByProjectId(projectUid);
        model.addAttribute("changeFroms",hzChangeOrderRecordList);
        model.addAttribute("titles",titleList);
        model.addAttribute("puids",puidList);
        return  "cfg/materielFeature/MaterieFeatureSetChangeFrom";
    }

    @RequestMapping("goBackData")
    @ResponseBody
    public JSONObject goBackData(@RequestBody List<HzComposeDelDto> delDtos){
        JSONObject result = new JSONObject();
        result.put("status",true);

        Iterator<HzComposeDelDto> iterator = delDtos.iterator();
        /*********根据主数据ID查出所有主数据**************/
        List<String> puids = new ArrayList<>();
        for(HzComposeDelDto hzComposeDelDto : delDtos){
            puids.add(String.valueOf(hzComposeDelDto.getBasicId()));
        }
        List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasics = hzDerivativeMaterielBasicDao.selectByPuids(puids);
        /*********找出主数据所有为删除状态的数据*******************/
        List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasicsDelete = new ArrayList<>();
        for(HzDerivativeMaterielBasic hzDerivativeMaterielBasic : hzDerivativeMaterielBasics){
            if(hzDerivativeMaterielBasic.getDmbStatus()!=null&&2==hzDerivativeMaterielBasic.getDmbStatus()){
                hzDerivativeMaterielBasic.setDmbStatus(1);
                hzDerivativeMaterielBasicsDelete.add(hzDerivativeMaterielBasic);
                while (iterator.hasNext()){
                    HzComposeDelDto hzComposeDelDto = iterator.next();
                    if(hzComposeDelDto.getBasicId().equals(hzDerivativeMaterielBasic.getId())){
                        iterator.remove();
                        break;
                    }
                }
            }
        }
        if(hzDerivativeMaterielBasicsDelete!=null&&hzDerivativeMaterielBasicsDelete.size()>0){
            int deleteNum = hzDerivativeMaterielBasicDao.updateStatus(hzDerivativeMaterielBasicsDelete);
            if(deleteNum<=0){
                result.put("status",false);
                result.put("msg","撤销删除数据失败");
                return result;
            }
        }

        /**********找出修改的数据******************/
        if(delDtos==null||delDtos.size()==0){
            return result;
        }
        List<HzDerivativeMaterielBasic> hzDerivativeMaterielBasicsUpdate= new ArrayList<>();
        List<HzDerivativeMaterielDetail> hzDerivativeMaterielDetailsUpdate = new ArrayList<>();
        //根据主数据id查找最近一次有效数据
        List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans = new ArrayList<>();
        hzDerivativeMaterielBasicChangeBeans = hzDerivativeMaterielBasicChangeDao.selectLastById(delDtos);
        //根据主数据找到从数据
        List<HzDerivativeMaterielDetailChangeBean> hzDerivativeMaterielDetailChangeBeans = new ArrayList<>();
        if(hzDerivativeMaterielBasicChangeBeans !=null&& hzDerivativeMaterielBasicChangeBeans.size()!=0){
            hzDerivativeMaterielDetailChangeBeans = hzDerivativeMaterielDetailChangeDao.selectByBasic(hzDerivativeMaterielBasicChangeBeans);
            //根据变更主数据生成源主数据
            for(HzDerivativeMaterielBasicChangeBean hzDerivativeMaterielBasicChangeBean : hzDerivativeMaterielBasicChangeBeans){
                HzDerivativeMaterielBasic hzDerivativeMaterielBasic = hzDerivativeMaterielBasicChangeBean.getHzDerivativeMaterielBasic();
                hzDerivativeMaterielBasic.setDmbStatus(0);
                hzDerivativeMaterielBasicsUpdate.add(hzDerivativeMaterielBasic);
            }
            if(hzDerivativeMaterielBasicsUpdate!=null&&hzDerivativeMaterielBasicsUpdate.size()>0){
                try {
                    hzDerivativeMaterielBasicDao.updateByBasicAll(hzDerivativeMaterielBasicsUpdate);
                }catch (Exception e){
                    result.put("status",false);
                    result.put("msg","撤销修改基本数据失败");
                    return result;
                }
            }
            //根据变更从数据生成源从数据
            for(HzDerivativeMaterielDetailChangeBean hzDerivativeMaterielDetailChangeBean : hzDerivativeMaterielDetailChangeBeans){
                HzDerivativeMaterielDetail hzDerivativeMaterielDetail = hzDerivativeMaterielDetailChangeBean.getHzDerivativeMaterielDetail();
                hzDerivativeMaterielDetailsUpdate.add(hzDerivativeMaterielDetail);
            }
            if(hzDerivativeMaterielDetailsUpdate!=null&&hzDerivativeMaterielDetailsUpdate.size()>0) {
                try {
                    hzDerivativeMaterielDetailDao.updateByDetailAll(hzDerivativeMaterielDetailsUpdate);
                }catch (Exception e){
                    result.put("status", false);
                    result.put("msg", "撤销修改从数据失败");
                    return result;
                }
            }
            //删除修改数据，只留下新增数据
            Iterator<HzComposeDelDto> iteratorUpdate = delDtos.iterator();
            for(HzDerivativeMaterielBasicChangeBean hzDerivativeMaterielBasicChangeBean : hzDerivativeMaterielBasicChangeBeans){
                while (iteratorUpdate.hasNext()){
                    HzComposeDelDto hzComposeDelDto = iteratorUpdate.next();
                    if(hzComposeDelDto.getBasicId().equals(hzDerivativeMaterielBasicChangeBean.getDmbSrcId())){
                        iteratorUpdate.remove();
                        break;
                    }
                }
            }
        }

        /****************删除所有新增的主从数据************************/
        if(delDtos==null||delDtos.size()==0){
            return result;
        }
        for(HzComposeDelDto hzComposeDelDto : delDtos){
            if(!hzConfigModelFeatureService.doDeleteByPrimaryKey(hzComposeDelDto.getPuidOfModelFeature())){
                result.put("status", false);
                result.put("msg", "撤销新增基本数据失败");
                return result;
            }
        }
        return  result;
    }
    /**********************************************废除方法****************************************/
//    /**
//     * 修改超级物料特性，已废除，不再使用
//     *
//     * @param superMateriel
//     * @return
//     */
//    @RequestMapping("/updateSuperMateriel")
//    @ResponseBody
//    @Deprecated
//    public boolean updateSuperMateriel(@RequestBody HzMaterielRecord superMateriel) {
//        if (superMateriel == null)
//            return false;
//        if (superMateriel.getpPertainToProjectPuid() == null || "".equals(superMateriel.getpPertainToProjectPuid()))
//            return false;
//        HzMaterielRecord sm = hzSuperMaterielServiceImpl.doSelectByProjectPuid(superMateriel.getpPertainToProjectPuid());
//        if (sm == null && (superMateriel.getPuid() == null || "".equals(superMateriel.getPuid()))) {
//            superMateriel.setPuid(UUID.randomUUID().toString());
//            return hzSuperMaterielServiceImpl.insertOne(superMateriel);
//        } else if (sm != null) {
//            superMateriel.setPuid(sm.getPuid());
//            return hzSuperMaterielServiceImpl.doUpdateByPrimaryKey(superMateriel);
//        }
//        return false;
//    }

//    /**
//     * 已废除，不再使用
//     *
//     * @param params
//     * @return
//     */
//    @Deprecated
//    @RequestMapping("/addVehicleModel")
//    @ResponseBody
//    public boolean addVehicleModel(@RequestBody Map<String, String> params) {
//        if (params != null) {
//            if (!params.containsKey("pCfg0ModelOfMainRecord")) {
//                return false;
//            } else {
//                HzCfg0ModelRecord modelRecord = new HzCfg0ModelRecord();
//                HzCfg0ModelFeature modelDetail = new HzCfg0ModelFeature();
//                List<HzCfg0ToModelRecord> toInsert = new ArrayList<>();
//                //生成UID
//                modelRecord.setPuid(UUIDHelper.generateUpperUid());
//                //生成UID
//                modelDetail.setPuid(UUIDHelper.generateUpperUid());
//                //设置归属车型
//                modelDetail.setpPertainToModel(modelRecord.getPuid());
//                for (String key : params.keySet()) {
//                    String value = params.get(key);
//                    if ("pCfg0ModelOfMainRecord".equals(key)) {
//                        //设置归属主配置
//                        modelRecord.setModelMainRecord(params.get("pCfg0ModelOfMainRecord"));
//                    } else if ("objectName".equals(key)) {
//                        //设置车型名
//                        modelRecord.setObjectName(params.get("objectName"));
//                    } else if ("objectDesc".equals(key)) {
//                        //设置车型描述
//                        modelRecord.setObjectDesc(params.get("objectDesc"));
//                    } else if ("pCfg0ModelBasicDetail".equals(key)) {
//                        //设置基本信息代码
//                        modelRecord.setModelBasicDetail(params.get("pCfg0ModelBasicDetail"));
//                    } else if ("pFeatureCnDesc".equals(key)) {
//                        //中文描述
//                        modelDetail.setpFeatureCnDesc(params.get("pFeatureCnDesc"));
//                    } else if ("pFeatureSingleVehicleCode".equals(key)) {
//                        //设置单车配置码
//                        modelDetail.setpFeatureSingleVehicleCode(params.get("pFeatureSingleVehicleCode"));
//                    } else {
//                        HzFeatureValue addedRecord = hzFeatureServiceImpl.selectOneByPuid(value);
//                        HzCfg0ToModelRecord hzCfg0ToModelRecord = new HzCfg0ToModelRecord();
//                        if (addedRecord == null) {
////                            try {
////                                throw new Exception("无法找到特性值，请检查数据");
////                            } catch (Exception e) {
////                                e.printStackTrace();
////                                return false;
////                            }
//                            continue;
//                        } else {
//                            hzCfg0ToModelRecord.setpCfg0IdRecord(addedRecord.getPuid());
//                            hzCfg0ToModelRecord.setpCfg0ModelRecord(modelRecord.getPuid());
//                            hzCfg0ToModelRecord.setpCfg0OptionValue(addedRecord.getFeatureValueCode());
//                            hzCfg0ToModelRecord.setpOfCfg0MainRecord(modelRecord.getModelMainRecord());
//                            hzCfg0ToModelRecord.setPuid(UUIDHelper.generateUpperUid());
//                            hzCfg0ToModelRecord.setpParseLogicValue(1);
//                            //插入数据
//                            toInsert.add(hzCfg0ToModelRecord);
////                            hzCfg0ToModelRecordDao.insert(hzCfg0ToModelRecord);
//                        }
//                    }
//                }
//
//                //没有设置归属的颜色车型
//                hzCfg0ModelRecordService.insert(Collections.singletonList(modelRecord));
//                hzConfigModelFeatureService.insert(modelDetail);
//                for (int i = 0; i < toInsert.size(); i++) {
//                    hzCfg0ToModelRecordDao.insert(toInsert.get(i));
//                }
//                //发送到SAP?
//            }
//            return true;
//        } else {
//            return false;
//        }
//    }
    /**********************************************废除方法****************************************/


}
