/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.relevance;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import cn.net.connor.hozon.dao.dao.configuration.relevance.HzRelevanceBasicChangeDao;
import cn.net.connor.hozon.dao.dao.configuration.relevance.HzRelevanceBasicDao;
import cn.net.connor.hozon.dao.dao.configuration.relevance.HzRelevanceRelationDao;
import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDto;
import cn.net.connor.hozon.dao.query.relevance.HzRelevanceQuery;
import cn.net.connor.hozon.dao.query.relevance.HzRelevanceQueryResult;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.modelColor.HzColorModel2;
import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasic;
import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasicChange;
import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceRelation;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.change.HzChangeOrderRecord;

import java.util.*;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in  2018/5/30 14:15
 * @Modified By:
 */
@Service("hzRelevanceService2")
public class HzRelevanceService2 {

    //特性服务
    @Autowired
    private HzCfg0Service hzCfg0Service;
    //配色方案服务
    @Autowired
    private HzColorModelDao hzColorModelDao;
    //相关性主表Dao
    @Autowired
    private HzRelevanceBasicDao hzRelevanceBasicDao;
    /**
     * 相关性关系表Dao
     */
    @Autowired
    private HzRelevanceRelationDao hzRelevanceRelationDao;
    /**
     * 相关性基础信息daoceng
     */
    @Autowired
    private HzRelevanceBasicChangeDao hzRelevanceBasicChangeDao;
    /**
     * 变更数据dao层
     */
    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;
    /**
     * 变更号记录dao层
     */
    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    /**
     * 过滤特性为“车身颜色/HZCSYS”和特性为“内外饰/HZNWS”
     *
     * @param projectPuid
     * @return
     */
    public JSONObject addRelevance(String projectPuid) {
        JSONObject response = new JSONObject();
        JSONArray datas = new JSONArray();
        Long index = 1L;

        //查询项目中的相关性
        List<HzRelevanceBasic> hzRelevanceBasicsOld = hzRelevanceBasicDao.selectByProjectPuid(projectPuid);
        Map<String, HzRelevanceBasic> hzRelevanceBasicsOldMap = new HashMap<>();
        for (HzRelevanceBasic hzRelevanceBasic : hzRelevanceBasicsOld) {
            hzRelevanceBasicsOldMap.put(hzRelevanceBasic.getRbRelevance(), hzRelevanceBasic);
        }

        //相关性list
        List<HzRelevanceBasic> hzRelevanceBasics = new ArrayList<HzRelevanceBasic>();

        //搜索全部特性值，并经过P_CFG0_OBJECT_ID 升序排序
        QueryBase queryBase = new QueryBase();
        queryBase.setSort("P_CFG0_OBJECT_ID");
        List<HzCfg0Record> hzCfg0Records = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, new HzFeatureQueryDto());

        //查询该项目下所有配色方案
        List<HzColorModel2> hzColorModel2s = hzColorModelDao.selectByProjectPuid(projectPuid);

