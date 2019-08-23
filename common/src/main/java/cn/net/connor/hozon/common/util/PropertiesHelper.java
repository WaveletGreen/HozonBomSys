/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 获取属性文件助手
 * @Date: Created in 2018/10/8 18:36
 * @Modified By:
 */
public class PropertiesHelper {
    private Properties properties;
    public final static String SRC = "myresource.properties";

    public Properties load() throws IOException {
        return execute(SRC);
    }

    public Properties load(String fileName) throws IOException {
        return execute(fileName);
    }

    public Properties execute(String src) throws IOException {
        properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream(src);
        properties.load(in);
        in.close();
        return properties;
    }
}
