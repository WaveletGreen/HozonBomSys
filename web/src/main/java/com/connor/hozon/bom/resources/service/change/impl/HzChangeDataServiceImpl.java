package com.connor.hozon.bom.resources.service.change.impl;

import com.alibaba.fastjson.JSONObject;
import cn.net.connor.hozon.services.service.configuration.fullConfigSheet.impl.HzCfg0ModelServiceImpl;
import com.connor.hozon.bom.resources.domain.constant.ChangeConstants;
import com.connor.hozon.bom.resources.domain.dto.request.BomBackReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.*;
import com.connor.hozon.bom.resources.domain.model.*;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataQuery;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.enumtype.ChangeTypeEnum;
import com.connor.hozon.bom.resources.enumtype.TableNameToHyperLinkNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.service.change.HzChangeDataService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomLineRecordVO;
import sql.pojo.bom.HzPbomLineRecord;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.change.HzChangeOrderRecord;
import sql.pojo.epl.HzEPLManageRecord;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;
import sql.pojo.work.HzWorkProcedure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:
 */
@Service("hzChangeDataService")
public class HzChangeDataServiceImpl implements HzChangeDataService {

    private ExecutorService pool = Executors.newFixedThreadPool(10);

    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzCfg0ModelServiceImpl hzCfg0ModelServiceImpl;


    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzMaterielDAO hzMaterielDAO;

