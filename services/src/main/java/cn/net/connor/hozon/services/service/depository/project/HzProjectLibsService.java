/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.depository.project;

import cn.net.connor.hozon.dao.pojo.depository.project.HzProjectLibs;
import cn.net.connor.hozon.dao.pojo.depository.project.IProject;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/29 17:10
 * @Modified By:
 */
public interface HzProjectLibsService {
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description:返回所有的项目信息
     * Date: 2018/5/30 14:18
     *
     * @return 所有的项目对象
     */
    List<HzProjectLibs> doLoadAllProjectLibs();

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据项目的puid获取到项目对象
     * Date: 2018/5/30 14:20
     *
     * @return 项目对象，找到则返回，否则返回null
     */
    HzProjectLibs doLoadProjectLibsById(String puid);

    /**
     * 验证项目是否符合要求
     * 只验证项目代码和项目名称
     *
     * @param project 项目对象
     * @return
     */
    boolean validate(HzProjectLibs project);

    /**
     * 修改检查
     * 不修改平台信息，所以不用检查平台
     * <p>
     * 分别验证项目代号，项目名称
     *
     * @param project 项目对象
     * @return
     */
    boolean modifyValidate(HzProjectLibs project) ;

    /**
     * 根据项目编号找项目
     *
     * @param projectCode 项目编号
     * @return
     */
    HzProjectLibs doGetByProjectCode(String projectCode);

    /**
     * 插入一个新项目
     *
     * @param project
     * @return
     */
    boolean doInsertOne(HzProjectLibs project) ;

    /**
     * 根据主键删除项目
     *
     * @param puid 项目puid
     * @return
     */
    boolean doDeleteByPuid(String puid) ;

    /**
     * 根据主键更新项目
     * @param project
     * @return
     */
    boolean doUpdateByPrimaryKey(HzProjectLibs project) ;
    /**
     * 项目编号查重
     *
     * @param project 项目对象
     * @return
     */
    JSONObject doValidateCodeWithPuid(IProject project) ;
}
