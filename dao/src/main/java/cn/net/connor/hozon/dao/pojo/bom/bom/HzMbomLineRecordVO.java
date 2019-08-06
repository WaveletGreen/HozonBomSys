/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.bom.bom;

import lombok.Data;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/9/21
 * @Description:
 */
@Data
public class HzMbomLineRecordVO {

    private String tableName;

    private List<HzMbomLineRecord> recordList;
    
}
