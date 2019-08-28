/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.depository.project.impl;

import cn.net.connor.hozon.dao.dao.depository.project.HzBrandRecordDao;
import cn.net.connor.hozon.dao.pojo.depository.project.HzBrandRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.IProject;
import cn.net.connor.hozon.services.service.depository.project.HzBrandService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in  2018/6/1 11:30
 * @Modified By:
 */
@Service("hzBrandService")
@Transactional
public class HzBrandServiceImpl implements HzBrandService {
    /**
     * 品牌dao层
     */
    @Autowired
    private HzBrandRecordDao hzBrandRecordDao;

    /**
     * 根据主键筛选1条品牌数据
     *
     * @param puid
     * @return
     */
    public HzBrandRecord doGetByPuid(String puid) {
        return hzBrandRecordDao.selectByPrimaryKey(puid);
    }

    /**
     * 更新所有数据
     *
     * @param record
     * @return
     */
    public boolean doUpdate(HzBrandRecord record) {
        return hzBrandRecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    /**
     * 选择性更新，为空的不更新，不为空的才更新
     *
     * @param record
     * @return
     */
    public boolean doUpdateSelective(HzBrandRecord record) {
        return hzBrandRecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    /**
     * 插入1条品牌信息
     *
     * @param record
     * @return
     */
    public boolean doInsertOne(HzBrandRecord record) {
        return hzBrandRecordDao.insert(record) > 0 ? true : false;
    }

    /**
     * 根据主键删除品牌信息
     *
     * @param puid
     * @return
     */
    public boolean doDeleteByPuid(String puid) {
        return hzBrandRecordDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    /**
     * 加载所有品牌信息
     *
     * @return
     */
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
    /**
     * 查重品牌编号
     *
     * @param brand 品牌对象
     * @return
     */
    public JSONObject doValidateCodeWithPuid(IProject brand) {
        JSONObject result = new JSONObject();
        result.put("valid", true);
        HzBrandRecord _brand = null;
        //有puid，则时更新，没有则为新增
        if (StringUtils.isNotEmpty(brand.getPuid())) {
            _brand = doGetByPuid(brand.getPuid());
            //根据puid查出来的同名，代表自身，则验证通过
            if (_brand.getCode().trim().equals(brand.getCode().trim())) {
                result.put("valid", true);
            } else if ((_brand = doGetByBrandCode(brand.getCode().trim())) != null) {
                //不是自身，更换了代号，检查是否有同代号的，有同代号则不允许验证通过
                result.put("valid", false);
            }
        } else if ((_brand = doGetByBrandCode(brand.getCode().trim())) != null) {
            result.put("valid", false);
        }
        return result;
    }
}
