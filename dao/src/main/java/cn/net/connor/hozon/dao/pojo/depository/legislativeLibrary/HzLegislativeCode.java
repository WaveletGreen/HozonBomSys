package cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary;

import java.util.Date;

public class HzLegislativeCode {
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

}