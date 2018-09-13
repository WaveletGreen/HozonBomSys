package com.connor.hozon.bom.resources.mybatis.materiel;

import com.connor.hozon.bom.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.project.HzMaterielRecord;

import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/7/2
 * @Description:
 */
public interface HzMaterielDAO {

    /**
     * 插入一条数据
     *
     * @param hzMaterielRecord
     * @return
     */
    int insert(HzMaterielRecord hzMaterielRecord);

    /**
     * 更改一条数据
     *
     * @param hzMaterielRecord
     * @return
     */
    int update(HzMaterielRecord hzMaterielRecord);

    /**
     * 删除一条数据
     *
     * @param puid
     * @return
     */
    int delete(String puid);

    /**
     * 批量插入 内部直接调用插入 对外增加 走单个插入
     *
     * @param hzMaterielRecords
     * @return
     */
    int insertList(List<HzMaterielRecord> hzMaterielRecords);

    /**
     * 分页获取物料数据
     *
     * @param query
     * @return
     */
    Page<HzMaterielRecord> findHzMaterielForPage(HzMaterielByPageQuery query);

    /**
     * 获取物料数据 list
     *
     * @param query
     * @return
     */
    List<HzMaterielRecord> findHzMaterielForList(HzMaterielQuery query);

    /**
     * 获取整车衍生物料主数据
     *
     * @param query
     * @return
     */
    List<HzCfg0ModelRecord> findHzCfg0ModelRecord(HzMaterielByPageQuery query);

    /**
     * 查询物料数据是否存在（根据外键来查询）
     *
     * @param map
     * @return
     */
    boolean HzMaterielIsExist(Map<String, Object> map);

    /**
     * 根据id获取一条物料数据
     *
     * @param query
     * @return
     */
    HzMaterielRecord getHzMaterielRecord(HzMaterielQuery query);

    /**
     * 获取不同类别物料的数量
     *
     * @param projectId
     * @param pMaterielDataType
     * @return
     */
    Integer getHzMaterielCount(String projectId, Integer pMaterielDataType);

    /**
     * 获取整车衍生物料主数据全部
     *
     * @param query
     * @return
     */
    List<HzCfg0ModelRecord> findHzCfg0ModelRecordAll(HzMaterielByPageQuery query);

    /**
     * 批量更新数据
     *
     * @param list
     * @return
     */
    int updateByBatch(List<String> list,String tableName,String field);


    int realDelete(String materielResourceId);

    boolean isRepeat(HzMaterielQuery query);
}
