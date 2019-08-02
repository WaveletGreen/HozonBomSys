/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.resources.controller.change.vwo;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoFormListQueryBase;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoOptionUserDto;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoProcessDto;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVWOManagerService;
import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import cn.net.connor.hozon.common.entity.QueryBase;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cn.net.connor.hozon.dao.pojo.change.vwo.*;
import sql.pojo.change.HzChangeOrderRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/vwo")
public class HzVwoController {
    /***VWO表单服务层*/
    @Autowired
    IHzVWOManagerService iHzVWOManagerService;

    @Autowired
    HzChangeOrderDAO hzChangeOrderDAO;
    /***日志*/
    private static Logger logger = LoggerFactory.getLogger(HzVwoController.class);

    /**
     * 详情页面
     *
     * @param id      vwoid
     * @param vwoType 发起vwo的类型，1为特性，2为配色方案
     * @param model
     * @return
     */
    @RequestMapping(value = "/vwoFormList", method = RequestMethod.GET)
    public String getVwoFromList(@RequestParam Long id, @RequestParam Integer vwoType, Model model) {

        String error = "errorWithEntity";
        HzVwoInfo vwoInfo;
        HzVwoInfluenceDept influenceDept;
        HzVwoInfluenceUser influenceUser;
        HzVwoOpiBom hzVwoOpiBom;
        HzVwoOpiPmt hzVwoOpiPmt;
        HzVwoOpiProj hzVwoOpiProj;
        switch (vwoType) {
            case 1:
                List<HzFeatureChangeBean> after = iHzVWOManagerService.getFeatureChangeAfter(id);
                List<HzFeatureChangeBean> before = iHzVWOManagerService.getFeatureChangeBefore(id);
                model.addAttribute("after", after);
                model.addAttribute("before", before);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                model.addAttribute("msg", "没有对应的VWO变更");
                return error;
        }
        //表单数据
        vwoInfo = iHzVWOManagerService.getVwoInfo(id);
        influenceDept = iHzVWOManagerService.getInfluenceDept(id);
        influenceUser = iHzVWOManagerService.getInfluenceUser(id);
        hzVwoOpiBom = iHzVWOManagerService.getOpiOfBomMng(id);
        hzVwoOpiPmt = iHzVWOManagerService.getOpiOfPmtMng(id);
        hzVwoOpiProj = iHzVWOManagerService.getOpiOfProjMng(id);
        if (null == influenceDept) {
            model.addAttribute("msg", "初始化VWO:" + id + "的影响部门数据失败");
            return error;
        }
        if (null == influenceUser) {
            model.addAttribute("msg", "初始化VWO:" + id + "的影响人员数据失败");
            return error;
        }
        if (null == hzVwoOpiBom) {
            model.addAttribute("msg", "初始化VWO:" + id + "的BOM经理意见数据失败");
            return error;
        }
        if (null == hzVwoOpiPmt) {
            model.addAttribute("msg", "初始化VWO:" + id + "的专业PMT经理意见数据失败");
            model.addAttribute("msg", "");
            return error;
        }
        if (null == hzVwoOpiProj) {
            model.addAttribute("msg", "初始化VWO:" + id + "的项目经理意见数据失败");
            return error;
        }
        //数据回传
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

        model.addAttribute("user",UserInfo.getUser().getId());
        return "changeManage/vwo/vwoBasicInformation";
    }

