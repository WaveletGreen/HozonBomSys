package com.connor.hozon.bom.resources.domain.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/8/16
 * @Description:
 */
public class HzLouRespDTO {
    /**
     * 配置名
     */
    private String pCfg0name;
    /**
     * 特性值描述
     */
    private String cfg0Desc;
    /**
     * 族名
     */
    private String pCfg0familyname;
    /**
     * 特性描述
     */
    private String cfg0FamilyDesc;


    public String getpCfg0name() {
        return pCfg0name;
    }

    public void setpCfg0name(String pCfg0name) {
        this.pCfg0name = pCfg0name;
    }

    public String getCfg0Desc() {
        return cfg0Desc;
    }

    public void setCfg0Desc(String cfg0Desc) {
        this.cfg0Desc = cfg0Desc;
    }

    public String getpCfg0familyname() {
        return pCfg0familyname;
    }

    public void setpCfg0familyname(String pCfg0familyname) {
        this.pCfg0familyname = pCfg0familyname;
    }

    public String getCfg0FamilyDesc() {
        return cfg0FamilyDesc;
    }

    public void setCfg0FamilyDesc(String cfg0FamilyDesc) {
        this.cfg0FamilyDesc = cfg0FamilyDesc;
    }
}
