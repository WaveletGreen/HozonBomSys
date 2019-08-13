/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.connor.hozon.bom.bomSystem.iservice.integrate.SynMaterielService;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzMaterielReqDTO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.net.connor.hozon.common.util.StringHelper.checkString;

/**
 * 同步物料主数据 x
 */
@Controller
@RequestMapping("/synMateriel")
public class SynMaterielController extends ExtraIntegrate {

    @Autowired
    SynMaterielService synMaterielService;

    /**
     * 删除物料主数据
     *
     * @param dtos
     * @return
     */
    @RequestMapping("/deleteByPuids")
    @ResponseBody
    public JSONObject deleteByPuids(List<EditHzMaterielReqDTO> dtos) {
        return synMaterielService.deleteByPuids(dtos,"HZ_MATERIEL_RECORD","P_MATERIEL_CODE");
    }

    /**
     * 更新物料主数据
     *
     * @param dtos
     * @return
     */
    @RequestMapping(value = "/updateOrAddByUids", method = RequestMethod.POST)
    public String updateOrAddByUids(@RequestBody List<EditHzMaterielReqDTO> dtos, Model model) {
        JSONObject entities = synMaterielService.updateOrAddByUids(dtos,"HZ_MATERIEL_RECORD","P_MATERIEL_CODE");
        addToModel(entities, model);
        return "stage/templateOfIntegrate";
    }

    /**
     * 一开始触发同步所有数据
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/synAllByProjectPuid", method = RequestMethod.POST)
    public String synAllByProjectPuid(@RequestParam("projectId") String projectId, Model model) {
        JSONObject result = new JSONObject();
        if (!checkString(projectId)) {
            result.put("msg", "请选择项目再操作!");
            return "errorWithEntity";
        }
        JSONObject entities = synMaterielService.synAllByProjectPuid(projectId,"HZ_MATERIEL_RECORD","P_MATERIEL_CODE");
        addToModel(entities, model);
        return "stage/templateOfIntegrate";
    }

}
