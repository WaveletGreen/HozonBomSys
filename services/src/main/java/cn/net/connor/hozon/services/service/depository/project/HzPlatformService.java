/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.depository.project;

import cn.net.connor.hozon.dao.pojo.depository.project.HzPlatformRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.IProject;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/29 17:10
 * @Modified By:
 */
public interface HzPlatformService {
    /**
     * 根据主键查询,puid就是主键
     * @param puid
     * @return
     */
    HzPlatformRecord doGetByPuid(String puid);

    /**
     * 更新单条平台数据
     * @param record
     * @return
     */
    boolean doUpdate(HzPlatformRecord record);

    /**
     * 插入1条平台对象
     * @param record
     * @return
     */
    boolean doInsertOne(HzPlatformRecord record);

    /**
     * 根据主键删除平台对象
     * @param puid
     * @return
     */
    boolean doDeleteByPuid(String puid);

    /**
     * 罗列所有的平台对象
     * @return
     */
    List<HzPlatformRecord> doGetAllPlatform();

    /**
     * 验证平台是否符合要求
     * <p>
     * 只验证平台代码和平台名称
     *
     * @param platform 平台对象
     * @return
     */
    boolean validate(HzPlatformRecord platform);

    /**
     * 验证平台是否符合要求
     * <p>
     * 只验证平台代码和平台名称
     *
     * @param platform 平台对象
     * @return
     */
    boolean modifyValidate(HzPlatformRecord platform);

    /**
     * 根据平台代号查找平台数据
     *
     * @param platformCode
     * @return
     */
    HzPlatformRecord doGetByPlatformCode(String platformCode);

    /**
     * 查重平台编号
     *
     * @param platform 平台对象
     * @return
     */
    JSONObject doValidateCodeWithPuid(IProject platform);
}
