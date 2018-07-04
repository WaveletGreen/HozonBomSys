package com.connor.hozon.bom.resources.mybatis.materiel;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;
import sql.pojo.project.HzMaterielRecord;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/7/2
 * @Description:
 */
public interface HzMaterielDAO {

    /**
     * 插入一条数据
     * @param hzMaterielRecord
     * @return
     */
    int insert(HzMaterielRecord hzMaterielRecord);

    /**
     * 更改一条数据
     * @param hzMaterielRecord
     * @return
     */
    int update(HzMaterielRecord hzMaterielRecord);

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    int delete(String puid);

    /**
     * 批量插入
     * @param hzMaterielRecord
     * @return
     */
    int insertList(List<HzMaterielRecord> hzMaterielRecord);

    /**
     * 分页获取物料数据
     * @param query
     * @return
     */
    Page<HzMaterielRecord> findHzMaterielForPage(HzMaterielByPageQuery query);

    /**
     * 获取物料数据 list
     * @param query
     * @return
     */
    List<HzMaterielRecord> findHzMaterielForList(HzMaterielQuery query);
}
