package com.connor.hozon.bom.resources.service.bom.impl;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomMainRecordDao;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomLeveReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.bom.resources.domain.model.HzEbomRecordFactory;
import com.connor.hozon.bom.resources.domain.model.HzPbomRecordFactory;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.bom.resources.service.bom.HzEBOMReadService;
import com.connor.hozon.bom.resources.service.bom.HzEBOMWriteService;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import com.connor.hozon.bom.sys.exception.HzBomException;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.epl.HzEPLRecord;

import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/12/20
 * @Description:
 */
@Service("hzEBOMWriteService")
public class HzEBOMWriteServiceImpl implements HzEBOMWriteService {

    private HzEPLDAO hzEPLDAO;

    private HzPbomRecordDAO hzPbomRecordDAO;

    private HzEbomRecordDAO hzEbomRecordDAO;

    private HzBomMainRecordDao hzBomMainRecordDao;

    private HzMbomService hzMbomService;

    private TransactionTemplate configTransactionTemplate;

    private HzEBOMReadService hzEBOMReadService;


    /**关于bean依赖注入 这里推荐使用set方法注入或者使用构造器注入 避免使用变量的方式直接注入*/
    @Autowired
    public void setConfigTransactionTemplate(TransactionTemplate configTransactionTemplate) {
        this.configTransactionTemplate = configTransactionTemplate;
    }

    @Autowired
    public void setHzEPLDAO(HzEPLDAO hzEPLDAO) {
        this.hzEPLDAO = hzEPLDAO;
    }

    @Autowired
    public void setHzPbomRecordDAO(HzPbomRecordDAO hzPbomRecordDAO) {
        this.hzPbomRecordDAO = hzPbomRecordDAO;
    }

    @Autowired
    public void setHzEbomRecordDAO(HzEbomRecordDAO hzEbomRecordDAO) {
        this.hzEbomRecordDAO = hzEbomRecordDAO;
    }

    @Autowired
    public void setHzBomMainRecordDao(HzBomMainRecordDao hzBomMainRecordDao) {
        this.hzBomMainRecordDao = hzBomMainRecordDao;
    }

    @Autowired
    public void setHzMbomService(HzMbomService hzMbomService) {
        this.hzMbomService = hzMbomService;
    }

    @Autowired
    public void setHzEBOMReadService(HzEBOMReadService hzEBOMReadService) {
        this.hzEBOMReadService = hzEBOMReadService;
    }




