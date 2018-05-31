package com.connor.hozon.bom.bomSystem.dao.project;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzProjectLibs;

@Configuration
public interface HzProjectLibsDao {

    HzProjectLibs selectByPrimaryKey(String puid);

    List<HzProjectLibs> selectAllProject();
}