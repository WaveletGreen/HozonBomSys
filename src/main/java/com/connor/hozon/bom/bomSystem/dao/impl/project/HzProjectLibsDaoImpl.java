package com.connor.hozon.bom.bomSystem.dao.impl.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzProjectLibsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.project.HzProjectLibs;

import java.util.List;

/**
 * Author: Fancyears·Maylos·Mayways
 * Date: 2018/5/30
 * Time: 14:10
 * Description:
 */
@Service("hzProjectLibsDao")
public class HzProjectLibsDaoImpl implements HzProjectLibsDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据项目的puid找到该项目信息
     * Date: 2018/5/30 14:13
     *
     * @param puid 项目的puid
     * @return 找到则返回项目对象，否则返回null
     */
    @Override
    public HzProjectLibs selectByPrimaryKey(String puid) {
        HzProjectLibs libs = new HzProjectLibs();
        libs.setPuid(puid);
        return baseSQLUtil.executeQueryById(libs, "com.connor.hozon.bom.bomSystem.dao.project.HzProjectLibsDao.selectByPrimaryKey");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description:
     * Date: 2018/5/30 14:14
     *
     * @return 找到所有的项目信息
     */
    @Override
    public List<HzProjectLibs> selectAllProject() {
        return baseSQLUtil.executeQuery(new HzProjectLibs(), "com.connor.hozon.bom.bomSystem.dao.project.HzProjectLibsDao.selectAllProject");
    }
}
