package com.connor.hozon.bom.resources.mybatis.work;

import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.work.HzWorkCenter;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
public interface HzWorkCenterDAO {
    /**
     * 新增工作中心主数据
     * @param hzWorkCenter
     * @return
     */
    int insert(HzWorkCenter hzWorkCenter);

    /**
     * 编辑工艺中心主数据
     * @param hzWorkCenter
     * @return
     */
    int update(HzWorkCenter hzWorkCenter);

    /**
     * 查询工作中心主数据 分页
     * @param map
     * @return
     */
    Page findWorkCenterForPage(Map<String,Object> map);

    /**
     * 删除工作中心主数据
     * @param puid
     * @return
     */
    int delete(String puid);
}
