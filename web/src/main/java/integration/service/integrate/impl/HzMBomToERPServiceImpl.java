/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.service.integrate.impl;

import cn.net.connor.hozon.dao.dao.integration.HzMBomToERPDao;
import integration.service.integrate.HzMBomToERPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.integration.HzMBomToERPBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/5 15:37
 * @Modified By:
 */
@Service
public class HzMBomToERPServiceImpl implements HzMBomToERPService {
    @Autowired
    HzMBomToERPDao hzMBomToERPDao;
    /**
     * 根据项目UID和MBOM行的UID列表获取到一组记录
     *
     * @param projectUid 项目的UID
     * @param list       MBOM记录的UID列表
     * @return
     */
    @Override
    public List<HzMBomToERPBean> selectByUidOfBatch(String projectUid, List<String> list) {
        Map<String, Object> _param = new HashMap<>();
        _param.put("projectUid", projectUid);
        _param.put("flag", "T.P_MBOM_PUID");
        _param.put("list", list);
        return hzMBomToERPDao.selectByUidOfBatch(_param);

    }

    /**
     * 根据项目UID和MBOM行的父层UID列表获取一组父层的数据，需要从@{@link HzMBomToERPBean}中getParentUid获取到父层Uid
     *
     * @param projectUid 项目UID
     * @param list       父层UID列表
     * @return
     */
    @Override
    public List<HzMBomToERPBean> selectbyParentUidOfBatch(String projectUid, List<String> list) {
        Map<String, Object> _param = new HashMap<>();
        _param.put("projectUid", projectUid);
        _param.put("flag", "T.P_EBOM_PUID");
        _param.put("list", list);
        return hzMBomToERPDao.selectByUidOfBatch(_param);

    }

    /**
     * 根据项目UID，筛选2Y层或非2Y层或项目MBOM表下的所有数据
     *
     * @param projectUid 包含项目UID
     * @param is2Y       是否是2Y层，如果要所有，传0和1以外的值即可
     * @return
     */
    @Override
    public List<HzMBomToERPBean> selectByProjectUidWithCondition(String projectUid, Integer is2Y) {
        Map<String, Object> _param = new HashMap<>();
        _param.put("projectUid", projectUid);
        _param.put("is2Y", is2Y);
        return hzMBomToERPDao.selectByProjectUidWithCondition(_param);
    }
}
