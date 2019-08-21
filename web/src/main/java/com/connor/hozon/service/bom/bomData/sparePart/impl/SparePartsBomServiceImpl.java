/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.bomData.sparePart.impl;

import cn.net.connor.hozon.common.factory.SimpleResponseResultFactory;
import cn.net.connor.hozon.common.setting.CommonSetting;
import cn.net.connor.hozon.common.util.ListUtils;
import cn.net.connor.hozon.dao.basic.impl.DaoListExecutor;
import cn.net.connor.hozon.dao.dao.bom.sparePart.SparePartBomStructureDao;
import cn.net.connor.hozon.dao.dao.bom.sparePart.SparePartDataDao;
import cn.net.connor.hozon.dao.dao.bom.sparePart.SparePartOfProjectDao;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartBomStructure;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartOfProject;
import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import cn.net.connor.hozon.services.beanMapper.bom.sparePart.SparePartDTOMapper;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartOfProjectRequestQueryDTO;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartPostDTO;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartQuoteEbomLinesPostDTO;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartDataResponseDTO;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartQueryEbomPageResponseDTO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartBomQueryPageResponse;
import com.connor.hozon.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.bom.HzEBOMReadService;
import com.connor.hozon.service.bom.bomData.sparePart.SparePartStructureCache;
import com.connor.hozon.service.bom.bomData.sparePart.SparePartsBomService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

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
@Slf4j
public class SparePartsBomServiceImpl implements SparePartsBomService {
    /**
     * 项目下的备件零件对象dao
     */
    @Autowired
    private SparePartOfProjectDao sparePartOfProjectDao;
    /**
     * 备件零件对象dao
     */
    @Autowired
    SparePartDataDao sparePartDataDao;
    /**
     * 备件结构dao
     */
    @Autowired
    SparePartBomStructureDao sparePartBomStructureDao;
    /**
     * 批量操作工具类
     */
    @Autowired
    DaoListExecutor daoListExecutor;
    /**
     * 添加子层的同时是否添加到项目下
     */
    @Setter
    private boolean addChildAllowPutToProject;
    @Setter
    private boolean createWithChildren;

