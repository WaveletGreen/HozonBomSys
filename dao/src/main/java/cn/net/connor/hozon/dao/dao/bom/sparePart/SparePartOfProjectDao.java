/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.bom.sparePart;

import cn.net.connor.hozon.dao.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartOfProject;
import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 项目的备件零件dao层
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 13:52
 * @Modified By:
 */
@Repository
public interface SparePartOfProjectDao extends MyBatisBaseDao<SparePartOfProject, Long, SparePartOfProjectQuery> {
    /**
     * 根据项目ID查询出当前项目下的所有备件零件
     *
     * @param pageQuery
     * @return
     */
    List<SparePartOfProject> selectAllByProjectId(SparePartOfProjectQuery pageQuery);

    /**
     * 查询总数
     * @param query
     * @return
     */
    int count(SparePartOfProjectQuery query);
}