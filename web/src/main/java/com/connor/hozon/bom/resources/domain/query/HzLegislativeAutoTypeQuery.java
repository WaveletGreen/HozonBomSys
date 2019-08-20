package com.connor.hozon.bom.resources.domain.query;

public class HzLegislativeAutoTypeQuery extends DefaultPageQuery {
    /**
     * 公告号
     */
    private String noticeNo;
    /**
     * 车型
     */
    private String autoType;
    /**
     * VIN码前8位
     */
    private String vinNo;

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getAutoType() {
        return autoType;
    }

    public void setAutoType(String autoType) {
        this.autoType = autoType;
    }

    public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo;
    }
}
