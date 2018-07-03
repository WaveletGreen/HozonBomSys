package com.connor.hozon.bom.resources.dto.request;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/7/3
 * @Description:
 */
public class UpdateHzMaterielReqDTO {
    /**
     * 主键id
     */
    private String puid;
    /**
     * 物料代码
     */
    private Object pMaterielCode;
    /**
     * 物料类型
     */
    private Object pMaterielType;
    /**
     * 物料中文描述
     */
    private Object pMaterielDesc;
    /**
     * 项目id
     */
    private String pPertainToProjectPuid;
    /**
     * 物料描述 英文
     */
    private String pMaterielDescEn;
    /**
     * 基本计量单位
     */
    private String pBasicUnitMeasure;
    /**
     * 物料组
     */
    private String pMaterielGroup;
    /**
     * 外部物料组
     */
    private String pOutMaterielGroup;
    /**
     * 采购组
     */
    private String pBuyGroup;
    /**
     * 自动采购订单
     */
    private String pPurchaseOrderAuto;
    /**
     * 过账到检验库存
     */
    private String pPassBillToStock;
    /**
     * 配额
     */
    private String pQuota;
    /**
     * MRP类型
     */
    private String pMrpType;
    /**
     * MRP控制者
     */
    private String pMrpController;
    /**
     * 批量大小
     */
    private String pBatchSize;
    /**
     * 固定批量大小
     */
    private String pFixedBatchSize;
    /**
     * 最小批量大小
     */
    private String pMinBatchSize;
    /**
     * 最大批量大小
     */
    private String pMaxBatchSize;
    /**
     * 舍入值
     */
    private String pRoundValue;
    /**
     * 采购类型
     */
    private String pBuyType;
    /**
     * 特殊采购类
     */
    private String pSpecBuyType;
    /**
     * 反冲
     */
    private String pRecoil;
    /**
     * 生产仓存储地点
     */
    private String pMakeLocation;
    /**
     * 外部采购存储地点
     */
    private String pOuterBuyLocation;
    /**
     * 计划边际码
     */
    private String pPlanMarginCode;
    /**
     * 计划交货时间
     */
    private Date pPlanDeliveryTime;
    /**
     * 收货处理时间
     */
    private Date pAcceptHandTime;
    /**
     * 安全库存
     */
    private String pSafetyStock;
    /**
     * 散装物料
     */
    private String pBulkMateriel;
    /**
     * 可用性检查
     */
    private String pUsefulCheck;
    /**
     * 评估类
     */
    private String pAssessmentType;
    /**
     * 物料来源
     */
    private String pMaterielResource;
    /**
     * 利润中心
     */
    private String pProfitCenter;
    /**
     * 价格控制
     */
    private String pPriceControl;
    /**
     * 价格确定
     */
    private String pPriceSure;
    /**
     * 不计算成本
     */
    private String pNoCalCost;
    /**
     * 自制生产时间
     */
    private String pMakeTime;
    /**
     * 策略组
     */
    private String pStrategyGroup;
    /**
     * 消耗模式
     */
    private String pConsumeModel;
    /**
     * 逆向消耗期间
     */
    private String pReverseConsumeTime;
    /**
     * 向前消耗期间
     */
    private String pForwardTime;
    /**
     * 重复制造
     */
    private String pRepeatMake;
    /**
     * 重复制造参数文件
     */
    private String pRepeatMakeArgsFile;
    /**
     * 生产管理员
     */
    private String pMakeAdmin;
    /**
     * 生产计划参数文件
     */
    private String pMakePlanArgsFile;
    /**
     * 序列号参数文件
     */
    private String pSerialArgsFile;
    /**
     * 销售组织
     */
    private String pSaleGroup;
    /**
     * 分销渠道
     */
    private String pDistributionChannel;
    /**
     * 分部
     */
    private String pBranch;
    /**
     * 交货工厂
     */
    private String pDeliveryFactory;
    /**
     * 销项税
     */
    private String pOutputTax;
    /**
     * 物料统计组
     */
    private String pMaterielSummaryGroup;
    /**
     * 物料价格组
     */
    private String pMaterielPriceGroup;
    /**
     * 科目设置组
     */
    private String pSubjectSetGroup;
    /**
     * 普通项目类别组
     */
    private String pOrdinaryProjGroup;
    /**
     * 项目类别组
     */
    private String pProjGroup;
    /**
     * 产品层次
     */
    private String pProductLevel;
    /**
     * 装载组
     */
    private String pLoadingGroup;
    /**
     * 运输组
     */
    private String pTransportGroup;
    /**
     * 物料类型  严格按照注释来读写数据
     * （11 整车超级物料主数据
     * 21 整车衍生物料主数据
     * 31 虚拟件物料主数据
     * 41半成品物料主数据
     * 51 生产性外购物料主数据
     * 61自制备件物料主数据）
     */
    private Integer pMaterielDataType;
    /**
     * 工厂代码
     */
    private String factoryCode;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public Object getpMaterielCode() {
        return pMaterielCode;
    }

