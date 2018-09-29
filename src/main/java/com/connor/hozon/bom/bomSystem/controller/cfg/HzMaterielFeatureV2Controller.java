package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.controller.integrate.ExtraIntegrate;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ToModelRecordDao;
import com.connor.hozon.bom.bomSystem.dto.cfg.compose.HzComposeDelDto;
import com.connor.hozon.bom.bomSystem.dto.cfg.compose.HzComposeMFDTO;
import com.connor.hozon.bom.bomSystem.service.business.cfg.HzComposeMFService;
import com.connor.hozon.bom.bomSystem.service.cfg.*;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.IHzCfg0ModelFeatureService;
import com.connor.hozon.bom.bomSystem.service.project.HzSuperMaterielService;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.HzCfg0ModelColor;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.project.HzMaterielRecord;

import java.util.List;
import java.util.Map;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description: 配置物料表Version 2
 * Date: 2018/6/8 17:03
 */
@Controller
@RequestMapping("/materielV2")
public class HzMaterielFeatureV2Controller extends ExtraIntegrate {
    /**
     * 族层服务
     */
    @Autowired
    HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    /**
     * 车身颜色服务
     */
    @Autowired
    HzCfg0ModelColorService hzCfg0ModelColorService;
    /**
     * 颜色车型dao层
     */
    @Autowired
    HzCfg0ModelColorDao hzCfg0ModelColorDao;
    /**
     * 配置值服务层
     */
    @Autowired
    HzCfg0Service hzCfg0Service;
    /**
     * 超级物料服务层
     */
    @Autowired
    HzSuperMaterielService hzSuperMaterielService;
    /**
     * 车型模型服务层
     */
    @Autowired
    HzCfg0ModelRecordService hzCfg0ModelRecordService;
    /**
     * 配置主模型服务层
     */
    @Autowired
    HzCfg0MainService hzCfg0MainService;
    /**
     * 模型特性数据服务
     */
    @Autowired
    IHzCfg0ModelFeatureService hzCfg0ModelFeatureService;
    /**
     * 工厂
     */
    @Autowired
    HzFactoryDAO hzFactoryDAO;
    /**
     * 配置-模型关系
     */
    @Autowired
    HzCfg0ToModelRecordDao hzCfg0ToModelRecordDao;

    @Autowired
    HzComposeMFService hzComposeMFService;


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
        List<HzCfg0ModelRecord> models = hzCfg0ModelRecordService.doSelectByProjectUid(projectUid);
        if (models == null || models.size() <= 0) {
            model.addAttribute("msg", "没有车型模型，请至少添加一个车型模型");
            return "errorWithEntity";
        }
        HzMaterielRecord sm = hzSuperMaterielService.doSelectByProjectPuid(projectUid);
        model.addAttribute("colorModels", colorModels);
        model.addAttribute("models", models);
        model.addAttribute("projectUid", projectUid);
        model.addAttribute("sm", sm);
        model.addAttribute("action", "./materielV2/saveCompose");
        return "cfg/materielFeature/add";
    }

    @RequestMapping(value = "/saveCompose", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveCompose(@RequestBody HzComposeMFDTO hzComposeMFDTO) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        result.put("msg", "新增衍生物料成功");
        if (hzComposeMFDTO == null) {
            result.put("status", false);
            return result;
        }
        hzComposeMFService.saveCompose2(hzComposeMFDTO, result);
        return result;
    }

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
        hzComposeMFService.deleteCompose(delDtos, result);
        return result;
    }


    @RequestMapping(value = "/loadComposes", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadComposes(@RequestParam String projectPuid) {
        return hzComposeMFService.loadComposes(projectPuid, new QueryBase());
    }

    @RequestMapping("/saveCompose")
    @ResponseBody
    public JSONObject saveCompose(String projectPuid){
        return hzComposeMFService.saveCompose3(projectPuid);
    }
}
