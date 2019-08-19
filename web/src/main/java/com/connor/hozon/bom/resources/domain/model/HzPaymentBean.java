/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.model;

import java.util.Date;

//预算管理实体类
public class HzPaymentBean {

    //项目类型  1为项目预算  2为固定资产预算
    private Integer projectType;

    //基地
    private String base;

    //投资类型
    private String investmentType;
    //项目名称
    private String projectName;
    //项目阶段
    private String projectStage;
    //一级科目
    private String subject1;
    //二级科目
    private String subject2;
    //三级科目
    private String subject3;
    //需求部门
    private String demandDepartment;
    //项目费用描述
    private String projectCostDes;
    //WBS号
    private String wbsNumber;
    //预算金额（含税）

    //1月
    private Double janSum;
    //2月
    private Double febSum;
    //3月
    private Double marSum;
    //1季度
    private Double quarter1;
    //4月
    private Double aprSum;
    //5月
    private Double maySum;
    //6月
    private Double junSum;
    //2季度
    private Double quarter2;
    //7月
    private Double julSum;
    //8月
    private Double augSum;
    //9月
    private Double sepSum;
    //3季度
    private Double quarter3;
    //10月
    private Double octSum;
    //11月
    private Double novSum;
    //12月
    private Double decSum;
    //4季度
    private Double quarter4;

    //历史年度累计
    private Double historyYearSum;
    //本年度
    private Double thisYearSum;
    //N+1年度
    private Double nOneYearSum;
    //N+2年度
    private Double nTwoYearSum;
    //N+3年度
    private Double nThreeYearSum;
    //计划时间(询议标（PR))
    private Date planTime;
    //签订时间(询议标（PR))
    private Date signTime;
    //编号(询议标（PR))
    private String serialNumber;
    //预算总额
    private Double budgetSum;
    //供应商
    private String supplier;
    //合同编号(合同信息(PO))
    private String contractNumber;
    //签订时间（合同信息(PO)）
    private Date contractSignTime;
    //合同金额
    private Double contractSum;
    //差异数
    private Double differenceNumber;
    //税点
    private Double taxPoint;
    //合同发起人
    private String contractInitiator;
    //付款条款
    private String payClause;
    //已付
    private Double hasPay;
    //未付
    private Double notPay;
    //应付未付
    private Double hasNotPay;

    //第一次付款
    //付款比例
    private String payRatio1;
    //验收完工判定（是/否）   0为是，1为否
    private Short checkFlag1;
    //计划付款金额
    private Double planPaySum1;
    //计划付款时间
    private Date planPayTime1;
    //发票号（必填）
    private String invoiceNumber1;
    //发票提交时间（必填）
    private Date invoiceSubmitTime1;
    //OA付款申请流水号
    private String oaPayNumber1;
    //申请支付金额（发票金额/必填项）
    private Double applyPaySum1;
    //财务预计支付时间
    private Date finacePrepaymentTime1;
    //实际支付时间
    private Date realityPayTime1;
    //实际支付金额
    private Double realityPaySum1;
    //支付金额差异
    private Double payDifferenceSum1;

    //第2次付款
    //付款比例
    private String payRatio2;
    //验收完工判定（是/否）   0为是，1为否
    private Short checkFlag2;
    //计划付款金额
    private Double planPaySum2;
    //计划付款时间
    private Date planPayTime2;
    //发票号（必填）
    private String invoiceNumber2;
    //发票提交时间（必填）
    private Date invoiceSubmitTime2;
    //OA付款申请流水号
    private String oaPayNumber2;
    //申请支付金额（发票金额/必填项）
    private Double applyPaySum2;
    //财务预计支付时间
    private Date finacePrepaymentTime2;
    //实际支付时间
    private Date realityPayTime2;
    //实际支付金额
    private Double realityPaySum2;
    //支付金额差异
    private Double payDifferenceSum2;

