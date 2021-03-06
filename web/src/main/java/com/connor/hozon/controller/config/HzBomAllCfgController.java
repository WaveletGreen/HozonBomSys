/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.config;

import cn.net.connor.hozon.common.util.DateStringUtils;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgMain;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelDetail;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import com.connor.hozon.service.configuration.fullCfg.HzBomAllCfgService;
import net.sf.json.JSONObject;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.net.connor.hozon.common.util.StringUtils.checkString;


/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 全配置BOM一级清单
 * 配置管理controller的所有返回消息字段key都是msg
 * 配置管理controller的所有返回成功标志字段key都是status
 * 已完成注释
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Controller
@RequestMapping("/bomAllCfg")
public class HzBomAllCfgController {
    /***全配置BOM一级清单服务*/
    @Autowired
    HzBomAllCfgService hzBomAllCfgService;

    /**
     * 获取添加基础车型页面
     *
     * @param projectPuid 项目UID
     * @param model       不用传
     * @return
     */
    @RequestMapping("/addVehicleModelPage2")
    public String addVehicleModelPage2(@RequestParam String projectPuid, Model model) {
        if (checkString(projectPuid)) {
            HzCfg0ModelDetail detail = new HzCfg0ModelDetail();
            HzMainConfig mainRecord = new HzMainConfig();
            hzBomAllCfgService.initAddingPageParams(projectPuid, detail, mainRecord);
            model.addAttribute("hzCfg0ModelDetail", detail);
            model.addAttribute("cfgmain", mainRecord);
            model.addAttribute("action", "./materielV2/addVehicleModel2");
            return "bom/addModel2";
        } else {
            model.addAttribute("msg", "请选择项目再操作");
            return "errorWithEntity";
        }
    }

    /**
     * 全配置BOM一级清单页面初始化
     *
     * @param bdf 项目UID
     * @return
     */
    @RequestMapping("/loadCfg0BomLineOfModel")
    @ResponseBody
    public JSONObject loadCfg0BomLineOfModel(@RequestParam String bdf,@RequestParam String orderKey) {
        return hzBomAllCfgService.parse(bdf,orderKey);
    }

    /**
     * 保存2Y层对应的各数据
     *
     * @param bomLinePuid 2Y层id
     * @param cfgPuid     特性id
     * @param colorPart   是否颜色件
     * @param msgVal      备注
     * @return
     */
    @RequestMapping("/saveOneRow")
    @ResponseBody
    public JSONObject saveOneRow(String bomLinePuid, String cfgPuid, Integer colorPart, String msgVal,String projectPuid) {
        return hzBomAllCfgService.saveOneRow(bomLinePuid, cfgPuid, colorPart, msgVal,projectPuid);
    }

    /**
     * 保存所有打点图
     *
     * @param data 所有打点图信息<车辆模型id<特性id，打点图信息>>
     * @return
     */
    @RequestMapping("/savePoint")
    @ResponseBody
    public JSONObject savePoint(@RequestBody Map<String, Map<String, String>> data,String projectPuid) {
        return hzBomAllCfgService.savePoint(data,projectPuid);
    }

    /**
     * 删除车辆模型，将全配置BOM一级清单下对应的某个基本车型删除，但同时也会删除掉其下的所有打点图
     *
     * @param modelId 车辆模型id
     * @return
     */
    @RequestMapping("/deleteModel")
    @ResponseBody
    public JSONObject deleteModel(@RequestParam String modelId) {
        return hzBomAllCfgService.deleteModel(modelId);
    }

    /**
     * 获取阶段弹窗页面
     *
     * @return
     * @Autor Fancyears
     */
    @RequestMapping("setStageOrVersionPage")
    public String setStagePage(@RequestParam String projectUid, String setName, Model model) {
        if (!checkString(projectUid)) {
            model.addAttribute("msg", "请选择一个项目进行操作");
            return "errorWithEntity";
        }
        HzFullCfgMain fullCfgMain = hzBomAllCfgService.getFullCfgMain(projectUid);
        if (fullCfgMain == null) {
            model.addAttribute("msg", "该项目下没有全配置BOM一级清单表，请先添加2Y层和特性值再进行操作");
            return "errorWithEntity";
        }
        String releaseDate = fullCfgMain.getEffectiveDate() == null ? "" : DateStringUtils.dateToString(fullCfgMain.getEffectiveDate());
        model.addAttribute("releaseDate", releaseDate);
        if (setName != null && "version".equals(setName)) {
            Integer stage = fullCfgMain.getStage();
            String stageStr = HzFullCfgMain.parseStage(stage);
            model.addAttribute("entity", fullCfgMain);
            model.addAttribute("stageStr", stageStr);
            model.addAttribute("action", "./bomAllCfg/setVersion");
            return "bom/setVersionPage";
        } else if (setName != null && "stage".equals(setName)) {
            model.addAttribute("entity", fullCfgMain);
            model.addAttribute("action", "./bomAllCfg/setStage");
            return "bom/setStagePage";
        }
        model.addAttribute("msg", "发生未知错误");
        return "errorWithEntity";
    }

