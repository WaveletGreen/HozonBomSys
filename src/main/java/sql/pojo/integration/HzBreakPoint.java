package sql.pojo.integration;

import java.util.Date;

public class HzBreakPoint {
    private Long pid;

    private String pPackno;

    private String pItem;

    private String pTcecn;

    private String pEcn;

    private String pDate;

    private String pOecn;

    private String pOdate;

    private String pVin;

    private String pUser;

    private String pCdate;

    private String pCtime;

    private String pReserve1;

    private String pReserve2;

    private String pReserve3;

    private String pReserve4;

    private String pReserve5;

    private Date feedbackTime;

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getpPackno() {
        return pPackno;
    }

    public void setpPackno(String pPackno) {
        this.pPackno = pPackno == null ? null : pPackno.trim();
    }

    public String getpItem() {
        return pItem;
    }

    public void setpItem(String pItem) {
        this.pItem = pItem == null ? null : pItem.trim();
    }

    public String getpTcecn() {
        return pTcecn;
    }

    public void setpTcecn(String pTcecn) {
        this.pTcecn = pTcecn == null ? null : pTcecn.trim();
    }

    public String getpEcn() {
        return pEcn;
    }

    public void setpEcn(String pEcn) {
        this.pEcn = pEcn == null ? null : pEcn.trim();
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate == null ? null : pDate.trim();
    }

    public String getpOecn() {
        return pOecn;
    }

    public void setpOecn(String pOecn) {
        this.pOecn = pOecn == null ? null : pOecn.trim();
    }

    public String getpOdate() {
        return pOdate;
    }

    public void setpOdate(String pOdate) {
        this.pOdate = pOdate == null ? null : pOdate.trim();
    }

    public String getpVin() {
        return pVin;
    }

    public void setpVin(String pVin) {
        this.pVin = pVin == null ? null : pVin.trim();
    }

    public String getpUser() {
        return pUser;
    }

    public void setpUser(String pUser) {
        this.pUser = pUser == null ? null : pUser.trim();
    }

    public String getpCdate() {
        return pCdate;
    }

    public void setpCdate(String pCdate) {
        this.pCdate = pCdate == null ? null : pCdate.trim();
    }

    public String getpCtime() {
        return pCtime;
    }

    public void setpCtime(String pCtime) {
        this.pCtime = pCtime == null ? null : pCtime.trim();
    }

    public String getpReserve1() {
        return pReserve1;
    }

    public void setpReserve1(String pReserve1) {
        this.pReserve1 = pReserve1 == null ? null : pReserve1.trim();
    }

    public String getpReserve2() {
        return pReserve2;
    }

    public void setpReserve2(String pReserve2) {
        this.pReserve2 = pReserve2 == null ? null : pReserve2.trim();
    }

    public String getpReserve3() {
        return pReserve3;
    }

    public void setpReserve3(String pReserve3) {
        this.pReserve3 = pReserve3 == null ? null : pReserve3.trim();
    }

    public String getpReserve4() {
        return pReserve4;
    }

    public void setpReserve4(String pReserve4) {
        this.pReserve4 = pReserve4 == null ? null : pReserve4.trim();
    }

    public String getpReserve5() {
        return pReserve5;
    }

    public void setpReserve5(String pReserve5) {
        this.pReserve5 = pReserve5 == null ? null : pReserve5.trim();
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
}