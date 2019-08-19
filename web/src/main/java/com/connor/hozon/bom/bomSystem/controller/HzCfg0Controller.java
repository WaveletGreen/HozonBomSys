/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import cn.net.connor.hozon.common.util.DateStringUtils;
import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureDao;
import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureValueDao;
import cn.net.connor.hozon.dao.dao.main.HzMainConfigDao;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeature;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.dao.query.configuration.feature.HzFeatureQuery;
import com.connor.hozon.bom.bomSystem.controller.integrate.ExtraIntegrate;
import cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet.HzFullCfgWithCfgDao;
import cn.net.connor.hozon.services.response.configuration.relevance.HzRelevanceResponseDTO;
import cn.net.connor.hozon.common.util.UUIDHelper;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.HzFeatureChangeService;
import integration.service.integrate.SynFeatureService;
import integration.service.integrate.SynRelevanceService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFamilyService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzFeatureService;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.bom.resources.service.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryService;
import cn.net.connor.hozon.dao.pojo.sys.User;
import integration.Author;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgWithCfg;
import cn.net.connor.hozon.dao.pojo.depository.dictionaryLibrary.HzDictionaryLibrary;

import java.util.*;

import static cn.net.connor.hozon.common.util.StringUtils.checkString;


/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 特性controller
 * 配置管理controller的所有返回消息字段key都是msg
 * 配置管理controller的所有返回成功标志字段key都是status
 * 如发现不一致需要特殊处理
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Controller
@RequestMapping("/feature")
@Slf4j
public class HzCfg0Controller extends ExtraIntegrate {
    /***特性值服务*/
    @Autowired
    HzFeatureService hzFeatureService;
    /***主配置Dao层*/
    @Autowired
    HzMainConfigDao hzMainConfigDao;
    /*** 同步特性，不再在当前controller中使用*/
    @Autowired
    SynFeatureService synFeatureService;
    /*** 同步相关性，不再在当前controller中使用*/
    @Autowired
    SynRelevanceService synRelevanceService;
    /***项目下的特性，值针对当前项目的特性，更高一层的是字典库*/
    @Autowired
    HzCfg0OptionFamilyService cfg0OptionFamilyService;
    /*** 特性变更服务，特性的变更数据交由该服务进行操作，实际做操作的还是当前controller*/
    @Autowired
    HzFeatureChangeService hzFeatureChangeService;
    /***这就是他妈的传说中的配置字典库了*/
    @Autowired
    HzDictionaryLibraryService hzDictionaryLibraryService;
    @Autowired
    HzFeatureValueDao hzFeatureValueDao;

    @Autowired
    HzFullCfgWithCfgDao hzFullCfgWithCfgDao;

    @Autowired
    HzFeatureDao hzFeatureDao;

    /**
     * 特性页面初始化显示，查询当前项目下的特性值并进行分页显示到前端
     *
     * @param projectPuid 项目UID
     * @param queryBase   分页操作实体，具体字段{@link HzFeatureQuery}POST进来交由Spring自动高解析，字段对应上即可解析
     * @return 特性值的分页查询数据
     */
    @RequestMapping("/loadFeature")
    @ResponseBody
    public Map<String, Object> loadCfg0(@RequestParam("projectPuid") String projectPuid, HzFeatureQuery queryBase) {
        return hzFeatureService.loadFeature(projectPuid,queryBase);
    }

    /**
     * 跳转到添加页面，该页面所有的数据获取都将来自于配置字典
     *
     * @param projectPuid 项目UID
     * @return 添加特性页面
     */
    @RequestMapping("/addPage2")
    public String addPage2(@RequestParam("projectPuid") String projectPuid, Model model) {
        HzMainConfig mainRecord = hzMainConfigDao.selectByProjectId(projectPuid);
        if (mainRecord == null) {
            return "error";
        }
        model.addAttribute("entity", mainRecord);
        return "cfg/feature/addFeature2";
    }

