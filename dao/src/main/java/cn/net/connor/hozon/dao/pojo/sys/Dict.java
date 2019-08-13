/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.sys;

import lombok.Data;

/**
 * @author linzf
 **/
@Data
public class Dict {
    private int id;
    private String code;
    private String text;
    private String type;
    private String value;
    private String isLoad;
}
