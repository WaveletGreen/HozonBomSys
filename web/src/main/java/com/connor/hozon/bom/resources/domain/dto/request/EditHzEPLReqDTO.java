/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.dto.request;

import cn.net.connor.hozon.common.constant.BOMTransConstants;
import com.connor.hozon.bom.resources.domain.dto.BaseDTO;
import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description: EPL编辑 页面传参
 */
@Data
public class EditHzEPLReqDTO extends BaseDTO {
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
    private String is3cpart;
    /**
     * 内外饰标识（1 内饰件 0 外饰件 其他不显示）
     */
    private String inOutSideFlag;
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
    private String regulationFlag;
    /**
     * 黑白灰匣子件
     */
    private String bwgBoxPart;
    /**
     * 开发类别
     */
    private String developType;
    /**
     * 数据版本
     */
    private String dataVersion;
    /**
     * 目标重量
     */
    private String targetWeight;
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
     * 责任工程师
     */
    private String buyEngineer;

    public Integer getIs3cpart() {
        return BOMTransConstants.constantStringToInteger(is3cpart);
    }

    public Integer getInOutSideFlag() {
        return BOMTransConstants.constantStringToInteger(inOutSideFlag);
    }

    public Integer getRegulationFlag() {
        return BOMTransConstants.constantStringToInteger(regulationFlag);
    }
}
