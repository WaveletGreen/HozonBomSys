package com.connor.hozon.bom.resources.mybatis.resourcesLibrary.VPPSLibrary;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzVPPSLibraryQuery;
import sql.pojo.resourcesLibrary.VPPSLibrary.HzVPPSLibrary;
/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/4
 * Time: 17:10
 */
public interface HzVPPSLibraryDao {

    /**
     * 分页获取字典库的数据
     * @param query
     * @return
     */
    Page findVPPSLibraryToPage(HzVPPSLibraryQuery query);
    /**
     * 插入一条数据
     * @param library
     * @return
     */
    int insert (HzVPPSLibrary library);

    /**
     * 查询一条数据
     * @param puid
     * @return
     */
    HzVPPSLibrary findVPPSLibrary(String puid);

    /**
     * 编辑一条数据
     * @param library
     * @return
     */
    int update(HzVPPSLibrary library);

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    int delete(String puid);
}
