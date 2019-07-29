/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.setting;

import org.springframework.context.annotation.PropertySource;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/26 11:23
 * @Modified By:
 */
@PropertySource("classpath:common.properties")
public class CommonSetting {
    /**工艺合件查询字符串拆分符号*/
    public static String semicolon = ";";
    /**输出的数据JSON字段定义*/
    public final static String RESULT_DATA = "data";
    /**
     * 成功与否消息字段
     */
    public final static String STATUS_FIELD ="success";
    /**
     *错误消息字段
     */
    public final static String ERROR_FIELD="errMsg";

    /** 输出到前端的状态*/
    public enum STATUS{
        SUCCESS,
        ERROR,
    }
}
