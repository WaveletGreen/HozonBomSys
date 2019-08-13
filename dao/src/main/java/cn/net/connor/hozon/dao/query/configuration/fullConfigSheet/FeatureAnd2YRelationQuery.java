/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.query.configuration.fullConfigSheet;

import lombok.Data;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/12 15:48
 * @Modified By:
 */
@Data
public class FeatureAnd2YRelationQuery {
    /**
     * 根据查找编号进行排序
     */
    private String orderBySortNum;
    /**
     * 根据表字段排序
     */
    private String orderByTableKey;
    /**
     * 项目ID
     */
    private String projectId;

    public void setOrder(String orderKey) {
        switch (orderKey) {
            case "sortNum":
                setOrderBySortNum("P_SORT_NUM");//查找编号需要解算成float才能进行排序
                setOrderByTableKey(null);//和tableKey互斥关系
                break;
            case "bomLineId":
                setOrderByTableKey("P_BOM_LINE_ID");
                setOrderBySortNum(null);//查找编号sortNum互斥关系
                break;
            case "featureValue":
                setOrderByTableKey("P_CFG0_OBJECT_ID");
                setOrderBySortNum(null);//查找编号sortNum互斥关系
                break;
        }
    }
}
