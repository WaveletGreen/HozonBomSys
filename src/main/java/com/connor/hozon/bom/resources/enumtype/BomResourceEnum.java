package com.connor.hozon.bom.resources.enumtype;

/**
 * @Author: haozt
 * @Date: 2018/9/11
 * @Description: BOM系统零件来源
 */
public enum BomResourceEnum {
    PURCHASE_PART("采购件"),
    PURCHASE_OF_SPLIT_PART("采购拆分单件"),
    PURCHASE_OF_SPLIT_ASSEMBLY_PART("采购拆分总成件"),
    PURCHASE_OF_SPLIT_STANDARD_PART("采购拆分标准件"),
    PURCHASE_OF_SPLIT_OFF_STANDARD_PART("采购拆分非标件"),
    MADE_ASSEMBLY("自制总成"),
    MADE_PART("自制单件"),
    MADE_SELF_USE_STANDARD("自用标准件"),
    MADE_SELF_USE_OFF_STANDARD("自用非标件"),
    VIRTUAL_ASSEMBLY("虚拟总成")
    ;
    BomResourceEnum(String resourceMsg) {
        this.resourceMsg = resourceMsg;
    }

    /**
     * 零件来源信息
     */
    private String resourceMsg;


    public String getResourceMsg() {
        return resourceMsg;
    }

    public void setResourceMsg(String resourceMsg) {
        this.resourceMsg = resourceMsg;
    }

    /**
     * 物料类型  严格按照注释来读写数据
     * 11 整车超级物料主数据
     * 21  整车衍生物料主数据
     * 31  虚拟件物料主数据  2Y
     * 41  半成品物料主数据  自制单件
     * 51  生产性外购物料主数据  采购件
     * 61  自制备件物料主数据
     * 71  自制总成 -->工艺路线 半成品工艺路线
     * 81  MBOM中出现数据 但是方案中 物料中没有维护的 现需要出现在物料总表中
     *
     */

    public static Integer enumTypeToMaterielTypeNum(String resource,Integer is2Y){
        if(resource!=null){
            if(resource.equals(BomResourceEnum.MADE_PART.getResourceMsg())){
                return 41;
            }else if(resource.equals(BomResourceEnum.PURCHASE_PART.getResourceMsg())){
                return 51;
            }else if(resource.equals(BomResourceEnum.MADE_ASSEMBLY.getResourceMsg())){
                return 71;
            }else if(Integer.valueOf(1).equals(is2Y)){
                return 31;
            }else if(null == is2Y || Integer.valueOf(0).equals(is2Y)){
                if(resource.equals(BomResourceEnum.MADE_SELF_USE_STANDARD.getResourceMsg())){
                    return 81;
                }
                if(resource.equals(BomResourceEnum.MADE_SELF_USE_OFF_STANDARD.getResourceMsg())){
                    return 81;
                }
                if(resource.equals(BomResourceEnum.VIRTUAL_ASSEMBLY.getResourceMsg())){
                    return 81;
                }
            }
        }
        return null;
    }
}
