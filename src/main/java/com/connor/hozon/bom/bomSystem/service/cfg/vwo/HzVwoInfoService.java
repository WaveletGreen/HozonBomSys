package com.connor.hozon.bom.bomSystem.service.cfg.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzVwoInfoDao;
import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVwoInfoService;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.vwo.HzVwoInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Long doInsert(HzVwoInfo record)  {
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
    public boolean doUpdateByPrimaryKey(HzVwoInfo record)  {
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

    /**
     * 根据分页进行查询
     *
     * @param queryBase
     */
    @Override
    public List<HzVwoInfo> doSelectListByProjectUid(QueryBase queryBase, String projectUid) {
        Map<String, Object> params = new HashMap<>();
        params.put("param", queryBase);
        params.put("projectUid", projectUid);
        return hzVwoInfoDao.selectListByProjectUid(params);
    }

    /**
     * 获取总数
     *
     * @param projectUid
     * @return
     */
    @Override
    public int tellMeHowManyOfIt(String projectUid) {
        return hzVwoInfoDao.tellMeHowManyOfIt(projectUid);
    }

    /**
     * 生成VWO号码
     */
    @Override
    public HzVwoInfo generateVWONum() {
        Date now = new Date();
        HzVwoInfo hzVwoInfo = doFindMaxAreaVwoNum();
        if (hzVwoInfo == null || hzVwoInfo.getVwoNum() == null) {
            hzVwoInfo = new HzVwoInfo();
            //当月第一位vwo号
            hzVwoInfo.setVwoNum("VC" + DateStringHelper.dateToString4(now) + "0001");
        } else {
            hzVwoInfo.setVwoNum("VC" + String.valueOf(Long.parseLong(hzVwoInfo.getVwoNum().substring(2)) + 1));
        }
        return hzVwoInfo;
    }

}
