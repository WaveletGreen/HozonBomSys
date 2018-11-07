/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.derivative;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzCfg0ToModelRecord {
    private String puid;

    private String pCfg0IdRecord;

    private String pCfg0OptionValue;

    private String pOfCfg0MainRecord;

    private String pCfg0ModelRecord;

    private Integer pParseLogicValue;

    private Integer isSent;
    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpCfg0IdRecord() {
        return pCfg0IdRecord;
    }

    public void setpCfg0IdRecord(String pCfg0IdRecord) {
        this.pCfg0IdRecord = pCfg0IdRecord == null ? null : pCfg0IdRecord.trim();
    }

    public String getpCfg0OptionValue() {
        return pCfg0OptionValue;
    }

    public void setpCfg0OptionValue(String pCfg0OptionValue) {
        this.pCfg0OptionValue = pCfg0OptionValue == null ? null : pCfg0OptionValue.trim();
    }

    public String getpOfCfg0MainRecord() {
        return pOfCfg0MainRecord;
    }

    public void setpOfCfg0MainRecord(String pOfCfg0MainRecord) {
        this.pOfCfg0MainRecord = pOfCfg0MainRecord == null ? null : pOfCfg0MainRecord.trim();
    }

    public String getpCfg0ModelRecord() {
        return pCfg0ModelRecord;
    }

    public void setpCfg0ModelRecord(String pCfg0ModelRecord) {
        this.pCfg0ModelRecord = pCfg0ModelRecord == null ? null : pCfg0ModelRecord.trim();
    }

    public Integer getpParseLogicValue() {
        return pParseLogicValue;
    }

    public void setpParseLogicValue(Integer pParseLogicValue) {
        this.pParseLogicValue = pParseLogicValue;
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }
}