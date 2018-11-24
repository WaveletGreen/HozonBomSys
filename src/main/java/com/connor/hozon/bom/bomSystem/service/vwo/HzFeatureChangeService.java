/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.vwo;

import com.connor.hozon.bom.bomSystem.dao.vwo.HzFeatureChangeDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzFeatureChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

import java.util.*;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/9 19:25
 * @Modified By:
 */
@Service("hzFeatureChangeService")
public class HzFeatureChangeService implements IHzFeatureChangeService {
    @Autowired
    HzFeatureChangeDao hzFeatureChangeDao;

    /**
     * 主键删除
     *
     * @param bean
     * @return
     */
    @Override
    public boolean doDeleteByPrimaryKey(HzFeatureChangeBean bean) {
        return hzFeatureChangeDao.deleteByPrimaryKey(bean) > 0 ? true : false;
    }

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    @Override
    public Long doInsert(HzFeatureChangeBean record) {
        hzFeatureChangeDao.insert(record);
        return record.getId();
    }

    /**
     * 主键查找
     *
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean doSelectByPrimaryKey(HzFeatureChangeBean bean) {
        return hzFeatureChangeDao.selectByPrimaryKey(bean);
    }

    /**
     * 主键查找
     *
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean doSelectAfterByPk(HzFeatureChangeBean bean) {
        setAfterTable(bean);
        return doSelectByPrimaryKey(bean);
    }

    /**
     * 主键查找
     *
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean doSelectBeforeByPk(HzFeatureChangeBean bean) {
        setBeforeTable(bean);
        return doSelectByPrimaryKey(bean);
    }


    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean doFindNewestChange(HzFeatureChangeBean bean) {
        return hzFeatureChangeDao.findNewestChange(bean);
    }

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean doFindNewestChangeFromAfter(HzFeatureChangeBean bean) {
        setAfterTable(bean);
        return doFindNewestChange(bean);
    }

    /**
     * 查找特性下最新的更改
     *
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean doFindNewestChangeFromBefore(HzFeatureChangeBean bean) {
        setBeforeTable(bean);
        return doFindNewestChange(bean);
    }


    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    @Override
    public boolean doUpdateByPrimaryKey(HzFeatureChangeBean record) {
        return hzFeatureChangeDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    /**
     * 更新变更后
     *
     * @param bean
     * @return
     */
    @Override
    public boolean doUpdateAfterByPk(HzFeatureChangeBean bean) {
        setAfterTable(bean);
        return doUpdateByPrimaryKey(bean);
    }

    /**
     * 更新变更前
     *
     * @param bean
     * @return
     */
    @Override
    public boolean doUpdateBeforeByPk(HzFeatureChangeBean bean) {
        setBeforeTable(bean);
        return doUpdateByPrimaryKey(bean);
    }

    /**
     * 根据配置进行插入
     *
     * @param record
     * @return
     */
    @Override
    public Long insertByCfgAfter(HzCfg0Record record) {
        HzFeatureChangeBean bean = new HzFeatureChangeBean();
        setAfterTable(bean);
        bean.setChangeCreateDate(new Date());
        return insertByCfg(record, bean);
    }

    /**
     * 根据配置进行插入
     *
     * @param record
     * @return
     */
    @Override
    public Long insertByCfgBefore(HzCfg0Record record) {
        HzFeatureChangeBean bean = new HzFeatureChangeBean();
        setBeforeTable(bean);
        bean.setChangeCreateDate(new Date());
        return insertByCfg(record, bean);
    }

    /**
     * 设置记录的表名
     *
     * @param bean
     * @return
     */
    private HzFeatureChangeBean setAfterTable(HzFeatureChangeBean bean) {
        bean.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
        bean.setSeqName("SEQ_HZ_FEATURE_AFTER_CHANGE");
        return bean;
    }

    /**
     * 设置记录的表名
     *
     * @param bean
     * @return
     */
    private HzFeatureChangeBean setBeforeTable(HzFeatureChangeBean bean) {
        bean.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");
        bean.setSeqName("SEQ_HZ_FEATURE_BEFORE_CHANGE");
        return bean;
    }


    /**
     * 根据配置进行插入
     *
     * @param record
     * @return
     */
    public Long insertByCfg(HzCfg0Record record, HzFeatureChangeBean bean) {
        reflect(record, bean);
//        /*所属表*/
//        bean.setTableName(tableName);
//        /*序列名称*/
//        bean.setSeqName(seqName);
        return doInsert(bean);
    }


