package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzProjectLibsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzProjectLibs;

import java.util.List;

/***
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/30 14:15
 *
 * Description: 项目对象信息服务层，用于获取到项目信息，目前项目信息在BOM系统没有必要进行增删
 *
 **/
@Service("hzProjectLibsService")
public class HzProjectLibsService {
    @Autowired
    HzProjectLibsDao hzProjectLibsDao;

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description:返回所有的项目信息
     * Date: 2018/5/30 14:18
     *
     * @return 所有的项目对象
     */
    public List<HzProjectLibs> doLoadAllProjectLibs() {
        return hzProjectLibsDao.selectAllProject();
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据项目的puid获取到项目对象
     * Date: 2018/5/30 14:20
     *
     * @return 项目对象，找到则返回，否则返回null
     */
    public HzProjectLibs doLoadProjectLibsById(String puid) {
        return hzProjectLibsDao.selectByPrimaryKey(puid);
    }

    /**
     * 验证项目是否符合要求
     * 只验证项目代码和项目名称
     *
     * @param project 项目对象
     * @return
     */
    public boolean validate(HzProjectLibs project) {
        //项目编号和项目名称不能为空
        if (null == project.getpProjectCode() || null == project.getpProjectName() || "".equals(project.getpProjectCode()) || "".equals(project.getpProjectName())
                || null == project.getpProjectPertainToPlatform() || "".equals(project.getpProjectPertainToPlatform())
                ) {
            return false;
        } else return true;
    }

    /**
     * 根据项目编号找项目
     *
     * @param projectCode 项目编号
     * @return
     */
    public HzProjectLibs doGetByProjectCode(String projectCode) {
        return hzProjectLibsDao.selectByProjectCode(projectCode);
    }

    /**
     * 插入一个新项目
     *
     * @param project
     * @return
     */
    public boolean doInsertOne(HzProjectLibs project) {
        return hzProjectLibsDao.insert(project) > 0 ? true : false;
    }

    /**
     * 根据主键删除项目
     *
     * @param puid 项目puid
     * @return
     */
    public boolean doDeleteByPuid(String puid) {
        return hzProjectLibsDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }
}
