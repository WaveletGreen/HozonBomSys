package sql.pojo.bom;

import lombok.Data;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/9/26
 * @Description:单车BOM
 */
@Data
public class HzSingleVehiclesBomRecord {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 父层puid
     */
    private String parentUid;
    /**
     * 是否部门层
     */
    private Integer isDept;
    /**
     * 数模层PUID
     */
    private String bomDigifaxId;
    /**
     * Bom行的层级索引
     */
    private String lineIndex;
    /**
     * Bom行对应的零件号
     */
    private String lineId;
    /**
     * 是否有子层
     */
    private Integer isHas;

    /**
     * 是否2Y层
     */
    private Integer is2Y;
    /**
     * 是否零件
     */
    private Integer isPart;
    /**
     * 2Y层归属哪个部门
     */
    private String pBomOfWhichDept;
    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;

    /**
     * 英文名称
     */
    private String pBomLinePartEnName;
    /**
     * Bom行对应的零件类别
     */
    private String pBomLinePartClass;

    /**
     * 零件来源
     */
    private String pBomLinePartResource;

    /**
     * 备件
     */
    private String sparePart;
    /**
     *备件编号
     */
    private String sparePartNum;
    /**
     * 工艺路线
     */
    private String processRoute;
    /**
     * 人工工时
     */
    private String laborHour;
    /**
     *节拍
     */
    private String rhythm;
    /**
     * 焊点
     */
    private String solderJoint;
    /**
     * 机物料
     */
    private String machineMaterial;

    /**
     * 标准件
     */
    private String standardPart;
    /**
     * 工具
     */
    private String tools;
    /**
     * 废品
     */
    private String wasterProduct;
    /**
     * 变更
     */
    private String change;
    /**
     * 变更编号
     */
    private String changeNum;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 创建者
     */
    private String createName;
    /**
     * 更改者
     */
    private String updateName;

    /**
     * 采购件类型
     */
    private String resource;

    /**
     * 发货料库存地点
     */
    private String pStockLocation;

    /**
     * 工厂
     */
    private String pFactoryId;

    /**
     * 排序号 用于展示BOM排列顺序
     */
    private String sortNum;

    /**
     * 对应于MBOM的主键
     */
    private String mBomPuid;

    /**
     * 对应于数据源头（ebom，pbom）主键，该键用于查询父子层关系
     */
    private String eBomPuid;

    /**
     * 对应于单车清单的主键
     */
    private Long singleVehiclesId;

    /**
     * BOM类型
     */
    private Integer bomType;
}
