/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.file;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadUpdataMbom2Service {
    /**
     * 生产MBOM导入excel数据
     * @param file
     * @return
     */
    WriteResultRespDTO UploadMbomToDB(MultipartFile file, String projectId);
}
