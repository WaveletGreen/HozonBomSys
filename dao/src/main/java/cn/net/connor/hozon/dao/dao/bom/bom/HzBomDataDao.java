/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.bom.bom;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomLineRecord;
import cn.net.connor.hozon.dao.query.configuration.fullConfigSheet.FeatureAnd2YRelationQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 0.0.1版的BOM dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzBomDataDao extends BasicDao<HzBomLineRecord>{
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层puid获取到所有的BomLine
     * Date: 2018/5/23 9:59
     *
     * @param bomLineRecord
     * @return
     */
    List<HzBomLineRecord> selectByProjectId(HzBomLineRecord bomLineRecord);



    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据数模层puid获取到所有的BomLine
     * Date: 2018/5/23 9:59
     *
     * @param query 查询对象，包含了排序规则
     * @return
     */
    List<HzBomLineRecord> select2YByProjectPuid(FeatureAnd2YRelationQuery query);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据部门和2Y层获取到2Y层的下级总成，不包含modelUid
     * Date: 2018/5/23 9:59
     *
     * @param params 包含部门名称和项目UID
     * @return
     */
    List<HzBomLineRecord> selectVehicleAssembly(Map<String, Object> params);
}
