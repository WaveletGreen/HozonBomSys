/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet;

import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Deprecated
public class HzFcfgBomLvl1ListOperation {
    private String puid;

    private Object pOprationTypeName;

    private Object pOprator;

    private Date pOprationDate;

    private Short pOprationStatus;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public Object getpOprationTypeName() {
        return pOprationTypeName;
    }

    public void setpOprationTypeName(Object pOprationTypeName) {
        this.pOprationTypeName = pOprationTypeName;
    }

    public Object getpOprator() {
        return pOprator;
    }

    public void setpOprator(Object pOprator) {
        this.pOprator = pOprator;
    }

    public Date getpOprationDate() {
        return pOprationDate;
    }

    public void setpOprationDate(Date pOprationDate) {
        this.pOprationDate = pOprationDate;
    }

    public Short getpOprationStatus() {
        return pOprationStatus;
    }

    public void setpOprationStatus(Short pOprationStatus) {
        this.pOprationStatus = pOprationStatus;
    }
}