    /**
     * EBOM service
     */
    @Autowired
    private HzEBOMReadService hzEBOMReadService;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    /**
     * 分页查询出项目
     * 先将controller的查询对象转成dao层的查询对象再进行查询
     * <p>
     * 查询完成后，将dao层的对象再转成controller层使用的对象
     *
     * @param query controller和service连接对象
     * @return
     */
    public SparePartBomQueryPageResponse selectPageByProjectId(SparePartOfProjectRequestQueryDTO query) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        SparePartOfProjectQuery queryBean = mapper.DTOToQueryBean(query);//转成dao层对象进行查询，从这里就需要拆分，不同的层级对象工作职责不一样
        int count = sparePartOfProjectDao.countByQuery(queryBean);
        List<SparePartData> queryResult = sparePartDataDao.selectPageByQuery(queryBean);
        List<SparePartDataResponseDTO> result = mapper.BeanToDTO(queryResult);//再转成service层的对象
        return new SparePartBomQueryPageResponse(count, result);
    }


    @Override
    public JSONObject saveSparePart(SparePartPostDTO data) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        SparePartData bean = mapper.DTOToBean(data);//post请求提交的对象转成dao层对象
        boolean success = sparePartDataDao.insert(bean) > 0 ? true : false;
        if (success) {
            SparePartOfProject partOfProject = new SparePartOfProject();
            partOfProject.setProjectId(data.getProjectId());//设置项目ID
            partOfProject.setSparePartId(bean.getId());//设置零件ID
            sparePartOfProjectDao.insert(partOfProject);
            log.info("保存单条备件信息成功:" + bean.getSparePartCode());
            return SimpleResponseResultFactory.createSuccessResult("添加备件数据成功!");
        }
        return SimpleResponseResultFactory.createErrorResult("添加备件数据失败");
    }

    @Override
    public JSONObject addSparePartChild(SparePartPostDTO data) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        SparePartData bean = mapper.DTOToBean(data);//post请求提交的对象转成dao层对象
        boolean success = sparePartDataDao.insert(bean) > 0 ? true : false;
        if (success) {
            SparePartBomStructure structure = new SparePartBomStructure();
            structure.setSparePartId(data.getParentId());//设置父层ID
            structure.setSparePartChildId(bean.getId());//设置子层ID
            sparePartBomStructureDao.insert(structure);
            if (addChildAllowPutToProject) {//追加到当前项目下形成平铺结构
                SparePartOfProject partOfProject = new SparePartOfProject();
                partOfProject.setProjectId(data.getProjectId());//设置项目ID
                partOfProject.setSparePartId(bean.getId());//设置零件ID
                sparePartOfProjectDao.insert(partOfProject);
            }
            log.info("添加单条子备件信息成功:" + bean.getSparePartCode());
            return SimpleResponseResultFactory.createSuccessResult("添加备件数据成功!");
        }
        return SimpleResponseResultFactory.createErrorResult("添加备件数据失败");
    }

    @Override
    public JSONObject updateSparePart(SparePartPostDTO data) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        SparePartData bean = mapper.DTOToBean(data);//post请求提交的对象转成dao层对象
        boolean success = sparePartDataDao.updateByPrimaryKeySelective(bean) > 0 ? true : false;
        if (success) {
            log.info("更新单条子备件信息成功:" + bean.getSparePartCode());
            return SimpleResponseResultFactory.createSuccessResult("更新备件数据成功!");
        }
        return SimpleResponseResultFactory.createErrorResult("更新备件数据失败");
    }

    @Override
    public JSONObject deleteList(List<SparePartPostDTO> data) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        List<SparePartData> beans = mapper.DTOToBean(data);//post请求提交的对象转成dao层对象
        boolean success = daoListExecutor.deleteList(beans.size(), CommonSetting.SQL_IN_PARAM_LIMIT, beans, sparePartDataDao) > 0 ? true : false;
        List<HzEPLManageRecord> updateList = new LinkedList<>();
        if (success) {
            for (SparePartData _d : beans) {
                //过滤没有关联的EBOM信息
                if (StringUtils.isNotEmpty(_d.getRelEbomLineId())) {
                    HzEPLManageRecord record = new HzEPLManageRecord();
                    record.setPuid(_d.getRelEbomLineId());
                    record.setSparePart("");//清空备件信息
                    record.setSparePartNum("");//清空备件信息
                    updateList.add(record);
                }
            }
            //执行批量更新
            hzEbomRecordDAO.updateListByPuids(updateList);
            log.info("删除:" + data.size() + "\t条备件信息成功，同步清空关联的EBOM备件信息:\t" + updateList.size());
            return SimpleResponseResultFactory.createSuccessResult("删除备件数据成功!");
        }
        return SimpleResponseResultFactory.createErrorResult("删除备件数据失败");
    }

    @Override
    public LinkedHashMap<String, String> createRelEbomTitle() {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("pBomLinePartName", "名称");
        tableTitle.put("level", "层级");
//        tableTitle.put("pBomOfWhichDept", "专业");
//        tableTitle.put("rank", "级别");
//        tableTitle.put("groupNum", "分组号");
//        tableTitle.put("lineNo", "查找编号");
//        tableTitle.put("pBomLinePartEnName", "英文名称");
//        tableTitle.put("pLouaFlag", "LOU/LOA");
//        tableTitle.put("pUnit", "单位");
//        tableTitle.put("pPictureNo", "图号");
//        tableTitle.put("pPictureSheet", "图幅");
//        tableTitle.put("pMaterialHigh", "料厚");
//        tableTitle.put("pMaterial1", "材料1");
//        tableTitle.put("pMaterial2", "材料2");
//        tableTitle.put("pMaterial3", "材料3");
//        tableTitle.put("pDensity", "密度");
//        tableTitle.put("pMaterialStandard", "材料标准");
//        tableTitle.put("pSurfaceTreat", "表面处理");
//        tableTitle.put("pTextureColorNum", "纹理编号/色彩编号");
//        tableTitle.put("pManuProcess", "制造工艺");
//        tableTitle.put("pSymmetry", "对称");
//        tableTitle.put("pImportance", "重要度");
//        tableTitle.put("pRegulationFlag", "是否法规件");
//        tableTitle.put("p3cpartFlag", "是否3C件");
//        tableTitle.put("pRegulationCode", "法规件型号");
//        tableTitle.put("pBwgBoxPart", "黑白灰匣子件");
//        tableTitle.put("pDevelopType", "开发类别");
//        tableTitle.put("pDataVersion", "数据版本");
//        tableTitle.put("pTargetWeight", "目标重量(kg)");
//        tableTitle.put("pFeatureWeight", "预估重量(kg)");
//        tableTitle.put("pActualWeight", "实际重量(kg)");
//        tableTitle.put("pFastener", "紧固件");
//        tableTitle.put("pFastenerStandard", "紧固件规格");
//        tableTitle.put("pFastenerLevel", "紧固件性能等级");
//        tableTitle.put("pTorque", "扭矩");
//        tableTitle.put("pDutyEngineer", "责任工程师");
//        tableTitle.put("pSupply", "供应商");
//        tableTitle.put("pSupplyCode", "供应商代码");
//        tableTitle.put("pBuyEngineer", "采购工程师");
//        tableTitle.put("pRemark", "备注");
//        tableTitle.put("pBomLinePartClass", "零件分类");
//        tableTitle.put("pBomLinePartResource", "零件来源");
//        tableTitle.put("pInOutSideFlag", "内外饰标识");
//        tableTitle.put("pUpc", "UPC");
//        tableTitle.put("fna", "FNA");
//        tableTitle.put("pFnaDesc", "FNA描述");
//        tableTitle.put("number", "数量");
        tableTitle.put("sparePart", "备件");
        tableTitle.put("sparePartNum", "备件编号");
        tableTitle.put("colorPart", "是否颜色件");
//        tableTitle.put("effectTime", "生效时间");
        return tableTitle;
    }

    @Override
    public SparePartQueryEbomPageResponseDTO getEbomList(HzEbomByPageQuery query) {
        Page<HzEbomRespDTO> recordRespDTOPage = hzEBOMReadService.getHzEbomPage(query);
        SparePartQueryEbomPageResponseDTO response = new SparePartQueryEbomPageResponseDTO();
        if (recordRespDTOPage == null) {
            return response;
        }
        List<HzEbomRespDTO> recordRespDTOS = recordRespDTOPage.getResult();
        if (ListUtils.isEmpty(recordRespDTOS)) {
            return response;
        }
        List<JSONObject> list = new ArrayList<>();
        JSONArray array = recordRespDTOS.get(0).getJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = array.getJSONObject(i);
            list.add(o);
        }
        response.setTotalCount(recordRespDTOPage.getTotalCount());
        response.setResult(list);
        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public JSONObject quoteEbomLines(SparePartQuoteEbomLinesPostDTO dto) {
        //过滤的EBOM数据
        Map<String, HzEPLManageRecord> queryMap = new LinkedHashMap<>();
        //批量插入记录
        List<SparePartData> parts;
        //结构map
        Map<String, SparePartStructureCache> structureMap = new LinkedHashMap<>();
        //EBOM对象需要更新备件信息
        List<HzEPLManageRecord> eplUpdateSparePartList = new LinkedList<>();
        if (null != dto && null != dto.getIds() && dto.getIds().size() > 0) {
            String projectId = dto.getProjectId();//项目ID
            for (String id : dto.getIds()) {
                //构建查询对象
                HzEbomTreeQuery query = new HzEbomTreeQuery();
                query.setPuid(id);
                query.setProjectId(projectId);
                //查找当前BOM结构树
                List<HzEPLManageRecord> queryList = null;
                if (createWithChildren) {
                    queryList = hzEbomRecordDAO.getHzBomLineChildren(query);
                } else {
                    queryList = Collections.singletonList(hzEbomRecordDAO.findEbomById(id, projectId));
                }
                for (HzEPLManageRecord epl : queryList) {
                    if (queryMap.containsKey(epl.getPuid())) {
                        continue;//过滤掉重复的数据
                    } else {
                        queryMap.put(epl.getPuid(), epl);//存储当前EBOM对象数据
                    }
                }
            }
            parts = new ArrayList<>(queryMap.size() + 8);
            //构建结构并存储结构
            //进行汇总
            for (Map.Entry<String, HzEPLManageRecord> entry : queryMap.entrySet()) {
                HzEPLManageRecord record = entry.getValue();
                String ebomId = record.getPuid();
                //产生备件零件对象，有子层也可能是自身也是子层
                SparePartData part = createPart(record);
                //添加到批量插入记录中
                parts.add(part);
                //添加到更新记录中
                eplUpdateSparePartList.add(record);
                //有子层，一定不是最后一层
                if (null != record.getIsHas() && 1 == record.getIsHas()) {
                    structureMap.put(ebomId, new SparePartStructureCache(new LinkedList<>(), new LinkedList<>(), part));
                }
                //没有子层，或者是子层的最后一层
                String parentId = record.getParentUid();
                SparePartStructureCache cache = structureMap.get(parentId);
                if (cache != null) {
                    SparePartBomStructure structure = new SparePartBomStructure();
                    structure.setReserved1(parentId);//设置父层的ID
                    structure.setReserved2(record.getPuid());//设置子层的ID
                    cache.getStructures().add(structure);//记录结构数据
                    //然后将零件记录上去
                    cache.getParts().add(part);
                }
            }
            //保存数据
            savePartAndStructure(parts, structureMap, eplUpdateSparePartList, dto.getProjectId());
            return SimpleResponseResultFactory.createSuccessResult("引用EBOM数据成功");
        }
        return SimpleResponseResultFactory.createErrorResult("引用EBOM数据失败");
    }

    /**
     * 保存零件和结构数据
     *
     * @param parts
     * @param structureMap
     * @param eplUpdateSparePartList
     * @param projectId
     */
    @Transactional(rollbackFor = {Exception.class, SQLException.class})
    void savePartAndStructure(List<SparePartData> parts, Map<String, SparePartStructureCache> structureMap, List<HzEPLManageRecord> eplUpdateSparePartList, String projectId) {
        //批量插入不返回主键，所以需要一个个插入
        List<SparePartOfProject> projectParts = null;
        if (addChildAllowPutToProject) {
            projectParts = new LinkedList<>();
        }
        for (SparePartData part : parts) {
            //TODO 可以考虑先生成UUID，然后将备件零件数据批量插入，最后根据UUID批量查询出来获得所有的主键
            sparePartDataDao.insert(part);
            if (addChildAllowPutToProject) {
                SparePartOfProject partOfProject = new SparePartOfProject();
                partOfProject.setProjectId(projectId);
                partOfProject.setSparePartId(part.getId());
                projectParts.add(partOfProject);
            }
        }
        for (Map.Entry<String, SparePartStructureCache> entry : structureMap.entrySet()) {
            SparePartData parent = entry.getValue().getSelf();
            List<SparePartData> partList = entry.getValue().getParts();
            List<SparePartBomStructure> structureList = entry.getValue().getStructures();

            if (partList.size() > 0) {
                for (int i = 0; i < partList.size(); i++) {
                    structureList.get(i).setSparePartId(parent.getId());
                    structureList.get(i).setSparePartChildId(partList.get(i).getId());
                }
                sparePartBomStructureDao.insertList(structureList);
            }
        }
        //更新数据
        hzEbomRecordDAO.updateListByPuids(eplUpdateSparePartList);
        //是否保存到当前项目下
        if (addChildAllowPutToProject) {
            sparePartOfProjectDao.insertList(projectParts);
        }
    }

    /**
     * 根据EBOM对象创建备件对象对象
     *
     * @param record EBOM对象
     * @return
     */
    private SparePartData createPart(HzEPLManageRecord record) {

        SparePartData part = new SparePartData();
        //关联EBOM的主键
        part.setRelEbomLineId(record.getPuid());

        //层级
        Integer is2Y = record.getIs2Y();
        Integer hasChildren = record.getIsHas();
        String lineIndex = record.getLineIndex();
        String[] strings = HzBomSysFactory.getLevelAndRank(lineIndex, is2Y, hasChildren);


        //生产零件编号
        part.setProductivePartCode(record.getLineID());
        //生产零件名称
        part.setProductivePartName(record.getpBomLinePartName());
        //备件代码
        part.setSparePartCode(record.getLineID());
        //备件名称
        part.setSparePartName(record.getpBomLinePartName());


        //层级
        part.setHierarchy(strings[0]);
        //级别
        part.setLevel(strings[1]);
        //部门
        part.setDepartment(record.getpBomOfWhichDept());
        //供应商
        part.setSupplier(record.getpSupply());
        //是否备件
        part.setIsSparePart(1);//强制设为是备件
        //采购工程师
        part.setPurchasingEngineer(record.getpBuyEngineer());
        //责任工程师
        part.setResponsibleEngineer(record.getpDutyEngineer());
        //备注
        part.setRemark(record.getpRemark());
        //零件分类
        part.setPartClass(record.getpBomLinePartClass());
        //单位
        part.setUnit(record.getpUnit());
        //专业
        part.setMajor(record.getpBomOfWhichDept());
        //车间1

        //车间2
        //图号
        part.setDrawingNum(record.getpPictureNo());
        //然后将备件数据反向存储到EBOM身上
        record.setSparePart("Y");//是否备件
        record.setSparePartNum(part.getSparePartCode());//备件编号
        return part;
    }
}
