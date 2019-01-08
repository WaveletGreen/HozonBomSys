package sql.pojo.epl;

import lombok.Data;
import sql.pojo.BaseChangeDO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description: 需求更改 EPL 字段
 *  EPL 零部件信息 做单独维护
 *  之前的E + P + MBOM 字段 {@link HzEPLManageRecord},不在维护
 *  代码兼容性 {@link HzEPLManageRecord}仍在使用 未废弃
 */
@Data
public class HzEPLRecord extends BaseChangeDO {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 零件号
     */
    private String partId;
    /**
     * 专业
     */
    private String partOfWhichDept;
    /**
     * 零件名称
     */
    private String partName;
    /**
     * 零件分类
     */
    private String partClass;
    /**
     * 零件英文名称
     */
    private String partEnName;
    /**
     * 零件来源
     */
    private String partResource;
    /**
     * 状态标志位 1 已生效 2草稿状态 4删除状态 5审核中 0已删除
     */
    private Integer status;
    /**
     * 紧固件
     */
    private String fastener;
    /**
     * 是否3C件（1 是 0不是）
     */
    private Integer is3cpart;
    /**
     * 内外饰标识（1 内饰件 0 外饰件 其他不显示）
     */
    private Integer inOutSideFlag;
    /**
     * 单位
     */
    private String unit;
    /**
     * 图号
     */
    private String pictureNo;
    /**
     * 图幅
     */
    private String pictureSheet;
    /**
     * 料厚
     */
    private String materialHigh;
    /**
     * 材料1
     */
    private String material1;
    /**
     * 材料2
     */
    private String material2;
    /**
     * 材料3
     */
    private String material3;
    /**
     * 密度
     */
    private String density;
    /**
     * 材料标准
     */
    private String materialStandard;
    /**
     * 表面处理
     */
    private String surfaceTreat;
    /**
     * 纹理编号/色彩编号
     */
    private String textureColorNum;
    /**
     * 制造工艺
     */
    private String manuProcess;
    /**
     * 对称
     */
    private String symmetry;
    /**
     * 重要度
     */
    private String importance;
    /**
     * 是否法规件
     */
    private Integer regulationFlag;
    /**
     * 黑白灰匣子件
     */
    private String bwgBoxPart;
    /**
     * 开发类别
     */
    private String developType;
    /**
     * 目标重量
     */
    private String targetWeight;
    /**
     * 数据版本
     */
    private String dataVersion;
    /**
     * 预估重量
     */
    private String featureWeight;
    /**
     * 实际重量
     */
    private String actualWeight;
    /**
     * 紧固件规格
     */
    private String fastenerStandard;
    /**
     * 紧固件性能等级
     */
    private String fastenerLevel;
    /**
     * 扭矩
     */
    private String torque;
    /**
     * 责任工程师
     */
    private String dutyEngineer;
    /**
     * 供应商
     */
    private String supply;
    /**
     * 供应商代码
     */
    private String supplyCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 法规件型号
     */
    private String regulationCode;
    /**
     * 采购工程师
     */
    private String buyEngineer;


    public static List<HzEPLRecord> getListBeen(List<String[]> data,String projectId){
        List<HzEPLRecord> hzEPLRecords = new ArrayList<>();

        for(String[] properties : data){
            HzEPLRecord hzEPLRecord = new HzEPLRecord();
            hzEPLRecord.setProjectId(projectId);
            hzEPLRecord.setPartId(properties[1]);
            hzEPLRecord.setPartName(properties[2]);
            hzEPLRecord.setPartOfWhichDept(properties[3]);
            hzEPLRecord.setPartEnName(properties[4]);
            hzEPLRecord.setUnit(properties[5]);
            hzEPLRecord.setPictureNo(properties[6]);
            hzEPLRecord.setPictureSheet(properties[7]);
            hzEPLRecord.setMaterialHigh(properties[8]);
            hzEPLRecord.setMaterial1(properties[9]);
            hzEPLRecord.setMaterial2(properties[10]);
            hzEPLRecord.setMaterial3(properties[11]);
            hzEPLRecord.setDensity(properties[12]);
            hzEPLRecord.setMaterialStandard(properties[13]);
            hzEPLRecord.setSurfaceTreat(properties[14]);
            hzEPLRecord.setTextureColorNum(properties[15]);
            hzEPLRecord.setManuProcess(properties[16]);
            hzEPLRecord.setSymmetry(properties[17]);
            hzEPLRecord.setImportance(properties[18]);
            hzEPLRecord.setRegulationFlag("".equals(properties[19])?null:"/".equals(properties[19])?null:"Y".equals(properties[19])?1:0);
            hzEPLRecord.setIs3cpart("".equals(properties[20])?null:"/".equals(properties[20])?null:"Y".equals(properties[20])?1:0);
            hzEPLRecord.setRegulationCode(properties[21]);
            hzEPLRecord.setBwgBoxPart(properties[22]);
            hzEPLRecord.setDevelopType(properties[23]);
            hzEPLRecord.setDataVersion(properties[24]);
            hzEPLRecord.setTargetWeight(properties[25]);
            hzEPLRecord.setFeatureWeight(properties[26]);
            hzEPLRecord.setActualWeight(properties[27]);
            hzEPLRecord.setFastener(properties[28]);
            hzEPLRecord.setFastenerStandard(properties[29]);
            hzEPLRecord.setFastenerLevel(properties[30]);
            hzEPLRecord.setTorque(properties[31]);
            hzEPLRecord.setDutyEngineer(properties[32]);
            hzEPLRecord.setSupply(properties[33]);
            hzEPLRecord.setSupplyCode(properties[34]);
            hzEPLRecord.setBuyEngineer(properties[35]);
            hzEPLRecord.setRemark(properties[36]);
            hzEPLRecord.setPartClass(properties[37]);
            hzEPLRecord.setPartResource(properties[38]);
            hzEPLRecord.setInOutSideFlag("".equals(properties[39])?null:"/".equals(properties[39])?null:"内饰件".equals(properties[39])?1:0);

            hzEPLRecords.add(hzEPLRecord);
        }

        return hzEPLRecords;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        if(o instanceof HzEPLRecord){
            final HzEPLRecord record = (HzEPLRecord) o;
            if(this.id != null){
                return this.id.equals(record.getId());
            }
            if(this.partId != null){
                return this.partId.equals(record.getPartId());
            }
            return false;
        }
        return  false;
    }

    @Override
    public int hashCode() {
        if(id == null ){
            return Objects.hash(partId);
        }
        return Objects.hash(id, partId);
    }
}
