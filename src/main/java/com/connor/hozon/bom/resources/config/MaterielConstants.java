package com.connor.hozon.bom.resources.config;

import io.swagger.models.auth.In;

/**
 * @Author: haozt
 * @Date: 2018/7/4
 * @Description:
 */
public class MaterielConstants {
    /*
     * 11 整车超级物料主数据
     * 21 整车衍生物料主数据
     * 31 虚拟件物料主数据
     * 41半成品物料主数据
     * 51 生产性外购物料主数据
     * 61自制备件物料主数据）
     */
    public final static Integer SUPER_MATERIEL_DATA   = 11;
    public final static Integer CAR_LOAD_MATERIEL_DATA = 21;
    public final static Integer INVENTED_MATERIEL_DATA = 31;
    public final static Integer SEMI_MANUFACTURES_MATERIEL_DATA=41;
    public final static Integer SELF_MADE_FOREIGN_PURCHASE_MATERIEL_DATA = 51;
    public final static Integer SELF_MADE_SPARE_PARTS_MATERIEL_DATA=61;

}
