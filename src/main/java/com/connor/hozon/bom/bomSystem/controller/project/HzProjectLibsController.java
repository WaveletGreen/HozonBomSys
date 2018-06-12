package com.connor.hozon.bom.bomSystem.controller.project;

import com.connor.hozon.bom.bomSystem.bean.HzProjectBean;
import com.connor.hozon.bom.bomSystem.service.project.HzBrandService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.project.HzBrandRecord;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;

import java.util.*;

/**
 * <strong>Author: Fancyears·Maylos·Mayways</strong>
 * <p>
 * Date: 2018/5/30 14:27</p>
 * <p>
 * Description: 项目控制器，用于前端获取到项目信息，由项目信息驱动下面的额bom数据和配置数据</p>
 */

@Controller
@RequestMapping("/project")
public class HzProjectLibsController {
    /***项目服务*/
    private final HzProjectLibsService hzProjectLibsService;
    /***品牌服务*/
    private final HzBrandService hzBrandService;
    /***平台服务*/
    private final HzPlatformService hzPlatformService;

    @Autowired
    public HzProjectLibsController(HzProjectLibsService hzProjectLibsService, HzBrandService hzBrandService, HzPlatformService hzPlatformService) {
        this.hzProjectLibsService = hzProjectLibsService;
        this.hzBrandService = hzBrandService;
        this.hzPlatformService = hzPlatformService;
    }

    /**
     * 加载所有项目
     *
     * @return 所有项目
     */
    @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
    @ResponseBody
    public List<HzProjectLibs> loadAll() {
        return hzProjectLibsService.doLoadAllProjectLibs();
    }

