package com.connor.hozon.bom.interaction.service;

import com.connor.hozon.bom.interaction.dao.HzConfigBomColorDao;
import com.connor.hozon.bom.interaction.iservice.IHzConfigBomColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.pojo.interaction.HzConfigBomColorBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/19 12:26
 * @Modified By:
 */
@Configuration
public class HzConfigBomColorService implements IHzConfigBomColorService {
    @Autowired
    HzConfigBomColorDao hzConfigBomColorDao;

    /**
     * 根据某个2Y的主键和项目主键查找2Y的所有配色代码
     *
     * @param bomline    2Y主键
     * @param projectUid 项目主键
     * @return
     */
    @Override
    public List<HzConfigBomColorBean> doSelectBy2YUidWithProject(String bomline, String projectUid) {
        return hzConfigBomColorDao.selectBy2YUidWithProject(bomline, projectUid);
    }
}
