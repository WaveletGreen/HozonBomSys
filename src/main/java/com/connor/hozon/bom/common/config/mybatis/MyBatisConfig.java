package com.connor.hozon.bom.common.config.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/*
* 类描述：开启mybatis的支持
* @auther linzf
* @create 2017/9/25
*/
@Configuration
@MapperScan("com.connor.hozon.bom.sys.dao")
public class MyBatisConfig {

}
