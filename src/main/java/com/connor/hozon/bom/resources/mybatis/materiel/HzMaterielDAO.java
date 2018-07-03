package com.connor.hozon.bom.resources.mybatis.materiel;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMaterialByPageQuery;
import sql.pojo.project.HzMaterielRecord;

/**
 * @Author: haozt
 * @Date: 2018/7/2
 * @Description:
 */
public interface HzMaterielDAO {

    int insert(HzMaterielRecord hzMaterielRecord);

    int update(HzMaterielRecord hzMaterielRecord);

    int delete(String puid);

    Page<HzMaterielRecord> findHzMaterielForPage(HzMaterialByPageQuery query);
}
