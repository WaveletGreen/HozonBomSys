package sql.pojo.resourcesLibrary.VPPSLibrary;

import java.util.Date;

public class HzVPPSLibrary {
    /**
     * 主键
     */
    private String puid;
    /**
     * VPPS层级
     */
    private Long vppsLevel;
    /**
     *VSG代码
     */
    private String vsgCode;
    /**
     *VPPS代码
     */
    private String vppsCode;
    /**
     *VPPS英文描述
     */
    private String vppsEnDesc;
    /**
     *VPPS中文 描述
     */
    private String vppsChDesc;
    /**
     *UPC
     */
    private String upc;
    /**
     *FNA
     */
    private String fna;
    /**
     *FNA中文描述
     */
    private String fnaChDesc;
    /**
     *零件标准代码
     */
    private String standardPartCode;
    /**
     *创建时间
     */
    private Date insertTime;
    /**
     *修改时间
     */
    private Date updateTime;
    /**
     * 删除标志（0删除1未删除）
     */
    private Short status;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public Long getVppsLevel() {
        return vppsLevel;
    }

    public void setVppsLevel(Long vppsLevel) {
        this.vppsLevel = vppsLevel;
    }

    public String getVsgCode() {
        return vsgCode;
    }

    public void setVsgCode(String vsgCode) {
        this.vsgCode = vsgCode;
    }

    public String getVppsCode() {
        return vppsCode;
    }

    public void setVppsCode(String vppsCode) {
        this.vppsCode = vppsCode;
    }

    public String getVppsEnDesc() {
        return vppsEnDesc;
    }

    public void setVppsEnDesc(String vppsEnDesc) {
        this.vppsEnDesc = vppsEnDesc;
    }

    public String getVppsChDesc() {
        return vppsChDesc;
    }

    public void setVppsChDesc(String vppsChDesc) {
        this.vppsChDesc = vppsChDesc;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getFna() {
        return fna;
    }

    public void setFna(String fna) {
        this.fna = fna;
    }

    public String getFnaChDesc() {
        return fnaChDesc;
    }

    public void setFnaChDesc(String fnaChDesc) {
        this.fnaChDesc = fnaChDesc;
    }

    public String getStandardPartCode() {
        return standardPartCode;
    }

    public void setStandardPartCode(String standardPartCode) {
        this.standardPartCode = standardPartCode;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}