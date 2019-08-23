package com.connor.hozon.resources.domain.dto.request;


import cn.net.connor.hozon.common.entity.BaseDTO;

/**
 * @Author: zhangl
 * @Date: 2019/8/23
 * @Description: 修改一条法规件
 */
public class UpdateHzLegislativeReqDTO extends BaseDTO {

    /**
     * 主键
     */
    private String puid;
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

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

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
