/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.process.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.bom.service.process.IFunctionDesc;
import com.connor.hozon.bom.service.process.IInterruptionCallBack;
import com.connor.hozon.bom.service.process.IDataModifier;
import com.connor.hozon.bom.resources.domain.model.*;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzMbomLineRecord;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzPbomLineRecord;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkProcedure;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:  流程中断操作类，这里写具体的操作业务逻辑
 * @Date: Created in  2018/11/22 11:17
 * @Modified By:
 */
@Component
@EnableTransactionManagement(proxyTargetClass = true)
//要在事务的类中抛出RuntimeException异常，而不是抛出Exception
@Transactional(rollbackFor = {IllegalArgumentException.class, RuntimeException.class, Exception.class})
public class InterruptEntity implements IInterruptionCallBack, IFunctionDesc, IDataModifier {

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


    @Override
    public void interruptionFunctionDesc() {
        System.out.println("执行的是" + this.getClass().getCanonicalName() + ".interruption回调");
    }

    @Override
    public void releaseFunctionDesc() {

    }

    /**
     * 中断流程，所有的数据恢复上一个发布版本
     *
     * @param orderId 变更表单的ID
     * @param params  配置参数，预留
     * @return
     */
    @Override
    public boolean interrupt(Long orderId, Object... params) {
        interruptionFunctionDesc();
        /***
         * 在这里写数据中断操作
         */
        return configuration(orderId, params) && bom(orderId, params);
    }
    /**
     * 在这里写配置管理部分数据的回到上一个版本的代码
     *
     * @param orderId
     * @param params
     * @return
     * @InChage zhudb
     */
    @Override
    public boolean configuration(Long orderId, Object... params) {
        return true;
    }

    /**
     * 在这里写BOM管理部分数据的回到发起流程前的状态
     * BOM 端变更表单数据审核不通过
     * @param orderId 变更表单的ID
     * @param params 配置参数，预留
     * @return
     * @InChage haozt
     */
    @Override
    public boolean bom(Long orderId, Object... params) {
        return true;
        //根据表单id 获取全部的变更数据
//        HzChangeDataQuery hzChangeDataQuery = new HzChangeDataQuery();
//        hzChangeDataQuery.setOrderId(orderId);
//        //项目id
//        HzChangeOrderRecord hzChangeOrderRecord = hzChangeOrderDAO.findHzChangeOrderRecordById(orderId);
//        if (hzChangeOrderRecord == null) {
//            return false;
//        }
//        String projectId = hzChangeOrderRecord.getProjectId();
//
//        List<HzChangeDataRecord> list = hzChangeDataRecordDAO.getChangeDataTableName(hzChangeDataQuery);
//        //EBOM  PBOM  MBOM  物料  工艺路线 BOM端 目前就这5种类型
//        if (ListUtils.isNotEmpty(list)) {
//            ExecutorServices executorServices = new ExecutorServices(list.size());
//            ExecutorService service = executorServices.getPool();
//            try {
//                for (HzChangeDataRecord record : list) {
//                    if (ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName().equals(record.getTableName())) {
//                        service.submit(() -> {
//                            ebomChangeInterrupt(record.getTableName(), orderId, projectId);
//                        });
//                    } else if (ChangeTableNameEnum.HZ_PBOM_AFTER.getTableName().equals(record.getTableName())) {
//                        service.submit(() -> {
//                            pbomChangeInterrupt(record.getTableName(), orderId, projectId);
//                        });
//                    } else if (ChangeTableNameEnum.HZ_MBOM_AFTER.getTableName().equals(record.getTableName())) {
//                        service.submit(() -> {
//                            mbomChangeInterrupt(record.getTableName(), orderId, projectId);
//                        });
//                    } else if (ChangeTableNameEnum.HZ_MBOM_PRODUCT_AFTER.getTableName().equals(record.getTableName())) {
//                        service.submit(() -> {
//                            mbomProductChangeInterrupt(record.getTableName(), orderId, projectId);
//                        });
//                    } else if (ChangeTableNameEnum.HZ_MBOM_FINANCE_AFTER.getTableName().equals(record.getTableName())) {
//                        service.submit(() -> {
//                            mbomFinanceChangeInterrupt(record.getTableName(), orderId, projectId);
//                        });
//                    } else if (ChangeTableNameEnum.HZ_MATERIEL_AFTER.getTableName().equals(record.getTableName())) {
//                        service.submit(() -> {
//                            materielChangeInterrupt(record.getTableName(), orderId, projectId);
//                        });
//                    } else if (ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.getTableName().equals(record.getTableName())) {
//                        service.submit(() -> {
//                            workProduceChangeInterrupt(record.getTableName(), orderId, projectId);
//                        });
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            } finally {
//                if (service != null) {
//                    service.shutdown();
//                }
//            }
//        }
//        return true;
    }



