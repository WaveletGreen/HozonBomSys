/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.resourcesLibrary.dictionaryLibrary;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzDictionaryLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzDictionaryLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzDictionaryLibraryRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzDictionaryLibraryQuery;
import com.connor.hozon.bom.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.depository.dictionaryLibrary.HzDictionaryLibrary;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/30
 * Time: 15:02
 */
public interface HzDictionaryLibraryService {
    /**
     * 添加一条数据
     *
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO insertHzDictionaryLibrary(AddHzDictionaryLibraryReqDTO reqDTO);

    /**
     * 修改一条数据
     *
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateHzDictionaryLibrary(UpdateHzDictionaryLibraryReqDTO reqDTO);

    /**
     * 分页获取字典库数据
     *
     * @param query
     * @return
     */
    Page<HzDictionaryLibraryRespDTO> findHzDictionaryLibraryToPage(HzDictionaryLibraryQuery query);

    /**
     * 根据PUID查询一条数据
     *
     * @param puid
     * @return
     */
    HzDictionaryLibraryRespDTO findHzDictionaryLibraryByPuid(String puid);

    /**
     * 根据PUID删除一条数据
     *
     * @param puid
     * @return
     */
    WriteResultRespDTO deleteHzDictionaryLibrary(String puid);

    HzDictionaryLibrary queryLibraryDTOByCfgObject(String cfgObjectId);

}