    /**
     * 配置对应的变更，做字段按对应
     *
     * @param record
     * @param bean
     * @return
     */
    @Override
    public HzFeatureChangeBean reflect(HzCfg0Record record, HzFeatureChangeBean bean) {
        /*主配置的puid，用这个可以找到主配置的对象*/
        bean.setCfg0MainItemPuid(record.getpCfg0MainItemPuid());
        /*废止时间*/
        bean.setCfgAbolishDate(record.getCfgAbolishDate());
        /*生效时间*/
        bean.setCfgEffectedDate(record.getCfgEffectedDate());
        /*相关性，并不需要在初次导入到Bom系统的时候进行值写入，是在Bom系统进行手动维护，存储规则并传到ERP中*/
        bean.setCfg0Relevance(record.getpCfg0Relevance());
        /*是否在流程中*/
        bean.setCfgIsInProcess(record.getCfgIsInProcess());
        /*当前的特性值的主键，作为外键*/
        bean.setCfgPuid(record.getPuid());
        /*状态*/
        bean.setCfgStatus(record.getCfgStatus());
        /*创建人*/
        bean.setFeatureCreator(record.getCreator());
        /*创建日期*/
        bean.setFeatureCreateDate(record.getCreateDate());
        /*族描述*/
        bean.setFeatureDesc(record.getpCfg0FamilyDesc());
        /*最近一次修改者*/
        bean.setFeatureLastModifier(record.getLastModifier());
        /*最近一次修改日期*/
        bean.setFeatureLastModifyDate(record.getLastModifyDate());
        /*选项族的名称，也是选项族的objectid*/
        bean.setFeatureName(record.getpCfg0FamilyName());
        /*选项族的数据库puid */
        bean.setFeaturePuid(record.getpCfg0FamilyPuid());
        /*配置描述*/
        bean.setFeatureValueDesc(record.getpCfg0Desc());
        /*选项值的objectid*/
        bean.setFeatureValueName(record.getpCfg0ObjectId());
        /*特性英文名*/
        bean.setH9featureenname(record.getpH9featureenname());
        /*特性是否已成功发送到SAP*/
        bean.setIsFeatureSent(record.getIsFeatureSent());
        /*相关性是否已成功发送到SAP*/
        bean.setIsRelevanceSent(record.getIsRelevanceSent());
        /*VWO_ID*/
        bean.setVwoId(record.getVwoId());
        return bean;
    }

    /**
     * 根据VWO号查找变更后的数据
     * @param vwo
     * @return
     */
    @Override
    public List<HzFeatureChangeBean> doSelectAfterByVwoId(Long vwo) {
        HzFeatureChangeBean bean = new HzFeatureChangeBean();
        bean.setVwoId(vwo);
        setAfterTable(bean);
        return hzFeatureChangeDao.selectByVwoId(bean);
    }
    /**
     * 根据VWO号查找变更前的数据
     * @param vwo
     * @return
     */
    @Override
    public List<HzFeatureChangeBean> doSelectBeforeByVwoId(Long vwo) {
        HzFeatureChangeBean bean = new HzFeatureChangeBean();
        bean.setVwoId(vwo);
        setAfterTable(bean);
        return hzFeatureChangeDao.selectByVwoId(bean);
    }

    @Override
    public int doInsertListBefore(List<HzFeatureChangeBean> hzFeatureChangeBeanListBefore) {
        for(HzFeatureChangeBean hzFeatureChangeBean : hzFeatureChangeBeanListBefore){
            setBeforeTable(hzFeatureChangeBean);
        }
        return hzFeatureChangeDao.insertList(hzFeatureChangeBeanListBefore);
    }

    @Override
    public int doInsertListAfter(List<HzFeatureChangeBean> hzFeatureChangeBeanListAfter) {
        for(HzFeatureChangeBean hzFeatureChangeBean : hzFeatureChangeBeanListAfter){
            setAfterTable(hzFeatureChangeBean);
        }
        return hzFeatureChangeDao.insertList(hzFeatureChangeBeanListAfter);
    }

    @Override
    public List<HzFeatureChangeBean> doSelectCfgUidsByVwoId(Long vwoId) {
        HzFeatureChangeBean bean=new HzFeatureChangeBean();
        bean.setVwoId(vwoId);
        return hzFeatureChangeDao.selectCfgUidsByVwoId(bean);
    }

    @Override
    public List<HzFeatureChangeBean> doSelectHasEffect(List<HzCfg0Record> records) {
        return hzFeatureChangeDao.doSelectHasEffect(records);
    }

    @Override
    public boolean updateStatusByOrderId(Long orderId, int status) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("status",status);
        int updateNum = hzFeatureChangeDao.updateStatusByOrderId(map);
        if(updateNum<=0){
            return false;
        }
        return true;
    }

    @Override
    public int doDeleteByPrimaryKeys(List<Long> changeFeatureIds) {
        return hzFeatureChangeDao.doDeleteByPrimaryKeys(changeFeatureIds);
    }

    @Override
    public List<HzFeatureChangeBean> doselectByChangeId(Long orderId) {
        return hzFeatureChangeDao.doselectByChangeId(orderId);
    }

    /**
     * 查询变更前的数据和当前数据
     * @param cfgPuid
     * @param vwoId
     * @return
     */
    public List<HzFeatureChangeBean> doQueryLastTwoChange(String cfgPuid, Long vwoId){
        HzFeatureChangeBean hzFeatureChangeBean = new HzFeatureChangeBean();
        hzFeatureChangeBean.setCfgPuid(cfgPuid);
        hzFeatureChangeBean.setVwoId(vwoId);
        HzFeatureChangeBean hzFeatureChangeBeanBefor = hzFeatureChangeDao.selectLast(hzFeatureChangeBean);
        HzFeatureChangeBean hzFeatureChangeBeanAfter = hzFeatureChangeDao.selectByChangeIdAndCfgid(hzFeatureChangeBean);
        List<HzFeatureChangeBean> hzFeatureChangeBeans = new ArrayList<>();
        hzFeatureChangeBeans.add(hzFeatureChangeBeanBefor);
        hzFeatureChangeBeans.add(hzFeatureChangeBeanAfter);
        return hzFeatureChangeBeans;
//        return hzFeatureChangeDao.doQueryLastTwoChange(hzFeatureChangeBean);
    }
}
