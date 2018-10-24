/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDTO;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;
import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.bomSystem.helper.StringHelper;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.main.HzCfg0MainService;
import com.connor.hozon.bom.resources.mybatis.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.cfg.cfg0.HzCfg0OptionFamily;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.resourcesLibrary.dictionaryLibrary.HzDictionaryLibrary;

import java.util.*;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzCfg0Service")
public class HzCfg0Service {
    /**
     * 特性值dao层
     */
    @Autowired
    HzCfg0RecordDao hzCfg0RecordDao;
    /**
     * 主配置
     */
    @Autowired
    HzCfg0MainService hzCfg0MainService;

    /**
     * 字典
     */
    @Autowired
    HzDictionaryLibraryDao hzDictionaryLibraryDao;

    /**
     * 特性
     */
    @Autowired
    HzCfg0OptionFamilyService hzCfg0OptionFamilyService;

    private final static Logger LOGGER = LoggerFactory.getLogger(HzCfg0Service.class);

    /**
     * 主键查询
     *
     * @param puid
     * @return
     */
    public HzCfg0Record doSelectByPrimaryKey(String puid) {
        return hzCfg0RecordDao.selectByPrimaryKey(puid);
    }

    /**
     * 分页查询
     *
     * @param projectPuid
     * @param queryBase
     * @return
     */
    public List<HzCfg0Record> doLoadCfgListByProjectPuid(String projectPuid, HzFeatureQueryDTO queryBase) {
        return hzCfg0RecordDao.selectListByProjectPuid(projectPuid, queryBase);
    }


    public List<HzMaterielFeatureBean> doSelectMaterielFeatureByProjectPuid(String projectPuid) {
        return hzCfg0RecordDao.selectMaterielFeatureByProjectPuid(projectPuid);
    }

    /**
     * 插入单条特性值
     *
     * @param record
     * @return
     */
    public boolean doInsertOne(HzCfg0Record record) {
        return hzCfg0RecordDao.insert(record) > 0 ? true : false;
    }


    /**
     * 主键查询
     *
     * @param puid
     * @return
     */
    public HzCfg0Record doSelectOneByPuid(String puid) {
        return hzCfg0RecordDao.selectByPrimaryKey(puid);
    }


