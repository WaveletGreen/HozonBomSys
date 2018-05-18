package com.connor.hozon.bom;

import com.connor.hozon.HzBomSysApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 其实没什么用
 *
 * @author Administrator
 */
public class ServletInitializer

        extends SpringBootServletInitializer

{

    @Override
    protected SpringApplicationBuilder
    configure(SpringApplicationBuilder application) {
        return
                application.sources(HzBomSysApplication.class);
    }


}
