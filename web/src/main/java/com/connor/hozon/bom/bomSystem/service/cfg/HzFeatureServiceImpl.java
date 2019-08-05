/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.cfg;

import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureValueDao;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeature;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzMaterielFeatureBean;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.dao.query.feature.HzFeatureQuery;
import cn.net.connor.hozon.services.service.main.HzMainConfigService;
import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;
import cn.net.connor.hozon.common.util.DateStringHelper;
import cn.net.connor.hozon.common.util.StringHelper;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.resources.mybatis.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.resourcesLibrary.dictionaryLibrary.HzDictionaryLibrary;

import java.util.*;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 特性服务层，起名为Cfg0是由于TC中配置管理中前缀是Cfg0，沿用TC的命名，到后来不再沿用TC，改采用英文Feature来标识特性
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzFeatureService")
@Slf4j
public class HzFeatureServiceImpl implements HzFeatureService {
    /**
     * 特性值dao层
     */

    @Autowired
    private HzFeatureValueDao hzFeatureValueDao;
    /**
     * 主配置
     */
    @Autowired
    private HzMainConfigService hzMainConfigService;

    /**
     * 字典
     */
    @Autowired
    private HzDictionaryLibraryDao hzDictionaryLibraryDao;

    /**
     * 特性
     */
    @Autowired
    private HzCfg0OptionFamilyService hzCfg0OptionFamilyService;

    /**
     * 主键查询
     *
     * @param puid
     * @return
     */
    public HzFeatureValue doSelectByPrimaryKey(String puid) {
        HzFeatureValue record = new HzFeatureValue();
        record.setPuid(puid);
        record.setWhichTable("HZ_CFG0_RECORD");
        return hzFeatureValueDao.selectByPrimaryKey(record);
    }

    /**
     * 分页查询
     *
     * @param projectId 项目ID
     * @param queryBase 分页查询参数
     * @return
     */
    public List<HzFeatureValue> selectFeatureListByProjectId(String projectId, HzFeatureQuery queryBase) {
        Map<String, Object> params = new HashMap<>();
        params.put("projectPuid", projectId);
        params.put("whichTable", "HZ_CFG0_RECORD");
        params.put("param", queryBase);
        return doLoadCfgListByProjectPuid(params);
    }
    /**
     * 分页查询
     * @param params 分页查询入参
     * @return
     */
    public List<HzFeatureValue> doLoadCfgListByProjectPuid(Map<String,Object> params) {
        return hzFeatureValueDao.selectListByProjectPuid(params);
    }


    public List<HzMaterielFeatureBean> doSelectMaterielFeatureByProjectPuid(String projectPuid) {
        return hzFeatureValueDao.selectMaterielFeatureByProjectPuid(projectPuid);
    }

    /**
     * 插入单条特性值
     *
     * @param record
     * @return
     */
    public boolean doInsertOne(HzFeatureValue record) {
        return hzFeatureValueDao.insert(record) > 0 ? true : false;
    }


    /**
     * 主键查询
     *
     * @param puid
     * @return
     */
    public HzFeatureValue doSelectOneByPuid(String puid) {
        HzFeatureValue record = new HzFeatureValue();
        record.setPuid(puid);
        record.setWhichTable("HZ_CFG0_RECORD");
        return hzFeatureValueDao.selectByPrimaryKey(record);
    }


