package com.connor.hozon.bom.interaction.iservice;

import org.springframework.stereotype.Service;
import sql.pojo.interaction.HzConfigBomColorBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/19 12:26
 * @Modified By:
 */
@Service("iHzConfigBomColorService")
public interface IHzConfigBomColorService {
    /**
     * 根据某个2Y的主键和项目主键查找2Y的所有配色代码
     *
     * @param bomline    2Y主键
     * @param projectUid 项目主键
     * @param paintFlag 是否为油漆车身
     * @return
     */
    List<HzConfigBomColorBean> doSelectBy2YUidWithProject(String bomline, String projectUid,boolean paintFlag);
}
