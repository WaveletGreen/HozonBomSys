/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.bom.sparePart;

import cn.net.connor.hozon.dao.basic.InsertListDao;
import cn.net.connor.hozon.dao.basic.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartBomStructure;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartStructureRecursion;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 备件BOM结构dao层
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 13:51
 * @Modified By:
 */
@Repository
public interface SparePartBomStructureDao extends MyBatisBaseDao<SparePartBomStructure, Long, SparePartBomStructure>, InsertListDao<SparePartBomStructure> {
    /**
     * 递归查询出某一层下面的所有子层，但是不带出自己
     * @param id
     * @return
     */
    List<SparePartStructureRecursion> selectRecursionByTopLayerId(Long id);
}