    //第3次付款
    //付款比例
    private String payRatio3;
    //验收完工判定（是/否）   0为是，1为否
    private Short checkFlag3;
    //计划付款金额
    private Double planPaySum3;
    //计划付款时间
    private Date planPayTime3;
    //发票号（必填）
    private String invoiceNumber3;
    //发票提交时间（必填）
    private Date invoiceSubmitTime3;
    //OA付款申请流水号
    private String oaPayNumber3;
    //申请支付金额（发票金额/必填项）
    private Double applyPaySum3;
    //财务预计支付时间
    private Date finacePrepaymentTime3;
    //实际支付时间
    private Date realityPayTime3;
    //实际支付金额
    private Double realityPaySum3;
    //支付金额差异
    private Double payDifferenceSum3;

    //第4次付款
    //付款比例
    private String payRatio4;
    //验收完工判定（是/否）   0为是，1为否
    private Short checkFlag4;
    //计划付款金额
    private Double planPaySum4;
    //计划付款时间
    private Date planPayTime4;
    //发票号（必填）
    private String invoiceNumber4;
    //发票提交时间（必填）
    private Date invoiceSubmitTime4;
    //OA付款申请流水号
    private String oaPayNumber4;
    //申请支付金额（发票金额/必填项）
    private Double applyPaySum4;
    //财务预计支付时间
    private Date finacePrepaymentTime4;
    //实际支付时间
    private Date realityPayTime4;
    //实际支付金额
    private Double realityPaySum4;
    //支付金额差异
    private Double payDifferenceSum4;

    //第5次付款
    //付款比例
    private String payRatio5;
    //验收完工判定（是/否）   0为是，1为否
    private Short checkFlag5;
    //计划付款金额
    private Double planPaySum5;
    //计划付款时间
    private Date planPayTime5;
    //发票号（必填）
    private String invoiceNumber5;
    //发票提交时间（必填）
    private Date invoiceSubmitTime5;
    //OA付款申请流水号
    private String oaPayNumber5;
    //申请支付金额（发票金额/必填项）
    private Double applyPaySum5;
    //财务预计支付时间
    private Date finacePrepaymentTime5;
    //实际支付时间
    private Date realityPayTime5;
    //实际支付金额
    private Double realityPaySum5;
    //支付金额差异
    private Double payDifferenceSum5;

    //第6次付款
    //付款比例
    private String payRatio6;
    //验收完工判定（是/否）   0为是，1为否
    private Short checkFlag6;
    //计划付款金额
    private Double planPaySum6;
    //计划付款时间
    private Date planPayTime6;
    //发票号（必填）
    private String invoiceNumber6;
    //发票提交时间（必填）
    private Date invoiceSubmitTime6;
    //OA付款申请流水号
    private String oaPayNumber6;
    //申请支付金额（发票金额/必填项）
    private Double applyPaySum6;
    //财务预计支付时间
    private Date finacePrepaymentTime6;
    //实际支付时间
    private Date realityPayTime6;
    //实际支付金额
    private Double realityPaySum6;
    //支付金额差异
    private Double payDifferenceSum6;

    public Integer getProjectType() {
        return projectType;
    }










    /****************get和set方法************************/
    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getInvestmentType() {
        return investmentType;
    }