        //新生成的相关性
        List<HzRelevanceBasic> hzRelevanceBasicsNew = new ArrayList<>();
        //遍历特性值
        for (HzCfg0Record hzCfg0Record : hzCfg0Records) {
            //车身颜色不参与相关性
            if ((!"HZCSYS".equals(hzCfg0Record.getpCfg0FamilyName())) && (!"HZNSYS".equals(hzCfg0Record.getpCfg0FamilyName()))) {
                //将同一特性的配色方案分组
                List<HzColorModel2> hzColorModel2List = new ArrayList<HzColorModel2>();
                for (HzColorModel2 hzColorModel2 : hzColorModel2s) {
                    if (hzCfg0Record.getpCfg0FamilyName().equals(hzColorModel2.getpOptionfamilyName())) {
                        hzColorModel2List.add(hzColorModel2);
                    }
                }
                //将同特性下不同颜色的配色方案放入Map
                Map<String, List<HzColorModel2>> colorMap = new HashMap<String, List<HzColorModel2>>();
                for (HzColorModel2 hzColorModel2 : hzColorModel2List) {
//                    if ("-".equals(hzColorModel2.getColorCode())) {
//                        continue;
//                    }
                    String colorcode = hzColorModel2.getColorCode();
                    if (colorMap.get(colorcode) == null) {
                        colorMap.put(colorcode, new ArrayList<HzColorModel2>());
                    }
                    if (!("-".equals(colorcode) && colorMap.get(colorcode).size() >= 1)) {
                        List<HzColorModel2> hzColorModel2List1 = colorMap.get(colorcode);
                        hzColorModel2List1.add(hzColorModel2);
                    }
                }

                Set<String> keys = colorMap.keySet();
                //生成相关性
                for (String key : keys) {
                    //拼接相关性
                    String relevance = "";
                    String relevanceDesc = "";
                    String relevanceCode = "$ROOT." + hzCfg0Record.getpCfg0FamilyName() + " = '" + hzCfg0Record.getpCfg0ObjectId() + "'";
                    if ("-".equals(key)) {
                        //拼接相关性
                        relevance = hzCfg0Record.getpCfg0FamilyName() + "-" + hzCfg0Record.getpCfg0ObjectId();
                        //拼接相关性描述
                        relevanceDesc = hzCfg0Record.getpCfg0FamilyDesc() + "-" + hzCfg0Record.getpCfg0Desc();
                    } else {
                        //拼接相关性
                        relevance = hzCfg0Record.getpCfg0FamilyName() + "-" + hzCfg0Record.getpCfg0ObjectId() + "-" + key;
                        //拼接相关性描述
                        relevanceDesc = hzCfg0Record.getpCfg0FamilyDesc() + "-" + hzCfg0Record.getpCfg0Desc() + "-" + colorMap.get(key).get(0).getColorName();
                        //拼接相关性代码
                        relevanceCode += "' AND ";
                        List<HzColorModel2> hzColorModel2s1 = colorMap.get(key);
                        int size = hzColorModel2s1.size();
                        if (size == 1) {
                            relevanceCode += "$ROOT.HZCSYS = '" + key + "'";
                        } else if (size > 1) {
                            relevanceCode += "( ";
                            for (int i = 0; i < size; i++) {
                                HzColorModel2 hzColorModel2 = hzColorModel2s1.get(i);
                                relevanceCode = relevanceCode + "$ROOT.HZCSYS = '" + hzColorModel2.getpModelShell() + "' ";
                                if (size - i > 1) {
                                    relevanceCode += "OR ";
                                }
                            }
                            relevanceCode += " )";
                        }
                    }


                    /**
                     *后端添加
                     * 1.添加相关性主表
                     * 2.添加相关性关联表
                     */

                    //添加相关性主表
                    HzRelevanceBasic hzRelevanceBasic = new HzRelevanceBasic();
                    //颜色code
                    hzRelevanceBasic.setRbColorCode(key);
                    //颜色id
                    hzRelevanceBasic.setRbColorUid(colorMap.get(key).get(0).getpColorUid());
                    //特性code
                    hzRelevanceBasic.setRbFeatureCode(hzCfg0Record.getpCfg0FamilyName());
                    //特性id
                    hzRelevanceBasic.setRbFeatureUid(hzCfg0Record.getpCfg0FamilyPuid());
                    //特性值code
                    hzRelevanceBasic.setRbFeatureValueCode(hzCfg0Record.getpCfg0ObjectId());
                    //特性值id
                    hzRelevanceBasic.setRbFeatureValueUid(hzCfg0Record.getPuid());
                    //相关性
                    hzRelevanceBasic.setRbRelevance(relevance);
                    //相关性描述
                    hzRelevanceBasic.setRbRelevanceDesc(relevanceDesc);
                    //相关性代码
                    hzRelevanceBasic.setRbRelevanceCode(relevanceCode);
                    //项目代码
                    hzRelevanceBasic.setRbProjectUid(projectPuid);
                    //状态
                    hzRelevanceBasic.setRelevanceStatus(0);

                    Long relevanceUid;
                    //判断原相关性是否存在新生成的相关性，有则修改，无则新增
                    HzRelevanceBasic hzRelevanceBasic1Old = hzRelevanceBasicsOldMap.get(relevance);
                    if (hzRelevanceBasic1Old != null) {
                        //判断新生成的相关性与源相关性是否一模一样，一样则不修改
                        if (hzRelevanceBasic1Old.getRbRelevanceDesc().equals(relevanceDesc) && hzRelevanceBasic1Old.getRbRelevanceCode().equals(relevanceCode)) {
                            //判断一模一样的数据是否为删除状态，如果是则改为已生效状态
                            if (hzRelevanceBasic1Old.getRelevanceStatus() == 2) {
                                hzRelevanceBasic1Old.setRelevanceStatus(1);
                                hzRelevanceBasicDao.updateByPrimaryKey(hzRelevanceBasic1Old);
                            }
                            hzRelevanceBasicsNew.add(hzRelevanceBasic);
                            continue;
                        }
                        hzRelevanceBasic.setId(hzRelevanceBasicsOldMap.get(relevance).getId());
                        hzRelevanceBasicDao.updateByPrimaryKey(hzRelevanceBasic);
                    } else {
                        //向相关性主表增加数据
                        relevanceUid = hzRelevanceBasicDao.insertBasic(hzRelevanceBasic);
                        //添加相关性关联表
                        List<HzColorModel2> hzColorModel2List1 = colorMap.get(key);
                        for (HzColorModel2 hzColorModel2 : hzColorModel2List1) {
                            HzRelevanceRelation hzRelevanceRelation = new HzRelevanceRelation();
                            //特性id
                            hzRelevanceRelation.setRrCfgFamilyUid(hzCfg0Record.getpCfg0FamilyPuid());
                            //特性值id
                            hzRelevanceRelation.setRrCfg0Uid(hzCfg0Record.getPuid());
                            //颜色id
                            hzRelevanceRelation.setRrColorUid(hzColorModel2.getpColorUid());
                            //相关性id
                            hzRelevanceRelation.setRrRelevanceId(relevanceUid);
                            //配色方案的id
                            hzRelevanceRelation.setRrColorModelUid(hzColorModel2.getModelUid());
                            hzRelevanceRelationDao.insertOne(hzRelevanceRelation);
                        }
                    }
                    hzRelevanceBasicsNew.add(hzRelevanceBasic);


                    //前端显示
                    JSONObject data = new JSONObject();
                    data.put("index", index);
                    index++;
                    data.put("relevance", relevance);
                    data.put("relevanceDesc", relevanceDesc);
                    data.put("relevanceCode", relevanceCode);
                    datas.add(data);
                }
            }
        }

