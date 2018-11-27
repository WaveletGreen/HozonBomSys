/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzFeatureChangeService;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVWOManagerService;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;
import sql.pojo.change.HzChangeOrderRecord;

import java.util.ArrayList;
import java.util.Iterator;
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
     * 特性变更表服务
     */
    @Autowired
    IHzFeatureChangeService iHzFeatureChangeService;

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
     * 特性跳转到变更表单选择页面
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

    /**
     * 特性撤销,返回最近一次生效数据
     */
    @RequestMapping("/goBackData")
    @ResponseBody
    public JSONObject goBackData(@RequestBody List<HzCfg0Record> records,@RequestParam String projectUid){
        JSONObject reslut = new JSONObject();
        reslut.put("status",true);
        reslut.put("msg","撤销成功");
        /*********找出所有删除状态的特性************/
        List<HzCfg0Record> hzCfg0RecordListDelete = new ArrayList<>();

        Iterator<HzCfg0Record> hzCfg0RecordIterator = records.iterator();
        while (hzCfg0RecordIterator.hasNext()){
            HzCfg0Record hzCfg0Record = hzCfg0RecordIterator.next();
            if(hzCfg0Record.getCfgStatus()==2){
                hzCfg0Record.setCfgStatus(1);
                hzCfg0RecordListDelete.add(hzCfg0Record);
                hzCfg0RecordIterator.remove();
            }
        }
        if(hzCfg0RecordListDelete!=null&&hzCfg0RecordListDelete.size()!=0){
            int deleteNum = hzCfg0RecordDao.updateStatus(hzCfg0RecordListDelete);
            if(deleteNum<=0){
                reslut.put("status",false);
                reslut.put("msg","撤销删除数据失败");
                return  reslut;
            }
        }
        /********找出所有修改的特性并还原为修改之前的数据*******/
        if(records==null||records.size()==0){
            return reslut;
        }
        List<HzFeatureChangeBean> hzFeatureChangeBeans = iHzFeatureChangeService.doSelectHasEffect(records);
        //需修改的特性集合
        List<HzCfg0Record> hzCfg0RecordListUpdata = new ArrayList<>();
        for(HzFeatureChangeBean hzFeatureChangeBean : hzFeatureChangeBeans){
            HzCfg0Record hzCfg0Record = hzFeatureChangeBean.getHzCfg0Record();
            hzCfg0RecordListUpdata.add(hzCfg0Record);
        }
        if(hzCfg0RecordListUpdata!=null&&hzCfg0RecordListUpdata.size()!=0){
            try {
                hzCfg0RecordDao.updateListAll(hzCfg0RecordListUpdata);
            }catch (Exception e) {
                    reslut.put("status", false);
                    reslut.put("msg", "撤销修改数据失败");
                    return reslut;
            }
        }
        //从特性数据集中删除修改的数据，只留下新增的数据
        Iterator<HzCfg0Record> hzCfg0RecordIteratorUpdate = records.iterator();
        while (hzCfg0RecordIteratorUpdate .hasNext()){
            HzCfg0Record hzCfg0Record = hzCfg0RecordIteratorUpdate .next();
            for(HzFeatureChangeBean hzFeatureChangeBean : hzFeatureChangeBeans){
                if(hzCfg0Record.getPuid().equals(hzFeatureChangeBean.getCfgPuid())){
                    hzCfg0RecordIteratorUpdate .remove();
                    break;
                }
            }
        }
        /*****************删除新增的数据***************/
        if(records!=null&&records.size()!=0){
            int addNum = hzCfg0RecordDao.deleteCfgByList(records);
            if(addNum<=0){
                reslut.put("status",false);
                reslut.put("msg","撤销新增数据失败");
                return  reslut;
            }
        }
        return reslut;
    }

    @RequestMapping("/changeDetails")
    public String changeDetails(){
        return "";
    }
}
