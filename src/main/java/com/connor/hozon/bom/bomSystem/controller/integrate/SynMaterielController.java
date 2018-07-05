package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynMaterielService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @param puids
     * @return
     */
    @RequestMapping("/deleteByPuids")
    public JSONObject deleteByPuids(List<String> puids) {
        return iSynMaterielService.deleteByPuids(puids);
    }

    /**
     * 更新物料主数据
     *
     * @param puids
     * @return
     */
    @RequestMapping("/updateByPuids")
    public JSONObject updateByPuids(List<String> puids) {
        return iSynMaterielService.updateByPuids(puids);
    }

    /**
     * 一开始触发同步所有数据
     *
     * @param projectPuid
     * @return
     */
    @RequestMapping("/synAllByProjectPuid")
    public JSONObject synAllByProjectPuid(@RequestParam("projectPuid") String projectPuid) {
        JSONObject result = new JSONObject();
        if (projectPuid == null || "".equalsIgnoreCase(projectPuid)) {
            result.put("status", false);
            result.put("result", "请选择项目再操作!");
            return result;
        }
        return iSynMaterielService.synAllByProjectPuid(projectPuid);
    }

}
