package com.connor.hozon.bom.sys.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.BaseSQLUtil;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0ModelDetail;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Controller
@RequestMapping("/model")
public class Cfg0ModelController {
    IBaseSQLUtil baseSQLUtil;

    @RequestMapping(value = "/saveModelData", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveModelData(@RequestBody HzCfg0ModelDetail detail) {
        if (baseSQLUtil == null) {
            baseSQLUtil = new BaseSQLUtil();
        }
        JSONObject result = new JSONObject();
        if (detail == null || (detail.getpModelPuid() == null || "".equals(detail.getpModelPuid()))) {
            result.put("state", false);
            result.put("msg", "it seem don't has the model data,please contact with the administrator");
            return result;
        }
        HzCfg0ModelDetail fromDBDetail = baseSQLUtil.executeQueryById(detail, "sql.mapper.cfg.i.HzCfg0ModelDetailMapper.selectByModelId");
        StringBuilder sb = new StringBuilder();
        if (fromDBDetail != null) {
            detail.setpModelPuid(fromDBDetail.getpModelPuid());
            detail.setPuid(fromDBDetail.getPuid());
            baseSQLUtil.executeUpdate(detail, "sql.mapper.cfg.i.HzCfg0ModelDetailMapper.update");
            sb.append("update");
        } else {
            detail.setPuid(UUID.randomUUID().toString());
            baseSQLUtil.executeInsert(detail, "sql.mapper.cfg.i.HzCfg0ModelDetailMapper.insert");
            sb.append("record");
        }
        result.put("msg", sb + detail.getpModelName() + "success");
        result.put("state", true);
        return result;
    }


    private static void saveModelDetailToDB(@NotNull Cfg0ModelController controller) {
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
        Cfg0ModelController controller = new Cfg0ModelController();
        saveModelDetailToDB(controller);
    }
}
