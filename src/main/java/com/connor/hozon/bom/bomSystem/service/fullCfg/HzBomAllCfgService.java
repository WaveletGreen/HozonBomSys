/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.fullCfg;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.bomSystem.dao.model.HzCfg0ModelDetailDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgModelDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgWithCfgDao;
import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDTO;
import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.bomSystem.helper.ProjectHelper;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.bom.HzBomDataService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.main.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.model.HzCfg0ModelRecordService;
import com.connor.hozon.bom.bomSystem.service.project.HzBrandService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import com.connor.hozon.bom.bomSystem.service.project.HzVehicleService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.fullCfg.HzFullCfgMain;
import sql.pojo.cfg.fullCfg.HzFullCfgModel;
import sql.pojo.cfg.fullCfg.HzFullCfgWithCfg;
import sql.pojo.cfg.main.HzCfg0MainRecord;
import sql.pojo.cfg.model.HzCfg0ModelDetail;
import sql.pojo.cfg.model.HzCfg0ModelRecord;
import sql.pojo.project.HzBrandRecord;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;
import sql.pojo.project.HzVehicleRecord;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

@Service("hzBomAllCfgService")
public class HzBomAllCfgService {

    @Autowired
    private HzCfg0Service hzCfg0Service;
    @Autowired
    private HzCfg0ModelService hzCfg0ModelService;
    @Autowired
    private HzFullCfgMainDao hzFullCfgMainDao;
    @Autowired
    private HzFullCfgModelDao hzFullCfgModelDao;
    @Autowired
    private HzFullCfgWithCfgDao hzFullCfgWithCfgDao;


    @Autowired
    private HzProjectLibsService hzProjectLibsService;
    @Autowired
    private HzVehicleService hzVehicleService;
    @Autowired
    private HzPlatformService hzPlatformService;
    @Autowired
    private HzBrandService hzBrandService;

    @Autowired
    private HzCfg0ModelDetailDao hzCfg0ModelDetailDao;

    /**
     * 车型模型服务层
     */
    @Autowired
    HzCfg0ModelRecordService hzCfg0ModelRecordService;
    //2Y层
    @Autowired
    HzBomDataService hzBomDataService;
    /**
     * 主配置
     */
    @Autowired
    HzCfg0MainService hzCfg0MainService;
    @Autowired
    ProjectHelper projectHelper;
    private static final String[] selfDesc =
            {
                    "operationType"/*操作类型*/,
                    "pBomOfWhichDept"/*系统，即所属部门*/,
                    "pBomLineId"/*总成零件号*/,
                    "pBomLineName"/*总成零件名称*/,
                    "pH9featureenname"/*总成英文名称*/,
                    "owningUser"/*责任工程师*/,
                    "pCfg0Desc"/*配置描述*/,
                    "pCfg0ObjectId"/*配置代码*/,
                    "isColorPart"/*是否颜色件*/,
                    "comment"/*备注*/
            };

    private static Logger logger = LoggerFactory.getLogger(HzBomAllCfgService.class);

    /**
     * @param projectPuid 项目puid
     * @return net.sf.json.JSONObject
     * Description: 根据数模层获取到bom的配置信息和车型模型信息
     */
    public JSONObject parse(String projectPuid) {
        //搜索全部特性，并经过P_CFG0_OBJECT_ID 升序排序
        HzFeatureQueryDTO queryBase = new HzFeatureQueryDTO();
        queryBase.setSort("P_CFG0_OBJECT_ID");
//        QueryBase queryBase = new QueryBase();
//        queryBase.setSort("P_CFG0_OBJECT_ID");
        List<HzCfg0Record> hzCfg0Records = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, queryBase);
        //获取该项目下的所有车型模型
        List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(projectPuid);
        //搜素所属有2Y层
        List<HzBomLineRecord> lines = hzBomDataService.doSelect2YByProjectPuid(projectPuid);
        if (lines == null) {
            lines = new ArrayList<HzBomLineRecord>();
        }
        initLoad(projectPuid, hzCfg0ModelRecords, lines);

        HzProjectLibs project = hzProjectLibsService.doLoadProjectLibsById(projectPuid);
        HzVehicleRecord vehicle = hzVehicleService.doGetByPuid(project.getpProjectPertainToVehicle());
        HzPlatformRecord platform = hzPlatformService.doGetByPuid(vehicle.getpVehiclePertainToPlatform());
        HzBrandRecord brand = hzBrandService.doGetByPuid(platform.getpPertainToBrandPuid());

        JSONObject respond = new JSONObject();
        JSONArray _data = new JSONArray();
        JSONArray _model = new JSONArray();

        Set<String> optionName = new HashSet<>();
        Map<String, Map<String, HzFullCfgModel>> mapToModel = new HashMap<String, Map<String, HzFullCfgModel>>();
        Map<String, HzFullCfgWithCfg> modelWithBomLineMap = new HashMap();
        Map<String, List<HzFullCfgModel>> mapModelHasCfg0 = new HashMap<>();

        HzFullCfgMain hzFullCfgMain = hzFullCfgMainDao.selectByProjectId(projectPuid);
        //model数据
        List<HzFullCfgModel> hzFullCfgModels = hzFullCfgModelDao.selectByMainPuid(hzFullCfgMain.getId());
        //withcfg数据
        List<HzFullCfgWithCfg> hzFullCfgWithCfgs = hzFullCfgWithCfgDao.selectByMainID(hzFullCfgMain.getId());


