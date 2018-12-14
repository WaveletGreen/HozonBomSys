package com.connor.hozon.bom.resources.mybatis.accessories;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzAccessoriesLibsDTO;
import com.connor.hozon.bom.resources.domain.query.HzAccessoriesLibsPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.accessories.HzAccessoriesLibs;
import sql.pojo.accessories.HzImportAccessoriesLibs;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/27
 * Time: 15:12
 */
public interface HzAccessoriesLibsDAO {

    int insert(HzAccessoriesLibs accessoriesLibs);

    int importList(List<HzImportAccessoriesLibs> records);

    int update(HzAccessoriesLibs hzAccessoriesLibs);

    int deleteList(List<DeleteHzAccessoriesLibsDTO> libs);

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
}
