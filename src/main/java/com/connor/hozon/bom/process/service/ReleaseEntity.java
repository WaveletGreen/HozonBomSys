/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.process.service;

import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDMBasicChangeDao;
import com.connor.hozon.bom.bomSystem.dao.derivative.HzDerivativeMaterielBasicDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainChangeDao;
import com.connor.hozon.bom.bomSystem.dao.fullCfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.dao.relevance.HzRelevanceBasicChangeDao;
import com.connor.hozon.bom.bomSystem.dao.relevance.HzRelevanceBasicDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzFeatureChangeService;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVWOManagerService;
import com.connor.hozon.bom.bomSystem.iservice.integrate.ISynBomService;
import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;
import com.connor.hozon.bom.process.iservice.IDataModifier;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.*;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataQuery;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.enumtype.ChangeTypeEnum;
import com.connor.hozon.bom.resources.executors.ExecutorServices;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.exception.HzBomException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomLineRecordVO;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.cfg.relevance.HzRelevanceBasic;
import sql.pojo.cfg.relevance.HzRelevanceBasicChange;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.change.HzChangeOrderRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.project.HzMaterielRecord;
import sql.pojo.work.HzWorkProcedure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 流程通过，进行数据发布，这里写具体的修改数据的逻辑
 * @Date: Created in  2018/11/22 15:15
 * @Modified By:
 */
@Component
@EnableTransactionManagement(proxyTargetClass = true)
//要在事务的类中抛出RuntimeException异常，而不是抛出Exception，也不知道对不对
@Transactional(rollbackFor = {IllegalArgumentException.class, RuntimeException.class, Exception.class})
public class ReleaseEntity implements IReleaseCallBack, IFunctionDesc, IDataModifier {

    /***
     * 添加多个配置管理的service和BOM管理的service
     */

    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzMaterielDAO hzMaterielDAO;

    @Autowired
    private HzWorkProcedureDAO hzWorkProcedureDAO;

    @Autowired
    private IHzFeatureChangeService iHzFeatureChangeService;
    @Autowired
    private HzCfg0RecordDao hzCfg0RecordDao;

    @Autowired
    private HzCmcrDetailChangeDao hzCmcrDetailChangeDao;
    @Autowired
    private HzCmcrChangeDao hzCmcrChangeDao;
    @Autowired
    private HzCfg0ModelColorDao hzCfg0ModelColorDao;

    @Autowired
    private HzDMBasicChangeDao hzDMBasicChangeDao;
    @Autowired
    private HzDerivativeMaterielBasicDao hzDerivativeMaterielBasicDao;

    @Autowired
    private HzFullCfgMainDao hzFullCfgMainDao;
    @Autowired
    private HzFullCfgMainChangeDao hzFullCfgMainChangeDao;

    @Autowired
    @Qualifier("synBomService")
    private ISynBomService synBomService;
    @Autowired
    private HzRelevanceBasicDao hzRelevanceBasicDao;
    @Autowired
    private HzRelevanceBasicChangeDao hzRelevanceBasicChangeDao;

    @Autowired
    private IHzVWOManagerService hzVWOManagerService;
    @Override
    public void interruptionFunctionDesc() {

    }

    @Override
    public void releaseFunctionDesc() {
        System.out.println("执行的是" + this.getClass().getCanonicalName() + ".release回调");
    }

    /**
     * 发布数据，进行所有数据的统一操作
     *
     * @param orderId 变更表单的ID
     * @param params  配置参数，预留
     * @return 发布成功返回true，反之返回false
     */
    @Override
    public boolean release(Long orderId, Object... params) {
        releaseFunctionDesc();
        /***
         * 在这里写数据发布操作
         */
        return configuration(orderId, params) && bom(orderId, params);
    }

