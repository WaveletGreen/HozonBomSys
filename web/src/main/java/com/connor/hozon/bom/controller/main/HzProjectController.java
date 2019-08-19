/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.controller.main;

import cn.net.connor.hozon.dao.pojo.depository.project.HzBrandRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzPlatformRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzProjectLibs;
import cn.net.connor.hozon.dao.pojo.depository.project.HzVehicleRecord;
import cn.net.connor.hozon.services.service.depository.project.HzBrandService;
import cn.net.connor.hozon.services.service.depository.project.HzPlatformService;
import cn.net.connor.hozon.services.service.depository.project.HzProjectLibsService;
import cn.net.connor.hozon.services.service.depository.project.HzVehicleService;
import cn.net.connor.hozon.services.service.main.ProjectService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 项目controller，用于前端获取到项目信息，由项目信息驱动下面的额bom数据和配置数据
 * 配置管理controller的所有返回消息字段key都是msg
 * 配置管理controller的所有返回成功标志字段key都是status
 * 如发现不一致需要特殊处理
 * 已完成注释
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Controller
@RequestMapping("/project")
@Slf4j
public class HzProjectController {

    /***品牌服务*/
    @Autowired
    private HzBrandService hzBrandService;
    /***平台服务*/
    @Autowired
    private HzPlatformService hzPlatformService;
    /***车型服务*/
    @Autowired
    private HzVehicleService hzVehicleService;
    /***项目服务*/
    @Autowired
    HzProjectLibsService hzProjectLibsService;
    /**
     * 针对该controller的service
     */
    @Autowired
    private ProjectService projectService;


