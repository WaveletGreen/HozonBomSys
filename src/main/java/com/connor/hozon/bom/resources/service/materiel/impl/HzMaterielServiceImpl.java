package com.connor.hozon.bom.resources.service.materiel.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.config.MaterielType;
import com.connor.hozon.bom.resources.dto.request.AddHzMaterielReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzMaterielReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.service.materiel.HzMaterielService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.entity.User;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;
import sql.redis.SerializeUtil;

import java.util.*;

import static com.connor.hozon.bom.resources.config.MaterielType.SUPER_MATERIEL_DATA;

/**
 * @Author: haozt
 * @Date: 2018/7/3
 * @Description:
 */
@Service("HzMaterielService")
public class HzMaterielServiceImpl implements HzMaterielService {
    @Autowired
    private HzMaterielDAO hzMaterielDAO;

    @Autowired
    private HzFactoryDAO hzFactoryDAO;
    @Override
    public OperateResultMessageRespDTO addHzMateriel(AddHzMaterielReqDTO addHzMaterielReqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        if(null == addHzMaterielReqDTO.getProjectId() || addHzMaterielReqDTO.getProjectId().equals("")){
            respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
            respDTO.setErrMsg("请选择项目！");
            return respDTO;
        }
        try{
            User user = UserInfo.getUser();
            if(user.getGroupId()!=9l){
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                respDTO.setErrMsg("您当前没有权限进行此操作！");
                return respDTO;
            }
            HzMaterielRecord record = new HzMaterielRecord();
            HzFactory hzFactory = hzFactoryDAO.findFactory("",addHzMaterielReqDTO.getFactoryCode());
            if(hzFactory == null){
                String puid = UUID.randomUUID().toString();
                hzFactory =  new HzFactory();
                hzFactory.setPuid(puid);
                hzFactory.setpFactoryCode(addHzMaterielReqDTO.getFactoryCode());
                hzFactory.setpUpdateName(user.getUserName());
                hzFactory.setpCreateName(user.getUserName());
                int i = hzFactoryDAO.insert(hzFactory);
                if(i<0){
                    return OperateResultMessageRespDTO.getFailResult();
                }
                record.setpFactoryPuid(puid);
            }else{
                record.setpFactoryPuid(hzFactory.getPuid());
            }
            record.setPuid(UUID.randomUUID().toString());
            record.setpPertainToProjectPuid(addHzMaterielReqDTO.getProjectId());

            record.setpBasicUnitMeasure(addHzMaterielReqDTO.getpBasicUnitMeasure());

            record.setpCreateName(user.getUserName());

            record.setpMaterielDataType(addHzMaterielReqDTO.getpMaterielDataType());
            record.setpMaterielDescEn(addHzMaterielReqDTO.getpMaterielDescEn());

            record.setpMrpController(addHzMaterielReqDTO.getpMrpController());



            record.setpUpdateName(user.getUserName());
            record.setpMaterielCode(addHzMaterielReqDTO.getpMaterielCode());
            record.setpMaterielType(addHzMaterielReqDTO.getpMaterielType());
            int i = hzMaterielDAO.insert(record);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return null;
        }

        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO updateHzMateriel(UpdateHzMaterielReqDTO updateHzMaterielReqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        if(null == updateHzMaterielReqDTO.getProjectId() || updateHzMaterielReqDTO.getProjectId().equals("")){
            respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
            respDTO.setErrMsg("请选择项目！");
            return respDTO;
        }
        try {
            User user = UserInfo.getUser();
            if (user.getGroupId() != 9l) {
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                respDTO.setErrMsg("您当前没有权限进行此操作！");
                return respDTO;
            }
            HzMaterielRecord record = new HzMaterielRecord();
            HzFactory hzFactory = hzFactoryDAO.findFactory("", updateHzMaterielReqDTO.getFactoryCode());
            if (hzFactory == null) {
                String puid = UUID.randomUUID().toString();
                hzFactory = new HzFactory();
                hzFactory.setPuid(puid);
                hzFactory.setpFactoryCode(updateHzMaterielReqDTO.getFactoryCode());
                hzFactory.setpUpdateName(user.getUserName());
                hzFactory.setpCreateName(user.getUserName());
                int i = hzFactoryDAO.insert(hzFactory);
                if (i < 0) {
                    return OperateResultMessageRespDTO.getFailResult();
                }
                record.setpFactoryPuid(puid);
            } else {
                record.setpFactoryPuid(hzFactory.getPuid());
            }
            record.setPuid(updateHzMaterielReqDTO.getPuid());
            record.setpPertainToProjectPuid(updateHzMaterielReqDTO.getProjectId());
            record.setpBasicUnitMeasure(updateHzMaterielReqDTO.getpBasicUnitMeasure());
            record.setpCreateName(user.getUserName());
            record.setpMaterielDataType(updateHzMaterielReqDTO.getpMaterielDataType());
            record.setpMaterielDescEn(updateHzMaterielReqDTO.getpMaterielDescEn());
            record.setpMrpController(updateHzMaterielReqDTO.getpMrpController());
            record.setpUpdateName(user.getUserName());
            record.setpMaterielCode(updateHzMaterielReqDTO.getpMaterielCode());
            record.setpMaterielType(updateHzMaterielReqDTO.getpMaterielType());
            int i = hzMaterielDAO.update(record);
            if(i>0){
                return  OperateResultMessageRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return null;
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public OperateResultMessageRespDTO deleteHzMateriel(String puid) {
        try{
            OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
            if(null == puid || puid.equals("")){
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                respDTO.setErrMsg("请选择一条需要删除的数据！");
                return respDTO;
            }
            User user = UserInfo.getUser();
            if (user.getGroupId() != 9l) {
                respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                respDTO.setErrMsg("您当前没有权限进行此操作！");
                return respDTO;
            }
            int i = hzMaterielDAO.delete(puid);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }

    @Override
    public Page<HzMaterielRespDTO> findHzMaterielForPage(HzMaterielByPageQuery query) {
        User user = UserInfo.getUser();
        //先找出对应的物料类型，添加到物料类型表，将其数据自动带出

        /**
         * 物料类型  严格按照注释来读写数据
         * （11 整车超级物料主数据
         * 21 整车衍生物料主数据
         * 31 虚拟件物料主数据
         * 41半成品物料主数据
         * 51 生产性外购物料主数据
         * 61自制备件物料主数据）
         */
        Integer type = query.getpMaterielDataType();
        //默认工厂为1001工厂
        HzFactory factory = hzFactoryDAO.findFactory("","1001");
        String puid = UUID.randomUUID().toString();
        if(factory == null) {
            factory = new HzFactory();
            factory.setPuid(puid);
            factory.setpFactoryCode("1001");
            factory.setpUpdateName(user.getUserName());
            factory.setpCreateName(user.getUserName());
            hzFactoryDAO.insert(factory);
        }

        //一次性同步所有数据，后期对应数据变化 进行分步 同步
        int m2=hzMaterielDAO.getHzMaterielCount(query.getProjectId(),21);
        int m3= hzMaterielDAO.getHzMaterielCount(query.getProjectId(),31);
        int m4 =hzMaterielDAO.getHzMaterielCount(query.getProjectId(),41);
        int m5 =hzMaterielDAO.getHzMaterielCount(query.getProjectId(),51);
        int m6 =hzMaterielDAO.getHzMaterielCount(query.getProjectId(),61);

        if(m2<=0){
            List<HzCfg0ModelRecord> cfg0ModelRecords = hzMaterielDAO.findHzCfg0ModelRecord(query);
            if(ListUtil.isNotEmpty(cfg0ModelRecords)){
                int size = cfg0ModelRecords.size();
                //分批插入数据 一次1000条
                int i =0;
                int cou = 0;
                if(size>1000){
                    for(i =0;i<size/1000;i++){
                        List<HzMaterielRecord> materielRecords = new ArrayList<>();
                        for(int j = 0;j<1000;j++){
                            HzCfg0ModelRecord record = cfg0ModelRecords.get(cou);
                            HzMaterielRecord hzMaterielRecord  =m2(record,query.getProjectId());
                            if(factory!=null){
                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                            }
                            else{
                                hzMaterielRecord.setpFactoryPuid(puid);
                            }
                            materielRecords.add(hzMaterielRecord);
                            cou++;
                        }
                        hzMaterielDAO.insertList(materielRecords);
                    }
                }
                if(i*1000<size){
                    List<HzMaterielRecord> materielRecords = new ArrayList<>();
                    for(int j = 0;j<size-i*1000;j++){
                        HzCfg0ModelRecord record = cfg0ModelRecords.get(cou);
                        HzMaterielRecord hzMaterielRecord  =m2(record,query.getProjectId());
                        if(factory!=null){
                            hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                        }
                        else{
                            hzMaterielRecord.setpFactoryPuid(puid);
                        }
                        materielRecords.add(hzMaterielRecord);
                        cou++;
                    }
                    hzMaterielDAO.insertList(materielRecords);
                }
            }
        }

        if(m3<=0){
            List<HzMbomLineRecord> hzMbomLineRecords = hzMaterielDAO.findHz2YMbomRecord(query);
            if(ListUtil.isNotEmpty(hzMbomLineRecords)){
                int size = hzMbomLineRecords.size();
                //分批插入数据 一次1000条
                int i =0;
                int cou = 0;
                if(size>1000){
                    for(i =0;i<size/1000;i++){
                        List<HzMaterielRecord> materielRecords = new ArrayList<>();
                        for(int j = 0;j<1000;j++){
                            HzMbomLineRecord record = hzMbomLineRecords.get(cou);
                            HzMaterielRecord hzMaterielRecord =m3(record,query.getProjectId());
                            if(factory!=null){
                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                            }
                            else{
                                hzMaterielRecord.setpFactoryPuid(puid);
                            }
                            materielRecords.add(hzMaterielRecord);
                            cou++;
                        }
                        hzMaterielDAO.insertList(materielRecords);
                    }
                }
                if(i*1000<size){
                    List<HzMaterielRecord> materielRecords = new ArrayList<>();
                    for(int j = 0;j<size-i*1000;j++){
                        HzMbomLineRecord record = hzMbomLineRecords.get(cou);
                        HzMaterielRecord hzMaterielRecord =m3(record,query.getProjectId());
                        if(factory!=null){
                            hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                        }
                        else{
                            hzMaterielRecord.setpFactoryPuid(puid);
                        }
                        materielRecords.add(hzMaterielRecord);
                        cou++;
                    }
                    hzMaterielDAO.insertList(materielRecords);
                }
            }
        }

        if(m4<=0){
            List<HzMbomLineRecord> lineRecords = hzMaterielDAO.findHzResourceMakeSingleMbomRecord(query);
            if(ListUtil.isNotEmpty(lineRecords)){
                int size = lineRecords.size();
                //分批插入数据 一次1000条
                int i =0;
                int cou = 0;
                if(size>1000){
                    for(i =0;i<size/1000;i++){
                        List<HzMaterielRecord> materielRecords = new ArrayList<>();
                        for(int j = 0;j<1000;j++){
                            HzMbomLineRecord record = lineRecords.get(cou);
                            HzMaterielRecord hzMaterielRecord =m4(record,query.getProjectId());
                            if(factory!=null){
                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                            }
                            else{
                                hzMaterielRecord.setpFactoryPuid(puid);
                            }
                            materielRecords.add(hzMaterielRecord);
                            cou++;
                        }
                        hzMaterielDAO.insertList(materielRecords);
                    }
                }
                if(i*1000<size){
                    List<HzMaterielRecord> materielRecords = new ArrayList<>();
                    for(int j = 0;j<size-i*1000;j++){
                        HzMbomLineRecord record = lineRecords.get(cou);
                        HzMaterielRecord hzMaterielRecord =m4(record,query.getProjectId());
                        if(factory!=null){
                            hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                        }
                        else{
                            hzMaterielRecord.setpFactoryPuid(puid);
                        }
                        materielRecords.add(hzMaterielRecord);
                        cou++;
                    }
                    hzMaterielDAO.insertList(materielRecords);
                }
            }
        }


        if(m5<=0){
            List<HzMbomLineRecord> lineRecordList = hzMaterielDAO.findHzResourceBuyMbomRecord(query);
            if(ListUtil.isNotEmpty(lineRecordList)){
                int size = lineRecordList.size();
                //分批插入数据 一次1000条
                int i =0;
                int cou = 0;
                if(size>1000){
                    for(i =0;i<size/1000;i++){
                        List<HzMaterielRecord> materielRecords = new ArrayList<>();
                        for(int j = 0;j<1000;j++){
                            HzMbomLineRecord record = lineRecordList.get(cou);
                            HzMaterielRecord hzMaterielRecord =m5(record,query.getProjectId());
                            if(factory!=null){
                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                            }
                            else{
                                hzMaterielRecord.setpFactoryPuid(puid);
                            }
                            materielRecords.add(hzMaterielRecord);
                            cou++;
                        }
                        hzMaterielDAO.insertList(materielRecords);
                    }
                }
                if(i*1000<size){
                    List<HzMaterielRecord> materielRecords = new ArrayList<>();
                    for(int j = 0;j<size-i*1000;j++){
                        HzMbomLineRecord record = lineRecordList.get(cou);
                        HzMaterielRecord hzMaterielRecord =m5(record,query.getProjectId());
                        if(factory!=null){
                            hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                        }
                        else{
                            hzMaterielRecord.setpFactoryPuid(puid);
                        }
                        materielRecords.add(hzMaterielRecord);
                        cou++;
                    }
                    hzMaterielDAO.insertList(materielRecords);
                }
            }
        }




        if(m6<=0){
            List<HzMbomLineRecord> mbomLineRecords = hzMaterielDAO.findHzMadeBySelfSpareMbomRecord(query);
            if(ListUtil.isNotEmpty(mbomLineRecords)){
                int size = mbomLineRecords.size();
                //分批插入数据 一次1000条
                int i =0;
                int cou = 0;
                if(size>1000){
                    for(i =0;i<size/1000;i++){
                        List<HzMaterielRecord> materielRecords = new ArrayList<>();
                        for(int j = 0;j<1000;j++){
                            HzMbomLineRecord record = mbomLineRecords.get(cou);
                            HzMaterielRecord hzMaterielRecord =m6(record,query.getProjectId());

                            if(factory!=null){
                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                            }
                            else{
                                hzMaterielRecord.setpFactoryPuid(puid);
                            }
                            materielRecords.add(hzMaterielRecord);
                            cou++;
                        }
                        hzMaterielDAO.insertList(materielRecords);
                    }
                }
                if(i*1000<size){
                    List<HzMaterielRecord> materielRecords = new ArrayList<>();
                    for(int j = 0;j<size-i*1000;j++){
                        HzMbomLineRecord record = mbomLineRecords.get(cou);
                        HzMaterielRecord hzMaterielRecord =m6(record,query.getProjectId());
                        if(factory!=null){
                            hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                        }
                        else{
                            hzMaterielRecord.setpFactoryPuid(puid);
                        }
                        materielRecords.add(hzMaterielRecord);
                        cou++;
                    }
                    hzMaterielDAO.insertList(materielRecords);
                }
            }
        }


        if(type!=null){
            List<HzMaterielRecord> list = new ArrayList<>();
            switch (type){
                case 11:
                    break;
                case 21:
                    List<HzCfg0ModelRecord> cfg0ModelRecords = hzMaterielDAO.findHzCfg0ModelRecord(query);
                    if(ListUtil.isNotEmpty(cfg0ModelRecords)){
                        for(HzCfg0ModelRecord record :cfg0ModelRecords){
                            Map<String,Object> map = new HashMap<>();
                            //外键id
                            map.put("",record.getPuid());
                            map.put("projectId",query.getProjectId());
                            boolean b = hzMaterielDAO.HzMaterielIsExist(map);
                            if(b){
                                continue;
                            }else{
                                HzMaterielRecord hzMaterielRecord = m2(record,query.getProjectId());
                                if(factory!=null){
                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                                }
                                else{
                                    hzMaterielRecord.setpFactoryPuid(puid);
                                }
                                list.add(hzMaterielRecord);
                            }
                        }
                        if(ListUtil.isNotEmpty(list)){
                            hzMaterielDAO.insertList(list);
                        }
                    }
                    break;

                case 31:
                    List<HzMbomLineRecord> hzMbomLineRecords = hzMaterielDAO.findHz2YMbomRecord(query);
                    if(ListUtil.isNotEmpty(hzMbomLineRecords)){
                        for(HzMbomLineRecord record :hzMbomLineRecords){
                            Map<String,Object> map = new HashMap<>();
                            map.put("",record.getPuid());
                            map.put("projectId",query.getProjectId());
                            boolean b = hzMaterielDAO.HzMaterielIsExist(map);
                            if(b){
                                continue;
                            }else{
                                HzMaterielRecord hzMaterielRecord = m3(record,query.getProjectId());

                                if(factory!=null){
                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                                }
                                else{
                                    hzMaterielRecord.setpFactoryPuid(puid);
                                }
                                list.add(hzMaterielRecord);
                            }
                        }
                        if(ListUtil.isNotEmpty(list)){
                            hzMaterielDAO.insertList(list);
                        }
                    }
                    break;
                case 41:
                    List<HzMbomLineRecord> lineRecords = hzMaterielDAO.findHzResourceMakeSingleMbomRecord(query);
                    if(ListUtil.isNotEmpty(lineRecords)){
                        for(HzMbomLineRecord record:lineRecords){
                            Map<String,Object> map = new HashMap<>();
                            map.put("",record.getPuid());
                            map.put("projectId",query.getProjectId());
                            boolean b = hzMaterielDAO.HzMaterielIsExist(map);
                            if(b){
                                continue;
                            }else{
                                HzMaterielRecord hzMaterielRecord = m4(record,query.getProjectId());

                                if(factory!=null){
                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                                }
                                else{
                                    hzMaterielRecord.setpFactoryPuid(puid);
                                }
                                list.add(hzMaterielRecord);
                            }
                        }
                        if(ListUtil.isNotEmpty(list)){
                            hzMaterielDAO.insertList(list);
                        }
                    }
                    break;
                case 51:
                    List<HzMbomLineRecord> lineRecordList = hzMaterielDAO.findHzResourceBuyMbomRecord(query);
                    if(ListUtil.isNotEmpty(lineRecordList)){
                        for(HzMbomLineRecord record:lineRecordList){
                            Map<String,Object> map = new HashMap<>();
                            map.put("",record.getPuid());
                            map.put("projectId",query.getProjectId());
                            boolean b = hzMaterielDAO.HzMaterielIsExist(map);
                            if(b){
                                continue;
                            }else{
                                HzMaterielRecord hzMaterielRecord = m5(record,query.getProjectId());
                                if(factory!=null){
                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                                }
                                else{
                                    hzMaterielRecord.setpFactoryPuid(puid);
                                }
                                list.add(hzMaterielRecord);
                            }
                        }
                        if(ListUtil.isNotEmpty(list)){
                            hzMaterielDAO.insertList(list);
                        }
                    }
                    break;
                case 61:
                    List<HzMbomLineRecord> mbomLineRecords = hzMaterielDAO.findHzMadeBySelfSpareMbomRecord(query);
                    if(ListUtil.isNotEmpty(mbomLineRecords)){
                        for(HzMbomLineRecord record:mbomLineRecords){
                            Map<String,Object> map = new HashMap<>();
                            map.put("",record.getPuid());
                            map.put("projectId",query.getProjectId());
                            boolean b = hzMaterielDAO.HzMaterielIsExist(map);
                            if(b){
                                continue;
                            }else{
                                HzMaterielRecord hzMaterielRecord = m6(record,query.getProjectId());
                                if(factory!=null){
                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                                }
                                else{
                                    hzMaterielRecord.setpFactoryPuid(puid);
                                }
                                list.add(hzMaterielRecord);
                            }
                        }
                        if(ListUtil.isNotEmpty(list)){
                            hzMaterielDAO.insertList(list);
                        }
                    }
                    break;
                default:break;
            }
        }

        Page<HzMaterielRecord> page = hzMaterielDAO.findHzMaterielForPage(query);
        if(page == null || page.getResult() == null){
            return new Page<>(query.getPage(),query.getLimit(),0);
        }
        try{
            List<HzMaterielRecord> recordList = page.getResult();
            List<HzMaterielRespDTO> respDTOS = new ArrayList<>();
            for(HzMaterielRecord record:recordList){
                HzMaterielRespDTO respDTO = recordToRespDTO(record);
                HzFactory hzFactory = hzFactoryDAO.findFactory(record.getpFactoryPuid(),"");
                if(factory !=null){
                    respDTO.setFactoryCode(hzFactory.getpFactoryCode());
                }
                respDTOS.add(respDTO);
            }
            return new Page<>(query.getPage(),query.getLimit(),page.getTotalCount(),respDTOS);
        }catch (Exception e){
            return null;
        }
    }



    @Override
    public HzMaterielRespDTO getHzMateriel(HzMaterielQuery query) {
        try{
            HzMaterielRecord record = hzMaterielDAO.getHzMaterielRecord(query);
            if(record!= null){
               return recordToRespDTO(record);
            }
        }catch (Exception e){
            return null;
        }
        return  null;
    }


    private HzMaterielRespDTO recordToRespDTO(HzMaterielRecord record){
        HzMaterielRespDTO respDTO = new HzMaterielRespDTO();
        respDTO.setpBasicUnitMeasure(record.getpBasicUnitMeasure());
        respDTO.setpMaterielCode(record.getpMaterielCode());
        respDTO.setpMaterielDesc(record.getpMaterielDesc());
        respDTO.setpMaterielDescEn(record.getpMaterielDescEn());
        respDTO.setpMaterielType(record.getpMaterielType());
        respDTO.setpMrpController(record.getpMrpController());
        respDTO.setPuid(record.getPuid());
        Integer p3CPartFlag = record.getP3cPartFlag();
        Integer colorPart = record.getpColorPart();
        Integer inOutSideFlag = record.getpInOutSideFlag();
        Integer inventedFlag = record.getpInventedPart();
        Integer loosePartFlag = record.getpLoosePartFlag();
        if (Integer.valueOf(0).equals(p3CPartFlag)) {
            respDTO.setP3cPartFlag("Y");
        } else if (Integer.valueOf(1).equals(p3CPartFlag)) {
            respDTO.setP3cPartFlag("N");
        } else {
            respDTO.setP3cPartFlag("/");
        }

        if (Integer.valueOf(0).equals(inventedFlag)) {
            respDTO.setpInventedPart("Y");
        } else if (Integer.valueOf(1).equals(inventedFlag)) {
            respDTO.setpInventedPart("N");
        } else {
            respDTO.setpInventedPart("/");
        }

        if (Integer.valueOf(0).equals(colorPart)) {
            respDTO.setpColorPart("Y");
        } else if (Integer.valueOf(1).equals(colorPart)) {
            respDTO.setpColorPart("N");
        } else {
            respDTO.setpColorPart("/");
        }

        if (Integer.valueOf(0).equals(inOutSideFlag)) {
            respDTO.setpInOutSideFlag("内饰件");
        } else if (Integer.valueOf(1).equals(inOutSideFlag)) {
            respDTO.setpInOutSideFlag("外饰件");
        } else {
            respDTO.setpInOutSideFlag("/");
        }

        if (Integer.valueOf(0).equals(loosePartFlag)) {
            respDTO.setpLoosePartFlag("Y");
        } else if (Integer.valueOf(1).equals(loosePartFlag)) {
            respDTO.setpLoosePartFlag("N");
        } else {
            respDTO.setpLoosePartFlag("/");
        }
        respDTO.setpHeight(record.getpHeight());
        respDTO.setpPartImportantDegree(record.getpPartImportantDegree());
        respDTO.setpSpareMaterial(record.getpSpareMaterial());
        respDTO.setpVinPerNo(record.getpVinPerNo());
        respDTO.setResource(record.getResource());
        return respDTO;
    }



    /** 物料类型转换**/
    private HzMaterielRecord m2(HzCfg0ModelRecord hzCfg0ModelRecord,String projectId){
        HzMaterielRecord record = new HzMaterielRecord();
        record.setpMaterielType("A001");
        record.setpMaterielCode(hzCfg0ModelRecord.getpCfg0ModelBasicDetail());
        record.setpMaterielDesc(hzCfg0ModelRecord.getObjectDesc());
        record.setpMaterielDescEn("");
        record.setpMaterielDataType(21);
        record.setpPertainToProjectPuid(projectId);
        record.setPuid(UUID.randomUUID().toString());
        record.setType(1);
        return record;

    }

    private HzMaterielRecord m3(HzMbomLineRecord hzMbomLineRecord,String projectId){
        HzMaterielRecord record = new HzMaterielRecord();
        record.setpMaterielCode(hzMbomLineRecord.getLineId());
        record.setpMaterielDesc(hzMbomLineRecord.getpBomLinePartName());
        record.setpMaterielDescEn(hzMbomLineRecord.getpBomLinePartEnName());
        record.setpMaterielType("A002");
        record.setpPertainToProjectPuid(projectId);
        record.setpMaterielDataType(31);
        record.setPuid(UUID.randomUUID().toString());
        return record;

    }

    private HzMaterielRecord m4(HzMbomLineRecord record,String projectId){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        hzMaterielRecord.setpMaterielCode(record.getLineId());
        hzMaterielRecord.setpMaterielDesc(record.getpBomLinePartName());
        hzMaterielRecord.setpMaterielDescEn(record.getpBomLinePartEnName());
        hzMaterielRecord.setpMaterielType("A002");
        hzMaterielRecord.setpPertainToProjectPuid(projectId);
        hzMaterielRecord.setpMaterielDataType(41);
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        return hzMaterielRecord;

    }

    private HzMaterielRecord m5(HzMbomLineRecord record,String projectId){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        hzMaterielRecord.setpMaterielCode(record.getLineId());
        hzMaterielRecord.setpMaterielDesc(record.getpBomLinePartName());
        hzMaterielRecord.setpMaterielDescEn(record.getpBomLinePartEnName());
        hzMaterielRecord.setpMaterielType("A002");
        hzMaterielRecord.setpPertainToProjectPuid(projectId);
        hzMaterielRecord.setpMaterielDataType(51);
        return hzMaterielRecord;

    }
    private HzMaterielRecord m6(HzMbomLineRecord record,String projectId){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        hzMaterielRecord.setpMaterielCode(record.getLineId());
        hzMaterielRecord.setpMaterielDesc(record.getpBomLinePartName());
        hzMaterielRecord.setpMaterielDescEn(record.getpBomLinePartEnName());
        hzMaterielRecord.setpMaterielType("A002");
        hzMaterielRecord.setpPertainToProjectPuid(projectId);
        hzMaterielRecord.setpMaterielDataType(61);
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        return hzMaterielRecord;

    }

}
