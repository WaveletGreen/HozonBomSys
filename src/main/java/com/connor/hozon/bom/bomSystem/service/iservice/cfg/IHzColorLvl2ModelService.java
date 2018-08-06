package com.connor.hozon.bom.bomSystem.service.iservice.cfg;

import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzColorLvl2Model;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/3 11:03
 * @Modified By:
 */
@Service
public interface IHzColorLvl2ModelService {
    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    int doDeleteByPrimaryKey(String puid);

    /**
     * 插入
     *
     * @param record 二级配色方案
     * @return
     */
    int doInsert(HzColorLvl2Model record);

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */
    HzColorLvl2Model doSelectByPrimaryKey(String puid);

    /**
     * 主键更新
     *
     * @param record 二级配色方案
     * @return
     */
    int doUpdateByPrimaryKey(HzColorLvl2Model record);
}
