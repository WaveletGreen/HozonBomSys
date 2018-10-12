package sql.pojo.interaction;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/25 13:18
 * @Modified By:单车解算用，特性+2++颜色
 */
@Data
public class HzSingleVehicleBomLineBean {
    /**
     * 特性值UID
     */
    private String featureValueUid;
    /**
     * 特性值
     */
    private String featureValue;
    /**
     * 特性值描述
     */
    private String featureValueDesc;
    /**
     * 特性UID
     */
    private String featureUid;
    /**
     * 特性
     */
    private String feature;
    /**
     * 特性描述
     */
    private String featureDesc;
    /**
     * 2Y主键
     */
    private String bomLineUid;
    /**
     * 零件号
     */
    private String partId;
    /**
     * 零件名
     */
    private String partName;
    /**
     * 颜色UID
     */
    private String colorUid;
    /**
     * 颜色名
     */
    private String colorName;
    /**
     * 颜色代码
     */
    private String colorCode;

}