    /**
     * 保存版本数据，对当前全配置BOM一级清单进行升大版本操作
     *
     * @param params
     * @return
     */
    @RequestMapping("setVersion")
    @ResponseBody
    public JSONObject setVersion(@RequestBody Map<String, String> params) {
        return hzBomAllCfgService.setVersion(params);
    }

    /**
     * 保存阶段数据
     *
     * @param params
     * @return
     */
    @RequestMapping("setStage")
    @ResponseBody
    public JSONObject setStage(@RequestBody Map<String, String> params) {
        return hzBomAllCfgService.setStage(params);
    }

    /**
     * 查询所有以关联2Y层的特性和当前2Y层关联的特性，实现前端特性选择下拉列表的动态效果
     *
     * @param projectPuid 项目UID
     * @param bomLineId   2Y层主键
     * @return
     */
    @RequestMapping("query2YCfg")
    @ResponseBody
    public JSONObject query2YCfg(@RequestParam String projectPuid, @RequestParam String bomLineId) {
        return hzBomAllCfgService.query2YCfg(projectPuid, bomLineId);
    }

    /**
     * 升小版本，为全配置BOM一级清单进行当前版本+0.1小版本状态
     * 仅仅是升级小版本，不是升级为大版本，升级大版本{@link HzBomAllCfgController#}
     *
     * @param projectUid 项目id
     * @return
     */
    @RequestMapping(value = "promote", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject promote(@RequestParam String projectUid) {
        return hzBomAllCfgService.promote(projectUid);
    }


    /**
     * 保存2Y层对应的打点图
     *
     * @param dataMap
     * @return
     */
    @RequestMapping(value = "saveBomLinePiont", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveBomLinePiont(@RequestBody Map<String, Map<String, String>> dataMap) {
        return hzBomAllCfgService.saveBomLinePiont(dataMap);
    }

    /**
     * 选择变更表单
     */
    @RequestMapping("setChangeFromPage")
    public String setChangeFrom(String projectUid, Model model){
        List<HzChangeOrderRecord> hzChangeOrderRecordList = hzBomAllCfgService.getChangeFroms(projectUid);
        model.addAttribute("changeFroms",hzChangeOrderRecordList);
        return "bom/setChangeFrom";
    }
    /**
     * 发起变更表单
     * @param projectId 项目id
     * @return
     */
    @RequestMapping("getVwo")
    @ResponseBody
    public JSONObject getVwo(String projectId, Integer changeFromId){
        return hzBomAllCfgService.getVwo(projectId, changeFromId);
    }

    /**
     * 撤销
     * @param projectUid
     * @return
     */
    @RequestMapping("goBackData")
    @ResponseBody
    public JSONObject goBackData(@RequestParam String projectUid){
        return hzBomAllCfgService.goBackData(projectUid);
    }

    @RequestMapping("getExcel")
    public ResponseEntity<byte[]> getExcel(String projectUid,String orderKey) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        Date date = new Date();
        String pattern = "yyyy-MM-dd HH：mm：ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String dateStr = sdf.format(date);
        String fileName = "全配置BOM一级清单"+dateStr+".xlsx";

        SXSSFWorkbook hssfWorkbook = hzBomAllCfgService.getWorkBook(projectUid,orderKey);

        try {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
            if(hssfWorkbook==null){
                return new ResponseEntity(null,headers,HttpStatus.NOT_FOUND);
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            hssfWorkbook.write(os);
            byte[] bytes = os.toByteArray();
            os.flush();
            os.close();
            return new ResponseEntity<byte[]>(bytes,
                    headers, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(null,headers,HttpStatus.NOT_FOUND);
        }
    }
}
