package com.connor.hozon.bom.resources.config;

/**
 * @Author: haozt
 * @Date: 2018/7/4
 * @Description: 物料类型
 */
public enum MaterielType {

    SUPER_MATERIEL_DATA(MaterielConstants.SUPER_MATERIEL_DATA,"整车超级物料主数据"),
    CAR_LOAD_MATERIEL_DATA(MaterielConstants.CAR_LOAD_MATERIEL_DATA,"整车衍生物料主数据"),
    INVENTED_MATERIEL_DATA(MaterielConstants.INVENTED_MATERIEL_DATA,"虚拟件物料主数据"),
    SEMI_MANUFACTURES_MATERIEL_DATA(MaterielConstants.SEMI_MANUFACTURES_MATERIEL_DATA,"半成品物料主数据"),
    SELF_MADE_FOREIGN_PURCHASE_MATERIEL_DATA(MaterielConstants.SELF_MADE_FOREIGN_PURCHASE_MATERIEL_DATA,"生产性外购物料主数据"),
    SELF_MADE_SPARE_PARTS_MATERIEL_DATA(MaterielConstants.SELF_MADE_SPARE_PARTS_MATERIEL_DATA,"自制备件物料主数据");


    /**
     * 具体物料类型
     */
    private Integer materielType;

    /**
     * 操作名称
     */
    private String actionName;
    MaterielType(Integer materielType,String actionName){
        this.materielType = materielType;
        this.actionName = actionName;
    }

    public Integer getMaterielType() {
        return materielType;
    }

    public String getActionName() {
        return actionName;
    }

}