    public void setInvestmentType(String investmentType) {
        this.investmentType = investmentType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStage() {
        return projectStage;
    }

    public void setProjectStage(String projectStage) {
        this.projectStage = projectStage;
    }

    public String getSubject1() {
        return subject1;
    }

    public void setSubject1(String subject1) {
        this.subject1 = subject1;
    }

    public String getSubject2() {
        return subject2;
    }

    public void setSubject2(String subject2) {
        this.subject2 = subject2;
    }

    public String getSubject3() {
        return subject3;
    }

    public void setSubject3(String subject3) {
        this.subject3 = subject3;
    }

    public String getDemandDepartment() {
        return demandDepartment;
    }

    public void setDemandDepartment(String demandDepartment) {
        this.demandDepartment = demandDepartment;
    }

    public String getProjectCostDes() {
        return projectCostDes;
    }

    public void setProjectCostDes(String projectCostDes) {
        this.projectCostDes = projectCostDes;
    }

    public String getWbsNumber() {
        return wbsNumber;
    }

    public void setWbsNumber(String wbsNumber) {
        this.wbsNumber = wbsNumber;
    }

    public Double getJanSum() {
        return janSum;
    }

    public void setJanSum(Double janSum) {
        this.janSum = janSum;
    }

    public Double getFebSum() {
        return febSum;
    }

    public void setFebSum(Double febSum) {
        this.febSum = febSum;
    }

    public Double getMarSum() {
        return marSum;
    }

    public void setMarSum(Double marSum) {
        this.marSum = marSum;
    }

    public Double getQuarter1() {
        return quarter1;
    }

    public void setQuarter1(Double quarter1) {
        this.quarter1 = quarter1;
    }

    public Double getAprSum() {
        return aprSum;
    }

    public void setAprSum(Double aprSum) {
        this.aprSum = aprSum;
    }

    public Double getMaySum() {
        return maySum;
    }

    public void setMaySum(Double maySum) {
        this.maySum = maySum;
    }

    public Double getJunSum() {
        return junSum;
    }

    public void setJunSum(Double junSum) {
        this.junSum = junSum;
    }

    public Double getQuarter2() {
        return quarter2;
    }

    public void setQuarter2(Double quarter2) {
        this.quarter2 = quarter2;
    }

    public Double getJulSum() {
        return julSum;
    }

    public void setJulSum(Double julSum) {
        this.julSum = julSum;
    }

    public Double getAugSum() {
        return augSum;
    }

    public void setAugSum(Double augSum) {
        this.augSum = augSum;
    }

    public Double getSepSum() {
        return sepSum;
    }

    public void setSepSum(Double sepSum) {
        this.sepSum = sepSum;
    }

    public Double getQuarter3() {
        return quarter3;
    }

    public void setQuarter3(Double quarter3) {
        this.quarter3 = quarter3;
    }

    public Double getOctSum() {
        return octSum;
    }

    public void setOctSum(Double octSum) {
        this.octSum = octSum;
    }

    public Double getNovSum() {
        return novSum;
    }

    public void setNovSum(Double novSum) {
        this.novSum = novSum;
    }

    public Double getDecSum() {
        return decSum;
    }

    public void setDecSum(Double decSum) {
        this.decSum = decSum;
    }

    public Double getQuarter4() {
        return quarter4;
    }

    public void setQuarter4(Double quarter4) {
        this.quarter4 = quarter4;
    }

    public Double getHistoryYearSum() {
        return historyYearSum;
    }

    public void setHistoryYearSum(Double historyYearSum) {
        this.historyYearSum = historyYearSum;
    }

    public Double getThisYearSum() {
        return thisYearSum;
    }

    public void setThisYearSum(Double thisYearSum) {
        this.thisYearSum = thisYearSum;
    }

    public Double getnOneYearSum() {
        return nOneYearSum;
    }

    public void setnOneYearSum(Double nOneYearSum) {
        this.nOneYearSum = nOneYearSum;
    }

    public Double getnTwoYearSum() {
        return nTwoYearSum;
    }

    public void setnTwoYearSum(Double nTwoYearSum) {
        this.nTwoYearSum = nTwoYearSum;
    }

    public Double getnThreeYearSum() {
        return nThreeYearSum;
    }

    public void setnThreeYearSum(Double nThreeYearSum) {
        this.nThreeYearSum = nThreeYearSum;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Double getBudgetSum() {
        return budgetSum;
    }

    public void setBudgetSum(Double budgetSum) {
        this.budgetSum = budgetSum;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public Date getContractSignTime() {
        return contractSignTime;
    }

    public void setContractSignTime(Date contractSignTime) {
        this.contractSignTime = contractSignTime;
    }

    public Double getContractSum() {
        return contractSum;
    }

    public void setContractSum(Double contractSum) {
        this.contractSum = contractSum;
    }

    public Double getDifferenceNumber() {
        return differenceNumber;
    }

    public void setDifferenceNumber(Double differenceNumber) {
        this.differenceNumber = differenceNumber;
    }

    public Double getTaxPoint() {
        return taxPoint;
    }

    public void setTaxPoint(Double taxPoint) {
        this.taxPoint = taxPoint;
    }

    public String getContractInitiator() {
        return contractInitiator;
    }

    public void setContractInitiator(String contractInitiator) {
        this.contractInitiator = contractInitiator;
    }

    public String getPayClause() {
        return payClause;
    }

    public void setPayClause(String payClause) {
        this.payClause = payClause;
    }

    public Double getHasPay() {
        return hasPay;
    }

    public void setHasPay(Double hasPay) {
        this.hasPay = hasPay;
    }

    public Double getNotPay() {
        return notPay;
    }

    public void setNotPay(Double notPay) {
        this.notPay = notPay;
    }

    public Double getHasNotPay() {
        return hasNotPay;
    }

    public void setHasNotPay(Double hasNotPay) {
        this.hasNotPay = hasNotPay;
    }

    public String getPayRatio1() {
        return payRatio1;
    }

    public void setPayRatio1(String payRatio1) {
        this.payRatio1 = payRatio1;
    }

    public Short getCheckFlag1() {
        return checkFlag1;
    }

    public void setCheckFlag1(Short checkFlag1) {
        this.checkFlag1 = checkFlag1;
    }

    public Double getPlanPaySum1() {
        return planPaySum1;
    }

    public void setPlanPaySum1(Double planPaySum1) {
        this.planPaySum1 = planPaySum1;
    }

    public Date getPlanPayTime1() {
        return planPayTime1;
    }

    public void setPlanPayTime1(Date planPayTime1) {
        this.planPayTime1 = planPayTime1;
    }

    public String getInvoiceNumber1() {
        return invoiceNumber1;
    }

    public void setInvoiceNumber1(String invoiceNumber1) {
        this.invoiceNumber1 = invoiceNumber1;
    }

    public Date getInvoiceSubmitTime1() {
        return invoiceSubmitTime1;
    }

    public void setInvoiceSubmitTime1(Date invoiceSubmitTime1) {
        this.invoiceSubmitTime1 = invoiceSubmitTime1;
    }

    public String getOaPayNumber1() {
        return oaPayNumber1;
    }

    public void setOaPayNumber1(String oaPayNumber1) {
        this.oaPayNumber1 = oaPayNumber1;
    }

    public Double getApplyPaySum1() {
        return applyPaySum1;
    }

    public void setApplyPaySum1(Double applyPaySum1) {
        this.applyPaySum1 = applyPaySum1;
    }

    public Date getFinacePrepaymentTime1() {
        return finacePrepaymentTime1;
    }

    public void setFinacePrepaymentTime1(Date finacePrepaymentTime1) {
        this.finacePrepaymentTime1 = finacePrepaymentTime1;
    }

    public Date getRealityPayTime1() {
        return realityPayTime1;
    }

    public void setRealityPayTime1(Date realityPayTime1) {
        this.realityPayTime1 = realityPayTime1;
    }

    public Double getRealityPaySum1() {
        return realityPaySum1;
    }

    public void setRealityPaySum1(Double realityPaySum1) {
        this.realityPaySum1 = realityPaySum1;
    }

    public Double getPayDifferenceSum1() {
        return payDifferenceSum1;
    }

    public void setPayDifferenceSum1(Double payDifferenceSum1) {
        this.payDifferenceSum1 = payDifferenceSum1;
    }

    public String getPayRatio2() {
        return payRatio2;
    }

    public void setPayRatio2(String payRatio2) {
        this.payRatio2 = payRatio2;
    }

    public Short getCheckFlag2() {
        return checkFlag2;
    }

    public void setCheckFlag2(Short checkFlag2) {
        this.checkFlag2 = checkFlag2;
    }

    public Double getPlanPaySum2() {
        return planPaySum2;
    }

    public void setPlanPaySum2(Double planPaySum2) {
        this.planPaySum2 = planPaySum2;
    }

    public Date getPlanPayTime2() {
        return planPayTime2;
    }

    public void setPlanPayTime2(Date planPayTime2) {
        this.planPayTime2 = planPayTime2;
    }

    public String getInvoiceNumber2() {
        return invoiceNumber2;
    }

    public void setInvoiceNumber2(String invoiceNumber2) {
        this.invoiceNumber2 = invoiceNumber2;
    }

    public Date getInvoiceSubmitTime2() {
        return invoiceSubmitTime2;
    }

    public void setInvoiceSubmitTime2(Date invoiceSubmitTime2) {
        this.invoiceSubmitTime2 = invoiceSubmitTime2;
    }

    public String getOaPayNumber2() {
        return oaPayNumber2;
    }

    public void setOaPayNumber2(String oaPayNumber2) {
        this.oaPayNumber2 = oaPayNumber2;
    }

    public Double getApplyPaySum2() {
        return applyPaySum2;
    }

    public void setApplyPaySum2(Double applyPaySum2) {
        this.applyPaySum2 = applyPaySum2;
    }

    public Date getFinacePrepaymentTime2() {
        return finacePrepaymentTime2;
    }

    public void setFinacePrepaymentTime2(Date finacePrepaymentTime2) {
        this.finacePrepaymentTime2 = finacePrepaymentTime2;
    }

    public Date getRealityPayTime2() {
        return realityPayTime2;
    }

    public void setRealityPayTime2(Date realityPayTime2) {
        this.realityPayTime2 = realityPayTime2;
    }

    public Double getRealityPaySum2() {
        return realityPaySum2;
    }

    public void setRealityPaySum2(Double realityPaySum2) {
        this.realityPaySum2 = realityPaySum2;
    }

    public Double getPayDifferenceSum2() {
        return payDifferenceSum2;
    }

    public void setPayDifferenceSum2(Double payDifferenceSum2) {
        this.payDifferenceSum2 = payDifferenceSum2;
    }

    public String getPayRatio3() {
        return payRatio3;
    }

    public void setPayRatio3(String payRatio3) {
        this.payRatio3 = payRatio3;
    }

    public Short getCheckFlag3() {
        return checkFlag3;
    }

    public void setCheckFlag3(Short checkFlag3) {
        this.checkFlag3 = checkFlag3;
    }

    public Double getPlanPaySum3() {
        return planPaySum3;
    }

    public void setPlanPaySum3(Double planPaySum3) {
        this.planPaySum3 = planPaySum3;
    }

    public Date getPlanPayTime3() {
        return planPayTime3;
    }

    public void setPlanPayTime3(Date planPayTime3) {
        this.planPayTime3 = planPayTime3;
    }

    public String getInvoiceNumber3() {
        return invoiceNumber3;
    }

    public void setInvoiceNumber3(String invoiceNumber3) {
        this.invoiceNumber3 = invoiceNumber3;
    }

    public Date getInvoiceSubmitTime3() {
        return invoiceSubmitTime3;
    }

    public void setInvoiceSubmitTime3(Date invoiceSubmitTime3) {
        this.invoiceSubmitTime3 = invoiceSubmitTime3;
    }

    public String getOaPayNumber3() {
        return oaPayNumber3;
    }

    public void setOaPayNumber3(String oaPayNumber3) {
        this.oaPayNumber3 = oaPayNumber3;
    }

    public Double getApplyPaySum3() {
        return applyPaySum3;
    }

    public void setApplyPaySum3(Double applyPaySum3) {
        this.applyPaySum3 = applyPaySum3;
    }

    public Date getFinacePrepaymentTime3() {
        return finacePrepaymentTime3;
    }

    public void setFinacePrepaymentTime3(Date finacePrepaymentTime3) {
        this.finacePrepaymentTime3 = finacePrepaymentTime3;
    }

    public Date getRealityPayTime3() {
        return realityPayTime3;
    }

    public void setRealityPayTime3(Date realityPayTime3) {
        this.realityPayTime3 = realityPayTime3;
    }

    public Double getRealityPaySum3() {
        return realityPaySum3;
    }

    public void setRealityPaySum3(Double realityPaySum3) {
        this.realityPaySum3 = realityPaySum3;
    }

    public Double getPayDifferenceSum3() {
        return payDifferenceSum3;
    }

    public void setPayDifferenceSum3(Double payDifferenceSum3) {
        this.payDifferenceSum3 = payDifferenceSum3;
    }

    public String getPayRatio4() {
        return payRatio4;
    }

    public void setPayRatio4(String payRatio4) {
        this.payRatio4 = payRatio4;
    }

    public Short getCheckFlag4() {
        return checkFlag4;
    }

    public void setCheckFlag4(Short checkFlag4) {
        this.checkFlag4 = checkFlag4;
    }

    public Double getPlanPaySum4() {
        return planPaySum4;
    }

    public void setPlanPaySum4(Double planPaySum4) {
        this.planPaySum4 = planPaySum4;
    }

    public Date getPlanPayTime4() {
        return planPayTime4;
    }

    public void setPlanPayTime4(Date planPayTime4) {
        this.planPayTime4 = planPayTime4;
    }

    public String getInvoiceNumber4() {
        return invoiceNumber4;
    }

    public void setInvoiceNumber4(String invoiceNumber4) {
        this.invoiceNumber4 = invoiceNumber4;
    }

    public Date getInvoiceSubmitTime4() {
        return invoiceSubmitTime4;
    }

    public void setInvoiceSubmitTime4(Date invoiceSubmitTime4) {
        this.invoiceSubmitTime4 = invoiceSubmitTime4;
    }

    public String getOaPayNumber4() {
        return oaPayNumber4;
    }

    public void setOaPayNumber4(String oaPayNumber4) {
        this.oaPayNumber4 = oaPayNumber4;
    }

    public Double getApplyPaySum4() {
        return applyPaySum4;
    }

    public void setApplyPaySum4(Double applyPaySum4) {
        this.applyPaySum4 = applyPaySum4;
    }

    public Date getFinacePrepaymentTime4() {
        return finacePrepaymentTime4;
    }

    public void setFinacePrepaymentTime4(Date finacePrepaymentTime4) {
        this.finacePrepaymentTime4 = finacePrepaymentTime4;
    }

    public Date getRealityPayTime4() {
        return realityPayTime4;
    }

    public void setRealityPayTime4(Date realityPayTime4) {
        this.realityPayTime4 = realityPayTime4;
    }

    public Double getRealityPaySum4() {
        return realityPaySum4;
    }

    public void setRealityPaySum4(Double realityPaySum4) {
        this.realityPaySum4 = realityPaySum4;
    }

    public Double getPayDifferenceSum4() {
        return payDifferenceSum4;
    }

    public void setPayDifferenceSum4(Double payDifferenceSum4) {
        this.payDifferenceSum4 = payDifferenceSum4;
    }

    public String getPayRatio5() {
        return payRatio5;
    }

    public void setPayRatio5(String payRatio5) {
        this.payRatio5 = payRatio5;
    }

    public Short getCheckFlag5() {
        return checkFlag5;
    }

    public void setCheckFlag5(Short checkFlag5) {
        this.checkFlag5 = checkFlag5;
    }

    public Double getPlanPaySum5() {
        return planPaySum5;
    }

    public void setPlanPaySum5(Double planPaySum5) {
        this.planPaySum5 = planPaySum5;
    }

    public Date getPlanPayTime5() {
        return planPayTime5;
    }

    public void setPlanPayTime5(Date planPayTime5) {
        this.planPayTime5 = planPayTime5;
    }

    public String getInvoiceNumber5() {
        return invoiceNumber5;
    }

    public void setInvoiceNumber5(String invoiceNumber5) {
        this.invoiceNumber5 = invoiceNumber5;
    }

    public Date getInvoiceSubmitTime5() {
        return invoiceSubmitTime5;
    }

    public void setInvoiceSubmitTime5(Date invoiceSubmitTime5) {
        this.invoiceSubmitTime5 = invoiceSubmitTime5;
    }

    public String getOaPayNumber5() {
        return oaPayNumber5;
    }

    public void setOaPayNumber5(String oaPayNumber5) {
        this.oaPayNumber5 = oaPayNumber5;
    }

    public Double getApplyPaySum5() {
        return applyPaySum5;
    }

    public void setApplyPaySum5(Double applyPaySum5) {
        this.applyPaySum5 = applyPaySum5;
    }

    public Date getFinacePrepaymentTime5() {
        return finacePrepaymentTime5;
    }

    public void setFinacePrepaymentTime5(Date finacePrepaymentTime5) {
        this.finacePrepaymentTime5 = finacePrepaymentTime5;
    }

    public Date getRealityPayTime5() {
        return realityPayTime5;
    }

    public void setRealityPayTime5(Date realityPayTime5) {
        this.realityPayTime5 = realityPayTime5;
    }

    public Double getRealityPaySum5() {
        return realityPaySum5;
    }

    public void setRealityPaySum5(Double realityPaySum5) {
        this.realityPaySum5 = realityPaySum5;
    }

    public Double getPayDifferenceSum5() {
        return payDifferenceSum5;
    }

    public void setPayDifferenceSum5(Double payDifferenceSum5) {
        this.payDifferenceSum5 = payDifferenceSum5;
    }

    public String getPayRatio6() {
        return payRatio6;
    }

    public void setPayRatio6(String payRatio6) {
        this.payRatio6 = payRatio6;
    }

    public Short getCheckFlag6() {
        return checkFlag6;
    }

    public void setCheckFlag6(Short checkFlag6) {
        this.checkFlag6 = checkFlag6;
    }

    public Double getPlanPaySum6() {
        return planPaySum6;
    }

    public void setPlanPaySum6(Double planPaySum6) {
        this.planPaySum6 = planPaySum6;
    }

    public Date getPlanPayTime6() {
        return planPayTime6;
    }

    public void setPlanPayTime6(Date planPayTime6) {
        this.planPayTime6 = planPayTime6;
    }

    public String getInvoiceNumber6() {
        return invoiceNumber6;
    }

    public void setInvoiceNumber6(String invoiceNumber6) {
        this.invoiceNumber6 = invoiceNumber6;
    }

    public Date getInvoiceSubmitTime6() {
        return invoiceSubmitTime6;
    }

    public void setInvoiceSubmitTime6(Date invoiceSubmitTime6) {
        this.invoiceSubmitTime6 = invoiceSubmitTime6;
    }

    public String getOaPayNumber6() {
        return oaPayNumber6;
    }

    public void setOaPayNumber6(String oaPayNumber6) {
        this.oaPayNumber6 = oaPayNumber6;
    }

    public Double getApplyPaySum6() {
        return applyPaySum6;
    }

    public void setApplyPaySum6(Double applyPaySum6) {
        this.applyPaySum6 = applyPaySum6;
    }

    public Date getFinacePrepaymentTime6() {
        return finacePrepaymentTime6;
    }

    public void setFinacePrepaymentTime6(Date finacePrepaymentTime6) {
        this.finacePrepaymentTime6 = finacePrepaymentTime6;
    }

    public Date getRealityPayTime6() {
        return realityPayTime6;
    }

    public void setRealityPayTime6(Date realityPayTime6) {
        this.realityPayTime6 = realityPayTime6;
    }

    public Double getRealityPaySum6() {
        return realityPaySum6;
    }

    public void setRealityPaySum6(Double realityPaySum6) {
        this.realityPaySum6 = realityPaySum6;
    }

    public Double getPayDifferenceSum6() {
        return payDifferenceSum6;
    }

    public void setPayDifferenceSum6(Double payDifferenceSum6) {
        this.payDifferenceSum6 = payDifferenceSum6;
    }
}