        List<HzCfg0ModelDetail> hzCfg0ModelDetailList = new ArrayList<HzCfg0ModelDetail>();
        for (HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecords) {
            HzCfg0ModelDetail hzCfg0ModelDetail = new HzCfg0ModelDetail();
            hzCfg0ModelDetail.setpModelPuid(hzCfg0ModelRecord.getPuid());
            hzCfg0ModelDetailList.add(hzCfg0ModelDetail);
        }
        //为detail赋值
        List<HzCfg0ModelDetail> hzCfg0ModelDetails = null;
        if (hzCfg0ModelDetailList.size() != 0) {
            hzCfg0ModelDetails = hzCfg0ModelDetailDao.selectByModelIds(hzCfg0ModelDetailList);
        }

        //为特性Map赋值
        for (HzFullCfgWithCfg hzFullCfgWithCfg : hzFullCfgWithCfgs) {
            modelWithBomLineMap.put(hzFullCfgWithCfg.getCfgCfg0Uid(), hzFullCfgWithCfg);
        }
        //为车辆模型Map赋值
        for (HzFullCfgModel hzFullCfgModel : hzFullCfgModels) {
            if (mapToModel.get(hzFullCfgModel.getModModelUid()) == null) {
                Map<String, HzFullCfgModel> mapToModel_ = new HashMap<String, HzFullCfgModel>();
                mapToModel.put(hzFullCfgModel.getModModelUid(), mapToModel_);
            }
            mapToModel.get(hzFullCfgModel.getModModelUid()).put(hzFullCfgModel.getModCfg0Uid(), hzFullCfgModel);
        }

//        for(HzCfg0Record hzCfg0Record : hzCfg0Records){
//            JSONObject data = new JSONObject();
//
//            data.put(selfDesc[0], modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgBomlineName()==null?"":modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgBomlineName());
//            data.put(selfDesc[1], modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgBomlineName()==null?"":modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgBomlineName());
//            data.put(selfDesc[2], modelWithBomLineMap.get(hzCfg0Record.getPuid()).getCfgBomlineUid()==null?"":modelWithBomLineMap.get(hzCfg0Record.getPuid()).getCfgBomlineUid());
//            data.put(selfDesc[3], hzCfg0Record.getpCfg0FamilyName()==null?"":hzCfg0Record.getpCfg0FamilyName());
//            data.put(selfDesc[4], hzCfg0Record.getpH9featureenname()==null?"":hzCfg0Record.getpH9featureenname());
//            data.put(selfDesc[5], modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgCreator()==null?"":modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgCreator());
//            data.put(selfDesc[6], hzCfg0Record.getpCfg0ObjectId()==null?"":hzCfg0Record.getpCfg0ObjectId());
//            data.put(selfDesc[7], hzCfg0Record.getpCfg0ObjectId()==null?"":hzCfg0Record.getpCfg0ObjectId());
//            data.put(selfDesc[8], hzCfg0Record.getpCfg0Desc()==null?"":hzCfg0Record.getpCfg0Desc());
//            data.put("cfgPuid",hzCfg0Record.getPuid());
//            _data.add(data);
//        }
        for (HzBomLineRecord hzBomLineRecord : lines) {
            JSONObject data = new JSONObject();
            HzFullCfgWithCfg cfg = hzFullCfgWithCfgDao.selectByBomLineUidWithVersion(hzFullCfgMain.getId(), hzBomLineRecord.getPuid());
            data.put(selfDesc[0], cfg == null ? "" : cfg.getFlOperationType() == 1 ? "新增" : cfg.getFlOperationType() == 2 ? "更新" : cfg.getFlOperationType() == 0 ? "删除" : "新增");
            data.put(selfDesc[1], hzBomLineRecord.getpBomOfWhichDept() == null ? "" : hzBomLineRecord.getpBomOfWhichDept());
            data.put(selfDesc[2], hzBomLineRecord.getLineID() == null ? "" : hzBomLineRecord.getLineID());
            data.put(selfDesc[3], hzBomLineRecord.getpBomLinePartName() == null ? "" : hzBomLineRecord.getpBomLinePartName());
            data.put(selfDesc[4], hzBomLineRecord.getpBomLinePartEnName() == null ? "" : hzBomLineRecord.getpBomLinePartEnName());
            data.put(selfDesc[5], "");
            data.put(selfDesc[6], "");
            data.put(selfDesc[7], "");
            //颜色件
            data.put(selfDesc[8], (null == hzBomLineRecord.getColorPart() || hzBomLineRecord.getColorPart() == 0) ? "N" : "Y");
            data.put(selfDesc[9], cfg == null ? "" : cfg.getFlComment() == null ? "" : cfg.getFlComment());
//            boolean flag = false;
            for (HzFullCfgWithCfg hzFullCfgWithCfg : hzFullCfgWithCfgs) {
                if (hzFullCfgWithCfg.getCfgBomlineUid().equals(hzBomLineRecord.getPuid())) {
//                    flag = true;
                    data.put(selfDesc[5], hzBomLineRecord.getpDutyEngineer() == null ? "" : hzBomLineRecord.getpDutyEngineer()/*hzFullCfgWithCfg.getFlCfgCreator() == null ? "" : hzFullCfgWithCfg.getFlCfgCreator()*/);
                    for (HzCfg0Record hzCfg0Record : hzCfg0Records) {
                        if (hzCfg0Record.getPuid().equals(hzFullCfgWithCfg.getCfgCfg0Uid())) {
                            data.put(selfDesc[6], hzCfg0Record.getpCfg0Desc() == null ? "" : hzCfg0Record.getpCfg0Desc());
                            data.put(selfDesc[7], hzCfg0Record.getpCfg0ObjectId() == null ? "" : hzCfg0Record.getpCfg0ObjectId());
                        }
                    }
                }
            }
//            if(flag){
//                data.put(selfDesc[5], "");
//                data.put(selfDesc[6], "");
//                data.put(selfDesc[7], "");
//            }
            data.put(selfDesc[9], cfg == null ? "" : cfg.getFlComment() == null ? "" : cfg.getFlComment());
            data.put("bomLinePuid", hzBomLineRecord.getPuid());
            _data.add(data);
        }

