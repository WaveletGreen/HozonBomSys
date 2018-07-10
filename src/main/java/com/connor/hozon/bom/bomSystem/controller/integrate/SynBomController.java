package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynBomService;
import com.connor.hozon.bom.resources.dto.request.EditHzMaterielReqDTO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 同步BOM数据
 */
@Controller
@RequestMapping("/synBom")
public class SynBomController {
    /**
     * 服务层
     */
    @Autowired
    ISynBomService bomService;

    /*   */

    /**
     * 根据项目同步所有数据
     *
     * @param projectUid
     * @return
     *//*
    @RequestMapping("/synAllBomByProjectPuid")
    @ResponseBody
    public JSONObject synAllBomByProjectPuid(@RequestParam("projectUid") String projectUid) {
        JSONObject result = new JSONObject();
        if (projectUid == null || "".equalsIgnoreCase(projectUid)) {
            result.put("status", false);
            result.put("result", "请选择项目再操作!");
            return result;
        }
        return bomService.synAllByProjectUid(projectUid);
    }*/
    @RequestMapping("/synAllBomByProjectPuid")
    public String synAllBomByProjectPuid(@RequestParam("projectUid") String projectUid, Model model) {
        if (projectUid == null || "".equals(projectUid)) {
            model.addAttribute("msg", "未选择项目进行操作，请选择一个项目再操作!");
            return "errorWithEntity";
        }

        JSONObject entities = bomService.synAllByProjectUid(projectUid);

        model.addAttribute("msgOfSuccess", "发送成功项");
        model.addAttribute("msgOfFail", "发送失败项");
        model.addAttribute("success", entities.get("success"));
        model.addAttribute("fail", entities.get("fail"));
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
        return bomService.deleteByUids(dtos.get(0).getPuid(), projectUid);
    }

    /**
     * 根据UID更新BOM
     *
     * @param dtos
     * @return
     */
    @RequestMapping("/updateByUids")
    @ResponseBody
    public JSONObject updateByUids(@RequestBody List<EditHzMaterielReqDTO> dtos, @RequestParam("projectUid") String projectUid) {
        JSONObject result = validate(dtos, projectUid);
        if (!result.getBoolean("status")) {
            return result;
        }
        return bomService.updateByUids(dtos.get(0).getPuid(), projectUid);
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
