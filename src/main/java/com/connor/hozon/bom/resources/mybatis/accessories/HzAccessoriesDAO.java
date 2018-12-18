package com.connor.hozon.bom.resources.mybatis.accessories;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzAccessoriesDTO;
import com.connor.hozon.bom.resources.domain.query.HzAccessoriesPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.accessories.HzAccessoriesLib;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/7/16
 * @Description: 废除 已不再使用
 *  新DAO层代码 替换为 {@link HzAccessoriesLibsDAO}
 */
@Deprecated
public interface HzAccessoriesDAO {
    int insert(HzAccessoriesLib accessoriesLib);

    int update(HzAccessoriesLib hzAccessoriesLib);

    int deleteList(List<DeleteHzAccessoriesDTO> libs);

    List<HzAccessoriesLib> getHzAccessoriesLibs(String puid);

    Page<HzAccessoriesLib> getHzAccessoriesByPage(HzAccessoriesPageQuery query);
}