        for (HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecords) {
            JSONObject object = new JSONObject();
            object.put("modelPuid", hzCfg0ModelRecord.getPuid());
            object.put("brand", brand.getpBrandName());
            object.put("platform", platform.getpPlatformName());
            object.put("vehicle", vehicle.getpVehicleName());
            object.put("key", hzCfg0ModelRecord.getObjectName());
            object.put("hide", hzCfg0ModelRecord.getPuid());
            if (hzCfg0ModelDetails == null || hzCfg0ModelDetails.size() == 0) {
                object.put("pModelShape", "");
                object.put("pModelAnnouncement", "");
                object.put("pModelCfgDesc", "");
                object.put("pModelCfgMng", "");
                object.put("pModelTrailNum", "");
                object.put("pModelGoodsNum", "");
            } else {
                boolean flag = false;
                for (HzCfg0ModelDetail hzCfg0ModelDetail : hzCfg0ModelDetails) {
                    if (hzCfg0ModelDetail.getpModelPuid().equals(hzCfg0ModelRecord.getPuid())) {
                        object.put("pModelShape", hzCfg0ModelDetail.getpModelShape() == null ? "" : hzCfg0ModelDetail.getpModelShape());
                        object.put("pModelAnnouncement", hzCfg0ModelDetail.getpModelAnnouncement() == null ? "" : hzCfg0ModelDetail.getpModelAnnouncement());
                        object.put("pModelCfgDesc", hzCfg0ModelDetail.getpModelCfgDesc() == null ? "" : hzCfg0ModelDetail.getpModelCfgDesc());
                        object.put("pModelCfgMng", hzCfg0ModelDetail.getpModelCfgMng() == null ? "" : hzCfg0ModelDetail.getpModelCfgMng());
                        object.put("pModelTrailNum", hzCfg0ModelDetail.getpModelTrailNum() == null ? "" : hzCfg0ModelDetail.getpModelTrailNum());
                        object.put("pModelGoodsNum", hzCfg0ModelDetail.getpModelGoodsNum() == null ? "" : hzCfg0ModelDetail.getpModelGoodsNum());
                        flag = true;
                    }
                }
                if (!flag) {
                    object.put("pModelShape", "");
                    object.put("pModelAnnouncement", "");
                    object.put("pModelCfgDesc", "");
                    object.put("pModelCfgMng", "");
                    object.put("pModelTrailNum", "");
                    object.put("pModelGoodsNum", "");
                }

            }
//            JSONArray object_ = new JSONArray();
//            for(HzCfg0Record hzCfg0Record : hzCfg0Records){
//                HzFullCfgModel hzFullCfgModel = mapToModel.get(hzCfg0ModelRecord.getPuid()).get(hzCfg0Record.getPuid());
//                Short sPoint = hzFullCfgModel.getModPointType();
//                String point;
//                if(sPoint==0){
//                    point = "-";
//                }else if(sPoint==1){
//                    point = "○";
//                }else{
//                    point = "●";
//                }
//                JSONObject pointJson = new JSONObject();
//                pointJson.put(hzCfg0Record.getpCfg0ObjectId(),point);
//                object_.add(pointJson);
//            }
//            object.put("point",object_);
//            _model.add(object);
            JSONArray object_ = new JSONArray();
            for (HzBomLineRecord hzBomLineRecord : lines) {
//                boolean flag = false;
                for (HzFullCfgModel hzFullCfgModel : hzFullCfgModels) {
                    if (hzBomLineRecord.getPuid().equals(hzFullCfgModel.getFlModelBomlineUid()) && hzFullCfgModel.getModModelUid().equals(hzCfg0ModelRecord.getPuid())) {
                        Short sPoint = hzFullCfgModel.getModPointType();
                        String point;
                        if (sPoint == null) {
                            point = "";
                        } else if (sPoint == 0) {
                            point = "-";
                        } else if (sPoint == 1) {
                            point = "○";
                        } else {
                            point = "●";
                        }
                        JSONObject pointJson = new JSONObject();
                        pointJson.put("point", point);
                        object_.add(pointJson);
                        break;
                    }
                }
//                if (!flag) {
//                    String point = "-";
//                    JSONObject pointJson = new JSONObject();
//                    pointJson.put("point", point);
//                    object_.add(pointJson);
//                }
            }
            object.put("point", object_);
            _model.add(object);
        }

