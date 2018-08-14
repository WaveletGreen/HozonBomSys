package com.connor.hozon.bom.bomSystem.dao.impl.cfg0.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.vwo.HzVwoInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/10 13:46
 * @Modified By:
 */
@Configuration
public class HzVwoInfoDaoImpl implements HzVwoInfoDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    private static final HzVwoInfo INFO = new HzVwoInfo();

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return baseSQLUtil.executeDelete(id, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.deleteByPrimaryKey");
    }

    /**
     * 插入1条vwo
     *
     * @param record
     * @return
     */
    @Override
    public int insert(HzVwoInfo record) {
        return baseSQLUtil.executeInsert(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.insert");
    }

    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfo selectByPrimaryKey(Long id) {
        HzVwoInfo hzVwoInfo = new HzVwoInfo();
        hzVwoInfo.setId(id);
        return baseSQLUtil.executeQueryById(hzVwoInfo, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.selectByPrimaryKey");
    }

    /**
     * 更新vwo
     *
     * @param record
     * @return
     */
    @Override
    public int updateByPrimaryKey(HzVwoInfo record) {
        return baseSQLUtil.executeUpdate(record, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.updateByPrimaryKey");
    }

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    @Override
    public HzVwoInfo findMaxAreaVwoNum() {
        return baseSQLUtil.executeQueryById(INFO, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.findMaxAreaVwoNum");
    }

    /**
     * 分页查询
     *
     * @param params
     * @return
     *
     * QueryBase queryBase,String projectUid
     */
    @Override
    public List<HzVwoInfo> selectListByProjectUid(Map<String,Object> params) {
        return baseSQLUtil.executeQueryByPass(INFO, params, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.selectListByProjectUid");
    }

    /**
     * 获取当前项目下的变更总数
     *
     * @param projectUid
     * @return
     */
    @Override
    public int tellMeHowManyOfIt(String projectUid) {
        return baseSQLUtil.executeQueryByPass(new Integer(-1), projectUid, "com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzVwoInfoDao.tellMeHowManyOfIt", true);
    }


}
