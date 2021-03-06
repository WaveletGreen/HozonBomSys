package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;
import com.connor.hozon.resources.domain.dto.response.HzMbomRecordRespDTO;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/7/6
 * @Description:
 */
@Deprecated
public class ApplyMbomDataTOHzMaterielReqDTO extends BaseDTO {

    List<HzMbomRecordRespDTO> mbomRecordRespDTOS;
    /**
     * 类型（1 半成品工艺路线 2整车工艺路线  3总成分总成工艺路线  ）
     */
    private Integer type;
    /**
     * 项目id
     */
    private String projectId;

    public List<HzMbomRecordRespDTO> getMbomRecordRespDTOS() {
        return mbomRecordRespDTOS;
    }

    public void setMbomRecordRespDTOS(List<HzMbomRecordRespDTO> mbomRecordRespDTOS) {
        this.mbomRecordRespDTOS = mbomRecordRespDTOS;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
