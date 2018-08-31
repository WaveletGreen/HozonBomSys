package com.connor.hozon.bom.resources.service.resourcesLibrary.dictionaryLibrary;

import com.connor.hozon.bom.resources.dto.request.AddHzDictionaryLibraryReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzDictionaryLibraryRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzDictionaryLibraryQuery;
import sql.pojo.resourcesLibrary.dictionaryLibrary.HzDictionaryLibrary;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/30
 * Time: 15:02
 */
public interface HzDictionaryLibraryService {
    /**
     * 添加一条数据
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO insertHzDictionaryLibrary(AddHzDictionaryLibraryReqDTO reqDTO);

    /**
     * 修改一条数据
     * @param reqDTO
     * @return
     */
    OperateResultMessageRespDTO updateHzDictionaryLibrary(AddHzDictionaryLibraryReqDTO reqDTO);

    /**
     * 分页获取字典库数据
     * @param query
     * @return
     */
    Page<HzDictionaryLibraryRespDTO> findHzDictionaryLibraryToPage(HzDictionaryLibraryQuery query);

    /**
     * 根据PUID查询一条数据
     * @param puid
     * @return
     */
    HzDictionaryLibraryRespDTO findHzDictionaryLibraryByPuid(String puid);

    /**
     * 根据PUID删除一条数据
     * @param puid
     * @return
     */
    OperateResultMessageRespDTO deleteHzDictionaryLibrary(String puid);
}
