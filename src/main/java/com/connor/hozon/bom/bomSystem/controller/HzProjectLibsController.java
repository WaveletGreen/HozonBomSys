/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.bom.HzPreferenceSettingDao;
import com.connor.hozon.bom.bomSystem.dao.main.HzCfg0MainRecordDao;
import com.connor.hozon.bom.bomSystem.dto.HzProjectBean;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.iservice.project.IHzVehicleService;
import com.connor.hozon.bom.bomSystem.service.project.HzBrandService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.HzPreferenceSetting;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.project.HzBrandRecord;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;
import sql.pojo.project.HzVehicleRecord;

import javax.servlet.http.HttpServletRequest;
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
    /***车型服务*/
    private final IHzVehicleService hzVehicleService;
    /***日志*/
    private final Logger logger;

    @Autowired
    private final HzPreferenceSettingDao hzPreferenceSettingDao;
    @Autowired
    HzCfg0MainRecordDao hzCfg0MainRecordDao;
    @Autowired
    HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    public HzProjectLibsController(HzProjectLibsService hzProjectLibsService, HzBrandService hzBrandService, HzPlatformService hzPlatformService, IHzVehicleService hzVehicleService, HzPreferenceSettingDao hzPreferenceSettingDao) {
        this.hzProjectLibsService = hzProjectLibsService;
        this.hzBrandService = hzBrandService;
        this.hzPlatformService = hzPlatformService;
        this.hzVehicleService = hzVehicleService;
        this.hzPreferenceSettingDao = hzPreferenceSettingDao;
        logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * 加载所有项目
     *
     * @return 所有项目
     */
    @RequestMapping(value = "/loadAll", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loadAll(HttpServletRequest request) {
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
        result.put("data", hzProjectLibsService.doLoadAllProjectLibs());
        result.put("brand", hzBrandService.doGetAllBrand());
        request.getSession().setMaxInactiveInterval(30 * 60);
        return result;
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
        List<HzVehicleRecord> vehicles = hzVehicleService.doGetAllVehicle();
        List<HzProjectLibs> projects = hzProjectLibsService.doLoadAllProjectLibs();
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
     * 根据项目的puid，获取到项目的详细信息，包含了品牌，平台等信息
     * @param puid 项目的puid
     * @return 项目的详细信息
     */
    @RequestMapping(value = "/getProjectDetailById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getProjectDetailById(@RequestParam String puid) {
        Map<String, Object> result = new HashMap<>();
        HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(puid);
        HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(project.getpProjectPertainToVehicle());
        HzPlatformRecord platform = hzPlatformService.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
        HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());
        result.put("project", project);
        result.put("vehicle", vehicle);
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
        try {
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
                //加入车型
                case "vehicle":
                    HzPlatformRecord platform_v = hzPlatformService.doGetByPuid(id);
                    HzBrandRecord brand_v = hzBrandService.doGetByPuid(platform_v.getpPertainToBrandPuid());
                    model.addAttribute("brand", brand_v);
                    model.addAttribute("platform", platform_v);
                    model.addAttribute("action", "./project/addVehicle");
                    return "project/addVehicle";
                //加入项目
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
            logger.error("发生错误", e);
            model.addAttribute("msg", "发生错误，请联系管理员");
            return "errorWithEntity";
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
        User user = UserInfo.getUser();
        if (!hzBrandService.validate(brand)) {
            result.put("status", -1);
        }
        if (null == hzBrandService.doGetByBrandCode(brand.getpBrandCode())) {
            Date date = new Date();
            brand.setPuid(UUID.randomUUID().toString());
            brand.setpBrandCreateDate(date);
            brand.setpBrandLastModDate(date);
            brand.setpBrandLastModifier(user.getUsername());
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
        User user = UserInfo.getUser();
        if (!hzPlatformService.validate(platform)) {
            result.put("status", -1);
        }
        if (null == hzPlatformService.doGetByPlatformCode(platform.getpPlatformCode())) {
            Date date = new Date();
            platform.setPuid(UUID.randomUUID().toString());
            platform.setpPlatformCreateDate(date);
            platform.setpPlatformLastModDate(date);
            platform.setpPlatformLastModifier(user.getUsername());
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

    @RequestMapping(value = "/addProject", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addProject(@RequestBody HzProjectLibs project) {
        Date now = new Date();
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzProjectLibsService.validate(project) || user == null) {
            result.put("status", -1);
        }
        if (null == hzProjectLibsService.doGetByProjectCode(project.getpProjectCode())) {

            project.setPuid(UUID.randomUUID().toString());
            project.setpProjectCreateDate(now);
            project.setpProjectLastModDate(now);
            //设置创建者
            project.setpProjectOwningUser(user.getUsername());
            project.setpProjectLastModifier(user.getUsername());
            //设置失效年份为9999年12月31日23时59分59秒
            Calendar calendar = Calendar.getInstance();
            calendar.set(9999, 11, 31, 23, 59, 59);
            project.setpProjectDiscontinuationDate(calendar.getTime());
            if (hzProjectLibsService.doInsertOne(project)) {

                int status1 = 0;
                int status2 = 0;
                int status3 = 0;
                //自动添加数模层和主配置
                {

                    //数模层
                    HZBomMainRecord hzBomMainRecord = new HZBomMainRecord();
                    //主配置
                    HzCfg0MainRecord hzCfg0MainRecord = new HzCfg0MainRecord();

                    //数模层
                    hzBomMainRecord.setPuid(UUIDHelper.generateUpperUid());
                    hzBomMainRecord.setPostDate(now);
                    hzBomMainRecord.setPoster(user.getUserName());
                    hzBomMainRecord.setBomDigifax(project.getpProjectCode() + "的数模层");
                    hzBomMainRecord.setBomOrgPuid(hzBomMainRecord.getPuid().substring(0, 15));
                    hzBomMainRecord.setpCfg0LastModDate(now);
                    hzBomMainRecord.setpCfg0OfWhichProjectPuid(project.getPuid());
                    hzBomMainRecord.setpCfg0OfWhichProject(project.getpProjectName() == null ? project.getpProjectCode() : project.getpProjectName());
                    hzBomMainRecord.setpCfg0OrgPoster(user.getUserName());

                    //主配置
                    hzCfg0MainRecord.setPuid(UUIDHelper.generateUpperUid());
                    hzCfg0MainRecord.setpCfg0LastModDate(now);
                    hzCfg0MainRecord.setpCfg0OfWhichProject(project.getpProjectName() == null ? project.getpProjectCode() : project.getpProjectName());
                    hzCfg0MainRecord.setpCfg0OfWhichProjectPuid(project.getPuid());
                    hzCfg0MainRecord.setpCfg0OrgPuid(hzCfg0MainRecord.getPuid().substring(0, 15));
                    hzCfg0MainRecord.setpItemName(project.getpProjectCode() + "的主配置");
                    hzCfg0MainRecord.setPostDate(now);
                    hzCfg0MainRecord.setPoster(user.getUserName());
                    hzCfg0MainRecord.setpCfg0OrgPoster(user.getUserName());
                    //新的项目的特性都将继承配置字典的数据
                    hzCfg0MainRecord.setFeatureSynDicFlag(1);

                    //同步插入数模层和主配置
                    if (hzCfg0MainRecordDao.insert(hzCfg0MainRecord) > 0) {
                        result.put("dxgMainMsg", "自动创建关联数模层成功");
                        status2 = 1;
                    } else {
                        result.put("dxgMainMsg", "自动创建关联数模层失败，请联系系统管理员");
                        status2 = 0;
                    }
                    if (hzBomMainRecordDao.insert(hzBomMainRecord) > 0) {
                        result.put("cfgMsg", "自动创建主配置数据成功");
                        status3 = 1;
                    } else {
                        result.put("cfgMsg", "自动创建主配置数据失败，请联系系统管理员");
                        status3 = 0;
                    }
                    //首选项
                    HzPreferenceSetting setting = new HzPreferenceSetting();
                    setting.setSettingName("Hz_ExportBomPreferenceRedis");

                    HzPreferenceSetting thisSetting;
                    List<HzPreferenceSetting> list = hzPreferenceSettingDao.selectSettingByTemplateName(setting);
                    if (list == null || list.size() <= 0) {
                        result.put("settingMsg", "自动创建首选项模板失败，请保证HZ_PREFERENCESETTING表包含Hz_ExportBomPreferenceRedis首选项模板");
                        status1 = 0;
                    } else {
                        thisSetting = list.get(0);
                        thisSetting.setBomMainRecordPuid(hzBomMainRecord.getPuid());
                        thisSetting.setPuid(UUIDHelper.generateUpperUid());
                        thisSetting.setSettingName("Hz_ExportBomPreferenceRedis_" + project.getpProjectCode());
                        if (status2 == 1) {
                            if (hzPreferenceSettingDao.insert(thisSetting) > 0) {
                                result.put("settingMsg", "自动创建首选项模板成功");
                                status1 = 1;
                            } else {
                                result.put("settingMsg", "自动创建首选项模板失败，请保证HZ_PREFERENCESETTING表包含Hz_ExportBomPreferenceRedis首选项模板");
                                status1 = 0;
                            }
                        } else {
                            result.put("settingMsg", "自动创建首选项模板失败，请保证HZ_PREFERENCESETTING表包含Hz_ExportBomPreferenceRedis首选项模板");
                            status1 = 0;
                        }

                    }
                }
                result.put("status", 1);
                result.put("status1", status1);
                result.put("status2", status2);
                result.put("status3", status3);
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

        if (!hzBrandService.validate(brand)) {
            result.put("status", -1);
        }
        if (null != hzBrandService.doGetByPuid(brand.getPuid())) {
//            if (null != hzBrandService.doGetByBrandCode(brand.getpBrandCode())) {
//                result.put("status", -1);
//            } else {
            brand.setpBrandLastModifier(user.getUsername());
            brand.setpBrandLastModDate(new Date());
            if (hzBrandService.doUpdateSelective(brand)) {
                result.put("status", 1);
                brand = hzBrandService.doGetByPuid(brand.getPuid());
//                brand.setpBrandCreateDate(new Date());
                result.put("entity", brand);
            } else {
                result.put("status", -1);
            }
//            }
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
        if (!hzPlatformService.modifyValidate(platform)) {
            result.put("status", -1);
        }
        if (null != hzPlatformService.doGetByPuid(platform.getPuid())) {
//            if (null != hzPlatformService.doGetByPlatformCode(platform.getpPlatformCode())) {
//                result.put("status", -1);
//            } else {
            platform.setpPlatformLastModDate(new Date());
            platform.setpPlatformLastModifier(user.getUsername());
            if (hzPlatformService.doUpdate(platform)) {
                result.put("status", 1);
//                platform.setpPlatformCreateDate(new Date());
//                platform.setpPertainToBrandPuid("");
                platform = hzPlatformService.doGetByPuid(platform.getPuid());
                result.put("entity", platform);
            } else {
                result.put("status", -1);
            }
//            }
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
//            if (null != hzProjectLibsService.doGetByProjectCode(project.getpProjectCode())) {
//                result.put("status", -1);
//            } else {
            vehicle.setpVehicleLastModDate(new Date());
            vehicle.setpVehicleLastModifier(user.getUsername());
            if (hzVehicleService.doUpdateByPuid(vehicle)) {
                result.put("status", 1);
                vehicle = hzVehicleService.doGetByPuid(vehicle.getPuid());
                //不能传空值，空值可能来源于数据库
//                hzProjectLibsService.toDTO(project);
                result.put("entity", vehicle);
            } else {
                result.put("status", -1);
            }
//            }
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
        if (!hzProjectLibsService.modifyValidate(project)) {
            result.put("status", -1);
        }
        if (null != hzProjectLibsService.doLoadProjectLibsById(project.getPuid())) {
//            if (null != hzProjectLibsService.doGetByProjectCode(project.getpProjectCode())) {
//                result.put("status", -1);
//            } else {
            project.setpProjectLastModDate(new Date());
            if (hzProjectLibsService.doUpdateByPrimaryKey(project)) {
                result.put("status", 1);
                project = hzProjectLibsService.doLoadProjectLibsById(project.getPuid());
                //不能传空值，空值可能来源于数据库
//                hzProjectLibsService.toDTO(project);
                result.put("entity", project);
            } else {
                result.put("status", -1);
            }
//            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    /**
     * 根据类型和主键进行删除
     *
     * @param puid 主键
     * @param type 类型，分别是brand，plaatform和project
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public boolean delete(@RequestParam String puid, @RequestParam String type) {
        if (null == puid || type == null || "".equals(puid) || "".equals(type)) {
            return false;
        }
        switch (type) {
            case "brand":
                return hzBrandService.doDeleteByPuid(puid);
            case "platform":
                return hzPlatformService.doDeleteByPuid(puid);
            case "project":
                return hzProjectLibsService.doDeleteByPuid(puid);
            case "vehicle":
                return hzVehicleService.doDeleteByPuid(puid);
            default:
                return false;
        }
    }
    //////////////////////////////////////////////////验证编号重复性/////////////////////////////////////////////////////////

    /**
     * 项目编号查重
     *
     * @param project 项目对象
     * @return
     */
    @RequestMapping(value = "/validateProjectCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validateProjectCodeWithPuid(HzProjectLibs project) {
        return hzProjectLibsService.doValidateCodeWithPuid(project);
    }

    /**
     * 车型代号查重
     *
     * @param vehicle 车型对象
     * @return
     */
    @RequestMapping(value = "/validateVehicleCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validateVehicleCodeWithPuid(HzVehicleRecord vehicle) {
        return hzVehicleService.doValidateCodeWithPuid(vehicle);
    }

    /**
     * 查重平台编号
     *
     * @param platform 平台对象
     * @return
     */
    @RequestMapping(value = "/validatePlatformCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validatePlatformCodeWithPuid(HzPlatformRecord platform) {
        return hzPlatformService.doValidateCodeWithPuid(platform);
    }

    /**
     * 查重品牌编号
     *
     * @param brand 品牌对象
     * @return
     */
    @RequestMapping(value = "/validateBrandCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validateBrandCodeWithPuid(HzBrandRecord brand) {
        return hzBrandService.doValidateCodeWithPuid(brand);
    }
    //////////////////////////////////////////////////验证编号重复性/////////////////////////////////////////////////////////


}
