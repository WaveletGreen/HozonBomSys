package com.connor.hozon.bom.resources.domain.model;

import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeItem;
import cn.net.connor.hozon.dao.pojo.sys.User;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzLegislativeReqDTO;

import java.util.Date;
import java.util.UUID;

/**
 * @Author: zhangl
 * @Date: 2019/8/22
 * @Description:
 */
public class HzLegislativeFactory {
    /**
     * 添加DTO信息转Dao信息
     * @param reqDTO
     * @return
     */
    public static HzLegislativeItem addLegReqDTOToRecord(AddHzLegislativeReqDTO reqDTO){
        HzLegislativeItem record = new HzLegislativeItem();
        record.setPuid(UUID.randomUUID().toString());
        record.setLegislativeName(reqDTO.getLegislativeName());
        record.setLegislativeNo(reqDTO.getLegislativeNo());
        record.setNoticeNo(reqDTO.getNoticeNo());
        record.setApplicableModels(reqDTO.getApplicableModels());
        record.setTechnologyDesc(reqDTO.getTechnologyDesc());
        record.setApplyDepa(reqDTO.getApplyDepa());
        if ("Y".equals(reqDTO.getIsHaveTest())){
            record.setIsHaveTest(1);
        }else if ("N".equals(reqDTO.getIsHaveTest())){
            record.setIsHaveTest(0);
        }
        record.setRemarks(reqDTO.getRemarks());
        record.setInsertTime(new Date());
        record.setStatus(1);

        return record;
    }
    

}
