package com.connor.hozon.bom.resources.domain.dto.response;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;
import com.connor.hozon.bom.resources.enumtype.TableNameToHyperLinkNameEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:
 */
@Data
public class HzChangeDataRespDTO extends BaseDTO {
    /**
     * 名称
     */
    private String name;
//    /**
//     * 申请人
//     */
//    private String applicant;
//    /**
//     * 审核人
//     */
//    private String auditor;
//    /**
//     * 申请时间
//     */
//    private Date applicantTime;

    /**
     * 表单id
     */
    private Long orderId;

    /**
     * 状态值 1有 0 无
     */
    private Integer status;
//    /**
//     * 审核人id
//     */
//    private Long auditorId;

    /**
     * 初始化数据
     */
    public List<HzChangeDataRespDTO>  init(){
        List<HzChangeDataRespDTO> list = new ArrayList<>();
        for(TableNameToHyperLinkNameEnum t: TableNameToHyperLinkNameEnum.values()) {
            HzChangeDataRespDTO respDTO = new HzChangeDataRespDTO();
            respDTO.setStatus(0);
            respDTO.setName(t.getLinkName());
            list.add(respDTO);
        }
        return list;
    }
}
