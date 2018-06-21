package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ModelService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ModelRecordService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.HzCfg0ModelDetail;
import sql.pojo.cfg.HzCfg0ModelRecord;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Controller
@RequestMapping("/model")
public class HzCfg0ModelController {
    /**
     * 模型的详细信息
     */
    @Autowired
    HzCfg0ModelService hzCfg0ModelService;
    /**
     * 数据库中的车型模型，没有详细信息
     */
    @Autowired
    HzCfg0ModelRecordService hzCfg0modelRecordService;

    @RequestMapping(value = "/saveModelData", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveModelData(@RequestBody HzCfg0ModelDetail detail) {
        JSONObject result = new JSONObject();
        StringBuilder sb = new StringBuilder();
        boolean isSuccess;
        if (detail == null || (detail.getpModelPuid() == null || "".equals(detail.getpModelPuid()))) {
            result.put("state", false);
            result.put("msg", "没有找到模型信息，请联系管理员");
            return result;
        }
        HzCfg0ModelDetail fromDBDetail = hzCfg0ModelService.getOneByModelId(detail);
        if (fromDBDetail != null) {
            detail.setpModelPuid(fromDBDetail.getpModelPuid());
            detail.setPuid(fromDBDetail.getPuid());
            isSuccess = hzCfg0ModelService.doUpdateOne(detail);
            sb.append("更新");
        } else {
            detail.setPuid(UUID.randomUUID().toString());
            isSuccess = hzCfg0ModelService.doInsertOne(detail);
            sb.append("数据");
        }
        result.put("msg", sb + detail.getpModelName() + "成功");
        result.put("state", isSuccess);
        return result;
    }

    @RequestMapping(value = "/modModel", method = RequestMethod.GET)
    public String modifyModel(@RequestParam String pModelPuid, Model model) {
        HzCfg0ModelDetail fromDBDetail = new HzCfg0ModelDetail();
        fromDBDetail.setpModelPuid(pModelPuid);
        fromDBDetail = hzCfg0ModelService.getOneByModelId(fromDBDetail);
        if (fromDBDetail == null) {
            HzCfg0ModelRecord record = hzCfg0modelRecordService.doGetById(pModelPuid);
            if (record == null) {
                return "error";
            } else {
                fromDBDetail = new HzCfg0ModelDetail();
                fromDBDetail.setpModelPuid(record.getPuid());
                fromDBDetail.setpModelName(record.getObjectName());
                fromDBDetail.setpModelDesc(record.getObjectDesc());
//                detail.setpModelPuid(record.getPuid());
//                detail.setpModelName(record.getObjectName());
//                detail.setpModelDesc(record.getObjectDesc());
                model.addAttribute("entity", fromDBDetail);
            }
        } else {
            model.addAttribute("entity", fromDBDetail);
        }
        return "bom/addModel";
    }


    private static void saveModelDetailToDB(@NotNull HzCfg0ModelController controller) {
        HzCfg0ModelDetail detail = new HzCfg0ModelDetail();
        detail.setpModelPuid("046adedc-09b2-43ca-a49c-a99d47c9fa3e");
        detail.setpModelName("setpModelName");
        detail.setpModelDesc("setpModelDesc");
        detail.setpModelSaleArea("setpModelSaleArea");
        detail.setpModelBrand("setpModelBrand");
        detail.setpModelVehicle("setpModelVehicle");
        detail.setpModelPlatform("setpModelPlatform");
        detail.setpModelMod("setpModelMod");
        detail.setpModelAnnual("setpModelAnnual");
        detail.setpModelVersion("setpModelVersion");
        detail.setpModelTransform("setpModelTransform");
        detail.setpModelDriverPosition("setpModelDriverPosition");
        detail.setpModelMembers("setpModelMembers");
        detail.setpModelShape("setpModelShape");
        detail.setpModelAnnouncement("setpModelAnnouncement");
        detail.setpModelPowers("setpModelPowers");
        detail.setpModelCfgVersion("setpModelCfgVersion");
        detail.setpModelCfgDesc("setpModelCfgDesc");
        detail.setpModelTrailNum("setpModelTrailNum");
        detail.setpModelGoodsNum("setpModelGoodsNum");
        controller.saveModelData(detail);
        System.out.println();
    }

}
