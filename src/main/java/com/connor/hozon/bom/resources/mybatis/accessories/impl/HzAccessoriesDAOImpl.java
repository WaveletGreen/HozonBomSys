package com.connor.hozon.bom.resources.mybatis.accessories.impl;

import com.connor.hozon.bom.resources.dto.request.DeleteHzAccessoriesDTO;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.accessories.HzAccessoriesLib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/7/16
 * @Description:
 */
@Service("HzAccessoriesDAO")
public class HzAccessoriesDAOImpl  extends BaseSQLUtil implements HzAccessoriesDAO {
    @Override
    public int insert(HzAccessoriesLib accessoriesLib) {
        return super.insert("HzAccessoriesDAOImpl_insert",accessoriesLib);
    }

    @Override
    public int update(HzAccessoriesLib hzAccessoriesLib) {
        return super.update("HzAccessoriesDAOImpl_update",hzAccessoriesLib);
    }

    @Override
    public int deleteList(List<DeleteHzAccessoriesDTO> libs) {
        return super.update("HzAccessoriesDAOImpl_deleteList",libs);
    }

    @Override
    public List<HzAccessoriesLib> getHzAccessoriesLibs(String puid) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        return super.findForList("HzAccessoriesDAOImpl_getHzAccessoriesLibs",map);
    }

}