    /**
     * 添加特性值到当前项目中
     * <p>
     * 先判断当前项目是否存在该特性，存在特不允许添加，反之则允许操作
     * <p>
     * 添加特性值的同时检查项目中是否存在当前特性值父层特性，若没有父层特性，则创建一个新的特性并依附到当前项目下
     * 依附新的特性到当前项目下是因为配色方案和配置物料特性表需要获取表头，该表头恰巧是特性Code+特性Desc，因此需要单独依附方便查询
     * <p>
     * 又同时创建新的特性值变更前的数据并存储到数据库中，只是该数据并不关联VWO的主键，充当第一次变更前的数据对比信息
     *
     * @param record 新的特性值记录
     * @return 添加成功与否标志和消息
     */
    @RequestMapping(value = "/add2", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add2(@RequestBody HzFeatureValue record) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();

        //获取特性值
        String cfgObjectId = record.getFeatureValueCode();
        HzDictionaryLibrary hzDictionaryLibrary = hzDictionaryLibraryService.queryLibraryDTOByCfgObject(cfgObjectId);
        if (hzDictionaryLibrary == null || hzDictionaryLibrary.getPuid() == null) {
            result.put("status", false);
            result.put("msg", "<p style='color:red;'>特性值在配置字典中不存在</p>");
            return result;
        }
        if (!checkString(hzDictionaryLibrary.getFamillyCode()) || !checkString(hzDictionaryLibrary.getFamillyCh())) {
            result.put("status", false);
            result.put("msg", "<p style='color:red;'>配置字典中的特性/特性名称不能为空</p>");
            return result;
        }
        record.setpCfg0FamilyName(hzDictionaryLibrary.getFamillyCode().toUpperCase());
        record.setFeatureValueCode(hzDictionaryLibrary.getEigenValue().toUpperCase());
        //创建人和修改人
        record.setCreator(user.getUsername());
        record.setLastModifier(user.getUsername());
        record.setCfgAbolishDate(DateStringUtils.forever());
        //基本信息
        record.setpCfg0FamilyDesc(hzDictionaryLibrary.getFamillyCh());
        record.setpCfg0Desc(hzDictionaryLibrary.getValueDescCh());
        record.setpH9featureenname(hzDictionaryLibrary.getValueDescEn());

        if (!hzFeatureService.preCheck(record)) {
            result.put("status", false);
            result.put("msg", "<p style='color:red;'>特性值已存在</p>");
            return result;
        }
        /**生成自身的puid*/
        record.setPuid(UUIDHelper.generateUpperUid());
        HzFeature family = new HzFeature();
        //主配置
        family.setMainConfigUid(record.getpCfg0MainItemPuid());
        //特性代码
        family.setFeatureCode(record.getpCfg0FamilyName());
        //特性描述
        family.setFeatureDesc(record.getpCfg0FamilyDesc());

        HzFeature _family = cfg0OptionFamilyService.doGetByCodeAndDescWithMain(family);
        //检查当前项目有没有用到特定的特性
        if (_family == null) {
            family.setId(UUIDHelper.generateUpperUid());
            //没有用到，则进行特性插入
            if (!cfg0OptionFamilyService.doInsert(family)) {
                result.put("status", false);
                result.put("msg", "添加特性" + family.getFeatureCode() + "失败，请联系系统管理员");
                return result;
            }
        } else {
            family = _family;
        }

        //关联到特性的UID
        record.setpCfg0FamilyPuid(family.getId());
        if (!checkString(record.getpCfg0Relevance())) {
            record.setpCfg0Relevance("$ROOT." + record.getpCfg0FamilyName() + " = '" + record.getFeatureValueCode() + "'");
        }
        //将特性值插入到表中
        if (hzFeatureService.doInsertOne(record)) {
            result.put("status", true);
            result.put("msg", "添加特性值" + record.getFeatureValueCode() + "成功");
//            //发送到SAP,走流程
//            if (!SynMaterielService.debug) {
//                synFeatureService.addFeature(Collections.singletonList(record));
//            }
            record = hzFeatureService.doSelectOneByPuid(record.getPuid());
//            if (hzFeatureChangeService.insertByCfgAfter(record) <= 0) {
//                log.error("创建后自动同步变更后记录值失败，请联系管理员");
//            }
//            HzFeatureValue record1 = new HzFeatureValue();
//            record1.setId(record.getId());
//            if (hzFeatureChangeService.insertByCfgBefore(record1) <= 0) {
//                logger.error("创建后自动同步变更前记录值失败，请联系管理员");
//            }


        } else {
            result.put("status", false);
            result.put("msg", "添加特性值" + record.getFeatureValueCode() + "失败，请联系系统管理员");
        }
        return result;
    }

