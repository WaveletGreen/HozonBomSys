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
import cn.net.connor.hozon.dao.pojo.bom.epl.EbomWithPbomData;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartBomStructure;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartOfProject;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartStructureRecursion;
import cn.net.connor.hozon.dao.pojo.configuration.model.HzCfg0ModelRecord;
import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import cn.net.connor.hozon.services.beanMapper.bom.sparePart.SparePartDTOMapper;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartOfProjectRequestQueryDTO;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartPostDTO;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartQuoteEbomLinesPostDTO;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartDataResponseDTO;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartQueryEbomPageResponseDTO;
import cn.net.connor.hozon.services.service.configuration.fullConfigSheet.HzCfg0ModelService;
import cn.net.connor.hozon.services.service.configuration.fullConfigSheet.impl.HzCfg0ModelServiceImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartBomQueryPageResponse;
import com.connor.hozon.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.resources.domain.query.HzEbomRecursionQuery;
import com.connor.hozon.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.bom.HzEBOMReadService;
import com.connor.hozon.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.service.bom.bomData.sparePart.EbomStructureTree;
import com.connor.hozon.service.bom.bomData.sparePart.SparePartStructureCache;
import com.connor.hozon.service.bom.bomData.sparePart.SparePartsBomService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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
@PropertySource("classpath:sparePartSetting.properties")//指定属性文件路径
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
    private SparePartDataDao sparePartDataDao;
    /**
     * 备件结构dao
     */
    @Autowired
    private SparePartBomStructureDao sparePartBomStructureDao;
    /**
     * 批量操作工具类
     */
    @Autowired
    private DaoListExecutor daoListExecutor;
    /**
     * 添加子层的同时是否添加到项目下
     */


    /**
     * EBOM service
     */
    @Autowired
    private HzEBOMReadService hzEBOMReadService;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;
    /**
     * 单车service层
     */
    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzCfg0ModelService hzCfg0ModelService;

    @Setter
    private boolean addChildAllowPutToProject;
    @Setter
    private boolean createWithChildren;
    @Setter
    private boolean spDebug;

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
    public JSONObject selectRecursionByTopLayerId(SparePartPostDTO data) {
        SparePartDTOMapper mapper = SparePartDTOMapper.INSTANCE;
        SparePartData bean = mapper.DTOToBean(data);//post请求提交的对象转成dao层对象
        List<SparePartStructureRecursion> recu = sparePartBomStructureDao.selectRecursionByTopLayerId(bean.getId());
        if (recu!=null&&recu.size()>0) {
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
            //执行批量更新，不再进行同步更新数据，因为备件BOM和EBOM由不同人员维护，允许产生2套不同的数据
            //hzEbomRecordDAO.updateListByPuids(updateList);
            log.info("删除:" + data.size() + "\t条备件信息成功，同步清空关联的EBOM备件信息:\t" + updateList.size());
            return SimpleResponseResultFactory.createSuccessResult("删除备件数据成功!");
        }
        return SimpleResponseResultFactory.createErrorResult("删除备件数据失败");
    }

    @Override
    public LinkedHashMap<String, String> getVehicleUsageTitle(String projectId, String breakType) {
        //获取该项目下的所有车型模型
        List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(projectId);
        LinkedHashMap<String, String> map = new LinkedHashMap();
        //根据车型模型下
        if (ListUtils.isNotEmpty(hzCfg0ModelRecords)) {
            for (HzCfg0ModelRecord rec : hzCfg0ModelRecords) {
                String name = rec.getObjectName();
                String mng = rec.getModelBasicDetail();
                if (null == name || mng == null) {
                    continue;
                }
                String field = name + mng.replaceAll("[**]", "");//前端的字段
                String title = name + breakType + mng;//显示出来的title
                map.put(field, title);
            }
        }
        return map;
    }

    @Override
    public LinkedHashMap<String, String> createRelEbomTitle(String projectId) {
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
        tableTitle.putAll(hzSingleVehiclesServices.singleVehDosageTitle(projectId));
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
    @Deprecated
    public JSONObject quoteEbomLines(SparePartQuoteEbomLinesPostDTO dto) {
        //过滤的EBOM数据
        Map<String, EbomWithPbomData> queryMap = new LinkedHashMap<>();
        //批量插入记录
        List<SparePartData> parts;
        //结构map
        Map<String, SparePartStructureCache> structureMap = new LinkedHashMap<>();
        //EBOM对象需要更新备件信息，不再需要更新EBOM的信息，备件BOM和EBOM是否备件分人员维护数据
        List<EbomWithPbomData> eplUpdateSparePartList = null/*new LinkedList<>()*/;
        if (null != dto && null != dto.getIds() && dto.getIds().size() > 0) {
            String projectId = dto.getProjectId();//项目ID
            for (String id : dto.getIds()) {
                //构建查询对象
                HzEbomRecursionQuery query = new HzEbomRecursionQuery();
                query.setPuid(id);
                query.setProjectId(projectId);
                //查找当前BOM结构树
                List<EbomWithPbomData> queryList = null;
                if (createWithChildren) {
                    queryList = hzEbomRecordDAO.getEbomRecursionWithPbom(query);
                } else {
                    //强转就不会带上车间信息了也是头疼
                    queryList = Collections.singletonList((EbomWithPbomData)hzEbomRecordDAO.findEbomById(id, projectId));
                }
                for (EbomWithPbomData epl : queryList) {
                    if (queryMap.containsKey(epl.getPuid())) {
                        continue;//过滤掉重复的数据
                    } else {
                        queryMap.put(epl.getPuid(), epl);//存储当前EBOM对象数据
                    }
                }
            }
            parts = new ArrayList<>(queryMap.size() + 8);
            //备件代码和备件名称根据引用类型判断
            String[] pair = generateQuoteTypeToPartNumAndName(dto.getQuoteType());
            String sparePartAppendCode = pair[0];
            String sparePartAppendName = pair[1];
            //构建结构并存储结构
            //进行汇总
            for (Map.Entry<String, EbomWithPbomData> entry : queryMap.entrySet()) {
                EbomWithPbomData record = entry.getValue();
                String ebomId = record.getPuid();
                //产生备件零件对象，有子层也可能是自身也是子层
                SparePartData part = createPart(record, sparePartAppendCode, sparePartAppendName);
                //添加到批量插入记录中
                parts.add(part);
                //添加到更新记录中
                // eplUpdateSparePartList.add(record);
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

    @Override
    public JSONObject quoteEbomLinesUpAndDown(SparePartQuoteEbomLinesPostDTO dto) {
        //过滤的EBOM数据record.getSparePart();
        Map<String, EbomWithPbomData> queryMap = new LinkedHashMap<>();
        //批量插入记录
        List<SparePartData> parts;
        //结构map
        Map<String, SparePartStructureCache> structureMap = new LinkedHashMap<>();
        //EBOM对象需要更新备件信息，不再需要更新EBOM的信息，备件BOM和EBOM是否备件分人员维护数据
        List<EbomWithPbomData> eplUpdateSparePartList = null/*new LinkedList<>()*/;
        if (null != dto && null != dto.getIds() && dto.getIds().size() > 0) {
            String projectId = dto.getProjectId();//项目ID
            for (String id : dto.getIds()) {
                //构建查询对象
                HzEbomRecursionQuery query = new HzEbomRecursionQuery();
                query.setPuid(id);
                query.setProjectId(projectId);
                //查找当前BOM结构树
                List<EbomWithPbomData> tree = hzEbomRecordDAO.getCurrentTreeNodePathUpAndDown(query);
                for (EbomWithPbomData epl : tree) {
                    if (queryMap.containsKey(epl.getPuid())) {
                        continue;//过滤掉重复的数据，要么就放在tree里面
                    } else {
                        queryMap.put(epl.getPuid(), epl);//存储当前EBOM对象数据
                    }
                }
            }
            parts = new ArrayList<>(queryMap.size() + 8);
            //备件代码和备件名称根据引用类型判断
            String[] pair = generateQuoteTypeToPartNumAndName(dto.getQuoteType());
            String sparePartAppendCode = pair[0];
            String sparePartAppendName = pair[1];
            //构建结构并存储结构
            //进行汇总,绝对从2Y层进行遍历
            ArrayList<EbomWithPbomData> sourceList=new ArrayList<>(queryMap.values());
            /**
             *从后往前排,遇到不是备件的数据不记录，如果是备件的数据，先记录
             */

            for (int i=0;i<sourceList.size();i++) {

                EbomWithPbomData record = sourceList.get(i);//当前数据
                EbomWithPbomData pre = i==0?null:sourceList.get(i-1);//上一个数据


                //当前EBOM的主键
                String ebomId = record.getPuid();
                //产生备件零件对象，有子层也可能是自身也是子层
                SparePartData part = createPart(record, sparePartAppendCode, sparePartAppendName);
                //添加到批量插入记录中
                parts.add(part);
                //添加到更新记录中
                // eplUpdateSparePartList.add(record);
                //不是叶子节点
                if (null != record.getIsHas() && 1 == record.getIsHas()) {
                    structureMap.put(ebomId, new SparePartStructureCache(new LinkedList<>(), new LinkedList<>(), part));
                }
                String parentId = record.getParentUid();
                //获取到父层的索引map
                SparePartStructureCache cache = structureMap.get(parentId);
                //没有存储叶子
                if (cache != null) {
                    SparePartBomStructure structure = new SparePartBomStructure();
                    structure.setReserved1(parentId);//设置父层的ID
                    structure.setReserved2(record.getPuid());//设置子层的ID
                    cache.getStructures().add(structure);//记录结构数据
                    //然后将零件记录上去
                    cache.getParts().add(part);
                }
                //2Y层没有结构，则开始构建2Y层的tree
                else if(null!=record.getIs2Y()&&1==record.getIs2Y()){
                    //还原原来的EBOM结构树
                    EbomStructureTree tree=new EbomStructureTree();
                    tree.setPuid(record.getPuid());

                }
            }
            if(spDebug){
                return null;
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
    void savePartAndStructure(List<SparePartData> parts, Map<String, SparePartStructureCache> structureMap, List<EbomWithPbomData> eplUpdateSparePartList, String projectId) {
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
        //更新数据，不再进行同步更新数据，因为备件BOM和EBOM由不同人员维护，允许产生2套不同的数据
        //hzEbomRecordDAO.updateListByPuids(eplUpdateSparePartList);
        //是否保存到当前项目下
        if (addChildAllowPutToProject) {
            sparePartOfProjectDao.insertList(projectParts);
        }
    }

    /**
     * 根据EBOM对象创建备件对象对象
     *
     * @param record              EBOM对象
     * @param sparePartAppendCode 追加的备件代码
     * @param sparePartAppendName 追加的备件名称
     * @return
     */
    private SparePartData createPart(EbomWithPbomData record, String sparePartAppendCode, String sparePartAppendName) {

        SparePartData part = new SparePartData();
        //关联EBOM的主键
        part.setRelEbomLineId(record.getPuid());

        //层级
        Integer is2Y = record.getIs2Y();
        Integer hasChildren = record.getIsHas();
        String lineIndex = record.getLineIndex();
        String[] strings = HzBomSysFactory.getLevelAndRank(lineIndex, is2Y, hasChildren);


        //生产零件编号
        part.setProductivePartCode(record.getLineID().trim());
        //生产零件名称
        part.setProductivePartName(record.getpBomLinePartName().trim());

        //备件代码
        part.setSparePartCode((record.getLineID() + sparePartAppendCode).trim());
        //备件名称
        part.setSparePartName((record.getpBomLinePartName() + sparePartAppendName).trim());
        //层级
        part.setHierarchy(strings[0]);
        //级别
        part.setLevel(strings[1]);
        //部门
        part.setDepartment(record.getpBomOfWhichDept());
        //供应商
        part.setSupplier(record.getpSupply());
        //是否备件
        part.setIsSparePart("Y".equals(record.getSparePart()) ? 1 : "N".equals(record.getSparePart()) ? 0 : -1);//根据生成零件号判断是否是备件
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
        part.setWorkshop1(record.getWorkshop1());
        //车间2
        part.setWorkshop2(record.getWorkshop2());
        //图号
        part.setDrawingNum(record.getpPictureNo());
        //然后将备件数据反向存储到EBOM身上，也不需要反向写数据到EBOM上了
        //        record.setSparePart("Y");//是否备件
        //        record.setSparePartNum(part.getSparePartCode());//备件编号


        //单车用量
        String vehNum = record.getVehNum();
        if (null != vehNum && vehNum.contains("**")) {
            part.setReserved2(vehNum.replaceAll("[**]", ""));//去除所有的**，这里如果不去除，则只能在前端进行去除
        }

        return part;
    }

    public String[] generateQuoteTypeToPartNumAndName(String quoteType) {
        String[] pair = new String[2];
        if (StringUtils.isNotEmpty(quoteType)) {
            switch (quoteType) {
                case "DQ":
                    pair[0] = "-DQ";
                    pair[1] = "-底漆";
                    break;
                case "DY":
                    pair[0] = "-DY";
                    pair[1] = "-电泳";
                    break;
                default:
                    pair[0] = pair[1] = "";//全部置空字符串
            }
        } else {
            pair[0] = pair[1] = "";//全部置空字符串
        }
        return pair;
    }
}
