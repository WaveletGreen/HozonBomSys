package com.connor.hozon.bom.interaction.service;

import com.connor.hozon.bom.interaction.dao.HzConfigBomColorDao;
import com.connor.hozon.bom.interaction.iservice.IHzConfigBomColorService;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesLibsDAO;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.pojo.accessories.HzAccessoriesLibs;
import sql.pojo.interaction.HzConfigBomColorBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/19 12:26
 * @Modified By:
 */
@Configuration
public class HzConfigBomColorService implements IHzConfigBomColorService {

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
    public List<HzConfigBomColorBean> doSelectBy2YUidWithProject(String bomline, String projectUid) {
        List<HzConfigBomColorBean> list = hzConfigBomColorDao.selectBy2YUidWithProject(bomline,projectUid);

        if(ListUtil.isNotEmpty(list)){
            list.forEach(l->{
                if(StringUtils.isNotBlank(l.getMaterielCodes())){
                    List<String> materielCodes = Lists.newArrayList(l.getMaterielCodes().split("<br>"));
                    List<HzAccessoriesLibs> libs = hzAccessoriesLibsDAO.queryAccessoriesByMaterielCodes(materielCodes);
                    if(ListUtil.isNotEmpty(libs)){
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
