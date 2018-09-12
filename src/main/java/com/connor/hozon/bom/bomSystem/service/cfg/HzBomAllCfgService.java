package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelDetailDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgWithCfgDao;
import com.connor.hozon.bom.bomSystem.service.project.HzBrandService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import com.connor.hozon.bom.bomSystem.service.project.HzVehicleService;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.*;
import sql.pojo.project.HzBrandRecord;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;
import sql.pojo.project.HzVehicleRecord;

import java.math.BigDecimal;
import java.util.*;

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

    private static final String[] selfDesc =
            {
                    "pBomOfWhichDept", "operationType","pBomLineId", "pBomLineName", "pH9featureenname", "owningUser","pCfg0Desc", "pCfg0ObjectId", "comment"
            };
    /**
     * @param projectPuid 项目puid
     * @return net.sf.json.JSONObject
     * Description: 根据数模层获取到bom的配置信息和车型模型信息
     */
    public JSONObject parse(String projectPuid) {
        initLoad(projectPuid);

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

        //搜索全部特性，并经过P_CFG0_OBJECT_ID 升序排序
        QueryBase queryBase = new QueryBase();
        queryBase.setSort("P_CFG0_OBJECT_ID");
        List<HzCfg0Record> hzCfg0Records = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, queryBase);
        //获取该项目下的所有车型模型
        List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(projectPuid);

        List<HzCfg0ModelDetail> hzCfg0ModelDetailList = new ArrayList<HzCfg0ModelDetail>();
        for(HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecords){
            HzCfg0ModelDetail hzCfg0ModelDetail = new HzCfg0ModelDetail();
            hzCfg0ModelDetail.setpModelPuid(hzCfg0ModelRecord.getPuid());
            hzCfg0ModelDetailList.add(hzCfg0ModelDetail);
        }
        //为detail赋值
        List<HzCfg0ModelDetail> hzCfg0ModelDetails = null;
        if(hzCfg0ModelDetailList.size()!=0){
            hzCfg0ModelDetails = hzCfg0ModelDetailDao.selectByModelIds(hzCfg0ModelDetailList);
        }

        //为特性Map赋值
        for(HzFullCfgWithCfg hzFullCfgWithCfg :hzFullCfgWithCfgs){
            modelWithBomLineMap.put(hzFullCfgWithCfg.getCfgCfg0Uid(), hzFullCfgWithCfg);
        }
        //为车辆模型Map赋值
        for(HzFullCfgModel hzFullCfgModel : hzFullCfgModels){
            if(mapToModel.get(hzFullCfgModel.getModModelUid())==null){
                Map<String, HzFullCfgModel> mapToModel_ = new HashMap<String, HzFullCfgModel>();
                mapToModel.put(hzFullCfgModel.getModModelUid(),mapToModel_);
            }
            mapToModel.get(hzFullCfgModel.getModModelUid()).put(hzFullCfgModel.getModCfg0Uid(),hzFullCfgModel);
        }

        for(HzCfg0Record hzCfg0Record : hzCfg0Records){
            JSONObject data = new JSONObject();

            data.put(selfDesc[0], modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgBomlineName()==null?"":modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgBomlineName());
            data.put(selfDesc[1], modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgBomlineName()==null?"":modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgBomlineName());
            data.put(selfDesc[2], modelWithBomLineMap.get(hzCfg0Record.getPuid()).getCfgBomlineUid()==null?"":modelWithBomLineMap.get(hzCfg0Record.getPuid()).getCfgBomlineUid());
            data.put(selfDesc[3], hzCfg0Record.getpCfg0FamilyName()==null?"":hzCfg0Record.getpCfg0FamilyName());
            data.put(selfDesc[4], hzCfg0Record.getpH9featureenname()==null?"":hzCfg0Record.getpH9featureenname());
            data.put(selfDesc[5], modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgCreator()==null?"":modelWithBomLineMap.get(hzCfg0Record.getPuid()).getFlCfgCreator());
            data.put(selfDesc[6], hzCfg0Record.getpCfg0ObjectId()==null?"":hzCfg0Record.getpCfg0ObjectId());
            data.put(selfDesc[7], hzCfg0Record.getpCfg0ObjectId()==null?"":hzCfg0Record.getpCfg0ObjectId());
            data.put(selfDesc[8], hzCfg0Record.getpCfg0Desc()==null?"":hzCfg0Record.getpCfg0Desc());
            data.put("cfgPuid",hzCfg0Record.getPuid());
            _data.add(data);
        }

        for(HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecords){
            JSONObject object = new JSONObject();
            object.put("modelPuid", hzCfg0ModelRecord.getPuid());
            object.put("brand", brand.getpBrandName());
            object.put("platform", platform.getpPlatformName());
            object.put("vehicle", vehicle.getpVehicleName());
            object.put("key", hzCfg0ModelRecord.getObjectName());
            object.put("hide", hzCfg0ModelRecord.getPuid());
            if(hzCfg0ModelDetails==null||hzCfg0ModelDetails.size()==0){
                object.put("pModelShape", "");
                object.put("pModelAnnouncement", "");
                object.put("pModelCfgDesc", "");
                object.put("pModelCfgMng", "");
            }else {
                for(HzCfg0ModelDetail hzCfg0ModelDetail : hzCfg0ModelDetails){
                    if(hzCfg0ModelDetail.getpModelPuid().equals(hzCfg0ModelRecord.getPuid())){
                        object.put("pModelShape", hzCfg0ModelDetail.getpModelShape() == null ? "" : hzCfg0ModelDetail.getpModelShape());
                        object.put("pModelAnnouncement", hzCfg0ModelDetail.getpModelAnnouncement() == null ? "" : hzCfg0ModelDetail.getpModelAnnouncement());
                        object.put("pModelCfgDesc", hzCfg0ModelDetail.getpModelCfgDesc() == null ? "" : hzCfg0ModelDetail.getpModelCfgDesc());
                        object.put("pModelCfgMng", hzCfg0ModelDetail.getpModelCfgMng() == null ? "" : hzCfg0ModelDetail.getpModelCfgMng());
                    }else {
                        object.put("pModelShape", "");
                        object.put("pModelAnnouncement", "");
                        object.put("pModelCfgDesc", "");
                        object.put("pModelCfgMng", "");
                    }
                }

            }
            JSONArray object_ = new JSONArray();
            for(HzCfg0Record hzCfg0Record : hzCfg0Records){
                HzFullCfgModel hzFullCfgModel = mapToModel.get(hzCfg0ModelRecord.getPuid()).get(hzCfg0Record.getPuid());
                Short sPoint = hzFullCfgModel.getModPointType();
                String point;
                if(sPoint==0){
                    point = "-";
                }else if(sPoint==1){
                    point = "●";
                }else{
                    point = "○";
                }
                JSONObject pointJson = new JSONObject();
                pointJson.put("point",point);
                object_.add(pointJson);
            }
            object.put("point",object_);
            _model.add(object);
        }


        respond.put("data", _data);
        respond.put("model", _model);
        respond.put("modelSize",_model.size());
        respond.put("cfgSize",_data.size());
        return respond;
    }


    /**
     * 表单初始化
     * @param projectPuid
     */
    private void initLoad(String projectPuid){
        User user = UserInfo.getUser();

        //搜索全部特性，并经过P_CFG0_OBJECT_ID 升序排序
        QueryBase queryBase = new QueryBase();
        queryBase.setSort("P_CFG0_OBJECT_ID");
        List<HzCfg0Record> hzCfg0Records = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, queryBase);

        //获取该项目下的所有车型模型
        List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(projectPuid);

        //model表的数据集
        List<HzFullCfgModel> hzFullCfgModels = null;
        //withCfg表的数据集
        List<HzFullCfgWithCfg> hzFullCfgWithCfgs = null;
        //main表数据集
        HzFullCfgMain hzFullCfgMain = hzFullCfgMainDao.selectByProjectId(projectPuid);
        //检查main、model、withcfg三张表的数据是否为空
        if(hzFullCfgMain==null){
            //为main新增一条信息
            BigDecimal mainPuid = addHzFullCfgMain(projectPuid,user);
            //为model新增信息
            addHzFullCfgModel(hzCfg0ModelRecords, hzCfg0Records, user, mainPuid);
            //为withcfg新增数据
            addHzFullCfgWithCfg(hzCfg0Records, user, mainPuid);
        }else{
            //判断model表是否为空，为空则添加数据
            hzFullCfgModels = hzFullCfgModelDao.selectByMainPuid(hzFullCfgMain.getId());
            if(hzFullCfgModels==null||hzFullCfgModels.size()==0){
                addHzFullCfgModel(hzCfg0ModelRecords, hzCfg0Records, user, hzFullCfgMain.getId());
                //判断model表数据完整性
            }else if(hzFullCfgModels.size()<(hzCfg0Records.size()*hzCfg0ModelRecords.size())){
                //遍历所有的车辆模型
                for(HzCfg0ModelRecord hzCfg0ModelRecord :hzCfg0ModelRecords){
                    //存放同一车辆模型数据据集合
                    List<HzFullCfgModel> hzFullCfgModelsCheck = new ArrayList<HzFullCfgModel >();
                    //遍历model表中数据，将同一车辆模型对象分开存放
                    for(HzFullCfgModel hzFullCfgModel : hzFullCfgModels){
                        if(hzFullCfgModel.getModModelUid().equals(hzCfg0ModelRecord.getPuid())){
                            hzFullCfgModelsCheck.add(hzFullCfgModel);
                        }
                    }
                    //判断同一车辆模型是否每个特性都有值
                    if(hzFullCfgModelsCheck.size()!=hzCfg0Records.size()){
                        //遍历特性数据，与model数据对比，如发现model中不存在某种特性则添加
                        //存放model中不存在的特性
                        List<HzCfg0Record> hzCfg0RecordsChecck = new ArrayList<HzCfg0Record>();
                        for(HzCfg0Record hzCfg0Record : hzCfg0Records){
                            //标志，如model中存在该特性则为true
                            boolean flag = false;
                            for(HzFullCfgModel hzFullCfgModel : hzFullCfgModelsCheck){
                                if(hzFullCfgModel.getModCfg0Uid().equals(hzCfg0Record.getPuid())){
                                    flag = true;
                                }
                            }
                            if(!flag){
                                hzCfg0RecordsChecck.add(hzCfg0Record);
                            }
                        }
                        //如果model中缺失特性，则新增该特性数据
                        if(hzCfg0RecordsChecck.size()>0){
                            List<HzCfg0ModelRecord> hzCfg0ModelRecordsInsert = new ArrayList<HzCfg0ModelRecord>();
                            hzCfg0ModelRecordsInsert.add(hzCfg0ModelRecord);
                            addHzFullCfgModel(hzCfg0ModelRecordsInsert, hzCfg0RecordsChecck, user, hzFullCfgMain.getId());
                        }
                    }
                }

            }
            //判断withcfg表是否为空，为空则添加数据
            hzFullCfgWithCfgs = hzFullCfgWithCfgDao.selectByMainID(hzFullCfgMain.getId());
            if(hzFullCfgModels==null||hzFullCfgWithCfgs.size()==0){
                addHzFullCfgWithCfg(hzCfg0Records, user, hzFullCfgMain.getId());
                //判断withcfg表数据完整性
            }else if(hzFullCfgWithCfgs.size()<hzCfg0Records.size()){
                List<HzCfg0Record> hzCfg0RecordsInsert = new ArrayList<HzCfg0Record>();
                for(HzCfg0Record hzCfg0Record : hzCfg0Records){
                    boolean flag = false;
                    for(HzFullCfgWithCfg hzFullCfgWithCfg : hzFullCfgWithCfgs){
                        if(hzFullCfgWithCfg.getCfgCfg0Uid().equals(hzCfg0Record.getPuid())){
                            flag = true;
                        }
                    }
                    if(!flag){
                        hzCfg0RecordsInsert.add(hzCfg0Record);
                    }
                }
                addHzFullCfgWithCfg(hzCfg0RecordsInsert, user, hzFullCfgMain.getId());
            }
        }
    }



    /**
     * 为main新增一条信息
     * @param projectPuid   项目ID
     * @param user          当前用户
     * @return
     */
    private BigDecimal addHzFullCfgMain(String projectPuid, User user){
        HzFullCfgMain hzFullCfgMain1 = new HzFullCfgMain();
        hzFullCfgMain1.setProjectUid(projectPuid);
        hzFullCfgMain1.setStatus("编辑");
        hzFullCfgMain1.setVersion("1.0");
        hzFullCfgMain1.setCreator(user.getUserName());
        hzFullCfgMain1.setUpdater(user.getUserName());
        BigDecimal mainPuid = hzFullCfgMainDao.insert(hzFullCfgMain1);
        return mainPuid;
    }

    /**
     * 为model新增一条信息
     * @param hzCfg0ModelRecords    所有的车辆模型集合
     * @param hzCfg0Records         所有的特性集合
     * @param user                  当前用户
     * @param mainPuid              项目对应主表的ID
     */
    private void addHzFullCfgModel(List<HzCfg0ModelRecord> hzCfg0ModelRecords, List<HzCfg0Record> hzCfg0Records, User user, BigDecimal mainPuid){
        List<HzFullCfgModel> hzFullCfgModels = new ArrayList<HzFullCfgModel>();
        for(HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecords){
            for(HzCfg0Record hzCfg0Record : hzCfg0Records){
                HzFullCfgModel hzFullCfgModel = new HzFullCfgModel();
                //模型id
                hzFullCfgModel.setModModelUid(hzCfg0ModelRecord.getPuid());
                //特性值id
                hzFullCfgModel.setModCfg0Uid(hzCfg0Record.getPuid());
                //打点状态
                hzFullCfgModel.setModPointType((short) 0);
                //创建人
                hzFullCfgModel.setFlModCreator(user.getLogin());
                //最后修改人
                hzFullCfgModel.setFlModLastUpdater(user.getLogin());
                //版本
                hzFullCfgModel.setFlModVersion(mainPuid);
                hzFullCfgModels.add(hzFullCfgModel);
            }
        }
            hzFullCfgModelDao.insertCfgs(hzFullCfgModels);
    }

    /**
     * 为withcfg新增数据
     * @param hzCfg0Records     所有特性集合
     * @param user               当前用户
     * @param mainPuid          项目对应主表的ID
     */
    private void addHzFullCfgWithCfg(List<HzCfg0Record> hzCfg0Records, User user, BigDecimal mainPuid){
        List<HzFullCfgWithCfg> hzFullCfgWithCfgs = new ArrayList<HzFullCfgWithCfg>();
        for(HzCfg0Record hzCfg0Record :hzCfg0Records){
            HzFullCfgWithCfg hzFullCfgWithCfg = new HzFullCfgWithCfg();
            //特性值id
            hzFullCfgWithCfg.setCfgCfg0Uid(hzCfg0Record.getPuid());
            //创建者
            hzFullCfgWithCfg.setFlCfgCreator(user.getUserName());
            //跟新人
            hzFullCfgWithCfg.setFlCfgUpdator(user.getUserName());
            //版本
            hzFullCfgWithCfg.setFlCfgVersion(mainPuid);
            hzFullCfgWithCfgs.add(hzFullCfgWithCfg);
        }
        hzFullCfgWithCfgDao.insert(hzFullCfgWithCfgs);
    }
}