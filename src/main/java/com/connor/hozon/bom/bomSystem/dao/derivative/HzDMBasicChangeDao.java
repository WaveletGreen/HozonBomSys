/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.derivative;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import com.connor.hozon.bom.bomSystem.dto.cfg.compose.HzComposeDelDto;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.derivative.HzDMBasicChangeBean;

import java.util.List;

/**
 * @Author: zhuDB
 * @Description: 新版配置物料基础变更dao层
 * @Date: Created in 2018/11/9 10:49
 * @Modified By:
 */
public interface HzDMBasicChangeDao extends BasicDao<HzDMBasicChangeBean> {

    int insertList(List<HzDMBasicChangeBean> hzDMBasicChangeBeans);

    List<HzDMBasicChangeBean> selectByFormid(Long changeFromId);

    List<HzDMBasicChangeBean> selectBefor(Long formId);


    List<HzDMBasicChangeBean> selectAfter(Long formId);

    List<HzDMBasicChangeBean> selectLastByPuid(List<HzComposeDelDto> delDtos);
}