    @RequestMapping("/deleteByPuidFake")
    @ResponseBody
    public JSONObject deleteByPuidFake(@RequestBody List<HzFeatureValue> records){
        JSONObject result = new JSONObject();
        List<HzFeatureValue> toDelete = new ArrayList<>();
        Map<String, HzFeatureValue> mapOfDelete = new HashMap<>();
        if (records == null || records.size() <= 0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列做删除");
            return result;
        }
        for (HzFeatureValue record : records) {
            if (record == null || "".equalsIgnoreCase(record.getPuid()) || null == record.getPuid()) {
                result.put("status", false);
                result.put("msg", "找不到需要删除的数据，请重试或联系系统管理员");
                return result;
            }
            //原始配置先不给删除，只能删除新加的配置项
            else {
                //查看特性是否在全配置中已被引用
                HzFullCfgWithCfg hzFullCfgWithCfg = hzFullCfgWithCfgDao.selectByFeatureId(record.getPuid());
                if(hzFullCfgWithCfg!=null){
                    result.put("status", false);
                    result.put("msg", record.getFeatureValueCode()+"在全配置中BOM一级清单中已被引用，如需删除请在全配置中先解除引用");
                    return result;
                }


                if (hzFeatureService.doSelectOneByPuid(record.getPuid()) != null) {
                    //如果需要删除原数据
                    toDelete.add(record);
                    mapOfDelete.put(record.getpCfg0FamilyName() + "-" + record.getpCfg0FamilyDesc() + "-" + record.getFeatureValueCode() + "-" + record.getpCfg0FamilyDesc(), record);
                } else {
                    result.put("status", false);
                    result.put("msg", "找不到需要删除的数据，请重试或联系系统管理员");
                    return result;
                }
            }
            if (record.getCfgIsInProcess() != null&&record.getCfgIsInProcess() == 1) {
                result.put("status", false);
                result.put("msg", "已在流程中，不允许删除");
                return result;
            }
            record.setCfgStatus(2);
        }

        //判读变更中是否存在已生效数据，如没有则直接删除，有则改成删除状态
        List<HzFeatureChangeBean> hzFeatureChangeBeans = hzFeatureChangeService.doSelectHasEffect(records);
        List<HzFeatureValue> hzCfg0RecordsDelete = new ArrayList<>();
        List<HzFeatureValue> hzCfg0RecordsUpdate = new ArrayList<>();
        if(hzFeatureChangeBeans!=null&&hzFeatureChangeBeans.size()>0){
            for(HzFeatureValue hzFeatureValue : records){
                boolean flag = false;
                for(HzFeatureChangeBean hzFeatureChangeBean : hzFeatureChangeBeans){
                    if(hzFeatureValue.getPuid().equals(hzFeatureChangeBean.getCfgPuid())){
                        flag = true;
                        break;
                    }
                }
                if(flag){
                    hzCfg0RecordsUpdate.add(hzFeatureValue);
                }else {
                    hzCfg0RecordsDelete.add(hzFeatureValue);
                }
            }
            if(hzCfg0RecordsUpdate!=null&&hzCfg0RecordsUpdate.size()>0){
                int updateNum = hzFeatureValueDao.updateStatus(hzCfg0RecordsUpdate);
                if(updateNum<=0){
                    result.put("status", false);
                    result.put("msg", "删除失败");
                    return result;
                }else {
                    result.put("status", true);
                    result.put("msg", "删除成功");
                }
            }
            if(hzCfg0RecordsDelete!=null&&hzCfg0RecordsDelete.size()>0){
                int daleteNum = hzFeatureValueDao.deleteCfgByList(hzCfg0RecordsDelete);
                List<HzFeatureValue> familyNames = new ArrayList<>();
                for(HzFeatureValue hzFeatureValue : hzCfg0RecordsDelete){
                    String familyName = hzFeatureValue.getpCfg0FamilyName();
                    List<HzFeatureValue> hzFeatureValueList = hzFeatureValueDao.selectByFamilyName(hzFeatureValue);
                    if(hzFeatureValueList ==null|| hzFeatureValueList.size()<=0){
                        familyNames.add(hzFeatureValue);
                    }
                }
                if(familyNames!=null&&familyNames.size()>0){

                    Map<String,Object> map = new HashMap<>();
                    map.put("date",familyNames);
                    map.put("mainItemPuid",familyNames.get(0).getpCfg0MainItemPuid());

                    if(hzFeatureDao.deleteByFamilyName(map)<=0){
                        result.put("status", false);
                        result.put("msg", "删除失败");
                        return result;
                    }
                }
                if(daleteNum <=0){
                    result.put("status", false);
                    result.put("msg", "删除失败");
                    return result;
                }else {
                    result.put("status", true);
                    result.put("msg", "删除成功");
                }
            }
        }else {
            int deleteNum = hzFeatureValueDao.deleteCfgByList(records);
            List<HzFeatureValue> familyNames = new ArrayList<>();
            for(HzFeatureValue hzFeatureValue : records){
                String familyName = hzFeatureValue.getpCfg0FamilyName();
                List<HzFeatureValue> hzFeatureValueList = hzFeatureValueDao.selectByFamilyName(hzFeatureValue);
                if(hzFeatureValueList ==null|| hzFeatureValueList.size()<=0){
                    familyNames.add(hzFeatureValue);
                }
            }
            if(familyNames!=null&&familyNames.size()>0){

                Map<String,Object> map = new HashMap<>();
                map.put("date",familyNames);
                map.put("mainItemPuid",familyNames.get(0).getpCfg0MainItemPuid());

                if(hzFeatureDao.deleteByFamilyName(map)<=0){
                    result.put("status", false);
                    result.put("msg", "删除失败");
                    return result;
                }
            }
            if(deleteNum<=0){
                result.put("status", false);
                result.put("msg", "删除失败");
                return result;
            }else {
                result.put("status", true);
                result.put("msg", "删除成功");
            }
        }
        return result;
    }
    /**
     * 通过特性值主键删除特性值，删除特性值的同时，判断特性值父层下是否还有其他特性值，如果该特性是最后一个特性值，则连同特性一起删除
     *
     * @param records 一组前端选择的特性值记录，这些记录存储{@link HzFeatureValue#puid}即可
     * @return 操作成功与否标识和消息
     * @throws Exception
     */
    @RequestMapping(value = "/deleteByPuid", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteByPuid(@RequestBody List<HzFeatureValue> records) throws Exception {
        List<HzFeatureValue> toDelete = new ArrayList<>();
        Map<String, HzFeatureValue> mapOfDelete = new HashMap<>();
        JSONObject result = new JSONObject();
        if (records == null || records.size() <= 0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列做删除");
            return result;
        }
        for (HzFeatureValue record : records) {
            if (record == null || "".equalsIgnoreCase(record.getPuid()) || null == record.getPuid()) {
                result.put("status", false);
                result.put("msg", "找不到需要删除的数据，请重试或联系系统管理员");
                return result;
            }
            //原始配置先不给删除，只能删除新加的配置项
            else {
                /*if (hzFeatureService.doSelectOneAddedCfgByPuid(record.getId()) != null) {
                    toDelete.add(record);
                }
                else*/
                if (hzFeatureService.doSelectOneByPuid(record.getPuid()) != null) {
                    //如果需要删除原数据
                    toDelete.add(record);
                    mapOfDelete.put(record.getpCfg0FamilyName() + "-" + record.getpCfg0FamilyDesc() + "-" + record.getFeatureValueCode() + "-" + record.getpCfg0FamilyDesc(), record);
//                    result.put("status", false);
//                    result.put("msg", "目前不允许删除原数据，请重试或联系系统管理员");
//                    return result;
                } else {
                    result.put("status", false);
                    result.put("msg", "找不到需要删除的数据，请重试或联系系统管理员");
//                    result.put("msg", "目前不允许删除原数据，请重试或联系系统管理员");
                    return result;
                }
            }

            if (record.getCfgIsInProcess() == 1) {
                result.put("status", false);
                result.put("msg", "已在VWO流程中，不允许删除");
                return result;
            }
        }
        List<HzFeatureValue> _toDelete = new ArrayList<>();

        /**同步删除已发送到ERP的特性值和相关性值（标记为状态3：不可用）*/
        if (Author.SYN_DELETE) {
            JSONObject resultFromSap = synFeatureService.deleteFeature(toDelete);

            JSONObject resultFromSapOfRelevance;
            //整理数据
            List<HzRelevanceResponseDTO> myBeans = new ArrayList<>();
//            synRelevanceService.sortData(records, myBeans);

            for (HzRelevanceResponseDTO myBean : myBeans) {
                log.warn("---------------同步在SAP中标记像关系状态为3:" + (myBean.getRelevanceCode()));
            }
//            resultFromSapOfRelevance = synRelevanceService.deleteRelevance(myBeans);

            Object obj = resultFromSap.get("_forDelete");


            if (obj != null && obj instanceof List) {
                if (((List) obj).size() > 0) {
                    for (int i = 0; i < ((List<String>) obj).size(); i++) {
                        if (mapOfDelete.containsKey(((List<String>) obj).get(i))) {
                            _toDelete.add(mapOfDelete.get(((List) obj).get(i)));
                            log.warn("---------------同步在SAP中删除特性:" + (mapOfDelete.get(((List) obj).get(i)).getFeatureValueCode()));
                        }
                    }
                }
            } else {
                _toDelete.addAll(records);
            }
        }
//        没有进行同步删除时执行该段代码
        if (_toDelete.size() == 0) {
            _toDelete.addAll(records);
        }

        HzMainConfig main = null;
        Map<String, HzMainConfig> mapOfMain = new HashMap<>();
//        HzFeature family = new HzFeature();

        for (int i = 0; i < _toDelete.size(); i++) {
            if (mapOfMain.containsKey(_toDelete.get(i).getpCfg0MainItemPuid())) {
                main = mapOfMain.get(_toDelete.get(i).getpCfg0MainItemPuid());
            } else {
                main = hzMainConfigDao.selectByPrimaryKey(_toDelete.get(i).getpCfg0MainItemPuid());
                mapOfMain.put(main.getId(), main);
            }
            List<HzFeatureValue> children = hzFeatureService.doSelectByFamilyUidWithProject(_toDelete.get(i).getpCfg0FamilyPuid(), main.getProjectId());
            if (children.size() <= 1 && children.get(0).getPuid().equals(_toDelete.get(i).getPuid())) {
//                family.setId(_toDelete.get(i).getpCfg0FamilyPuid());
                log.warn("正在自动删除特性");
                if (cfg0OptionFamilyService.doDeleteByPrimaryKey(_toDelete.get(i).getpCfg0FamilyPuid())) {
                    log.warn("自动删除特性失败，请手动删除");
                }
            }
        }

        if (_toDelete.size() > 0) {
            log.warn(hzFeatureService.doDeleteCfgByList(_toDelete) ? "删除特性多个" : "删除失败或已经删除族");
        }
        result.put("status", true);
        result.put("msg", "删除成功");

        return result;
    }

    /**
     * 根据特性值(code)查询配置字典中的特性，特性从配置字典中继承且无法修改，也达到了验证特性值的作用
     *
     * @param cfgVal 特性值
     * @return 验证成功则返回true和字典库中的数据，否则只返回false
     */
    @RequestMapping("/returnCfgMsg")
    @ResponseBody
    public JSONObject returnCfgMsg(String cfgVal) {
        JSONObject result = new JSONObject();
        HzDictionaryLibrary hzDictionaryLibrary = hzDictionaryLibraryService.queryLibraryDTOByCfgObject(cfgVal);
        if (hzDictionaryLibrary != null && hzDictionaryLibrary.getPuid() != null) {
            hzDictionaryLibrary.setFailureTime(new Date());
            hzDictionaryLibrary.setEffectTime(new Date());
            JSONObject libraryJson = JSONObject.fromObject(hzDictionaryLibrary);
            result.put("stage", true);
            result.put("data", libraryJson);
        } else {
            result.put("stage", false);
        }
        return result;
    }

    /**********************************************废除方法****************************************/

    /**
     * 获取添加特性页面，已废除，采用{@link HzCfg0Controller#addPage2(String, Model)}代替
     *
     * @param projectPuid 项目UID
     * @return 添加特性页面
     */
    @RequestMapping("/addPage")
    @Deprecated
    public String addPage(@RequestParam("projectPuid") String projectPuid, Model model) {
        HzMainConfig mainRecord = hzMainConfigDao.selectByProjectId(projectPuid);
        if (mainRecord == null) {
            model.addAttribute("msg", "请选择一个项目进行操作");
            return "errorWithEntity";
        }
        model.addAttribute("entity", mainRecord);
        return "cfg/feature/addFeature";
    }

    /**
     * 已不再使用，跳转到修改页面，如果值不能再修改，则特性没有必要走变更的表单，走审核即可，实际上却需要走表单，什么脑回路
     * <p>
     * 新的特性不再允许修改特性数据，因为特性来源于配置字典，即便是修改了配置字典也不允许修改特性数据
     *
     * @param puid 特性值UID
     * @return 特性修改页面
     */
    @Deprecated
    @RequestMapping("/modifyPage")
    public String modifyPage(@RequestParam("projectPuid") String puid, Model model) {
        HzFeatureValue record = hzFeatureService.doSelectOneByPuid(puid);
        if (record == null) {
//            record = hzFeatureService.doSelectOneAddedCfgByPuid(puid);
//            if (record == null) {
            model.addAttribute("msg", "没有找到对应的特性数据，请重试或联系系统管理员");
            return "errorWithEntity";
//            }
        }
        model.addAttribute("entity", record);
        model.addAttribute("action", "./feature/modify");
        return "cfg/feature/modFeature";
    }

    /**
     * 已废除
     * 该方法可以直接添加特性到数据库中，缺少配置字典的约束，请采用{@link HzCfg0Controller#add2(HzFeatureValue)}代替
     *
     * @param record
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public JSONObject add(@RequestBody HzFeatureValue record) throws Exception {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();

        record.setpCfg0FamilyName(record.getpCfg0FamilyName().toUpperCase());
        record.setFeatureValueCode(record.getFeatureValueCode().toUpperCase());
        //创建人和修改人
        record.setCreator(user.getUsername());
        record.setLastModifier(user.getUsername());
        record.setCfgAbolishDate(DateStringUtils.forever());
        if (!hzFeatureService.preCheck(record)) {
            result.put("status", false);
            result.put("msg", "<p style='color:red;'>特性值已存在</p>");
            return result;
        }
        /**生成自身的puid*/
        record.setPuid(UUIDHelper.generateUpperUid());

        HzFeature family = new HzFeature();
        //主配置
        family.setMainConfigUid(record.getpCfg0MainItemPuid());
        //特性代码
        family.setFeatureCode(record.getpCfg0FamilyName());
        //特性描述
        family.setFeatureDesc(record.getpCfg0FamilyDesc());

        HzFeature _family = cfg0OptionFamilyService.doGetByCodeAndDescWithMain(family);
        //检查当前项目有没有用到特定的特性
        if (_family == null) {
            family.setId(UUIDHelper.generateUpperUid());
            //没有用到，则进行特性插入
            if (!cfg0OptionFamilyService.doInsert(family)) {
                result.put("status", false);
                result.put("msg", "添加特性" + family.getFeatureCode() + "失败，请联系系统管理员");
                return result;
            }
        } else {
            family = _family;
        }

        //关联到特性的UID
        record.setpCfg0FamilyPuid(family.getId());
        if (!checkString(record.getpCfg0Relevance())) {
            record.setpCfg0Relevance("$ROOT." + record.getpCfg0FamilyName() + " = '" + record.getFeatureValueCode() + "'");
        }
        //将特性值插入到表中
        if (hzFeatureService.doInsertOne(record)) {
            result.put("status", true);
            result.put("msg", "添加特性值" + record.getFeatureValueCode() + "成功");
//            //发送到SAP,走流程
//            if (!SynMaterielService.debug) {
//                synFeatureService.addFeature(Collections.singletonList(record));
//            }
            record = hzFeatureService.doSelectOneByPuid(record.getPuid());
            if (hzFeatureChangeService.insertByCfgAfter(record) <= 0) {
                log.error("创建后自动同步变更后记录值失败，请联系管理员");
            }
            HzFeatureValue record1 = new HzFeatureValue();
            record1.setPuid(record.getPuid());
            if (hzFeatureChangeService.insertByCfgBefore(record1) <= 0) {
                log.error("创建后自动同步变更前记录值失败，请联系管理员");
            }


        } else {
            result.put("status", false);
            result.put("msg", "添加特性值" + record.getFeatureValueCode() + "失败，请联系系统管理员");
        }
        return result;
    }

