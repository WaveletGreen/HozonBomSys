/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.bomData.sparePart.impl;

import cn.net.connor.hozon.common.factory.SimpleResponseResultFactory;
import cn.net.connor.hozon.common.setting.CommonSetting;
import cn.net.connor.hozon.dao.basic.impl.DaoListExecutor;
import cn.net.connor.hozon.dao.dao.bom.sparePart.SparePartBomStructureDao;
import cn.net.connor.hozon.dao.dao.bom.sparePart.SparePartDataDao;
import cn.net.connor.hozon.dao.dao.bom.sparePart.SparePartOfProjectDao;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartBomStructure;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartOfProject;
import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import cn.net.connor.hozon.services.beanMapper.bom.sparePart.SparePartDTOMapper;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartPostDTO;
import com.alibaba.fastjson.JSONObject;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartBomQueryResponse;
import com.connor.hozon.service.bom.bomData.sparePart.SparePartsBomService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 备件BOM service层
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 9:10
 * @Modified By:
 */
@Service
@ConfigurationProperties(prefix = "sparePart")
@Transactional
public class SparePartsBomServiceImpl implements SparePartsBomService {
    @Autowired
    private SparePartOfProjectDao sparePartOfProjectDao;

    @Autowired
    SparePartDataDao sparePartDataDao;

    @Autowired
    SparePartBomStructureDao sparePartBomStructureDao;

    @Autowired
    DaoListExecutor daoListExecutor;
    @Setter
    private boolean addChildAllowPutToProject;

    /**
     * 分页查询出项目
     *
     * @param query
     * @return
     */
    public SparePartBomQueryResponse selectPageByProjectId(SparePartOfProjectQuery query) {
        int count = sparePartOfProjectDao.count(query);
        List<SparePartData> result = sparePartDataDao.selectPageByQuery(query);
        return new SparePartBomQueryResponse(count, result);
    }


    @Override
    public JSONObject saveSparePart(SparePartPostDTO data) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        SparePartData bean = mapper.DTOToBean(data);
        boolean success = sparePartDataDao.insert(bean) > 0 ? true : false;
        if (success) {
            SparePartOfProject partOfProject = new SparePartOfProject();
            partOfProject.setProjectId(data.getProjectId());
            partOfProject.setSparePartId(bean.getId());
            sparePartOfProjectDao.insert(partOfProject);
            return SimpleResponseResultFactory.createSuccessResult("添加备件数据成功!");
        }
        return SimpleResponseResultFactory.createErrorResult("添加备件数据失败");
    }

    @Override
    public JSONObject addSparePartChild(SparePartPostDTO data) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        SparePartData bean = mapper.DTOToBean(data);
        boolean success = sparePartDataDao.insert(bean) > 0 ? true : false;
        if (success) {
            SparePartBomStructure structure = new SparePartBomStructure();
            structure.setSparePartId(data.getParentId());
            structure.setSparePartChildId(bean.getId());
            sparePartBomStructureDao.insert(structure);
            if (addChildAllowPutToProject) {
                SparePartOfProject partOfProject = new SparePartOfProject();
                partOfProject.setProjectId(data.getProjectId());
                partOfProject.setSparePartId(bean.getId());
                sparePartOfProjectDao.insert(partOfProject);
            }
            return SimpleResponseResultFactory.createSuccessResult("添加备件数据成功!");
        }
        return SimpleResponseResultFactory.createErrorResult("添加备件数据失败");
    }

    @Override
    public JSONObject updateSparePart(SparePartPostDTO data) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        SparePartData bean = mapper.DTOToBean(data);
        boolean success = sparePartDataDao.updateByPrimaryKeySelective(bean) > 0 ? true : false;
        if (success) {
            return SimpleResponseResultFactory.createSuccessResult("添加备件数据成功!");
        }
        return SimpleResponseResultFactory.createErrorResult("添加备件数据失败");
    }

    @Override
    public JSONObject deleteList(List<SparePartPostDTO> data) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        List<SparePartData> beans = mapper.DTOToBean(data);
        boolean success = daoListExecutor.deleteList(beans.size(),CommonSetting.SQL_IN_PARAM_LIMIT,beans,sparePartDataDao)>0?true:false;
        if(success){
            return SimpleResponseResultFactory.createSuccessResult("删除备件数据成功!");
        }
        return SimpleResponseResultFactory.createErrorResult("删除备件数据失败");

    }
}
