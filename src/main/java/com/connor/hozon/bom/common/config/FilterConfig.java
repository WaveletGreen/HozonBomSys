package com.connor.hozon.bom.common.config;

import com.connor.hozon.bom.sys.filter.HzFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/11/8
 * @Description:
 */
@Configuration
public class FilterConfig {
    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean hzFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy());
        Map<String,String> m = new HashMap<String,String>();
        m.put("targetBeanName","hzFilter");
        m.put("targetFilterLifecycle","true");
        registration.setInitParameters(m);
        registration.addUrlPatterns("/*");
        registration.setName("hzFilter");
        registration.setOrder(1);
        return registration;
    }

    /**
     * 创建一个bean
     * @return
     */
    @Bean(name = "hzFilter")
    public Filter hzFilter() {
        return new HzFilter();
    }
}