        //对比新旧相关性，将未发送至sap的数据直接删除，发送过的改为删除状态
        Iterator<HzRelevanceBasic> hzRelevanceBasicIterator = hzRelevanceBasicsOld.iterator();
        while (hzRelevanceBasicIterator.hasNext()) {
            HzRelevanceBasic hzRelevanceBasicOld = hzRelevanceBasicIterator.next();
            for (HzRelevanceBasic hzRelevanceBasicNew : hzRelevanceBasicsNew) {
                if (hzRelevanceBasicNew.getRbRelevance().equals(hzRelevanceBasicOld.getRbRelevance())) {
                    hzRelevanceBasicIterator.remove();
                    ;
                    break;
                }
            }
        }
        List<HzRelevanceBasic> hzRelevanceBasicsDelete = new ArrayList<>();
        List<HzRelevanceBasic> hzRelevanceBasicsUpdate = new ArrayList<>();
        for (HzRelevanceBasic hzRelevanceBasic : hzRelevanceBasicsOld) {
            if (hzRelevanceBasic.getIsSent() == null || hzRelevanceBasic.getIsSent() == 0) {
                hzRelevanceBasicsDelete.add(hzRelevanceBasic);
            } else {
                hzRelevanceBasic.setRelevanceStatus(2);
                hzRelevanceBasicsUpdate.add(hzRelevanceBasic);
            }
        }

