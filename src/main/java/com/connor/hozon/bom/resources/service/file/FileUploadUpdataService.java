package com.connor.hozon.bom.resources.service.file;

import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadUpdataService {
    /**
     * PBOM导入excel数据
     * @param file
     * @return
     */
    OperateResultMessageRespDTO UploadPbomToDB(MultipartFile file, String projectId);
}