    public void setpMaterielCode(Object pMaterielCode) {
        this.pMaterielCode = pMaterielCode;
    }

    public Object getpMaterielType() {
        return pMaterielType;
    }

    public void setpMaterielType(Object pMaterielType) {
        this.pMaterielType = pMaterielType;
    }

    public Object getpMaterielDesc() {
        return pMaterielDesc;
    }

    public void setpMaterielDesc(Object pMaterielDesc) {
        this.pMaterielDesc = pMaterielDesc;
    }

    public String getpPertainToProjectPuid() {
        return pPertainToProjectPuid;
    }

    public void setpPertainToProjectPuid(String pPertainToProjectPuid) {
        this.pPertainToProjectPuid = pPertainToProjectPuid;
    }

    public String getpMaterielDescEn() {
        return pMaterielDescEn;
    }

    public void setpMaterielDescEn(String pMaterielDescEn) {
        this.pMaterielDescEn = pMaterielDescEn;
    }

    public String getpBasicUnitMeasure() {
        return pBasicUnitMeasure;
    }

    public void setpBasicUnitMeasure(String pBasicUnitMeasure) {
        this.pBasicUnitMeasure = pBasicUnitMeasure;
    }

    public String getpMaterielGroup() {
        return pMaterielGroup;
    }

    public void setpMaterielGroup(String pMaterielGroup) {
        this.pMaterielGroup = pMaterielGroup;
    }

    public String getpOutMaterielGroup() {
        return pOutMaterielGroup;
    }

    public void setpOutMaterielGroup(String pOutMaterielGroup) {
        this.pOutMaterielGroup = pOutMaterielGroup;
    }

    public String getpBuyGroup() {
        return pBuyGroup;
    }

    public void setpBuyGroup(String pBuyGroup) {
        this.pBuyGroup = pBuyGroup;
    }

    public String getpPurchaseOrderAuto() {
        return pPurchaseOrderAuto;
    }

    public void setpPurchaseOrderAuto(String pPurchaseOrderAuto) {
        this.pPurchaseOrderAuto = pPurchaseOrderAuto;
    }

    public String getpPassBillToStock() {
        return pPassBillToStock;
    }

    public void setpPassBillToStock(String pPassBillToStock) {
        this.pPassBillToStock = pPassBillToStock;
    }

    public String getpQuota() {
        return pQuota;
    }

    public void setpQuota(String pQuota) {
        this.pQuota = pQuota;
    }

    public String getpMrpType() {
        return pMrpType;
    }

    public void setpMrpType(String pMrpType) {
        this.pMrpType = pMrpType;
    }

    public String getpMrpController() {
        return pMrpController;
    }

    public void setpMrpController(String pMrpController) {
        this.pMrpController = pMrpController;
    }

    public String getpBatchSize() {
        return pBatchSize;
    }

    public void setpBatchSize(String pBatchSize) {
        this.pBatchSize = pBatchSize;
    }

    public String getpFixedBatchSize() {
        return pFixedBatchSize;
    }

    public void setpFixedBatchSize(String pFixedBatchSize) {
        this.pFixedBatchSize = pFixedBatchSize;
    }

    public String getpMinBatchSize() {
        return pMinBatchSize;
    }

    public void setpMinBatchSize(String pMinBatchSize) {
        this.pMinBatchSize = pMinBatchSize;
    }

    public String getpMaxBatchSize() {
        return pMaxBatchSize;
    }

