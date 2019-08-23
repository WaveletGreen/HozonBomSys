/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.bom.sparePart;

import cn.net.connor.hozon.dao.basic.DeleteListDao;
import cn.net.connor.hozon.dao.basic.InsertListDao;
import cn.net.connor.hozon.dao.basic.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 备件零件dao层
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 13:51
 * @Modified By:
 */
@Repository
public interface SparePartDataDao extends MyBatisBaseDao<SparePartData,Long,SparePartOfProjectQuery> ,DeleteListDao<SparePartData>,InsertListDao<SparePartData> {
    /**
     * 分页查询
     * @param query
     * @return
     */
    List<SparePartData> selectPageByQuery(SparePartOfProjectQuery query);
    int countByQuery(SparePartOfProjectQuery query);
}