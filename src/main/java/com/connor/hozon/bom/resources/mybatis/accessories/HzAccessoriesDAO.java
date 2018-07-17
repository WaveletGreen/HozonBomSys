package com.connor.hozon.bom.resources.mybatis.accessories;

import com.connor.hozon.bom.resources.dto.request.DeleteHzAccessoriesDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzAccessoriesPageQuery;
import sql.pojo.accessories.HzAccessoriesLib;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/7/16
 * @Description:
 */
public interface HzAccessoriesDAO {
    int insert(HzAccessoriesLib accessoriesLib);

    int update(HzAccessoriesLib hzAccessoriesLib);

    int deleteList(List<DeleteHzAccessoriesDTO> libs);

    List<HzAccessoriesLib> getHzAccessoriesLibs(String puid);

    Page<HzAccessoriesLib> getHzAccessoriesByPage(HzAccessoriesPageQuery query);
}