    /**
     * EBOM变更审核不通过
     *
     * @param tableName
     */
    private void ebomChangeInterrupt(String tableName, Long orderId, String projectId) {
//        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
//        dataDetailQuery.setOrderId(orderId);
//        dataDetailQuery.setTableName(tableName);
//        dataDetailQuery.setProjectId(projectId);
//
//        List<HzEPLManageRecord> records = hzEbomRecordDAO.getEbomRecordsByOrderId(dataDetailQuery);
//        List<HzEPLManageRecord> bomLineRecords = new ArrayList<>();
//        if(ListUtils.isNotEmpty(records)){
//            records.forEach(record -> {
//                HzEPLManageRecord bomLineRecord = HzEbomRecordFactory.ebomRecordToEBOMRecord(record);
//                bomLineRecord.setStatus(record.getStatus());
//                bomLineRecord.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
//                bomLineRecords.add(bomLineRecord);
//            });
//        }
//        if(ListUtils.isNotEmpty(bomLineRecords)){
//            hzEbomRecordDAO.updateListByEplId(bomLineRecords);
//        }
    }

    /**
     * PBOM变更审核不通过
     *
     * @param tableName
     */
    private void pbomChangeInterrupt(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomRecordsByOrderId(dataDetailQuery);
        if(ListUtils.isNotEmpty(records)){
            hzPbomRecordDAO.updateList(records);
        }

    }

    /**
     * MBOM变更审核不通过
     *
     * @param tableName
     */
    private void mbomChangeInterrupt(String tableName, Long orderId, String projectId) {

        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);

        List<HzMbomLineRecord> records = hzMbomRecordDAO.getMbomRecordsByOrderId(dataDetailQuery);
        List<HzMbomLineRecord> bomLineRecords = new ArrayList<>();
        if(ListUtils.isNotEmpty(records)){
            records.forEach(record -> {
                HzMbomLineRecord bomLineRecord = HzMbomRecordFactory.mbomLineRecordToMbomLineRecord(record);
                bomLineRecord.setStatus(record.getStatus());
                bomLineRecord.setTableName(ChangeTableNameEnum.HZ_MBOM.getTableName());
                bomLineRecords.add(bomLineRecord);
            });
        }
        if(ListUtils.isNotEmpty(bomLineRecords)){
            hzMbomRecordDAO.updateList(bomLineRecords);
        }
    }

    /**
     * 财务型MBOM变更审核不通过
     *
     * @param tableName
     */
    private void mbomFinanceChangeInterrupt(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);

        List<HzMbomLineRecord> records = hzMbomRecordDAO.getMbomRecordsByOrderId(dataDetailQuery);
        List<HzMbomLineRecord> bomLineRecords = new ArrayList<>();
        if(ListUtils.isNotEmpty(records)){
            records.forEach(record -> {
                HzMbomLineRecord bomLineRecord = HzMbomRecordFactory.mbomLineRecordToMbomLineRecord(record);
                bomLineRecord.setStatus(record.getStatus());
                bomLineRecord.setTableName(ChangeTableNameEnum.HZ_MBOM_FINANCE.getTableName());
                bomLineRecords.add(bomLineRecord);
            });
        }
        if(ListUtils.isNotEmpty(bomLineRecords)){
            hzMbomRecordDAO.updateList(bomLineRecords);
        }
    }

    /**
     * 生产型MBOM变更审核不通过
     *
     * @param tableName
     */
    private void mbomProductChangeInterrupt(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);

        List<HzMbomLineRecord> records = hzMbomRecordDAO.getMbomRecordsByOrderId(dataDetailQuery);
        List<HzMbomLineRecord> bomLineRecords = new ArrayList<>();
        if(ListUtils.isNotEmpty(records)){
            records.forEach(record -> {
                HzMbomLineRecord bomLineRecord = HzMbomRecordFactory.mbomLineRecordToMbomLineRecord(record);
                bomLineRecord.setStatus(record.getStatus());
                bomLineRecord.setTableName(ChangeTableNameEnum.HZ_MBOM_PRODUCT.getTableName());
                bomLineRecords.add(bomLineRecord);
            });
        }
        if(ListUtils.isNotEmpty(bomLineRecords)){
            hzMbomRecordDAO.updateList(bomLineRecords);
        }
    }

    /**
     * 物料变更审核不通过
     *
     * @param tableName
     */
    private void materielChangeInterrupt(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);

        List<HzMaterielRecord> records = hzMaterielDAO.getMaterielRecordsByOrderId(dataDetailQuery);
        if(ListUtils.isNotEmpty(records)){
            hzMaterielDAO.updateList(records);
        }

    }

    /**
     * 工艺路线变更审核不通过
     *
     * @param tableName
     */
    private void workProduceChangeInterrupt(String tableName, Long orderId, String projectId) {
        HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
        dataDetailQuery.setOrderId(orderId);
        dataDetailQuery.setTableName(tableName);
        dataDetailQuery.setProjectId(projectId);

        List<HzWorkProcedure> records = hzWorkProcedureDAO.getWorkProcedureByOrderId(dataDetailQuery);
        if (ListUtils.isNotEmpty(records)) {
            hzWorkProcedureDAO.updateList(records);
        }
    }

}
