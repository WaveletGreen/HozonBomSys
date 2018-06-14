package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzBrandRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzBrandRecord;

import java.util.List;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/6/1 11:30
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzBrandService")
public class HzBrandService {
    @Autowired
    HzBrandRecordDao hzBrandRecordDao;

    public HzBrandRecord doGetByPuid(String puid) {
        return hzBrandRecordDao.selectByPrimaryKey(puid);
    }

    public boolean doUpdate(HzBrandRecord record) {
        return hzBrandRecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    public boolean doUpdateSelective(HzBrandRecord record) {
        return hzBrandRecordDao.updateSelective(record) > 0 ? true : false;
    }

    public boolean doInsertOne(HzBrandRecord record) {
        return hzBrandRecordDao.insert(record) > 0 ? true : false;
    }

    public boolean doDeleteByPuid(String puid) {
        return hzBrandRecordDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    public List<HzBrandRecord> doGetAllBrand() {
        return hzBrandRecordDao.selectAll();
    }

    /**
     * 验证品牌是否符合要求
     * 只验证品牌代码和品牌名称
     *
     * @param brand 品牌对象
     * @return
     */
    public boolean validate(HzBrandRecord brand) {
        //品牌代号和名称不能为空
        if (null == brand.getpBrandCode() || null == brand.getpBrandName() || "".equals(brand.getpBrandCode()) || "".equals(brand.getpBrandName())) {
            return false;
        } else return true;
    }

    /***
     * 根据品牌代码查询品牌
     * @param brandCode 品牌代码
     * @return
     */
    public HzBrandRecord doGetByBrandCode(String brandCode) {
        return hzBrandRecordDao.selectByBrandCode(brandCode);
    }
}
