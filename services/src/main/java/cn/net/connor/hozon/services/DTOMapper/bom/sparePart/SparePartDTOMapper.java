/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.DTOMapper.bom.sparePart;

import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartPostDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 备件提交DTO对象转换成数据库实体对象
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/16 14:58
 * @Modified By:
 */
@Mapper
public interface SparePartDTOMapper {
    //当前接口的实例
    SparePartDTOMapper INSTANCE = Mappers.getMapper(SparePartDTOMapper.class);

    SparePartData DTOToBean(SparePartPostDTO dto);
    /**
     * 批量转换
     *
     * @param list
     * @return
     */
    List<SparePartData> DTOToBean(List<SparePartPostDTO> list);
}
