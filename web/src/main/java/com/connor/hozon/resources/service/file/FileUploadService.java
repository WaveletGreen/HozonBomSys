package com.connor.hozon.resources.service.file;

import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
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
