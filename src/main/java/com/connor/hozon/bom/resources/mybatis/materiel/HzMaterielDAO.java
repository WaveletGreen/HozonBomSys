package com.connor.hozon.bom.resources.mybatis.materiel;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;
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
     * 批量插入 内部直接调用插入 对外增加 走单个插入
     * @param hzMaterielRecords
     * @return
     */
    int insertList(List<HzMaterielRecord> hzMaterielRecords);

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

    /**
     * 获取整车衍生物料主数据
     * @param query
     * @return
     */
    List<HzCfg0ModelRecord> findHzCfg0ModelRecord(HzMaterielByPageQuery query);

    /**
     * 获取虚拟物料主数据
     * @param query
     * @return
     */
    List<HzMbomLineRecord> findHz2YMbomRecord(HzMaterielByPageQuery query);

    /**
     * 获取半成品物料主数据  零件来源为自制单件
     * @param query
     * @return
     */
    List<HzMbomLineRecord> findHzResourceMakeSingleMbomRecord(HzMaterielByPageQuery query);

    /**
     * 获取生产性外购物料主数据  零件来源为采购件
     * @param query
     * @return
     */
    List<HzMbomLineRecord> findHzResourceBuyMbomRecord(HzMaterielByPageQuery query);

    /**
     * 获取自制备件物料主数据
     * @param query
     * @return
     */
    List<HzMbomLineRecord> findHzMadeBySelfSpareMbomRecord(HzMaterielByPageQuery query);

    /**
     * 查询物料数据是否存在（根据外键来查询）
     * @param map
     * @return
     */
    boolean HzMaterielIsExist(Map<String,Object> map);

    /**
     * 根据id获取一条物料数据
     * @param query
     * @return
     */
    HzMaterielRecord getHzMaterielRecord(HzMaterielQuery query);

    Integer getHzMaterielCount(String projectId,Integer pMaterielDataType );
}
