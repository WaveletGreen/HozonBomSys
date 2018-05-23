package com.connor.hozon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * 需要告知扫描的包名
 */
@SpringBootApplication(scanBasePackages = {"sql", "com.connor.hozon"})
//@MapperScan("com.connor.hozon.bom.resources.mybatis")
public class HzBomSysApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HzBomSysApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HzBomSysApplication.class, args);
    }

}
