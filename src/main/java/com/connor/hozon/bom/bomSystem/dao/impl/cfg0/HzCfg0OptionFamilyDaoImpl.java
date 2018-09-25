package com.connor.hozon.bom.bomSystem.dao.impl.cfg0;

import com.connor.hozon.bom.bomSystem.dao.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.IBaseSQLUtil;
import sql.pojo.cfg.HzCfg0OptionFamily;

import java.util.List;
import java.util.Map;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/23
 * Time: 9:23
 */
@Service("hzCfg0OptionFamilyDao")
public class HzCfg0OptionFamilyDaoImpl extends BasicDaoImpl<HzCfg0OptionFamily> implements HzCfg0OptionFamilyDao {
    private final static HzCfg0OptionFamily FAMILY = new HzCfg0OptionFamily();

    public HzCfg0OptionFamilyDaoImpl() {
        clz = HzCfg0OptionFamilyDao.class;
    }

    @Override
    public List<HzCfg0OptionFamily> selectNameByMainId(String mainId) {
        return baseSQLUtil.executeQueryByPass(FAMILY, mainId, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao.selectNameByMainId");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param mainId 产品配置器的puid
     * @return 返回一组系统名称
     */
    @Override
    public List<HzCfg0OptionFamily> selectNameByMainId2(String mainId) {
        return baseSQLUtil.executeQueryByPass(FAMILY, mainId, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao.selectNameByMainId2");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param param
     * @return 返回一组系统名称
     */
    @Override
    public List<HzCfg0OptionFamily> selectNameByMap(Map<String, Object> param) {
        return baseSQLUtil.executeQueryByPass(FAMILY, param, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao.selectNameByMap");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param param
     * @return 返回一组系统名称
     */
    @Override
    public List<HzCfg0OptionFamily> selectForColorBluePrint(Map<String, Object> param) {
        return baseSQLUtil.executeQueryByPass(FAMILY, param, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao.selectForColorBluePrint");
    }


    @Override
    public HzCfg0OptionFamily selectByCodeAndDescWithMain(HzCfg0OptionFamily family) {
        return baseSQLUtil.executeQueryById(family, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao.selectByCodeAndDescWithMain");
    }

    /**
     * @param family 包含主配置UID，描述和配置代码
     * @return
     */
    @Override
    public List<HzCfg0OptionFamily> selectByCodeAndDescWithMain2(HzCfg0OptionFamily family) {
        return baseSQLUtil.executeQuery(family, "com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao.selectByCodeAndDescWithMain");
    }

}