    /**
     * 更新单条特性值数据
     *
     * @param record
     * @return
     */
    public boolean doUpdate(HzCfg0Record record) {
        return hzCfg0RecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    /**
     * 批量更新
     *
     * @param record
     * @return
     */
    public boolean doUpdateByBatch(Map<String, Object> record) {
        return hzCfg0RecordDao.setIsSent(record) > 0 ? true : false;
    }


    public boolean doDeleteByPuid(HzCfg0Record record) {
        return false;
    }


    public boolean doDeleteCfgByList(List<HzCfg0Record> records) {
        return hzCfg0RecordDao.deleteCfgByList(records) > 0 ? true : false;
    }


    /***
     * 根据table，进行查询并拼接成相关性值
     * @param projectPuid 项目puid
     * @param _list 存储结果的list
     * @param index 序号，使用封装类进行引用从而可以修改引用数据
     * @param _table 取数据的表：HZ_CFG0_RECORD是原数据，HZ_CFG0_ADD_CFG_RECORD是添加的数据
     * @return 返回当前最后一个筛选结果的的正序顺序
     */
    public int doLoadRelevance(String projectPuid, List<HzRelevanceBean> _list, int index, String _table) {
        List<HzCfg0Record> records = null;
        List<HzCfg0Record> needToUpdate = new ArrayList<>();
        if ("HZ_CFG0_RECORD".equals(_table)) {
            records = doLoadCfgListByProjectPuid(projectPuid, new HzFeatureQueryDTO());
        }
//        else if ("HZ_CFG0_ADD_CFG_RECORD".equals(_table)) {
//            records = doLoadAddedCfgListByProjectPuid(projectPuid);
//        }
        for (HzCfg0Record record : records) {
            HzRelevanceBean bean = new HzRelevanceBean();
            bean.set_table(_table);
            bean.setIndex(++index);
            bean.setPuid(record.getPuid());
            bean.setRelevance(record.getpCfg0FamilyName() + "-" + record.getpCfg0ObjectId());
            bean.setRelevanceDesc((record.getpCfg0FamilyDesc() == null ? "" : record.getpCfg0FamilyDesc()) + "-" + (record.getpCfg0Desc() == null ? "" : record.getpCfg0Desc()));
            if (record.getpCfg0Relevance() == null) {
                record.setpCfg0Relevance("$ROOT." + record.getpCfg0FamilyName() + " = '" + record.getpCfg0ObjectId() + "'");
                needToUpdate.add(record);
            }
            bean.setRelevanceCode(record.getpCfg0Relevance());
            _list.add(bean);
        }
        updateByTableName(needToUpdate, _table);
        return index;
    }

    public List<HzCfg0Record> doLoadListByPuids(List<String> list) {
        Map<String, Object> _map = new HashMap<>();
        _map.put("whichTable", "HZ_CFG0_RECORD");
        _map.put("list", list);
        return hzCfg0RecordDao.selectCfg0ListByPuids(_map);
    }

    public void updateByTableName(List<HzCfg0Record> list, String table) {
        switch (table) {
            case "HZ_CFG0_RECORD":
                list.forEach(_l -> hzCfg0RecordDao.updateByPrimaryKey(_l));
                break;
//            case "HZ_CFG0_ADD_CFG_RECORD":
//                list.forEach(_l -> hzCfg0RecordDao.updateAddedCfgByPrimaryKey(_l));
//                break;
        }
    }

    public boolean preCheck(HzCfg0Record record) {
        //存在puid的可以同名更新
        if (StringHelper.checkString(record.getPuid())) {
            return true;
        }
        record.setWhichTable("HZ_CFG0_RECORD");
        List<HzCfg0Record> list;
        list = hzCfg0RecordDao.selectByCodeAndDesc(record);
        if (list == null || list.isEmpty()) {
//            record.setWhichTable("HZ_CFG0_ADD_CFG_RECORD");
//            list = hzCfg0RecordDao.selectByCodeAndDesc(record);
//            if (list == null || list.isEmpty()) {
//                return true;
//            }
            return true;
        }
        return false;
    }

    /**
     * 项目上的特性总数是多少，针对项目而非整个合众公司
     *
     * @param dto
     * @return
     */
    public int tellMeHowManyOfThose(HzFeatureQueryDTO dto) {
        return hzCfg0RecordDao.tellMeHowManyOfThose(dto);
    }

    /**
     * 设置已经在流程中
     *
     * @param cfgs 特性值集合
     * @return
     */
    public int doSetToProcess(List<HzCfg0Record> cfgs) {
        Map<String, Object> params = new HashMap<>();
        params.put("cfgIsInProcess", "1");
        params.put("list", cfgs);
        return hzCfg0RecordDao.setToProcess(params);
    }

    public List<HzCfg0Record> doLoadByCondition(HzFeatureQueryDTO queryBase) {
        return hzCfg0RecordDao.selectByCondition(queryBase);
    }

    /**
     * 用HZCSYS+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    public HzCfg0Record doSelectByCodeAndDescWithMainItem(HzCfg0Record record) {
        return hzCfg0RecordDao.selectByCodeAndDescWithMainItem(record);
    }

    /**
     * 用"车身颜色"+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    public HzCfg0Record doSelectByCodeAndCnDescWithMainItem(HzCfg0Record record) {
        return hzCfg0RecordDao.selectByCodeAndCnDescWithMainItem(record);
    }

    public List<HzCfg0Record> doSelectByFamilyUidWithProject(String familyUid, String projectUid) {
        return hzCfg0RecordDao.selectByFamilyUidWithProject(familyUid, projectUid);
    }

    /**
     * 进行配置字典联动的后续操作
     *
     * @param projectUid 项目UID
     */
    public void synDictionaryAfterOption(String projectUid) {
        HzCfg0MainRecord mainRecord = hzCfg0MainService.doGetbyProjectPuid(projectUid);
        HzCfg0Record record = new HzCfg0Record();
        HzCfg0OptionFamily family = new HzCfg0OptionFamily();
        Set<String> setOfFamily = new HashSet<>();
        Date now = new Date();
        boolean isAllOver = true;
        //关键字段为空，则同步同步历史数据到配置字典中
        if (null == mainRecord.getFeatureSynDicFlag() || 0 == mainRecord.getFeatureSynDicFlag()) {
            Map<String, HzDictionaryLibrary> libraryMap = new HashMap<>();
            List<HzCfg0Record> records = hzCfg0RecordDao.selectListByProjectPuid(projectUid, new HzFeatureQueryDTO());
//            List<HzCfg0OptionFamily> families = hzCfg0OptionFamilyService.doSelectNameByMap(projectUid, null, false);
            //以特性值为基准，查找配置字典中的特性值数据，但是特性不匹配的话，则强制增加特性，并记录特性
            for (int i = 0; i < records.size(); i++) {
                HzCfg0Record local = records.get(i);
                HzDictionaryLibrary library = hzDictionaryLibraryDao.findDictionaryLibraryOrCode(local.getpCfg0ObjectId());
                if (library == null) {
                    //新插入
                    library = new HzDictionaryLibrary();
                    library.setPuid(UUIDHelper.generateUpperUid());
                    library.setEffectTime(now);
                    library.setFailureTime(DateStringHelper.forever());
                    library.setFamillyCode(local.getpCfg0FamilyName());
                    library.setFamillyCh(local.getpCfg0FamilyDesc());
                    library.setEigenValue(local.getpCfg0ObjectId());
                    library.setValueDescCh(local.getpCfg0Desc());
                    library.setValueDescEn(local.getpH9featureenname());
                    library.setNote("");
                    if (hzDictionaryLibraryDao.insert(library) <= 0) {
                        LOGGER.error("自动同步历史特性值到配置字典失败");
                        isAllOver = false;
                        continue;
                    }
                }
                record.setPuid(records.get(i).getPuid());
                record.setCfgDicLibUid(library.getPuid());
                if (hzCfg0RecordDao.updateByPrimaryKey(record) <= 0) {
                    LOGGER.error("更新特性值对应的配置字典外键值发生失败");
                    isAllOver = false;
                } else {
                    if (setOfFamily.contains(local.getpCfg0FamilyPuid())) {
                        continue;
                    }
                    family.setPuid(local.getpCfg0FamilyPuid());
                    if (hzCfg0OptionFamilyService.doGetById(family) != null) {
                        family.setOfDicLibUid(library.getPuid());
                        if (!hzCfg0OptionFamilyService.doUpdateByPrimaryKeySelective(family)) {
                            LOGGER.error("更新特性与配置字典外键值发生失败");
                            isAllOver = false;
                        }
                        setOfFamily.add(local.getpCfg0FamilyPuid());
                    }
                }
            }
            if (isAllOver) {
                HzCfg0MainRecord ma = new HzCfg0MainRecord();
                ma.setPuid(mainRecord.getPuid());
                ma.setFeatureSynDicFlag(1);
                if (!hzCfg0MainService.doUpdateByPrimaryKeySelective(ma)) {
                    LOGGER.error("更新主配置的特性值历史数据更新标签失败");
                }
            }
        }
    }

    public int doupdateList(List<HzCfg0Record> hzCfg0RecordList) {
        return hzCfg0RecordDao.updateList(hzCfg0RecordList);
    }
}