        if (hzRelevanceBasicsDelete != null && hzRelevanceBasicsDelete.size() > 0) {
            hzRelevanceBasicDao.deleteByPrimaryKeyList(hzRelevanceBasicsDelete);
        }

        if (hzRelevanceBasicsUpdate != null && hzRelevanceBasicsUpdate.size() > 0) {
            hzRelevanceBasicDao.updateStatusList(hzRelevanceBasicsUpdate);
        }

        response.put("totalCount", index - 1);
        response.put("result", datas);
        return response;
    }
//    public JSONObject addRelevance(String projectPuid) {
//        JSONObject response = new JSONObject();
//        JSONArray datas = new JSONArray();
//        Long index = 1L;
//
//        //查询项目中的相关性
//        List<HzRelevanceBasic> hzRelevanceBasicsOld = hzRelevanceBasicDao.selectByProjectPuid(projectPuid);
//        Map<String,HzRelevanceBasic> hzRelevanceBasicsOldMap = new HashMap<>();
//        for(HzRelevanceBasic hzRelevanceBasic : hzRelevanceBasicsOld){
//            hzRelevanceBasicsOldMap.put(hzRelevanceBasic.getRbRelevance(),hzRelevanceBasic);
//        }
//
//
//        //查看相关性主表和相关性关联表是否有数据，有则删除
//        hzRelevanceBasicDao.deleteByProjectUid(projectPuid);
//
//        //相关性list
//        List<HzRelevanceBasic> hzRelevanceBasics = new ArrayList<HzRelevanceBasic>();
//
//        //搜索全部特性值，并经过P_CFG0_OBJECT_ID 升序排序
//        QueryBase queryBase = new QueryBase();
//        queryBase.setSort("P_CFG0_OBJECT_ID");
//         List<HzCfg0Record> hzCfg0Records = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, new HzFeatureQueryDto());
//
//        //查询该项目下所有配色方案
//        List<HzColorModel2> hzColorModel2s = hzColorModelDao.selectByProjectPuid(projectPuid);
//
//        //遍历特性值
//        for (HzCfg0Record hzCfg0Record : hzCfg0Records) {
//            //车身颜色不参与相关性
//            if ((!"HZCSYS".equals(hzCfg0Record.getpCfg0FamilyName()))&&(!"HZNSYS".equals(hzCfg0Record.getpCfg0FamilyName()))){
//                //将同一特性的配色方案分组
//                List<HzColorModel2> hzColorModel2List = new ArrayList<HzColorModel2>();
//                for (HzColorModel2 hzColorModel2 : hzColorModel2s) {
//                    if (hzCfg0Record.getpCfg0FamilyName().equals(hzColorModel2.getpOptionfamilyName())) {
//                        hzColorModel2List.add(hzColorModel2);
//                    }
//                }
//                //将同特性下不同颜色的配色方案放入Map
//                Map<String, List<HzColorModel2>> colorMap = new HashMap<String, List<HzColorModel2>>();
//                for (HzColorModel2 hzColorModel2 : hzColorModel2List) {
////                    if ("-".equals(hzColorModel2.getColorCode())) {
////                        continue;
////                    }
//                    String colorcode = hzColorModel2.getColorCode();
//                    if (colorMap.get(colorcode) == null) {
//                        colorMap.put(colorcode, new ArrayList<HzColorModel2>());
//                    }
//                    if(!("-".equals(colorcode)&&colorMap.get(colorcode).size()>=1)){
//                        List<HzColorModel2> hzColorModel2List1 = colorMap.get(colorcode);
//                        hzColorModel2List1.add(hzColorModel2);
//                    }
//                }
//
//                Set<String> keys = colorMap.keySet();
//                //生成相关性
//                for (String key : keys) {
//                    //拼接相关性
//                    String relevance = "";
//                    String relevanceDesc = "";
//                    String relevanceCode = "$ROOT." + hzCfg0Record.getpCfg0FamilyName() + " = '" + hzCfg0Record.getpCfg0ObjectId()+"'";
//                    if("-".equals(key)){
//                        //拼接相关性
//                        relevance = hzCfg0Record.getpCfg0FamilyName() + "-" + hzCfg0Record.getpCfg0ObjectId();
//                        //拼接相关性描述
//                        relevanceDesc = hzCfg0Record.getpCfg0FamilyDesc() + "-" +hzCfg0Record.getpCfg0Desc();
//                    }else {
//                        //拼接相关性
//                        relevance = hzCfg0Record.getpCfg0FamilyName() + "-" + hzCfg0Record.getpCfg0ObjectId() + "-" + key;
//                        //拼接相关性描述
//                        relevanceDesc = hzCfg0Record.getpCfg0FamilyDesc() + "-" +hzCfg0Record.getpCfg0Desc()+ "-" + colorMap.get(key).get(0).getColorName();
//                        //拼接相关性代码
//                        relevanceCode+="' AND ";
//                        List<HzColorModel2> hzColorModel2s1 = colorMap.get(key);
//                        int size = hzColorModel2s1.size();
//                        if (size == 1) {
//                            relevanceCode += "$ROOT.HZCSYS = '" + key + "'";
//                        } else if (size > 1) {
//                            relevanceCode += "( ";
//                            for (int i = 0; i < size; i++) {
//                                HzColorModel2 hzColorModel2 = hzColorModel2s1.get(i);
//                                relevanceCode = relevanceCode + "$ROOT.HZCSYS = '" + hzColorModel2.getpModelShell() + "' ";
//                                if (size - i > 1) {
//                                    relevanceCode += "OR ";
//                                }
//                            }
//                            relevanceCode += " )";
//                        }
//                    }
//
//
//
//                    /**
//                     *后端添加
//                     * 1.添加相关性主表
//                     * 2.添加相关性关联表
//                     */
//
//                    //添加相关性主表
//                    HzRelevanceBasic hzRelevanceBasic = new HzRelevanceBasic();
//                    //颜色code
//                    hzRelevanceBasic.setRbColorCode(key);
//                    //颜色id
//                    hzRelevanceBasic.setRbColorUid(colorMap.get(key).get(0).getpColorUid());
//                    //特性code
//                    hzRelevanceBasic.setRbFeatureCode(hzCfg0Record.getpCfg0FamilyName());
//                    //特性id
//                    hzRelevanceBasic.setRbFeatureUid(hzCfg0Record.getpCfg0FamilyPuid());
//                    //特性值code
//                    hzRelevanceBasic.setRbFeatureValueCode(hzCfg0Record.getpCfg0ObjectId());
//                    //特性值id
//                    hzRelevanceBasic.setRbFeatureValueUid(hzCfg0Record.getPuid());
//                    //相关性
//                    hzRelevanceBasic.setRbRelevance(relevance);
//                    //相关性描述
//                    hzRelevanceBasic.setRbRelevanceDesc(relevanceDesc);
//                    //相关性代码
//                    hzRelevanceBasic.setRbRelevanceCode(relevanceCode);
//                    //项目代码
//                    hzRelevanceBasic.setRbProjectUid(projectPuid);
//                    //状态
//                    hzRelevanceBasic.setRelevanceStatus(0);
//
//                    //是否发送至sap
//                    if(hzRelevanceBasicsOldMap.get(relevance)!=null){
//                        hzRelevanceBasic.setIsSent(hzRelevanceBasicsOldMap.get(relevance).getIsSent());
//                    }
//
//                    //向相关性主表增加数据
//                    Long relevanceUid = hzRelevanceBasicDao.insertBasic(hzRelevanceBasic);
//
//
//                    //添加相关性关联表
//                    List<HzColorModel2> hzColorModel2List1 = colorMap.get(key);
//                    for (HzColorModel2 hzColorModel2 : hzColorModel2List1) {
//                        HzRelevanceRelation hzRelevanceRelation = new HzRelevanceRelation();
//                        //特性id
//                        hzRelevanceRelation.setRrCfgFamilyUid(hzCfg0Record.getpCfg0FamilyPuid());
//                        //特性值id
//                        hzRelevanceRelation.setRrCfg0Uid(hzCfg0Record.getPuid());
//                        //颜色id
//                        hzRelevanceRelation.setRrColorUid(hzColorModel2.getpColorUid());
//                        //相关性id
//                        hzRelevanceRelation.setRrRelevanceId(relevanceUid);
//                        //配色方案的id
//                        hzRelevanceRelation.setRrColorModelUid(hzColorModel2.getModelUid());
//                        hzRelevanceRelationDao.insertOne(hzRelevanceRelation);
//                    }
//
//
//                    //前端显示
//                    JSONObject data = new JSONObject();
//                    data.put("index", index);
//                    index++;
//                    data.put("relevance", relevance);
//                    data.put("relevanceDesc", relevanceDesc);
//                    data.put("relevanceCode", relevanceCode);
//                    datas.add(data);
//                }
//            }
//        }
//        response.put("totalCount", index - 1);
//        response.put("result", datas);
//        return response;
//    }

    /**
     * 查询相关性
     *
     * @param dto
     * @return
     */
    public Map<String, Object> queryRelevance(HzRelevanceQuery dto) {
        Map<String, Object> result = new HashMap<>();
        dto.setSort(HzRelevanceQuery.relectSortToDB(dto));
        Integer totalCount = hzRelevanceBasicDao.tellMeHowManyOfIt(dto);
        if ("ALL".equals(dto.getLimit())) {
            dto.setLimit(String.valueOf(totalCount));
        }
        List<HzRelevanceQueryResult> beans = hzRelevanceBasicDao.selectByPage(dto);
        result.put("result", beans);
        result.put("totalCount", totalCount);
        return result;
    }

    //发起变更流程
    public JSONObject getChange(Long changeFromId, String projectPuid) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        result.put("msg", "发起流程成功");


        List<HzRelevanceBasic> hzRelevanceBasics = hzRelevanceBasicDao.selectByProjectPuid(projectPuid);
        if (hzRelevanceBasics == null || hzRelevanceBasics.size() <= 0) {
            result.put("status", false);
            result.put("msg", "该项目下不存在相关性，请生成相关性后再发起流程");
            return result;
        }
        //根据项目查询该项目下的相关性最大版本
        List<HzRelevanceBasicChange> hzRelevanceBasicChanges = new ArrayList<>();
        for (HzRelevanceBasic hzRelevanceBasic : hzRelevanceBasics) {
            if (hzRelevanceBasic.getRelevanceStatus() == 1) {
                continue;
            }
            HzRelevanceBasicChange hzRelevanceBasicChange = new HzRelevanceBasicChange(hzRelevanceBasic);
            HzRelevanceBasicChange maxVersion = hzRelevanceBasicChangeDao.selectMaxVersion(hzRelevanceBasicChange);
//            if(hzRelevanceBasic.getRelevanceStatus()==0){
//                continue;
//            }
            if (maxVersion == null) {
                hzRelevanceBasicChange.setChangeVersion(0);
            } else {
                hzRelevanceBasicChange.setChangeVersion(maxVersion.getChangeVersion() + 1);
            }
            hzRelevanceBasicChange.setChangeOrderId(changeFromId);
            hzRelevanceBasicChanges.add(hzRelevanceBasicChange);
        }

        if (hzRelevanceBasicChanges != null && hzRelevanceBasicChanges.size() > 0) {
            int insertNum = hzRelevanceBasicChangeDao.insertList(hzRelevanceBasicChanges);
            if (insertNum <= 0) {
                result.put("status", false);
                result.put("msg", "新增变更数据失败");
                return result;
            }
        } else {
            result.put("status", false);
            result.put("msg", "不存在变更数据");
            return result;
        }
        HzRelevanceBasic hzRelevanceBasicUpdate = new HzRelevanceBasic();
        hzRelevanceBasicUpdate.setRbProjectUid(projectPuid);
        hzRelevanceBasicUpdate.setRelevanceStatus(10);
        if (hzRelevanceBasicDao.updateStatus(hzRelevanceBasicUpdate) <= 0 ? true : false) {
            result.put("status", false);
            result.put("msg", "修改源数据状态失败");
            return result;
        }

        //为关系表新增数据
        HzChangeDataRecord hzChangeDataRecord = new HzChangeDataRecord();
        hzChangeDataRecord.setOrderId(changeFromId);
        hzChangeDataRecord.setTableName(ChangeTableNameEnum.HZ_RELEVANCE_BASIC_CHANGE.getTableName());
        hzChangeDataRecord.setApplicantId(Long.valueOf(UserInfo.getUser().getId()));
        int insertNum = hzChangeDataRecordDAO.insert(hzChangeDataRecord);
        if (insertNum <= 0) {
            result.put("status", false);
            result.put("msg", "绑定人员失败");
            return result;
        }
        return result;
    }

    /**
     * 撤销
     *
     * @param projectPuid
     * @return
     */
    public JSONObject goBackData(String projectPuid) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        result.put("msg", "撤销成功");
        //查询草稿状态的原数据
        List<HzRelevanceBasic> hzRelevanceBasicList = hzRelevanceBasicDao.selectByProjectPuidAndStatus(projectPuid);
        //删除草稿状态原数据
        if (hzRelevanceBasicDao.deleteByProjectUid(projectPuid) <= 0 ? true : false) {
            result.put("status", false);
            result.put("msg", "删除草稿数据失败");
            return result;
        }

        //查找最近一次变更数据
