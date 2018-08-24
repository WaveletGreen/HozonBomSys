package com.connor.hozon.bom.resources.controller.change.vwo;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/17 11:13
 * @Modified By:
 */
public class VWOUserGroupDTO {
    /**
     * ID
     */
    private String uid;
    /**
     * 父层ID
     */
    private String pUid;
    /**
     * 名称
     */
    private String name;
    /**
     * 数据库ID
     */
    private Long dbId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getpUid() {
        return pUid;
    }

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }
}