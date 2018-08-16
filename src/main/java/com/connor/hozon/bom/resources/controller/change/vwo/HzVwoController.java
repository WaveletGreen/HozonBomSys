package com.connor.hozon.bom.resources.controller.change.vwo;

import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzFeatureChangeService;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzVwoInfoService;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzVwoInformChangesService;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;
import sql.pojo.cfg.vwo.HzVwoInfo;
import sql.pojo.cfg.vwo.HzVwoInformChanges;

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
    IHzVwoInfoService iHzVwoInfoService;

    @Autowired
    IHzFeatureChangeService iHzFeatureChangeService;

    @Autowired
    IHzVwoInformChangesService iHzVwoInformChangesService;

    /**
     * 详情页面
     *
     * @param vwo
     * @param vwoType
     * @param model
     * @return
     */
    @RequestMapping(value = "/vwoFormList", method = RequestMethod.GET)
    public String getVwoFromList(@RequestParam Long vwo, @RequestParam Integer vwoType, Model model) {
        HzVwoInfo vwoInfo = iHzVwoInfoService.doSelectByPrimaryKey(vwo);
        if (1 == vwoType) {
            List<HzFeatureChangeBean> after = iHzFeatureChangeService.doSelectAfterByVwoId(vwo);
            List<HzFeatureChangeBean> before = iHzFeatureChangeService.doSelectBeforeByVwoId(vwo);
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
        model.addAttribute("vwo", vwo);
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
    public Map<String, Object> getInformChanges(@RequestParam Long vwo, QueryBase queryBase) {
        Map<String, Object> result = new HashMap<>();
        List<HzVwoInformChanges> list = iHzVwoInformChangesService.doSelectByVwoId(vwo);
        Long totalCount = iHzVwoInformChangesService.tellMeHowManyOfIt(vwo);
        result.put("totalCount", totalCount);
        result.put("result", list);
        return result;
    }

}