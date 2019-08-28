# HozonBomSys BOM系统

#### 项目介绍
Bom系统，作为整车BOM(物料清单)管理工具，应用于整车厂的BOM设计与管理，依附配置的管理，提供EBOM->PBOM->MBOM->单车BOM的最终演算过程

内置数据管理的简化流程，用于提供专业人员的数据校验与审核功能，大大简化审核流程，为设计工程师提供最大的便利


##### 具体开发注意事项请参照Note.txt

#### 软件架构
#####软件功能模块说明
- BOM数据管理模块、配置管理模块和系统管理模块
- BOM数据管理模块作为整个项目数据的基础
- 配置模块中的全配置BOM一级清单为整合整车BOM的主心骨数据模型

#####数据库
- oracle数据库

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


#### 参与贡献
* haozt 郝志涛
* zhudb 朱东波
* xulf 许林飞
* zhuan 朱安亮
* zhuangl 张乐
* duanyf 段云峰