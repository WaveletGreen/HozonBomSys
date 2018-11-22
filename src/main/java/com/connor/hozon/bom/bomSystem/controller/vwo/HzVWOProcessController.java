/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVWOManagerService;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.change.HzChangeOrderRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/8/9 11:13
 * @Modified By:
 */
@Controller
@RequestMapping("/vwoProcess")
public class HzVWOProcessController {

    @Autowired
    IHzVWOManagerService iHzVWOManagerService;

    @Autowired
    HzChangeOrderDAO hzChangeOrderDAO;

    @Autowired
    HzCfg0RecordDao hzCfg0RecordDao;
    /**
     * 特性进入VWO流程Controller
     *
     * @param projectUid 项目UID
     * @param beans      特性值对象
     * @return
     */
    @RequestMapping("/featureGetIntoVWO")
    @ResponseBody
    public JSONObject featureGetIntoVWO(@RequestParam String projectUid, @RequestBody List<HzCfg0Record> beans,Long changeFromId) {
        return iHzVWOManagerService.featureGetIntoVWO2(projectUid, beans, changeFromId);
    }

    /**
     * 跳转到变更表单选择页面
     */
    @RequestMapping("/setChangeFromPage")
    public String setChangeFromPage(String projectUid, String puids, Model model){
        List<String> puidList = new ArrayList<>();
        String[] puidArr = puids.split(",");
        for(String puid : puidArr){
            puidList.add(puid);
        }
        List<HzCfg0Record> beans = hzCfg0RecordDao.selectByPuids(puidList);
        List<HzChangeOrderRecord> hzChangeOrderRecordList = hzChangeOrderDAO.findHzChangeOrderRecordByProjectId(projectUid);
        model.addAttribute("beans",beans);
        model.addAttribute("changeFroms",hzChangeOrderRecordList);
        return "cfg/feature/featureSetChangeFrom";
    }

    @RequestMapping("/changeDetails")
    public String changeDetails(){
        return "";
    }
}
