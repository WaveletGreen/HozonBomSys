package com.connor.hozon.bom.resources.service.file;

import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadUpdataMbomService {
    /**
     * 超级MBOM导入excel数据
     * @param file
     * @return
     */
    OperateResultMessageRespDTO UploadMbomToDB(MultipartFile file, String projectId);
}
