/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 全配置一级BOM清单主数据
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzFullCfgMain {
    private Long id;

    private String projectUid;

    private Integer status;

    private Integer stage;

    private String version;

    private Date effectiveDate;

    private Date createDate;

    private Date updateDate;

    private String creator;

    private String updater;

    private Integer fmIsRelease;
    public static String parseStage(Integer code) {
        if (null == code) {
            return "";
        }
        switch (code) {
            case 1:
                return "P0-P1阶段";
            case 2:
                return "P1-P2阶段";
            case 3:
                return "P2-P3阶段";
            case 4:
                return "P3-P4阶段";
            case 5:
                return "P4-P5阶段";
            case 6:
                return "P5-P6阶段";
            case 7:
                return "P6-P7阶段";
            case 8:
                return "P7-P8阶段";
            case 9:
                return "P8-P9阶段";
            case 10:
                return "P9-P10阶段";
            default:
                return "";
        }
    }


}