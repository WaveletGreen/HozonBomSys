/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.process.impl;

import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDerivativeMaterielBasicDao;
import cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet.HzFullCfgMainChangeDao;
import cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet.HzFullCfgMainDao;
import cn.net.connor.hozon.dao.dao.configuration.relevance.HzRelevanceBasicChangeDao;
import cn.net.connor.hozon.dao.dao.configuration.relevance.HzRelevanceBasicDao;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBOMScheduleTask;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzMbomLineRecord;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzMbomLineRecordVO;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzPbomLineRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeDataRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;
import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasic;
import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasicChange;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkProcedure;
import cn.net.connor.hozon.dao.query.configuration.fullConfigSheet.HzFullCfgMainChangeQuery;
import cn.net.connor.hozon.dao.query.configuration.fullConfigSheet.HzFullCfgMainQuery;
import cn.net.connor.hozon.services.service.configuration.derivative.HzDMBasicChangeService;
import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.dao.configuration.modelColor.HzCfg0ModelColorDao;
import com.connor.hozon.dao.configuration.modelColor.HzCmcrChangeDao;
import com.connor.hozon.dao.configuration.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.service.change.vwo.HzFeatureChangeService;
import com.connor.hozon.service.change.vwo.HzVWOManagerService;
import com.connor.hozon.service.configuration.feature.FeatureValueService;
import integration.service.integrate.SynBomService;
import com.connor.hozon.service.process.IFunctionDesc;
import com.connor.hozon.service.process.IReleaseCallBack;
import com.connor.hozon.service.process.IDataModifier;
import com.connor.hozon.resources.domain.dto.request.EditHzMaterielReqDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.model.*;
import com.connor.hozon.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.resources.domain.query.HzChangeDataQuery;
import com.connor.hozon.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.resources.domain.query.HzPbomTreeQuery;
import cn.net.connor.hozon.services.common.enumtype.ChangeTableNameEnum;
import com.connor.hozon.resources.executors.ExecutorServices;
import com.connor.hozon.resources.mybatis.bom.HzBOMScheduleTaskDAO;
import com.connor.hozon.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.resources.mybatis.work.HzWorkProcedureDAO;
import cn.net.connor.hozon.common.exception.HzBomException;
import integration.service.integrate.impl.SynMaterielServiceImpl;
import integration.service.integrate.impl.SynProcessRouteService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.concurrent.ExecutorService;
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
    private HzFeatureChangeService hzFeatureChangeService;
    @Autowired
    private FeatureValueService featureValueService;

    @Autowired
    private HzCmcrDetailChangeDao hzCmcrDetailChangeDao;
    @Autowired
    private HzCmcrChangeDao hzCmcrChangeDao;
    @Autowired
    private HzCfg0ModelColorDao hzCfg0ModelColorDao;

    @Autowired
    private HzDMBasicChangeService hzDMBasicChangeService;
    @Autowired
    private HzDerivativeMaterielBasicDao hzDerivativeMaterielBasicDao;

    @Autowired
    private HzFullCfgMainDao hzFullCfgMainDao;

    @Autowired
    private HzFullCfgMainChangeDao hzFullCfgMainChangeDao;

    @Autowired
    private SynBomService synBomService;

    @Autowired
    private SynMaterielServiceImpl synMaterielServiceImpl;

    @Autowired
    private HzRelevanceBasicDao hzRelevanceBasicDao;

    @Autowired
    private HzRelevanceBasicChangeDao hzRelevanceBasicChangeDao;

    @Autowired
    private SynProcessRouteService synProcessRouteService;

    @Autowired
    private HzVWOManagerService hzVWOManagerService;

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
                    //将变更数据的状态修改为以生效
                    if(!hzFeatureChangeService.updateStatusByOrderId(orderId,1)){
                        return false;
                    }
                    //将源数据修改为已生效
                    if(featureValueService.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                    //配色方案变更批准
                }
            }
            for(HzChangeDataRecord hzChangeDataRecord : list){
                if(ChangeTableNameEnum.HZ_FULL_CFG_MAIN_RECORD_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())){
                    if(hzFullCfgMainDao.updateStatusByOrderId(new HzFullCfgMainQuery(orderId,1))<=0?true:false){
                        return false;
                    }
                    if(hzFullCfgMainChangeDao.updateStatusByOrderId(new HzFullCfgMainChangeQuery(orderId,1))<=0?true:false){
                        return false;
                    }
                }
            }
            for(HzChangeDataRecord hzChangeDataRecord : list){
                if(ChangeTableNameEnum.HZ_CMCR_AFTER_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())){
                    //修改配色方案变更状态为已生效
                    if(hzCmcrChangeDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                    if(hzCfg0ModelColorDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                    //衍生物料变更批准
                }
            }
            for(HzChangeDataRecord hzChangeDataRecord : list){
                if(ChangeTableNameEnum.HZ_DM_BASIC_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())){
                    if(hzDMBasicChangeService.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                    if(hzDerivativeMaterielBasicDao.updateStatusByOrderId(orderId,1)<=0?true:false){
                        return false;
                    }
                }
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


            for(HzChangeDataRecord hzChangeDataRecord : list){
                if(ChangeTableNameEnum.HZ_RELEVANCE_BASIC_CHANGE.getTableName().equals(hzChangeDataRecord.getTableName())){
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
        if (ListUtils.isNotEmpty(list)) {
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
                    if (ListUtils.isNotEmpty(records)) {
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


                    if (ListUtils.isNotEmpty(deletePuids)) {
                        //父层删除 子层一起删除
                        List<String> deleteList = new ArrayList<>();//直接进行删除操作
                        List<String> ebomHasChildrenPuids = new ArrayList<>();
                            deletePuids.forEach(puid-> {
                                HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid, projectId);
                                if (record != null) {
                                    ebomHasChildrenPuids.add(record.getParentUid());
                                    if (Integer.valueOf(1).equals(record.getIsHas())) {
                                        HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                                        hzEbomTreeQuery.setPuid(puid);
                                        hzEbomTreeQuery.setProjectId(projectId);
                                        List<HzEPLManageRecord> list = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
                                        if (ListUtils.isNotEmpty(list)) {
                                            list.forEach(l -> {
                                                deleteList.add(l.getPuid());
                                            });
                                        }

                                    } else {
                                        deleteList.add(puid);
                                    }
                                }
                            });
                        hzEbomRecordDAO.deleteByPuids(deleteList,ChangeTableNameEnum.HZ_EBOM.getTableName());

                        List<HzEPLManageRecord> ebomUpdateRecords  = new ArrayList<>();
                        if(ListUtils.isNotEmpty(ebomHasChildrenPuids)){
                            ebomHasChildrenPuids.forEach(puid->{
                                HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                                hzEbomTreeQuery.setPuid(puid);
                                hzEbomTreeQuery.setProjectId(projectId);
                                List<HzEPLManageRecord> list = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
                                if(ListUtils.isNotEmpty(list) && list.size() ==1){
                                    HzEPLManageRecord record = new HzEPLManageRecord();
                                    record.setPuid(list.get(0).getPuid());
                                    record.setIs2Y(list.get(0).getIs2Y());
                                    if(!Integer.valueOf(1).equals(record.getIs2Y())){
                                        record.setIsHas(0);
                                        ebomUpdateRecords.add(record);
                                    }
                                }
                            });
                        }
                        if(ListUtils.isNotEmpty(ebomUpdateRecords)){
                            hzEbomRecordDAO.updateListByPuids(ebomUpdateRecords);
                        }
                    }

                    if (ListUtils.isNotEmpty(updateList)) {
                        hzEbomRecordDAO.updateListByEplId(updateList);
                    }
                    if (ListUtils.isNotEmpty(addList)) {
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
                List<String> deletePuids = new ArrayList<>();
                StringBuffer stringBuffer = new StringBuffer();
                //要新增的数据
                List<HzPbomLineRecord> addList = new ArrayList<>();
                try {
                    if (ListUtils.isNotEmpty(records)) {
                        Date date = new Date();
                        records.forEach(record -> {
                            if (Integer.valueOf(4).equals(record.getStatus())) {
                                deletePuids.add(record.geteBomPuid());
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
                        //父层删除 子层一起删除
                        List<String> deleteList = new ArrayList<>();//直接进行删除操作
                        List<String> pbomHasChildrenPuids = new ArrayList<>();
                        deletePuids.forEach(puid-> {
                            HzPbomLineRecord record = hzPbomRecordDAO.getHzPbomByEbomPuid(puid, projectId);
                            if (record != null) {
                                pbomHasChildrenPuids.add(record.getParentUid());
                                if (Integer.valueOf(1).equals(record.getIsHas())) {
                                    HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                                    hzPbomTreeQuery.setPuid(puid);
                                    hzPbomTreeQuery.setProjectId(projectId);
                                    List<HzPbomLineRecord> list = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                                    if (ListUtils.isNotEmpty(list)) {
                                        list.forEach(l -> {
                                            deleteList.add(l.getPuid());
                                        });
                                    }
                                } else {
                                    deleteList.add(puid);
                                }
                            }
                        });
                        hzPbomRecordDAO.deleteListByPuids(deleteList,ChangeTableNameEnum.HZ_PBOM.getTableName());

                        List<HzPbomLineRecord> pbomUpdateRecords  = new ArrayList<>();
                        if(ListUtils.isNotEmpty(pbomHasChildrenPuids)){
                            pbomHasChildrenPuids.forEach(puid->{
                                HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                                hzPbomTreeQuery.setPuid(puid);
                                hzPbomTreeQuery.setProjectId(projectId);
                                List<HzPbomLineRecord> list = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                                if(ListUtils.isNotEmpty(list) && list.size() ==1){
                                    HzPbomLineRecord record = new HzPbomLineRecord();
                                    record.setPuid(list.get(0).getPuid());
                                    record.setIs2Y(list.get(0).getIs2Y());
                                    if(!Integer.valueOf(1).equals(record.getIs2Y())){
                                        record.setIsHas(0);
                                        pbomUpdateRecords.add(record);
                                    }
                                }
                            });
                        }
                        if(ListUtils.isNotEmpty(pbomUpdateRecords)){
                            hzPbomRecordDAO.updateListByPuids(pbomUpdateRecords);
                        }
                    }
                    if (ListUtils.isNotEmpty(updateList)) {
                        hzPbomRecordDAO.updateList(updateList);
                    }
                    if (ListUtils.isNotEmpty(addList)) {
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
                    if (ListUtils.isNotEmpty(records)) {
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

                    if (ListUtils.isNotEmpty(deleteMbom)) {
                        HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
                        vo.setTableName(ChangeTableNameEnum.HZ_MBOM.getTableName());
                        vo.setRecordList(deleteMbom);
                        hzMbomRecordDAO.deleteMbomList(vo);
                    }
                    if (ListUtils.isNotEmpty(updateList)) {
                        hzMbomRecordDAO.updateList(updateList);
                    }
                    if (ListUtils.isNotEmpty(addList)) {
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
                    if (ListUtils.isNotEmpty(records)) {
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

                    if (ListUtils.isNotEmpty(deleteMbom)) {
                        HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
                        vo.setTableName(ChangeTableNameEnum.HZ_MBOM_FINANCE.getTableName());
                        vo.setRecordList(deleteMbom);
                        hzMbomRecordDAO.deleteMbomList(vo);
                    }
                    if (ListUtils.isNotEmpty(updateList)) {
                        hzMbomRecordDAO.updateList(updateList);
                    }
                    if (ListUtils.isNotEmpty(addList)) {
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
                    if (ListUtils.isNotEmpty(records)) {
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

                    if (ListUtils.isNotEmpty(deleteMbom)) {
                        HzMbomLineRecordVO vo = new HzMbomLineRecordVO();
                        vo.setTableName(ChangeTableNameEnum.HZ_MBOM_PRODUCT.getTableName());
                        vo.setRecordList(deleteMbom);
                        hzMbomRecordDAO.deleteMbomList(vo);
                    }
                    if (ListUtils.isNotEmpty(updateList)) {
                        hzMbomRecordDAO.updateList(updateList);
                    }
                    if (ListUtils.isNotEmpty(addList)) {
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

              if (ListUtils.isNotEmpty(records)) {
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
              if (ListUtils.isNotEmpty(deleteList)) {
//            synMaterielService.deleteByPuids();
                  JSONObject object = synMaterielServiceImpl.deleteByPuids(sapDeleteList,ChangeTableNameEnum.HZ_MATERIEL.getTableName(),"puid");
                  errorCount += sapTransDataErrMsg(stringBuffer,object);
                  hzMaterielDAO.deleteMaterielList(deleteList,ChangeTableNameEnum.HZ_MATERIEL.getTableName());
              }
              if (ListUtils.isNotEmpty(updateList)) {
                  hzMaterielDAO.updateList(updateList);
                  if(ListUtils.isNotEmpty(sapInsertList)){
                      JSONObject object = synMaterielServiceImpl.updateOrAddByUids(sapInsertList,ChangeTableNameEnum.HZ_MATERIEL.getTableName(),"P_MATERIEL_CODE");
                      errorCount += sapTransDataErrMsg(stringBuffer,object);
                  }
                  if(ListUtils.isNotEmpty(sapUpdateList)){
                      JSONObject object = synMaterielServiceImpl.updateOrAddByUids(sapUpdateList,ChangeTableNameEnum.HZ_MATERIEL.getTableName(),"P_MATERIEL_CODE");
                      errorCount += sapTransDataErrMsg(stringBuffer,object);
                  }
              }
              if (ListUtils.isNotEmpty(addList)) {
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
                  if (ListUtils.isNotEmpty(records)) {
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
                  if (ListUtils.isNotEmpty(deleteList)) {
                      try {
                          String[] strings = materielIds.toArray(new String[materielIds.size()]);
                          synProcessRouteService.deleteRouting(strings,projectId);
                      }catch (Exception e){
                          // todo  接口有问题 暂时不负责
                      }
                      hzWorkProcedureDAO.deleteByPuids(deleteList,ChangeTableNameEnum.HZ_WORK_PROCEDURE.getTableName());
                  }
                  if (ListUtils.isNotEmpty(updateList)) {
                      try {
                          String[] strings = addSapSet.toArray(new String[addSapSet.size()]);
                          synProcessRouteService.addRouting(strings,projectId);
                      }catch (Exception e){
                          // todo  接口有问题 暂时不负责
                      }
                      hzWorkProcedureDAO.updateList(updateList);
                  }
                  if (ListUtils.isNotEmpty(addList)) {
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
