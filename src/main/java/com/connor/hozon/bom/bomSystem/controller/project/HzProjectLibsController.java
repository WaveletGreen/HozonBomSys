package com.connor.hozon.bom.bomSystem.controller.project;

import com.connor.hozon.bom.bomSystem.bean.HzProjectBean;
import com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao;
import com.connor.hozon.bom.bomSystem.service.project.HzBrandService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.sys.entity.Tree;
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
        List<Tree> treeList = new ArrayList<>();
        Tree tree = new Tree();

        tree.setId(1);
//        tree.setCode("#");
        tree.setName("哪吒");
        tree.setpId(0);
//        tree.setTreeOrder(10000);
//        tree.setState("1");

        Tree tree2 = new Tree();

        tree2.setId(2);
//        tree2.setCode("platform");
        tree2.setName("平台");
        tree2.setpId(1);
//        tree2.setTreeOrder(20000);
//        tree2.setState("1");

        Tree tree3 = new Tree();

        tree3.setId(3);
//        tree3.setCode("platform");
        tree3.setName("项目(车型)");
        tree3.setpId(2);
//        tree3.setTreeOrder(30000);
//        tree3.setState("1");

        treeList.add(tree);
        treeList.add(tree2);
        treeList.add(tree3);


        List<HzProjectBean> beans = new ArrayList<>();
        HzProjectBean bean = new HzProjectBean();
        HzProjectBean bean2 = new HzProjectBean();
        HzProjectBean bean3 = new HzProjectBean();
        HzProjectBean bean4 = new HzProjectBean();

        bean.setPuid("dasds");
        bean.setpPuid("#");
        bean.setName("品牌");

        bean2.setPuid("das");
        bean2.setpPuid("dasds");
        bean2.setName("平台");

        bean3.setPuid("giQtAHNv46t5TA");
        bean3.setpPuid("das");
        bean3.setName("EP11");

        bean4.setPuid("QaatAHSl46t5TA");
        bean4.setpPuid("das");
        bean4.setName("EP10项目");

        beans.add(bean);
        beans.add(bean2);
        beans.add(bean3);
        beans.add(bean4);
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG, "加载菜单数据成功！");
//        result.put("data", treeMapper.treesToTressDTOs(treeList));
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
