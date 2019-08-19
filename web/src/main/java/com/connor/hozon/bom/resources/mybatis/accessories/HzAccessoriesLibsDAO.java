/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.accessories;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzAccessoriesLibsDTO;
import com.connor.hozon.bom.resources.domain.query.HzAccessoriesLibsPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.depository.accessories.HzAccessoriesLibs;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/27
 * Time: 15:12
 */
public interface HzAccessoriesLibsDAO {

    int insert(HzAccessoriesLibs accessoriesLibs);

    int importList(List<HzAccessoriesLibs> records);

    int update(HzAccessoriesLibs hzAccessoriesLibs);

    int deleteList(List<DeleteHzAccessoriesLibsDTO> libs);

    /**
     * 批量更新 根据物料号更新数据
     * @param libs
     * @return
     */
    int updateList(List<HzAccessoriesLibs> libs);

    List<HzAccessoriesLibs> getHzAccessoriesLibs(String puid);

    Page<HzAccessoriesLibs> getHzAccessoriesByPage(HzAccessoriesLibsPageQuery query);

    HzAccessoriesLibs queryAccessoriesByMaterielCode(String materielCode);

    int selectHzAccessoriesLibsByCount(String pMaterielCode);

    HzAccessoriesLibs getHzAccessoriesLibsByCode(String pMaterielCode);

    List<String> queryAccessoriesListByMaterielCode(List<String> materielCodeList);

    /**
     * 根据物料号查询对应
     * @param materielCodes
     * @return
     */
    List<HzAccessoriesLibs> queryAccessoriesByMaterielCodes(List<String> materielCodes);

    List<HzAccessoriesLibs> queryAccessoriesByCode(String materielCode);
}
