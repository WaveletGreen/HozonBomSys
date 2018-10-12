package com.connor.hozon.bom.resources.controller.change.vwo;

import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.*;
import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.sys.entity.OrgGroup;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.service.OrgGroupService;
import com.connor.hozon.bom.sys.service.UserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;
import sql.pojo.cfg.vwo.*;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;


/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/8/14
 * Time: 10:52
 */
@Controller
@RequestMapping(value = "/vwo")
public class HzVwoController {
    @Autowired
    IHzVwoInfoService iHzVwoInfoService;

    @Autowired
    IHzFeatureChangeService iHzFeatureChangeService;

    @Autowired
    IHzVwoInformChangesService iHzVwoInformChangesService;

    @Autowired
    OrgGroupService orgGroupService;
    @Autowired
    UserService userService;

    @Autowired
    HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    IHzVwoInfluenceDeptService iHzVwoInfluenceDeptService;

    @Autowired
    IHzVwoInfluenceUserService iHzVwoInfluenceUserService;
    private static Logger logger = LoggerFactory.getLogger(HzVwoController.class);

    /**
     * 详情页面
     *
     * @param id
     * @param vwoType
     * @param model
     * @return
     */
    @RequestMapping(value = "/vwoFormList", method = RequestMethod.GET)
    public String getVwoFromList(@RequestParam Long id, @RequestParam Integer vwoType, Model model) {
        HzVwoInfo vwoInfo = iHzVwoInfoService.doSelectByPrimaryKey(id);
        HzVwoInfluenceDept influenceDept = iHzVwoInfluenceDeptService.doSelectByVwoId(id);
        HzVwoInfluenceUser influenceUser = iHzVwoInfluenceUserService.doSelectByVwoId(id);
        if (influenceDept == null) {
            influenceDept = new HzVwoInfluenceDept();
            influenceDept.setVwoId(id);
            if (iHzVwoInfluenceDeptService.doInsert(influenceDept) <= 0) {
                logger.error("初始化VWO:" + id + "的影响部门数据失败");
                model.addAttribute("msg", "初始化VWO:" + id + "的影响部门数据失败");
                return "errorWithEntity";
            }
        }
        if (influenceUser == null) {
            influenceUser = new HzVwoInfluenceUser();
            influenceUser.setVwoId(id);
            if (iHzVwoInfluenceUserService.doInsert(influenceUser) <= 0) {
                logger.error("初始化VWO:" + id + "的影响人员数据失败");
                model.addAttribute("msg", "初始化VWO:" + id + "的影响人员数据失败");
                return "errorWithEntity";
            }
        }
        if (1 == vwoType) {
            List<HzFeatureChangeBean> after = iHzFeatureChangeService.doSelectAfterByVwoId(id);
            List<HzFeatureChangeBean> before = iHzFeatureChangeService.doSelectBeforeByVwoId(id);
            model.addAttribute("after", after);
            model.addAttribute("before", before);

        } else if (2 == vwoType) {

        } else if (3 == vwoType) {

        } else {
            model.addAttribute("msg", "没有对应的VWO变更");
            return "errorWithEntity";
        }
        model.addAttribute("vwoInfo", vwoInfo);
        model.addAttribute("href", "returnToVwoFromList");
        model.addAttribute("url", "getInformChangers");
        model.addAttribute("vwo", id);
        model.addAttribute("influenceDept", influenceDept);
        model.addAttribute("influenceUser", influenceUser);
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

    /**
     * 分页查询
     *
     * @param projectUid
     * @param queryBase
     * @return
     */
    @RequestMapping(value = "/queryByBase", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryByBase(@RequestParam String projectUid, QueryBase queryBase) {
        Map<String, Object> result = new HashMap<>();
        queryBase.setSort(HzVwoInfo.reflectToDBField(queryBase.getSort()));
        result.put("totalCount", iHzVwoInfoService.tellMeHowManyOfIt(projectUid));
        result.put("result", iHzVwoInfoService.doSelectListByProjectUid(queryBase, projectUid));
        return result;
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
        Map<String, Object> result = new HashMap<>();
        List<HzVwoInformChanges> list = iHzVwoInformChangesService.doSelectByVwoId(vwo);
        Long totalCount = iHzVwoInformChangesService.tellMeHowManyOfIt(vwo);
        result.put("totalCount", totalCount);
        result.put("result", list);
        return result;
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

    @RequestMapping(value = "/saveInformChanger", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveInformChanger(@RequestBody HzVwoInformChanges change) {
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

    @RequestMapping(value = "/deleteVwoInfoChange", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteVwoInfoChange(@RequestBody List<HzVwoInformChanges> changes) {
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

    @RequestMapping(value = "/saveBasic", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveBasic(@RequestBody HzVwoInfo info) {

        JSONObject result = new JSONObject();
        result.put("status", true);
        if (info == null || info.getId() == null) {
            result.put("status", false);
            result.put("msg", "请选择一个VWO表单进行操作");
            return result;
        } else {
            HzVwoInfo fromDb = iHzVwoInfoService.doSelectByPrimaryKey(info.getId());
            fromDb.setVwoName(info.getVwoName());
            fromDb.setContactPhoneNum(info.getContactPhoneNum());
            fromDb.setVwoDemandFinishTime(info.getVwoDemandFinishTime());
//            fromDb.setStrVwoStartEffectiveTime(info.getStrVwoStartEffectiveTime());
//            fromDb.setStrVwoEndEffectiveTime(info.getStrVwoEndEffectiveTime());
            fromDb.setVwoProjectStage(info.getVwoProjectStage());
            fromDb.setVwoChangeReason(info.getVwoChangeReason());
            fromDb.setVwoStartEffectiveTime(info.getVwoStartEffectiveTime());
            fromDb.setVwoEndEffectiveTime(info.getVwoEndEffectiveTime());
            fromDb.setVwoConnectedVwo(info.getVwoConnectedVwo());
            if (!iHzVwoInfoService.doUpdateByPrimaryKey(fromDb)) {
                logger.error("无法更新VWO数据,VWO ID为:" + info.getId());
                result.put("status", false);
                result.put("msg", "无法更新VWO数据");
            }
            return result;
        }
    }

    @RequestMapping(value = "/saveInfluenceDept", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveInfluenceDept(@RequestBody HzVwoInfluenceDept dept) {

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
        JSONObject result = new JSONObject();
        String name = "";
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
     * 获取用户和部门等组织架构信息
     *
     * @return
     */
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
}