package com.connor.hozon.bom.resources.service.materiel.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzMaterielReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.domain.query.HzMbomByPageQuery;
import com.connor.hozon.bom.resources.enumtype.BomResourceEnum;
import com.connor.hozon.bom.resources.enumtype.MbomTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.materiel.HzMaterielService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;

import java.util.*;

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

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;
    @Override
    public OperateResultMessageRespDTO editHzMateriel(EditHzMaterielReqDTO editHzMaterielReqDTO) {
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        if(null == editHzMaterielReqDTO.getProjectId() || editHzMaterielReqDTO.getProjectId().equals("")){
            respDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
            respDTO.setErrMsg("请选择项目！");
            return respDTO;
        }
        try{
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            HzMaterielRecord record = new HzMaterielRecord();
            User user = UserInfo.getUser();
            if(!editHzMaterielReqDTO.getFactoryCode().equals("") && null != editHzMaterielReqDTO.getFactoryCode()){
                HzFactory hzFactory = hzFactoryDAO.findFactory("", editHzMaterielReqDTO.getFactoryCode());
                if(hzFactory == null){
                    String puid = UUID.randomUUID().toString();
                    hzFactory =  new HzFactory();
                    hzFactory.setPuid(puid);
                    hzFactory.setpFactoryCode(editHzMaterielReqDTO.getFactoryCode());
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
            }
            record.setpCreateName(user.getUserName());
            record.setpVinPerNo(editHzMaterielReqDTO.getpVinPerNo());
            record.setpSpareMaterial(editHzMaterielReqDTO.getpSpareMaterial());
            String loosePartFlag = editHzMaterielReqDTO.getpLoosePartFlag();
            if("N".equals(loosePartFlag.toUpperCase())){
                record.setpLoosePartFlag(0);
            }else if("Y".equals(loosePartFlag.toUpperCase())){
                record.setpLoosePartFlag(1);
            }else{
                record.setpLoosePartFlag(null);
            }
            record.setpMrpController(editHzMaterielReqDTO.getpMrpController());
            record.setpUpdateName(user.getUserName());
            record.setPuid(editHzMaterielReqDTO.getPuid());
            record.setpBasicUnitMeasure(editHzMaterielReqDTO.getpBasicUnitMeasure());
            if("Y".equals(editHzMaterielReqDTO.getpInventedPart().toUpperCase())){
                record.setpInventedPart(1);
            }else if("N".equals(editHzMaterielReqDTO.getpInventedPart().toUpperCase())){
                record.setpInventedPart(0);
            }else {
                record.setpInventedPart(null);
            }
            record.setResource(editHzMaterielReqDTO.getResource());
            if("Y".equals(editHzMaterielReqDTO.getpColorPart().toUpperCase())){
                record.setpColorPart(1);
            }else if("N".equals(editHzMaterielReqDTO.getpColorPart().toUpperCase())){
                record.setpColorPart(0);
            }else {
                record.setpColorPart(null);
            }
            record.setpHeight(editHzMaterielReqDTO.getpHeight());

            if("内饰件".equals(editHzMaterielReqDTO.getpInOutSideFlag())){
                record.setpInOutSideFlag(1);
            }else if("外饰件".equals(editHzMaterielReqDTO.getpInOutSideFlag())){
                record.setpInOutSideFlag(0);
            }else {
                record.setpInOutSideFlag(null);
            }

            if("Y".equals(editHzMaterielReqDTO.getP3cPartFlag().toUpperCase())){
                record.setP3cPartFlag(1);
            }else if("N".equals(editHzMaterielReqDTO.getP3cPartFlag().toUpperCase())){
                record.setP3cPartFlag(0);
            }else {
                record.setP3cPartFlag(null);
            }
            record.setpPartImportantDegree(editHzMaterielReqDTO.getpPartImportantDegree());
            int i = hzMaterielDAO.update(record);
            if(i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
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
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
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
        try{
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
            int m7 = hzMaterielDAO.getHzMaterielCount(query.getProjectId(),71);
            if(m2<=0){
                List<HzCfg0ModelRecord> cfg0ModelRecords = hzMaterielDAO.findHzCfg0ModelRecordAll(query);
                if(ListUtil.isNotEmpty(cfg0ModelRecords)){
                    int size = cfg0ModelRecords.size();
                    //分批插入数据 一次1000条
                    int i =0;
                    int cou = 0;
                    if(size>1000){
                        for(i =0;i<size/1000;i++){
                            List<HzMaterielRecord> materielRecords2 = new ArrayList<>();
                            for(int j = 0;j<1000;j++){
                                HzCfg0ModelRecord record = cfg0ModelRecords.get(cou);
                                HzMaterielRecord hzMaterielRecord  =m2(record,query.getProjectId());
                                if(factory!=null){
                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                                }
                                else{
                                    hzMaterielRecord.setpFactoryPuid(puid);
                                }
                                materielRecords2.add(hzMaterielRecord);
                                cou++;
                            }
                            hzMaterielDAO.insertList(materielRecords2);
                        }
                    }
                    if(i*1000<size){
                        List<HzMaterielRecord> materielRecords2 = new ArrayList<>();
                        for(int j = 0;j<size-i*1000;j++){
                            HzCfg0ModelRecord record = cfg0ModelRecords.get(cou);
                            HzMaterielRecord hzMaterielRecord  =m2(record,query.getProjectId());
                            if(factory!=null){
                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
                            }
                            else{
                                hzMaterielRecord.setpFactoryPuid(puid);
                            }
                            materielRecords2.add(hzMaterielRecord);
                            cou++;
                        }
                        hzMaterielDAO.insertList(materielRecords2);
                    }
                }
            }

            int m = m3+m4+m5+m6+m7;
            if(m<=0){
                List<HzMbomLineRecord> records= hzMbomRecordDAO.findHzMbomAll(query.getProjectId(),MbomTableNameEnum.tableName(0));
                if(ListUtil.isNotEmpty(records)){
                    int size = records.size();
                    //分批插入数据 一次1000条
                    int i =0;
                    int cou = 0;
                    if(size>1000){
                        for(i =0;i<size/1000;i++){
                            List<HzMaterielRecord> materielRecords = new ArrayList<>();
                            for(int j = 0;j<1000;j++){
                                HzMbomLineRecord record = records.get(cou);
                                HzMaterielRecord hzMaterielRecord =m(record,query.getProjectId());
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
                            HzMbomLineRecord record = records.get(cou);
                            HzMaterielRecord hzMaterielRecord =m(record,query.getProjectId());
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




//            if(m3<=0){
//                HzMbomByPageQuery hzMbomByPageQuery = new HzMbomByPageQuery();
//                hzMbomByPageQuery.setProjectId(query.getProjectId());
//                List<HzMbomLineRecord> hzMbomLineRecords = hzMbomRecordDAO.findHz2YMbomRecordAll(hzMbomByPageQuery);
//                if(ListUtil.isNotEmpty(hzMbomLineRecords)){
//                    int size = hzMbomLineRecords.size();
//                    //分批插入数据 一次1000条
//                    int i =0;
//                    int cou = 0;
//                    if(size>1000){
//                        for(i =0;i<size/1000;i++){
//                            List<HzMaterielRecord> materielRecords3 = new ArrayList<>();
//                            for(int j = 0;j<1000;j++){
//                                HzMbomLineRecord record = hzMbomLineRecords.get(cou);
//                                HzMaterielRecord hzMaterielRecord =m3(record,query.getProjectId());
//                                if(factory!=null){
//                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                }
//                                else{
//                                    hzMaterielRecord.setpFactoryPuid(puid);
//                                }
//                                materielRecords3.add(hzMaterielRecord);
//                                cou++;
//                            }
//                            hzMaterielDAO.insertList(materielRecords3);
//                        }
//                    }
//                    if(i*1000<size){
//                        List<HzMaterielRecord> materielRecords3 = new ArrayList<>();
//                        for(int j = 0;j<size-i*1000;j++){
//                            HzMbomLineRecord record = hzMbomLineRecords.get(cou);
//                            HzMaterielRecord hzMaterielRecord =m3(record,query.getProjectId());
//                            if(factory!=null){
//                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                            }
//                            else{
//                                hzMaterielRecord.setpFactoryPuid(puid);
//                            }
//                            materielRecords3.add(hzMaterielRecord);
//                            cou++;
//                        }
//                        hzMaterielDAO.insertList(materielRecords3);
//                    }
//                }
//            }
//
//            if(m4<=0){
//                HzMbomByPageQuery hzMbomByPageQuery = new HzMbomByPageQuery();
//                hzMbomByPageQuery.setProjectId(query.getProjectId());
//                hzMbomByPageQuery.setpBomLinePartResource("自制单件");
//                List<HzMbomLineRecord> lineRecords = hzMbomRecordDAO.findHzMbomByResourceAll(hzMbomByPageQuery);
//                if(ListUtil.isNotEmpty(lineRecords)){
//                    int size = lineRecords.size();
//                    //分批插入数据 一次1000条
//                    int i =0;
//                    int cou = 0;
//                    if(size>1000){
//                        for(i =0;i<size/1000;i++){
//                            List<HzMaterielRecord> materielRecords4 = new ArrayList<>();
//                            for(int j = 0;j<1000;j++){
//                                HzMbomLineRecord record = lineRecords.get(cou);
//                                HzMaterielRecord hzMaterielRecord =m4(record,query.getProjectId());
//                                if(factory!=null){
//                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                }
//                                else{
//                                    hzMaterielRecord.setpFactoryPuid(puid);
//                                }
//                                materielRecords4.add(hzMaterielRecord);
//                                cou++;
//                            }
//                            hzMaterielDAO.insertList(materielRecords4);
//                        }
//                    }
//                    if(i*1000<size){
//                        List<HzMaterielRecord> materielRecords4 = new ArrayList<>();
//                        for(int j = 0;j<size-i*1000;j++){
//                            HzMbomLineRecord record = lineRecords.get(cou);
//                            HzMaterielRecord hzMaterielRecord =m4(record,query.getProjectId());
//                            if(factory!=null){
//                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                            }
//                            else{
//                                hzMaterielRecord.setpFactoryPuid(puid);
//                            }
//                            materielRecords4.add(hzMaterielRecord);
//                            cou++;
//                        }
//                        hzMaterielDAO.insertList(materielRecords4);
//                    }
//                }
//            }
//
//
//            if(m5<=0){
//                HzMbomByPageQuery hzMbomByPageQuery = new HzMbomByPageQuery();
//                hzMbomByPageQuery.setProjectId(query.getProjectId());
//                hzMbomByPageQuery.setpBomLinePartResource("采购件");
//                List<HzMbomLineRecord> lineRecordList = hzMbomRecordDAO.findHzMbomByResourceAll(hzMbomByPageQuery);
//                if(ListUtil.isNotEmpty(lineRecordList)){
//                    int size = lineRecordList.size();
//                    //分批插入数据 一次1000条
//                    int i =0;
//                    int cou = 0;
//                    if(size>1000){
//                        for(i =0;i<size/1000;i++){
//                            List<HzMaterielRecord> materielRecords5 = new ArrayList<>();
//                            for(int j = 0;j<1000;j++){
//                                HzMbomLineRecord record = lineRecordList.get(cou);
//                                HzMaterielRecord hzMaterielRecord =m5(record,query.getProjectId());
//                                if(factory!=null){
//                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                }
//                                else{
//                                    hzMaterielRecord.setpFactoryPuid(puid);
//                                }
//                                materielRecords5.add(hzMaterielRecord);
//                                cou++;
//                            }
//                            hzMaterielDAO.insertList(materielRecords5);
//                        }
//                    }
//                    if(i*1000<size){
//                        List<HzMaterielRecord> materielRecords5 = new ArrayList<>();
//                        for(int j = 0;j<size-i*1000;j++){
//                            HzMbomLineRecord record = lineRecordList.get(cou);
//                            HzMaterielRecord hzMaterielRecord =m5(record,query.getProjectId());
//                            if(factory!=null){
//                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                            }
//                            else{
//                                hzMaterielRecord.setpFactoryPuid(puid);
//                            }
//                            materielRecords5.add(hzMaterielRecord);
//                            cou++;
//                        }
//                        hzMaterielDAO.insertList(materielRecords5);
//                    }
//                }
//            }
//
//
//
//
//            if(m6<=0){
//                HzMbomByPageQuery hzMbomByPageQuery = new HzMbomByPageQuery();
//                hzMbomByPageQuery.setProjectId(query.getProjectId());
//                hzMbomByPageQuery.setSparePart("自制");
//                List<HzMbomLineRecord> mbomLineRecords = hzMbomRecordDAO.findHzMbomByResourceAll(hzMbomByPageQuery);
//                if(ListUtil.isNotEmpty(mbomLineRecords)){
//                    int size = mbomLineRecords.size();
//                    //分批插入数据 一次1000条
//                    int i =0;
//                    int cou = 0;
//                    if(size>1000){
//                        for(i =0;i<size/1000;i++){
//                            List<HzMaterielRecord> materielRecords6 = new ArrayList<>();
//                            for(int j = 0;j<1000;j++){
//                                HzMbomLineRecord record = mbomLineRecords.get(cou);
//                                HzMaterielRecord hzMaterielRecord =m6(record,query.getProjectId());
//
//                                if(factory!=null){
//                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                }
//                                else{
//                                    hzMaterielRecord.setpFactoryPuid(puid);
//                                }
//                                materielRecords6.add(hzMaterielRecord);
//                                cou++;
//                            }
//                            hzMaterielDAO.insertList(materielRecords6);
//                        }
//                    }
//                    if(i*1000<size){
//                        List<HzMaterielRecord> materielRecords6 = new ArrayList<>();
//                        for(int j = 0;j<size-i*1000;j++){
//                            HzMbomLineRecord record = mbomLineRecords.get(cou);
//                            HzMaterielRecord hzMaterielRecord =m6(record,query.getProjectId());
//                            if(factory!=null){
//                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                            }
//                            else{
//                                hzMaterielRecord.setpFactoryPuid(puid);
//                            }
//                            materielRecords6.add(hzMaterielRecord);
//                            cou++;
//                        }
//                        hzMaterielDAO.insertList(materielRecords6);
//                    }
//                }
//            }
//
//            if(m7<=0){
//                HzMbomByPageQuery hzMbomByPageQuery = new HzMbomByPageQuery();
//                hzMbomByPageQuery.setProjectId(query.getProjectId());
//                hzMbomByPageQuery.setpBomLinePartResource("自制总成");
//                List<HzMbomLineRecord> hzMbomLineRecords = hzMbomRecordDAO.findHzMbomByResourceAll(hzMbomByPageQuery);
//                if(ListUtil.isNotEmpty(hzMbomLineRecords)){
//                    int size = hzMbomLineRecords.size();
//                    //分批插入数据 一次1000条
//                    int i =0;
//                    int cou = 0;
//                    if(size>1000){
//                        for(i =0;i<size/1000;i++){
//                            List<HzMaterielRecord> materielRecords7 = new ArrayList<>();
//                            for(int j = 0;j<1000;j++){
//                                HzMbomLineRecord record = hzMbomLineRecords.get(cou);
//                                HzMaterielRecord hzMaterielRecord  =m7(record,query.getProjectId());
//                                if(factory!=null){
//                                    hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                }
//                                else{
//                                    hzMaterielRecord.setpFactoryPuid(puid);
//                                }
//                                materielRecords7.add(hzMaterielRecord);
//                                cou++;
//                            }
//                            hzMaterielDAO.insertList(materielRecords7);
//                        }
//                    }
//                    if(i*1000<size){
//                        List<HzMaterielRecord> materielRecords7 = new ArrayList<>();
//                        for(int j = 0;j<size-i*1000;j++){
//                            HzMbomLineRecord record = hzMbomLineRecords.get(cou);
//                            HzMaterielRecord hzMaterielRecord  =m7(record,query.getProjectId());
//                            if(factory!=null){
//                                hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                            }
//                            else{
//                                hzMaterielRecord.setpFactoryPuid(puid);
//                            }
//                            materielRecords7.add(hzMaterielRecord);
//                            cou++;
//                        }
//                        hzMaterielDAO.insertList(materielRecords7);
//                    }
//                }
//            }

            if(type!=null){
                List<HzMaterielRecord> list = new ArrayList<>();
                switch (type){
                    case 11:
//                        HzMbomByPageQuery hzMbomByPageQuery = new HzMbomByPageQuery();
//                        hzMbomByPageQuery.setProjectId(query.getProjectId());
//                        hzMbomByPageQuery.setpBomLinePartResource("自制总成");
//                        List<HzMbomLineRecord> records = hzMbomRecordDAO.findHzMbomByResourceAll(hzMbomByPageQuery);
//                        if(ListUtil.isNotEmpty(records)){
//                            for(HzMbomLineRecord record :records){
//                                Map<String,Object> map = new HashMap<>();
//                                map.put("materielResourceId",record.geteBomPuid());
//                                map.put("projectId",query.getProjectId());
//                                boolean b = hzMaterielDAO.HzMaterielIsExist(map);
//                                if(b){
//                                    continue;
//                                }else{
//                                    HzMaterielRecord hzMaterielRecord = m7(record,query.getProjectId());
//                                    if(factory!=null){
//                                        hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                    }
//                                    else{
//                                        hzMaterielRecord.setpFactoryPuid(puid);
//                                    }
//                                    list.add(hzMaterielRecord);
//                                }
//                            }
//                            if(ListUtil.isNotEmpty(list)){
//                                hzMaterielDAO.insertList(list);
//                            }
//                        }
                        break;
                    case 21:
                        List<HzCfg0ModelRecord> cfg0ModelRecords = hzMaterielDAO.findHzCfg0ModelRecordAll(query);
                        if(ListUtil.isNotEmpty(cfg0ModelRecords)){
                            for(HzCfg0ModelRecord record :cfg0ModelRecords){
                                Map<String,Object> map = new HashMap<>();
                                //外键id
                                map.put("materielResourceId",record.getPuid());
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
//                        HzMbomByPageQuery mbomByPageQuery = new HzMbomByPageQuery();
//                        mbomByPageQuery.setProjectId(query.getProjectId());
//                        List<HzMbomLineRecord> hzMbomLineRecords = hzMbomRecordDAO.findHz2YMbomRecordAll(mbomByPageQuery);
//                        if(ListUtil.isNotEmpty(hzMbomLineRecords)){
//                            for(HzMbomLineRecord record :hzMbomLineRecords){
//                                Map<String,Object> map = new HashMap<>();
//                                map.put("materielResourceId",record.geteBomPuid());
//                                map.put("projectId",query.getProjectId());
//                                boolean b = hzMaterielDAO.HzMaterielIsExist(map);
//                                if(b){
//                                    continue;
//                                }else{
//                                    HzMaterielRecord hzMaterielRecord = m3(record,query.getProjectId());
//
//                                    if(factory!=null){
//                                        hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                    }
//                                    else{
//                                        hzMaterielRecord.setpFactoryPuid(puid);
//                                    }
//                                    list.add(hzMaterielRecord);
//                                }
//                            }
//                            if(ListUtil.isNotEmpty(list)){
//                                hzMaterielDAO.insertList(list);
//                            }
//                        }
                        break;
                    case 41:
//                        HzMbomByPageQuery mbom = new HzMbomByPageQuery();
//                        mbom.setProjectId(query.getProjectId());
//                        mbom.setpBomLinePartResource("自制单件");
//                        List<HzMbomLineRecord> lineRecords = hzMbomRecordDAO.findHzMbomByResourceAll(mbom);
//                        if(ListUtil.isNotEmpty(lineRecords)){
//                            for(HzMbomLineRecord record:lineRecords){
//                                Map<String,Object> map = new HashMap<>();
//                                map.put("materielResourceId",record.geteBomPuid());
//                                map.put("projectId",query.getProjectId());
//                                boolean b = hzMaterielDAO.HzMaterielIsExist(map);
//                                if(b){
//                                    continue;
//                                }else{
//                                    HzMaterielRecord hzMaterielRecord = m4(record,query.getProjectId());
//
//                                    if(factory!=null){
//                                        hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                    }
//                                    else{
//                                        hzMaterielRecord.setpFactoryPuid(puid);
//                                    }
//                                    list.add(hzMaterielRecord);
//                                }
//                            }
//                            if(ListUtil.isNotEmpty(list)){
//                                hzMaterielDAO.insertList(list);
//                            }
//                        }
//                        break;
//                    case 51:
//                        HzMbomByPageQuery byPageQuery = new HzMbomByPageQuery();
//                        byPageQuery.setProjectId(query.getProjectId());
//                        byPageQuery.setpBomLinePartResource("采购件");
//                        List<HzMbomLineRecord> lineRecordList = hzMbomRecordDAO.findHzMbomByResourceAll(byPageQuery);
//                        if(ListUtil.isNotEmpty(lineRecordList)){
//                            for(HzMbomLineRecord record:lineRecordList){
//                                Map<String,Object> map = new HashMap<>();
//                                map.put("materielResourceId",record.geteBomPuid());
//                                map.put("projectId",query.getProjectId());
//                                boolean b = hzMaterielDAO.HzMaterielIsExist(map);
//                                if(b){
//                                    continue;
//                                }else{
//                                    HzMaterielRecord hzMaterielRecord = m5(record,query.getProjectId());
//                                    if(factory!=null){
//                                        hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                    }
//                                    else{
//                                        hzMaterielRecord.setpFactoryPuid(puid);
//                                    }
//                                    list.add(hzMaterielRecord);
//                                }
//                            }
//                            if(ListUtil.isNotEmpty(list)){
//                                hzMaterielDAO.insertList(list);
//                            }
//                        }
//                        break;
//                    case 61:
//                        hzMbomByPageQuery = new HzMbomByPageQuery();
//                        hzMbomByPageQuery.setProjectId(query.getProjectId());
//                        hzMbomByPageQuery.setSparePart("自制");
//                        List<HzMbomLineRecord> mbomLineRecords = hzMbomRecordDAO.findHzMbomByResourceAll(hzMbomByPageQuery);
//                        if(ListUtil.isNotEmpty(mbomLineRecords)){
//                            for(HzMbomLineRecord record:mbomLineRecords){
//                                Map<String,Object> map = new HashMap<>();
//                                map.put("materielResourceId",record.geteBomPuid());
//                                map.put("projectId",query.getProjectId());
//                                boolean b = hzMaterielDAO.HzMaterielIsExist(map);
//                                if(b){
//                                    continue;
//                                }else{
//                                    HzMaterielRecord hzMaterielRecord = m6(record,query.getProjectId());
//                                    if(factory!=null){
//                                        hzMaterielRecord.setpFactoryPuid(factory.getPuid());
//                                    }
//                                    else{
//                                        hzMaterielRecord.setpFactoryPuid(puid);
//                                    }
//                                    list.add(hzMaterielRecord);
//                                }
//                            }
//                            if(ListUtil.isNotEmpty(list)){
//                                hzMaterielDAO.insertList(list);
//                            }
//                        }
                        break;
                    default:break;
                }
            }
            Page<HzMaterielRecord> page = hzMaterielDAO.findHzMaterielForPage(query);
            int num = (page.getPageNumber()-1)*page.getPageSize();
            if(page == null || page.getResult() == null){
                return new Page<>(page.getPageNumber(),page.getPageSize(),0);
            }

            List<HzMaterielRecord> recordList = page.getResult();
            List<HzMaterielRespDTO> respDTOS = new ArrayList<>();
            for(HzMaterielRecord record:recordList){
                HzMaterielRespDTO respDTO = recordToRespDTO(record);
                respDTO.setNo(++num);
                HzFactory hzFactory = hzFactoryDAO.findFactory(record.getpFactoryPuid(),"");
                if(factory !=null){
                    respDTO.setFactoryCode(hzFactory.getpFactoryCode());
                }
                respDTOS.add(respDTO);
            }
            return new Page<>(page.getPageNumber(),page.getPageSize(),page.getTotalCount(),respDTOS);
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


        if(record.getpFactoryPuid() != null){
            HzFactory hzFactory = hzFactoryDAO.findFactory(record.getpFactoryPuid(),"");
            if(hzFactory !=null){
                respDTO.setFactoryCode(hzFactory.getpFactoryCode());
            }else{
                respDTO.setFactoryCode("1001");
            }
        }else {
            respDTO.setFactoryCode("1001");
        }
        respDTO.setPuid(record.getPuid());
        Integer p3CPartFlag = record.getP3cPartFlag();
        Integer colorPart = record.getpColorPart();
        Integer inOutSideFlag = record.getpInOutSideFlag();
        Integer inventedFlag = record.getpInventedPart();
        Integer loosePartFlag = record.getpLoosePartFlag();
        if (Integer.valueOf(0).equals(p3CPartFlag)) {
            respDTO.setP3cPartFlag("N");
        } else if (Integer.valueOf(1).equals(p3CPartFlag)) {
            respDTO.setP3cPartFlag("Y");
        } else {
            respDTO.setP3cPartFlag("-");
        }

        if (Integer.valueOf(0).equals(inventedFlag)) {
            respDTO.setpInventedPart("N");
        } else if (Integer.valueOf(1).equals(inventedFlag)) {
            respDTO.setpInventedPart("Y");
        } else {
            respDTO.setpInventedPart("-");
        }

        if (Integer.valueOf(0).equals(colorPart)) {
            respDTO.setpColorPart("N");
        } else if (Integer.valueOf(1).equals(colorPart)) {
            respDTO.setpColorPart("Y");
        } else {
            respDTO.setpColorPart("-");
        }

        if (Integer.valueOf(1).equals(inOutSideFlag)) {
            respDTO.setpInOutSideFlag("内饰件");
        } else if (Integer.valueOf(0).equals(inOutSideFlag)) {
            respDTO.setpInOutSideFlag("外饰件");
        } else {
            respDTO.setpInOutSideFlag("-");
        }

        if (Integer.valueOf(1).equals(loosePartFlag)) {
            respDTO.setpLoosePartFlag("Y");
        } else if (Integer.valueOf(0).equals(loosePartFlag)) {
            respDTO.setpLoosePartFlag("N");
        } else {
            respDTO.setpLoosePartFlag("-");
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
        record.setMaterielResourceId(hzCfg0ModelRecord.getPuid());
        return record;

    }

    private HzMaterielRecord m(HzMbomLineRecord hzMbomLineRecord,String projectId){
        HzMaterielRecord record = new HzMaterielRecord();
        record.setpMaterielCode(hzMbomLineRecord.getLineId());
        record.setpMaterielDesc(hzMbomLineRecord.getpBomLinePartName());
        record.setpMaterielDescEn(hzMbomLineRecord.getpBomLinePartEnName());
        record.setpMaterielType("A002");
        record.setpPertainToProjectPuid(projectId);
        record.setpMaterielDataType(BomResourceEnum.enumTypeToMaterielTypeNum(hzMbomLineRecord.getpBomLinePartResource(),hzMbomLineRecord.getIs2Y()));
        if(record.getpMaterielDataType().equals(71)){
            record.setType(2);
        }
        record.setPuid(UUID.randomUUID().toString());
        record.setMaterielResourceId(hzMbomLineRecord.geteBomPuid());
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
        hzMaterielRecord.setType(2);
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        hzMaterielRecord.setMaterielResourceId(record.geteBomPuid());
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
        hzMaterielRecord.setMaterielResourceId(record.geteBomPuid());
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        return hzMaterielRecord;

    }
    private HzMaterielRecord m6(HzMbomLineRecord record,String projectId){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        String lineId = record.getLineId();
        lineId+="-DY";
        hzMaterielRecord.setpMaterielCode(lineId);
        hzMaterielRecord.setpMaterielDesc(record.getpBomLinePartName());
        hzMaterielRecord.setpMaterielDescEn(record.getpBomLinePartEnName());
        hzMaterielRecord.setpMaterielType("A002");
        hzMaterielRecord.setpPertainToProjectPuid(projectId);
        hzMaterielRecord.setpMaterielDataType(61);
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        hzMaterielRecord.setMaterielResourceId(record.geteBomPuid());
        return hzMaterielRecord;

    }

    private HzMaterielRecord m7(HzMbomLineRecord record,String projectId){
        HzMaterielRecord hzMaterielRecord = new HzMaterielRecord();
        hzMaterielRecord.setpMaterielCode(record.getLineId());
        hzMaterielRecord.setpMaterielDesc(record.getpBomLinePartName());
        hzMaterielRecord.setpMaterielDescEn(record.getpBomLinePartEnName());
        hzMaterielRecord.setpMaterielType("");
        hzMaterielRecord.setpPertainToProjectPuid(projectId);
        hzMaterielRecord.setpMaterielDataType(71);
        hzMaterielRecord.setPuid(UUID.randomUUID().toString());
        hzMaterielRecord.setMaterielResourceId(record.geteBomPuid());
        hzMaterielRecord.setType(2);
        return hzMaterielRecord;

    }
}
