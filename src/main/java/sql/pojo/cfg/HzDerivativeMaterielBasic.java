package sql.pojo.cfg;

import lombok.Data;

import java.util.Date;

@Data
public class HzDerivativeMaterielBasic {
    /**
     * 主键
     */
    private Long id;
    /**
     * 基础模型外键
     */
    private String dmbModelUid;
    /**
     * 颜色车型外键
     */
    private String dmbColorModelUid;
    /**
     * 创建者
     */
    private String dmbCreator;
    /**
     * 创建日期
     */
    private Date dmbCreateDate;
    /**
     * 更新者
     */
    private String dmbUpdater;
    /**
     * 更新日期
     */
    private Date dmbUpdateDate;
    /**
     * 项目UID
     */
    private String dmbProjectUid;
    /**
     * 预留字段1
     */
    private String dmbModelFeatureUid;
    /**
     * 预留字段2
     */
    private String dmbReserved2;
    /**
     * 预留字段3
     */
    private String dmbReserved3;
    /**
     * 预留字段4
     */
    private String dmbReserved4;
    /**
     * 预留字段5
     */
    private String dmbReserved5;

    private String dmbSpecialFeatureUid;
}