    /**
     * 在这里写配置部分数据的发布代码
     *
     * @param orderId 变更表单的ID
     * @param params  配置参数，预留
     * @return
     * @InChage zhudb
     */
    @Override
    public boolean configuration(Long orderId, Object... params) {
        //根据表单id 获取全部的变更数据
        HzChangeDataQuery hzChangeDataQuery = new HzChangeDataQuery();
        hzChangeDataQuery.setOrderId(orderId);
        //项目id
        HzChangeOrderRecord hzChangeOrderRecord = hzChangeOrderDAO.findHzChangeOrderRecordById(orderId);
        if (hzChangeOrderRecord == null) {
            return false;
        }
        String projectId = hzChangeOrderRecord.getProjectId();
        //查询变更关系表数据
        List<HzChangeDataRecord> list = hzChangeDataRecordDAO.getChangeDataTableName(hzChangeDataQuery);
        if(list!=null&&list.size()>0){
            for(HzChangeDataRecord hzChangeDataRecord : list){
                //特性变更批准
                if(ChangeTableNameEnum.HZ_CFG0_AFTER_CHANGE_RECORD.getTableName().equals(hzChangeDataRecord.getTableName())){
                    //发送至sap
//                    if(!hzVWOManagerService.featureToSap(orderId)){
//                        return false;
//                    }
                    //将变更数据的状态修改为以生效
                    if(!iHzFeatureChangeService.updateStatusByOrderId(orderId,1)){
                        return false;
                    }
                    //将源数据修改为已生效
                    if(hzCfg0RecordDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                //配色方案变更批准
                }else if(ChangeTableNameEnum.HZ_CMCR_AFTER_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())){
                    //修改配色方案变更状态为已生效
                    if(hzCmcrChangeDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                    if(hzCfg0ModelColorDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                //衍生物料变更批准
                }else if(ChangeTableNameEnum.HZ_DM_BASIC_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())){
                    //发送至sap
//                    if(!hzVWOManagerService.derivativeMaterielToSap(orderId)){
//                        return false;
//                    }
                    if(hzDMBasicChangeDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                    if(hzDerivativeMaterielBasicDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                //全配置变更批准
                }else if(ChangeTableNameEnum.HZ_FULL_CFG_MAIN_RECORD_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())){
                    if(hzFullCfgMainDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                    if(hzFullCfgMainChangeDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                }else if(ChangeTableNameEnum.HZ_RELEVANCE_BASIC_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())){
                    //发送至SAP
//                    if(!hzVWOManagerService.relevanceToSap(orderId)){
//                        return false;
//                    }
                    HzRelevanceBasic hzRelevanceBasic = new HzRelevanceBasic();
                    hzRelevanceBasic.setRbVwoId(orderId);
                    hzRelevanceBasic.setRelevanceStatus(1);
                    HzRelevanceBasicChange hzRelevanceBasicChange = new HzRelevanceBasicChange();
                    hzRelevanceBasicChange.setChangeOrderId(orderId);
                    hzRelevanceBasicChange.setChangeStatus(1);
                    if(hzRelevanceBasicChangeDao.updateStatusByIOrderId(hzRelevanceBasicChange)<=0?true:false){
                        return false;
                    }
                    if(hzRelevanceBasicDao.updateStatusByOrderChangeId(hzRelevanceBasic)<=0?true:false){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 在这里写BOM管理部分数据的发布代码
     * 变更表单审核通过 BOM端数据部分
     *
     * @param orderId 变更表单的ID
     * @param params  配置参数，预留
     * @return
     * @InChage haozt
     */

    //这里是流程审核完成后 要传输的全部数据
    //传输数据 包括 物料 MBOM 和工艺路线
    @Override
    @Transactional(rollbackFor = HzBomException.class)
    public boolean bom(Long orderId, Object... params) {
        //根据表单id 获取全部的变更数据
        HzChangeDataQuery hzChangeDataQuery = new HzChangeDataQuery();
        hzChangeDataQuery.setOrderId(orderId);
        //项目id
        HzChangeOrderRecord hzChangeOrderRecord = hzChangeOrderDAO.findHzChangeOrderRecordById(orderId);
        if (hzChangeOrderRecord == null) {
            return false;
        }
        String projectId = hzChangeOrderRecord.getProjectId();

        List<HzChangeDataRecord> list = hzChangeDataRecordDAO.getChangeDataTableName(hzChangeDataQuery);
        //EBOM  PBOM  MBOM  物料  工艺路线 BOM端 目前就这5种类型
        if (ListUtil.isNotEmpty(list)) {
            ExecutorServices executorServices = new ExecutorServices(list.size());
            ExecutorService service = executorServices.getPool();
            try {
                for (HzChangeDataRecord record : list) {
                    if (ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName().equals(record.getTableName())) {
                        service.submit(() -> {
                            ebomChange(record.getTableName(), orderId, projectId);
                        });
                    } else if (ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName().equals(record.getTableName())) {
                        service.submit(() -> {
                            pbomChange(record.getTableName(), orderId, projectId);
                        });
                    } else if (ChangeTableNameEnum.HZ_MBOM_AFTER.getTableName().equals(record.getTableName())) {
                        service.submit(() -> {
                            mbomChange(record.getTableName(), orderId, projectId);
                        });
                    } else if (ChangeTableNameEnum.HZ_MBOM_PRODUCT_AFTER.getTableName().equals(record.getTableName())) {
                        service.submit(() -> {
                            mbomProductChange(record.getTableName(), orderId, projectId);
                        });
                    } else if (ChangeTableNameEnum.HZ_MBOM_FINANCE_AFTER.getTableName().equals(record.getTableName())) {
                        service.submit(() -> {
                            mbomFinanceChange(record.getTableName(), orderId, projectId);
                        });
                    } else if (ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName().equals(record.getTableName())) {
                        service.submit(() -> {
                            materielChange(record.getTableName(), orderId, projectId);
                        });
                    } else if (ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName().equals(record.getTableName())) {
                        service.submit(() -> {
                            workProduceChange(record.getTableName(), orderId, projectId);
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new HzBomException(WriteResultRespDTO.getFailResult());
            } finally {
                if (service != null) {
                    service.shutdown();
                }
            }
        }
        return true;
    }

    /**
     * EBOM变更审核通过
     *
     * @param tableName
     */
    private void ebomChange(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);
        List<HzEPLManageRecord> records = hzEbomRecordDAO.getEbomRecordsByOrderId(dataDetailQuery);
        //即将要更新的数据
        List<HzBomLineRecord> updateList = new ArrayList<>();
        //即将要删除的数据
        List<String> deletePuids = new ArrayList<>();
        //要新增的数据
        List<HzEPLManageRecord> addList = new ArrayList<>();


        if (ListUtil.isNotEmpty(records)) {
            Date date = new Date();
            records.forEach(record -> {
                if (4 == record.getStatus()) {
                    deletePuids.add(record.getPuid());
                } else {
                    HzBomLineRecord bomLineRecord = HzEbomRecordFactory.eplRecordToBomLineRecord(record);
                    String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision() + 1));
                    bomLineRecord.setStatus(1);
                    bomLineRecord.setEffectTime(date);
                    bomLineRecord.setRevision(revision);
                    bomLineRecord.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());

                    record.setStatus(1);
                    record.setRevision(revision);
                    record.setEffectTime(date);
                    updateList.add(bomLineRecord);
                    addList.add(record);
                }

            });
        }

        if (ListUtil.isNotEmpty(deletePuids)) {
            hzEbomRecordDAO.deleteByPuids(deletePuids,ChangeTableNameEnum.HZ_EBOM.getTableName());
        }
        if (ListUtil.isNotEmpty(updateList)) {
            hzEbomRecordDAO.updateList(updateList);
        }
        if (ListUtil.isNotEmpty(addList)) {
            hzEbomRecordDAO.insertList(addList, ChangeTableNameEnum.HZ_EBOM_BEFORE.getTableName());
        }


    }

    /**
     * PBOM变更审核通过
     *
     * @param tableName
     */
    private void pbomChange(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomRecordsByOrderId(dataDetailQuery);
        //即将要更新的数据
        List<HzPbomLineRecord> updateList = new ArrayList<>();
        //即将要删除的数据 线程安全
        StringBuffer stringBuffer = new StringBuffer();
        //要新增的数据
        List<HzPbomLineRecord> addList = new ArrayList<>();

        if (ListUtil.isNotEmpty(records)) {
            Date date = new Date();
            records.forEach(record -> {
                if (4 == record.getStatus()) {
                    stringBuffer.append(record.geteBomPuid() + ",");
                } else {
                    HzPbomLineRecord hzPbomLineRecord = HzPbomRecordFactory.bomLineRecordToBomRecord(record);
                    String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision() + 1));
                    hzPbomLineRecord.setStatus(1);
                    hzPbomLineRecord.setEffectTime(date);
                    hzPbomLineRecord.setRevision(revision);

                    record.setStatus(1);
                    record.setRevision(revision);
                    record.setEffectTime(date);
                    updateList.add(hzPbomLineRecord);
                    addList.add(record);
                }

            });
        }

        if (StringUtils.isNotBlank(stringBuffer.toString())) {
            hzPbomRecordDAO.deleteListByPuids(stringBuffer.toString(),ChangeTableNameEnum.HZ_PBOM.getTableName());
        }
        if (ListUtil.isNotEmpty(updateList)) {
            hzPbomRecordDAO.updateList(updateList);
        }
        if (ListUtil.isNotEmpty(addList)) {
            hzPbomRecordDAO.insertListForChange(addList, ChangeTableNameEnum.HZ_PBOM_BEFORE.getTableName());
        }


    }

    /**
     * MBOM变更审核通过
     *
     * @param tableName
     */
    private void mbomChange(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);
        List<HzMbomLineRecord> records = hzMbomRecordDAO.getMbomRecordsByOrderId(dataDetailQuery);
        //即将要更新的数据
        List<HzMbomLineRecord> updateList = new ArrayList<>();
        //即将要删除的数据
        List<HzMbomLineRecord> deleteMbom = new ArrayList<>();
        //要新增的数据
        List<HzMbomLineRecord> addList = new ArrayList<>();

        if (ListUtil.isNotEmpty(records)) {
            Date date = new Date();
            records.forEach(record -> {
                if (4 == record.getStatus()) {
                    deleteMbom.add(record);
                } else {
                    HzMbomLineRecord bomLineRecord = HzMbomRecordFactory.mbomLineRecordToMbomLineRecord(record);
                    String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision() + 1));
                    bomLineRecord.setStatus(1);
                    bomLineRecord.setEffectTime(date);
                    bomLineRecord.setRevision(revision);
                    bomLineRecord.setTableName(ChangeTableNameEnum.HZ_MBOM.getTableName());

                    record.setStatus(1);
                    record.setRevision(revision);
                    record.setEffectTime(date);
                    updateList.add(bomLineRecord);
                    addList.add(record);
                }

            });
        }

        //需要发送数据 给 sap？
//        synBomService.updateByUids();//更新
//        synBomService.addOne();//新增
//        synBomService.deleteByUids();//删除
        if (ListUtil.isNotEmpty(deleteMbom)) {
            HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
            vo.setTableName(ChangeTableNameEnum.HZ_MBOM.getTableName());
            vo.setRecordList(deleteMbom);
            hzMbomRecordDAO.deleteMbomList(vo);
        }
        if (ListUtil.isNotEmpty(updateList)) {
            hzMbomRecordDAO.updateList(updateList);
        }
        if (ListUtil.isNotEmpty(addList)) {
            HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
            vo.setTableName(ChangeTableNameEnum.HZ_MBOM_BEFORE.getTableName());
            vo.setRecordList(addList);
            hzMbomRecordDAO.insertVO(vo);
        }

    }

    /**
     * 财务型MBOM变更审核通过
     *
     * @param tableName
     */
    private void mbomFinanceChange(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);
        List<HzMbomLineRecord> records = hzMbomRecordDAO.getMbomRecordsByOrderId(dataDetailQuery);
        //即将要更新的数据
        List<HzMbomLineRecord> updateList = new ArrayList<>();
        //即将要删除的数据
        List<HzMbomLineRecord> deleteMbom = new ArrayList<>();
        //要新增的数据
        List<HzMbomLineRecord> addList = new ArrayList<>();

        if (ListUtil.isNotEmpty(records)) {
            Date date = new Date();
            records.forEach(record -> {
                if (4 == record.getStatus()) {
                    deleteMbom.add(record);
                } else {
                    HzMbomLineRecord bomLineRecord = HzMbomRecordFactory.mbomLineRecordToMbomLineRecord(record);
                    String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision() + 1));
                    bomLineRecord.setStatus(1);
                    bomLineRecord.setEffectTime(date);
                    bomLineRecord.setRevision(revision);
                    bomLineRecord.setTableName(ChangeTableNameEnum.HZ_MBOM_FINANCE.getTableName());

                    record.setStatus(1);
                    record.setRevision(revision);
                    record.setEffectTime(date);
                    updateList.add(bomLineRecord);
                    addList.add(record);
                }

            });
        }

        if (ListUtil.isNotEmpty(deleteMbom)) {
            HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
            vo.setTableName(ChangeTableNameEnum.HZ_MBOM_FINANCE.getTableName());
            vo.setRecordList(deleteMbom);
            hzMbomRecordDAO.deleteMbomList(vo);
        }
        if (ListUtil.isNotEmpty(updateList)) {
            hzMbomRecordDAO.updateList(updateList);
        }
        if (ListUtil.isNotEmpty(addList)) {
            HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
            vo.setTableName(ChangeTableNameEnum.HZ_MBOM_FINANCE_BRFORE.getTableName());
            vo.setRecordList(addList);

            hzMbomRecordDAO.insertVO(vo);
        }


    }

