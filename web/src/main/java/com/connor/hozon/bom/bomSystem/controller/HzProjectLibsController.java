/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.services.service.depository.project.impl.HzBrandServiceImpl;
import cn.net.connor.hozon.services.service.depository.project.impl.HzPlatformServiceImpl;
import cn.net.connor.hozon.dao.dao.main.HzMainBomDao;
import cn.net.connor.hozon.dao.dao.main.HzMainConfigDao;
import com.connor.hozon.bom.bomSystem.dto.HzProjectBean;
import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.bomSystem.helper.ProjectHelper;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import cn.net.connor.hozon.services.service.depository.project.HzVehicleService;
import cn.net.connor.hozon.services.service.depository.project.impl.HzProjectLibsServiceImpl;
import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cn.net.connor.hozon.dao.pojo.main.HzMainBom;
import cn.net.connor.hozon.dao.pojo.depository.project.HzBrandRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzPlatformRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzProjectLibs;
import cn.net.connor.hozon.dao.pojo.depository.project.HzVehicleRecord;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

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
public class HzProjectLibsController {
    /***项目服务*/
    @Autowired
    HzProjectLibsServiceImpl hzProjectLibsServiceImpl;
    /***品牌服务*/
    @Autowired
    HzBrandServiceImpl hzBrandServiceImpl;
    /***平台服务*/
    @Autowired
    HzPlatformServiceImpl hzPlatformServiceImpl;
    /***车型服务*/
    @Autowired
    HzVehicleService hzVehicleService;
    //    /***首选项dao层，目前首选项已经不再使用，只是保留了预研时的结构*/
    //    @Autowired
    //    HzPreferenceSettingDao hzPreferenceSettingDao;
    /*** 主配置dao层*/
    @Autowired
    HzMainConfigDao hzMainConfigDao;
    /*** BOM主数据层*/
    @Autowired
    HzMainBomDao hzMainBomDao;
    /***项目助手*/
    @Autowired
    ProjectHelper projectHelper;
    /***日志*/
    private static final Logger logger = LoggerFactory.getLogger(HzProjectLibsController.class);
    /*** session过时时间，默认为30分钟，不知道是否能生效*/
    private static int TIME_OUT = 30 * 60;

