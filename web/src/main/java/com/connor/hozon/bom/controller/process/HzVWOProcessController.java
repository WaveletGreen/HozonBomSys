/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.controller.process;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureValueDao;
import com.connor.hozon.bom.service.change.vwo.HzFeatureChangeService;
import com.connor.hozon.bom.service.change.vwo.HzVWOManagerService;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureChangeBean;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;

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
    HzVWOManagerService iHzVWOManagerService;

    @Autowired
    HzChangeOrderDAO hzChangeOrderDAO;

    @Autowired
    HzFeatureValueDao hzFeatureValueDao;
    /**
     * 特性变更表服务
     */
    @Autowired
    HzFeatureChangeService hzFeatureChangeService;

    /**
     * 特性进入VWO流程Controller
     *
     * @param projectUid 项目UID
     * @param beans      特性值对象
     * @return
     */
    @RequestMapping("/featureGetIntoVWO")
    @ResponseBody
    public JSONObject featureGetIntoVWO(@RequestParam String projectUid, @RequestBody List<HzFeatureValue> beans, Long changeFromId) {
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
        List<HzFeatureValue> beans = hzFeatureValueDao.selectByPuids(puidList);
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
    public JSONObject goBackData(@RequestBody List<HzFeatureValue> records, @RequestParam String projectUid){
        JSONObject reslut = new JSONObject();
        reslut.put("status",true);
        reslut.put("msg","撤销成功");
        /*********找出所有删除状态的特性************/
        List<HzFeatureValue> hzFeatureValueListDelete = new ArrayList<>();

        Iterator<HzFeatureValue> hzCfg0RecordIterator = records.iterator();
        while (hzCfg0RecordIterator.hasNext()){
            HzFeatureValue hzFeatureValue = hzCfg0RecordIterator.next();
            if(hzFeatureValue.getCfgStatus()==2){
                hzFeatureValue.setCfgStatus(1);
                hzFeatureValueListDelete.add(hzFeatureValue);
                hzCfg0RecordIterator.remove();
            }
        }
        if(hzFeatureValueListDelete !=null&& hzFeatureValueListDelete.size()!=0){
            int deleteNum = hzFeatureValueDao.updateStatus(hzFeatureValueListDelete);
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
        List<HzFeatureChangeBean> hzFeatureChangeBeans = hzFeatureChangeService.doSelectHasEffect(records);
        //需修改的特性集合
        List<HzFeatureValue> hzFeatureValueListUpdata = new ArrayList<>();
        for(HzFeatureChangeBean hzFeatureChangeBean : hzFeatureChangeBeans){
            HzFeatureValue hzFeatureValue = hzFeatureChangeBean.getHzCfg0Record();
            hzFeatureValueListUpdata.add(hzFeatureValue);
        }
        if(hzFeatureValueListUpdata !=null&& hzFeatureValueListUpdata.size()!=0){
            try {
                hzFeatureValueDao.updateListAll(hzFeatureValueListUpdata);
            }catch (Exception e) {
                    reslut.put("status", false);
                    reslut.put("msg", "撤销修改数据失败");
                    return reslut;
            }
        }
        //从特性数据集中删除修改的数据，只留下新增的数据
        Iterator<HzFeatureValue> hzCfg0RecordIteratorUpdate = records.iterator();
        while (hzCfg0RecordIteratorUpdate .hasNext()){
            HzFeatureValue hzFeatureValue = hzCfg0RecordIteratorUpdate .next();
            for(HzFeatureChangeBean hzFeatureChangeBean : hzFeatureChangeBeans){
                if(hzFeatureValue.getPuid().equals(hzFeatureChangeBean.getCfgPuid())){
                    hzCfg0RecordIteratorUpdate .remove();
                    break;
                }
            }
        }
        /*****************删除新增的数据***************/
        if(records!=null&&records.size()!=0){
            int addNum = hzFeatureValueDao.deleteCfgByList(records);
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
