package cn.net.connor.hozon.config.datasource.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/*
* 类描述：开启mybatis的支持
* @auther linzf
* @create 2017/9/25
*/
@Configuration
@MapperScan({"com.connor.hozon.controller.sys.dao","cn.net.connor.hozon.dao"})
public class MyBatisConfig {

}
