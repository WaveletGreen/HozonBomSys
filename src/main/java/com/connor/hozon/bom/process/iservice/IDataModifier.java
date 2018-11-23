package com.connor.hozon.bom.process.iservice;

/**
 * 数据变更，分配置的数据修改和BOM的数据修改
 */
public interface IDataModifier {
    /**
     * 配置部分
     *
     * @param orderId
     * @param params
     * @return
     */
    boolean configuration(Long orderId, Object... params);

    /**
     * BOM部分
     *
     * @param orderId
     * @param params
     * @return
     */
    boolean bom(Long orderId, Object... params);
}
