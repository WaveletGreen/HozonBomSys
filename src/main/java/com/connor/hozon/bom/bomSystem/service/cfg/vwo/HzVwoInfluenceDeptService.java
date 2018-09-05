package com.connor.hozon.bom.bomSystem.service.cfg.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfluenceDeptDao;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzVwoInfluenceDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoInfluenceDept;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/20 14:08
 * @Modified By:
 */
@Service("hzVwoInfluenceDeptService")
public class HzVwoInfluenceDeptService implements IHzVwoInfluenceDeptService {
    @Autowired
    HzVwoInfluenceDeptDao hzVwoInfluenceDeptDao;

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int doDeleteByPrimaryKey(Long id) {
        return hzVwoInfluenceDeptDao.deleteByPrimaryKey(id);
    }

    /**
     * 全插入
     *
     * @param record
     * @return
     */
    @Override
    public int doInsert(HzVwoInfluenceDept record) {
        return hzVwoInfluenceDeptDao.insert(record);
    }

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    @Override
    public int doInsertSelective(HzVwoInfluenceDept record) {
        return hzVwoInfluenceDeptDao.insertSelective(record);
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfluenceDept doSelectByPrimaryKey(Long id) {
        return hzVwoInfluenceDeptDao.selectByPrimaryKey(id);
    }

    /**
     * 根据VWO ID查找影响部门
     *
     * @param vwoId
     * @return
     */
    @Override
    public HzVwoInfluenceDept doSelectByVwoId(Long vwoId) {
        return hzVwoInfluenceDeptDao.selectByVwoId(vwoId);
    }


    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    @Override
    public int doUpdateByPrimaryKeySelective(HzVwoInfluenceDept record) {
        return hzVwoInfluenceDeptDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 主键全更新
     *
     * @param record
     * @return
     */
    @Override
    public int doUpdateByPrimaryKey(HzVwoInfluenceDept record) {
        return hzVwoInfluenceDeptDao.updateByPrimaryKey(record);
    }
}
