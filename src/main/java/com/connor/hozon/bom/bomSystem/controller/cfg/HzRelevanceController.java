package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.IHzColorModelService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 相关性表
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Controller
@RequestMapping("/relevance")
public class HzRelevanceController {
    /**
     * 特性值
     */
    @Autowired
    HzCfg0Service hzCfg0Service;
    /**
     * 配色车型
     */
    @Autowired
    IHzColorModelService hzColorModelService;
    /**
     * 特性
     */
    @Autowired
    HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;

    @RequestMapping("/generateRelevance")
    @ResponseBody
    public JSONObject generateRelevance() {
        JSONObject object = new JSONObject();
        return object;
    }

}
