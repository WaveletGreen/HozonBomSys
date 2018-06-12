package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzPlatformRecord;

import java.util.List;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/6/1 11:30
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzPlatformService")
public class HzPlatformService {
    @Autowired
    HzPlatformRecordDao hzPlatformRecordDao;

    public HzPlatformRecord doGetByPuid(String puid) {
        return hzPlatformRecordDao.selectByPrimaryKey(puid);
    }

    public boolean doUpdate(HzPlatformRecord record) {
        return hzPlatformRecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    public boolean doInsertOne(HzPlatformRecord record) {
        return hzPlatformRecordDao.insert(record) > 0 ? true : false;
    }

    public boolean doDeleteByPuid(String puid) {
        return hzPlatformRecordDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    public List<HzPlatformRecord> doGetAllPlatform() {
        return hzPlatformRecordDao.selectAll();
    }

    /**
     * 验证平台是否符合要求
     * <p>
     * 只验证平台代码和平台名称
     *
     * @param platform 平台对象
     * @return
     */
    public boolean validate(HzPlatformRecord platform) {
        //平台代号和名称不能为空
        if (null == platform.getpPlatformCode() || null == platform.getpPlatformName() || "".equals(platform.getpPlatformCode()) || "".equals(platform.getpPlatformName())) {
            return false;
        }
        return true;
    }

    /**
     * 根据平台代号查找平台数据
     * @param platformCode
     * @return
     */
    public HzPlatformRecord doGetByPlatformCode(String platformCode) {
        return hzPlatformRecordDao.selectByPlatformCode(platformCode);
    }
}
