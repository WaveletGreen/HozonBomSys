package com.connor.hozon.bom.resources.service.change.impl;

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
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.service.change.HzChangeDataService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.project.HzMaterielRecord;
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

    private ExecutorService pool = Executors.newFixedThreadPool(7);

    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzMaterielDAO hzMaterielDAO;

    @Autowired
    private HzWorkProcedureDAO hzWorkProcedureDAO;

    @Override
    public List<HzChangeDataRespDTO> getChangeDataHyperRecord(HzChangeDataQuery query) {
        try {
            List<HzChangeDataRecord> records = hzChangeDataRecordDAO.getChangeDataTableName(query);
            List<HzChangeDataRespDTO> respDTOS = new ArrayList<>();
            if(ListUtil.isNotEmpty(records)){
                for(HzChangeDataRecord record : records){
                    HzChangeDataRespDTO respDTO = new HzChangeDataRespDTO();
                    respDTO.setOrderId(record.getOrderId());
                    respDTO.setHyperLinkName(TableNameToHyperLinkNameEnum.getHyperLinkName(record.getTableName()));
                    respDTOS.add(respDTO);
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
        query.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
        try {
            Future future = pool.submit(()->{
                //变更数据
                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
                updateQuery.setStatus(2);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
                updateQuery.setPuids(puids);
                List<HzEPLManageRecord> afterRecords = hzEbomRecordDAO.getEbomRecordsByPuids(updateQuery);
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzEPLManageRecord record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_BEFORE.getTableName());
                        beforeUpdateQuery.setStatus(1);
                        beforeUpdateQuery.setRevision(true);
                        beforeUpdateQuery.setRevisionNo(record.getRevision());
                        beforeUpdateQuery.setPuid(record.getPuid());
                        HzEPLManageRecord eplManageRecord = hzEbomRecordDAO.getEBomRecordByPuidAndRevision(beforeUpdateQuery);

                        if(eplManageRecord!=null){
                            HzEbomRespDTO beforeRecord = HzEbomRecordFactory.eplRecordToEbomRespDTO(eplManageRecord);
                            beforeRecord.setChangeType(ChangeTypeEnum.BU.getChangeType());
                            respDTOs.add(beforeRecord);
                        }
                        HzEbomRespDTO afterRespDTO = HzEbomRecordFactory.eplRecordToEbomRespDTO(record);
                        afterRespDTO.setChangeType(ChangeTypeEnum.AU.getChangeType());
                        respDTOs.add(afterRespDTO);
                    }
                }
                //查新增的数据（无版本号 状态值为2）
                HzChangeDataDetailQuery addQuery = new HzChangeDataDetailQuery();
                addQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
                addQuery.setStatus(2);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzEPLManageRecord> addRecords = hzEbomRecordDAO.getEbomRecordsByPuids(addQuery);
                if(ListUtil.isNotEmpty(addRecords)){
                    for(HzEPLManageRecord record :addRecords){
                        HzEbomRespDTO addRespDTO = HzEbomRecordFactory.eplRecordToEbomRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.A.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                //查删除的数据（状态值为4）

                HzChangeDataDetailQuery deleteQuery = new HzChangeDataDetailQuery();
                deleteQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
                deleteQuery.setStatus(4);
                deleteQuery.setRevision(false);
                deleteQuery.setOrderId(query.getOrderId());
                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzEPLManageRecord> deleteRecords = hzEbomRecordDAO.getEbomRecordsByPuids(deleteQuery);
                if(ListUtil.isNotEmpty(deleteRecords)){
                    for(HzEPLManageRecord record :deleteRecords){
                        HzEbomRespDTO addRespDTO = HzEbomRecordFactory.eplRecordToEbomRespDTO(record);
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
    public List<HzPbomLineRespDTO> getChangeDataRecordForPBOM(HzChangeDataQuery query) {
        List<HzPbomLineRespDTO> respDTOs = new ArrayList<>();
        query.setTableName(ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
        try {
            Future future = pool.submit(()->{
                //变更数据
                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName());
                updateQuery.setStatus(2);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
                updateQuery.setPuids(puids);
                List<HzPbomLineRecord> afterRecords = hzPbomRecordDAO.getPbomRecordsByPuids(updateQuery);
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzPbomLineRecord record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.HZ_PBOM_BEFORE.getTableName());
                        beforeUpdateQuery.setStatus(1);
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
                addQuery.setStatus(2);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzPbomLineRecord> addRecords = hzPbomRecordDAO.getPbomRecordsByPuids(addQuery);
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
                deleteQuery.setStatus(4);
                deleteQuery.setRevision(false);
                deleteQuery.setOrderId(query.getOrderId());
                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzPbomLineRecord> deleteRecords = hzPbomRecordDAO.getPbomRecordsByPuids(deleteQuery);
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
        List<HzMbomRecordRespDTO> respDTOs = new ArrayList<>();
        Integer type = query.getType();
        query.setTableName(ChangeTableNameEnum.getMbomTableName(type,"MA"));
        try {
            Future future = pool.submit(()->{
                //变更数据
                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.getMbomTableName(type,"MA"));
                updateQuery.setStatus(2);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
                updateQuery.setPuids(puids);
                List<HzMbomLineRecord> afterRecords = hzMbomRecordDAO.getMbomRecordsByPuids(updateQuery);
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzMbomLineRecord record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.getMbomTableName(type,"MB"));
                        beforeUpdateQuery.setStatus(1);
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
                addQuery.setTableName(ChangeTableNameEnum.getMbomTableName(type,"MA"));
                addQuery.setStatus(2);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzMbomLineRecord> addRecords = hzMbomRecordDAO.getMbomRecordsByPuids(addQuery);
                if(ListUtil.isNotEmpty(addRecords)){
                    for(HzMbomLineRecord record :addRecords){
                        HzMbomRecordRespDTO addRespDTO = HzMbomRecordFactory.mbomRecordToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.A.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                //查删除的数据（状态值为4）

                HzChangeDataDetailQuery deleteQuery = new HzChangeDataDetailQuery();
                deleteQuery.setTableName(ChangeTableNameEnum.getMbomTableName(type,"MA"));
                deleteQuery.setStatus(4);
                deleteQuery.setRevision(false);
                deleteQuery.setOrderId(query.getOrderId());
                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzMbomLineRecord> deleteRecords = hzMbomRecordDAO.getMbomRecordsByPuids(deleteQuery);
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
        List<HzMaterielRespDTO> respDTOs = new ArrayList<>();
        query.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
        try {
            Future future = pool.submit(()->{
                //变更数据
                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
                updateQuery.setStatus(2);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
                updateQuery.setPuids(puids);
                List<HzMaterielRecord> afterRecords = hzMaterielDAO.getMaterialRecordsByPuids(updateQuery);
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzMaterielRecord record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_BEFORE.getTableName());
                        beforeUpdateQuery.setStatus(1);
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
                addQuery.setStatus(2);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzMaterielRecord> addRecords = hzMaterielDAO.getMaterialRecordsByPuids(addQuery);
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
                deleteQuery.setStatus(4);
                deleteQuery.setRevision(false);
                deleteQuery.setOrderId(query.getOrderId());
                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzMaterielRecord> deleteRecords = hzMaterielDAO.getMaterialRecordsByPuids(deleteQuery);
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
        List<HzWorkProcessRespDTO> respDTOs = new ArrayList<>();
        query.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName());
        try {
            Future future = pool.submit(()->{
                //变更数据
                List<String> puids = hzChangeDataRecordDAO.getChangeDataPuids(query);
                //查更新的数据 分变更前 变更后（有版本号 状态值为2）
                HzChangeDataDetailQuery updateQuery = new HzChangeDataDetailQuery();
                updateQuery.setProjectId(query.getProjectId());
                updateQuery.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName());
                updateQuery.setStatus(2);
                updateQuery.setRevision(true);
                updateQuery.setOrderId(query.getOrderId());
                updateQuery.setPuids(puids);
                List<HzWorkProcedure> afterRecords = hzWorkProcedureDAO.getHzWorkProcedureByPuids(updateQuery);
                if(ListUtil.isNotEmpty(afterRecords)){
                    for(HzWorkProcedure record :afterRecords){
                        HzChangeDataDetailQuery beforeUpdateQuery = new HzChangeDataDetailQuery();
                        beforeUpdateQuery.setProjectId(query.getProjectId());
                        beforeUpdateQuery.setTableName(ChangeTableNameEnum.HZ_WORK_PROCEDURE_BEFORE.getTableName());
                        beforeUpdateQuery.setStatus(1);
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
                addQuery.setStatus(2);
                addQuery.setRevision(false);
                addQuery.setOrderId(query.getOrderId());
                addQuery.setPuids(puids);
                addQuery.setProjectId(query.getProjectId());
                List<HzWorkProcedure> addRecords = hzWorkProcedureDAO.getHzWorkProcedureByPuids(addQuery);
                if(ListUtil.isNotEmpty(addRecords)){
                    for(HzWorkProcedure record :addRecords){
                        HzWorkProcessRespDTO addRespDTO = HzWorkProcedureFactory.workProcedureToRespDTO(record);
                        addRespDTO.setChangeType(ChangeTypeEnum.A.getChangeType());
                        respDTOs.add(addRespDTO);
                    }
                }
                //查删除的数据（状态值为4）

                HzChangeDataDetailQuery deleteQuery = new HzChangeDataDetailQuery();
                deleteQuery.setTableName(ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName());
                deleteQuery.setStatus(4);
                deleteQuery.setRevision(false);
                deleteQuery.setOrderId(query.getOrderId());
                deleteQuery.setPuids(puids);
                deleteQuery.setProjectId(query.getProjectId());
                List<HzWorkProcedure> deleteRecords = hzWorkProcedureDAO.getHzWorkProcedureByPuids(deleteQuery);
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
}