    /**
     * 已不再使用，修改特性，保存修改后特特性值数据
     *
     * @param record 特性对象
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public JSONObject modify(@RequestBody HzFeatureValue record) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        result.put("status", true);
        if (record == null || "".equalsIgnoreCase(record.getPuid()) || null == record.getPuid()) {
            result.put("status", false);
            result.put("msg", "没有选择任何1条数据，请选择1条数据");
            return result;
        }
        record.setpCfg0FamilyName(record.getpCfg0FamilyName().toUpperCase());
        record.setFeatureValueCode(record.getFeatureValueCode().toUpperCase());
        record.setLastModifier(user.getUsername());

        if (!hzFeatureService.preCheck(record)) {
            result.put("status", false);
            result.put("msg", "已存在的特性值");
            return result;
        }

        if (!checkString(record.getpCfg0Relevance())) {
            record.setpCfg0Relevance("$ROOT." + record.getpCfg0FamilyName() + " = '" + record.getFeatureValueCode() + "'");
        }
        if (hzFeatureService.doSelectOneByPuid(record.getPuid()) != null) {
            if (hzFeatureService.doUpdate(record)) {
                //重新取回一遍数据
                record = hzFeatureService.doSelectOneByPuid(record.getPuid());
                result.put("msg", "更新特性值" + record.getFeatureValueCode() + "成功");
                HzFeatureChangeBean after = new HzFeatureChangeBean();
                after.setCfgPuid(record.getPuid());
                after = hzFeatureChangeService.doSelectAfterByPk(after);
                if (after == null) {
                    if (hzFeatureChangeService.insertByCfgAfter(record) <= 0) {
                        log.error("更新" + record.getFeatureValueCode() + "变更后记录数据失败，请联系系统管理员");
                    }
                    HzFeatureChangeBean before = new HzFeatureChangeBean();
                    before.setCfgPuid(record.getPuid());
                    before = hzFeatureChangeService.doSelectBeforeByPk(before);
                    if (before == null) {
                        HzFeatureValue localRecord = new HzFeatureValue();
                        localRecord.setPuid(record.getPuid());
                        if (hzFeatureChangeService.insertByCfgBefore(localRecord) <= 0) {
                            log.error("更新" + record.getFeatureValueCode() + "变更前记录数据失败，请联系系统管理员");
                        }
                    }
                } else {
                    //先取回所有需要存储的数据
                    hzFeatureChangeService.reflect(record, after);
                    //再进行更新
                    if (!hzFeatureChangeService.doUpdateAfterByPk(after)) {
                        log.error("更新" + record.getFeatureValueCode() + "变更后记录数据失败，请联系系统管理员");
                    }
                }
            } else {
                result.put("msg", "更新特性值" + record.getFeatureValueCode() + "失败");
                log.error("更新特性值" + record.getFeatureValueCode() + "失败");

            }
        }
//        else if (hzFeatureService.doSelectOneAddedCfgByPuid(record.getId()) != null) {
//            if (hzFeatureService.doUpdateAddedCfg(record)) {
//                result.put("msg", "更新特性值" + record.getFeatureValueCode() + "成功");
//            } else {
//                logger.error("更新特性值" + record.getFeatureValueCode() + "失败");
//                result.put("msg", "更新特性值" + record.getFeatureValueCode() + "失败");
//            }
//        }
        else {
            result.put("msg", "更新特性值" + record.getFeatureValueCode() + "时发生错误，请联系系统管理员");
        }
        return result;
    }

    /**
     * 已废除
     *
     * @param records
     * @param model
     * @return
     * @throws Exception
     */
    @Deprecated
    @RequestMapping(value = "/sendToERP", method = RequestMethod.POST)
    public String sendToERP(@RequestBody List<HzFeatureValue> records, Model model) throws Exception {
        JSONObject result = synFeatureService.addFeature(records);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }
    /**
     * 加载相关性，一阶段相关性没有颜色，所以相关性都直接由特性按照规则拼接而成
     *
     * @param projectPuid 项目UID
     * @return
     */
    @RequestMapping("/loadRelevance")
    @ResponseBody
    @Deprecated
    public Map<String, Object> loadRelevance(@RequestParam("projectPuid") String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        List<HzRelevanceResponseDTO> _list = new ArrayList<>();
        int _index = 0;
        _index = hzFeatureService.doLoadRelevance(projectPuid, _list, _index, "HZ_CFG0_RECORD");
//        hzFeatureService.doLoadRelevance(projectPuid, _list, _index, "HZ_CFG0_ADD_CFG_RECORD");
        result.put("totalCount", _list.size());
        result.put("result", _list);
        return result;
    }

