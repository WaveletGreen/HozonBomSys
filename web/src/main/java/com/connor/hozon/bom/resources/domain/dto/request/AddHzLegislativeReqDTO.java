package com.connor.hozon.bom.resources.domain.dto.request;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;

import java.util.Map;

/**
 * @Author: zhangl
 * @Date: 2019/8/22
 * @Description: 新增一条法规件
 */
public class AddHzLegislativeReqDTO extends BaseDTO {
    /**
     * 法规件名称
     */
    private String legislativeName;
    /**
     * 法规件型号
     */
    private String legislativeNo;
    /**
     * 适用车型
     */
    private String applicableModels;
    /**
     * 公告号
     */
    private String  noticeNo;
    /**
     * 技术特性描述
     */
    private String technologyDesc;
    /**
     * 申请部门
     */
    private String applyDepa;
    /**
     * 是否需要强检报告
     */
    private String isHaveTest;
    /**
     * 备注
     */
    private String  remarks;

    public String getLegislativeName() {
        return legislativeName;
    }

    public void setLegislativeName(String legislativeName) {
        this.legislativeName = legislativeName;
    }

    public String getLegislativeNo() {
        return legislativeNo;
    }

    public void setLegislativeNo(String legislativeNo) {
        this.legislativeNo = legislativeNo;
    }

    public String getApplicableModels() {
        return applicableModels;
    }

    public void setApplicableModels(String applicableModels) {
        this.applicableModels = applicableModels;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getTechnologyDesc() {
        return technologyDesc;
    }

    public void setTechnologyDesc(String technologyDesc) {
        this.technologyDesc = technologyDesc;
    }

    public String getApplyDepa() {
        return applyDepa;
    }

    public void setApplyDepa(String applyDepa) {
        this.applyDepa = applyDepa;
    }

    public String getIsHaveTest() {
        return isHaveTest;
    }

    public void setIsHaveTest(String isHaveTest) {
        this.isHaveTest = isHaveTest;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
