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
        //登陆界面
        registry.addViewController("/login").setViewName("login");
        //首页
        registry.addViewController("/index").setViewName("index");
        //主界面
        registry.addViewController("/main").setViewName("main");
        //统一出错界面
        registry.addViewController("/Error").setViewName("error");
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
        //bom的树型结构
        registry.addViewController("/bom-treegrid").setViewName("/stage/bom-treegrid");
        //一级配置表
        registry.addViewController("/bom-all-cfg").setViewName("/bom/bom-all-cfg");
        //一级配置表
        registry.addViewController("/bom-all-cfg1").setViewName("/bom/bom-all-cfg1");
        //颜色库
        registry.addViewController("/colorSet").setViewName("/cfg/color/colorSet");
//        registry.addViewController("/colorUpdate").setViewName("/cfg/color/colorUpdate");
        //配色方案
        registry.addViewController("/modelColorCfg").setViewName("/cfg/modelColorCfg/modelColorCfg");
        //项目，应该作为首页
        registry.addViewController("/project").setViewName("project/project");
        registry.addViewController("/project2").setViewName("project/project2");
        //特性表
        registry.addViewController("/feature").setViewName("/cfg/feature/feature");
        //相关性表
        registry.addViewController("/relevance").setViewName("/cfg/relevance/relevance");
        /**物料配置表*/
        registry.addViewController("/materielFeature").setViewName("/cfg/materielFeature/materielFeature");
        //特性表页面
        registry.addViewController("/featuresList").setViewName("/cfg/features/featuresList");
        //配色方案
        registry.addViewController("/modelColorCfg").setViewName("/cfg/modelColorCfg/modelColorCfg");
        //EPL页面
        registry.addViewController("/eplManage").setViewName("/bomManage/epl/eplManage");
        //EBOM页面
        registry.addViewController("/ebomManage").setViewName("/bomManage/ebom/ebomManage/ebomManage");
        //PBOM管理页面
        registry.addViewController("/pbomManage").setViewName("/bomManage/pbom/pbomManage/pbomManage");
        //PBOM工艺合件页面
        registry.addViewController("/craftFitting").setViewName("/bomManage/pbom/processOfFitting/craftFitting");
        //PBOM工艺辅料页面
        registry.addViewController("/processAids").setViewName("/bomManage/pbom/processAids/processAids");
        //MBOM维护页面
        registry.addViewController("/mbomMaintenance").setViewName("/bomManage/mbom/mbomMaintenance/mbomMaintenance");
        //Mbom的超级MBOM页面
        registry.addViewController("/superMBom").setViewName("/bomManage/mbom/superMBom/superMBom");
        //MBOM物料数据类别页面
        registry.addViewController("/materialData").setViewName("/bomManage/mbom/materialData/materialData");
        //MBOM的工艺中心主数据页面
        registry.addViewController("/processCenter").setViewName("/bomManage/mbom/processCenter/processCenter");
        //MBOM的整车工艺路线页面
        registry.addViewController("/carRouting").setViewName("/bomManage/mbom/carRouting/carRouting");
        //MBOM的整车工艺路线页面
        registry.addViewController("/assemblyRouting").setViewName("/bomManage/mbom/assemblyRouting/assemblyRouting");
        //MBOM的半成品工艺路线页面
        registry.addViewController("/halfRouting").setViewName("/bomManage/mbom/halfRouting/halfRouting");
        //MBOM的工艺路线页面
        registry.addViewController("/routingData").setViewName("/bomManage/mbom/routingData/routingData");
        //MBOM的工艺路线页面
        registry.addViewController("/recycleBin").setViewName("/bomManage/recycleBin/recycleBin");


        //变更管理VWO表单
        registry.addViewController("/vwoFromList").setViewName("/changeManage/vwo/vwoFromList");
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
