package com.connor.hozon.resources.service.epl;

import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeCode;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.resources.domain.dto.request.EditHzEPLReqDTO;
import com.connor.hozon.resources.domain.dto.response.HzEplRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.resources.page.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description:
 */
public interface HzEPLService {
    /**
     * 新增一个零件
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO addPartToEPL(EditHzEPLReqDTO reqDTO);

    /**
     * 修改零件信息
     * @param reqDTO
     * @return
     */
    WriteResultRespDTO updatePartFromEPLRecord(EditHzEPLReqDTO reqDTO);

    /**
     * 删除零件
     * @param ids
     * @return
     */
    WriteResultRespDTO deletePartFromEPLByIds(String ids);

    /**
     * 分页查询EPL清单
     * @param query
     * @return
     */
    Page<HzEplRespDTO> getEPLRecordByPage(HzEPLByPageQuery query);

    /**
     * 获取一条EPL信息
     * @param id
     * @return
     */
    HzEplRespDTO getEplById(Long id);

    /**
     * 获取法规件库编号
     * @return
     */
    List<HzLegislativeCode> getLegislativeCode();

    boolean partIdIsRepeat(Long id,String partId,String projectId);

    JSONObject excelImport(MultipartFile file, String projectPuid);
}
