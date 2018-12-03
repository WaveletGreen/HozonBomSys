package com.connor.hozon.bom.resources.enumtype;

/**
 * @Author: haozt
 * @Date: 2018/11/19
 * @Description:变更类型
 */
public enum  ChangeTypeEnum {
    /**
     * 变更类型 A 新增  U 更新  D删除
     * B 变更前表
     * BU U(变更前)
     * AU U(变更后)
     */
    A("A"),
    BU("U(变更前)"),
    AU("U(变更后)"),
    D("D"),
    B("B"),
    ;


    private String changeType;

    ChangeTypeEnum(String changeType){
        this.changeType = changeType;
    }

    public String getChangeType() {
        return changeType;
    }
}
