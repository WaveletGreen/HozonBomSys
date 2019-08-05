/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.integration;

import java.io.Serializable;
import java.util.Date;

/**
 * 断点信息
 */
public class HzBreakPoint implements Serializable {
    private static final long serialVersionUID = 7391744885544086127L;
    /**
     * 主键
     */
    private Long pid;
    /**
     * 数据包号
     */
    private String pPackno;
    /**
     * 行号
     */
    private String pItem;
    /**
     *TC-ECN
     */
    private String pTcecn;
    /**
     *SAP-ECN
     */
    private String pEcn;
    /**
     *断点日期
     */
    private String pDate;
    /**
     *断点前ECN
     */
    private String pOecn;
    /**
     *前ECN结束日期
     */
    private String pOdate;
    /**
     *断点起始vin
     */
    private String pVin;
    /**
     *断点执行工号
     */
    private String pUser;
    /**
     *断点处理日期
     */
    private String pCdate;
    /**
     *断点处理时间
     */
    private String pCtime;
    /**
     *预留字段1
     */
    private String pReserve1;
    /**
     *预留字段2
     */
    private String pReserve2;
    /**
     *预留字段3
     */
    private String pReserve3;
    /**
     *预留字段4
     */
    private String pReserve4;
    /**
     *预留字段5
     */
    private String pReserve5;
    /**
     *断点回传到BOM系统时间
     */
    private Date feedbackTime;

    public static String reflectToDBField(String property) {
        switch (property) {
            /**
             * 主键
             */
            case "pid"://代码字段
                return "PID";//对应数据库字段信息
            /**
             * 数据包号
             */
            case "pPackno":
                return "P_Packno";

            /**
             * 生效时间
             */
//            case "cfgEffectedDate":
//                return "CFG_EFFECTED_DATE";
//            /**
//             * 废止时间
//             */
//            case "cfgAbolishDate":
//                return "CFG_ABOLISH_DATE";
            default:
                return null;
        }
    }

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