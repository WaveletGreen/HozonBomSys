/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.interaction.service.impl;

import cn.net.connor.hozon.dao.dao.interaction.HzConfigBomColorDao;
import cn.net.connor.hozon.dao.pojo.depository.accessories.HzAccessoriesLibs;
import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomColorBean;
import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.bom.interaction.service.HzConfigBomColorService;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesLibsDAO;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/19 12:26
 * @Modified By:
 */
@Service
public class HzConfigBomColorServiceImpl implements HzConfigBomColorService {

     @Autowired
     private HzConfigBomColorDao hzConfigBomColorDao;
     @Autowired
     private HzAccessoriesLibsDAO hzAccessoriesLibsDAO;
    /**
     * 根据某个2Y的主键和项目主键查找2Y的所有配色代码
     *
     * @param bomline    2Y主键
     * @param projectUid 项目主键
     * @modify haozt 返回油漆物料信息
     * @return
     */
    @Override
    public List<HzConfigBomColorBean> doSelectBy2YUidWithProject(String bomline, String projectUid,boolean paintFlag) {
        List<HzConfigBomColorBean> list = null;
        if(paintFlag){
            list = hzConfigBomColorDao.selectPaintColorSet(projectUid);
        }else {
            list = hzConfigBomColorDao.selectBy2YUidWithProject(bomline,projectUid);
        }

        if(ListUtils.isNotEmpty(list)){// 添加油漆物料
            list.forEach(l->{
                if(StringUtils.isNotBlank(l.getMaterielCodes())){
                    List<String> materielCodes = Lists.newArrayList(l.getMaterielCodes().split("<br>"));
                    List<HzAccessoriesLibs> libs = hzAccessoriesLibsDAO.queryAccessoriesByMaterielCodes(materielCodes);
                    if(ListUtils.isNotEmpty(libs)){// 这里主要是为了取出油漆物料的名称和代码 名称在颜色库中没有存储 需要到辅料库中查询
                        l.setMaterielList(libs);
                    }else {
                        List<HzAccessoriesLibs> libsList = new ArrayList<>();
                        materielCodes.forEach(m->{
                            HzAccessoriesLibs libs1 = new HzAccessoriesLibs();
                            libs1.setpMaterielCode(m);
                            libsList.add(libs1);
                        });
                        l.setMaterielList(libsList);
                    }
                }
            });
        }
        return list;
    }
}
