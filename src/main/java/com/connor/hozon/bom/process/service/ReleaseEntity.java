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
import com.connor.hozon.bom.bomSystem.dao.relevance.HzRelevanceBasicChangeDao;
import com.connor.hozon.bom.bomSystem.dao.relevance.HzRelevanceBasicDao;
import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzFeatureChangeService;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVWOManagerService;
import com.connor.hozon.bom.bomSystem.iservice.integrate.ISynBomService;
import com.connor.hozon.bom.bomSystem.iservice.process.IFunctionDesc;
import com.connor.hozon.bom.bomSystem.iservice.process.IReleaseCallBack;
import com.connor.hozon.bom.bomSystem.service.integrate.SynMaterielService;
import com.connor.hozon.bom.bomSystem.service.integrate.SynProcessRouteService;
import com.connor.hozon.bom.process.iservice.IDataModifier;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzMaterielReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.*;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataQuery;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.executors.ExecutorServices;
import com.connor.hozon.bom.resources.mybatis.bom.HzBOMScheduleTaskDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.exception.HzBomException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import sql.pojo.bom.*;
import sql.pojo.cfg.relevance.HzRelevanceBasic;
import sql.pojo.cfg.relevance.HzRelevanceBasicChange;
import sql.pojo.change.HzChangeDataRecord;
import sql.pojo.change.HzChangeOrderRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.project.HzMaterielRecord;
import sql.pojo.work.HzWorkProcedure;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 流程通过，进行数据发布，这里写具体的修改数据的逻辑
 * @Date: Created in  2018/11/22 15:15
 * @Modified By:
 */