    /**
     * 加载相关性，一阶段相关性没有颜色，所以相关性都直接由特性按照规则拼接而成
     *
     * @param projectPuid 项目UID
     * @return
     */
    @RequestMapping("/loadRelevance2")
    @ResponseBody
    @Deprecated
    public Map<String, Object> loadRelevance2(@RequestParam("projectPuid") String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        List<HzRelevanceResponseDTO> _list = new ArrayList<>();
        int _index = 0;
        _index = hzFeatureService.doLoadRelevance(projectPuid, _list, _index, "HZ_CFG0_RECORD");
//        hzFeatureService.doLoadRelevance(projectPuid, _list, _index, "HZ_CFG0_ADD_CFG_RECORD");
        result.put("totalCount", _list.size());
        result.put("result", _list);
        return result;
    }

    /**
     * 已废除
     *
     * @param bean
     * @return
     */
    @RequestMapping(value = "/relModify", method = RequestMethod.POST)
    @ResponseBody
    @Deprecated
    public boolean relOption(@RequestBody HzRelevanceResponseDTO bean) {
        if (bean == null || "".equalsIgnoreCase(bean.getPuid()) || null == bean.getPuid()) {
            return false;
        }
        HzFeatureValue record;

        record = hzFeatureService.doSelectOneByPuid(bean.getPuid());
        if (record != null) {
            record = hzFeatureService.doSelectOneByPuid(bean.getPuid());
            record.setpCfg0Relevance(bean.getRelevanceCode());
            return hzFeatureService.doUpdate(record);
        }
//        else if ((record = hzFeatureService.doSelectOneAddedCfgByPuid(bean.getId())) != null) {
//            record.setpCfg0Relevance(bean.getRelevanceCode());
//            return hzFeatureService.doUpdateAddedCfg(record);
//        }
        else {
            return false;
        }
    }