    /***
     * 添加的操作页，根据四大类别进行分类
     * @param id puid，可以是品牌/平台的puid，直接添加在对应的节点上，添加品牌不用传puid
     * @param page 添加的页面标识，分别传入brand/platform/vehicle/project用于标识品牌/平台/车型/项目，用于跳转到不同的添加页面
     * @param model model 不用传
     * @return 具体的指向页，如果不符合要求，则返回errorWithEntity页
     */
    @RequestMapping(value = "/addPage")
    public String addPage(@RequestParam String id, @RequestParam String page, Model model) {
        if (page == null)
            return "error";
        try {
            switch (page) {
                //跳转添加品牌页面
                case "brand":
                    model.addAttribute("action", "./project/addBrand");
                    return "project/addBrand";
                //跳转添加平台页面
                case "platform":
                    HzBrandRecord brand_p = hzBrandService.doGetByPuid(id);
                    model.addAttribute("brand", brand_p);
                    model.addAttribute("action", "./project/addPlatform");
                    return "project/addPlatform";
                //跳转添加车型页面
                case "vehicle":
                    HzPlatformRecord platform_v = hzPlatformService.doGetByPuid(id);
                    HzBrandRecord brand_v = hzBrandService.doGetByPuid(platform_v.getpPertainToBrandPuid());
                    model.addAttribute("brand", brand_v);
                    model.addAttribute("platform", platform_v);
                    model.addAttribute("action", "./project/addVehicle");
                    return "project/addVehicle";
                //跳转添加项目页面
                case "project":
                    HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(id);
                    HzPlatformRecord platform = hzPlatformService.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
                    HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());
                    model.addAttribute("brand", brand);
                    model.addAttribute("platform", platform);
                    model.addAttribute("vehicle", vehicle);
                    model.addAttribute("action", "./project/addProject");
                    return "project/add";
                //传入错误
                default:
                    model.addAttribute("msg", "发生错误，请联系管理员");
                    return "errorWithEntity";
            }
        } catch (Exception e) {
            log.error("发生错误", e);
            model.addAttribute("msg", "发生错误，请联系管理员");
            return "errorWithEntity";
        }

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
                    model.addAttribute("brand", brand_b);
                    model.addAttribute("action", "./project/modifyBrand");
                    return "project/modifyBrand";
                }
                //加平台
            case "platform":
                HzPlatformRecord platform_p = hzPlatformService.doGetByPuid(id);
                HzBrandRecord brand_p = hzBrandService.doGetByPuid(platform_p.getpPertainToBrandPuid());
                model.addAttribute("brand", brand_p);
                model.addAttribute("platform", platform_p);
                model.addAttribute("action", "./project/modifyPlatform");
                return "project/modifyPlatform";
            case "vehicle":
                HzVehicleRecord vehicle_v = hzVehicleService.doGetByPuid(id);
                HzPlatformRecord platform_v = hzPlatformService.doGetByPuid(vehicle_v.getpVehiclePertainToPlatform());
                HzBrandRecord brand_v = hzBrandService.doGetByPuid(platform_v.getpPertainToBrandPuid());
                model.addAttribute("brand", brand_v);
                model.addAttribute("platform", platform_v);
                model.addAttribute("vehicle", vehicle_v);
                model.addAttribute("action", "./project/modifyVehicle");
                return "project/modifyVehicle";

            //加入项目
            case "project":
                HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(id);
                if (project == null) {
                    model.addAttribute("msg", "找不到项目");
                    return "errorWithEntity";
                }
                HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(project.getpProjectPertainToVehicle());
                HzPlatformRecord platform = hzPlatformService.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
                HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());
                model.addAttribute("brand", brand);
                model.addAttribute("platform", platform);
                model.addAttribute("vehicle", vehicle);
                model.addAttribute("project", project);
                model.addAttribute("action", "./project/modifyProject");
                return "project/modifyProject";
            //传入错误
            default:
                return "error";
        }
    }


    /**
     * 加载所有项目
     *
     * @return 所有项目
     */
    @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadAll() {
        return projectService.loadAll();
    }

    /**
     * 直接加载整个菜单树的数据(且必须要有管理员权限才可以加载该菜单树的数据)
     *
     * @return 项目树形结构数据，需要用zTree进行初始化即可
     */
    @RequestMapping(value = "/loadProjectTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> loadProjectTree() {
        return projectService.loadProjectTree();
    }

    /***
     * 根据项目的puid，获取到项目的详细信息，包含了品牌，平台等项目结构树信息
     * @param puid 项目的puid
     * @return 项目的详细信息
     */
    @RequestMapping(value = "/getProjectDetailById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getProjectDetailById(@RequestParam String puid) {
        return projectService.getProjectDetailById(puid);
    }


    /***
     * 添加品牌
     * @param brand 品牌对象，该对象应该从前端页面收受了一定的用户输入
     * @return 数据信息，添加成功则品牌和标识符一起返回，反之则只返回标识符
     */
    @RequestMapping(value = "/addBrand", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addBrand(@RequestBody HzBrandRecord brand) {
        return projectService.addBrand(brand);
    }

    /**
     * 添加平台
     *
     * @param platform 平台对象，该对象应该从前端页面收受了一定的用户输入
     * @return 只有平台对象成功添加，返回一个平台对象供zTree绘制新的树节点，否则返回失败标识
     */
    @RequestMapping(value = "/addPlatform", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addPlatform(@RequestBody HzPlatformRecord platform) {
        return projectService.addPlatform(platform);
    }

    /**
     * 添加车型
     *
     * @param vehicle 车型对象，该对象应该从前端页面收受了一定的用户输入
     * @return 只有车型对象成功添加，返回一个车型对象供zTree绘制新的树节点，否则返回失败标识
     */
    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addVehicle(@RequestBody HzVehicleRecord vehicle) {
        return projectService.addVehicle(vehicle);
    }

    /**
     * 添加项目，由于继承了预研阶段的结构，因此添加项目的时候将自动多关联数模层、首选项和配置主数据
     *
     * @param project 项目对象，该对象应该从前端页面收受了一定的用户输入
     * @return
     */
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addProject(@RequestBody HzProjectLibs project) {
        return projectService.addProject(project);
    }


    /***
     * 添加品牌
     * @param brand 品牌对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/modifyBrand", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyBrand(@RequestBody HzBrandRecord brand) {
        return projectService.modifyBrand(brand);
    }

    /***
     * 添加品牌
     * @param platform 平台
     * @return 数据信息，添加成功则连同平台一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/modifyPlatform", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyPlatform(@RequestBody HzPlatformRecord platform) {
        return projectService.modifyPlatform(platform);
    }

    /***
     * 添加品牌
     * @param vehicle 车型对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/modifyVehicle", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyVehicle(@RequestBody HzVehicleRecord vehicle) {
        return projectService.modifyVehicle(vehicle);

    }

    /***
     * 添加品牌
     * @param project 品牌对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/modifyProject", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyProject(@RequestBody HzProjectLibs project) {
        return projectService.modifyProject(project);

    }

    /**
     * 根据类型和主键进行删除
     *
     * @param puid 主键
     * @param type 类型，分别是brand，plaatform，vehicle和project
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public boolean delete(@RequestParam String puid, @RequestParam String type) {
        return projectService.delete(puid, type);
    }
    //////////////////////////////////////////////////验证编号重复性/////////////////////////////////////////////////////////

    /**
     * 项目编号查重，查重条件是项目代码{@link HzProjectLibs#pProjectCode}
     * <p>
     * 若已存在的项目更换了项目代码，需要对更换后的代码进行查重，已存在代码则不允许更换代码，反之只进行更新项目代码操作
     *
     * @param project 项目对象
     * @return 返回的对象是JSON对象，用于前端表单验证是否通过
     */
    @RequestMapping(value = "/validateProjectCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validateProjectCodeWithPuid(HzProjectLibs project) {
        return projectService.validateProjectCodeWithPuid(project);
    }

    /**
     * 车型代号查重，查重条件是车型代码{@link HzVehicleRecord#pVehicleCode}
     * <p>
     * 若已存在的车型更换了车型代码，需要对更换后的代码进行查重，已存在代码则不允许更换代码，反之只进行更新车型代码操作
     *
     * @param vehicle 车型对象
     * @return 返回的对象是JSON对象，用于前端表单验证是否通过
     */
    @RequestMapping(value = "/validateVehicleCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validateVehicleCodeWithPuid(HzVehicleRecord vehicle) {
        return projectService.validateVehicleCodeWithPuid(vehicle);
    }

    /**
     * 查重平台编号，查重的条件是平台的代码{@link HzPlatformRecord#pPlatformCode}
     * <p>
     * 当若已存在的平台更换了平台代码，需要对更换后的代码进行查重，已存在代码则不允许更换代码，反之只进行更新平台代码操作
     *
     * @param platform 返回的对象是JSON对象，用于前端表单验证是否通过
     * @return
     */
    @RequestMapping(value = "/validatePlatformCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validatePlatformCodeWithPuid(HzPlatformRecord platform) {
        return projectService.validatePlatformCodeWithPuid(platform);
    }

    /**
     * 查重品牌编号，查重的条件是品牌的代码{@link HzBrandRecord#pBrandCode}
     *
     * @param brand 品牌对象
     * @return 返回的对象是JSON对象，用于前端表单验证是否通过
     */
    @RequestMapping(value = "/validateBrandCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validateBrandCodeWithPuid(HzBrandRecord brand) {
        return projectService.validateBrandCodeWithPuid(brand);
    }
    //////////////////////////////////////////////////验证编号重复性/////////////////////////////////////////////////////////
}
