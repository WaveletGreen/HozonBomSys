package com.connor.hozon.resources.service.file;

import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadUpdataService {
    /**
     * PBOM导入excel数据
     * @param file
     * @return
     */
    WriteResultRespDTO UploadPbomToDB(MultipartFile file, String projectId);
}