    /**
     * 生产型MBOM变更审核通过
     *
     * @param tableName
     */
    private void mbomProductChange(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);
        List<HzMbomLineRecord> records = hzMbomRecordDAO.getMbomRecordsByOrderId(dataDetailQuery);
        //即将要更新的数据
        List<HzMbomLineRecord> updateList = new ArrayList<>();
        //即将要删除的数据
        List<HzMbomLineRecord> deleteMbom = new ArrayList<>();
        //要新增的数据
        List<HzMbomLineRecord> addList = new ArrayList<>();

        if (ListUtil.isNotEmpty(records)) {
            Date date = new Date();
            records.forEach(record -> {
                if (4 == record.getStatus()) {
                    deleteMbom.add(record);
                } else {
                    HzMbomLineRecord bomLineRecord = HzMbomRecordFactory.mbomLineRecordToMbomLineRecord(record);
                    String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision() + 1));
                    bomLineRecord.setStatus(1);
                    bomLineRecord.setEffectTime(date);
                    bomLineRecord.setRevision(revision);
                    bomLineRecord.setTableName(ChangeTableNameEnum.HZ_MBOM_PRODUCT.getTableName());

                    record.setStatus(1);
                    record.setRevision(revision);
                    record.setEffectTime(date);
                    updateList.add(bomLineRecord);
                    addList.add(record);
                }

            });
        }

        if (ListUtil.isNotEmpty(deleteMbom)) {
            HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
            vo.setTableName(ChangeTableNameEnum.HZ_MBOM_PRODUCT.getTableName());
            vo.setRecordList(deleteMbom);
            hzMbomRecordDAO.deleteMbomList(vo);
        }
        if (ListUtil.isNotEmpty(updateList)) {
            hzMbomRecordDAO.updateList(updateList);
        }
        if (ListUtil.isNotEmpty(addList)) {
            HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
            vo.setTableName(ChangeTableNameEnum.HZ_MBOM_PRODUCT_BEFORE.getTableName());
            vo.setRecordList(addList);
            hzMbomRecordDAO.insertVO(vo);
        }


    }

    /**
     * 物料变更审核通过
     *
     * @param tableName
     */
    private void materielChange(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);
        List<HzMaterielRecord> records = hzMaterielDAO.getMaterielRecordsByOrderId(dataDetailQuery);
        //即将要更新的数据
        List<HzMaterielRecord> updateList = new ArrayList<>();
        //即将要删除的数据 线程安全
        List<HzMaterielRecord> deleteList = new ArrayList<>();
        //要新增的数据
        List<HzMaterielRecord> addList = new ArrayList<>();

        if (ListUtil.isNotEmpty(records)) {
            Date date = new Date();
            records.forEach(record -> {
                if (4 == record.getpValidFlag()) {
                    deleteList.add(record);
                } else {
                    HzMaterielRecord hzPbomLineRecord = HzMaterielFactory.hzMaterielRecordToMaterielRecord(record);
                    String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision() + 1));
                    hzPbomLineRecord.setpValidFlag(1);
                    hzPbomLineRecord.setEffectTime(date);
                    hzPbomLineRecord.setRevision(revision);

                    record.setpValidFlag(1);
                    record.setRevision(revision);
                    record.setEffectTime(date);
                    updateList.add(hzPbomLineRecord);
                    addList.add(record);
                }

            });
        }

        if (ListUtil.isNotEmpty(deleteList)) {
            hzMaterielDAO.deleteMaterielList(deleteList,ChangeTableNameEnum.HZ_MATERIEL.getTableName());
        }
        if (ListUtil.isNotEmpty(updateList)) {
            hzMaterielDAO.updateList(updateList);
        }
        if (ListUtil.isNotEmpty(addList)) {
            hzMaterielDAO.insertList(addList, ChangeTableNameEnum.HZ_MATERIEL_BEFORE.getTableName());
        }


