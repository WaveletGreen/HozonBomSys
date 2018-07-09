package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynMaterielService;
import com.connor.hozon.bom.resources.dto.request.EditHzMaterielReqDTO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping(value = "/updateByPuids", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateByPuids(@RequestBody List<EditHzMaterielReqDTO> dtos) {
        return iSynMaterielService.updateByPuids(dtos);
    }

    /**
     * 一开始触发同步所有数据
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/synAllByProjectPuid", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject synAllByProjectPuid(@RequestParam("projectId") String projectId) {
        JSONObject result = new JSONObject();
        if (projectId == null || "".equalsIgnoreCase(projectId)) {
            result.put("status", false);
            result.put("result", "请选择项目再操作!");
            return result;
        }
        return iSynMaterielService.synAllByProjectPuid(projectId);
    }

}
