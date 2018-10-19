package com.connor.hozon.bom.bomSystem.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/8 18:36
 * @Modified By:
 */
public class PropertiesHelper {
    private Properties properties;
    public final static String SRC = "resource.properties";

    public Properties load() throws IOException {
        properties = new Properties();
        InputStream in = getClass().getClassLoader().getResourceAsStream(SRC);
        properties.load(in);
        in.close();
        return properties;
    }
}
