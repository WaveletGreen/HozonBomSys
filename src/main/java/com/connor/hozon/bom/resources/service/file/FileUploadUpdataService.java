package com.connor.hozon.bom.resources.service.file;

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
