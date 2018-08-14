package com.connor.hozon.bom.resources.controller.change.vwo;

import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzFeatureChangeService;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzVwoInfoService;
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


    @RequestMapping(value = "vwoFormList", method = RequestMethod.GET)
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
        return "changeManage/vwo/vwoBasicInformation";
    }

    @RequestMapping(value = "returnToVwoFromList", method = RequestMethod.GET)
    public String returnToVwoFromList(Model model) {
        model.addAttribute("href", "vwoFromList");
        return "changeManage/vwo/vwoFormList";
    }

    @RequestMapping(value = "/queryByBase", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryByBase(@RequestParam String projectUid, QueryBase queryBase) {
        Map<String, Object> result = new HashMap<>();
        queryBase.setSort(HzVwoInfo.reflectToDBField(queryBase.getSort()));
        result.put("totalCount", iHzVwoInfoService.tellMeHowManyOfIt(projectUid));
        result.put("result", iHzVwoInfoService.doSelectListByProjectUid(queryBase, projectUid));
        return result;
    }

}