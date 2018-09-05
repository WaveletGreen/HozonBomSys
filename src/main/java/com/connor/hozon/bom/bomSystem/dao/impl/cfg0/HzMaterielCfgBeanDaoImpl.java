package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzMaterielCfgBeanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzMaterielCfgBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/8 14:44
 * @Modified By:
 */
@Configuration
public class HzMaterielCfgBeanDaoImpl implements HzMaterielCfgBeanDao {
    @Autowired
    IBaseSQLUtil baseSQLUtil;
    @Override
    public List<HzMaterielCfgBean> selectByDiff(HzMaterielCfgBean bean) {
        return baseSQLUtil.executeQuery(bean,"com.connor.hozon.bom.bomSystem.dao.cfg.HzMaterielCfgBeanDao.selectByDiff");
    }

    @Override
    public int updateIsSent(Map<String, Object> map) {
        return baseSQLUtil.executeUpdate(map , "com.connor.hozon.bom.bomSystem.dao.cfg.HzMaterielCfgBeanDao.updateIsSent");
    }

}
