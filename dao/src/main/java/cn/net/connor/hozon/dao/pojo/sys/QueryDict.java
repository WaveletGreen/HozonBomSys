/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.sys;


import cn.net.connor.hozon.common.entity.QueryBase;
import lombok.Data;

/**
 * @author linzf
 **/
@Data
public class QueryDict extends QueryBase {
    private String code;
    private String text;
    private String type;
    private String value;
    private String isLoad;
}
