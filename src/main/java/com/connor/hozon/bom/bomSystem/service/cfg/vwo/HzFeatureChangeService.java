package com.connor.hozon.bom.bomSystem.service.cfg.vwo;

import com.connor.hozon.bom.bomSystem.dao.cfg.vwo.HzFeatureChangeDao;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzFeatureChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0Record;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
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
     * @param id
     * @return
     */
    @Override
    public int doDeleteByPrimaryKey(Long id) {
        return hzFeatureChangeDao.deleteByPrimaryKey(id);
    }

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    @Override
    public int doInsert(HzFeatureChangeBean record) {
        return hzFeatureChangeDao.insert(record);
    }

    /**
     * 主键查找
     *
     * @param id
     * @return
     */
    @Override
    public HzFeatureChangeBean doSelectByPrimaryKey(Long id) {
        return hzFeatureChangeDao.selectByPrimaryKey(id);
    }

    /**
     * 主键更新
     *
     * @param record
     * @return
     */
    @Override
    public int doUpdateByPrimaryKey(HzFeatureChangeBean record) {
        return hzFeatureChangeDao.updateByPrimaryKey(record);
    }

    /**
     * 根据配置进行插入
     *
     * @param record
     * @return
     */
    @Override
    public boolean insertByCfg(HzCfg0Record record, String tableName) {
        HzFeatureChangeBean bean = new HzFeatureChangeBean();
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
        bean.setFeatureCreatDate(record.getCreateDate());
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
        /*所属表*/
        bean.setTableName(tableName);
        return doInsert(bean) > 0 ? true : false;
    }

}
