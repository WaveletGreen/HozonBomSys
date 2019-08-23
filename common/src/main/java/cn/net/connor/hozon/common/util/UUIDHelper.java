/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.util;

import java.util.UUID;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: UUID生成助手，主要用于生成主键
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class UUIDHelper {
    /**
     * 生成1个原始的UUID
     *
     * @return
     */
    public static String generateUid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成1个大写转换的UUID
     *
     * @return
     */
    public static String generateUpperUid() {
        return generateUid().toUpperCase();
    }

    /**
     * 生成1个小写的UUID
     *
     * @return
     */
    public static String generatLowerUid() {
        return generateUid().toLowerCase();
    }
}
