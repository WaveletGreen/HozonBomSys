package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzProjectLibsDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzProjectLibs;

import java.util.List;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

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
                || null == project.getpProjectPertainToVehicle() || "".equals(project.getpProjectPertainToVehicle())
                ) {
            return false;
        } else return true;
    }

    /**
     * 修改检查
     * 不修改平台信息，所以不用检查平台
     * <p>
     * 分别验证项目代号，项目名称
     *
     * @param project 项目对象
     * @return
     */
    public boolean modifyValidate(HzProjectLibs project) {
        //项目编号和项目名称不能为空
        if (null == project.getpProjectCode() ||
                null == project.getpProjectName() ||
                "".equals(project.getpProjectCode()) ||
                "".equals(project.getpProjectName()) ||
                null == project.getPuid() ||
                "".equals(project.getPuid())
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

    public boolean doUpdateByPrimaryKey(HzProjectLibs project) {
        return hzProjectLibsDao.updateByPrimaryKey(project) > 0 ? true : false;
    }

    public void toDTO(HzProjectLibs project) {
    }

    /**
     * 项目编号查重
     *
     * @param project 项目对象
     * @return
     */
    public JSONObject doValidateCodeWithPuid(IProject project) {
        JSONObject result = new JSONObject();
        result.put("valid", true);
        HzProjectLibs _project = null;
        //有puid，则时更新，没有则为新增
        if (checkString(project.getPuid())) {
            _project = doLoadProjectLibsById(project.getPuid());
            //根据puid查出来的同名，代表自身，则验证通过
            if (_project.getCode().trim().equals(project.getCode().trim())) {
                result.put("valid", true);
            } else if ((_project = doGetByProjectCode(project.getCode().trim())) != null) {
                //不是自身，更换了代号，检查是否有同代号的，有同代号则不允许验证通过
                result.put("valid", false);
            }
        } else if ((_project = doGetByProjectCode(project.getCode().trim())) != null) {
            result.put("valid", false);
        }
        return result;
    }
}
