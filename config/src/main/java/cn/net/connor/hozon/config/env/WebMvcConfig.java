/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.config.env;

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

        //一级配置表2
        registry.addViewController("/bom-all-cfg2").setViewName("/bom/bom-all-cfg2");
        //颜色库
        registry.addViewController("/colorSet").setViewName("/cfg/color/colorSet");
        //registry.addViewController("/colorUpdate").setViewName("/feature/color/colorUpdate");
        //配色方案
        registry.addViewController("/modelColorCfg").setViewName("/cfg/modelColorCfg/modelColorCfg");
        //项目，应该作为首页
        registry.addViewController("/project").setViewName("project/project");
        registry.addViewController("/project2").setViewName("project/project2");
        //特性表
        //原来的feature2.html页面修改名字为feature，但是数据库里的url映射还没有修改回feature，还是保留feature2
        registry.addViewController("/feature2").setViewName("/cfg/feature/feature");
        //原来的relevance2.html页面修改名字为relevance，但是数据库里的url映射还没有修改回relevance，还是保留relevance2
        registry.addViewController("/relevance2").setViewName("/cfg/relevance/relevance");
        /**物料配置表*/
        registry.addViewController("/materielFeature").setViewName("/cfg/materielFeature/materielFeature");
        //配色方案
        registry.addViewController("/modelColorCfg").setViewName("/cfg/modelColorCfg/modelColorCfg");
        //EPL页面
        registry.addViewController("/eplManage").setViewName("/bomManage/epl/eplManage");
        //EBOM页面
        registry.addViewController("/ebomManage").setViewName("/bomManage/ebom/ebomManage/ebomManage");
        //PBOM管理页面
        registry.addViewController("/pbomManage").setViewName("/bomManage/pbom/pbomManage/pbomManage");
        //PBOM工艺合件页面
        //registry.addViewController("/craftFitting").setViewName("/bomManage/pbom/processOfFitting/craftFitting");
        //PBOM工艺合件页面
        registry.addViewController("/craftFitting2").setViewName("/bomManage/pbom/processOfFitting/craftFitting2");
        //PBOM工艺辅料页面
        //registry.addViewController("/processAids").setViewName("/bomManage/pbom/processAids/processAids");
        registry.addViewController("/accessoriesLibrary").setViewName("/resourcesLibrary/accessoriesLibrary/accessoriesLibrary");
        //MBOM维护页面
        registry.addViewController("/mbomMaintenance").setViewName("/bomManage/mbom/mbomMaintenance/mbomMaintenance");
        //Mbom的超级MBOM页面
        //registry.addViewController("/superMBom").setViewName("/bomManage/mbom/superMBom/superMBom");
        //MBOM物料数据类别页面
        registry.addViewController("/materialData").setViewName("/bomManage/mbom/materialData/materialData");
        //MBOM的工艺中心主数据页面
        registry.addViewController("/processCenter").setViewName("/bomManage/mbom/processCenter/processCenter");
        //MBOM的整车工艺路线页面
        //registry.addViewController("/carRouting").setViewName("/bomManage/mbom/carRouting/carRouting");
        //MBOM的整车工艺路线页面
        //registry.addViewController("/assemblyRouting").setViewName("/bomManage/mbom/assemblyRouting/assemblyRouting");
        //MBOM的半成品工艺路线页面
        //registry.addViewController("/halfRouting").setViewName("/bomManage/mbom/halfRouting/halfRouting");
        //MBOM的工艺路线页面
        //registry.addViewController("/routingData").setViewName("/bomManage/mbom/routingData/routingData");
        registry.addViewController("/routingData2").setViewName("/bomManage/mbom/routingData/routingData2");
        //MBOM的工艺路线页面
        //registry.addViewController("/recycleBin").setViewName("/bomManage/recycleBin/recycleBin");
        //报价管理
        registry.addViewController("/offerManage").setViewName("/quote/offer/offerManage");
        //付款管理
        registry.addViewController("/payment").setViewName("/quote/payment/payment");
        //合众字典库
        registry.addViewController("/dictionaryLibrary").setViewName("/resourcesLibrary/dictionaryLibrary/dictionaryLibrary");
        //合众VPPS库
        registry.addViewController("/VPPSLibrary").setViewName("/resourcesLibrary/VPPSLibrary/VPPSLibrary");
        //法规件库
        registry.addViewController("/legislativeItemLibrary").setViewName("/resourcesLibrary/legislativeLibrary/legislativeItemLibrary/legislativeItemLibrary");
        //单车BOM
        registry.addViewController("/bikeBom").setViewName("/bikeBom/bikeBom");
        //备忘录
        registry.addViewController("/memo").setViewName("/memo/memo");
        //备忘录2
        registry.addViewController("/memo2").setViewName("/memo/memo2");
        //备忘录3
        registry.addViewController("/memo3").setViewName("/memo/memo3");


        //待办事项
        registry.addViewController("/untreated").setViewName("/myListJob/untreated/untreated");
        //已处理事项
        registry.addViewController("/processed").setViewName("/myListJob/processed/processed");
        //我的申请
        registry.addViewController("/myApplication").setViewName("/myListJob/myApplication/myApplication");


        //变更表单
        registry.addViewController("/changeForm").setViewName("/change/changeForm/changeForm");
        //变更管理CODPWO表单
        registry.addViewController("/breakPoint").setViewName("/change/breakPoint/breakPoint");

        //变更管理VWO表单
        //registry.addViewController("/vwoFormList").setViewName("/changeManage/vwo/vwoFormList");
        //变更管理EWO表单
        //registry.addViewController("/ewoFromList").setViewName("/changeManage/ewo/ewoFromList");
        //变更管理MWO表单
        //registry.addViewController("/mwoFromList").setViewName("/changeManage/mwo/mwoFromList");
        //变更管理物料数据表单
        //registry.addViewController("/materialForm").setViewName("/changeManage/materialForm");
        //变更管理工艺路线表单
        //registry.addViewController("/processForm").setViewName("/changeManage/processForm");
        //变更管理工作中心表单
        //registry.addViewController("/workFrom").setViewName("/changeManage/workFrom");
        //变更管理CODPWO表单
        //registry.addViewController("/codpwoFromList").setViewName("/changeManage/codpwo/codpwoFromList");
        //临时变更表单
        //registry.addViewController("/1111").setViewName("/updateLog/1111");





        //更新日志
        registry.addViewController("/updateLog").setViewName("/updateLog/updateLog");
        //备件零件主页
        registry.addViewController("/sparePartIndex").setViewName("/bomManage/sparepart/sparePartIndex");

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
