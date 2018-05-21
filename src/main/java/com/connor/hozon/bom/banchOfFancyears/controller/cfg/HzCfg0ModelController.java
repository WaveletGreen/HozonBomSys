package com.connor.hozon.bom.banchOfFancyears.controller.cfg;

import com.connor.hozon.bom.banchOfFancyears.service.cfg.HzCfg0ModelService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0ModelDetail;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Controller
@RequestMapping("/model")
public class HzCfg0ModelController {
    @Autowired
    HzCfg0ModelService hzCfg0ColorSerService;

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
        HzCfg0ModelDetail fromDBDetail = hzCfg0ColorSerService.getOneByModelId(detail);
        if (fromDBDetail != null) {
            detail.setpModelPuid(fromDBDetail.getpModelPuid());
            detail.setPuid(fromDBDetail.getPuid());
            isSuccess = hzCfg0ColorSerService.doUpdateOne(detail);
            sb.append("updateOne");
        } else {
            detail.setPuid(UUID.randomUUID().toString());
            isSuccess = hzCfg0ColorSerService.doInsertOne(detail);
            sb.append("record");
        }
        result.put("msg", sb + detail.getpModelName() + "success");
        result.put("state", isSuccess);
        return result;
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

    @Deprecated
    public static void main(String[] args) {
        HzCfg0ModelController controller = new HzCfg0ModelController();
        saveModelDetailToDB(controller);
    }
}