    /**
     * 返回到vwo变更主页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/returnToVwoFromList", method = RequestMethod.GET)
    public String returnToVwoFromList(Model model) {
        model.addAttribute("href", "vwoFromList");
        return "changeManage/vwo/vwoFormList";
    }

    @RequestMapping(value = "/getVwoExecuteDialog", method = RequestMethod.GET)
    public String getVwoExecuteDialog(@RequestParam String vwoId, Model model) {
        model.addAttribute("vwoId", vwoId);
        return "changeManage/vwo/vwoExecuteDialog";
    }

    /**
     * 分页查询
     *
     * @param projectUid 项目id
     * @param queryBase  查询基本对象
     * @return
     */
    @RequestMapping(value = "/queryByBase", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryByBase(@RequestParam String projectUid, HzVwoFormListQueryBase queryBase) {
        return iHzVWOManagerService.queryByBase(null, queryBase);
    }

    /**
     * 所有关联人
     *
     * @param vwo vwo号
     * @return
     */
    @RequestMapping(value = "/getInformChangers", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getInformChangers(@RequestParam Long vwo, QueryBase queryBase) {
        return iHzVWOManagerService.getInformChangers(vwo, queryBase);
    }

    @RequestMapping(value = "/getUserAndGroupPage", method = RequestMethod.GET)
    public String getUserAndGroupPage(@RequestParam Long vwoId, Model model) {
        model.addAttribute("vwoId", vwoId);
        return "changeManage/vwo/connectedDialog";
    }

    @RequestMapping(value = "/getUserAndGroup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getUserAndGroup() {
        Map<String, Object> result = new HashMap<>();
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG, "加载菜单数据成功！");
        result.put("data", getUserAndGroupOrg());
        return result;
    }

    @RequestMapping(value = "/getUserDetail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getUserDetail(VWOUserGroupDTO dto) {
        return iHzVWOManagerService.getUserDetail(dto);
    }

    @RequestMapping(value = "/saveInformChanger", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveInformChanger(@RequestBody HzVwoInformChanges change) {
        return iHzVWOManagerService.saveInformChanger(change);
    }

    @RequestMapping(value = "/deleteVwoInfoChange", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteVwoInfoChange(@RequestBody List<HzVwoInformChanges> changes) {
        return iHzVWOManagerService.deleteVwoInfoChange(changes);
    }

    @RequestMapping(value = "/saveBasic", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveBasic(@RequestBody HzVwoInfo info) {
        return iHzVWOManagerService.saveBasic(info);
    }

    @RequestMapping(value = "/saveInfluenceDept", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveInfluenceDept(@RequestBody HzVwoInfluenceDept dept) {
        return iHzVWOManagerService.saveInfluenceDept(dept);
    }

    @RequestMapping(value = "doSelectPersonPage", method = RequestMethod.GET)
    public String doSelectPersonPage(@RequestParam Long vwo, @RequestParam Integer selectType, Model model) {
        if (vwo == null && selectType == null) {
            model.addAttribute("发生错误，请选择正确的VWO表单");
            return "errorWithEntity";
        }
        String type = HzVwoInfluenceUser.getNameFromType(selectType);
        model.addAttribute("vwoId", vwo);
        model.addAttribute("selectId", selectType);
        model.addAttribute("typeId", type + "Id");
        model.addAttribute("typeName", type + "Name");
        return "changeManage/vwo/vwoSelectPerson";
    }

    @RequestMapping(value = "saveInfluenceUser", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveInfluenceUser(@RequestBody HzVwoInfluenceUser influenceUser) {
        return iHzVWOManagerService.saveInfluenceUser(influenceUser);
    }

    /**
     * 为评估意见选择评估人员
     *
     * @param vwo
     * @param selectType
     * @param model
     * @return
     */
    @RequestMapping(value = "doSelectBomMag", method = RequestMethod.GET)
    public String doSelectBomMag(@RequestParam Long vwo, @RequestParam Integer selectType, Model model) {
        if (vwo == null && selectType == null) {
            model.addAttribute("发生错误，请选择正确的VWO表单");
            return "errorWithEntity";
        }
        String type = "";
        if (selectType == 1) {
            type = "opiBom";
        } else if (selectType == 2) {
            type = "opiPmt";
        } else if (selectType == 3) {
            type = "opiProj";
        } else {
            model.addAttribute("选择类型错误");
            return "errorWithEntity";
        }

        model.addAttribute("vwoId", vwo);
        model.addAttribute("selectId", selectType);
        model.addAttribute("typeId", type + "Id");
        model.addAttribute("typeName", type + "Name");
        return "changeManage/vwo/vwoSelectOptionPerson";
    }

    @RequestMapping(value = "saveOptionUser", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveOptionUser(@RequestBody HzVwoOptionUserDto hzVwoOptionUserDto) {
        return iHzVWOManagerService.saveOptionUser(hzVwoOptionUserDto);
    }

    /**
     * 获取发布与实施数据
     *
     * @param vwo
     * @return
     */
    @RequestMapping(value = "getExecuteInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getExecuteInfo(@RequestParam Long vwo) {
        return iHzVWOManagerService.getExecuteInfo(vwo);
    }

    /**
     * 获取发布与实施数据
     *
     * @param execute
     * @return
     */
    @RequestMapping(value = "saveExecuteInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveExecuteInfo(@RequestBody HzVwoExecute execute) {
        return iHzVWOManagerService.saveExecuteInfo(execute);
    }

    /**
     * 获取发布与实施数据
     *
     * @param executes
     * @return
     */
    @RequestMapping(value = "deleteExecuteInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteExecuteInfo(@RequestBody List<HzVwoExecute> executes) {
        return iHzVWOManagerService.deleteExecuteInfo(executes);
    }


    /**
     * 获取用户和部门等组织架构信息
     *
     * @return
     */
    public List<VWOUserGroupDTO> getUserAndGroupOrg() {
        return iHzVWOManagerService.getUserAndGroupOrg();
    }


    /**
     * 流程发布完成，将对象的状态设置为发布状态
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/release", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject release(@RequestBody HzVwoProcessDto dto) {
        return iHzVWOManagerService.release(dto.getVwoType(), dto.getProjectUid(), dto.getVwoId());
    }

    /**
     * 流程中断，将对象的状态设置为“草稿状态”
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/interrupt", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject interrupt(@RequestBody HzVwoProcessDto dto) {
        return iHzVWOManagerService.interrupt(dto.getVwoType(), dto.getProjectUid(), dto.getVwoId());
    }

    /**
     * vwo发起
     *
     * @param dto vwo数据中间对象
     * @return
     */
    @RequestMapping(value = "/launch", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject launch(@RequestBody HzVwoProcessDto dto) {
        return iHzVWOManagerService.launch(dto.getVwoType(), dto.getProjectUid(), dto.getVwoId(), dto.getFormId());
    }

    @RequestMapping(value = "/saveLeaderOpinion", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveLeaderOpinion(@RequestParam HzVwoInfo info,
                                        @RequestParam HzVwoInfluenceDept dept
    ) {
        System.out.println();
        return null;
    }

    /**
     * BOM经理评估意见
     *
     * @param hzVwoOpiBom bom经理评估意见对象
     * @return
     */
    @RequestMapping(value = "/saveBomLeaderOpinion", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveBomLeaderOpinion(HzVwoOpiBom hzVwoOpiBom, Integer vwoType, String projectUid) {
        return iHzVWOManagerService.saveBomLeaderOpinion(hzVwoOpiBom, vwoType, projectUid);
    }

    /**
     * 专业PMT经理评估意见
     *
     * @param hzVwoOpiPmt 专业PMT经理评估意见对象
     * @return
     */
    @RequestMapping(value = "/savePmtLeaderOpinion", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject savePmtLeaderOpinion(HzVwoOpiPmt hzVwoOpiPmt, Integer vwoType, String projectUid) {
        return iHzVWOManagerService.savePmtLeaderOpinion(hzVwoOpiPmt, vwoType, projectUid);
    }

    /**
     * 项目经理评估意见         项目经理评估意见对象
     *
     * @param hzVwoOpiProj
     * @return
     */
    @RequestMapping(value = "/saveProjLeaderOpinion", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveProjLeaderOpinion(HzVwoOpiProj hzVwoOpiProj, Integer vwoType, String projectUid) {
        return iHzVWOManagerService.saveProjLeaderOpinion(hzVwoOpiProj, vwoType, projectUid);
    }


    /**************************特性变更表单****************************/
    @RequestMapping(value = "getFeatureTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getFeatureTable(@RequestParam Long vwoId) {
        return iHzVWOManagerService.getFeatureTable(vwoId);
    }


    /**************************配色方案变更描述表单***************************/
    @RequestMapping(value = "getModelColorTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getModelColorTable(@RequestParam Long vwoId) {
        Map<String, Object> map = new HashMap<String, Object>();
//        iHzVWOManagerService.doQueryCmcrDetailChangBefor(map, vwoId);
        iHzVWOManagerService.doQueryCmcrDetailChangBefor2(map, vwoId);
        return map;
    }

    /**************************衍生物料变更描述表单***************************/
    @RequestMapping(value = "getMaterielFeatureTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMaterielFeatureTable(@RequestParam Long formId){
        HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordById(formId);
        String projectUid = record.getProjectId();
        return iHzVWOManagerService.getMaterielFeatureTable(formId,projectUid);
    }

    /**************************全配置BOM变更描述表单*************************************/
    @RequestMapping(value = "getFullCfgTable", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getFullCfgTable(@RequestParam Integer orderChangeId){
        HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordById(Long.valueOf(orderChangeId));
        String projectUid = record.getProjectId();
        return  iHzVWOManagerService.getFullCfgTable(orderChangeId,projectUid);
    }

    /****************************相关性变更描述变更表单********************************/
    @RequestMapping(value = "getRelevance")
    @ResponseBody
    public Map<String,Object> getRelevance(@RequestParam Long orderChangeId){
        HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordById(orderChangeId);
        String projectUid = record.getProjectId();
        return  iHzVWOManagerService.getRelevance(orderChangeId,projectUid);
    }




    /***************删除特性变更数据*************************************/
    @RequestMapping("/deleteChangeFeature")
    @ResponseBody
    public JSONObject deleteChangeFeature(@RequestBody List<Long> ids,Long orderId){
        return iHzVWOManagerService.deleteChangeFeature(ids,orderId);
    }


    /***************删除配色方案变更数据*************************************/
    @RequestMapping("/deleteChangeColorModel")
    @ResponseBody
    public JSONObject deleteChangeColorModel(@RequestBody List<Long> ids,Long orderId){
        return iHzVWOManagerService.deleteChangeColorModel(ids,orderId);
    }


    /***************删除衍生物料变更数据*************************************/
    @RequestMapping("/deleteChangeMaterielFeature")
    @ResponseBody
    public JSONObject deleteChangeMaterielFeature(@RequestBody List<Long> ids,Long orderId){
        return iHzVWOManagerService.deleteChangeMaterielFeature(ids,orderId);
    }


    /***************删除全配置BOM变更数据*************************************/
    @RequestMapping("/deleteChangeBomAll")
    @ResponseBody
    public JSONObject deleteChangeBomAll(@RequestParam Long mainId,Long orderId){
        return iHzVWOManagerService.deleteChangeBomAll(mainId,orderId);
    }


    /***************删除相关性变更数据*************************************/
    @RequestMapping("/deleteChangeRelevance")
    @ResponseBody
    public JSONObject deleteChangeRelevance(Long orderChangeId){
        return iHzVWOManagerService.deleteChangeRelevance(orderChangeId);
    }













    @RequestMapping(value = "featurePage")
    public String featurePage (Long vwoId, Model model){
        model.addAttribute("orderChangeId",vwoId);
        return "change/ChangeOrder/changeFeatureTable";
    }

    @RequestMapping(value = "fullCfgPage")
    public String fullCfgPage(Long orderChangeId, Model model){
        model.addAttribute("orderChangeId", orderChangeId);
        return "change/ChangeOrder/changeBomCfgTable";
    }
}