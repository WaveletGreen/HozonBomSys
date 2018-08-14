package com.connor.hozon.bom.bomSystem.dao.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomLineRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;

import java.util.List;
import java.util.Map;


@Service("HzBomLineRecordDaoImpl")
public class HzBomLineRecordDaoImpl extends BaseSQLUtil {

    /**
     * 插入一条数据
     * @author haozt
     * @param record
     * @return
     */
    public int insert(HzBomLineRecord record){
        if(null == record.getTableName() || "".equals(record.getTableName())){
            record.setTableName("HZ_BOM_LINE_RECORD");
        }
        return super.insert("HzBomLineRecordDaoImpl_insert",record);
    }

    /**
     * 更新一条数据
     * @author haozt
     * @param record
     * @return
     */
    public int update(HzBomLineRecord record){
        if(null == record.getTableName() || "".equals(record.getTableName())){
            record.setTableName("HZ_BOM_LINE_RECORD");
        }
        return super.update("HzBomLineRecordDaoImpl_update",record);
    }

    /**
     * 找出最大排序号
     * @author haozt
     * @return
     */
    public Integer findMaxBomOrderNum(String projectId){
        return (Integer) super.findForObject("HzBomLineRecordDaoImpl_findMaxBomOrderNum",projectId);
    }

    /**
     * 找出一条BOMLine 对象
     * @author haozt
     * @param map 过滤器
     * @return
     */
    public HzBomLineRecord findBomLineByPuid(Map map){
        if(null == map.get("tableName") || map.get("tableName").equals("")){
            map.put("tableName","HZ_BOM_LINE_RECORD");
        }
        return (HzBomLineRecord)super.findForObject("HzBomLineRecordDaoImpl_findBomLineByPuid",map);
    }

    /**
     * 查询设变时BOM 历史记录
     * @author haozt
     * @param map 过滤器
     * @return
     */
    public List<HzBomLineRecord> findBomListForChange(Map map){
        if(null == map.get("tableName") || map.get("tableName").equals("")){
            map.put("tableName","HZ_BOM_LINE_RECORD");
        }
        return super.findForList("HzBomLineRecordDaoImpl_findBomListForChange",map);
    }

    /**
     * 找出全部的2或者2Y层级结构
     * @return
     */
    public List<String> findBomLineIndex(Map map){
        return super.findForList("HzBomLineRecordDaoImpl_findBomLineIndex",map);
    }

    public List<HzBomLineRecord> getAllBomLineRecordByProjectId(String projectId){
        return super.findForList("HzBomLineRecordDaoImpl_getAllBomLineRecordByProjectId",projectId);
    }

    public List<HzBomLineRecord> getLoadingCarPartBom(String projectId){

        return super.findForList("HzBomLineRecordDaoImpl_getLoadingCarPartBom",projectId);
    }

    public int delete(String puid){
        return super.delete("HzEbomRecordDAOImpl_delete",puid);
    }

    /**
     * 批量更新
     * @param records
     * @return
     */
    public int updateBatch(List<HzBomLineRecord> records){
        return super.update("HzEbomRecordDAOImpl_updateBatch",records);
    }
}
