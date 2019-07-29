/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.depository.color;

import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.dao.pojo.depository.color.HzCfg0ColorSet;
import cn.net.connor.hozon.dao.pojo.depository.color.HzColorSetQuery;

import java.util.*;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/29 17:54
 * @Modified By:
 */
public interface HzColorSetService {
    public Map<String, Object> selectAll(HzColorSetQuery query) ;

    /**
     * 根据分页条件获取颜色
     *
     * @param queryBase
     * @return
     */
    public List<HzCfg0ColorSet> doGetAll(QueryBase queryBase) ;

    /**
     * 获取全部颜色
     *
     * @return
     */
    public List<HzCfg0ColorSet> doGetAll() ;

    /**
     * @param entity 颜色
     * @return boolean
     * Description: 根据puid检查是否有颜色信息
     * Date: 2018/5/21 17:08
     */
    public HzCfg0ColorSet getById(HzCfg0ColorSet entity);
    /**
     * @param entity 颜色对象
     * @return boolean 只更新1个
     * Description: 执行更细颜色信息，只更新1个
     * Date: 2018/5/21 17:08
     */
    public boolean doUpdate(HzCfg0ColorSet entity) ;

    /**
     * @param entity 颜色信息的集合
     * @return boolean 是否删除成功
     * Description: 执行批量删除颜色信息
     * Date: 2018/5/21 17:07
     */
    public boolean doDeleteByList(List<HzCfg0ColorSet> entity) ;

    /**
     * @param entity 颜色对象
     * @return boolean 是否添加成功
     * Description: 添加一个颜色信息
     * Date: 2018/5/21 17:07
     */
    public boolean doAddOne(HzCfg0ColorSet entity);
    /**
     * 根据代码获取1个颜色对象
     *
     * @param entity
     * @return
     */
    public HzCfg0ColorSet doGetByColorCode(HzCfg0ColorSet entity);

    /**
     * 根据主键更新状态
     *
     * @param entity
     * @return
     */
    public boolean doUpdateStatusByPk(HzCfg0ColorSet entity) ;

    /**
     * 批量逻辑删除
     *
     * @param entity
     * @return
     */
    public boolean doLogicDeleteByBatch(List<HzCfg0ColorSet> entity);
}
