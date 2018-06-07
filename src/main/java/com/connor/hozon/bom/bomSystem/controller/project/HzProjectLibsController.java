package com.connor.hozon.bom.bomSystem.controller.project;

import com.connor.hozon.bom.bomSystem.bean.HzProjectBean;
import com.connor.hozon.bom.bomSystem.service.project.HzBrandService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.sys.mapper.TreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.project.HzBrandRecord;
import sql.pojo.project.HzPlatformRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.project.HzProjectLibs;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    HzBrandService hzBrandService;
    @Autowired
    HzPlatformService hzPlatformService;

    @Inject
    private TreeMapper treeMapper;

    @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
    @ResponseBody
    public List<HzProjectLibs> loadAll() {
        return hzProjectLibsService.doLoadAllProjectLibs();
    }

    /**
     * 功能描述：直接加载整个菜单树的数据(且必须要有管理员权限才可以加载该菜单树的数据)
     *
     * @return
     */
    @RequestMapping(value = "/loadProjectTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> loadUserTree() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<HzProjectBean> beans = new ArrayList<>();

        List<HzBrandRecord> brands= hzBrandService.doGetAllBrand();
        List<HzPlatformRecord> platforms= hzPlatformService.doGetAllPlatform();
        List<HzProjectLibs> projects= hzProjectLibsService.doLoadAllProjectLibs();
        brands.forEach(brand->{
            HzProjectBean bean = new HzProjectBean();
            bean.setpPuid("#");
            bean.setPuid(brand.getPuid());
            bean.setName(brand.getpBrandName());
            beans.add(bean);
        });
        platforms.forEach(platform->{
            HzProjectBean bean = new HzProjectBean();
            bean.setpPuid(platform.getpPertainToBrandPuid());
            bean.setPuid(platform.getPuid());
            bean.setName(platform.getpPlatformName());
            beans.add(bean);
        });
        projects.forEach(project->{
            HzProjectBean bean = new HzProjectBean();
            bean.setpPuid(project.getpProjectPertainToPlatform());
            bean.setPuid(project.getPuid());
            bean.setName(project.getpProjectName());
            beans.add(bean);
        });

        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG, "加载菜单数据成功！");
        result.put("data", beans);
        return result;
    }

    @RequestMapping(value = "/getProjectDetailById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getProjectDetailById(@RequestParam String puid) {
        Map<String, Object> result = new HashMap<>();
        HzProjectLibs proj = hzProjectLibsService.doLoadProjectLibsById(puid);
        HzPlatformRecord platform = hzPlatformService.doGetByPuid(proj.getpProjectPertainToPlatform());
        HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());
        result.put("proj", proj);
        result.put("platform", platform);
        result.put("brand", brand);
        return result;
    }
}
