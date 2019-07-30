/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.configuration.fullConfigSheet;

import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelDetail;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/30 14:21
 * @Modified By:
 */
public interface HzCfg0ModelService {
//    @Deprecated
//    boolean isExist(HzCfg0ModelDetail entity);

    /**
     * @param entity
     * @return sql.pojo.cfg0.model.HzCfg0ModelDetail
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据模型的ID，获取到相关的详细模型数据
     * @Date: 2018/5/21 17:06
     */
    HzCfg0ModelDetail getOneByModelId(HzCfg0ModelDetail entity) ;

    /**
     * @param entity
     * @return sql.pojo.cfg0.model.HzCfg0ModelDetail
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据模型的ID，获取到相关的详细模型数据
     * @Date: 2018/5/21 17:06
     */
    HzCfg0ModelDetail getOneByModelId2(HzCfg0ModelDetail entity) ;

    /**
     * 根据主键查找1个车型模型
     *
     * @param puid
     * @return
     */
    HzCfg0ModelRecord getModelByPuid(String puid);

    /**
     * @param detail
     * @return boolean
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行更新1条模型详细信息数据
     * @Date: 2018/5/21 17:06
     */
    boolean doUpdateOne(HzCfg0ModelDetail detail) ;

    /**
     * @param detail 1条模型详细信息数据
     * @return boolean
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行插入1条模型详细信息数据
     * @Date: 2018/5/21 17:05
     */
    boolean doInsert(HzCfg0ModelDetail detail);

    /**
     * 根据项目puid获取到车型集合数据
     *
     * @param projectPuid
     * @return
     */
    List<HzCfg0ModelRecord> doSelectByProjectPuid(String projectPuid) ;
    /**
     * 根据模型ID删除模式数据
     *
     * @param modelId
     * @return
     */
    int deleteModelById(String modelId) ;
}