    /**
     * 功能描述：直接加载整个菜单树的数据(且必须要有管理员权限才可以加载该菜单树的数据)
     *
     * @return 树形结构
     */
    @RequestMapping(value = "/loadProjectTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> loadUserTree() {
        Map<String, Object> result = new HashMap<>();
        List<HzProjectBean> beans = new ArrayList<>();

        List<HzBrandRecord> brands = hzBrandService.doGetAllBrand();
        List<HzPlatformRecord> platforms = hzPlatformService.doGetAllPlatform();
        List<HzProjectLibs> projects = hzProjectLibsService.doLoadAllProjectLibs();
        brands.forEach(brand -> {
            HzProjectBean bean = new HzProjectBean();
            bean.setpPuid("#");
            bean.setPuid(brand.getPuid());
            bean.setName(brand.getpBrandName());
            beans.add(bean);
        });
        platforms.forEach(platform -> {
            HzProjectBean bean = new HzProjectBean();
            bean.setpPuid(platform.getpPertainToBrandPuid());
            bean.setPuid(platform.getPuid());
            bean.setName(platform.getpPlatformName());
            beans.add(bean);
        });
        projects.forEach(project -> {
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

    /***
     * 根据项目的puid，获取到项目的详细信息，包含了品牌，平台等信息
     * @param puid 项目的puid
     * @return 项目的详细信息
     */
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

    /***
     * 添加的操作页，根据3大类别进行分类
     * @param id puid，可以是品牌/平台的puid，直接添加在对应的节点上，添加品牌不用传puid
     * @param page 添加的页面标识
     * @param model model
     * @return 具体的指向页，如果不符合要求，则返回error页
     */
    @RequestMapping(value = "/addPage")
    public String addPage(@RequestParam String id, @RequestParam String page, Model model) {
        if (page == null)
            return "error";
        switch (page) {
            //加入品牌
            case "brand":
                model.addAttribute("action", "./project/addBrand");
                return "project/addBrand";
            //加平台
            case "platform":
                HzBrandRecord brand_p = hzBrandService.doGetByPuid(id);
                model.addAttribute("brand", brand_p);
                model.addAttribute("action", "./project/addPlatform");
                return "project/addPlatform";
            //加入项目
            case "project":
                HzPlatformRecord platform = hzPlatformService.doGetByPuid(id);
                HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());
                model.addAttribute("brand", brand);
                model.addAttribute("platform", platform);
                model.addAttribute("action", "./project/addProject");
                return "project/add";
            //传入错误
            default:
                return "error";
        }
    }

    /***
     * 添加品牌
     * @param brand 品牌对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/addBrand", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addBrand(@RequestBody HzBrandRecord brand) {
        JSONObject result = new JSONObject();
        if (!hzBrandService.validate(brand)) {
            result.put("status", -1);
        }
        if (null == hzBrandService.doGetByBrandCode(brand.getpBrandCode())) {
            Date date = new Date();
            brand.setPuid(UUID.randomUUID().toString());
            brand.setpBrandCreateDate(date);
            brand.setpBrandLastModDate(date);
            if (hzBrandService.doInsertOne(brand)) {
                result.put("status", 1);
                result.put("entity", brand);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    @RequestMapping(value = "/addPlatform", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addPlatform(@RequestBody HzPlatformRecord platform) {
        JSONObject result = new JSONObject();
        if (!hzPlatformService.validate(platform)) {
            result.put("status", -1);
        }
        if (null == hzPlatformService.doGetByPlatformCode(platform.getpPlatformCode())) {
            Date date = new Date();
            platform.setPuid(UUID.randomUUID().toString());
            platform.setpPlatformCreateDate(date);
            platform.setpPlatformLastModDate(date);
            if (hzPlatformService.doInsertOne(platform)) {
                result.put("status", 1);
                result.put("entity", platform);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addProject(@RequestBody HzProjectLibs project) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzProjectLibsService.validate(project) || user == null) {
            result.put("status", -1);
        }
        if (null == hzProjectLibsService.doGetByProjectCode(project.getpProjectCode())) {
            Date date = new Date();
            project.setPuid(UUID.randomUUID().toString());
            project.setpProjectCreateDate(date);
            project.setpProjectLastModDate(date);
            //设置创建者
            project.setpProjectOwningUser(user.getUsername());
            //设置失效年份为9999年12月31日23时59分59秒
            Calendar calendar = Calendar.getInstance();
            calendar.set(9998, 12, 31, 23, 59, 59);
            project.setpProjectDiscontinuationDate(calendar.getTime());
            if (hzProjectLibsService.doInsertOne(project)) {
                result.put("status", 1);
                result.put("entity", project);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    /***
     * 添加的操作页，根据3大类别进行分类
     * @param id puid，可以是品牌/平台的puid，直接添加在对应的节点上，添加品牌不用传puid
     * @param page 添加的页面标识
     * @param model model
     * @return 具体的指向页，如果不符合要求，则返回error页
     */
    @RequestMapping(value = "/modifyPage")
    public String modifyPage(@RequestParam String id, @RequestParam String page, Model model) {
        if (page == null || id == null || "".equals(id))
            return "error";
        switch (page) {
            //加入品牌
            case "brand":
                HzBrandRecord brand_b = hzBrandService.doGetByPuid(id);
                if (brand_b == null) {
                    model.addAttribute("msg", "找不到品牌数据，请联系管理员");
                    return "errorWithEntity";
                } else {
                    model.addAttribute("entity", brand_b);
                    model.addAttribute("action", "./project/modifyBrand");
                    return "project/modifyBrand";
                }
                //加平台
            case "platform":
                HzBrandRecord brand_p = hzBrandService.doGetByPuid(id);
                model.addAttribute("brand", brand_p);
                model.addAttribute("action", "./project/addPlatform");
                return "project/addPlatform";
            //加入项目
            case "project":
                HzPlatformRecord platform = hzPlatformService.doGetByPuid(id);
                HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());
                model.addAttribute("brand", brand);
                model.addAttribute("platform", platform);
                model.addAttribute("action", "./project/addProject");
                return "project/add";
            //传入错误
            default:
                return "error";
        }
    }

    /***
     * 添加品牌
     * @param brand 品牌对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/addBrand", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyBrand(@RequestBody HzBrandRecord brand) {
        JSONObject result = new JSONObject();
        if (!hzBrandService.validate(brand)) {
            result.put("status", -1);
        }
        if (null == hzBrandService.doGetByBrandCode(brand.getpBrandCode())) {
            brand.setpBrandLastModDate(new Date());
            if (hzBrandService.doUpdateSelective(brand)) {
                result.put("status", 1);
                result.put("entity", brand);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

}