    /**
     * 加载所有项目
     *
     * @param request 由spring自动传入，不用传
     * @return 所有项目
     */
    @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadAll(HttpServletRequest request) {
        System.out.println("Session过期时间" + request.getSession().getMaxInactiveInterval());
        Map<String, Object> result = new HashMap<>();
        User user = UserInfo.getUser();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            //只有管理员才能快速操作，后续可能改成多个组成员能快速操作
            if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                result.put("auth", true);
                break;
            }
        }
        result.put("data", hzProjectLibsServiceImpl.doLoadAllProjectLibs());
        result.put("brand", hzBrandServiceImpl.doGetAllBrand());
        //        PropertiesHelper helper = new PropertiesHelper();
        //        try {
        //            Properties properties = helper.load();
        //            TIME_OUT = Integer.parseInt(properties.getProperty("SESSION_OUT"));
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        //        request.getSession().setMaxInactiveInterval(TIME_OUT);
        return result;
    }

    /**
     * 直接加载整个菜单树的数据(且必须要有管理员权限才可以加载该菜单树的数据)
     *
     * @return 项目树形结构数据，需要用zTree进行初始化即可
     */
    @RequestMapping(value = "/loadProjectTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> loadProjectTree() {
        Map<String, Object> result = new HashMap<>();
        List<HzProjectBean> beans = new ArrayList<>();

        List<HzBrandRecord> brands = hzBrandServiceImpl.doGetAllBrand();
        List<HzPlatformRecord> platforms = hzPlatformServiceImpl.doGetAllPlatform();
        List<HzVehicleRecord> vehicles = hzVehicleService.doGetAllVehicle();
        List<HzProjectLibs> projects = hzProjectLibsServiceImpl.doLoadAllProjectLibs();
        //品牌
        brands.forEach(brand -> {
            HzProjectBean bean = new HzProjectBean();
            bean.setpPuid("#");
            bean.setPuid(brand.getPuid());
            bean.setName(brand.getpBrandName());
            beans.add(bean);
        });
        //平台
        platforms.forEach(platform -> {
            HzProjectBean bean = new HzProjectBean();
            bean.setpPuid(platform.getpPertainToBrandPuid());
            bean.setPuid(platform.getPuid());
            bean.setName(platform.getpPlatformName());
            beans.add(bean);
        });
        //车型
        vehicles.forEach(vehicle -> {
            HzProjectBean bean = new HzProjectBean();
            bean.setpPuid(vehicle.getpVehiclePertainToPlatform());
            bean.setPuid(vehicle.getPuid());
            bean.setName(vehicle.getpVehicleName());
            beans.add(bean);
        });

        //项目
        projects.forEach(project -> {
            HzProjectBean bean = new HzProjectBean();
            bean.setpPuid(project.getpProjectPertainToVehicle());
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
     * 根据项目的puid，获取到项目的详细信息，包含了品牌，平台等项目结构树信息
     * @param puid 项目的puid
     * @return 项目的详细信息
     */
    @RequestMapping(value = "/getProjectDetailById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getProjectDetailById(@RequestParam String puid) {
        Map<String, Object> result = new HashMap<>();
        projectHelper.doGetProjectTreeByProjectId(puid);
        HzProjectLibs project = projectHelper.getProject();
        HzVehicleRecord vehicle = projectHelper.getVehicle();
        HzPlatformRecord platform = projectHelper.getPlatform();
        HzBrandRecord brand = projectHelper.getBrand();
        result.put("project", project);
        result.put("vehicle", vehicle);
        result.put("platform", platform);
        result.put("brand", brand);
        return result;
    }

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
                    HzBrandRecord brand_p = hzBrandServiceImpl.doGetByPuid(id);
                    model.addAttribute("brand", brand_p);
                    model.addAttribute("action", "./project/addPlatform");
                    return "project/addPlatform";
                //跳转添加车型页面
                case "vehicle":
                    HzPlatformRecord platform_v = hzPlatformServiceImpl.doGetByPuid(id);
                    HzBrandRecord brand_v = hzBrandServiceImpl.doGetByPuid(platform_v.getpPertainToBrandPuid());
                    model.addAttribute("brand", brand_v);
                    model.addAttribute("platform", platform_v);
                    model.addAttribute("action", "./project/addVehicle");
                    return "project/addVehicle";
                //跳转添加项目页面
                case "project":
                    HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(id);
                    HzPlatformRecord platform = hzPlatformServiceImpl.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
                    HzBrandRecord brand = hzBrandServiceImpl.doGetByPuid(platform.getpPertainToBrandPuid());
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
            logger.error("发生错误", e);
            model.addAttribute("msg", "发生错误，请联系管理员");
            return "errorWithEntity";
        }

    }

    /***
     * 添加品牌
     * @param brand 品牌对象，该对象应该从前端页面收受了一定的用户输入
     * @return 数据信息，添加成功则品牌和标识符一起返回，反之则只返回标识符
     */
    @RequestMapping(value = "/addBrand", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addBrand(@RequestBody HzBrandRecord brand) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzBrandServiceImpl.validate(brand)) {
            result.put("status", -1);
        }
        if (null == hzBrandServiceImpl.doGetByBrandCode(brand.getpBrandCode())) {
            Date date = new Date();
            brand.setPuid(UUID.randomUUID().toString());
            brand.setpBrandCreateDate(date);
            brand.setpBrandLastModDate(date);
            brand.setpBrandLastModifier(user.getUsername());
            if (hzBrandServiceImpl.doInsertOne(brand)) {
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

    /**
     * 添加平台
     *
     * @param platform 平台对象，该对象应该从前端页面收受了一定的用户输入
     * @return 只有平台对象成功添加，返回一个平台对象供zTree绘制新的树节点，否则返回失败标识
     */
    @RequestMapping(value = "/addPlatform", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addPlatform(@RequestBody HzPlatformRecord platform) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzPlatformServiceImpl.validate(platform)) {
            result.put("status", -1);
        }
        if (null == hzPlatformServiceImpl.doGetByPlatformCode(platform.getpPlatformCode())) {
            Date date = new Date();
            platform.setPuid(UUID.randomUUID().toString());
            platform.setpPlatformCreateDate(date);
            platform.setpPlatformLastModDate(date);
            platform.setpPlatformLastModifier(user.getUsername());
            if (hzPlatformServiceImpl.doInsertOne(platform)) {
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

    /**
     * 添加车型
     *
     * @param vehicle 车型对象，该对象应该从前端页面收受了一定的用户输入
     * @return 只有车型对象成功添加，返回一个车型对象供zTree绘制新的树节点，否则返回失败标识
     */
    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addVehicle(@RequestBody HzVehicleRecord vehicle) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzVehicleService.validate(vehicle) || user == null) {
            result.put("status", -1);
            return result;
        }
        if (null == hzVehicleService.doGetByVehicleCode(vehicle.getpVehicleCode())) {
            Date date = new Date();
            vehicle.setPuid(UUID.randomUUID().toString());
            vehicle.setpVehicleCreateDate(date);
            vehicle.setpVehicleLastModDate(date);
            vehicle.setpVehicleLastModifier(user.getUsername());
            //设置创建者
            vehicle.setpVehicleLastModifier(user.getUsername());
            if (hzVehicleService.doInsertOne(vehicle)) {
                result.put("status", 1);
                result.put("entity", vehicle);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    /**
     * 添加项目，由于继承了预研阶段的结构，因此添加项目的时候将自动多关联数模层、首选项和配置主数据
     *
     * @param project 项目对象，该对象应该从前端页面收受了一定的用户输入
     * @return
     */
    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public JSONObject addProject(@RequestBody HzProjectLibs project) {
        Date now = new Date();
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzProjectLibsServiceImpl.validate(project) || user == null) {
            result.put("status", -1);
        }
        if (null == hzProjectLibsServiceImpl.doGetByProjectCode(project.getpProjectCode())) {

            project.setPuid(UUID.randomUUID().toString());
            project.setpProjectCreateDate(now);
            project.setpProjectLastModDate(now);
            //设置创建者
            project.setpProjectOwningUser(user.getUsername());
            project.setpProjectLastModifier(user.getUsername());
            //设置失效年份为9999年12月31日23时59分59秒
            project.setpProjectDiscontinuationDate(DateStringHelper.forever());
            if (hzProjectLibsServiceImpl.doInsertOne(project)) {

                int status1 = 0;
                int status2 = 0;
                //自动添加数模层和主配置
                {

                    //数模层
                    HzMainBom hzMainBom = new HzMainBom();
                    //主配置
                    HzMainConfig hzMainConfig = new HzMainConfig();

                    //数模层
                    hzMainBom.setPuid(UUIDHelper.generateUpperUid());
                    hzMainBom.setCreateDate(now);
                    hzMainBom.setCreator(user.getUserName());
                    hzMainBom.setDigifaxName("BOM系统自动创建:" + project.getpProjectCode() + "的数模层");
                    //由于不是从TC中来，因此原始的TC UID将不存在，只能街截取首16位
                    hzMainBom.setDigifaxUidFromTC(hzMainBom.getPuid().substring(0, 15));
                    hzMainBom.setLastModifyDate(now);
                    hzMainBom.setProjectId(project.getPuid());
                    hzMainBom.setProjectName(project.getpProjectName() == null ? project.getpProjectCode() : project.getpProjectName());
                    hzMainBom.setUpdater(user.getUserName());

                    //主配置
                    hzMainConfig.setId(UUIDHelper.generateUpperUid());
                    hzMainConfig.setLastModifyDate(now);
                    hzMainConfig.setProjectName(project.getpProjectName() == null ? project.getpProjectCode() : project.getpProjectName());
                    hzMainConfig.setProjectId(project.getPuid());
                    hzMainConfig.setConfigUidFromTC(hzMainConfig.getId().substring(0, 15));
                    hzMainConfig.setItemName("BOM系统自动创建:" + project.getpProjectCode() + "的主配置");
                    hzMainConfig.setCreateDate(now);
                    hzMainConfig.setCreator(user.getUserName());
                    hzMainConfig.setUpdater(user.getUserName());
                    //新的项目的特性都将继承配置字典的数据
                    hzMainConfig.setFeatureSynDicFlag(1);

                    //同步插入数模层和主配置
                    if (hzMainConfigDao.insert(hzMainConfig) > 0) {
                        result.put("dxgMainMsg", "自动创建关联数模层成功");
                        status1 = 1;
                    } else {
                        result.put("dxgMainMsg", "自动创建关联数模层失败，请联系系统管理员");
                        status1 = 0;
                    }
                    if (hzMainBomDao.insert(hzMainBom) > 0) {
                        result.put("cfgMsg", "自动创建主配置数据成功");
                        status2 = 1;
                    } else {
                        result.put("cfgMsg", "自动创建主配置数据失败，请联系系统管理员");
                        status2 = 0;
                    }
//                    //首选项
//                    HzPreferenceSetting setting = new HzPreferenceSetting();
//                    setting.setSettingName("Hz_ExportBomPreferenceRedis");
//
//                    HzPreferenceSetting thisSetting;
//                    List<HzPreferenceSetting> list = hzPreferenceSettingDao.selectSettingByTemplateName(setting);
//                    if (list == null || list.size() <= 0) {
//                        result.put("settingMsg", "自动创建首选项模板失败，请保证HZ_PREFERENCESETTING表包含Hz_ExportBomPreferenceRedis首选项模板");
//                        status1 = 0;
//                    } else {
//                        thisSetting = list.get(0);
//                        thisSetting.setBomMainRecordPuid(hzMainBom.getId());
//                        thisSetting.setId(UUIDHelper.generateUpperUid());
//                        thisSetting.setSettingName("Hz_ExportBomPreferenceRedis_" + project.getpProjectCode());
//                        if (status2 == 1) {
//                            if (hzPreferenceSettingDao.insert(thisSetting) > 0) {
//                                result.put("settingMsg", "自动创建首选项模板成功");
//                                status1 = 1;
//                            } else {
//                                result.put("settingMsg", "自动创建首选项模板失败，请保证HZ_PREFERENCESETTING表包含Hz_ExportBomPreferenceRedis首选项模板");
//                                status1 = 0;
//                            }
//                        } else {
//                            result.put("settingMsg", "自动创建首选项模板失败，请保证HZ_PREFERENCESETTING表包含Hz_ExportBomPreferenceRedis首选项模板");
//                            status1 = 0;
//                        }
//                    }
                }
                result.put("status", 1);
                result.put("status1", status1);
                result.put("status2", status2);
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
                HzBrandRecord brand_b = hzBrandServiceImpl.doGetByPuid(id);
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
                HzPlatformRecord platform_p = hzPlatformServiceImpl.doGetByPuid(id);
                HzBrandRecord brand_p = hzBrandServiceImpl.doGetByPuid(platform_p.getpPertainToBrandPuid());
                model.addAttribute("brand", brand_p);
                model.addAttribute("platform", platform_p);
                model.addAttribute("action", "./project/modifyPlatform");
                return "project/modifyPlatform";
            case "vehicle":
                HzVehicleRecord vehicle_v = hzVehicleService.doGetByPuid(id);
                HzPlatformRecord platform_v = hzPlatformServiceImpl.doGetByPuid(vehicle_v.getpVehiclePertainToPlatform());
                HzBrandRecord brand_v = hzBrandServiceImpl.doGetByPuid(platform_v.getpPertainToBrandPuid());
                model.addAttribute("brand", brand_v);
                model.addAttribute("platform", platform_v);
                model.addAttribute("vehicle", vehicle_v);
                model.addAttribute("action", "./project/modifyVehicle");
                return "project/modifyVehicle";

            //加入项目
            case "project":
                HzProjectLibs project = hzProjectLibsServiceImpl.doLoadProjectLibsById(id);
                if (project == null) {
                    model.addAttribute("msg", "找不到项目");
                    return "errorWithEntity";
                }
                HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(project.getpProjectPertainToVehicle());
                HzPlatformRecord platform = hzPlatformServiceImpl.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
                HzBrandRecord brand = hzBrandServiceImpl.doGetByPuid(platform.getpPertainToBrandPuid());
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

    /***
     * 添加品牌
     * @param brand 品牌对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/modifyBrand", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyBrand(@RequestBody HzBrandRecord brand) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();

        if (!hzBrandServiceImpl.validate(brand)) {
            result.put("status", -1);
        }
        if (null != hzBrandServiceImpl.doGetByPuid(brand.getPuid())) {
            brand.setpBrandLastModifier(user.getUsername());
            brand.setpBrandLastModDate(new Date());
            if (hzBrandServiceImpl.doUpdateSelective(brand)) {
                result.put("status", 1);
                brand = hzBrandServiceImpl.doGetByPuid(brand.getPuid());
                result.put("entity", brand);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    /***
     * 添加品牌
     * @param platform 平台
     * @return 数据信息，添加成功则连同平台一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/modifyPlatform", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyPlatform(@RequestBody HzPlatformRecord platform) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzPlatformServiceImpl.modifyValidate(platform)) {
            result.put("status", -1);
        }
        if (null != hzPlatformServiceImpl.doGetByPuid(platform.getPuid())) {
            platform.setpPlatformLastModDate(new Date());
            platform.setpPlatformLastModifier(user.getUsername());
            if (hzPlatformServiceImpl.doUpdate(platform)) {
                result.put("status", 1);
                platform = hzPlatformServiceImpl.doGetByPuid(platform.getPuid());
                result.put("entity", platform);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    /***
     * 添加品牌
     * @param vehicle 车型对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/modifyVehicle", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyVehicle(@RequestBody HzVehicleRecord vehicle) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzVehicleService.validate(vehicle)) {
            result.put("status", -1);
        }
        if (null != hzVehicleService.doGetByPuid(vehicle.getPuid())) {
            vehicle.setpVehicleLastModDate(new Date());
            vehicle.setpVehicleLastModifier(user.getUsername());
            if (hzVehicleService.doUpdateByPuid(vehicle)) {
                result.put("status", 1);
                vehicle = hzVehicleService.doGetByPuid(vehicle.getPuid());
                result.put("entity", vehicle);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    /***
     * 添加品牌
     * @param project 品牌对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */
    @RequestMapping(value = "/modifyProject", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modifyProject(@RequestBody HzProjectLibs project) {
        JSONObject result = new JSONObject();
        if (!hzProjectLibsServiceImpl.modifyValidate(project)) {
            result.put("status", -1);
        }
        if (null != hzProjectLibsServiceImpl.doLoadProjectLibsById(project.getPuid())) {
            project.setpProjectLastModDate(new Date());
            if (hzProjectLibsServiceImpl.doUpdateByPrimaryKey(project)) {
                result.put("status", 1);
                project = hzProjectLibsServiceImpl.doLoadProjectLibsById(project.getPuid());
                //不能传空值，空值可能来源于数据库
                result.put("entity", project);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
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
        if (!checkString(puid) || !checkString(type)) {
            return false;
        }
        switch (type) {
            case "brand":
                return hzBrandServiceImpl.doDeleteByPuid(puid);
            case "platform":
                return hzPlatformServiceImpl.doDeleteByPuid(puid);
            case "project":
                return hzProjectLibsServiceImpl.doDeleteByPuid(puid);
            case "vehicle":
                return hzVehicleService.doDeleteByPuid(puid);
            default:
                return false;
        }
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
        return hzProjectLibsServiceImpl.doValidateCodeWithPuid(project);
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
        return hzVehicleService.doValidateCodeWithPuid(vehicle);
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
        return hzPlatformServiceImpl.doValidateCodeWithPuid(platform);
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
        return hzBrandServiceImpl.doValidateCodeWithPuid(brand);
    }
    //////////////////////////////////////////////////验证编号重复性/////////////////////////////////////////////////////////
}
