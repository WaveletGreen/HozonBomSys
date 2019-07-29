/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.depository.project;

import cn.net.connor.hozon.dao.pojo.depository.project.HzBrandRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.IProject;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/29 17:10
 * @Modified By:
 */
public interface HzBrandService {
    /**
     * 根据主键筛选1条品牌数据
     *
     * @param puid
     * @return
     */
    HzBrandRecord doGetByPuid(String puid);

    /**
     * 更新所有数据
     *
     * @param record
     * @return
     */
    boolean doUpdate(HzBrandRecord record);

    /**
     * 选择性更新，为空的不更新，不为空的才更新
     *
     * @param record
     * @return
     */
    boolean doUpdateSelective(HzBrandRecord record);

    /**
     * 插入1条品牌信息
     *
     * @param record
     * @return
     */
    boolean doInsertOne(HzBrandRecord record);

    /**
     * 根据主键删除品牌信息
     *
     * @param puid
     * @return
     */
    boolean doDeleteByPuid(String puid);

    /**
     * 加载所有品牌信息
     *
     * @return
     */
    List<HzBrandRecord> doGetAllBrand();

    /**
     * 验证品牌是否符合要求
     * 只验证品牌代码和品牌名称
     *
     * @param brand 品牌对象
     * @return
     */
    boolean validate(HzBrandRecord brand);

    /***
     * 根据品牌代码查询品牌
     * @param brandCode 品牌代码
     * @return
     */
    HzBrandRecord doGetByBrandCode(String brandCode);

    /**
     * 查重品牌编号
     *
     * @param brand 品牌对象
     * @return
     */
    JSONObject doValidateCodeWithPuid(IProject brand);
}