@Component
@EnableTransactionManagement(proxyTargetClass = true)
@Transactional(rollbackFor = {IllegalArgumentException.class, RuntimeException.class,HzBomException.class, Exception.class})
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
    private SynMaterielService synMaterielService;

    @Autowired
    private HzRelevanceBasicDao hzRelevanceBasicDao;

    @Autowired
    private HzRelevanceBasicChangeDao hzRelevanceBasicChangeDao;

    @Autowired
    private SynProcessRouteService synProcessRouteService;

    @Autowired
    private IHzVWOManagerService hzVWOManagerService;

    private String errMsg ="";

    private TransactionTemplate configTransactionTemplate;

    private HzBOMScheduleTaskDAO hzBOMScheduleTaskDAO;

    @Autowired
    public void setHzBOMScheduleTaskDAO(HzBOMScheduleTaskDAO hzBOMScheduleTaskDAO) {
        this.hzBOMScheduleTaskDAO = hzBOMScheduleTaskDAO;
    }

    @Autowired
    public void setConfigTransactionTemplate(TransactionTemplate configTransactionTemplate) {
        this.configTransactionTemplate = configTransactionTemplate;
    }
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
    @Transactional(rollbackFor = {HzBomException.class, Exception.class})
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
            for (HzChangeDataRecord hzChangeDataRecord : list) {
                //特性变更批准
                if (ChangeTableNameEnum.HZ_CFG0_AFTER_CHANGE_RECORD.getTableName().equals(hzChangeDataRecord.getTableName())) {
                    //发送至sap
                    if (!hzVWOManagerService.featureToSap(orderId)) {
                        throw new HzBomException("特性发送SAP失败");
//                            return false;
                    }
                    //将变更数据的状态修改为以生效
                    if (!iHzFeatureChangeService.updateStatusByOrderId(orderId, 1)) {
                        return false;
                    }
                    //删除状态为删除状态的源数据
                    hzCfg0RecordDao.deleteByOrderId(orderId);
                    //将源数据修改为已生效
                    if (hzCfg0RecordDao.updateStatusByOrderId(orderId, 1) <= 0 ? true : false) {
                        return false;
                    }
                    //配色方案变更批准
                } else if (ChangeTableNameEnum.HZ_CMCR_AFTER_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())) {
                    //修改配色方案变更状态为已生效
                    if (hzCmcrChangeDao.updateStatusByOrderId(orderId, 1) <= 0 ? true : false) {
                        return false;
                    }
                    //删除状态为删除状态的源数据
                    hzCfg0ModelColorDao.deleteByOrderId(orderId);

                    if (hzCfg0ModelColorDao.updateStatusByOrderId(orderId, 1) <= 0 ? true : false) {
                        return false;
                    }
                    //衍生物料变更批准
                } else if (ChangeTableNameEnum.HZ_DM_BASIC_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())) {
                    //发送至sap
                    if (!hzVWOManagerService.derivativeMaterielToSap(orderId)) {
                        throw new HzBomException("衍生物料发送至SAP失败");
//                            return false;
                    }
                    if (hzDMBasicChangeDao.updateStatusByOrderId(orderId, 1) <= 0 ? true : false) {
                        return false;
                    }
                    //删除状态为删除状态的源数据
                    hzDerivativeMaterielBasicDao.deleteByOrderId(orderId);
                    if (hzDerivativeMaterielBasicDao.updateStatusByOrderId(orderId, 1) <= 0 ? true : false) {
                        return false;
                    }

                    // 2019.1.2 by haozt 配置物料特性表变更通过后 需要记录定时任务
                    HzBOMScheduleTask task = new HzBOMScheduleTask();
                    task.setProjectId(projectId);
                    task.setOrderId(orderId);
                    task.setHasSynchronized(0);
                    task.setConfigFeatureChanged(1);
                    try {
                         hzBOMScheduleTaskDAO.insert(task);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    //全配置变更批准
                } else if (ChangeTableNameEnum.HZ_FULL_CFG_MAIN_RECORD_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())) {
                    if (hzFullCfgMainDao.updateStatusByOrderId(orderId, 1) <= 0 ? true : false) {
                        return false;
                    }
                    if (hzFullCfgMainChangeDao.updateStatusByOrderId(orderId, 1) <= 0 ? true : false) {
                        return false;
                    }
                } else if (ChangeTableNameEnum.HZ_RELEVANCE_BASIC_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())) {
                    //发送至SAP
                    if (!hzVWOManagerService.relevanceToSap(orderId)) {
                        throw new HzBomException("相关性发送至SAP失败");
//                            return false;
                    }
                    HzRelevanceBasic hzRelevanceBasic = new HzRelevanceBasic();
                    hzRelevanceBasic.setRbVwoId(orderId);
                    hzRelevanceBasic.setRelevanceStatus(1);
                    HzRelevanceBasicChange hzRelevanceBasicChange = new HzRelevanceBasicChange();
                    hzRelevanceBasicChange.setChangeOrderId(orderId);
                    hzRelevanceBasicChange.setChangeStatus(1);
                    if (hzRelevanceBasicChangeDao.updateStatusByIOrderId(hzRelevanceBasicChange) <= 0 ? true : false) {
                        return false;
                    }
                    //删除 状态为删除状态的源数据
                    hzRelevanceBasicDao.deleteByOrderId(orderId);
                    if (hzRelevanceBasicDao.updateStatusByOrderChangeId(hzRelevanceBasic) <= 0 ? true : false) {
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
    @Transactional(rollbackFor = {HzBomException.class, Exception.class})
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
                        Future future = service.submit(() -> {
                            ebomChange(record.getTableName(), orderId, projectId);
                        });
                        future.get();
                    } else if (ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName().equals(record.getTableName())) {
                        Future future = service.submit(() -> {
                            pbomChange(record.getTableName(), orderId, projectId);
                        });
                        future.get();
                    } else if (ChangeTableNameEnum.HZ_MBOM_AFTER.getTableName().equals(record.getTableName())) {
                        Future future =service.submit(() -> {
                            mbomChange(record.getTableName(), orderId, projectId);
                        });
                        future.get();
                    } else if (ChangeTableNameEnum.HZ_MBOM_PRODUCT_AFTER.getTableName().equals(record.getTableName())) {
                        Future future =service.submit(() -> {
                            mbomProductChange(record.getTableName(), orderId, projectId);
                        });
                        future.get();
                    } else if (ChangeTableNameEnum.HZ_MBOM_FINANCE_AFTER.getTableName().equals(record.getTableName())) {
                        Future future =service.submit(() -> {
                            mbomFinanceChange(record.getTableName(), orderId, projectId);
                        });
                        future.get();
                    } else if (ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName().equals(record.getTableName())) {
                        Future future = service.submit(() -> {
                            materielChange(record.getTableName(), orderId, projectId);
                        });
                        future.get();
                    } else if (ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName().equals(record.getTableName())) {
                        Future future =service.submit(() -> {
                            workProduceChange(record.getTableName(), orderId, projectId);
                        });
                        future.get();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new HzBomException(e);
            }
            finally {
                if (service != null) {
                    service.shutdown();
                }
                if(StringUtils.isNotBlank(this.errMsg)){
                    this.errMsg ="";
                }
            }
        }
        return true;
    }

    /**
     * EBOM变更审核通过
     * @param tableName
     */
    private void ebomChange(String tableName, Long orderId, String projectId) {
        configTransactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
                dataDetailQuery.setOrderId(orderId);
                dataDetailQuery.setTableName(tableName);
                dataDetailQuery.setProjectId(projectId);
                List<HzEPLManageRecord> records = hzEbomRecordDAO.getEbomRecordsByOrderId(dataDetailQuery);
                //即将要更新的数据
                List<HzEPLManageRecord> updateList = new ArrayList<>();
                //即将要删除的数据
                List<String> deletePuids = new ArrayList<>();
                //要新增的数据
                List<HzEPLManageRecord> addList = new ArrayList<>();

                try {
                    if (ListUtil.isNotEmpty(records)) {
                        Date date = new Date();
                        records.forEach(record -> {
                            if (Integer.valueOf(4).equals(record.getStatus())) {
                                deletePuids.add(record.getPuid());
                            } else {
                                HzEPLManageRecord bomLineRecord = HzEbomRecordFactory.ebomRecordToEBOMRecord(record);
                                String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision()) + 1);
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
                        hzEbomRecordDAO.updateListByEplId(updateList);
                    }
                    if (ListUtil.isNotEmpty(addList)) {
                        hzEbomRecordDAO.insertList(addList, ChangeTableNameEnum.HZ_EBOM_BEFORE.getTableName());
                    }
                }catch (Exception  e){
                    e.printStackTrace();
                    throw new HzBomException(WriteResultRespDTO.FAILED_MSG,e);
                }
                return null;
            };
        });

    }

    /**
     * PBOM变更审核通过
     * @param tableName
     */
    private void pbomChange(String tableName, Long orderId, String projectId) {
        configTransactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
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
                try {
                    if (ListUtil.isNotEmpty(records)) {
                        Date date = new Date();
                        records.forEach(record -> {
                            if (Integer.valueOf(4).equals(record.getStatus())) {
                                stringBuffer.append(record.geteBomPuid() + ",");
                            } else {
                                HzPbomLineRecord hzPbomLineRecord = HzPbomRecordFactory.bomLineRecordToBomRecord(record);
                                String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision()) + 1);
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
                    //2019.1.2 记录PBOM变更后定时任务
                    HzBOMScheduleTask task = new HzBOMScheduleTask();
                    task.setOrderId(orderId);
                    task.setProjectId(projectId);
                    task.setPbomChanged(1);
                    task.setHasSynchronized(0);
                    try {
                        hzBOMScheduleTaskDAO.insert(task);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    throw new HzBomException(WriteResultRespDTO.FAILED_MSG,e);
                }
                return null;
            };
        });


    }

    /**
     * MBOM变更审核通过
     *
     * @param tableName
     */
    private void mbomChange(String tableName, Long orderId, String projectId) {
        configTransactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
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

                try {
                    if (ListUtil.isNotEmpty(records)) {
                        Date date = new Date();
                        records.forEach(record -> {
                            if (Integer.valueOf(4).equals(record.getStatus())) {
                                deleteMbom.add(record);
                            } else {
                                HzMbomLineRecord bomLineRecord = HzMbomRecordFactory.mbomLineRecordToMbomLineRecord(record);
                                String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision())+1);
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

                    //2019.1.2 记录MBOM变更后定时任务
                    HzBOMScheduleTask task = new HzBOMScheduleTask();
                    task.setOrderId(orderId);
                    task.setProjectId(projectId);
                    task.setHasSynchronized(0);
                    task.setMbomChanged(1);
                    try {
                        hzBOMScheduleTaskDAO.insert(task);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    throw new HzBomException(WriteResultRespDTO.FAILED_MSG,e);
                }

                return null;
            };
        });

    }

    /**
     * 财务型MBOM变更审核通过
     *
     * @param tableName
     */
    private void mbomFinanceChange(String tableName, Long orderId, String projectId) {
        configTransactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
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

                try {
                    if (ListUtil.isNotEmpty(records)) {
                        Date date = new Date();
                        records.forEach(record -> {
                            if (Integer.valueOf(4).equals(record.getStatus())) {
                                deleteMbom.add(record);
                            } else {
                                HzMbomLineRecord bomLineRecord = HzMbomRecordFactory.mbomLineRecordToMbomLineRecord(record);
                                String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision()) + 1);
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
                }catch (Exception e){
                    e.printStackTrace();
                    throw new HzBomException(WriteResultRespDTO.FAILED_MSG,e);
                }
                return null;
            };
        });


    }

    /**
     * 生产型MBOM变更审核通过
     *
     * @param tableName
     */
    private void mbomProductChange(String tableName, Long orderId, String projectId) {
        configTransactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
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

                try {
                    if (ListUtil.isNotEmpty(records)) {
                        Date date = new Date();
                        records.forEach(record -> {
                            if (Integer.valueOf(4).equals(record.getStatus())) {
                                deleteMbom.add(record);
                            } else {
                                HzMbomLineRecord bomLineRecord = HzMbomRecordFactory.mbomLineRecordToMbomLineRecord(record);
                                String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision()) + 1);
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
                }catch (Exception e){
                    e.printStackTrace();
                    throw new HzBomException(WriteResultRespDTO.FAILED_MSG,e);
                }
                return null;
            };
        });




    }

    /**
     * 物料变更审核通过
     * @param tableName
     */
    private void materielChange(final String tableName, final Long orderId, final String projectId) {
        configTransactionTemplate.execute(new TransactionCallback<Void>() {
          @Override
          public Void doInTransaction(TransactionStatus status) {
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

              //给SAP传输 删除
              List<EditHzMaterielReqDTO> sapDeleteList = new ArrayList<>();
              //给SAP传输 更新
              List<EditHzMaterielReqDTO> sapUpdateList = new ArrayList<>();
              //给SAP传输 新增
              List<EditHzMaterielReqDTO> sapInsertList = new ArrayList<>();
              //SAP 传输失败信息
              StringBuffer stringBuffer = new StringBuffer();

              Integer errorCount = 0;

              if (ListUtil.isNotEmpty(records)) {
                  Date date = new Date();
                  records.forEach(record -> {
                      if (Integer.valueOf(4) .equals(record.getpValidFlag())) {
                          deleteList.add(record);
                          EditHzMaterielReqDTO reqDTO = new EditHzMaterielReqDTO();
                          reqDTO.setPuid(record.getPuid());
                          sapDeleteList.add(reqDTO);
                      } else {
                          HzMaterielRecord hzPbomLineRecord = HzMaterielFactory.hzMaterielRecordToMaterielRecord(record);
                          EditHzMaterielReqDTO reqDTO = new EditHzMaterielReqDTO();
                          String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision()) + 1);
                          hzPbomLineRecord.setpValidFlag(1);
                          hzPbomLineRecord.setEffectTime(date);
                          hzPbomLineRecord.setRevision(revision);
                          reqDTO.setPuid(record.getPuid());
                          //不要用 1==record.getSendSapFlag() 可能会报NPE
                          if(Integer.valueOf(1).equals(record.getSendSapFlag())){
                              sapUpdateList.add(reqDTO);
                          }else {
                              sapInsertList.add(reqDTO);
                          }
                          record.setpValidFlag(1);
                          record.setRevision(revision);
                          record.setEffectTime(date);
                          updateList.add(hzPbomLineRecord);
                          addList.add(record);
                      }

                  });
              }
              stringBuffer.append("审核中的物料数据传输到SAP系统失败，请核实后重试!<br>");
              //需要传输数据到SAP BOM端需要记录数据传输结果
              if (ListUtil.isNotEmpty(deleteList)) {
//            synMaterielService.deleteByPuids();
                  JSONObject object = synMaterielService.deleteByPuids(sapDeleteList,ChangeTableNameEnum.HZ_MATERIEL.getTableName(),"puid");
                  errorCount += sapTransDataErrMsg(stringBuffer,object);
                  hzMaterielDAO.deleteMaterielList(deleteList,ChangeTableNameEnum.HZ_MATERIEL.getTableName());
              }
              if (ListUtil.isNotEmpty(updateList)) {
                  hzMaterielDAO.updateList(updateList);
                  if(ListUtil.isNotEmpty(sapInsertList)){
                      JSONObject object = synMaterielService.updateOrAddByUids(sapInsertList,ChangeTableNameEnum.HZ_MATERIEL.getTableName(),"P_MATERIEL_CODE");
                      errorCount += sapTransDataErrMsg(stringBuffer,object);
                  }
                  if(ListUtil.isNotEmpty(sapUpdateList)){
                      JSONObject object = synMaterielService.updateOrAddByUids(sapUpdateList,ChangeTableNameEnum.HZ_MATERIEL.getTableName(),"P_MATERIEL_CODE");
                      errorCount += sapTransDataErrMsg(stringBuffer,object);
                  }
              }
              if (ListUtil.isNotEmpty(addList)) {
                  hzMaterielDAO.insertList(addList, ChangeTableNameEnum.HZ_MATERIEL_BEFORE.getTableName());
              }
              if(errorCount >0){
                  errMsg = stringBuffer.toString();
                  throw new HzBomException(errMsg);
              }
              return null;
          };
        });

