package com.connor.hozon.resources.domain.query;

public class HzLegislativeItemQuery extends DefaultPageQuery {

    /**
     * 公告号
     */
    private String noticeNo;
    /**
     * 法规件型号
     */
    private String legislativeNo;
    /**
     * 适用车型
     */
    private String applicableModels;

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
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
}
