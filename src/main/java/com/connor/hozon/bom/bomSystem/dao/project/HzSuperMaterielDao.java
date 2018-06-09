package com.connor.hozon.bom.bomSystem.dao.project;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzSuperMateriel;
@Configuration
public interface HzSuperMaterielDao {

    int deleteByPrimaryKey(String puid);

    int insert(HzSuperMateriel record);

    HzSuperMateriel selectByPrimaryKey(@Param("puid") String puid);

    HzSuperMateriel selectByProjectPuid(@Param("projectPuid") String projectPuid);

    int updateByPrimaryKey(HzSuperMateriel record);
}