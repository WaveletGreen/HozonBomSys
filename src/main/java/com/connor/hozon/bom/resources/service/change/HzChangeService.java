package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description: 变更
 */
public interface HzChangeService {
    WriteResultRespDTO insertChangeOrderRecord();

    WriteResultRespDTO updateChangeOrderRecord();
}
