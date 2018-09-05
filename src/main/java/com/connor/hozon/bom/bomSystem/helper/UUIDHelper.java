package com.connor.hozon.bom.bomSystem.helper;

import java.util.UUID;

/**
 * 生成UUID
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
