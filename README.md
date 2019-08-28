# HozonBomSys BOM系统
#### 公司网址 http://www.connor.net.cn/
#### 项目介绍
Bom系统，作为整车BOM(物料清单)管理工具，应用于整车厂的BOM设计与管理，依附配置的管理，提供EBOM->PBOM->MBOM->单车BOM的最终演算过程

内置数据管理的简化流程，用于提供专业人员的数据校验与审核功能，大大简化审核流程，为设计工程师提供最大的便利


##### 具体开发注意事项请参照Note.txt

#### 软件架构
#####软件功能模块说明
- BOM数据管理模块、配置管理模块和系统管理模块
- BOM数据管理模块作为整个项目数据的基础
- 配置模块中的全配置BOM一级清单为整合整车BOM的主心骨数据模型

#####需求软件
- oracle 11g
- Java 8
- Tomcat 9

#####后台设计模块说明
- common模块 用于构建通用的基础类和工具类
- config模块 web端的基本配置
- dao模块 基础数据库访问层
- service模块 业务处理层和领域类
- controller模块 暴露前端的REST服务
- web模块 历史遗留代码，无法全部完整的进行分模块化，相当于旧版本的src目录下所有包结构

#### 安装教程

ideal下载项目，构建maven项目，maven->package web模块即可得到hozon.war包，复制到tomcat->webapps文件夹下，启动tomcat即可运行。

如果需要访问数据库资源，请参照v1.0.0版本的附件backup.pde文件和seq_out.sql，导入到oracle数据库即可

####使用说明
keystore.p12为RSA秘钥，实际上线运行请自行更换自己的私钥

#### 参与贡献
* fancyears 黎立聪 https://gitee.com/fancyears
* xulf 许林飞 https://gitee.com/xu_linfei
* zhuangl 张乐 https://gitee.com/zhanglemy
* duanyf 段云峰 https://gitee.com/duanyunfeng
* zhudb 朱东波 https://gitee.com/zhudongbo
* haozt 郝志涛 https://gitee.com/notlowb
* zhuan 朱安亮 https://gitee.com/zhual

####测试地址
https://47.97.154.144:8444/hozon/main


