package com.connor.hozon.bom.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 类描述：springMVC的配置，在这里直接注册了调用的所有页面注册信息
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 重写方法描述：实现在url中输入相应的地址的时候直接跳转到某个地址
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/Error").setViewName("error");
        registry.addViewController("/home").setViewName("home");
        // 跳转到菜单管理页面
        registry.addViewController("/treeList").setViewName("/sys/tree/treeList");
        // 跳转到角色管理页面
        registry.addViewController("/userRoleList").setViewName("/sys/role/roleList");
        // 组织架构页面
        registry.addViewController("/groupList").setViewName("/sys/orggroup/groupList");
        // 用户管理页面
        registry.addViewController("/userList").setViewName("/sys/user/userList");
        // 数据字典页面
        registry.addViewController("/dictList").setViewName("/sys/dict/dictList");
        // 注册bom视图
        registry.addViewController("/bom").setViewName("/stage/bom");
        registry.addViewController("/bom-treegrid").setViewName("/stage/bom-treegrid");
        registry.addViewController("/bom-all-cfg").setViewName("/bom/bom-all-cfg");
        //颜色库
        registry.addViewController("/colorSet").setViewName("/cfg/color/colorSet");
        registry.addViewController("/colorUpdate").setViewName("/cfg/color/colorUpdate");

    }


    /**
     * 1、 extends WebMvcConfigurationSupport
     * <p>
     * 2、重写下面方法;
     * <p>
     * setUseSuffixPatternMatch : 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配；
     * <p>
     * setUseTrailingSlashMatch : 设置是否自动后缀路径模式匹配，如“/user”是否匹配“/user/”，默认真即匹配；
     * 设置是否是后缀模式匹配，如“/user”是否匹配/user.*，默认真即匹配；
     * <p>
     * 当此参数设置为true的时候，那么/user.html，/user.aa，/user.*都能是正常访问的。
     * <p>
     * 当此参数设置为false的时候，那么只能访问/user或者/user/( 这个前提是setUseTrailingSlashMatch 设置为true了)。
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false)
                .setUseTrailingSlashMatch(true);
        super.configurePathMatch(configurer);
    }
}
