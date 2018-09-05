package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzColorLvl2Model;

import java.util.List;

@Configuration
public interface HzColorLvl2ModelDao {
    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    int deleteByPrimaryKey(String puid);

    /**
     * 插入
     *
     * @param record 二级配色方案
     * @return
     */
    int insert(HzColorLvl2Model record);

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */
    HzColorLvl2Model selectByPrimaryKey(String puid);

    /**
     * 模型主键筛选
     *
     * @param puid 模型主键
     * @return
     */
    List<HzColorLvl2Model> selectByModelUid(String puid);

    /**
     * 主键更新
     *
     * @param record 二级配色方案
     * @return
     */
    int updateByPrimaryKey(HzColorLvl2Model record);

    /**
     * 根据模型和功能层查找是否已存在二级配色方案
     *
     * @param model
     * @return
     */
    HzColorLvl2Model selectByModelAndFunctionLvl(HzColorLvl2Model model);
}