    @Override
    public WriteResultRespDTO addHzEbomRecord(AddHzEbomReqDTO reqDTO) {
        /**
         * 大致业务逻辑
         * 1.新增EBOM时，需要到EPL中查找对应零件号是否存在，不存在直接return
         * 2.存在父层puid时，表示当前BOM插入到父层下面，不存在时，表示当前BOM为2Y层BOM
         * 3.EBOM中有数据插入，如果对应零部件信息的零件来源满足装车件，则需要给PBOM中插入对应数据
         * 4.有查找编号输入时，表示客户是有目的将BOM插入到现有BOM的某个位置，BOM的位置排序有sortNum决定
         * 5.无查找编号输入时，按照默认规则进行（插入到BOM结构层级下的最后一个位置）
         * 6.EBOM的零部件信息来源为EPL 零件库，BOM中只维护基本BOM属性
         */
        String parentId = reqDTO.getPuid(); //父层puid
        String lineId = reqDTO.getLineId();
        String projectId = reqDTO.getProjectId();
        if(StringUtils.isBlank(projectId)){
            return WriteResultRespDTO.failResultRespDTO("请选择项目！");
        }
        if(StringUtils.isBlank(lineId)){
            return WriteResultRespDTO.failResultRespDTO("零件号不能为空！");
        }
        try {
            HzEPLQuery hzEPLQuery = new HzEPLQuery();
            hzEPLQuery.setProjectId(reqDTO.getProjectId());
            hzEPLQuery.setPartId(reqDTO.getLineId());
            HzEPLRecord hzEPLRecord = hzEPLDAO.getEPLRecordById(hzEPLQuery);
            if(null == hzEPLRecord){
                return WriteResultRespDTO.failResultRespDTO("当前零件号EPL中不存在生效记录！");
            }
            HZBomMainRecord projectRecord = hzBomMainRecordDao.selectByProjectPuid(projectId);
            if(projectRecord == null){
                return WriteResultRespDTO.failResultRespDTO("当前项目不存在！");
            }

            String lineNo ="" ;//查找编号
            if (StringUtils.isNotBlank(reqDTO.getLineNo())) {
                lineNo = reqDTO.getLineNo().replaceFirst("^0*", "");
            }
            if(StringUtils.isBlank(parentId)){
                return insert2YBOMStructure(lineNo,projectId,reqDTO,hzEPLRecord);
            }else {
                return insertBOMIntoParentLevel(reqDTO,hzEPLRecord,parentId,projectId,lineNo);
            }

        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO) {
        return null;
    }

    @Override
    public WriteResultRespDTO extendsBomStructureInNewParent(UpdateHzEbomLeveReqDTO reqDTO) {
        return null;
    }

    @Override
    public WriteResultRespDTO deleteHzEbomRecordById(DeleteHzEbomReqDTO reqDTO) {
        //删除时 带子层进行删除 存在生效记录（版本号） 标记删除状态 否则直接删除 同步PBOM进行删除 逻辑类似
        //注：配置端如果关联 2Y层 则不允许进行删除
        if(StringUtils.isBlank(reqDTO.getPuids()) || StringUtils.isBlank(reqDTO.getProjectId())){
            return WriteResultRespDTO.IllgalArgument();
        }
        String puids = reqDTO.getPuids();
        String projectId = reqDTO.getProjectId();
        List<String> puidList = Lists.newArrayList(puids.split(","));

        try {
            //检查是否已关联特性
            Result result = hzEBOMReadService.checkConnectWithFeature(puidList,reqDTO.getProjectId());
            if(!result.isSuccess()){
                return WriteResultRespDTO.failResultRespDTO(result.getErrMsg());
            }

            List<String> effectList = new ArrayList<>();//改为删除状态
            List<String> deleteList = new ArrayList<>();//直接进行删除操作

            List<String> ebomHasChildrenPuids = new ArrayList<>();
            List<String> pbomHasChildrenPuids = new ArrayList<>();
            StringBuffer pbomDeleteBuffer = new StringBuffer();
            StringBuffer pbomUpdateBuffer = new StringBuffer();
            //找子 带子层删除 子层全部被删除后 更新父层的尾缀 如:3Y->2 其实就是更新isHas 字段
            if(ListUtil.isNotEmpty(puidList)){
                puidList.forEach(puid->{
                    boolean deleteFlag = true;
                    //EBOM 查找 将要删除记录
                    HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid,projectId);
                    if(record != null){
                        if(StringUtils.isNotBlank(record.getRevision())){
                            deleteFlag = false;
                        }
                        if(Integer.valueOf(1).equals(record.getIsHas())){
                            ebomHasChildrenPuids.add(record.getParentUid());
                            HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                            hzEbomTreeQuery.setPuid(puid);
                            hzEbomTreeQuery.setProjectId(projectId);
                            List<HzEPLManageRecord> list = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
                            if(ListUtil.isNotEmpty(list)){
                                if(deleteFlag){
                                    list.forEach(l->{
                                        deleteList.add(l.getPuid());
                                    });
                                }else {
                                    list.forEach(l->{
                                        effectList.add(l.getPuid());
                                    });
                                }

                            }
                        }

                        //PBOM 查找
                        HzPbomLineRecord pbomRecord = hzPbomRecordDAO.getHzPbomByEbomPuid(puid,projectId);
                        if(pbomRecord != null){
                            if(StringUtils.isNotBlank(pbomRecord.getRevision())){
                                deleteFlag = false;
                            }
                            if(Integer.valueOf(1).equals(pbomRecord.getIsHas())){
                                pbomHasChildrenPuids.add(pbomRecord.getParentUid());
                                HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                                hzPbomTreeQuery.setPuid(puid);
                                hzPbomTreeQuery.setProjectId(projectId);
                                List<HzPbomLineRecord> list = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                                if(ListUtil.isNotEmpty(list)){
                                    if(deleteFlag){
                                        list.forEach(l->{
                                            pbomDeleteBuffer.append(l.getPuid()+",");
                                        });
                                    }else {
                                        list.forEach(l->{
                                            pbomUpdateBuffer.append(l.getPuid()+",");
                                        });
                                    }
                                }
                            }
                        }
                    }
                });

            }

            //求差集 删除的记录 去除更新的记录
            deleteList.removeAll(effectList);

            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    if(ListUtil.isNotEmpty(deleteList)){
                        hzEbomRecordDAO.deleteByPuids(deleteList,ChangeTableNameEnum.HZ_EBOM.getTableName());
                    }
                    if(ListUtil.isNotEmpty(effectList)){
                        hzEbomRecordDAO.deleteList(null,effectList);
                    }
                    if(StringUtils.isNotBlank(pbomDeleteBuffer.toString())){
                        hzPbomRecordDAO.deleteListByPuids(pbomDeleteBuffer.toString(),ChangeTableNameEnum.HZ_PBOM.getTableName());
                    }
                    if(StringUtils.isNotBlank(pbomUpdateBuffer.toString())){
                        hzPbomRecordDAO.deleteByPuids(pbomUpdateBuffer.toString());
                    }

                    // 如果删除后 元素的父没有子 需要更改isHas 属性为0 2Y 层除外
                    List<HzEPLManageRecord> ebomUpdateRecords = new ArrayList<>();
                    List<HzPbomLineRecord> pbomUpdateRecords = new ArrayList<>();
                    if(ListUtil.isNotEmpty(ebomHasChildrenPuids)){
                        ebomHasChildrenPuids.forEach(puid->{
                            HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                            hzEbomTreeQuery.setPuid(puid);
                            hzEbomTreeQuery.setProjectId(projectId);
                            List<HzEPLManageRecord> list = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
                            if(ListUtil.isNotEmpty(list) && list.size() ==1){
                                HzEPLManageRecord record = new HzEPLManageRecord();
                                record.setPuid(list.get(0).getPuid());
                                record.setIs2Y(list.get(0).getIs2Y());
                                if(!Integer.valueOf(1).equals(record.getIs2Y())){
                                    record.setIsHas(0);
                                    ebomUpdateRecords.add(record);
                                }
                            }
                        });
                    }
                    if(ListUtil.isNotEmpty(pbomHasChildrenPuids)){
                        pbomHasChildrenPuids.forEach(puid->{
                            HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                            hzPbomTreeQuery.setProjectId(projectId);
                            hzPbomTreeQuery.setPuid(puid);
                            List<HzPbomLineRecord> list = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                            if(ListUtil.isNotEmpty(list) && list.size() ==1){
                                HzPbomLineRecord record = new HzPbomLineRecord();
                                record.setPuid(list.get(0).getPuid());
                                record.setIs2Y(list.get(0).getIs2Y());
                                if(!Integer.valueOf(1).equals(record.getIs2Y())){
                                    record.setIsHas(0);
                                    pbomUpdateRecords.add(record);
                                }
                            }
                        });
                    }

                    if(ListUtil.isNotEmpty(ebomUpdateRecords)){
                        hzEbomRecordDAO.updateListByPuids(ebomUpdateRecords);
                    }
                    if(ListUtil.isNotEmpty(pbomUpdateRecords)){
                        hzPbomRecordDAO.updateListByPuids(pbomUpdateRecords);
                    }
                    return null;
                }
            });
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }


    /**
     * 搭建BOM结构 2Y层结构
     * @param lineNo 查找编号
     * @param projectId 项目id
     * @param reqDTO 请求入参
     * @param hzEPLRecord 对应EPL中记录
     * @return
     */
    private WriteResultRespDTO insert2YBOMStructure(String lineNo,String projectId,AddHzEbomReqDTO reqDTO,HzEPLRecord hzEPLRecord){
        HZBomMainRecord projectRecord = hzBomMainRecordDao.selectByProjectPuid(projectId);
        if(projectRecord == null){
            return WriteResultRespDTO.failResultRespDTO("当前项目不存在！");
        }
        String bomDigifaxId =projectRecord.getPuid();
        //当前BOM数据是2Y层BOM
        HzEPLManageRecord record = HzEbomRecordFactory.addEbomTOEbomRecord(reqDTO,hzEPLRecord.getId(),1,1,bomDigifaxId);
        try {
            Double maxOrderNum  = hzEbomRecordDAO.findMaxBomOrderNum(projectId);
            if(StringUtils.isNotBlank(lineNo)){//找合适的位置 插入位置为大于当前用户输入lineNo的最小lineNo
                String lineIndex = lineNo+"."+lineNo;
                record.setLineIndex(lineIndex);
                boolean repeat = hzEbomRecordDAO.lineIndexRepeat(projectId,lineIndex);
                if(repeat){
                    return WriteResultRespDTO.failResultRespDTO("重复的查找编号输入！");
                }
                if(maxOrderNum == null){
                    record.setSortNum("100");
                }else {
                    HzBOMQuery hzEBOMQuery= new HzBOMQuery();
                    hzEBOMQuery.setIs2Y(1);
                    hzEBOMQuery.setLineNo(Integer.valueOf(lineNo));
                    hzEBOMQuery.setProjectId(projectId);

                    HzEPLManageRecord minRecord = hzEbomRecordDAO.findMinEBOMRecordWhichLineNoGreaterCurrentLineNo(hzEBOMQuery);//下一个位置
                    HzEPLManageRecord maxRecord =  hzEbomRecordDAO.findMaxEBOMRecordWhichLineNoLessCurrentNo(hzEBOMQuery);//上一个位置

                    if(minRecord == null && maxRecord == null){
                        return WriteResultRespDTO.failResultRespDTO("BOM结构发生错误或者服务器异常，请核对BOM数据后重试！");
                    }else if(minRecord == null){
                        record.setSortNum(String.valueOf(maxOrderNum+100));
                    }else if(maxRecord == null){
                        record.setSortNum(HzBomSysFactory.generateBomSortNum("0",minRecord.getSortNum()));
                    }else {
                        HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                        hzEbomTreeQuery.setPuid(maxRecord.getPuid());
                        hzEbomTreeQuery.setProjectId(projectId);
                        List<HzEPLManageRecord> recordList = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
                        if(ListUtil.isEmpty(recordList)){
                            return WriteResultRespDTO.failResultRespDTO("BOM数据发生错误或者服务器异常，请核对BOM数据后重试！");
                        }
                        String min = recordList.get(recordList.size()-1).getSortNum();
                        String max = minRecord.getSortNum();
                        record.setSortNum(HzBomSysFactory.generateBomSortNum(min,max));
                    }
                }
            }else {//直接插入到末尾位置
                HzEPLManageRecord manageRecord = hzEbomRecordDAO.getMaxLineIndexFirstNum(projectId);
                if(manageRecord == null){
                    record.setLineIndex("10.10");
                    record.setSortNum("100");
                }else {
                    if(maxOrderNum == null){
                        return WriteResultRespDTO.failResultRespDTO("BOM数据发生错误或者服务器异常，请核对BOM数据后重试！");
                    }
                    int lineIndexFirst = Integer.valueOf(manageRecord.getLineIndex().split("\\.")[0]);
                    record.setLineIndex(String.valueOf(lineIndexFirst+10)+"."+(lineIndexFirst+10));
                    record.setSortNum(String.valueOf(maxOrderNum+100));
                }
            }
            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    HzPbomLineRecord pbomRecord = null;
                    List carParts = hzMbomService.loadingCarPartType();
                    if(carParts.contains(hzEPLRecord.getPartResource())){
                        pbomRecord = HzPbomRecordFactory.editEbomToPbomRecord(hzEPLRecord,record);
                    }
                    hzEbomRecordDAO.insert(record);
                    if(pbomRecord != null){
                        hzPbomRecordDAO.insert(pbomRecord);
                    }
                    return null;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            throw new HzBomException(e);
        }
        return WriteResultRespDTO.getSuccessResult();
    }

    /**
     * 搭建BOM结构 往2Y层下添加BOM
     * @param reqDTO
     * @param hzEPLRecord
     * @param parentId
     * @param projectId
     * @param lineNo
     * @return
     */
    private WriteResultRespDTO insertBOMIntoParentLevel(AddHzEbomReqDTO reqDTO,HzEPLRecord hzEPLRecord,
                                                          String parentId,String projectId,String lineNo) {
        //当前BOM数据 要添加到某个父层下面 为了保持结构的一致性 给所有相同零件号（eplId）下都添加该结构
        //EBOM  PBOM
        try {
            HZBomMainRecord projectRecord = hzBomMainRecordDao.selectByProjectPuid(projectId);
            if(projectRecord == null){
                return WriteResultRespDTO.failResultRespDTO("当前项目不存在！");
            }
            String bomDigifaxId =projectRecord.getPuid();
            HzEPLManageRecord parentRecord = hzEbomRecordDAO.findEbomById(parentId,projectId);
            if(parentRecord == null){
                return WriteResultRespDTO.failResultRespDTO("未找到父项记录！");
            }
            String partId = parentRecord.getLineID();
            Long eplId = parentRecord.getEplId();
            HzBOMQuery query = new HzBOMQuery();
            query.setEplId(eplId);
            query.setProjectId(projectId);
            query.setPartId(partId);
            List<HzEPLManageRecord> list = hzEbomRecordDAO.findEBOMRecordsByEPLId(query);
            if(ListUtil.isEmpty(list)){
                return WriteResultRespDTO.failResultRespDTO("未找到父项记录！");
            }
            List<HzPbomLineRecord> pbomLineRecords = hzPbomRecordDAO.findPbomByLineId(query);

            //记录EBOM的插入操作 key parentId value eBomPuid 目的是为了 PBOM插入操作时 进行eBomPuid 匹配
            Map<String,String> map = new HashMap<>();

            List<HzEPLManageRecord> addEBOMList = new ArrayList<>();//EBOM 新增
            List<HzEPLManageRecord> updateEBOMList = new ArrayList<>();//EBOM更新

            List<HzPbomLineRecord> addPBOMList = new ArrayList<>();//PBOM 新增
            List<HzPbomLineRecord> updatePBOMList = new ArrayList<>();//PBOM 更新


            //EBOM 插入
            for(HzEPLManageRecord  parent : list){
                HzEbomTreeQuery ebomTreeQuery = new HzEbomTreeQuery();
                ebomTreeQuery.setProjectId(projectId);
                ebomTreeQuery.setPuid(parent.getPuid());
                List<HzEPLManageRecord> eBomTree = hzEbomRecordDAO.getHzBomLineChildren(ebomTreeQuery);
                if(ListUtil.isEmpty(eBomTree)){
                    return WriteResultRespDTO.failResultRespDTO("未找到父项记录！");
                }

                HzEPLManageRecord insertRecord = HzEbomRecordFactory.addEbomTOEbomRecord(reqDTO,hzEPLRecord.getId(),0,0,bomDigifaxId);
                insertRecord.setParentUid(parent.getPuid());//设置父层

                String lineIndex ="";
                String sortNum = "";

                if(StringUtils.isBlank(lineNo)){//插入到末尾位置
                    if(eBomTree.size() == 1){
                        parent.setIsHas(1);
                        lineIndex = parent.getLineIndex()+".10";
                        updateEBOMList.add(parent);
                    }

                    if(eBomTree.size() >1){
                        //查询当前父层的子一层记录
                        HzBOMQuery hzBOMQuery = new HzBOMQuery();
                        hzBOMQuery.setParentId(parent.getPuid());
                        hzBOMQuery.setProjectId(projectId);
                        List<HzEPLManageRecord> nextRecords = hzEbomRecordDAO.findNextLevelRecordByParentId(hzBOMQuery);
                        if(ListUtil.isEmpty(nextRecords)){
                            return WriteResultRespDTO.failResultRespDTO("未找到父项记录！");
                        }
                        String maxLineIndex = nextRecords.get(nextRecords.size()-1).getLineIndex();
                        lineIndex = parent.getLineIndex()+"."+(Integer.valueOf(maxLineIndex.split("\\.")[(maxLineIndex.split("\\.").length-1)])+10);
                    }

                    //设置sortNum 排序号
                    String lessSortNum = eBomTree.get(eBomTree.size()-1).getSortNum();
                    String greatSortNum = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,lessSortNum);
                    if(greatSortNum == null){
                        sortNum = String.valueOf(Double.parseDouble(lessSortNum)+100);
                    }else {
                        sortNum = HzBomSysFactory.generateBomSortNum(lessSortNum,greatSortNum);
                    }

                }else {// 按用户需求进行插入操作

                    //设置lineIndex 和sortNum
                    lineIndex = parent.getLineIndex()+"."+lineNo;
                    boolean repeat = hzEbomRecordDAO.lineIndexRepeat(projectId,lineIndex);
                    if(repeat){
                        return WriteResultRespDTO.failResultRespDTO("重复的查找编号输入！");
                    }

                    if(eBomTree.size() == 1){
                        parent.setIsHas(1);
                        String sortNo = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,parent.getSortNum());
                        if(sortNo == null){
                            sortNum = String.valueOf(Double.parseDouble(parent.getSortNum())+100);
                        }else {
                            sortNum = HzBomSysFactory.generateBomSortNum(parent.getSortNum(),sortNo);
                        }
                        updateEBOMList.add(parent);
                    }

                    if(eBomTree.size()>1){
                        HzBOMQuery hzEBOMQuery= new HzBOMQuery();
                        hzEBOMQuery.setParentId(parent.getPuid());
                        hzEBOMQuery.setLineNo(Integer.valueOf(lineNo));
                        hzEBOMQuery.setProjectId(projectId);

                        HzEPLManageRecord minRecord = hzEbomRecordDAO.findMinEBOMRecordWhichLineNoGreaterCurrentLineNo(hzEBOMQuery);//下一个位置
                        HzEPLManageRecord maxRecord =  hzEbomRecordDAO.findMaxEBOMRecordWhichLineNoLessCurrentNo(hzEBOMQuery);//上一个位置

                        if(minRecord == null && maxRecord == null){
                            return WriteResultRespDTO.failResultRespDTO("BOM结构发生错误或者服务器异常，请核对BOM数据后重试！");
                        }else if(minRecord == null){
                            String sortNo = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,maxRecord.getSortNum());
                            if(sortNo == null){
                                sortNum = String.valueOf(Double.parseDouble(maxRecord.getSortNum())+100);
                            }else {
                                sortNum = HzBomSysFactory.generateBomSortNum(maxRecord.getSortNum(),sortNo);
                            }
                        }else if(maxRecord == null){
                            sortNum = HzBomSysFactory.generateBomSortNum(parent.getSortNum(),minRecord.getSortNum());
                        }else {
                            //插入在上一个位置的包含该位置的所有的子的最后一个子下面
                            HzEbomTreeQuery hzEbomTreeQuery = new HzEbomTreeQuery();
                            hzEbomTreeQuery.setPuid(maxRecord.getPuid());
                            hzEbomTreeQuery.setProjectId(projectId);
                            List<HzEPLManageRecord> recordList = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
                            if(ListUtil.isEmpty(recordList)){
                                return WriteResultRespDTO.failResultRespDTO("BOM数据发生错误或者服务器异常，请核对BOM数据后重试！");
                            }
                            String min = recordList.get(recordList.size()-1).getSortNum();
                            String max = minRecord.getSortNum();
                            sortNum = HzBomSysFactory.generateBomSortNum(min,max);
                        }
                    }

                }

                insertRecord.setSortNum(sortNum);//设置sortNum
                insertRecord.setLineIndex(lineIndex);//设置 lineIndex
                addEBOMList.add(insertRecord);
                map.put(parent.getPuid(),insertRecord.getPuid());
            }
            List carParts = hzMbomService.loadingCarPartType();
            //PBOM 插入
            if(carParts.contains(hzEPLRecord.getPartResource()) && ListUtil.isNotEmpty(pbomLineRecords)){
                for(HzPbomLineRecord  parent : pbomLineRecords){
                    HzPbomTreeQuery pbomTreeQuery = new HzPbomTreeQuery();
                    pbomTreeQuery.setProjectId(projectId);
                    pbomTreeQuery.setPuid(parent.geteBomPuid());
                    List<HzPbomLineRecord> pBomTree = hzPbomRecordDAO.getHzPbomTree(pbomTreeQuery);
                    if(ListUtil.isEmpty(pBomTree)){
                        return WriteResultRespDTO.failResultRespDTO("未找到父项记录！");
                    }

                    HzPbomLineRecord insertRecord = HzPbomRecordFactory.editEbomToPbomRecord(hzEPLRecord,reqDTO);
                    insertRecord.setParentUid(parent.geteBomPuid());//设置父层
                    insertRecord.setIsHas(0);
                    insertRecord.setIs2Y(0);
                    insertRecord.setBomDigifaxId(bomDigifaxId);
                    String lineIndex ="";
                    String sortNum = "";
                    if(StringUtils.isBlank(lineNo)){//插入到末尾位置
                        if(pBomTree.size() == 1){
                            parent.setIsHas(1);
                            lineIndex = parent.getLineIndex()+".10";
                            updatePBOMList.add(parent);
                        }

                        if(pBomTree.size() >1){
                            //查询当前父层的子一层记录
                            List<HzPbomLineRecord> nextRecords = hzPbomRecordDAO.getFirstLevelBomByParentId(parent.geteBomPuid(),projectId);
                            if(ListUtil.isEmpty(nextRecords)){
                                return WriteResultRespDTO.failResultRespDTO("未找到父项记录！");
                            }
                            String maxLineIndex = nextRecords.get(nextRecords.size()-1).getLineIndex();
                            lineIndex = parent.getLineIndex()+"."+(Integer.valueOf(maxLineIndex.split("\\.")[(maxLineIndex.split("\\.").length-1)])+10);
                        }

                        //设置sortNum 排序号
                        String lessSortNum = pBomTree.get(pBomTree.size()-1).getSortNum();
                        String greatSortNum = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,lessSortNum);
                        if(greatSortNum == null){
                            sortNum = String.valueOf(Double.parseDouble(lessSortNum)+100);
                        }else {
                            sortNum = HzBomSysFactory.generateBomSortNum(lessSortNum,greatSortNum);
                        }

                    }else {// 按用户需求进行插入操作

                        //设置lineIndex 和sortNum
                        lineIndex = parent.getLineIndex()+"."+lineNo;
                        if(pBomTree.size() == 1){
                            String sortNo = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,parent.getSortNum());
                            if(sortNo == null){
                                sortNum = String.valueOf(Double.parseDouble(parent.getSortNum())+100);
                            }else {
                                sortNum = HzBomSysFactory.generateBomSortNum(parent.getSortNum(),sortNo);
                            }
                            parent.setIsHas(1);
                            updatePBOMList.add(parent);
                        }

                        if(pBomTree.size()>1){
                            HzBOMQuery hzPBOMQuery= new HzBOMQuery();
                            hzPBOMQuery.setParentId(parent.geteBomPuid());
                            hzPBOMQuery.setLineNo(Integer.valueOf(lineNo));
                            hzPBOMQuery.setProjectId(projectId);

                            HzPbomLineRecord minRecord = hzPbomRecordDAO.findMinPBOMRecordWhichLineNoGreaterCurrentLineNo(hzPBOMQuery);//下一个位置
                            HzPbomLineRecord maxRecord =  hzPbomRecordDAO.findMaxPBOMRecordWhichLineNoLessCurrentNo(hzPBOMQuery);//上一个位置

                            if(minRecord == null && maxRecord == null){
                                return WriteResultRespDTO.failResultRespDTO("BOM结构发生错误或者服务器异常，请核对BOM数据后重试！");
                            }else if(minRecord == null){
                                String sortNo = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,maxRecord.getSortNum());
                                if(sortNo == null){
                                    sortNum = String.valueOf(Double.parseDouble(maxRecord.getSortNum())+100);
                                }else {
                                    sortNum = HzBomSysFactory.generateBomSortNum(maxRecord.getSortNum(),sortNo);
                                }
                            }else if(maxRecord == null){
                                sortNum = HzBomSysFactory.generateBomSortNum(parent.getSortNum(),minRecord.getSortNum());
                            }else {
                                //插入在上一个位置的包含该位置的所有的子的最后一个子下面
                                HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                                hzPbomTreeQuery.setPuid(maxRecord.geteBomPuid());
                                hzPbomTreeQuery.setProjectId(projectId);
                                List<HzPbomLineRecord> recordList = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);
                                if(ListUtil.isEmpty(recordList)){
                                    return WriteResultRespDTO.failResultRespDTO("BOM数据发生错误或者服务器异常，请核对BOM数据后重试！");
                                }
                                String min = recordList.get(recordList.size()-1).getSortNum();
                                String max = minRecord.getSortNum();
                                sortNum = HzBomSysFactory.generateBomSortNum(min,max);
                            }
                        }

                    }
                    String eBomPuid = map.get(parent.geteBomPuid());
                    if(StringUtils.isBlank(eBomPuid)){
                        eBomPuid = UUID.randomUUID().toString();
                    }
                    insertRecord.seteBomPuid(eBomPuid);
                    insertRecord.setSortNum(sortNum);//设置排序号
                    insertRecord.setLineIndex(lineIndex);//设置 lineIndex
                    addPBOMList.add(insertRecord);
                }
            }

            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    if(ListUtil.isNotEmpty(addEBOMList)){
                        hzEbomRecordDAO.insertList(addEBOMList,ChangeTableNameEnum.HZ_EBOM.getTableName());
                    }
                    if(ListUtil.isNotEmpty(updateEBOMList)){
                        hzEbomRecordDAO.updateListByPuids(updateEBOMList);
                    }
                    if(ListUtil.isNotEmpty(addPBOMList)){
                        hzPbomRecordDAO.insertList(addPBOMList);
                    }
                    if(ListUtil.isNotEmpty(updatePBOMList)){
                        hzPbomRecordDAO.updateListByPuids(updatePBOMList);
                    }
                    return null;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            throw new HzBomException(e);
        }
        return WriteResultRespDTO.getSuccessResult();
    }

}
