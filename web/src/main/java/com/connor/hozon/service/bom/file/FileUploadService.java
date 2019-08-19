/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.file;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: haozt
 * @Date: 2018/8/22
 * @Description:
 */
public interface FileUploadService {
    /**
     * EBOM导入excel数据
     * @param file
     * @return
     */
    WriteResultRespDTO uploadEbomToDB(MultipartFile file, String projectId);
}