        com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(hzCfg0Records));


        JSONObject mainJson = new JSONObject();
        mainJson.put("stage", hzFullCfgMain.getStage() == null ? "" : HzFullCfgMain.parseStage(hzFullCfgMain.getStage()));
        mainJson.put("version", hzFullCfgMain.getVersion() == null ? "" : hzFullCfgMain.getVersion());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mainJson.put("effectiveDate", hzFullCfgMain.getEffectiveDate() == null ? "" : sdf.format(hzFullCfgMain.getEffectiveDate()));


        respond.put("data", _data);
        respond.put("model", _model);
        respond.put("array", array);
        respond.put("modelSize", _model.size());
        respond.put("cfgSize", _data.size());
        respond.put("main", mainJson);
        return respond;
    }


    /**
     * 表单初始化
     *
     * @param projectPuid        项目id
     * @param hzCfg0ModelRecords 打点图关系表数据
     * @param hzBomLineRecords   2Y层数据
     */
    private void initLoad(String projectPuid, List<HzCfg0ModelRecord> hzCfg0ModelRecords, List<HzBomLineRecord> hzBomLineRecords) {
        User user = UserInfo.getUser();

        //model表的数据集
        List<HzFullCfgModel> hzFullCfgModels = null;
        //withCfg表的数据集
        List<HzFullCfgWithCfg> hzFullCfgWithCfgs = null;
        //main表数据集
        HzFullCfgMain hzFullCfgMain = hzFullCfgMainDao.selectByProjectId(projectPuid);
        //检查main、model、withcfg三张表的数据是否为空
        if (hzFullCfgMain == null) {
            //为main新增一条信息
            BigDecimal mainPuid = addHzFullCfgMain(projectPuid, user);
            hzFullCfgMain.setId(mainPuid);
            //为withcfg新增数据
//            addHzFullCfgWithCfg(hzCfg0Records, user, mainPuid);
            addHzFullCfgWithCfgForBomLine(hzBomLineRecords, user, mainPuid);
            //为model新增信息
            addHzFullCfgModel(hzCfg0ModelRecords, hzBomLineRecords, user, hzFullCfgMain);
        } else {

            //判断withcfg表是否为空，为空则添加数据
            hzFullCfgWithCfgs = hzFullCfgWithCfgDao.selectByMainID(hzFullCfgMain.getId());
//            if(hzFullCfgModels==null||hzFullCfgWithCfgs.size()==0){
//                addHzFullCfgWithCfg(hzCfg0Records, user, hzFullCfgMain.getId());
//                //判断withcfg表数据完整性
//            }else if(hzFullCfgWithCfgs.size()<hzCfg0Records.size()){
//                List<HzCfg0Record> hzCfg0RecordsInsert = new ArrayList<HzCfg0Record>();
//                for(HzCfg0Record hzCfg0Record : hzCfg0Records){
//                    boolean flag = false;
//                    for(HzFullCfgWithCfg hzFullCfgWithCfg : hzFullCfgWithCfgs){
//                        if(hzFullCfgWithCfg.getCfgCfg0Uid().equals(hzCfg0Record.getPuid())){
//                            flag = true;
//                        }
//                    }
//                    if(!flag){
//                        hzCfg0RecordsInsert.add(hzCfg0Record);
//                    }
//                }
//                addHzFullCfgWithCfg(hzCfg0RecordsInsert, user, hzFullCfgMain.getId());
//            }

            if (hzFullCfgWithCfgs == null || hzFullCfgWithCfgs.size() == 0) {
                addHzFullCfgWithCfgForBomLine(hzBomLineRecords, user, hzFullCfgMain.getId());
                //判断withcfg表数据完整性
            } else if (hzFullCfgWithCfgs.size() < hzBomLineRecords.size()) {
                List<HzBomLineRecord> hzBomLineRecordsInsert = new ArrayList<HzBomLineRecord>();
                for (HzBomLineRecord hzBomLineRecord : hzBomLineRecords) {
                    boolean flag = false;
                    for (HzFullCfgWithCfg hzFullCfgWithCfg : hzFullCfgWithCfgs) {
                        if (hzFullCfgWithCfg.getCfgBomlineUid().equals(hzBomLineRecord.getPuid())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        hzBomLineRecordsInsert.add(hzBomLineRecord);
                    }
                }
                addHzFullCfgWithCfgForBomLine(hzBomLineRecordsInsert, user, hzFullCfgMain.getId());
            }
            //判断model表是否为空，为空则添加数据
            hzFullCfgModels = hzFullCfgModelDao.selectByMainPuid(hzFullCfgMain.getId());
            if (hzFullCfgModels == null || hzFullCfgModels.size() == 0) {
                addHzFullCfgModel(hzCfg0ModelRecords, hzBomLineRecords, user, hzFullCfgMain);
                //判断model表数据完整性
            } else if (hzFullCfgModels.size() < (hzBomLineRecords.size() * hzCfg0ModelRecords.size())) {
                //遍历所有的车辆模型
                for (HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecords) {
                    //存放同一车辆模型数据据集合
                    List<HzFullCfgModel> hzFullCfgModelsCheck = new ArrayList<HzFullCfgModel>();
                    //遍历model表中数据，将同一车辆模型对象分开存放
                    for (HzFullCfgModel hzFullCfgModel : hzFullCfgModels) {
                        if (hzFullCfgModel.getModModelUid().equals(hzCfg0ModelRecord.getPuid())) {
                            hzFullCfgModelsCheck.add(hzFullCfgModel);
                        }
                    }
                    //判断同一车辆模型是否每个2Y都有值
                    if (hzFullCfgModelsCheck.size() != hzBomLineRecords.size()) {
                        //遍历特性数据，与model数据对比，如发现model中不存在某种特性则添加
                        //存放model中不存在的特性
                        List<HzBomLineRecord> hzBomLineRecordsChecck = new ArrayList<HzBomLineRecord>();
                        for (HzBomLineRecord hzBomLineRecord : hzBomLineRecords) {
                            //标志，如model中存在该特性则为true
                            boolean flag = false;
                            for (HzFullCfgModel hzFullCfgModel : hzFullCfgModelsCheck) {
//                                if(!checkString(hzFullCfgModel.getFlModelBomlineUid())){
//                                    continue;
//                                }
                                if (hzFullCfgModel.getFlModelBomlineUid().equals(hzBomLineRecord.getPuid())) {
                                    flag = true;
                                }
                            }
                            if (!flag) {
                                hzBomLineRecordsChecck.add(hzBomLineRecord);
                            }
                        }
                        //如果model中缺失特性，则新增该特性数据
                        if (hzBomLineRecordsChecck.size() > 0) {
                            List<HzCfg0ModelRecord> hzCfg0ModelRecordsInsert = new ArrayList<HzCfg0ModelRecord>();
                            hzCfg0ModelRecordsInsert.add(hzCfg0ModelRecord);
                            addHzFullCfgModel(hzCfg0ModelRecordsInsert, hzBomLineRecordsChecck, user, hzFullCfgMain);
                        }
                    }
                }

            }

        }
    }


    /**
     * 为main新增一条信息
     *
     * @param projectPuid 项目ID
     * @param user        当前用户
     * @return
     */
    private BigDecimal addHzFullCfgMain(String projectPuid, User user) {
        HzFullCfgMain hzFullCfgMain1 = new HzFullCfgMain();
        hzFullCfgMain1.setProjectUid(projectPuid);
        hzFullCfgMain1.setStatus("编辑");
        hzFullCfgMain1.setVersion("1.0");
        hzFullCfgMain1.setCreator(user.getUserName());
        hzFullCfgMain1.setUpdater(user.getUserName());
        BigDecimal mainPuid = hzFullCfgMainDao.insertBackId(hzFullCfgMain1);
        return mainPuid;
    }

    /**
     * 为model新增一条信息
     *
     * @param hzCfg0ModelRecords 所有的车辆模型集合
     * @param hzBomLineRecords   所有的2Y层集合
     * @param user               当前用户
     * @param hzFullCfgMain      主表对象
     */
    private void addHzFullCfgModel(List<HzCfg0ModelRecord> hzCfg0ModelRecords, List<HzBomLineRecord> hzBomLineRecords, User user, HzFullCfgMain hzFullCfgMain) {
        List<HzFullCfgWithCfg> hzFullCfgWithCfgs = hzFullCfgWithCfgDao.selectByMainID(hzFullCfgMain.getId());
        List<HzFullCfgModel> hzFullCfgModels = new ArrayList<HzFullCfgModel>();
        for (HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecords) {
            for (HzBomLineRecord hzBomLineRecord : hzBomLineRecords) {
                HzFullCfgModel hzFullCfgModel = new HzFullCfgModel();
                //模型id
                hzFullCfgModel.setModModelUid(hzCfg0ModelRecord.getPuid());
                //2Y层id
                hzFullCfgModel.setFlModelBomlineUid(hzBomLineRecord.getPuid());
                //根据版本、备注设置打点状态
                String remark = "";
                for (HzFullCfgWithCfg hzFullCfgWithCfg : hzFullCfgWithCfgs) {
                    if (hzFullCfgWithCfg.getCfgBomlineUid().equals(hzBomLineRecord.getPuid())) {
                        remark = hzFullCfgWithCfg.getFlComment();
                        break;
                    }
                }
                String version = hzFullCfgMain.getVersion();
                String versionHeadStr = version.split("\\.")[0];
                Integer versionHead = Integer.valueOf(versionHeadStr);
                if (versionHead >= 2) {
                    if ("0".equals(remark)) {
                        //当备注为选配版本为2或以上打点图默认为空
                    } else if ("1".equals(remark)) {
                        hzFullCfgModel.setModPointType((short) 2);
                    }
                } else {
                    if ("0".equals(remark)) {
                        hzFullCfgModel.setModPointType((short) 1);
                    } else if ("1".equals(remark)) {
                        hzFullCfgModel.setModPointType((short) 2);
                    }
                }


                //创建人
                hzFullCfgModel.setFlModCreator(user.getLogin());
                //最后修改人
                hzFullCfgModel.setFlModLastUpdater(user.getLogin());
                //版本
                hzFullCfgModel.setFlModVersion(hzFullCfgMain.getId());
                hzFullCfgModels.add(hzFullCfgModel);
            }
        }
        if (hzFullCfgModels != null && hzFullCfgModels.size() != 0) {
            hzFullCfgModelDao.insertCfgs(hzFullCfgModels);
        }
    }

    /**
     * 为withcfg新增数据
     * @param hzCfg0Records     所有特性集合
     * @param user               当前用户
     * @param mainPuid          项目对应主表的ID
     */
//    private void addHzFullCfgWithCfg(List<HzCfg0Record> hzCfg0Records, User user, BigDecimal mainPuid){
//        List<HzFullCfgWithCfg> hzFullCfgWithCfgs = new ArrayList<HzFullCfgWithCfg>();
//        for(HzCfg0Record hzCfg0Record :hzCfg0Records){
//            HzFullCfgWithCfg hzFullCfgWithCfg = new HzFullCfgWithCfg();
//            //特性值id
//            hzFullCfgWithCfg.setCfgCfg0Uid(hzCfg0Record.getPuid());
//            //创建者
//            hzFullCfgWithCfg.setFlCfgCreator(user.getUserName());
//            //跟新人
//            hzFullCfgWithCfg.setFlCfgUpdator(user.getUserName());
//            //版本
//            hzFullCfgWithCfg.setFlCfgVersion(mainPuid);
//            hzFullCfgWithCfgs.add(hzFullCfgWithCfg);
//        }
//        hzFullCfgWithCfgDao.insert(hzFullCfgWithCfgs);
//    }

    /**
     * 为withcfg新增数据  BomLine
     *
     * @param hzBomLineRecords 2Y层的集合
     * @param user             当前用户
     * @param mainPuid         主表id
     */
    private void addHzFullCfgWithCfgForBomLine(List<HzBomLineRecord> hzBomLineRecords, User user, BigDecimal mainPuid) {
        List<HzFullCfgWithCfg> hzFullCfgWithCfgs = new ArrayList<HzFullCfgWithCfg>();
        for (HzBomLineRecord hzBomLineRecord : hzBomLineRecords) {
            HzFullCfgWithCfg hzFullCfgWithCfg = new HzFullCfgWithCfg();
            //2Y级PUID
            hzFullCfgWithCfg.setCfgBomlineUid(hzBomLineRecord.getPuid());
            //2Y级NAME
            hzFullCfgWithCfg.setFlCfgBomlineName(hzBomLineRecord.getpBomLinePartName());
            //创建者
            hzFullCfgWithCfg.setFlCfgCreator(user.getUserName());
            //跟新人
            hzFullCfgWithCfg.setFlCfgUpdator(user.getUserName());
            //版本
            hzFullCfgWithCfg.setFlCfgVersion(mainPuid);
            hzFullCfgWithCfgs.add(hzFullCfgWithCfg);
        }
        if (hzFullCfgWithCfgs != null && hzFullCfgWithCfgs.size() != 0) {
            hzFullCfgWithCfgDao.insertBomLine(hzFullCfgWithCfgs);
        }
    }

    /**
     * 保存单行
     *
     * @param bomLinePuid
     * @param cfgPuid
     * @param msgVal
     * @return
     */
    public JSONObject saveOneRow(String bomLinePuid, String cfgPuid, Integer colorPart, String msgVal) {
        JSONObject respone = new JSONObject();
        if (cfgPuid.equals("null")) {
            respone.put("flag", false);
            return respone;
        }
        HzBomLineRecord hzBomLineRecord = new HzBomLineRecord();
        hzBomLineRecord.setPuid(bomLinePuid);
        hzBomLineRecord.setColorPart(colorPart);
        int updata2YNum = hzBomDataService.updata2Y(hzBomLineRecord);
        if (updata2YNum != 1) {
            respone.put("flag", true);
            return respone;
        }
        HzFullCfgWithCfg hzFullCfgWithCfg = new HzFullCfgWithCfg();
        //bomLine PUID
        hzFullCfgWithCfg.setCfgBomlineUid(bomLinePuid);

        //特性ID
        if ("".equals(cfgPuid) || cfgPuid == null) {
            hzFullCfgWithCfg.setCfgCfg0Uid(null);
        } else {
            hzFullCfgWithCfg.setCfgCfg0Uid(cfgPuid);
        }
        //备注
        hzFullCfgWithCfg.setFlComment(msgVal);
        int updateRow = hzFullCfgWithCfgDao.updateByBomLinePuid(hzFullCfgWithCfg);
        if (updateRow == 1) {
            hzFullCfgModelDao.updateByBomLinePuid(hzFullCfgWithCfg);
            respone.put("flag", true);
        } else {
            respone.put("flag", false);

        }
        return respone;
    }

    /**
     * 保存打点图
     *
     * @param data model集合 格式为<车辆模型ID<特性ID,打点图状态>>
     * @return
     */
    public JSONObject savePoint(Map<String, Map<String, String>> data) {
        JSONObject respons = new JSONObject();

        //创建存储model数据集
        List<HzFullCfgModel> hzFullCfgModels = new ArrayList<HzFullCfgModel>();
        Set<String> modelKeys = data.keySet();
        for (String modelKey : modelKeys) {
            Map<String, String> cfgPointMap = data.get(modelKey);
            Set<String> cfgKeys = cfgPointMap.keySet();
            for (String cfgKey : cfgKeys) {
                String pointStr = cfgPointMap.get(cfgKey);
                short point;
                if ("-".equals(pointStr) || "".equals(pointStr)) {
                    point = 0;
                } else if ("○".equals(pointStr)) {
                    point = 1;
                } else {
                    point = 2;
                }
                HzFullCfgModel hzFullCfgModel = new HzFullCfgModel();
                hzFullCfgModel.setModModelUid(modelKey);
                hzFullCfgModel.setFlModelBomlineUid(cfgKey);
                hzFullCfgModel.setModPointType(point);
                hzFullCfgModels.add(hzFullCfgModel);
            }
        }
        int updataNumber = 0;
        if (hzFullCfgModels.size() > 0) {
            updataNumber = hzFullCfgModelDao.updateByHzFullCfgModelList(hzFullCfgModels);
        }
        if (hzFullCfgModels.size() == updataNumber) {
            respons.put("updateFlag", true);
        } else {
            respons.put("updateFlag", false);
        }
        return respons;
    }

    /**
     * 删除车辆模型
     *
     * @param modelId
     * @return
     */
    public JSONObject deleteModel(String modelId) {
        JSONObject respons = new JSONObject();
        int deleteRow = hzCfg0ModelService.deleteModelById(modelId);
        if (deleteRow == 1) {
            respons.put("flag", true);
        } else {
            respons.put("flag", false);
        }
        return respons;
    }

    /**
     * 获取全配置BOM一级清单主数据
     *
     * @param projectUid
     * @return
     */
    public HzFullCfgMain getFullCfgMain(String projectUid) {
        return hzFullCfgMainDao.selectByProjectId(projectUid);
    }

    /**
     * 存储版本信息
     *
     * @param params
     * @return
     */
    public JSONObject setVersion(Map<String, String> params) {
        JSONObject result = new JSONObject();
        result.put("status", false);
        result.put("msg", "更新全配置BOM一级清单主数据失败");
        User user = UserInfo.getUser();
        HzFullCfgMain fullCfgMain = hzFullCfgMainDao.selectByProjectId(params.get("projectPuid"));
        if (fullCfgMain != null) {
            if (checkString(fullCfgMain.getVersion()) && fullCfgMain.getVersion().contains(".")) {
                String stage = fullCfgMain.getVersion().substring(0, fullCfgMain.getVersion().indexOf("."));
                if (Integer.parseInt(params.get("version1")) > Integer.parseInt(stage)) {
                    fullCfgMain.setVersion(params.get("version1") + ".0");
                } else if (Integer.parseInt(params.get("version1")) < Integer.parseInt(stage)) {
                    logger.info("不能进行降版本操作");
                    result.put("status", false);
                    result.put("msg", "不能进行降版本操作");
                    return result;
                }
            }
            fullCfgMain.setStatus("更新");
//            fullCfgMain.setStage(Integer.parseInt(params.get("stage")));
            fullCfgMain.setUpdater(user.getUsername());
            fullCfgMain.setUpdateDate(new Date());
            fullCfgMain.setCreator(null);
            fullCfgMain.setCreateDate(null);
            if (hzFullCfgMainDao.updateByPrimaryKeySelective(fullCfgMain) > 0) {
                logger.info("更新全配置BOM一级清单主数据成功");
                result.put("status", true);
                result.put("msg", "更新全配置BOM一级清单主数据成功");
                result.put("version", fullCfgMain.getVersion());
                result.put("stage", fullCfgMain.getStage());
            }
        }
        return result;
    }

    /**
     * 存储阶段信息
     *
     * @param params
     * @return
     */
    public JSONObject setStage(Map<String, String> params) {
        JSONObject result = new JSONObject();
        result.put("status", false);
        result.put("msg", "更新全配置BOM一级清单主数据失败");
        User user = UserInfo.getUser();
        HzFullCfgMain fullCfgMain = hzFullCfgMainDao.selectByProjectId(params.get("projectPuid"));
        if (fullCfgMain != null) {
            fullCfgMain.setStatus("更新");
            fullCfgMain.setStage(Integer.parseInt(params.get("stage")));
            fullCfgMain.setUpdater(user.getUsername());
            fullCfgMain.setUpdateDate(new Date());
            fullCfgMain.setCreator(null);
            fullCfgMain.setCreateDate(null);
            if (hzFullCfgMainDao.updateByPrimaryKeySelective(fullCfgMain) > 0) {
                logger.info("更新全配置BOM一级清单主数据成功");
                result.put("status", true);
                result.put("msg", "更新全配置BOM一级清单主数据成功");
                result.put("version", fullCfgMain.getVersion());
                result.put("stage", fullCfgMain.getStage());
            }
        }
        return result;
    }

    /**
     * 发布，升小版本
     *
     * @param projectUid
     * @return
     */
    public JSONObject promote(String projectUid) {
        JSONObject result = new JSONObject();
        result.put("status", false);
        result.put("msg", "更新全配置BOM一级清单主数据失败");
        User user = UserInfo.getUser();
        HzFullCfgMain fullCfgMain = hzFullCfgMainDao.selectByProjectId(projectUid);
        if (fullCfgMain != null) {
            if (checkString(fullCfgMain.getVersion()) && fullCfgMain.getVersion().contains(".")) {
                String stage = fullCfgMain.getVersion().substring(0, fullCfgMain.getVersion().indexOf(".")) + "." + String.valueOf(Integer.parseInt(fullCfgMain.getVersion().substring(fullCfgMain.getVersion().indexOf(".") + 1)) + 1);
                HzFullCfgMain hzFullCfgMain = new HzFullCfgMain();
                hzFullCfgMain.setId(fullCfgMain.getId());
                hzFullCfgMain.setVersion(stage);
                hzFullCfgMain.setUpdater(user.getUsername());
                hzFullCfgMain.setUpdateDate(new Date());
                hzFullCfgMain.setEffectiveDate(new Date());
                if (hzFullCfgMainDao.updateByPrimaryKeySelective(hzFullCfgMain) > 0) {
                    logger.info("升小版本成功");
                    result.put("status", true);
                    result.put("msg", "升小版本成功");
                    result.put("version", hzFullCfgMain.getVersion());
                    result.put("releaseDate", DateStringHelper.dateToString(hzFullCfgMain.getEffectiveDate()));
                }
            }
        } else {
            result.put("msg", "没有找到全配置BOM一级清单主数据");
        }
        return result;
    }

    /**
     * 添加基本车型模型
     *
     * @param params
     * @return
     */
    public JSONObject addVehicleModel(Map<String, String> params) {
        JSONObject result = new JSONObject();
        result.put("msg", "请选择一个项目进行操作");
        result.put("status", false);
        if (null != params && params.containsKey("pCfg0ModelOfMainRecord") && checkString(params.get("pCfg0ModelOfMainRecord"))) {
            HzCfg0MainRecord mainRecord = null;
            if ((mainRecord = hzCfg0MainService.doGetByPrimaryKey(params.get("pCfg0ModelOfMainRecord"))) == null) {
                return result;
            }
            projectHelper.doGetProjectTreeByProjectId(mainRecord.getpCfg0OfWhichProjectPuid());
            HzCfg0ModelRecord modelRecord = new HzCfg0ModelRecord();
            HzCfg0ModelDetail modelDetail = new HzCfg0ModelDetail();
            //生成UID
            modelRecord.setPuid(UUIDHelper.generateUpperUid());
            //生成UID
            modelDetail.setPuid(UUIDHelper.generateUpperUid());
            //设置归属车型
            modelDetail.setpModelPuid(modelRecord.getPuid());
            //设置版型
            modelDetail.setpModelVersion(params.get("pModelVersion"));
            //设置车身形式
            modelDetail.setpModelShape(params.get("pModelShape"));
            //设置公告
            modelDetail.setpModelAnnouncement(params.get("pModelAnnouncement"));
            //设置配置描述
            modelDetail.setpModelCfgDesc(params.get("pModelCfgDesc"));
            //设置配置管理
            modelDetail.setpModelCfgMng(params.get("pModelCfgMng"));
            //车型
            modelDetail.setpModelMod(projectHelper.getVehicle().getpVehicleCode());
            //平台
            modelDetail.setpModelPlatform(projectHelper.getPlatform().getpPlatformCode());
            //品牌
            modelDetail.setpModelBrand(projectHelper.getBrand().getpBrandName());
            //从TC继承过来的模型
            //模型名
            modelRecord.setObjectName(checkString(params.get("objectName")) ? params.get("objectName") : params.get("pModelVersion"));
            //模型描述
            modelRecord.setObjectDesc(checkString(params.get("objectDesc")) ? params.get("objectDesc") : params.get("pModelVersion"));
            //模型基本信息
            modelRecord.setpCfg0ModelBasicDetail(params.get("pCfg0ModelBasicDetail"));
            //归属主配置
            modelRecord.setpCfg0ModelOfMainRecord(mainRecord.getPuid());
            //没有设置归属的颜色车型
            if (hzCfg0ModelRecordService.doInsert(Collections.singletonList(modelRecord))) {
                if (hzCfg0ModelDetailDao.insert(modelDetail) > 0) {
                    logger.error("添加列成功");
                    result.put("status", true);
                    result.put("msg", "添加列成功");
                } else {
                    logger.error("添加模型详细信息失败");
                    result.put("status", false);
                    result.put("msg", "添加列模型详细信息失败，请联系系统管理员查看日志");
                    hzCfg0ModelRecordService.doDeleteByUid(modelRecord.getPuid());
                }
            } else {
                logger.error("添加模型信息失败");
                result.put("status", false);
                result.put("msg", "添加列信息失败，请联系系统管理员查看日志");
            }
        }
        return result;

    }

    /**
     * 查询所有以关联2Y层的特性和当前2Y层关联的特性，实现前端特性选择下拉列表的动态效果
     *
     * @param projectId
     * @param bomLineId
     * @return
     */
    public JSONObject query2YCfg(String projectId, String bomLineId) {
        JSONObject respons = new JSONObject();

        List<HzFullCfgWithCfg> hzFullCfgWithCfgs = hzFullCfgWithCfgDao.query2YCfgByProjectId(projectId);
        HzFullCfgWithCfg hzFullCfgWithCfg = hzFullCfgWithCfgDao.query2YCfgByBomLineId(bomLineId);
        respons.put("cfgs", hzFullCfgWithCfgs);
        respons.put("selfCfg", hzFullCfgWithCfg);
        return respons;
    }

    /**
     * 保存一个2Y层对应所有车型的打点图
     *
     * @param dataMap
     * @return
     */
    public JSONObject saveBomLinePiont(Map<String, Map<String, String>> dataMap) {
        JSONObject respons = new JSONObject();

        List<HzFullCfgModel> hzFullCfgModels = new ArrayList<HzFullCfgModel>();
        Set<String> bomLineKeys = dataMap.keySet();
        for (String bomLinekey : bomLineKeys) {
            Map<String, String> modelMap = dataMap.get(bomLinekey);
            Set<String> modelKeys = modelMap.keySet();
            for (String modelKey : modelKeys) {
                String pointStr = modelMap.get(modelKey);
                short point;
                if ("-".equals(pointStr)) {
                    point = 0;
                } else if ("○".equals(pointStr)) {
                    point = 1;
                } else {
                    point = 2;
                }
                HzFullCfgModel hzFullCfgModel = new HzFullCfgModel();
                hzFullCfgModel.setFlModelBomlineUid(bomLinekey);
                hzFullCfgModel.setModModelUid(modelKey);
                if (!"".equals(pointStr)) {
                    hzFullCfgModel.setModPointType(point);
                }
                hzFullCfgModels.add(hzFullCfgModel);
            }
        }
        int updataNumber = 0;
        if (hzFullCfgModels.size() > 0) {
            updataNumber = hzFullCfgModelDao.updateByHzFullCfgModelList(hzFullCfgModels);
        }
        if (hzFullCfgModels.size() == updataNumber) {
            respons.put("updateFlag", true);
        } else {
            respons.put("updateFlag", false);
        }
        return respons;
    }
}