//        }
    }

    /**
     * 工艺路线变更审核通过
     *
     * @param tableName
     */
    private void workProduceChange(String tableName, Long orderId, String projectId) {

        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);
        List<HzWorkProcedure> records = hzWorkProcedureDAO.getWorkProcedureByOrderId(dataDetailQuery);
        //即将要更新的数据
        List<HzWorkProcedure> updateList = new ArrayList<>();
        //即将要删除的数据 线程安全
        List<String> deleteList = new ArrayList<>();
        //要新增的数据
        List<HzWorkProcedure> addList = new ArrayList<>();

        if (ListUtil.isNotEmpty(records)) {
            Date date = new Date();
            records.forEach(record -> {
                if (4 == record.getpStatus()) {
                    deleteList.add(record.getPuid());
                } else {
                    HzWorkProcedure hzPbomLineRecord = HzWorkProcedureFactory.workProcedureToProcedure(record);
                    String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision() + 1));
                    hzPbomLineRecord.setpStatus(1);
                    hzPbomLineRecord.setEffectTime(date);
                    hzPbomLineRecord.setRevision(revision);

                    record.setpStatus(1);
                    record.setRevision(revision);
                    record.setEffectTime(date);
                    updateList.add(hzPbomLineRecord);
                    addList.add(record);
                }

            });
        }

        if (ListUtil.isNotEmpty(deleteList)) {
            hzWorkProcedureDAO.deleteByPuids(deleteList,ChangeTableNameEnum.HZ_WORK_PROCEDURE.getTableName());
        }
        if (ListUtil.isNotEmpty(updateList)) {
            hzWorkProcedureDAO.updateList(updateList);
        }
        if (ListUtil.isNotEmpty(addList)) {
            hzWorkProcedureDAO.insertList(addList, ChangeTableNameEnum.HZ_WORK_PROCEDURE_BEFORE.getTableName());
        }

    }

}