    /**
     * 更新单条特性值数据
     *
     * @param record
     * @return
     */
    public boolean doUpdate(HzFeatureValue record) {
        return hzFeatureValueDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    /**
     * 批量更新
     *
     * @param record
     * @return
     */
    public boolean doUpdateByBatch(Map<String, Object> record) {
        return hzFeatureValueDao.setIsSent(record) > 0 ? true : false;
    }


    public boolean doDeleteByPuid(HzFeatureValue record) {
        return false;
    }


    public boolean doDeleteCfgByList(List<HzFeatureValue> records) {
        return hzFeatureValueDao.deleteCfgByList(records) > 0 ? true : false;
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
        List<HzFeatureValue> records = null;
        List<HzFeatureValue> needToUpdate = new ArrayList<>();
        if ("HZ_CFG0_RECORD".equals(_table)) {
            records = selectFeatureListByProjectId(projectPuid, new HzFeatureQuery());
        }
//        else if ("HZ_CFG0_ADD_CFG_RECORD".equals(_table)) {
//            records = doLoadAddedCfgListByProjectPuid(projectId);
//        }
        for (HzFeatureValue record : records) {
            HzRelevanceBean bean = new HzRelevanceBean();
            bean.set_table(_table);
            bean.setIndex(++index);
            bean.setPuid(record.getPuid());
            bean.setRelevance(record.getpCfg0FamilyName() + "-" + record.getFeatureValueCode());
            bean.setRelevanceDesc((record.getpCfg0FamilyDesc() == null ? "" : record.getpCfg0FamilyDesc()) + "-" + (record.getpCfg0Desc() == null ? "" : record.getpCfg0Desc()));
            if (record.getpCfg0Relevance() == null) {
                record.setpCfg0Relevance("$ROOT." + record.getpCfg0FamilyName() + " = '" + record.getFeatureValueCode() + "'");
                needToUpdate.add(record);
            }
            bean.setRelevanceCode(record.getpCfg0Relevance());
            _list.add(bean);
        }
        updateByTableName(needToUpdate, _table);
        return index;
    }

    public List<HzFeatureValue> doLoadListByPuids(List<String> list) {
        Map<String, Object> _map = new HashMap<>();
        _map.put("whichTable", "HZ_CFG0_RECORD");
        _map.put("list", list);
        return hzFeatureValueDao.selectCfg0ListByPuids(_map);
    }

    public void updateByTableName(List<HzFeatureValue> list, String table) {
        switch (table) {
            case "HZ_CFG0_RECORD":
                list.forEach(_l -> hzFeatureValueDao.updateByPrimaryKey(_l));
                break;
//            case "HZ_CFG0_ADD_CFG_RECORD":
//                list.forEach(_l -> hzCfg0RecordDao.updateAddedCfgByPrimaryKey(_l));
//                break;
        }
    }

    public boolean preCheck(HzFeatureValue record) {
        //存在puid的可以同名更新
        if (StringHelper.checkString(record.getPuid())) {
            return true;
        }
        record.setWhichTable("HZ_CFG0_RECORD");
        List<HzFeatureValue> list;
        list = hzFeatureValueDao.selectByCodeAndDesc(record);
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
    public int count(HzFeatureQuery dto) {
        return hzFeatureValueDao.count(dto);
    }

    /**
     * 设置已经在流程中
     *
     * @param cfgs 特性值集合
     * @return
     */
    public int doSetToProcess(List<HzFeatureValue> cfgs) {
        Map<String, Object> params = new HashMap<>();
        params.put("cfgIsInProcess", "1");
        params.put("list", cfgs);
        return hzFeatureValueDao.setToProcess(params);
    }

    public List<HzFeatureValue> doLoadByCondition(HzFeatureQuery queryBase) {
        return hzFeatureValueDao.selectByCondition(queryBase);
    }

    /**
     * 用HZCSYS+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    public HzFeatureValue doSelectByCodeAndDescWithMainItem(HzFeatureValue record) {
        record.setWhichTable("HZ_CFG0_RECORD");
        return hzFeatureValueDao.selectByCodeAndDescWithMainItem(record);
    }

    /**
     * 用"车身颜色"+特性值（从颜色中来）查找一条车身颜色特性值
     *
     * @return
     */
    public HzFeatureValue doSelectByCodeAndCnDescWithMainItem(HzFeatureValue record) {
        record.setWhichTable("HZ_CFG0_RECORD");
        return hzFeatureValueDao.selectByCodeAndCnDescWithMainItem(record);
    }

    public List<HzFeatureValue> doSelectByFamilyUidWithProject(String familyUid, String projectUid) {
        HzFeatureValue record = new HzFeatureValue();
        record.setpCfg0FamilyPuid(familyUid);
        record.setProjectPuid(projectUid);
        return hzFeatureValueDao.selectByFamilyUidWithProject(record);
    }

    /**
     * 进行配置字典联动的后续操作
     *
     * @param projectUid 项目UID
     */
    public void synDictionaryAfterOption(String projectUid) {
        HzMainConfig mainRecord = hzMainConfigService.selectByProjectId(projectUid);
        HzFeatureValue record = new HzFeatureValue();
        HzFeature family = new HzFeature();
        Set<String> setOfFamily = new HashSet<>();
        Date now = new Date();
        boolean isAllOver = true;
        //关键字段为空，则同步同步历史数据到配置字典中
        if (null == mainRecord.getFeatureSynDicFlag() || 0 == mainRecord.getFeatureSynDicFlag()) {
            Map<String, HzDictionaryLibrary> libraryMap = new HashMap<>();
            List<HzFeatureValue> records = selectFeatureListByProjectId(projectUid, new HzFeatureQuery());
//            List<HzFeature> families = hzCfg0OptionFamilyService.doSelectNameByMap(projectUid, null, false);
            //以特性值为基准，查找配置字典中的特性值数据，但是特性不匹配的话，则强制增加特性，并记录特性
            for (int i = 0; i < records.size(); i++) {
                HzFeatureValue local = records.get(i);
                HzDictionaryLibrary library = hzDictionaryLibraryDao.findDictionaryLibraryOrCode(local.getFeatureValueCode());
                if (library == null) {
                    //新插入
                    library = new HzDictionaryLibrary();
                    library.setPuid(UUIDHelper.generateUpperUid());
                    library.setEffectTime(now);
                    library.setFailureTime(DateStringHelper.forever());
                    library.setFamillyCode(local.getpCfg0FamilyName());
                    library.setFamillyCh(local.getpCfg0FamilyDesc());
                    library.setEigenValue(local.getFeatureValueCode());
                    library.setValueDescCh(local.getpCfg0Desc());
                    library.setValueDescEn(local.getpH9featureenname());
                    library.setNote("系统从历史数据回传");
                    if (hzDictionaryLibraryDao.insert(library) <= 0) {
                        log.error("自动同步历史特性值到配置字典失败");
                        isAllOver = false;
                        continue;
                    }
                }
                record.setPuid(records.get(i).getPuid());
                record.setCfgDicLibUid(library.getPuid());
                if (hzFeatureValueDao.updateByPrimaryKey(record) <= 0) {
                    log.error("更新特性值对应的配置字典外键值发生失败");
                    isAllOver = false;
                } else {
                    if (setOfFamily.contains(local.getpCfg0FamilyPuid())) {
                        continue;
                    }
                    family.setId(local.getpCfg0FamilyPuid());
                    if (hzCfg0OptionFamilyService.doGetById(family) != null) {
                        family.setDicLibUid(library.getPuid());
                        if (!hzCfg0OptionFamilyService.doUpdateByPrimaryKeySelective(family)) {
                            log.error("更新特性与配置字典外键值发生失败");
                            isAllOver = false;
                        }
                        setOfFamily.add(local.getpCfg0FamilyPuid());
                    }
                }
            }
            if (isAllOver) {
                HzMainConfig ma = new HzMainConfig();
                ma.setId(mainRecord.getId());
                ma.setFeatureSynDicFlag(1);
                if (!hzMainConfigService.doUpdateByPrimaryKeySelective(ma)) {
                    log.error("更新主配置的特性值历史数据更新标签失败");
                }
            }
        }
    }

    public int doupdateList(List<HzFeatureValue> hzFeatureValueList) {
        return hzFeatureValueDao.updateList(hzFeatureValueList);
    }

    public List<HzFeatureValue> doSelectByDescAndProjectId(HzFeatureValue cfg) {
        return hzFeatureValueDao.doSelectByDescAndProjectId(cfg);
    }

    /**
     * 加载特性值数据
     * @param projectPuid 项目id
     * @param queryBase 分页查询
     * @return
     */
    public Map<String,Object> loadFeature(String projectPuid, HzFeatureQuery queryBase) {
        Map<String, Object> result = new HashMap<>();
        queryBase.setSort(HzFeatureValue.reflectToDBField(queryBase.getSort()));
        queryBase.setProjectUid(projectPuid);
        int totalCount = count(queryBase);
        if("ALL".equals(queryBase.getLimit())){
            queryBase.setLimit(String.valueOf(totalCount));
        }
        List<HzFeatureValue> records = selectFeatureListByProjectId(projectPuid, queryBase);
        result.put("totalCount", totalCount);
        result.put("result", records);
        synDictionaryAfterOption(projectPuid);
        return result;
    }

    @Override
    public int updateStatusByOrderId(Long orderId, int status) {
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("status", status);
        map.put("effectedDate", new Date());
        return hzFeatureValueDao.updateStatusByOrderId(map);
    }

    @Override
    public HzFeatureValue selectByPrimaryKey(String id) {
        return hzFeatureValueDao.selectByPrimaryKey(id);
    }
}
