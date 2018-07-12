package com.connor.hozon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 需要告知扫描的包名
 * 启动事物扫描
 */
@SpringBootApplication(scanBasePackages = {"sql", "integration.service", "com.connor.hozon"})
@EnableTransactionManagement
//@ServletComponentScan
public class HzBomSysApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HzBomSysApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HzBomSysApplication.class, args);
    }

}