    /**
     * 已废除
     * 相关性获取页面。根据<strong>page</strong>的值来进行判断，以此进行相关页面的返回
     *
     * @param uid   可以是项目的puid，或者配置值的puid
     * @param page  相关页面，addPage或者modifyPage
     * @param model
     * @return 返回对应的前端页面
     */
    @Deprecated
    @RequestMapping("/relModifyPage")
    public String relPage(@RequestParam("uid") String uid, @RequestParam("page") String page, Model model) {
        HzRelevanceResponseDTO bean = new HzRelevanceResponseDTO();
        //其实没有添加的页面
        if ("addPage".equals(page)) {
            HzMainConfig mainRecord = hzMainConfigDao.selectByProjectId(uid);
            if (mainRecord == null) {
                return "error";
            }
            model.addAttribute("action", "./feature/relModify");
        } else if ("modifyPage".equals(page)) {
            HzFeatureValue record = hzFeatureService.doSelectOneByPuid(uid);
            if (record == null) {
//                record = hzFeatureService.doSelectOneAddedCfgByPuid(uid);
//                if (record == null) {
                model.addAttribute("msg", "没有找到对应的特性数据，请重试或联系系统管理员!");
                return "errorWithEntity";
//                }
//                record.setWhichTable("HZ_CFG0_ADD_CFG_RECORD");
            } else {
                record.setWhichTable("HZ_CFG0_RECORD");
            }

            bean.setPuid(record.getPuid());
            bean.setRelevance(record.getpCfg0FamilyName() + "-" + record.getFeatureValueCode());
            bean.setRelevanceDesc((record.getpCfg0FamilyDesc() == null ? "" : record.getpCfg0FamilyDesc()) + "-" + (record.getpCfg0Desc() == null ? "" : record.getpCfg0Desc()));
            bean.setRelevanceCode(record.getpCfg0Relevance());
            bean.set_table(record.getWhichTable());

            model.addAttribute("action", "./feature/relModify");
        }
        model.addAttribute("entity", bean);
        return "cfg/relevance/mergeRelevance";
    }

//    /**
//     * 已废除
//     *
//     * @throws Exception
//     */
//    @Deprecated
//    @RequestMapping(value = "/sendRelToERP", method = RequestMethod.POST)
//    public String sendRelToERP(@RequestBody List<HzRelevanceResponseDTO> beans, Model model) throws Exception {
//        //清空上次传输的内容
//        JSONObject result;
//        List<String> puids = new ArrayList<>();
//        List<HzFeatureValue> records;
//        //只要求获取puid
//        beans.forEach(bean -> puids.add(bean.getId()));
//        //从根本根本上查找数据
//        records = hzFeatureService.doLoadListByPuids(puids);
//        //整理数据
//        List<HzRelevanceResponseDTO> myBeans = new ArrayList<>();
//        synRelevanceService.sortData(records, myBeans);
//        result = synRelevanceService.addRelevance(myBeans);
//        addToModel(result, model);
//        return "stage/templateOfIntegrate";
//    }

//    /**
//     * 已废除
//     *
//     * @throws Exception
//     */
//    @Deprecated
//    @RequestMapping(value = "/sendRelToERPDelete", method = RequestMethod.POST)
//    public String sendRelToERPDelete(@RequestBody List<HzRelevanceResponseDTO> beans, Model model) throws Exception {
//        //清空上次传输的内容
//        JSONObject result;
//        List<String> puids = new ArrayList<>();
//        List<HzFeatureValue> records;
//        //只要求获取puid
//        beans.forEach(bean -> puids.add(bean.getId()));
//        //从根本根本上查找数据
//        records = hzFeatureService.doLoadListByPuids(puids);
//        //整理数据
//        List<HzRelevanceResponseDTO> myBeans = new ArrayList<>();
//        synRelevanceService.sortData(records, myBeans);
//        result = synRelevanceService.deleteRelevance(myBeans);
//        addToModel(result, model);
//        return "stage/templateOfIntegrate";
//    }
    /**********************************************废除方法****************************************/

}
