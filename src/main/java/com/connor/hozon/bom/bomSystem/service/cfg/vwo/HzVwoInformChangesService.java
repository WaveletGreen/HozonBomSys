package com.connor.hozon.bom.bomSystem.service.cfg.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoInformChangesDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVwoInformChangesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoInformChanges;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/15 16:37
 * @Modified By:
 */
@Service("hzVwoInformChangesService")
public class HzVwoInformChangesService implements IHzVwoInformChangesService {
    @Autowired
    HzVwoInformChangesDao hzVwoInformChangesDao;

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int doDeleteByPrimaryKey(Long id) {
        return hzVwoInformChangesDao.deleteByPrimaryKey(id);
    }

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    @Override
    public int doInsert(HzVwoInformChanges record) {
        return hzVwoInformChangesDao.insert(record);
    }

    /**
     * 选择性插入
     *
     * @param record
     * @return
     */
    @Override
    public int doInsertSelective(HzVwoInformChanges record) {
        return hzVwoInformChangesDao.insertSelective(record);
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInformChanges doSelectByPrimaryKey(Long id) {
        return hzVwoInformChangesDao.selectByPrimaryKey(id);
    }

    /**
     * 根据VWO主键查询所有关联的人员
     *
     * @param vwoId
     * @return
     */
    @Override
    public List<HzVwoInformChanges> doSelectByVwoId(Long vwoId) {
        return hzVwoInformChangesDao.selectByVwoId(vwoId);
    }

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int doUpdateByPrimaryKeySelective(HzVwoInformChanges record) {
        return hzVwoInformChangesDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 主键更新，无选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int doUpdateByPrimaryKey(HzVwoInformChanges record) {
        return hzVwoInformChangesDao.updateByPrimaryKey(record);
    }

    /**
     * 获取VWO号下的总关联数
     *
     * @param vwoId
     * @return
     */
    @Override
    public Long tellMeHowManyOfIt(Long vwoId) {
        return hzVwoInformChangesDao.tellMeHowManyOfIt(vwoId);
    }
}
