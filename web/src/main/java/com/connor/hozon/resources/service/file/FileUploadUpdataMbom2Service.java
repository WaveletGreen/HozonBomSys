package com.connor.hozon.resources.service.file;

import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadUpdataMbom2Service {
    /**
     * 生产MBOM导入excel数据
     * @param file
     * @return
     */
    WriteResultRespDTO UploadMbomToDB(MultipartFile file, String projectId);
}
