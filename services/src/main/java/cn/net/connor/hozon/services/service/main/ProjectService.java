/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.main;

import cn.net.connor.hozon.dao.pojo.depository.project.HzBrandRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzPlatformRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzProjectLibs;
import cn.net.connor.hozon.dao.pojo.depository.project.HzVehicleRecord;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 11:08
 * @Modified By:
 */
public interface ProjectService {
    /**
     * 加载所有项目信息
     *
     * @return
     */
    Map<String, Object> loadAll();

    /**
     * 加载项目树结构
     *
     * @return
     */
    Map<String, Object> loadProjectTree();

    /**
     * 加载项目详情信息
     *
     * @param puid
     * @return
     */
    Map<String, Object> getProjectDetailById(String puid);

    /**
     * 添加品牌信息
     *
     * @param brand
     * @return
     */
    JSONObject addBrand(HzBrandRecord brand);

    /**
     * 添加平台信息
     *
     * @param platform
     * @return
     */
    JSONObject addPlatform(HzPlatformRecord platform);

    /**
     * 添加车型信息
     *
     * @param vehicle
     * @return
     */
    JSONObject addVehicle(HzVehicleRecord vehicle);

    /**
     * 添加项目，由于继承了预研阶段的结构，因此添加项目的时候将自动多关联数模层、首选项和配置主数据
     *
     * @param project 项目对象，该对象应该从前端页面收受了一定的用户输入
     * @return
     */

    JSONObject addProject(HzProjectLibs project);

    /***
     * 添加品牌
     * @param brand 品牌对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */

    JSONObject modifyBrand(HzBrandRecord brand);

    /***
     * 添加品牌
     * @param platform 平台
     * @return 数据信息，添加成功则连同平台一起返回和标识符，反之则只返回标识符
     */

    JSONObject modifyPlatform(HzPlatformRecord platform);

    /***
     * 添加品牌
     * @param vehicle 车型对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */

    JSONObject modifyVehicle(HzVehicleRecord vehicle);

    /***
     * 添加品牌
     * @param project 品牌对象
     * @return 数据信息，添加成功则连同品牌一起返回和标识符，反之则只返回标识符
     */

    JSONObject modifyProject(HzProjectLibs project);

    /**
     * 根据类型和主键进行删除
     *
     * @param puid 主键
     * @param type 类型，分别是brand，plaatform，vehicle和project
     * @return
     */

    boolean delete(String puid, String type);

    /**
     * 项目编号查重，查重条件是项目代码{@link HzProjectLibs#pProjectCode}
     * <p>
     * 若已存在的项目更换了项目代码，需要对更换后的代码进行查重，已存在代码则不允许更换代码，反之只进行更新项目代码操作
     *
     * @param project 项目对象
     * @return 返回的对象是JSON对象，用于前端表单验证是否通过
     */
    JSONObject validateProjectCodeWithPuid(HzProjectLibs project);

    /**
     * 车型代号查重，查重条件是车型代码{@link HzVehicleRecord#pVehicleCode}
     * <p>
     * 若已存在的车型更换了车型代码，需要对更换后的代码进行查重，已存在代码则不允许更换代码，反之只进行更新车型代码操作
     *
     * @param vehicle 车型对象
     * @return 返回的对象是JSON对象，用于前端表单验证是否通过
     */
    JSONObject validateVehicleCodeWithPuid(HzVehicleRecord vehicle);

    /**
     * 查重平台编号，查重的条件是平台的代码{@link HzPlatformRecord#pPlatformCode}
     * <p>
     * 当若已存在的平台更换了平台代码，需要对更换后的代码进行查重，已存在代码则不允许更换代码，反之只进行更新平台代码操作
     *
     * @param platform 返回的对象是JSON对象，用于前端表单验证是否通过
     * @return
     */
    JSONObject validatePlatformCodeWithPuid(HzPlatformRecord platform);

    /**
     * 查重品牌编号，查重的条件是品牌的代码{@link HzBrandRecord#pBrandCode}
     *
     * @param brand 品牌对象
     * @return 返回的对象是JSON对象，用于前端表单验证是否通过
     */
    JSONObject validateBrandCodeWithPuid(HzBrandRecord brand);

}
