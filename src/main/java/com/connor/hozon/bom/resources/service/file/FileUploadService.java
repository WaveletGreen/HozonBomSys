package com.connor.hozon.bom.resources.service.file;

import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
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
    OperateResultMessageRespDTO UploadEbomToDB(MultipartFile file,String projectId);
}
