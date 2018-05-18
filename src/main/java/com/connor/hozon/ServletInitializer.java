package com.connor.hozon;

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
