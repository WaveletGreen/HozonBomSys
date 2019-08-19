/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.resourcesLibrary.VPPSLibrary;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzVPPSLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzVPPSLibraryRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzVPPSLibraryQuery;
import com.connor.hozon.bom.resources.page.Page;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/4
 * Time: 19:47
 */
public interface HzVPPSLibraryService {
    /**
     * 分页获取VPPS库的数据
     * @param query
     * @return
     */
    Page<HzVPPSLibraryRespDTO> findHzVPPSLibraryToPage(HzVPPSLibraryQuery query);

    /**
     * 添加一条数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO insertHzVPPSLibrary(AddHzVPPSLibraryReqDTO reqDTO);

    /**
     * 根据puid查询一条数据
     * @param puid
     * @return
     */
    HzVPPSLibraryRespDTO findHzVPPSLibraryById (String puid);

    /**
     * 编辑 一条数据
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updateHzVPPSLibrary(AddHzVPPSLibraryReqDTO reqDTO);

    /**
     * 根据puid删除一条数据
     * @param puid
     * @return
     */
    WriteResultRespDTO deleteHzVPPSLibrary (String puid);
}
