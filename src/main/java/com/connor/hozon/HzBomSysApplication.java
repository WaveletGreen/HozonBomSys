package com.connor.hozon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 需要告知扫描的包名
 */
@SpringBootApplication(scanBasePackages = {"sql", "com.connor.hozon"})

public class HzBomSysApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HzBomSysApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HzBomSysApplication.class, args);
    }

}