    public void setpMaxBatchSize(String pMaxBatchSize) {
        this.pMaxBatchSize = pMaxBatchSize;
    }

    public String getpRoundValue() {
        return pRoundValue;
    }

    public void setpRoundValue(String pRoundValue) {
        this.pRoundValue = pRoundValue;
    }

    public String getpBuyType() {
        return pBuyType;
    }

    public void setpBuyType(String pBuyType) {
        this.pBuyType = pBuyType;
    }

    public String getpSpecBuyType() {
        return pSpecBuyType;
    }

    public void setpSpecBuyType(String pSpecBuyType) {
        this.pSpecBuyType = pSpecBuyType;
    }

    public String getpRecoil() {
        return pRecoil;
    }

    public void setpRecoil(String pRecoil) {
        this.pRecoil = pRecoil;
    }

    public String getpMakeLocation() {
        return pMakeLocation;
    }

    public void setpMakeLocation(String pMakeLocation) {
        this.pMakeLocation = pMakeLocation;
    }

    public String getpOuterBuyLocation() {
        return pOuterBuyLocation;
    }

    public void setpOuterBuyLocation(String pOuterBuyLocation) {
        this.pOuterBuyLocation = pOuterBuyLocation;
    }

    public String getpPlanMarginCode() {
        return pPlanMarginCode;
    }

    public void setpPlanMarginCode(String pPlanMarginCode) {
        this.pPlanMarginCode = pPlanMarginCode;
    }

    public Date getpPlanDeliveryTime() {
        return pPlanDeliveryTime;
    }

    public void setpPlanDeliveryTime(Date pPlanDeliveryTime) {
        this.pPlanDeliveryTime = pPlanDeliveryTime;
    }

    public Date getpAcceptHandTime() {
        return pAcceptHandTime;
    }

    public void setpAcceptHandTime(Date pAcceptHandTime) {
        this.pAcceptHandTime = pAcceptHandTime;
    }

    public String getpSafetyStock() {
        return pSafetyStock;
    }

    public void setpSafetyStock(String pSafetyStock) {
        this.pSafetyStock = pSafetyStock;
    }

    public String getpBulkMateriel() {
        return pBulkMateriel;
    }

    public void setpBulkMateriel(String pBulkMateriel) {
        this.pBulkMateriel = pBulkMateriel;
    }

    public String getpUsefulCheck() {
        return pUsefulCheck;
    }

    public void setpUsefulCheck(String pUsefulCheck) {
        this.pUsefulCheck = pUsefulCheck;
    }

    public String getpAssessmentType() {
        return pAssessmentType;
    }

    public void setpAssessmentType(String pAssessmentType) {
        this.pAssessmentType = pAssessmentType;
    }

    public String getpMaterielResource() {
        return pMaterielResource;
    }

    public void setpMaterielResource(String pMaterielResource) {
        this.pMaterielResource = pMaterielResource;
    }

    public String getpProfitCenter() {
        return pProfitCenter;
    }

    public void setpProfitCenter(String pProfitCenter) {
        this.pProfitCenter = pProfitCenter;
    }

    public String getpPriceControl() {
        return pPriceControl;
    }

    public void setpPriceControl(String pPriceControl) {
        this.pPriceControl = pPriceControl;
    }

    public String getpPriceSure() {
        return pPriceSure;
    }

    public void setpPriceSure(String pPriceSure) {
        this.pPriceSure = pPriceSure;
    }

    public String getpNoCalCost() {
        return pNoCalCost;
    }

    public void setpNoCalCost(String pNoCalCost) {
        this.pNoCalCost = pNoCalCost;
    }

    public String getpMakeTime() {
        return pMakeTime;
    }

    public void setpMakeTime(String pMakeTime) {
        this.pMakeTime = pMakeTime;
    }

    public String getpStrategyGroup() {
        return pStrategyGroup;
    }

    public void setpStrategyGroup(String pStrategyGroup) {
        this.pStrategyGroup = pStrategyGroup;
    }

    public String getpConsumeModel() {
        return pConsumeModel;
    }

    public void setpConsumeModel(String pConsumeModel) {
        this.pConsumeModel = pConsumeModel;
    }

    public String getpReverseConsumeTime() {
        return pReverseConsumeTime;
    }

