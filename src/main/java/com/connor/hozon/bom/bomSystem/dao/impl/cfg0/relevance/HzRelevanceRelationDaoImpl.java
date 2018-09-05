package com.connor.hozon.bom.bomSystem.dao.impl.cfg0.relevance;

import com.connor.hozon.bom.bomSystem.dao.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.cfg.relevance.HzRelevanceRelationDao;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.relevance.HzRelevanceRelation;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/5 13:52
 * @Modified By:
 */
@Service
public class HzRelevanceRelationDaoImpl extends BasicDaoImpl<HzRelevanceRelation> implements HzRelevanceRelationDao {
    private final static HzRelevanceRelation RELATION = new HzRelevanceRelation();

    public HzRelevanceRelationDaoImpl() {
        clz = HzRelevanceRelationDao.class;
    }

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    @Override
    public int insertByBatch(List<HzRelevanceRelation> list) {
        return baseSQLUtil.executeInsert(list, clz.getCanonicalName() + ".insertByBatch");
    }

    /**
     * 相关性ID删除
     *
     * @param rrRelevanceId
     * @return
     */
    @Override
    public int deleteByBasicId(Long rrRelevanceId) {
        RELATION.setRrRelevanceId(rrRelevanceId);
        return baseSQLUtil.executeDelete(rrRelevanceId, clz.getCanonicalName() + ".deleteByBasicId");
    }
}
