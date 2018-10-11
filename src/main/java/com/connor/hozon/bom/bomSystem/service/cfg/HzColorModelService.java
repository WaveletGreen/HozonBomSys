package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelColorDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.service.cfg.vwo.HzVwoManagerService;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.IHzColorModelService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ModelColor;
import sql.pojo.cfg.HzCfg0ModelColorDetail;

import java.util.List;

@Service("hzColorModelService")
public class HzColorModelService implements IHzColorModelService {
    @Autowired
    HzColorModelDao hzColorModelDao;

    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    @Override
    public int doDeleteByPrimaryKey(String puid) {
        return hzColorModelDao.deleteByPrimaryKey(puid);
    }

    /**
     * 插入
     *
     * @param record 颜色+配置对象
     * @return
     */
    @Override
    public int doInsert(HzCfg0ModelColorDetail record) {
        return hzColorModelDao.insert(record);
    }

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */
    @Override
    public HzCfg0ModelColorDetail doSelectByPrimaryKey(String puid) {
        return hzColorModelDao.selectByPrimaryKey(puid);
    }


    /**
     * 主键更新
     *
     * @param record 需要更新的对象
     * @return
     */
    @Override
    public int doUpdateByPrimaryKey(HzCfg0ModelColorDetail record) {
        return hzColorModelDao.updateByPrimaryKey(record);
    }

    /**
     * 根据颜色模型
     *
     * @param modelUid
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> doSelectByModelUid(String modelUid) {
        return hzColorModelDao.selectByModelUid(modelUid);
    }

    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> doSelectByModelUidWithColor(String modelUid) {
        return hzColorModelDao.selectByModelUidWithColor(modelUid);
    }

    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> doSelectByModelUidWithColor2(String modelUid) {
        return hzColorModelDao.selectByModelUidWithColor2(modelUid);
    }


    /**
     * 根据项目ID查找
     *
     * @param projectUid
     * @return
     */
    @Override
    public List<HzCfg0ModelColorDetail> doSelectByCfgMainUid(String projectUid) {
        return hzColorModelDao.selectByCfgMainUid(projectUid);
    }

    /**
     * 批量插入
     *
     * @param colorModels 颜色+配置记录集合
     * @return
     */
    @Override
    public int doInsertByBatch(List<HzCfg0ModelColorDetail> colorModels) {
        return hzColorModelDao.insertByBatch(colorModels);
    }

    /**
     * 根据颜色车型和配置更新颜色信息
     *
     * @param model 模型集合
     * @return
     */
    @Override
    public boolean doUpdateColorModelWithCfg(HzCfg0ModelColorDetail model) {
        return hzColorModelDao.updateColorModelWithCfg(model) > 0 ? true : false;
    }

}