//        List<HzRelevanceBasicChange> hzRelevanceBasicChanges = hzRelevanceBasicChangeDao.selectLastexecutedByProjectId(projectPuid);
        List<HzRelevanceBasicChange> hzRelevanceBasicChanges = new ArrayList<>();
        for (HzRelevanceBasic hzRelevanceBasic : hzRelevanceBasicList) {
            HzRelevanceBasicChange hzRelevanceBasicChange = hzRelevanceBasicChangeDao.selectByLatestBySrc(hzRelevanceBasic.getId());
            if (hzRelevanceBasicChange != null) {
                hzRelevanceBasicChanges.add(hzRelevanceBasicChange);
            }
        }

        if (hzRelevanceBasicChanges != null && hzRelevanceBasicChanges.size() > 0) {
            List<HzRelevanceBasic> hzRelevanceBasics = new ArrayList<>();
            for (HzRelevanceBasicChange hzRelevanceBasicChange : hzRelevanceBasicChanges) {
                HzRelevanceBasic hzRelevanceBasic = new HzRelevanceBasic(hzRelevanceBasicChange,UserInfo.getUser().getLogin());
                hzRelevanceBasic.setRelevanceStatus(1);
                hzRelevanceBasics.add(hzRelevanceBasic);
            }
            if (hzRelevanceBasicDao.insertByBatch(hzRelevanceBasics) <= 0 ? true : false) {
                result.put("status", false);
                result.put("msg", "撤销变更数据失败");
                return result;
            }
        }

        return result;
    }

    public void getChangePageModel(String projectUid, Model model) {
        List<HzChangeOrderRecord> hzChangeOrderRecordList = hzChangeOrderDAO.findHzChangeOrderRecordByProjectId(projectUid);
        model.addAttribute("changeFroms", hzChangeOrderRecordList);
    }
}
