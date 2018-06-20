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
        return super.insert("HzBomLineRecordDaoImpl_insert",record);
    }

    /**
     * 更新一条数据
     * @author haozt
     * @param record
     * @return
     */
    public int update(HzBomLineRecord record){
        return super.update("HzBomLineRecordDaoImpl_update",record);
    }

    /**
     * 找出最大排序号
     * @author haozt
     * @return
     */
    public int findMaxBomOrderNum(){
        return (int)super.findForObject("HzBomLineRecordDaoImpl_findMaxBomOrderNum",null);
    }

    /**
     * 找出一条BOMLine 对象
     * @author haozt
     * @param map 过滤器
     * @return
     */
    public HzBomLineRecord findBobLineByPuid(Map map){
        return (HzBomLineRecord)super.findForObject("HzBomLineRecordDaoImpl_findBobLineByPuid",map);
    }
}
