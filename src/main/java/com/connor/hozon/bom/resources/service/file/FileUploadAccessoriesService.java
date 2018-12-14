package com.connor.hozon.bom.resources.service.file;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/12/12
 * Time: 13:50
 */
public interface FileUploadAccessoriesService {
    /**
     * 工艺辅料的导入功能
     * @param file
     * @return
     */
    WriteResultRespDTO uploadAccessoriesToDB(MultipartFile file);
}
