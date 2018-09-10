package com.connor.hozon.bom.resources.mybatis.resourcesLibrary.dictionaryLibrary;

import com.connor.hozon.bom.resources.domain.query.HzDictionaryLibraryQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.resourcesLibrary.dictionaryLibrary.HzDictionaryLibrary;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/29
 * Time: 16:09
 */

public interface HzDictionaryLibraryDao {
    /**
     * 插入一条数据
     * @param library
     * @return
     */
    int insert (HzDictionaryLibrary library);

    /**
     * 编辑一条数据
     * @param library
     * @return
     */
    int update(HzDictionaryLibrary library);

    /**
     * 分页获取字典库的数据
     * @param query
     * @return
     */
    Page findDictionaryLibraryToPage(HzDictionaryLibraryQuery query);

    /**
     * 查询一条数据
     * @param puid
     * @return
     */
    HzDictionaryLibrary findDictionaryLibrary(String puid);

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    int delete(String puid);

    /**
     * 根据特性值查条数
     * @param eigenValue
     * @return
     */
    int findDictionaryLibraryOrCodeToCount(String eigenValue);

    /**
     * 根据特性值查询 一条数据
     * @param eigenValue
     * @return
     */
    HzDictionaryLibrary findDictionaryLibraryOrCode(String eigenValue);
}
