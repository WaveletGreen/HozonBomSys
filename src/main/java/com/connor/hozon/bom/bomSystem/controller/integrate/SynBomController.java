package com.connor.hozon.bom.bomSystem.controller.integrate;

import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynBomService;
import com.connor.hozon.bom.resources.dto.request.EditHzMaterielReqDTO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    /**
     * 根据项目同步所有数据
     *
     * @param projectUid
     * @return
     */
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
        JSONObject result = new JSONObject();
        return bomService.deleteByUids(dtos);
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
        JSONObject result = new JSONObject();
        return bomService.updateByUids(dtos);
    }

}
