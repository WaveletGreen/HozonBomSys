package com.connor.hozon.bom.bomSystem.controller.project;

import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.project.HzProjectLibs;

import java.util.List;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/30 14:27
 *
 * Description: 项目控制器，用于前端获取到项目信息，由项目信息驱动下面的额bom数据和配置数据
 *
 * ***********************************************************************************************************************/

@Controller
@RequestMapping("/project")
public class HzProjectLibsController {
    @Autowired
    HzProjectLibsService hzProjectLibsService;

    @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
    @ResponseBody
    public List<HzProjectLibs> loadAll() {
        return hzProjectLibsService.doLoadAllProjectLibs();
    }
}