    public void setpReverseConsumeTime(String pReverseConsumeTime) {
        this.pReverseConsumeTime = pReverseConsumeTime;
    }

    public String getpForwardTime() {
        return pForwardTime;
    }

    public void setpForwardTime(String pForwardTime) {
        this.pForwardTime = pForwardTime;
    }

    public String getpRepeatMake() {
        return pRepeatMake;
    }

    public void setpRepeatMake(String pRepeatMake) {
        this.pRepeatMake = pRepeatMake;
    }

    public String getpRepeatMakeArgsFile() {
        return pRepeatMakeArgsFile;
    }

    public void setpRepeatMakeArgsFile(String pRepeatMakeArgsFile) {
        this.pRepeatMakeArgsFile = pRepeatMakeArgsFile;
    }

    public String getpMakeAdmin() {
        return pMakeAdmin;
    }

    public void setpMakeAdmin(String pMakeAdmin) {
        this.pMakeAdmin = pMakeAdmin;
    }

    public String getpMakePlanArgsFile() {
        return pMakePlanArgsFile;
    }

    public void setpMakePlanArgsFile(String pMakePlanArgsFile) {
        this.pMakePlanArgsFile = pMakePlanArgsFile;
    }

    public String getpSerialArgsFile() {
        return pSerialArgsFile;
    }

    public void setpSerialArgsFile(String pSerialArgsFile) {
        this.pSerialArgsFile = pSerialArgsFile;
    }

    public String getpSaleGroup() {
        return pSaleGroup;
    }

    public void setpSaleGroup(String pSaleGroup) {
        this.pSaleGroup = pSaleGroup;
    }

    public String getpDistributionChannel() {
        return pDistributionChannel;
    }

    public void setpDistributionChannel(String pDistributionChannel) {
        this.pDistributionChannel = pDistributionChannel;
    }

    public String getpBranch() {
        return pBranch;
    }

    public void setpBranch(String pBranch) {
        this.pBranch = pBranch;
    }

    public String getpDeliveryFactory() {
        return pDeliveryFactory;
    }

    public void setpDeliveryFactory(String pDeliveryFactory) {
        this.pDeliveryFactory = pDeliveryFactory;
    }

    public String getpOutputTax() {
        return pOutputTax;
    }

    public void setpOutputTax(String pOutputTax) {
        this.pOutputTax = pOutputTax;
    }

    public String getpMaterielSummaryGroup() {
        return pMaterielSummaryGroup;
    }

    public void setpMaterielSummaryGroup(String pMaterielSummaryGroup) {
        this.pMaterielSummaryGroup = pMaterielSummaryGroup;
    }

    public String getpMaterielPriceGroup() {
        return pMaterielPriceGroup;
    }

    public void setpMaterielPriceGroup(String pMaterielPriceGroup) {
        this.pMaterielPriceGroup = pMaterielPriceGroup;
    }

    public String getpSubjectSetGroup() {
        return pSubjectSetGroup;
    }

    public void setpSubjectSetGroup(String pSubjectSetGroup) {
        this.pSubjectSetGroup = pSubjectSetGroup;
    }

    public String getpOrdinaryProjGroup() {
        return pOrdinaryProjGroup;
    }

    public void setpOrdinaryProjGroup(String pOrdinaryProjGroup) {
        this.pOrdinaryProjGroup = pOrdinaryProjGroup;
    }

    public String getpProjGroup() {
        return pProjGroup;
    }

    public void setpProjGroup(String pProjGroup) {
        this.pProjGroup = pProjGroup;
    }

    public String getpProductLevel() {
        return pProductLevel;
    }

    public void setpProductLevel(String pProductLevel) {
        this.pProductLevel = pProductLevel;
    }

    public String getpLoadingGroup() {
        return pLoadingGroup;
    }

    public void setpLoadingGroup(String pLoadingGroup) {
        this.pLoadingGroup = pLoadingGroup;
    }

    public String getpTransportGroup() {
        return pTransportGroup;
    }

    public void setpTransportGroup(String pTransportGroup) {
        this.pTransportGroup = pTransportGroup;
    }

    public Integer getpMaterielDataType() {
        return pMaterielDataType;
    }

    public void setpMaterielDataType(Integer pMaterielDataType) {
        this.pMaterielDataType = pMaterielDataType;
    }

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }
}
