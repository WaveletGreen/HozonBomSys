/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.fullCfg;

/**
 * 配置值与BOM行的映射关系
 */
public class HzCfg0OfBomLineRecord {
    /**
     * 主键
     */
    private String puid;
    /**
     * 对应的BOM行的PUID
     */
    private String pBomlinepuid;
    /**
     * 配置名
     */
    private String pCfg0name;
    /**
     * 特性值描述
     */
    private String cfg0Desc;
    /**
     * 族名(特性名称)
     */
    private String pCfg0familyname;
    /**
     * 特性描述
     */
    private String cfg0FamilyDesc;
    /**
     * 数模层的PUID
     */
    private String pBomDigifaxId;
    /**
     * BOMLine行名
     */
    private String pBomLineName;
    /**
     * 指向选项值的puid，做为外键
     */
    private String pToCfg0IdOfBl;
    /**
     * 项目UID
     */
    private String projectUid;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpBomlinepuid() {
        return pBomlinepuid;
    }

    public void setpBomlinepuid(String pBomlinepuid) {
        this.pBomlinepuid = pBomlinepuid == null ? null : pBomlinepuid.trim();
    }

    public String getpCfg0name() {
        return pCfg0name;
    }

    public void setpCfg0name(String pCfg0name) {
        this.pCfg0name = pCfg0name;
    }

    public String getpCfg0familyname() {
        return pCfg0familyname;
    }

    public void setpCfg0familyname(String pCfg0familyname) {
        this.pCfg0familyname = pCfg0familyname;
    }

    public String getpBomDigifaxId() {
        return pBomDigifaxId;
    }

    public void setpBomDigifaxId(String pBomDigifaxId) {
        this.pBomDigifaxId = pBomDigifaxId == null ? null : pBomDigifaxId.trim();
    }

    public String getpBomLineName() {
        return pBomLineName;
    }

    public void setpBomLineName(String pBomLineName) {
        this.pBomLineName = pBomLineName == null ? null : pBomLineName.trim();
    }

    public String getpToCfg0IdOfBl() {
        return pToCfg0IdOfBl;
    }

    public void setpToCfg0IdOfBl(String pToCfg0IdOfBl) {
        this.pToCfg0IdOfBl = pToCfg0IdOfBl == null ? null : pToCfg0IdOfBl.trim();
    }

    public String getProjectUid() {
        return projectUid;
    }

    public void setProjectUid(String projectUid) {
        this.projectUid = projectUid;
    }

    public String getCfg0Desc() {
        return cfg0Desc;
    }

    public void setCfg0Desc(String cfg0Desc) {
        this.cfg0Desc = cfg0Desc;
    }

    public String getCfg0FamilyDesc() {
        return cfg0FamilyDesc;
    }

    public void setCfg0FamilyDesc(String cfg0FamilyDesc) {
        this.cfg0FamilyDesc = cfg0FamilyDesc;
    }
}