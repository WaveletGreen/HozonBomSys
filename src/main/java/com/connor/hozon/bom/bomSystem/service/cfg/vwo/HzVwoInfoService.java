package com.connor.hozon.bom.bomSystem.service.cfg.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzVwoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoInfo;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Service("hzVwoInfoService")
public class HzVwoInfoService implements IHzVwoInfoService {
    @Autowired
    HzVwoInfoDao hzVwoInfoDao;

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean doDeleteByPrimaryKey(Long id) {
        return hzVwoInfoDao.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    /**
     * 插入1条vwo
     *
     * @param record
     * @return
     */
    @Override
    public Long doInsert(HzVwoInfo record) {
        hzVwoInfoDao.insert(record);
        return record.getId();
    }

    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfo doSelectByPrimaryKey(Long id) {
        return hzVwoInfoDao.selectByPrimaryKey(id);
    }

    /**
     * 更新vwo
     *
     * @param record
     * @return
     */
    @Override
    public boolean doUpdateByPrimaryKey(HzVwoInfo record) {
        return hzVwoInfoDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    @Override
    public HzVwoInfo doFindMaxAreaVwoNum() {
        return hzVwoInfoDao.findMaxAreaVwoNum();
    }
}
