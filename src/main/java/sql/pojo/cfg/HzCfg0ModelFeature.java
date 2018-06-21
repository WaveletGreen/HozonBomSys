package sql.pojo.cfg;

public class HzCfg0ModelFeature {
    /**
     * 主键
     */
    private String puid;
    /**
     * 归属模型
     */
    private String pPertainToModel;
    /**
     * 归属颜色车身，车型模型+颜色车身=1个数据，1阶段不使用颜色车身，因此该字段留空
     */
    private String pPertainToColorModel;
    /**
     * 中文描述
     */
    private Object pFeatureCnDesc;
    /**
     * 单车配置配置码
     */
    private Object pFeatureSingleVehicleCode;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpPertainToModel() {
        return pPertainToModel;
    }

    public void setpPertainToModel(String pPertainToModel) {
        this.pPertainToModel = pPertainToModel == null ? null : pPertainToModel.trim();
    }

    public String getpPertainToColorModel() {
        return pPertainToColorModel;
    }

    public void setpPertainToColorModel(String pPertainToColorModel) {
        this.pPertainToColorModel = pPertainToColorModel == null ? null : pPertainToColorModel.trim();
    }

    public Object getpFeatureCnDesc() {
        return pFeatureCnDesc;
    }

    public void setpFeatureCnDesc(Object pFeatureCnDesc) {
        this.pFeatureCnDesc = pFeatureCnDesc;
    }

    public Object getpFeatureSingleVehicleCode() {
        return pFeatureSingleVehicleCode;
    }

    public void setpFeatureSingleVehicleCode(Object pFeatureSingleVehicleCode) {
        this.pFeatureSingleVehicleCode = pFeatureSingleVehicleCode;
    }
}