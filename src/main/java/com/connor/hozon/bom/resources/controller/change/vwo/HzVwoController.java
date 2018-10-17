package com.connor.hozon.bom.resources.controller.change.vwo;

import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoFormListQueryBase;
import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoProcessDto;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVWOManagerService;
import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.vwo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/8/14
 * Time: 10:52
 */
@Controller
@RequestMapping(value = "/vwo")
public class HzVwoController {

    @Autowired
    IHzVWOManagerService iHzVWOManagerService;

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
        if (iHzVWOManagerService.getVwoFromList(id, vwoType, model)) {
            return "changeManage/vwo/vwoBasicInformation";
        } else {
            return "errorWithEntity";
        }
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
     * @param projectUid
     * @param queryBase
     * @return
     */
    @RequestMapping(value = "/queryByBase", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryByBase(@RequestParam String projectUid, HzVwoFormListQueryBase queryBase) {
        return iHzVWOManagerService.queryByBase(projectUid, queryBase);
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

}