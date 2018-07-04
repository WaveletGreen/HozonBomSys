package com.connor.hozon.bom.resources.service.materiel.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.AddHzMaterielReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzMaterielReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.service.materiel.HzMaterielService;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        if(null == addHzMaterielReqDTO.getpPertainToProjectPuid() || addHzMaterielReqDTO.getpPertainToProjectPuid().equals("")){
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
            record.setpPertainToProjectPuid(addHzMaterielReqDTO.getpPertainToProjectPuid());
            record.setpAcceptHandTime(addHzMaterielReqDTO.getpAcceptHandTime());
            record.setpAssessmentType(addHzMaterielReqDTO.getpAssessmentType());
            record.setpBasicUnitMeasure(addHzMaterielReqDTO.getpBasicUnitMeasure());
            record.setpBatchSize(addHzMaterielReqDTO.getpBatchSize());
            record.setpBulkMateriel(addHzMaterielReqDTO.getpBulkMateriel());
            record.setpBranch(addHzMaterielReqDTO.getpBranch());
            record.setpBuyGroup(addHzMaterielReqDTO.getpBuyGroup());
            record.setpBuyType(addHzMaterielReqDTO.getpBuyType());
            record.setpConsumeModel(addHzMaterielReqDTO.getpConsumeModel());
            record.setpCreateName(user.getUserName());
            record.setpDeliveryFactory(addHzMaterielReqDTO.getpDeliveryFactory());
            record.setpDistributionChannel(addHzMaterielReqDTO.getpDistributionChannel());
            record.setpFixedBatchSize(addHzMaterielReqDTO.getpFixedBatchSize());
            record.setpForwardTime(addHzMaterielReqDTO.getpForwardTime());
            record.setpLoadingGroup(addHzMaterielReqDTO.getpLoadingGroup());
            record.setpMakeAdmin(addHzMaterielReqDTO.getpMakeAdmin());
            record.setpMakeLocation(addHzMaterielReqDTO.getpMakeLocation());
            record.setpMakePlanArgsFile(addHzMaterielReqDTO.getpMakePlanArgsFile());
            record.setpMakeTime(addHzMaterielReqDTO.getpMakeTime());
            record.setpMaterielDataType(addHzMaterielReqDTO.getpMaterielDataType());
            record.setpMaterielDescEn(addHzMaterielReqDTO.getpMaterielDescEn());
            record.setpMaterielGroup(addHzMaterielReqDTO.getpMaterielGroup());
            record.setpMaterielPriceGroup(addHzMaterielReqDTO.getpMaterielPriceGroup());
            record.setpMaterielResource(addHzMaterielReqDTO.getpMaterielResource());
            record.setpMaterielSummaryGroup(addHzMaterielReqDTO.getpMaterielSummaryGroup());
            record.setpMaxBatchSize(addHzMaterielReqDTO.getpMaxBatchSize());
            record.setpMinBatchSize(addHzMaterielReqDTO.getpMinBatchSize());
            record.setpMrpController(addHzMaterielReqDTO.getpMrpController());
            record.setpMrpType(addHzMaterielReqDTO.getpMrpType());
            record.setpNoCalCost(addHzMaterielReqDTO.getpNoCalCost());
            record.setpOrdinaryProjGroup(addHzMaterielReqDTO.getpOrdinaryProjGroup());
            record.setpOuterBuyLocation(addHzMaterielReqDTO.getpOuterBuyLocation());
            record.setpOutMaterielGroup(addHzMaterielReqDTO.getpOutMaterielGroup());
            record.setpOutputTax(addHzMaterielReqDTO.getpOutputTax());
            record.setpPassBillToStock(addHzMaterielReqDTO.getpPassBillToStock());
            record.setpPlanDeliveryTime(addHzMaterielReqDTO.getpPlanDeliveryTime());
            record.setpPlanMarginCode(addHzMaterielReqDTO.getpPlanMarginCode());
            record.setpPriceControl(addHzMaterielReqDTO.getpPriceControl());
            record.setpPriceSure(addHzMaterielReqDTO.getpPriceSure());
            record.setpProductLevel(addHzMaterielReqDTO.getpProductLevel());
            record.setpProfitCenter(addHzMaterielReqDTO.getpProfitCenter());
            record.setpProjGroup(addHzMaterielReqDTO.getpProjGroup());
            record.setpPurchaseOrderAuto(addHzMaterielReqDTO.getpPurchaseOrderAuto());
            record.setpQuota(addHzMaterielReqDTO.getpQuota());
            record.setpRecoil(addHzMaterielReqDTO.getpRecoil());
            record.setpRepeatMake(addHzMaterielReqDTO.getpRepeatMake());
            record.setpRepeatMakeArgsFile(addHzMaterielReqDTO.getpRepeatMakeArgsFile());
            record.setpReverseConsumeTime(addHzMaterielReqDTO.getpReverseConsumeTime());
            record.setpRoundValue(addHzMaterielReqDTO.getpRoundValue());
            record.setpSafetyStock(addHzMaterielReqDTO.getpSafetyStock());
            record.setpSaleGroup(addHzMaterielReqDTO.getpSaleGroup());
            record.setpSerialArgsFile(addHzMaterielReqDTO.getpSerialArgsFile());
            record.setpSpecBuyType(addHzMaterielReqDTO.getpSpecBuyType());
            record.setpStrategyGroup(addHzMaterielReqDTO.getpStrategyGroup());
            record.setpSubjectSetGroup(addHzMaterielReqDTO.getpSubjectSetGroup());
            record.setpTransportGroup(addHzMaterielReqDTO.getpTransportGroup());
            record.setpUpdateName(user.getUserName());
            record.setpUsefulCheck(addHzMaterielReqDTO.getpUsefulCheck());
            record.setpMaterielCode(addHzMaterielReqDTO.getpMaterielCode());
            record.setpMaterielType(addHzMaterielReqDTO.getpMaterielType());
            record.setpSubjectSetGroup(addHzMaterielReqDTO.getpStrategyGroup());
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
        if(null == updateHzMaterielReqDTO.getpPertainToProjectPuid() || updateHzMaterielReqDTO.getpPertainToProjectPuid().equals("")){
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
            record.setpPertainToProjectPuid(updateHzMaterielReqDTO.getpPertainToProjectPuid());
            record.setpAcceptHandTime(updateHzMaterielReqDTO.getpAcceptHandTime());
            record.setpAssessmentType(updateHzMaterielReqDTO.getpAssessmentType());
            record.setpBasicUnitMeasure(updateHzMaterielReqDTO.getpBasicUnitMeasure());
            record.setpBatchSize(updateHzMaterielReqDTO.getpBatchSize());
            record.setpBulkMateriel(updateHzMaterielReqDTO.getpBulkMateriel());
            record.setpBranch(updateHzMaterielReqDTO.getpBranch());
            record.setpBuyGroup(updateHzMaterielReqDTO.getpBuyGroup());
            record.setpBuyType(updateHzMaterielReqDTO.getpBuyType());
            record.setpConsumeModel(updateHzMaterielReqDTO.getpConsumeModel());
            record.setpCreateName(user.getUserName());
            record.setpDeliveryFactory(updateHzMaterielReqDTO.getpDeliveryFactory());
            record.setpDistributionChannel(updateHzMaterielReqDTO.getpDistributionChannel());
            record.setpFixedBatchSize(updateHzMaterielReqDTO.getpFixedBatchSize());
            record.setpForwardTime(updateHzMaterielReqDTO.getpForwardTime());
            record.setpLoadingGroup(updateHzMaterielReqDTO.getpLoadingGroup());
            record.setpMakeAdmin(updateHzMaterielReqDTO.getpMakeAdmin());
            record.setpMakeLocation(updateHzMaterielReqDTO.getpMakeLocation());
            record.setpMakePlanArgsFile(updateHzMaterielReqDTO.getpMakePlanArgsFile());
            record.setpMakeTime(updateHzMaterielReqDTO.getpMakeTime());
            record.setpMaterielDataType(updateHzMaterielReqDTO.getpMaterielDataType());
            record.setpMaterielDescEn(updateHzMaterielReqDTO.getpMaterielDescEn());
            record.setpMaterielGroup(updateHzMaterielReqDTO.getpMaterielGroup());
            record.setpMaterielPriceGroup(updateHzMaterielReqDTO.getpMaterielPriceGroup());
            record.setpMaterielResource(updateHzMaterielReqDTO.getpMaterielResource());
            record.setpMaterielSummaryGroup(updateHzMaterielReqDTO.getpMaterielSummaryGroup());
            record.setpMaxBatchSize(updateHzMaterielReqDTO.getpMaxBatchSize());
            record.setpMinBatchSize(updateHzMaterielReqDTO.getpMinBatchSize());
            record.setpMrpController(updateHzMaterielReqDTO.getpMrpController());
            record.setpMrpType(updateHzMaterielReqDTO.getpMrpType());
            record.setpNoCalCost(updateHzMaterielReqDTO.getpNoCalCost());
            record.setpOrdinaryProjGroup(updateHzMaterielReqDTO.getpOrdinaryProjGroup());
            record.setpOuterBuyLocation(updateHzMaterielReqDTO.getpOuterBuyLocation());
            record.setpOutMaterielGroup(updateHzMaterielReqDTO.getpOutMaterielGroup());
            record.setpOutputTax(updateHzMaterielReqDTO.getpOutputTax());
            record.setpPassBillToStock(updateHzMaterielReqDTO.getpPassBillToStock());
            record.setpPlanDeliveryTime(updateHzMaterielReqDTO.getpPlanDeliveryTime());
            record.setpPlanMarginCode(updateHzMaterielReqDTO.getpPlanMarginCode());
            record.setpPriceControl(updateHzMaterielReqDTO.getpPriceControl());
            record.setpPriceSure(updateHzMaterielReqDTO.getpPriceSure());
            record.setpProductLevel(updateHzMaterielReqDTO.getpProductLevel());
            record.setpProfitCenter(updateHzMaterielReqDTO.getpProfitCenter());
            record.setpProjGroup(updateHzMaterielReqDTO.getpProjGroup());
            record.setpPurchaseOrderAuto(updateHzMaterielReqDTO.getpPurchaseOrderAuto());
            record.setpQuota(updateHzMaterielReqDTO.getpQuota());
            record.setpRecoil(updateHzMaterielReqDTO.getpRecoil());
            record.setpRepeatMake(updateHzMaterielReqDTO.getpRepeatMake());
            record.setpRepeatMakeArgsFile(updateHzMaterielReqDTO.getpRepeatMakeArgsFile());
            record.setpReverseConsumeTime(updateHzMaterielReqDTO.getpReverseConsumeTime());
            record.setpRoundValue(updateHzMaterielReqDTO.getpRoundValue());
            record.setpSafetyStock(updateHzMaterielReqDTO.getpSafetyStock());
            record.setpSaleGroup(updateHzMaterielReqDTO.getpSaleGroup());
            record.setpSerialArgsFile(updateHzMaterielReqDTO.getpSerialArgsFile());
            record.setpSpecBuyType(updateHzMaterielReqDTO.getpSpecBuyType());
            record.setpStrategyGroup(updateHzMaterielReqDTO.getpStrategyGroup());
            record.setpSubjectSetGroup(updateHzMaterielReqDTO.getpSubjectSetGroup());
            record.setpTransportGroup(updateHzMaterielReqDTO.getpTransportGroup());
            record.setpUpdateName(user.getUserName());
            record.setpUsefulCheck(updateHzMaterielReqDTO.getpUsefulCheck());
            record.setpMaterielCode(updateHzMaterielReqDTO.getpMaterielCode());
            record.setpMaterielType(updateHzMaterielReqDTO.getpMaterielType());
            record.setpSubjectSetGroup(updateHzMaterielReqDTO.getpStrategyGroup());
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
        //先找出对应的物料类型，添加到物料类型表，将其数据自动带出
        Page<HzMaterielRecord> page = hzMaterielDAO.findHzMaterielForPage(query);
        if(page == null || page.getResult() == null){
            return new Page<>(query.getPage(),query.getLimit(),0);
        }
        try{
            List<HzMaterielRecord> recordList = page.getResult();
            List<HzMaterielRespDTO> respDTOS = new ArrayList<>();
            for(HzMaterielRecord record:recordList){
                HzMaterielRespDTO respDTO = new HzMaterielRespDTO();
                HzFactory factory = hzFactoryDAO.findFactory(record.getpFactoryPuid(),"");
                if(factory !=null){
                    respDTO.setFactoryCode(factory.getpFactoryCode());
                }
                respDTO.setpAcceptHandTime(record.getpAcceptHandTime());
                respDTO.setpAssessmentType(record.getpAssessmentType());
                respDTO.setpBasicUnitMeasure(record.getpBasicUnitMeasure());
                respDTO.setpBatchSize(record.getpBatchSize());
                respDTO.setpBranch(record.getpBranch());
                respDTO.setpBulkMateriel(record.getpBulkMateriel());
                respDTO.setpBuyGroup(record.getpBuyGroup());
                respDTO.setpBuyType(record.getpBuyType());
                respDTO.setpConsumeModel(record.getpConsumeModel());
                respDTO.setpDeliveryFactory(record.getpDeliveryFactory());
                respDTO.setpDistributionChannel(record.getpDistributionChannel());
                respDTO.setpFixedBatchSize(record.getpFixedBatchSize());
                respDTO.setpForwardTime(record.getpForwardTime());
                respDTO.setpLoadingGroup(record.getpLoadingGroup());
                respDTO.setpMakeAdmin(record.getpMakeAdmin());
                respDTO.setpMakeLocation(record.getpMakeLocation());
                respDTO.setpMakePlanArgsFile(record.getpMakePlanArgsFile());
                respDTO.setpMakeTime(record.getpMakeTime());
                respDTO.setpMaterielCode(record.getpMaterielCode());
                respDTO.setpMaterielDesc(record.getpMaterielDesc());
                respDTO.setpMaterielDescEn(record.getpMaterielDescEn());
                respDTO.setpMaterielGroup(record.getpMaterielGroup());
                respDTO.setpMaterielPriceGroup(record.getpMaterielPriceGroup());
                respDTO.setpMaterielResource(record.getpMaterielResource());
                respDTO.setpMaterielSummaryGroup(record.getpMaterielSummaryGroup());
                respDTO.setpMaterielType(record.getpMaterielType());
                respDTO.setpMaxBatchSize(record.getpMaxBatchSize());
                respDTO.setpMinBatchSize(record.getpMinBatchSize());
                respDTO.setpMrpController(record.getpMrpController());
                respDTO.setpMrpType(record.getpMrpType());
                respDTO.setpNoCalCost(record.getpNoCalCost());
                respDTO.setpOrdinaryProjGroup(record.getpOrdinaryProjGroup());
                respDTO.setpOuterBuyLocation(record.getpOuterBuyLocation());
                respDTO.setpOutMaterielGroup(record.getpOutMaterielGroup());
                respDTO.setpOutputTax(record.getpOutputTax());
                respDTO.setpPassBillToStock(record.getpPassBillToStock());
                respDTO.setpPertainToProjectPuid(record.getpPertainToProjectPuid());
                respDTO.setpPlanDeliveryTime(record.getpPlanDeliveryTime());
                respDTO.setpPlanMarginCode(record.getpPlanMarginCode());
                respDTO.setpPriceControl(record.getpPriceControl());
                respDTO.setpPriceSure(record.getpPriceSure());
                respDTO.setpProductLevel(record.getpProductLevel());
                respDTO.setpProfitCenter(record.getpProfitCenter());
                respDTO.setpProjGroup(record.getpProjGroup());
                respDTO.setpPurchaseOrderAuto(record.getpPurchaseOrderAuto());
                respDTO.setpQuota(record.getpQuota());
                respDTO.setpRecoil(record.getpRecoil());
                respDTO.setpRepeatMake(record.getpRepeatMake());
                respDTO.setpRepeatMakeArgsFile(record.getpRepeatMakeArgsFile());
                respDTO.setpReverseConsumeTime(record.getpReverseConsumeTime());
                respDTO.setpRoundValue(record.getpRoundValue());
                respDTO.setpSafetyStock(record.getpSafetyStock());
                respDTO.setpSaleGroup(record.getpSaleGroup());
                respDTO.setpSerialArgsFile(record.getpSerialArgsFile());
                respDTO.setpSpecBuyType(record.getpSpecBuyType());
                respDTO.setpStrategyGroup(record.getpStrategyGroup());
                respDTO.setpSubjectSetGroup(record.getpSubjectSetGroup());
                respDTO.setpTransportGroup(record.getpTransportGroup());
                respDTO.setPuid(record.getPuid());
                respDTO.setpUsefulCheck(record.getpUsefulCheck());
                respDTOS.add(respDTO);
            }
            return new Page<>(query.getPage(),query.getLimit(),page.getTotalCount(),respDTOS);
        }catch (Exception e){
            return null;
        }
    }
    
}