    @Autowired
    private HzWorkProcedureDAO hzWorkProcedureDAO;

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;
    @Override
    public List<HzChangeDataRespDTO> getChangeDataHyperRecord(HzChangeDataQuery query) {
        try {
            List<HzChangeDataRecord> records = hzChangeDataRecordDAO.getChangeDataTableName(query);
            HzChangeDataRespDTO respDTO = new HzChangeDataRespDTO();
            List<HzChangeDataRespDTO> respDTOS = respDTO.init();
            if(ListUtil.isNotEmpty(records)){
                for(HzChangeDataRespDTO dto:respDTOS){
                    dto.setOrderId(query.getOrderId());
                    for(HzChangeDataRecord record : records){
                        if(dto.getName().equals(TableNameToHyperLinkNameEnum.getHyperLinkName(record.getTableName()))){
                            dto.setStatus(1);
                            break;
                        }
                    }
                }
            }
            return respDTOS;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HzEbomRespDTO> getChangeDataRecordForEBOM(HzChangeDataQuery query) {
        List<HzEbomRespDTO> respDTOs = new ArrayList<>();
        if(null == query.getOrderId()){
            return new ArrayList<>();
        }
        query.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
        if(StringUtils.isBlank(query.getProjectId())){
            HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordById(query.getOrderId());
            query.setProjectId(record.getProjectId());
        }
        try {
            Future future = pool.submit(()->{
                //变更数据
//                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
                updateQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
//                updateQuery.setPuids(puids);
                List<HzEPLManageRecord> afterRecords = hzEbomRecordDAO.getEbomRecordsByOrderId(updateQuery);
                List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelServiceImpl.doSelectByProjectPuid(query.getProjectId());
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzEPLManageRecord record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_BEFORE.getTableName());
                        beforeUpdateQuery.setStatus(ChangeConstants.EFFECT_STATUS);
                        beforeUpdateQuery.setRevision(true);
                        beforeUpdateQuery.setRevisionNo(record.getRevision());
                        beforeUpdateQuery.setPuid(record.getPuid());
                        HzEPLManageRecord eplManageRecord = hzEbomRecordDAO.getEBomRecordByPuidAndRevision(beforeUpdateQuery);

                        if(eplManageRecord != null){
                            HzEbomRespDTO beforeRecord = HzEbomRecordFactory.eplRecordToEbomRespDTO(eplManageRecord);
                            beforeRecord.setChangeType(ChangeTypeEnum.BU.getChangeType());
                            JSONObject jsonObject = hzSingleVehiclesServices.singleVehNum(eplManageRecord.getVehNum(),hzCfg0ModelRecords);
                            beforeRecord.setMap(jsonObject);
                            respDTOs.add(beforeRecord);
                        }
                        HzEbomRespDTO afterRespDTO = HzEbomRecordFactory.eplRecordToEbomRespDTO(record);
                        afterRespDTO.setChangeType(ChangeTypeEnum.AU.getChangeType());
                        JSONObject jsonObject = hzSingleVehiclesServices.singleVehNum(record.getVehNum(),hzCfg0ModelRecords);
                        afterRespDTO.setMap(jsonObject);
                        respDTOs.add(afterRespDTO);
                    }
                }
                //查新增的数据（无版本号 状态值为2）
                HzChangeDataDetailQuery addQuery = new HzChangeDataDetailQuery();
                addQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
                addQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
//                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzEPLManageRecord> addRecords = hzEbomRecordDAO.getEbomRecordsByOrderId(addQuery);
                if(ListUtil.isNotEmpty(addRecords)){
                    for(HzEPLManageRecord record :addRecords){
                        HzEbomRespDTO addRespDTO = HzEbomRecordFactory.eplRecordToEbomRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.A.getChangeType());
                        JSONObject jsonObject = hzSingleVehiclesServices.singleVehNum(record.getVehNum(),hzCfg0ModelRecords);
                        addRespDTO.setMap(jsonObject);
                        respDTOs.add(addRespDTO);
                    }
                }
                //查删除的数据（状态值为4）

                HzChangeDataDetailQuery deleteQuery = new HzChangeDataDetailQuery();
                deleteQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
                deleteQuery.setStatus(ChangeConstants.DELETE_STATUS);
                deleteQuery.setOrderId(query.getOrderId());
//                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzEPLManageRecord> deleteRecords = hzEbomRecordDAO.getEbomRecordsByOrderId(deleteQuery);
                if(ListUtil.isNotEmpty(deleteRecords)){
                    for(HzEPLManageRecord record :deleteRecords){
                        HzEbomRespDTO deleteRespDTO = HzEbomRecordFactory.eplRecordToEbomRespDTO(record);
                        deleteRespDTO.setChangeType(ChangeTypeEnum.D.getChangeType());
                        JSONObject jsonObject = hzSingleVehiclesServices.singleVehNum(record.getVehNum(),hzCfg0ModelRecords);
                        deleteRespDTO.setMap(jsonObject);
                        respDTOs.add(deleteRespDTO);
                    }
                }
                return respDTOs;
            });
            future.get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return respDTOs;
    }

    @Override
    public List<HzPbomLineRespDTO> getChangeDataRecordForPBOM(HzChangeDataQuery query) {
        if(null == query.getOrderId()){
            return null;
        }

        List<HzPbomLineRespDTO> respDTOs = new ArrayList<>();
        query.setTableName(ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
        if(StringUtils.isBlank(query.getProjectId())){
            HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordById(query.getOrderId());
            query.setProjectId(record.getProjectId());
        }
        try {
            Future future = pool.submit(()->{
                //变更数据
//                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
                updateQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
//                updateQuery.setPuids(puids);
                List<HzPbomLineRecord> afterRecords = hzPbomRecordDAO.getPbomRecordsByOrderId(updateQuery);
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzPbomLineRecord record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.HZ_PBOM_BEFORE.getTableName());
                        beforeUpdateQuery.setStatus(ChangeConstants.EFFECT_STATUS);
                        beforeUpdateQuery.setRevision(true);
                        beforeUpdateQuery.setRevisionNo(record.getRevision());
                        beforeUpdateQuery.setPuid(record.getPuid());
                        HzPbomLineRecord eplManageRecord = hzPbomRecordDAO.getPBomRecordByPuidAndRevision(beforeUpdateQuery);

                        if(eplManageRecord!=null){
                            HzPbomLineRespDTO beforeRecord = HzPbomRecordFactory.bomLineRecordToRespDTO(eplManageRecord);
                            beforeRecord.setChangeType(ChangeTypeEnum.BU.getChangeType());
                            respDTOs.add(beforeRecord);
                        }
                        HzPbomLineRespDTO afterRespDTO = HzPbomRecordFactory.bomLineRecordToRespDTO(record);
                        afterRespDTO.setChangeType(ChangeTypeEnum.AU.getChangeType());
                        respDTOs.add(afterRespDTO);
                    }
                }
                //查新增的数据（无版本号 状态值为2）
                HzChangeDataDetailQuery addQuery = new HzChangeDataDetailQuery();
                addQuery.setTableName(ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
                addQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
//                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzPbomLineRecord> addRecords = hzPbomRecordDAO.getPbomRecordsByOrderId(addQuery);
                if(ListUtil.isNotEmpty(addRecords)){
                    for(HzPbomLineRecord record :addRecords){
                        HzPbomLineRespDTO addRespDTO = HzPbomRecordFactory.bomLineRecordToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.A.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                //查删除的数据（状态值为4）

                HzChangeDataDetailQuery deleteQuery = new HzChangeDataDetailQuery();
                deleteQuery.setTableName(ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
                deleteQuery.setStatus(ChangeConstants.DELETE_STATUS);
                deleteQuery.setOrderId(query.getOrderId());
//                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzPbomLineRecord> deleteRecords = hzPbomRecordDAO.getPbomRecordsByOrderId(deleteQuery);
                if(ListUtil.isNotEmpty(deleteRecords)){
                    for(HzPbomLineRecord record :deleteRecords){
                        HzPbomLineRespDTO addRespDTO = HzPbomRecordFactory.bomLineRecordToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.D.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                return respDTOs;
            });
            future.get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return respDTOs;
    }

    @Override
    public List<HzMbomRecordRespDTO> getChangeDataRecordForMBOM(HzChangeDataQuery query) {

        if(null == query.getOrderId()){
            return null;
        }
        if(StringUtils.isBlank(query.getProjectId())){
            HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordById(query.getOrderId());
            query.setProjectId(record.getProjectId());
        }
        List<HzMbomRecordRespDTO> respDTOs = new ArrayList<>();
        Integer type = query.getType();
        query.setTableName(ChangeTableNameEnum.getMbomTableName(type,ChangeConstants.MBOM_AFTER_CHANGE));
        try {
            Future future = pool.submit(()->{
                //变更数据
//                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.getMbomTableName(type,ChangeConstants.MBOM_AFTER_CHANGE));
                updateQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
//                updateQuery.setPuids(puids);
                List<HzMbomLineRecord> afterRecords = hzMbomRecordDAO.getMbomRecordsByOrderId(updateQuery);
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzMbomLineRecord record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.getMbomTableName(type,ChangeConstants.MBOM_BEFORE_CHANGE));
                        beforeUpdateQuery.setStatus(ChangeConstants.EFFECT_STATUS);
                        beforeUpdateQuery.setRevision(true);
                        beforeUpdateQuery.setRevisionNo(record.getRevision());
                        beforeUpdateQuery.setPuid(record.getPuid());
                        HzMbomLineRecord eplManageRecord = hzMbomRecordDAO.getMBomRecordByPuidAndRevision(beforeUpdateQuery);

                        if(eplManageRecord!=null){
                            HzMbomRecordRespDTO beforeRecord = HzMbomRecordFactory.mbomRecordToRespDTO(eplManageRecord);
                            beforeRecord.setChangeType(ChangeTypeEnum.BU.getChangeType());
                            respDTOs.add(beforeRecord);
                        }
                        HzMbomRecordRespDTO afterRespDTO = HzMbomRecordFactory.mbomRecordToRespDTO(record);
                        afterRespDTO.setChangeType(ChangeTypeEnum.AU.getChangeType());
                        respDTOs.add(afterRespDTO);
                    }
                }
                //查新增的数据（无版本号 状态值为2）
                HzChangeDataDetailQuery addQuery = new HzChangeDataDetailQuery();
                addQuery.setTableName(ChangeTableNameEnum.getMbomTableName(type,ChangeConstants.MBOM_AFTER_CHANGE));
                addQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
//                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzMbomLineRecord> addRecords = hzMbomRecordDAO.getMbomRecordsByOrderId(addQuery);
                if(ListUtil.isNotEmpty(addRecords)){
                    for(HzMbomLineRecord record :addRecords){
                        HzMbomRecordRespDTO addRespDTO = HzMbomRecordFactory.mbomRecordToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.A.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                //查删除的数据（状态值为4）

                HzChangeDataDetailQuery deleteQuery = new HzChangeDataDetailQuery();
                deleteQuery.setTableName(ChangeTableNameEnum.getMbomTableName(type,ChangeConstants.MBOM_AFTER_CHANGE));
                deleteQuery.setStatus(ChangeConstants.DELETE_STATUS);
                deleteQuery.setOrderId(query.getOrderId());
//                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzMbomLineRecord> deleteRecords = hzMbomRecordDAO.getMbomRecordsByOrderId(deleteQuery);
                if(ListUtil.isNotEmpty(deleteRecords)){
                    for(HzMbomLineRecord record :deleteRecords){
                        HzMbomRecordRespDTO addRespDTO = HzMbomRecordFactory.mbomRecordToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.D.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                return respDTOs;
            });
            future.get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return respDTOs;
    }

    @Override
    public List<HzMaterielRespDTO> getChangeDataRecordForMateriel(HzChangeDataQuery query) {
        if(null == query.getOrderId()){
            return null;
        }
        List<HzMaterielRespDTO> respDTOs = new ArrayList<>();
        query.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
        if(StringUtils.isBlank(query.getProjectId())){
            HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordById(query.getOrderId());
            query.setProjectId(record.getProjectId());
        }
        try {
            Future future = pool.submit(()->{
                //变更数据
//                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
                updateQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
//                updateQuery.setPuids(puids);
                List<HzMaterielRecord> afterRecords = hzMaterielDAO.getMaterielRecordsByOrderId(updateQuery);
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzMaterielRecord record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_BEFORE.getTableName());
                        beforeUpdateQuery.setStatus(ChangeConstants.EFFECT_STATUS);
                        beforeUpdateQuery.setRevision(true);
                        beforeUpdateQuery.setRevisionNo(record.getRevision());
                        beforeUpdateQuery.setPuid(record.getPuid());
                        HzMaterielRecord eplManageRecord = hzMaterielDAO.getMaterialRecordByPuidAndRevision(beforeUpdateQuery);

                        if(eplManageRecord!=null){
                            HzMaterielRespDTO beforeRecord = HzMaterielFactory.hzMaterielRecordToRespDTO(eplManageRecord);
                            beforeRecord.setChangeType(ChangeTypeEnum.BU.getChangeType());
                            respDTOs.add(beforeRecord);
                        }
                        HzMaterielRespDTO afterRespDTO = HzMaterielFactory.hzMaterielRecordToRespDTO(record);
                        afterRespDTO.setChangeType(ChangeTypeEnum.AU.getChangeType());
                        respDTOs.add(afterRespDTO);
                    }
                }
                //查新增的数据（无版本号 状态值为2）
                HzChangeDataDetailQuery addQuery = new HzChangeDataDetailQuery();
                addQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
                addQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
//                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzMaterielRecord> addRecords = hzMaterielDAO.getMaterielRecordsByOrderId(addQuery);
                if(ListUtil.isNotEmpty(addRecords)){
                    for(HzMaterielRecord record :addRecords){
                        HzMaterielRespDTO addRespDTO = HzMaterielFactory.hzMaterielRecordToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.A.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                //查删除的数据（状态值为4）

                HzChangeDataDetailQuery deleteQuery = new HzChangeDataDetailQuery();
                deleteQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
                deleteQuery.setStatus(ChangeConstants.DELETE_STATUS);
                deleteQuery.setOrderId(query.getOrderId());
//                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzMaterielRecord> deleteRecords = hzMaterielDAO.getMaterielRecordsByOrderId(deleteQuery);
                if(ListUtil.isNotEmpty(deleteRecords)){
                    for(HzMaterielRecord record :deleteRecords){
                        HzMaterielRespDTO addRespDTO = HzMaterielFactory.hzMaterielRecordToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.D.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                return respDTOs;
            });
            future.get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return respDTOs;
    }

    @Override
    public List<HzWorkProcessRespDTO> getChangeDataRecordForWorkProcedure(HzChangeDataQuery query) {
        if(null == query.getOrderId()){
            return null;
        }
        List<HzWorkProcessRespDTO> respDTOs = new ArrayList<>();
        query.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName());
        if(StringUtils.isBlank(query.getProjectId())){
            HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordById(query.getOrderId());
            query.setProjectId(record.getProjectId());
        }
        try {
            Future future = pool.submit(()->{
                //变更数据
//                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName());
                updateQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
//                updateQuery.setPuids(puids);
                List<HzWorkProcedure> afterRecords = hzWorkProcedureDAO.getWorkProcedureByOrderId(updateQuery);
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzWorkProcedure record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_BEFORE.getTableName());
                        beforeUpdateQuery.setStatus(ChangeConstants.EFFECT_STATUS);
                        beforeUpdateQuery.setRevision(true);
                        beforeUpdateQuery.setRevisionNo(record.getRevision());
                        beforeUpdateQuery.setPuid(record.getPuid());
                        HzWorkProcedure eplManageRecord = hzWorkProcedureDAO.getHzWorkProcedureByPuidAndRevision(beforeUpdateQuery);

                        if(eplManageRecord!=null){
                            HzWorkProcessRespDTO beforeRecord = HzWorkProcedureFactory.workProcedureToRespDTO(eplManageRecord);
                            beforeRecord.setChangeType(ChangeTypeEnum.BU.getChangeType());
                            respDTOs.add(beforeRecord);
                        }
                        HzWorkProcessRespDTO afterRespDTO = HzWorkProcedureFactory.workProcedureToRespDTO(record);
                        afterRespDTO.setChangeType(ChangeTypeEnum.AU.getChangeType());
                        respDTOs.add(afterRespDTO);
                    }
                }
                //查新增的数据（无版本号 状态值为2）
                HzChangeDataDetailQuery addQuery = new HzChangeDataDetailQuery();
                addQuery.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName());
                addQuery.setStatus(ChangeConstants.DRAFT_STATUS);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
//                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzWorkProcedure> addRecords = hzWorkProcedureDAO.getWorkProcedureByOrderId(addQuery);
                if(ListUtil.isNotEmpty(addRecords)){
                    for(HzWorkProcedure record :addRecords){
                        HzWorkProcessRespDTO addRespDTO = HzWorkProcedureFactory.workProcedureToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.A.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                //查删除的数据（状态值为4）

                HzChangeDataDetailQuery deleteQuery = new HzChangeDataDetailQuery();
                deleteQuery.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName());
                deleteQuery.setStatus(ChangeConstants.DELETE_STATUS);
                deleteQuery.setOrderId(query.getOrderId());
//                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzWorkProcedure> deleteRecords = hzWorkProcedureDAO.getWorkProcedureByOrderId(deleteQuery);
                if(ListUtil.isNotEmpty(deleteRecords)){
                    for(HzWorkProcedure record :deleteRecords){
                        HzWorkProcessRespDTO addRespDTO = HzWorkProcedureFactory.workProcedureToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.D.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                return respDTOs;
            });
            future.get();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return respDTOs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WriteResultRespDTO deleteEBOMChangeDataDetail(BomBackReqDTO reqDTO) {
        if(StringUtils.isBlank(reqDTO.getPuids()) || reqDTO.getOrderId() == null){
            return WriteResultRespDTO.IllgalArgument();
        }
        WriteResultRespDTO resultRespDTO = deleteBeforeCheck(reqDTO.getOrderId());
        if(StringUtils.isNotBlank(resultRespDTO.getErrMsg())){
            resultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            return resultRespDTO;
        }
        List<String> list = Lists.newArrayList(reqDTO.getPuids().split(","));
        //删除变更后表中的数据
        //更新原来表中的状态 为发起流程前的状态
        //如果变更后表中的数据为空 则表示全部删除 这时需要清空中间表数据
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
        dataDetailQuery.setOrderId(reqDTO.getOrderId());
        dataDetailQuery.setProjectId(reqDTO.getProjectId());
        dataDetailQuery.setPuids(list);
        List<HzEPLManageRecord> updateList = new ArrayList<>();
        List<HzEPLManageRecord> recordList =  hzEbomRecordDAO.getEbomRecordsByOrderId(dataDetailQuery);//表中记录的
        List<HzEPLManageRecord> records = hzEbomRecordDAO.getEbomRecordsByPuids(dataDetailQuery);//要删除的
        try {
            if(ListUtil.isNotEmpty(recordList) && ListUtil.isNotEmpty(records)){
                records.forEach(record->{
                    HzEPLManageRecord lineRecord = HzEbomRecordFactory.ebomRecordToEBOMRecord(record);
                    lineRecord.setStatus(record.getStatus());//审核状态
                    lineRecord.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
                    updateList.add(lineRecord);
                });
                hzEbomRecordDAO.deleteByPuids(list,ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
                hzEbomRecordDAO.updateListByEplId(updateList);
                if(recordList.size() == records.size()){
                    HzChangeDataRecord record = new HzChangeDataRecord();
                    record.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
                    record.setOrderId(reqDTO.getOrderId());
                    hzChangeDataRecordDAO.deleteByOrderIdAndTableName(record);
                }
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    @Transactional
    public WriteResultRespDTO deletePBOMChangeDataDetail(BomBackReqDTO reqDTO) {
        if(StringUtils.isBlank(reqDTO.getPuids()) || reqDTO.getOrderId() == null){
            return WriteResultRespDTO.IllgalArgument();
        }
        WriteResultRespDTO resultRespDTO = deleteBeforeCheck(reqDTO.getOrderId());
        if(StringUtils.isNotBlank(resultRespDTO.getErrMsg())){
            resultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            return resultRespDTO;
        }
        List<String> list = Lists.newArrayList(reqDTO.getPuids().split(","));
        //删除变更后表中的数据
        //更新原来表中的状态 为发起流程前的状态
        //如果变更后表中的数据为空 则表示全部删除 这时需要清空中间表数据
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
        dataDetailQuery.setOrderId(reqDTO.getOrderId());
        dataDetailQuery.setProjectId(reqDTO.getProjectId());
        dataDetailQuery.setPuids(list);
        List<HzPbomLineRecord> updateList = new ArrayList<>();
        List<HzPbomLineRecord> recordList =  hzPbomRecordDAO.getPbomRecordsByOrderId(dataDetailQuery);//表中记录的
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomRecordsByPuids(dataDetailQuery);//要删除的
        try {
            if(ListUtil.isNotEmpty(recordList) && ListUtil.isNotEmpty(records)){
                records.forEach(record->{
                    record.setTableName(ChangeTableNameEnum.HZ_PBOM.getTableName());
                    updateList.add(record);
                });
                hzPbomRecordDAO.deleteListByPuids(reqDTO.getPuids(),ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
                hzPbomRecordDAO.updateList(updateList);
                if(recordList.size() == records.size()){
                    HzChangeDataRecord record = new HzChangeDataRecord();
                    record.setTableName(ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
                    record.setOrderId(reqDTO.getOrderId());
                    hzChangeDataRecordDAO.deleteByOrderIdAndTableName(record);
                }
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    @Transactional
    public WriteResultRespDTO deleteMBOMChangeDataDetail(BomBackReqDTO reqDTO) {
        if(StringUtils.isBlank(reqDTO.getPuids()) || reqDTO.getOrderId() == null){
            return WriteResultRespDTO.IllgalArgument();
        }
        WriteResultRespDTO resultRespDTO = deleteBeforeCheck(reqDTO.getOrderId());
        if(StringUtils.isNotBlank(resultRespDTO.getErrMsg())){
            resultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            return resultRespDTO;
        }
        List<String> list = Lists.newArrayList(reqDTO.getPuids().split(","));
        //删除变更后表中的数据
        //更新原来表中的状态 为发起流程前的状态
        //如果变更后表中的数据为空 则表示全部删除 这时需要清空中间表数据
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setTableName(ChangeTableNameEnum.getMbomTableName(reqDTO.getType(),ChangeConstants.MBOM_AFTER_CHANGE));
        dataDetailQuery.setOrderId(reqDTO.getOrderId());
        dataDetailQuery.setProjectId(reqDTO.getProjectId());
        dataDetailQuery.setPuids(list);
        List<HzMbomLineRecord> updateList = new ArrayList<>();
        List<HzMbomLineRecord> recordList =  hzMbomRecordDAO.getMbomRecordsByOrderId(dataDetailQuery);//表中记录的
        List<HzMbomLineRecord> records = hzMbomRecordDAO.getMbomRecordsByPuids(dataDetailQuery);//要删除的
        try {
            if(ListUtil.isNotEmpty(recordList) && ListUtil.isNotEmpty(records)){
                records.forEach(record->{
                    record.setTableName(ChangeTableNameEnum.getMbomTableName(reqDTO.getType(),ChangeConstants.MBOM_CHANGE));
                    updateList.add(record);
                });
                HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
                vo.setTableName(ChangeTableNameEnum.getMbomTableName(reqDTO.getType(),ChangeConstants.MBOM_AFTER_CHANGE));
                vo.setRecordList(records);
                hzMbomRecordDAO.deleteMbomList(vo);
                hzMbomRecordDAO.updateList(updateList);
                if(recordList.size() == records.size()){
                    HzChangeDataRecord record = new HzChangeDataRecord();
                    record.setTableName(ChangeTableNameEnum.getMbomTableName(reqDTO.getType(),ChangeConstants.MBOM_AFTER_CHANGE));
                    record.setOrderId(reqDTO.getOrderId());
                    hzChangeDataRecordDAO.deleteByOrderIdAndTableName(record);
                }
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
    }


    @Override
    @Transactional
    public WriteResultRespDTO deleteMaterielChangeDataDetail(BomBackReqDTO reqDTO) {
        if(StringUtils.isBlank(reqDTO.getPuids()) || reqDTO.getOrderId() == null){
            return WriteResultRespDTO.IllgalArgument();
        }

        WriteResultRespDTO resultRespDTO = deleteBeforeCheck(reqDTO.getOrderId());
        if(StringUtils.isNotBlank(resultRespDTO.getErrMsg())){
            resultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            return resultRespDTO;
        }

        List<String> list = Lists.newArrayList(reqDTO.getPuids().split(","));
        //删除变更后表中的数据
        //更新原来表中的状态 为发起流程前的状态
        //如果变更后表中的数据为空 则表示全部删除 这时需要清空中间表数据
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
        dataDetailQuery.setOrderId(reqDTO.getOrderId());
        dataDetailQuery.setProjectId(reqDTO.getProjectId());
        dataDetailQuery.setPuids(list);
        List<HzMaterielRecord> updateList = new ArrayList<>();
        List<HzMaterielRecord> recordList =  hzMaterielDAO.getMaterielRecordsByOrderId(dataDetailQuery);//表中记录的
        List<HzMaterielRecord> records = hzMaterielDAO.getMaterialRecordsByPuids(dataDetailQuery);//要删除的
        try {
            if(ListUtil.isNotEmpty(recordList) && ListUtil.isNotEmpty(records)){
                records.forEach(record->{
                    record.setTableName(ChangeTableNameEnum.HZ_MATERIEL.getTableName());
                    updateList.add(record);
                });
                hzMaterielDAO.deleteMaterielList(records,ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
                hzMaterielDAO.updateList(updateList);
                if(recordList.size() == records.size()){
                    HzChangeDataRecord record = new HzChangeDataRecord();
                    record.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
                    record.setOrderId(reqDTO.getOrderId());
                    hzChangeDataRecordDAO.deleteByOrderIdAndTableName(record);
                }
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    @Transactional
    public WriteResultRespDTO deleteWorkProcedureChangeDataDetail(BomBackReqDTO reqDTO) {
        if(StringUtils.isBlank(reqDTO.getPuids()) || reqDTO.getOrderId() == null){
            return WriteResultRespDTO.IllgalArgument();
        }
        WriteResultRespDTO resultRespDTO = deleteBeforeCheck(reqDTO.getOrderId());
        if(StringUtils.isNotBlank(resultRespDTO.getErrMsg())){
            resultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            return resultRespDTO;
        }
        List<String> list = Lists.newArrayList(reqDTO.getPuids().split(","));
        //删除变更后表中的数据
        //更新原来表中的状态 为发起流程前的状态
        //如果变更后表中的数据为空 则表示全部删除 这时需要清空中间表数据
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName());
        dataDetailQuery.setOrderId(reqDTO.getOrderId());
        dataDetailQuery.setProjectId(reqDTO.getProjectId());
        dataDetailQuery.setPuids(list);
        List<HzWorkProcedure> updateList = new ArrayList<>();
        List<HzWorkProcedure> recordList =  hzWorkProcedureDAO.getWorkProcedureByOrderId(dataDetailQuery);//表中记录的
        List<HzWorkProcedure> records = hzWorkProcedureDAO.getHzWorkProcedureByPuids(dataDetailQuery);//要删除的
        try {
            if(ListUtil.isNotEmpty(recordList) && ListUtil.isNotEmpty(records)){
                records.forEach(record->{
                    record.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE.getTableName());
                    updateList.add(record);
                });
                hzWorkProcedureDAO.deleteByPuids(list,ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName());
                hzWorkProcedureDAO.updateList(updateList);
                if(recordList.size() == records.size()){
                    HzChangeDataRecord record = new HzChangeDataRecord();
                    record.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName());
                    record.setOrderId(reqDTO.getOrderId());
                    hzChangeDataRecordDAO.deleteByOrderIdAndTableName(record);
                }
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }
    }


    /**
     * 删除数据前的判断
     * @param id
     * @return
     */
    private WriteResultRespDTO deleteBeforeCheck(Long id){
        HzChangeOrderRecord changeOrderRecord = hzChangeOrderDAO.findHzChangeOrderRecordById(id);
        String str = "";
        if(changeOrderRecord == null){
            str = WriteResultRespDTO.FAILED_MSG;
        }
        if(!Integer.valueOf(2).equals(changeOrderRecord.getState())){
            str ="流程中和已完成的表单数据不允许删除!";
        }
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        respDTO.setErrMsg(str);
        return respDTO;
    }
}
