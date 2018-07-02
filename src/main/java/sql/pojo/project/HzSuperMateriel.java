package sql.pojo.project;

public class HzSuperMateriel {
    /**
     * puid
     */
    private String puid;
    /**
     * 超级物料编码
     */
    private Object pMaterielCode;
    /**
     * 超级物料类型
     */
    private Object pMaterielType;
    /**
     * 工厂
     */
    private Object pMaterielWerk;
    /**
     * 物料描述
     */
    private Object pMaterielDesc;
    /**
     * 归属项目
     */
    private String pPertainToProjectPuid;


    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public Object getpMaterielCode() {
        return pMaterielCode;
    }

    public void setpMaterielCode(Object pMaterielCode) {
        this.pMaterielCode = pMaterielCode;
    }

    public Object getpMaterielType() {
        return pMaterielType;
    }

    public void setpMaterielType(Object pMaterielType) {
        this.pMaterielType = pMaterielType;
    }

    public Object getpMaterielWerk() {
        return pMaterielWerk;
    }

    public void setpMaterielWerk(Object pMaterielWerk) {
        this.pMaterielWerk = pMaterielWerk;
    }

    public Object getpMaterielDesc() {
        return pMaterielDesc;
    }

    public void setpMaterielDesc(Object pMaterielDesc) {
        this.pMaterielDesc = pMaterielDesc;
    }

    public String getpPertainToProjectPuid() {
        return pPertainToProjectPuid;
    }

    public void setpPertainToProjectPuid(String pPertainToProjectPuid) {
        this.pPertainToProjectPuid = pPertainToProjectPuid == null ? null : pPertainToProjectPuid.trim();
    }
}