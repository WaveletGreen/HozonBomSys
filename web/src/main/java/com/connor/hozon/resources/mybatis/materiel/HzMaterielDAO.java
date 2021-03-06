package com.connor.hozon.resources.mybatis.materiel;

import com.connor.hozon.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;

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
     * 页面数据直接从MBOM中来 不进行插入操作
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
     * 删除数据 逻辑删除
     * @param puids
     * @return
     */
    int deleteList(List<String> puids);

    /**
     * 批量插入 内部直接调用插入 对外增加 走单个插入
     * @param hzMaterielRecords
     * @return
     */
    int insertList(List<HzMaterielRecord> hzMaterielRecords,String tableName);

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
     * 查询物料数据是否存在（根据外键来查询）
     * @param map
     * @return
     */
    boolean HzMaterielIsExist(Map<String, Object> map);

    /**
     * 根据id获取一条物料数据
     * @param query
     * @return
     */
    HzMaterielRecord getHzMaterielRecord(HzMaterielQuery query);

    /**
     * 获取不同类别物料的数量
     * @param projectId
     * @param pMaterielDataType
     * @return
     */
    Integer getHzMaterielCount(String projectId, Integer pMaterielDataType);

    /**
     * 获取整车衍生物料主数据全部
     * @param query
     * @return
     */
    List<HzCfg0ModelRecord> findHzCfg0ModelRecordAll(HzMaterielByPageQuery query);

    /**
     * 批量更新数据
     * @param list
     * @return
     */
    int updateByBatch(List<String> list,String tableName,String field);


    int realDelete(String materielResourceId);

    boolean isRepeat(HzMaterielQuery query);

    List<HzMaterielRecord> getAllMaterielExceptVehicleMateriel(String projectId);

    int deleteMaterielList(List<HzMaterielRecord> list,String tableName);

    int updateList(List<HzMaterielRecord> list);

    int updateMaterielList(List<HzMaterielRecord> list);
    /**
     * 工艺路线初始化掉用
     * @param projectId
     * @return
     */
    List<HzMaterielRecord> findHzMaterielForProcess(String projectId);

    int deleteMaterielByProjectId(String projectId);

    /**
     * 变更 根据puids 获取物料数据
     * @param query
     * @return
     */
    List<HzMaterielRecord> getMaterialRecordsByPuids(HzChangeDataDetailQuery query);

    /**
     * 根据puid 和版本号获取唯一物料
     * @param query
     * @return
     */
    HzMaterielRecord getMaterialRecordByPuidAndRevision(HzChangeDataDetailQuery query);


    List<HzMaterielRecord> getMaterielRecordsByOrderId(HzChangeDataDetailQuery query);
}
