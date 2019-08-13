package com.connor.hozon.bom.sys.mapper;


import com.connor.hozon.bom.sys.dto.TreeDTO;

import cn.net.connor.hozon.dao.pojo.sys.Tree;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19 0019.
 */
public interface TreeMapper {

    @Mapping(source = "id", target = "id")
    TreeDTO treeToTreeDTO(Tree entity);

    List<TreeDTO> treesToTressDTOs(List<Tree> treeList);

    TreeMapper INSTANCE = Mappers.getMapper(TreeMapper.class);

}
