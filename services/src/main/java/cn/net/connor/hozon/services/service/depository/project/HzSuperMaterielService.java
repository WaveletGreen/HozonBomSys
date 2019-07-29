/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.depository.project;

import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/29 17:11
 * @Modified By:
 */
public interface HzSuperMaterielService {
    /**
     * 主键删除1条超级物料
     * @param puid
     * @return
     */
    boolean doDeleteByPrimaryKey(String puid);

    /**
     * 插入1条超级物料
     * @param record
     * @return
     */
    boolean doInsertOne(HzMaterielRecord record);

    /**
     * 根据主键查询1条超级物料
     * @param puid
     * @return
     */
    HzMaterielRecord doSelectByPrimaryKey(String puid);

    /**
     * 根据项目ID查询1条超级物料数据
     * @param projectPuid
     * @return
     */
    HzMaterielRecord doSelectByProjectPuid(String projectPuid);

    /**
     * 根据主键更新1条超级物料数据
     * @param record
     * @return
     */
    boolean doUpdateByPrimaryKey(HzMaterielRecord record);

}