//        }
    }

    /**
     * 工艺路线变更审核通过
     * @param tableName
     */
    private void workProduceChange(String tableName, Long orderId, String projectId) {
        configTransactionTemplate.execute(new TransactionCallback<Void>() {
          @Override
          public Void doInTransaction(TransactionStatus status) {
              HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
              dataDetailQuery.setOrderId(orderId);
              dataDetailQuery.setTableName(tableName);
              dataDetailQuery.setProjectId(projectId);
              List<HzWorkProcedure> records = hzWorkProcedureDAO.getWorkProcedureByOrderId(dataDetailQuery);
              //即将要更新的数据
              List<HzWorkProcedure> updateList = new ArrayList<>();
              //即将要删除的数据 线程安全
              List<String> deleteList = new ArrayList<>();
              Set<String> materielIds = new HashSet<>();//传SAP 删除
              //要新增的数据
              List<HzWorkProcedure> addList = new ArrayList<>();
              Set<String> addSapSet = new HashSet<>();//传SAP 新增

              try {
                  if (ListUtil.isNotEmpty(records)) {
                      Date date = new Date();
                      records.forEach(record -> {
                          if (Integer.valueOf(4).equals(record.getpStatus())) {
                              deleteList.add(record.getPuid());
                              materielIds.add(record.getMaterielId());
                          } else {
                              HzWorkProcedure hzPbomLineRecord = HzWorkProcedureFactory.workProcedureToProcedure(record);
                              String revision = record.getRevision() == null ? "00" : String.format("%02d", Integer.valueOf(record.getRevision()) + 1);
                              hzPbomLineRecord.setpStatus(1);
                              hzPbomLineRecord.setEffectTime(date);
                              hzPbomLineRecord.setRevision(revision);

                              record.setpStatus(1);
                              record.setRevision(revision);
                              record.setEffectTime(date);
                              updateList.add(hzPbomLineRecord);
                              addSapSet.add(hzPbomLineRecord.getMaterielId());
                              addList.add(record);
                          }

                      });
                  }

                  //传数据到SAP
                  // todo  工艺路线 SAP的接口目前有问题 待和SAP方沟通
                  if (ListUtil.isNotEmpty(deleteList)) {
                      try {
                          String[] strings = materielIds.toArray(new String[materielIds.size()]);
                          synProcessRouteService.deleteRouting(strings,projectId);
                      }catch (Exception e){
                          // todo  接口有问题 暂时不负责
                      }
                      hzWorkProcedureDAO.deleteByPuids(deleteList,ChangeTableNameEnum.HZ_WORK_PROCEDURE.getTableName());
                  }
                  if (ListUtil.isNotEmpty(updateList)) {
                      try {
                          String[] strings = addSapSet.toArray(new String[addSapSet.size()]);
                          synProcessRouteService.addRouting(strings,projectId);
                      }catch (Exception e){
                          // todo  接口有问题 暂时不负责
                      }
                      hzWorkProcedureDAO.updateList(updateList);
                  }
                  if (ListUtil.isNotEmpty(addList)) {
                      hzWorkProcedureDAO.insertList(addList, ChangeTableNameEnum.HZ_WORK_PROCEDURE_BEFORE.getTableName());
                  }
              }catch (Exception e){
                  e.printStackTrace();
                  throw new HzBomException(WriteResultRespDTO.FAILED_MSG,e);
              }
              return null;
          };
        });

    }


    /**
     * 传输SAP途中的磨难 9x9 81难 也未能修成正果
     * @param stringBuffer
     * @param object
     * @return
     */
    private int sapTransDataErrMsg(StringBuffer stringBuffer,JSONObject object){
        Object fail = object.get("fail");
//        Object fail = object.get("success");
        int errorCount = 0;
        if(fail instanceof List){
            List failList = (List)fail;
            for(int i=0;i<failList.size();i++ ){
                String str = (String)((JSONObject) failList.get(i)).get("msg");
                stringBuffer.append(str+"<br>");
                errorCount++;
            }
        }
        return errorCount;
    }

}
