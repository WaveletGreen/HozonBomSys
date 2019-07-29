/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.depository.project;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import org.apache.ibatis.annotations.Param;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;
import org.springframework.stereotype.Repository;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 超级物料dao，用物料dao层代替
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzSuperMaterielDao extends BasicDao<HzMaterielRecord> {

    int deleteByPrimaryKey(String puid);

    HzMaterielRecord selectByPrimaryKey(@Param("puid") String puid);

    HzMaterielRecord selectByProjectPuid(@Param("projectPuid") String projectPuid);


}