/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.beanMapper.sys;

import cn.net.connor.hozon.dao.pojo.sys.Tree;
import cn.net.connor.hozon.services.response.sys.TreeDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-17T15:46:11+0800",
    comments = "version: 1.1.0.Final, compiler: javac, environment: Java 1.8.0_65 (Oracle Corporation)"
)
@Component
public class TreeMapperImpl implements TreeMapper {

    @Override
    public TreeDTO treeToTreeDTO(Tree entity) {
        if ( entity == null ) {
            return null;
        }

        TreeDTO treeDTO = new TreeDTO();

        treeDTO.setId( entity.getId() );
        treeDTO.setChecked( entity.isChecked() );
        treeDTO.setpId( entity.getpId() );
        treeDTO.setName( entity.getName() );
        treeDTO.setCode( entity.getCode() );
        treeDTO.setIcon( entity.getIcon() );
        treeDTO.setState( entity.getState() );
        treeDTO.setTreeOrder( entity.getTreeOrder() );

        return treeDTO;
    }

    @Override
    public List<TreeDTO> treesToTressDTOs(List<Tree> treeList) {
        if ( treeList == null ) {
            return null;
        }

        List<TreeDTO> list = new ArrayList<TreeDTO>();
        for ( Tree tree : treeList ) {
            list.add( treeToTreeDTO( tree ) );
        }

        return list;
    }
}
