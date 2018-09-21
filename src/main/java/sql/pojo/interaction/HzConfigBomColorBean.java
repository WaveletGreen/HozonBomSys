package sql.pojo.interaction;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/19 12:10
 * @Modified By:
 */

@Data
public class HzConfigBomColorBean {
    /**
     * 2Y层UID
     */
    private String bomLineUid;
    /**
     * 颜色代码
     */
    private String colorCode;
    /**
     * 配色模型UID
     */
    private String colorModelUid;
    /**
     * 配色模型描述
     */
    private String colorModelDesc;

    /**
     * 特性值代码
     */
    private String featureValueCode;
    /**
     * 特性代码
     */
    private String featureCode;

    public String getBomLineUid() {
        return bomLineUid;
    }

    public void setBomLineUid(String bomLineUid) {
        this.bomLineUid = bomLineUid;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorModelUid() {
        return colorModelUid;
    }

    public void setColorModelUid(String colorModelUid) {
        this.colorModelUid = colorModelUid;
    }

    public String getColorModelDesc() {
        return colorModelDesc;
    }

    public void setColorModelDesc(String colorModelDesc) {
        this.colorModelDesc = colorModelDesc;
    }

    public String getFeatureValueCode() {
        return featureValueCode;
    }

    public void setFeatureValueCode(String featureValueCode) {
        this.featureValueCode = featureValueCode;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }
}
