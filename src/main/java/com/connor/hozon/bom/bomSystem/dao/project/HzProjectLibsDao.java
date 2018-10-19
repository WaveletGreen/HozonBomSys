package com.connor.hozon.bom.bomSystem.dao.project;

import java.util.List;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzProjectLibs;

@Configuration
public interface HzProjectLibsDao extends BasicDao<HzProjectLibs> {

    HzProjectLibs selectByPrimaryKey(@Param("puid") String puid);

    List<HzProjectLibs> selectAllProject();

    HzProjectLibs selectByProjectCode(@Param("pProjectCode") String pProjectCode);

    int deleteByPrimaryKey(@Param("puid") String puid);
}