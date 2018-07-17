package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynMaterielService;
import com.connor.hozon.bom.resources.dto.request.EditHzMaterielReqDTO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * 同步物料主数据
 */
@Controller
@RequestMapping("/synMateriel")
public class SynMaterielController {

    @Autowired
    ISynMaterielService iSynMaterielService;

    /**
     * 删除物料主数据
     *
     * @param dtos
     * @return
     */
    @RequestMapping("/deleteByPuids")
    @ResponseBody
    public JSONObject deleteByPuids(List<EditHzMaterielReqDTO> dtos) {
        return iSynMaterielService.deleteByPuids(dtos);
    }

    /**
     * 更新物料主数据
     *
     * @param dtos
     * @return
     */
    @RequestMapping(value = "/updateOrAddByUids", method = RequestMethod.POST)
    public String updateOrAddByUids(@RequestBody List<EditHzMaterielReqDTO> dtos, Model model) {
        JSONObject entities = iSynMaterielService.updateOrAddByUids(dtos);
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
        JSONObject entities = iSynMaterielService.synAllByProjectPuid(projectId);
        addToModel(entities, model);
        return "stage/templateOfIntegrate";
    }

    /**
     * 添加进model中
     *
     * @param entities
     * @param model
     */
    private void addToModel(JSONObject entities, Model model) {
        model.addAttribute("msgOfSuccess", "发送成功项");
        model.addAttribute("msgOfFail", "发送失败项");
        model.addAttribute("success", entities.get("success"));
        model.addAttribute("fail", entities.get("fail"));
        model.addAttribute("total", entities.get("total"));
        model.addAttribute("totalOfSuccess", entities.get("totalOfSuccess"));
        model.addAttribute("totalOfFail", entities.get("totalOfFail"));
        model.addAttribute("totalOfOutOfParent", entities.get("totalOfOutOfParent"));
        model.addAttribute("totalOfUnknown", entities.get("totalOfUnknown"));
    }
}
