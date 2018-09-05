package com.connor.hozon.bom.bomSystem.dao.project;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzMaterielRecord;

import java.util.List;

@Configuration
public interface HzSuperMaterielDao {

    int deleteByPrimaryKey(String puid);

    int insert(HzMaterielRecord record);

    HzMaterielRecord selectByPrimaryKey(@Param("puid") String puid);

    HzMaterielRecord selectByProjectPuid(@Param("projectPuid") String projectPuid);

    int updateByPrimaryKey(HzMaterielRecord record);

}