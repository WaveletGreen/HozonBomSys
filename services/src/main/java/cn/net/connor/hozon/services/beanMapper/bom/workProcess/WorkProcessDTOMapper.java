/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.beanMapper.bom.workProcess;

import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkProcedure;
import cn.net.connor.hozon.services.request.bom.workProcess.WorkProcessDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/6 15:48
 * @Modified By:
 */
@Mapper
public interface WorkProcessDTOMapper {
    //当前接口的实例
    WorkProcessDTOMapper INSTANCE = Mappers.getMapper(WorkProcessDTOMapper.class);
    @Mappings({
            @Mapping(source = "pWorkCode",target = "workCenterCode"),
            @Mapping(source = "pWorkDesc",target = "workCenterDesc"),
    })
    HzWorkProcedure DTOToBean(WorkProcessDTO dto);

    /**
     * 批量转换
     * @param list
     * @return
     */
    List<HzWorkProcedure> DTOToBean(List<WorkProcessDTO> list);
}
