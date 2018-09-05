package com.connor.hozon.bom.bomSystem.dao.impl.cfg0.relevance;

import com.connor.hozon.bom.bomSystem.dao.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.cfg.relevance.HzRelevanceBasicDao;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.relevance.HzRelevanceBasic;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/5 13:26
 * @Modified By:
 */
@Service
public class HzRelevanceBasicDaoImpl extends BasicDaoImpl<HzRelevanceBasic> implements HzRelevanceBasicDao {
    private final static HzRelevanceBasic BASIC = new HzRelevanceBasic();

    public HzRelevanceBasicDaoImpl() {
        clz = HzRelevanceBasicDao.class;
    }

    /**
     * 删除项目下的全部相关性
     *
     * @param rbProjectUid
     * @return
     */
    @Override
    public int deleteByProjectUid(String rbProjectUid) {
        return baseSQLUtil.executeDeleteByPass(rbProjectUid, clz.getCanonicalName() + ".deleteByProjectUid");
    }

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    @Override
    public int insertByBatch(List<HzRelevanceBasic> list) {
        return baseSQLUtil.executeInsert(list, clz.getCanonicalName() + ".insertByBatch");
    }

    @Override
    public Long insertBasic(HzRelevanceBasic hzRelevanceBasic) {
        baseSQLUtil.executeInsert(hzRelevanceBasic,"com.connor.hozon.bom.bomSystem.dao.cfg.relevance.HzRelevanceBasicDao.insertBasic");
        return hzRelevanceBasic.getId();

    }

}
