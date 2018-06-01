package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzProjectLibsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzProjectLibs;

import java.util.List;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/30 14:15
 *
 * Description: 项目对象信息服务层，用于获取到项目信息，目前项目信息在BOM系统没有必要进行增删
 *
 * ***********************************************************************************************************************/
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
}
