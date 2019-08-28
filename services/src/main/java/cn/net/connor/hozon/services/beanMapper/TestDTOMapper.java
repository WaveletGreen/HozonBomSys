/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.beanMapper;

import cn.net.connor.hozon.dao.pojo.Test;
import cn.net.connor.hozon.services.request.TestRequestDTO;
import cn.net.connor.hozon.services.response.TestResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 将DTO对象和数据对象进行映射
 * <p>
 * 需要用@Mapper注解进行
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/29 9:43
 * @Modified By:
 */
@Mapper
public interface TestDTOMapper {
    //当前接口的实例
    TestDTOMapper INSTANCE = Mappers.getMapper(TestDTOMapper.class);

    //将DTO对象转成数据实体对象，如果字段一模一样，则会进行完全的匹配
    Test map(TestRequestDTO dto);

    //将数据对象转成DTO对象
    TestResponseDTO toResponse(Test test);


    /**
     * 将采用源属性和目标属性进行对应
     */
    @Mapping(source = "name", target = "dtaName")
    @Mapping(target = "name", ignore = true)//忽略目标映射对象的字段，否则如果有同名的字段会自动映射上去
    TestResponseDTO toResponseSp(Test test);


    Test maps(TestRequestDTO dto, Long id, String name);

    /**
     * 默认接口方法进行针对的属性映射，每一个属性都从DTO对象与实体对象进行映射
     * @param test
     * @return
     */
    default Test maps(TestRequestDTO test) {
        return INSTANCE.maps(test, test.getId(), test.getName());
    }

}
