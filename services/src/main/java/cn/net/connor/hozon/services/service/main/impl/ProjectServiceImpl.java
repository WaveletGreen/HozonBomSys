/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.main.impl;

import cn.net.connor.hozon.common.constant.SystemStaticConst;
import cn.net.connor.hozon.common.util.DateStringUtils;
import cn.net.connor.hozon.common.util.UUIDHelper;
import cn.net.connor.hozon.dao.dao.main.HzMainBomDao;
import cn.net.connor.hozon.dao.dao.main.HzMainConfigDao;
import cn.net.connor.hozon.dao.pojo.depository.project.HzBrandRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzPlatformRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzProjectLibs;
import cn.net.connor.hozon.dao.pojo.depository.project.HzVehicleRecord;
import cn.net.connor.hozon.dao.pojo.main.HzMainBom;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.services.response.main.project.HzProjectTreeResponseDTO;
import cn.net.connor.hozon.services.service.depository.project.HzBrandService;
import cn.net.connor.hozon.services.service.depository.project.HzPlatformService;
import cn.net.connor.hozon.services.service.depository.project.HzProjectLibsService;
import cn.net.connor.hozon.services.service.depository.project.HzVehicleService;
import cn.net.connor.hozon.services.service.main.ProjectHelperService;
import cn.net.connor.hozon.services.service.main.ProjectService;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static cn.net.connor.hozon.common.util.StringUtils.checkString;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 11:09
 * @Modified By:
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private HzProjectLibsService hzProjectLibsService;
    @Autowired
    private HzBrandService hzBrandService;
    @Autowired
    private HzPlatformService hzPlatformService;
    @Autowired
    private HzVehicleService hzVehicleService;
    @Autowired
    private ProjectHelperService projectHelperService;
    @Autowired
    HzMainConfigDao hzMainConfigDao;
    /*** BOM主数据层*/
    @Autowired
    HzMainBomDao hzMainBomDao;

    @Override
    public Map<String, Object> loadAll() {
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
        return result;
    }

    @Override
    public Map<String, Object> loadProjectTree() {
        Map<String, Object> result = new HashMap<>();
        List<HzProjectTreeResponseDTO> beans = new ArrayList<>();

        List<HzBrandRecord> brands = hzBrandService.doGetAllBrand();
        List<HzPlatformRecord> platforms = hzPlatformService.doGetAllPlatform();
        List<HzVehicleRecord> vehicles = hzVehicleService.doGetAllVehicle();
        List<HzProjectLibs> projects = hzProjectLibsService.doLoadAllProjectLibs();
        //品牌
        brands.forEach(brand -> {
            HzProjectTreeResponseDTO bean = new HzProjectTreeResponseDTO();
            bean.setpPuid("#");
            bean.setPuid(brand.getPuid());
            bean.setName(brand.getpBrandName());
            beans.add(bean);
        });
        //平台
        platforms.forEach(platform -> {
            HzProjectTreeResponseDTO bean = new HzProjectTreeResponseDTO();
            bean.setpPuid(platform.getpPertainToBrandPuid());
            bean.setPuid(platform.getPuid());
            bean.setName(platform.getpPlatformName());
            beans.add(bean);
        });
        //车型
        vehicles.forEach(vehicle -> {
            HzProjectTreeResponseDTO bean = new HzProjectTreeResponseDTO();
            bean.setpPuid(vehicle.getpVehiclePertainToPlatform());
            bean.setPuid(vehicle.getPuid());
            bean.setName(vehicle.getpVehicleName());
            beans.add(bean);
        });

        //项目
        projects.forEach(project -> {
            HzProjectTreeResponseDTO bean = new HzProjectTreeResponseDTO();
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

    @Override
    public Map<String, Object> getProjectDetailById(String puid) {
        Map<String, Object> result = new HashMap<>();
        projectHelperService.doGetProjectTreeByProjectId(puid);
        HzProjectLibs project = projectHelperService.getProject();
        HzVehicleRecord vehicle = projectHelperService.getVehicle();
        HzPlatformRecord platform = projectHelperService.getPlatform();
        HzBrandRecord brand = projectHelperService.getBrand();
        result.put("project", project);
        result.put("vehicle", vehicle);
        result.put("platform", platform);
        result.put("brand", brand);
        return result;
    }

    @Override
    @Transactional
    public JSONObject addBrand(HzBrandRecord brand) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzBrandService.validate(brand)) {
            result.put("status", -1);
        }
        if (null == hzBrandService.doGetByBrandCode(brand.getpBrandCode())) {
            Date date = new Date();
            brand.setPuid(UUIDHelper.generateUpperUid());
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

    @Override
    public JSONObject addPlatform(HzPlatformRecord platform) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzPlatformService.validate(platform)) {
            result.put("status", -1);
        }
        if (null == hzPlatformService.doGetByPlatformCode(platform.getpPlatformCode())) {
            Date date = new Date();
            platform.setPuid(UUIDHelper.generateUpperUid());
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

    @Override
    public JSONObject addVehicle(HzVehicleRecord vehicle) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzVehicleService.validate(vehicle) || user == null) {
            result.put("status", -1);
            return result;
        }
        if (null == hzVehicleService.doGetByVehicleCode(vehicle.getpVehicleCode())) {
            Date date = new Date();
            vehicle.setPuid(UUIDHelper.generateUpperUid());
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

    @Transactional
    @Override
    public JSONObject addProject(HzProjectLibs project) {
        Date now = new Date();
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzProjectLibsService.validate(project) || user == null) {
            result.put("status", -1);
        }
        if (null == hzProjectLibsService.doGetByProjectCode(project.getpProjectCode())) {

            project.setPuid(UUIDHelper.generateUpperUid());
            project.setpProjectCreateDate(now);
            project.setpProjectLastModDate(now);
            //设置创建者
            project.setpProjectOwningUser(user.getUsername());
            project.setpProjectLastModifier(user.getUsername());
            //设置失效年份为9999年12月31日23时59分59秒
            project.setpProjectDiscontinuationDate(DateStringUtils.forever());
            if (hzProjectLibsService.doInsertOne(project)) {

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
                    hzMainBom.setCreator(user.getUsername());
                    hzMainBom.setDigifaxName("BOM系统自动创建:" + project.getpProjectCode() + "的数模层");
                    //由于不是从TC中来，因此原始的TC UID将不存在，只能街截取首16位
                    hzMainBom.setDigifaxUidFromTC(hzMainBom.getPuid().substring(0, 15));
                    hzMainBom.setLastModifyDate(now);
                    hzMainBom.setProjectId(project.getPuid());
                    hzMainBom.setProjectName(project.getpProjectName() == null ? project.getpProjectCode() : project.getpProjectName());
                    hzMainBom.setUpdater(user.getUsername());

                    //主配置
                    hzMainConfig.setId(UUIDHelper.generateUpperUid());
                    hzMainConfig.setLastModifyDate(now);
                    hzMainConfig.setProjectName(project.getpProjectName() == null ? project.getpProjectCode() : project.getpProjectName());
                    hzMainConfig.setProjectId(project.getPuid());
                    hzMainConfig.setConfigUidFromTC(hzMainConfig.getId().substring(0, 15));
                    hzMainConfig.setItemName("BOM系统自动创建:" + project.getpProjectCode() + "的主配置");
                    hzMainConfig.setCreateDate(now);
                    hzMainConfig.setCreator(user.getUsername());
                    hzMainConfig.setUpdater(user.getUsername());
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

    @Transactional
    @Override
    public JSONObject modifyBrand(HzBrandRecord brand) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();

        if (!hzBrandService.validate(brand)) {
            result.put("status", -1);
        }
        if (null != hzBrandService.doGetByPuid(brand.getPuid())) {
            brand.setpBrandLastModifier(user.getUsername());
            brand.setpBrandLastModDate(new Date());
            if (hzBrandService.doUpdateSelective(brand)) {
                result.put("status", 1);
                brand = hzBrandService.doGetByPuid(brand.getPuid());
                result.put("entity", brand);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    @Transactional
    @Override
    public JSONObject modifyPlatform(HzPlatformRecord platform) {
        JSONObject result = new JSONObject();
        User user = UserInfo.getUser();
        if (!hzPlatformService.modifyValidate(platform)) {
            result.put("status", -1);
        }
        if (null != hzPlatformService.doGetByPuid(platform.getPuid())) {
            platform.setpPlatformLastModDate(new Date());
            platform.setpPlatformLastModifier(user.getUsername());
            if (hzPlatformService.doUpdate(platform)) {
                result.put("status", 1);
                platform = hzPlatformService.doGetByPuid(platform.getPuid());
                result.put("entity", platform);
            } else {
                result.put("status", -1);
            }
        } else {
            result.put("status", 0);
        }
        return result;
    }

    @Transactional
    @Override
    public JSONObject modifyVehicle(HzVehicleRecord vehicle) {
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

    @Transactional
    @Override
    public JSONObject modifyProject(HzProjectLibs project) {
        JSONObject result = new JSONObject();
        if (!hzProjectLibsService.modifyValidate(project)) {
            result.put("status", -1);
        }
        if (null != hzProjectLibsService.doLoadProjectLibsById(project.getPuid())) {
            project.setpProjectLastModDate(new Date());
            if (hzProjectLibsService.doUpdateByPrimaryKey(project)) {
                result.put("status", 1);
                project = hzProjectLibsService.doLoadProjectLibsById(project.getPuid());
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

    @Transactional
    @Override
    public boolean delete(String puid, String type) {
        if (!checkString(puid) || !checkString(type)) {
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

    @Override
    public JSONObject validateProjectCodeWithPuid(HzProjectLibs project) {
        return hzProjectLibsService.doValidateCodeWithPuid(project);
    }

    @Override
    public JSONObject validateVehicleCodeWithPuid(HzVehicleRecord vehicle) {
        return hzVehicleService.doValidateCodeWithPuid(vehicle);
    }

    @Override
    public JSONObject validatePlatformCodeWithPuid(HzPlatformRecord platform) {
        return hzPlatformService.doValidateCodeWithPuid(platform);
    }

    @Override
    public JSONObject validateBrandCodeWithPuid(HzBrandRecord brand) {
        return hzBrandService.doValidateCodeWithPuid(brand);
    }
}
