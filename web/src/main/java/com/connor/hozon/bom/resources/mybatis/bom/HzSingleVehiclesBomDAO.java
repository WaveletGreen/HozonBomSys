package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.domain.query.HzSingleVehiclesBomByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzSingleVehiclesBomRecord;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:单车BOM  DAO层
 */
public interface HzSingleVehiclesBomDAO {

    /**
     * 批量新增数据
     * @param records
     * @return
     */
    int insertList(List<HzSingleVehiclesBomRecord> records);

    /**
     * 根据项目id 删除数据
     * @param projectId
     * @return
     */
    int deleteByProjectId(String projectId);


    /**
     * 查询当前项目下单车BOM的全部的puid
     * @param projectId
     * @return
     */
    List<String> getAllPuidByProjectId(String projectId);
    /**
     * 分页查询单车BOM清单
     * @param query
     * @return
     */
    Page<HzSingleVehiclesBomRecord>getHzSingleVehiclesBomByPage(HzSingleVehiclesBomByPageQuery query);

    /**
     * 分页获取单车BOM树状结构
     * @param query
     * @return
     */
    Page<HzSingleVehiclesBomRecord> getHzSingleVehiclesBomTreeByPage(HzSingleVehiclesBomByPageQuery query);

}
