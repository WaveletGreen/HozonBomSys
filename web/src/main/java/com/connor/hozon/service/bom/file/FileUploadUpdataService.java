/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.file;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadUpdataService {
    /**
     * PBOM导入excel数据
     * @param file
     * @return
     */
    WriteResultRespDTO UploadPbomToDB(MultipartFile file, String projectId);
}
