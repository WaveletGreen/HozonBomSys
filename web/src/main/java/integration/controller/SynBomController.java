/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.controller;

import com.connor.hozon.service.configuration.fullCfg.HzConfigToBomLineService;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzMaterielReqDTO;
import integration.service.integrate.SynBomService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步BOM数据 x
 */
@Controller
@RequestMapping("/synBom")
public class SynBomController extends ExtraIntegrateController {
    /**
     * 服务层
     */
    @Autowired
    SynBomService bomService;

    @Autowired
    HzConfigToBomLineService hzConfigToBomLineService;

    @RequestMapping("/synAllBomByProjectPuid")
    public String synAllBomByProjectPuid(@RequestParam("projectUid") String projectUid, Model model) {
        if (projectUid == null || "".equals(projectUid)) {
            model.addAttribute("msg", "未选择项目进行操作，请选择一个项目再操作!");
            return "errorWithEntity";
        }

        JSONObject entities = bomService.synAllByProjectUid(projectUid);
        addToModel(entities, model);
        return "stage/templateOfIntegrate";
    }

    /**
     * 根据UID删除，真正意义上的删除
     *
     * @param dtos
     * @return
     */
    @RequestMapping("/deleteByUids")
    @ResponseBody
    public JSONObject deleteByUids(@RequestBody List<EditHzMaterielReqDTO> dtos, @RequestParam("projectUid") String projectUid) {
        JSONObject result = validate(dtos, projectUid);
        if (!result.getBoolean("status")) {
            return result;
        }
        List<String> puids = new ArrayList<>();
        dtos.forEach(dto -> puids.add(dto.getPuid()));
        return bomService.deleteByUids(projectUid, puids);
    }

    /**
     * 根据UID更新BOM
     *
     * @param dtos
     * @return
     */
    @RequestMapping("/updateByUids")
    public String updateByUids(@RequestBody List<EditHzMaterielReqDTO> dtos, @RequestParam("projectUid") String projectUid, Model model) {
//        Object obj = hzConfigToBomLineService.selectByBLUidAndPrjUid(projectUid, dtos.get(0).getPuid());
        JSONObject result = validate(dtos, projectUid);
        if (!result.getBoolean("status")) {
            model.addAttribute("msg", result.get("msg"));
            return "errorWithEntity";
        }
        JSONObject entities = bomService.updateByUids(projectUid, dtos.get(0).getPuid());
        addToModel(entities, model);
        return "stage/templateOfIntegrate";
    }

    /**
     * 根据UID更新BOM
     *
     * @param dtos
     * @return
     */
    @RequestMapping("/addByUids")
    public String addByUids(@RequestBody List<EditHzMaterielReqDTO> dtos, @RequestParam("projectUid") String projectUid, Model model) {
        JSONObject result = validate(dtos, projectUid);
        if (!result.getBoolean("status")) {
            model.addAttribute("msg", result.get("msg"));
            return "errorWithEntity";
        }
        JSONObject entities = bomService.addOne(projectUid, dtos.get(0).getPuid());
        addToModel(entities, model);
        return "stage/templateOfIntegrate";
    }


    private JSONObject validate(List<EditHzMaterielReqDTO> dtos, String projectUid) {
        JSONObject result = new JSONObject();
        if (dtos == null || dtos.isEmpty()) {
            result.put("status", false);
            result.put("msg", "没有选择任何1条MBOM行数据");
            return result;
        } else if (projectUid == null || "".equals(projectUid)) {
            result.put("status", false);
            result.put("msg", "请选择项目再进行操作!");
            return result;
        }
        result.put("status", true);
        return result;
    }

}
