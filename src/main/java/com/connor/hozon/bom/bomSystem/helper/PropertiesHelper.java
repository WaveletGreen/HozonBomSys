/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 获取属性文件助手
 * @Date: Created in 2018/10/8 18:36
 * @Modified By:
 */
public class PropertiesHelper {
    private Properties properties;
    public final static String SRC = "myresource.properties";

    public Properties load() throws IOException {
        properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream(SRC);
        properties.load(in);
        in.close();
        return properties;
    }
}
