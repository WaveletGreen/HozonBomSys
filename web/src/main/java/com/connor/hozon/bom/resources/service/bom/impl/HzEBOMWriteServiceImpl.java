package com.connor.hozon.bom.resources.service.bom.impl;

import cn.net.connor.hozon.dao.dao.main.HzMainBomDao;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.domain.constant.ChangeConstants;
import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.bom.resources.domain.model.HzEbomRecordFactory;
import com.connor.hozon.bom.resources.domain.model.HzPbomRecordFactory;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.executors.ExecutorServices;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.bom.resources.service.bom.HzEBOMReadService;
import com.connor.hozon.bom.resources.service.bom.HzEBOMWriteService;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import com.connor.hozon.bom.resources.util.StringUtil;
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
import cn.net.connor.hozon.dao.pojo.main.HzMainBom;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzPbomLineRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeDataRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLRecord;

import java.util.*;

import java.util.List;

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

    private HzMainBomDao hzMainBomDao;

    private HzMbomService hzMbomService;

    private TransactionTemplate configTransactionTemplate;

    private HzEBOMReadService hzEBOMReadService;

    private HzChangeDataRecordDAO hzChangeDataRecordDAO;

    private HzMbomRecordDAO hzMbomRecordDAO;

    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

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
    public void setHzMainBomDao(HzMainBomDao hzMainBomDao) {
        this.hzMainBomDao = hzMainBomDao;
    }

    @Autowired
    public void setHzMbomService(HzMbomService hzMbomService) {
        this.hzMbomService = hzMbomService;
    }

    @Autowired
    public void setHzEBOMReadService(HzEBOMReadService hzEBOMReadService) {
        this.hzEBOMReadService = hzEBOMReadService;
    }

    @Autowired
    public void setHzChangeDataRecordDAO(HzChangeDataRecordDAO hzChangeDataRecordDAO) {
        this.hzChangeDataRecordDAO = hzChangeDataRecordDAO;
    }

    @Autowired
    public void setHzMbomRecordDAO(HzMbomRecordDAO hzMbomRecordDAO) {
        this.hzMbomRecordDAO = hzMbomRecordDAO;
    }

    @Autowired
    public void setHzBomLineRecordDao(HzBomLineRecordDaoImpl hzBomLineRecordDao) {
        this.hzBomLineRecordDao = hzBomLineRecordDao;
    }


    @Override
    @Transactional(rollbackFor = {RuntimeException.class,HzBomException.class})
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
            HzMainBom projectRecord = hzMainBomDao.selectByProjectId(projectId);
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

    /**
     * 获得当前下面的所有子件
     * @param lineId
     * @param projectId
     * @return
     */
    private List<HzEPLManageRecord> getHzEbomRecordTreeByParentId(String lineId,String projectId) {
        List<HzEPLManageRecord> recordList=null;
        return recordList;
    }

    @Override
    public WriteResultRespDTO updateHzEbomRecord(UpdateHzEbomReqDTO reqDTO) {
        //参数校验
        if(StringUtils.isBlank(reqDTO.getProjectId()) || StringUtils.isBlank(reqDTO.getPuid())){
            return WriteResultRespDTO.IllgalArgument();
        }
        HzEPLManageRecord record = HzEbomRecordFactory.updateEBOMReqTORecord(reqDTO);
        if(hzEbomRecordDAO.update(record) <= 0){
            return WriteResultRespDTO.getFailResult();
        }
        //如果是2Y层，还需调整查询编号
        // todo 下次在做 先不做了
//        if(StringUtils.isNotBlank(reqDTO.getLineNo())){
//            List<HzEPLManageRecord> hzEPLManageRecordUpdate = new ArrayList<>();
//
//            HzEPLManageRecord hzEPLManageRecord = hzEbomRecordDAO.findEbomById(reqDTO.getId(), reqDTO.getProjectId());
//            if(hzEPLManageRecord == null){
//                return WriteResultRespDTO.failResultRespDTO("当前要修改的BOM不存在！");
//            }
//
//            String oldLineNo = HzBomSysFactory.getLevelAndRank(hzEPLManageRecord.getLineIndex(),hzEPLManageRecord.getIs2Y(),hzEPLManageRecord.getIsHas())[2];
//            //如果查询编号没变则不修改
//            if(oldLineNo.equals(reqDTO.getLineNo())){
//                return WriteResultRespDTO.getSuccessResult();
//            }
//            String lineIndex = reqDTO.getLineNo()+"."+ reqDTO.getLineNo();
//            if(hzEbomRecordDAO.lineIndexRepeat(reqDTO.getProjectId(),lineIndex)){
//                return WriteResultRespDTO.failResultRespDTO("重复的查找编号输入");
//            }
//            HzEbomTreeQuery query = new HzEbomTreeQuery();
//            query.setId(reqDTO.getId());
//            query.setProjectId(reqDTO.getProjectId());
//            List<HzEPLManageRecord> hzEPLManageRecordsChildren = hzEbomRecordDAO.getHzBomLineChildren(query);
//
//            Map<String,Object> map = new HashMap<>();
//            map.put("projectId",reqDTO.getProjectId());
//            map.put("lineNo",reqDTO.getLineNo());
//            map.put("flag","previous");
//            //查找上一个2Y
//            HzEPLManageRecord  hzEPLManagePrevious = hzEbomRecordDAO.findEbom2Y(map);
//            //查找下一个2Y
//            map.put("flag","next");
//            HzEPLManageRecord  hzEPLManageNext = hzEbomRecordDAO.findEbom2Y(map);
//            Double increment = 0.0;
//            Double previous = 0.0;
//            if(hzEPLManagePrevious!=null){
//                previous = Double.valueOf(hzEPLManagePrevious.getSortNum());
//            }
//
//            if(hzEPLManageNext==null){
//                increment = 100.0;
//            }else {
//                Double next = Double.valueOf(hzEPLManageNext.getSortNum());
//                increment = (next-previous)/(hzEPLManageRecordsChildren.size()+2);
//            }
//            Double sortNum = previous+increment;
//            String indexOld = hzEPLManageRecordsChildren.get(0).getLineIndex();
//            hzEPLManageRecordsChildren.get(0).setSortNum(String.valueOf(sortNum));
//            hzEPLManageRecordsChildren.get(0).setLineIndex(reqDTO.getLineNo().replaceFirst("0*","")+"."+reqDTO.getLineNo().replaceFirst("0*",""));
//            hzEPLManageRecordUpdate.add(hzEPLManageRecordsChildren.get(0));
//            for(int i=1;i<hzEPLManageRecordsChildren.size();i++){
//                sortNum += increment;
//                HzEPLManageRecord hzEPLManageRecordChildren = hzEPLManageRecordsChildren.get(i);
//                hzEPLManageRecordChildren.setSortNum(String.valueOf(sortNum));
//                String indexChildrenOld = hzEPLManageRecordChildren.getLineIndex();
//                hzEPLManageRecordChildren.setLineIndex(hzEPLManageRecordsChildren.get(0).getLineIndex()+indexChildrenOld.substring(hzEPLManageRecordsChildren.get(0).getLineIndex().length()));
//                hzEPLManageRecordUpdate.add(hzEPLManageRecordChildren);
//            }
//            try {
//                hzEbomRecordDAO.updateEPLList(hzEPLManageRecordUpdate);
//            }catch (Exception e){
//                e.printStackTrace();
//                return WriteResultRespDTO.failResultRespDTO("修改查询编号失败");
//            }
//        }
        return WriteResultRespDTO.getSuccessResult();
    }

    @Override
    public WriteResultRespDTO extendsBomStructureInNewParent(UpdateHzEbomLeveReqDTO reqDTO) {
        List<HzEPLManageRecord> insertList = new ArrayList<>();
        List<HzPbomLineRecord> insertListPbom = new ArrayList<>();

        HzEbomTreeQuery query = new HzEbomTreeQuery();
        query.setProjectId(reqDTO.getProjectId());
        query.setPuid(reqDTO.getPuid());

        //被添加的父
        List<HzEPLManageRecord> hzEPLManageRecordsFather = hzEbomRecordDAO.findBaseEbomById(reqDTO.getLineId(), reqDTO.getProjectId());
        if(hzEPLManageRecordsFather==null||hzEPLManageRecordsFather.size()<=0){
            return WriteResultRespDTO.failResultRespDTO("父层不存在");
        }

        //添加的根
        HzEPLManageRecord hzEPLManageRecordRoot = hzEbomRecordDAO.findEbomById(reqDTO.getPuid(), reqDTO.getProjectId());

        //添加的子
        List<HzEPLManageRecord> hzBomLineChildren = hzEbomRecordDAO.getHzBomLineChildren(query);

        //判断子中是否存在父
        for(HzEPLManageRecord hzEPLManageRecordChildren : hzBomLineChildren ){
            if(hzEPLManageRecordChildren.getPuid().equals(hzEPLManageRecordsFather.get(0).getPuid())){
                return WriteResultRespDTO.failResultRespDTO("父层为根的子，不能添加");
            }
        }


        for(HzEPLManageRecord hzEPLManageRecordFather : hzEPLManageRecordsFather){
            //校验插入点是否已存在数据
            HzEPLManageRecord hzEPLManageRecordChildren = hzEbomRecordDAO.findEbomChildrenByLineIndex(hzEPLManageRecordFather.getPuid(),reqDTO.getLineNo().replaceFirst("0*",""));
            if(hzEPLManageRecordChildren!=null){
                return WriteResultRespDTO.failResultRespDTO("查找编号已存在，请重新输入");
            }
            //插入点下一个数据
            HzEPLManageRecord hzEPLManageRecordNext = hzEbomRecordDAO.findNextLineIndex(hzEPLManageRecordFather.getPuid(),reqDTO.getLineNo());
            //插入点上一个数据
            HzEPLManageRecord hzEPLManageRecordPrevious = null;
            if(hzEPLManageRecordNext==null){
                HzEbomTreeQuery queryPrevious = new HzEbomTreeQuery();
                queryPrevious.setProjectId(reqDTO.getProjectId());
                queryPrevious.setPuid(hzEPLManageRecordFather.getPuid());
                List<HzEPLManageRecord> hzEPLManageRecordsPrevious = hzEbomRecordDAO.getHzBomLineChildren(queryPrevious);

                hzEPLManageRecordPrevious = hzEPLManageRecordsPrevious.get(hzEPLManageRecordsPrevious.size()-1);

                hzEPLManageRecordNext = hzEbomRecordDAO.findNextSortNum(hzEPLManageRecordPrevious);
            }else {
                hzEPLManageRecordPrevious = hzEbomRecordDAO.findPreviousEbom(hzEPLManageRecordNext);
                if(hzEPLManageRecordPrevious==null){
                    hzEPLManageRecordPrevious = hzEPLManageRecordFather;
                }
            }
            //根据插入点上下数据生成插入数据的  排序号 并插入
            //根节点的 查询编号
            String rootLineIndex = hzEPLManageRecordFather.getLineIndex()+"."+reqDTO.getLineNo().replaceFirst("^0*", "");
            //每次排序号增量
            Double increment = 0.0;
            //如果存在下一个数据不存在上一个数据,将父作为上一个数据
            if(hzEPLManageRecordNext==null){
                increment = 100.0;
            }else {
                Double allNo = Double.valueOf(hzEPLManageRecordNext.getSortNum()) - Double.valueOf(hzEPLManageRecordPrevious.getSortNum());
                increment = allNo/(hzBomLineChildren.size()+2);
            }
            //根节点的排序号
            Double rootLineNo = Double.valueOf(hzEPLManageRecordPrevious.getSortNum()) + increment;

            String rootLineIndexOld = hzEPLManageRecordRoot.getLineIndex();
            hzEPLManageRecordRoot.setLineIndex(rootLineIndex);
            hzEPLManageRecordRoot.setSortNum(rootLineNo.toString());
            hzEPLManageRecordRoot.setPuid(UUIDHelper.generateUpperUid());
            hzEPLManageRecordRoot.setParentUid(hzEPLManageRecordFather.getPuid());
            insertList.add(hzEPLManageRecordRoot);
            //根据根的 排序号 和 查找编号 生成子数据并插入
            String[] lineIndexs = hzEPLManageRecordFather.getLineIndex().split("\\.");
            for(int i=1;i<hzBomLineChildren.size();i++){
                HzEPLManageRecord hzEPLManageRecord = hzBomLineChildren.get(i);
                rootLineNo = rootLineNo+increment;
                hzEPLManageRecord.setSortNum(rootLineNo.toString());
                String childrenIndexOld = hzEPLManageRecord.getLineIndex();
                String childrenIndexNew = hzEPLManageRecordRoot.getLineIndex()+childrenIndexOld.substring(rootLineIndexOld.length(),childrenIndexOld.length());
                hzEPLManageRecord.setLineIndex(childrenIndexNew);
                hzEPLManageRecord.setPuid(UUIDHelper.generateUpperUid());
                insertList.add(hzEPLManageRecord);
            }
        }
        try {
            hzEbomRecordDAO.insertList(insertList,ChangeTableNameEnum.HZ_EBOM.getTableName());
            if(hzEPLManageRecordsFather.get(0).getIsHas()==0){
                for(HzEPLManageRecord hzEPLManageRecord : hzEPLManageRecordsFather){
                    hzEPLManageRecord.setIsHas(1);
                }
                hzEbomRecordDAO.updateEPLList(hzEPLManageRecordsFather);
            }
        }catch (Exception e){
            WriteResultRespDTO.failResultRespDTO("引用层级失败");
        }


        /**************************同步PBOM**************************/
        //父PBOM
        List<HzPbomLineRecord> hzPbomLineRecordsFather = hzPbomRecordDAO.findPbomsByEBom(hzEPLManageRecordsFather);
        //根PBOM
        HzPbomLineRecord hzPbomLineRecordRoot = hzPbomRecordDAO.findPbomByEBom(reqDTO.getPuid(), reqDTO.getProjectId());
        //如果PBOM中没找到对应的EBOM，不再同步到PBOM
        if(hzPbomLineRecordsFather==null||hzPbomLineRecordRoot==null){
            return WriteResultRespDTO.getSuccessResult();
        }
        //子PBOM
        HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
        hzPbomTreeQuery.setProjectId(reqDTO.getProjectId());
        hzPbomTreeQuery.setPuid(hzPbomLineRecordRoot.getPuid());
        List<HzPbomLineRecord> hzPbomLineRecordsChildren = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);

        for(HzPbomLineRecord hzPbomLineRecordChildren : hzPbomLineRecordsChildren ){
            if(hzPbomLineRecordChildren.getPuid().equals(hzPbomLineRecordsFather.get(0).getPuid())){
                return WriteResultRespDTO.failResultRespDTO("在PBOM中，父层为根的子，不能添加");
            }
        }

        for(HzPbomLineRecord hzPbomLineRecordFather : hzPbomLineRecordsFather){
            //校验插入点是否已存在数据
            HzPbomLineRecord hzPbomLineRecordChildren = hzPbomRecordDAO.findEbomChildrenByLineIndex(hzPbomLineRecordFather.getPuid(),reqDTO.getLineNo().replaceFirst("0*",""));
            if(hzPbomLineRecordChildren!=null){
                return WriteResultRespDTO.failResultRespDTO("查找编号已存在，请重新输入");
            }

            //插入点下一个数据
            HzPbomLineRecord hzPbomLineRecord1Next = hzPbomRecordDAO.findNextLineIndex(hzPbomLineRecordFather.geteBomPuid(),reqDTO.getLineNo());
            //插入点上一个数据
            HzPbomLineRecord hzPbomLineRecordPrevious  = null;
            if(hzPbomLineRecord1Next==null){
                HzPbomTreeQuery pBomQuery = new HzPbomTreeQuery();
                hzPbomTreeQuery.setProjectId(reqDTO.getProjectId());
                hzPbomTreeQuery.setPuid(hzPbomLineRecordFather.getPuid());
                List<HzPbomLineRecord> hzPbomLineRecordsPrevious = hzPbomRecordDAO.getHzPbomTree(pBomQuery);

                if(hzPbomLineRecordsPrevious==null||hzPbomLineRecordsPrevious.size()==0){
                    return WriteResultRespDTO.getSuccessResult();
                }
                hzPbomLineRecordPrevious = hzPbomLineRecordsPrevious.get(hzPbomLineRecordsPrevious.size()-1);

                hzPbomLineRecord1Next = hzPbomRecordDAO.findNextSortNum(hzPbomLineRecordPrevious);
            }else {
                hzPbomLineRecordPrevious = hzPbomRecordDAO.findPreviousPbom(hzPbomLineRecord1Next);
                if(hzPbomLineRecordPrevious==null){
                    hzPbomLineRecordPrevious = hzPbomLineRecordFather;
                }
            }

            //根据插入点上下数据生成插入数据的  排序号 并插入
            //根节点的 查询编号
            String rootLineIndex = hzPbomLineRecordFather.getLineIndex()+"."+reqDTO.getLineNo().replaceFirst("^0*", "");
            //每次排序号增量
            Double increment = 0.0;
            //如果存在下一个数据不存在上一个数据,将父作为上一个数据
            if(hzPbomLineRecord1Next==null){
                increment = 100.0;
            }else {
                Double allNo = Double.valueOf(hzPbomLineRecord1Next.getSortNum()) - Double.valueOf(hzPbomLineRecordPrevious.getSortNum());
                increment = allNo/(hzPbomLineRecordsChildren.size()+2);
            }
            //根节点的排序号
            Double rootLineNo = Double.valueOf(hzPbomLineRecordPrevious.getSortNum()) + increment;

            String rootLineIndexOld = hzPbomLineRecordRoot.getLineIndex();
            hzPbomLineRecordRoot.setLineIndex(rootLineIndex);
            hzPbomLineRecordRoot.setSortNum(rootLineNo.toString());
            hzPbomLineRecordRoot.setPuid(UUIDHelper.generateUpperUid());
            hzPbomLineRecordRoot.setParentUid(hzPbomLineRecordFather.getPuid());
            insertListPbom.add(hzPbomLineRecordRoot);
            //根据根的 排序号 和 查找编号 生成子数据并插入
            String[] lineIndexs = hzPbomLineRecordFather.getLineIndex().split("\\.");
            for(int i=1;i<hzPbomLineRecordsChildren.size();i++){
                HzPbomLineRecord hzPbomLineRecord = hzPbomLineRecordsChildren.get(i);
                rootLineNo = rootLineNo+increment;
                hzPbomLineRecord.setSortNum(rootLineNo.toString());
                String childrenIndexOld = hzPbomLineRecord.getLineIndex();
                String childrenIndexNew = hzPbomLineRecordRoot.getLineIndex()+childrenIndexOld.substring(rootLineIndexOld.length(),childrenIndexOld.length());
                hzPbomLineRecord.setLineIndex(childrenIndexNew);
                hzPbomLineRecord.setPuid(UUIDHelper.generateUpperUid());
                insertListPbom.add(hzPbomLineRecord);
            }
        }


        try {
            hzPbomRecordDAO.insertList(insertListPbom);
            if(hzPbomLineRecordsFather.get(0).getIsHas()==0){
                for(HzPbomLineRecord hzPbomLineRecord : hzPbomLineRecordsFather){
                    hzPbomLineRecord.setIsHas(1);
                }
                hzPbomRecordDAO.updateList(hzPbomLineRecordsFather);
            }
        }catch (Exception e){
            e.printStackTrace();
            WriteResultRespDTO.failResultRespDTO("引用层级失败");
        }
        return WriteResultRespDTO.getSuccessResult();

    }

    @Override
    public WriteResultRespDTO deleteHzEbomRecord(DeleteHzEbomReqDTO reqDTO) {
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
                        ebomHasChildrenPuids.add(record.getParentUid());
                        if(Integer.valueOf(1).equals(record.getIsHas())){
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
                        }else {
                            if(deleteFlag){
                                deleteList.add(puid);
                            }else {
                                effectList.add(puid);
                            }

                        }

                        //PBOM 查找
                        HzPbomLineRecord pbomRecord = hzPbomRecordDAO.getHzPbomByEbomPuid(puid,projectId);
                        if(pbomRecord != null){
                            if(StringUtils.isNotBlank(pbomRecord.getRevision())){
                                deleteFlag = false;
                            }
                            pbomHasChildrenPuids.add(pbomRecord.getParentUid());
                            if(Integer.valueOf(1).equals(pbomRecord.getIsHas())){
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
                            }else {
                                if(deleteFlag){
                                    pbomDeleteBuffer.append(pbomRecord.getPuid()+",");
                                }else {
                                    pbomUpdateBuffer.append(pbomRecord.getPuid()+",");
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

    @Override
    @Transactional
    public WriteResultRespDTO dataToChangeOrder(AddDataToChangeOrderReqDTO reqDTO) {
        if(StringUtil.isEmpty(reqDTO.getPuids()) || StringUtil.isEmpty(reqDTO.getProjectId())
                || null == reqDTO.getOrderId()){
            return WriteResultRespDTO.IllgalArgument();
        }


        //表单id
        Long orderId = reqDTO.getOrderId();

        //数据库表名
        String tableName = ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName();
        //获取数据信息
        List<String> puids = Lists.newArrayList(reqDTO.getPuids().split(","));
        //统计操作数据
        Map<String,Object> map = new HashMap<>();
        Map<Integer,List<String>> puidMap = HzBomSysFactory.spiltList(puids);//1000条约束
        List<HzEPLManageRecord> records = new ArrayList<>();
        for(List<String> v:puidMap.values()){
            //查询EBOM表数据 保存历史记录
            HzChangeDataDetailQuery query  = new HzChangeDataDetailQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setPuids(v);
            query.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
            List<HzEPLManageRecord> records1 = hzEbomRecordDAO.getEbomRecordsByPuids(query);
            if(ListUtil.isNotEmpty(records1)){
                records.addAll(records1);
            }
        }

        List<HzEPLManageRecord> afterRecords = new ArrayList<>();
        if(ListUtil.isNotEmpty(records)){
            //到 after表中查询看是否存在记录
            //存在记录则过滤 不存在记录则插入
            HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
            dataDetailQuery.setProjectId(reqDTO.getProjectId());
            dataDetailQuery.setOrderId(orderId);
            dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_AFTER.getTableName());
            List<HzEPLManageRecord> recordList = hzEbomRecordDAO.getEbomRecordsByOrderId(dataDetailQuery);
            if(ListUtil.isEmpty(recordList)){
                records.forEach(record -> {
                    record.setOrderId(orderId);
                    afterRecords.add(record);
                });
            }else {
                for(int i=0;i<records.size();i++){
                    records.get(i).setOrderId(orderId);
                    for(HzEPLManageRecord record:recordList){
                        if(records.get(i).equals(record)){
                            records.remove(records.get(i));
                            i--;
                            break;
                        }
                    }
                }
                afterRecords.addAll(records);
            }

            map.put("ebomAfter",afterRecords);

            //修改发起流程后状态值
            List<HzEPLManageRecord> bomLineRecords = new ArrayList<>();
            for(HzEPLManageRecord record:records){
                HzEPLManageRecord lineRecord = HzEbomRecordFactory.ebomRecordToEBOMRecord(record);
                lineRecord.setStatus(ChangeConstants.VERIFY_STATUS);//审核状态
                lineRecord.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
                bomLineRecords.add(lineRecord);
            }

            map.put("ebomBefore",bomLineRecords);
            //保存以上获取信息
            HzChangeDataRecord record = new HzChangeDataRecord();
            record.setOrderId(reqDTO.getOrderId());
            record.setTableName(tableName);
            map.put("changeData",record);

            try {

                //启动线程进行插入操作
                for(Map.Entry<String,Object> entry:map.entrySet()){
                    new ExecutorServices(1) {
                        @Override
                        public void action() {
                            switch (entry.getKey()){
                                case "ebomAfter":
                                    hzEbomRecordDAO.insertList((List<HzEPLManageRecord>) entry.getValue(),tableName);
                                    break;
                                case "ebomBefore":
                                    hzEbomRecordDAO.updateListByEplId((List<HzEPLManageRecord>) entry.getValue());
                                    break;
                                case "changeData":
                                    hzChangeDataRecordDAO.insert((HzChangeDataRecord) entry.getValue());
                                    break;
                                default:break;
                            }
                        }
                    }.execute();

                }
            }catch (Exception e){
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return WriteResultRespDTO.getFailResult();
            }

        }
        return WriteResultRespDTO.getSuccessResult();
    }

    /**
     * 撤销EBOM数据到上一个生效版本
     * @param reqDTO
     * @return
     */
    @Override
    public WriteResultRespDTO backBomUtilLastValidState(BomBackReqDTO reqDTO) {
        try{
            List<String> puids = Lists.newArrayList(reqDTO.getPuids().split(","));

            List<String> deletePuids = new ArrayList<>();
            List<HzEPLManageRecord> updateRecords = new ArrayList<>();
            List<HzEPLManageRecord> updateList = new ArrayList<>();
            Set<HzEPLManageRecord> set = new HashSet<>();
            Map<Integer,List<String>> puidMap = HzBomSysFactory.spiltList(puids);//1000条约束
            List<HzEPLManageRecord> list = new ArrayList<>();
            for(List<String> v:puidMap.values()){
                HzChangeDataDetailQuery query = new HzChangeDataDetailQuery();
                query.setProjectId(reqDTO.getProjectId());
                query.setPuids(v);
                query.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
                List<HzEPLManageRecord> list1 = hzEbomRecordDAO.getEbomRecordsByPuids(query);
                if(ListUtil.isNotEmpty(list1)){
                    list.addAll(list1);
                }
            }

            //带子层撤销
            //撤销 1找不存在版本记录的--删除    2找存在记录-直接更新数据为上个版本生效数据
            if(ListUtil.isNotEmpty(list)){
                list.forEach(r->{
                    if(Integer.valueOf(1).equals(r.getIsHas())){
                        HzEbomTreeQuery ebomTreeQuery = new HzEbomTreeQuery();
                        ebomTreeQuery.setProjectId(reqDTO.getProjectId());
                        ebomTreeQuery.setPuid(r.getPuid());
                        List<HzEPLManageRecord> l = hzEbomRecordDAO.getHzBomLineChildren(ebomTreeQuery);
                        if(ListUtil.isNotEmpty(l))
                            set.addAll(l);
                    }else {
                        set.add(r);
                    }
                });
            }
            if(ListUtil.isNotEmpty(set)){
                set.forEach(record -> {
                    if(StringUtils.isBlank(record.getRevision())){
                        deletePuids.add(record.getPuid());
                    }else {
                        updateRecords.add(record);
                    }
                });
            }
            if(ListUtil.isNotEmpty(updateRecords)){
                HzChangeDataDetailQuery dataDetailQuery = new HzChangeDataDetailQuery();
                dataDetailQuery.setRevision(true);
                dataDetailQuery.setProjectId(reqDTO.getProjectId());
                dataDetailQuery.setTableName(ChangeTableNameEnum.HZ_EBOM_BEFORE.getTableName());
                dataDetailQuery.setStatus(ChangeConstants.EFFECT_STATUS);
                updateRecords.forEach(record -> {
                    dataDetailQuery.setRevisionNo(record.getRevision());
                    dataDetailQuery.setPuid(record.getPuid());
                    HzEPLManageRecord manageRecord = hzEbomRecordDAO.getEBomRecordByPuidAndRevision(dataDetailQuery);
                    if(manageRecord!=null){
                        updateList.add(manageRecord);
                    }
                });
            }
            if(ListUtil.isNotEmpty(updateList)){
                hzEbomRecordDAO.updateEBOMListByEplId(updateList);
            }
            if(ListUtil.isNotEmpty(deletePuids)){
                hzEbomRecordDAO.deleteByPuids(deletePuids,ChangeTableNameEnum.HZ_EBOM.getTableName());
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    /**
     * 设置为LOU 或者取消设置为LOU
     */
    @Override
    public WriteResultRespDTO setCurrentBomAsLou(SetLouReqDTO reqDTO) {
        try {
            if (StringUtils.isBlank(reqDTO.getProjectId()) || StringUtils.isBlank(reqDTO.getPuid())) {
                return WriteResultRespDTO.IllgalArgument();
            }
            String puid = reqDTO.getPuid();
            HzEPLManageRecord record = hzEbomRecordDAO.findEbomById(puid,reqDTO.getProjectId());
            HzPbomLineRecord pbomLineRecord = hzPbomRecordDAO.getHzPbomByEbomPuid(puid,reqDTO.getProjectId());

            //设置EBOM LOU  1 LOU  0 LOA  2都不是
            if(record == null){
                return WriteResultRespDTO.failResultRespDTO("当前选中的BOM行不存在!");
            }
            HzEbomTreeQuery hzEbomTreeQuery  = new HzEbomTreeQuery();
            hzEbomTreeQuery.setProjectId(reqDTO.getProjectId());
            hzEbomTreeQuery.setPuid(puid);
            List<HzEPLManageRecord> records = hzEbomRecordDAO.getHzBomLineChildren(hzEbomTreeQuery);
            if(ListUtil.isEmpty(records)){
                return WriteResultRespDTO.failResultRespDTO("当前选中的BOM行不存在!");
            }
            boolean setLou = false;
            if(Integer.valueOf(0).equals(record.getpLouaFlag())){//它的父层设置为LOU 当前给与提示
                return WriteResultRespDTO.failResultRespDTO("当前BOM结构中已存在LOU，不可重复设置!");
            }else if(Integer.valueOf(1).equals(record.getpLouaFlag())){//之前设置为LOU 现在改为取消设置
                records.forEach(record1 -> {
                    record1.setpLouaFlag(2);
                });
            }else {//现在需要设置为LOU
                setLou = true;
                records.forEach(record1 -> {
                    if(record1.equals(record)){
                        record1.setpLouaFlag(1);
                    }else {
                        record1.setpLouaFlag(0);
                    }
                });
            }

            //PBOM 同步设置
            List<HzPbomLineRecord> setLouRecords = new ArrayList<>();
            if(pbomLineRecord != null){
                HzPbomTreeQuery hzPbomTreeQuery = new HzPbomTreeQuery();
                hzPbomTreeQuery.setPuid(puid);
                hzPbomTreeQuery.setProjectId(reqDTO.getProjectId());

                List<HzPbomLineRecord> pbomLineRecords = hzPbomRecordDAO.getHzPbomTree(hzPbomTreeQuery);

                if(ListUtil.isNotEmpty(pbomLineRecords)){
                    for(HzPbomLineRecord lineRecord : pbomLineRecords){
                        if(Integer.valueOf(0).equals(pbomLineRecord.getpLouaFlag())){//它的父层设置为LOU 不进行任何操作
                            break;
                        }else if(Integer.valueOf(1).equals(pbomLineRecord.getpLouaFlag())){//之前设置为LOU 现在改为取消设置
                            if(!setLou){//EBOM 取消设置
                                lineRecord.setpLouaFlag(2);
                                setLouRecords.add(lineRecord);
                            }
                        }else {//现在需要设置为LOU
                            if(setLou){//EBOM设置
                                if (lineRecord.equals(pbomLineRecord)) {
                                    lineRecord.setpLouaFlag(1);
                                } else {
                                    lineRecord.setpLouaFlag(0);
                                }
                                setLouRecords.add(lineRecord);
                            }
                        }
                    }

                }
            }

            if(ListUtil.isNotEmpty(records)){
                hzEbomRecordDAO.updateListByPuids(records);
            }
            if(ListUtil.isNotEmpty(setLouRecords)){
                hzPbomRecordDAO.updateListByPuids(setLouRecords);
            }
            return WriteResultRespDTO.getSuccessResult();
        } catch (Exception e) {
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
        HzMainBom projectRecord = hzMainBomDao.selectByProjectId(projectId);
        if(projectRecord == null){
            return WriteResultRespDTO.failResultRespDTO("当前项目不存在！");
        }
        String bomDigifaxId =projectRecord.getPuid();
        //当前BOM数据是2Y层BOM
        HzEPLManageRecord record = HzEbomRecordFactory.addEbomTOEbomRecord(reqDTO,hzEPLRecord.getId(),1,1,bomDigifaxId);

        try {

            List<HzEPLManageRecord> addChildEBOMList = new ArrayList<>();//EBOM新增
            List<HzPbomLineRecord> addChildPBOMList = new ArrayList<>();//PBOM 新增子

            //记录子PBOM属于哪个父PBOM  key ebompuid(pbom的ebomid) value 子对象集合 目的是为了 排序号生成和查找编号累加
            Map<String,List<HzPbomLineRecord>> pbomMap= new HashMap<>();

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

            List<HzPbomLineRecord> addPBOMList=new ArrayList<HzPbomLineRecord>();
            HzPbomLineRecord pbomRecord=null;
            List carParts = hzMbomService.loadingCarPartType();
            if(carParts.contains(hzEPLRecord.getPartResource())){
                pbomRecord = HzPbomRecordFactory.editEbomToPbomRecord(hzEPLRecord,record);
            }
            addPBOMList.add(pbomRecord);

            List<HzEPLManageRecord>  ebomRecord= hzEbomRecordDAO.findBaseEbomById(reqDTO.getLineId(), projectId);

            if (ebomRecord!=null&&ebomRecord.size()>0){
                //设置新增的子属性
                thisHzEbom=record;
                thisHzPbom=pbomRecord;
                setNewHzChildEbomUids2Y(ebomRecord.get(0),addChildEBOMList,addChildPBOMList,record,pbomRecord,projectId);
            }


            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {

                    hzEbomRecordDAO.insert(record);
                    if (ListUtil.isNotEmpty(addChildEBOMList)){
                        hzEbomRecordDAO.insertList(addChildEBOMList,ChangeTableNameEnum.HZ_EBOM.getTableName());
                    }
                    if( ListUtil.isNotEmpty(addPBOMList)){
                        hzPbomRecordDAO.insert(addPBOMList.get(0));
                        if (ListUtil.isNotEmpty(addChildPBOMList)){
                            hzPbomRecordDAO.insertList(addChildPBOMList);
                        }
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
            HzMainBom projectRecord = hzMainBomDao.selectByProjectId(projectId);
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

            List<HzEPLManageRecord>  eboms= hzEbomRecordDAO.findBaseEbomById(reqDTO.getLineId(), projectId);
            if (eboms != null && eboms.size() > 0) {
                HzEbomTreeQuery queryebom = new HzEbomTreeQuery();
                queryebom.setProjectId(reqDTO.getProjectId());
                queryebom.setPuid(eboms.get(0).getPuid());
                //添加的子
                List<HzEPLManageRecord> hzBomLineChildren = hzEbomRecordDAO.getHzBomLineChildren(queryebom);

                //判断子中是否存在父
                for (HzEPLManageRecord hzEPLManageRecordChildren : hzBomLineChildren) {
                    if (hzEPLManageRecordChildren.getPuid().equals(list.get(0).getPuid())) {
                        return WriteResultRespDTO.failResultRespDTO("存在循环结构，不能添加");
                    }
                }
            }



            //记录EBOM的插入操作 key parentId value eBomPuid 目的是为了 PBOM插入操作时 进行eBomPuid 匹配
            Map<String,String> map = new HashMap<>();
            //记录子PBOM属于哪个父PBOM  key ebompuid(pbom的ebomid) value 子对象集合 目的是为了 排序号生成和查找编号累加
            Map<String,List<HzPbomLineRecord>> pbomMap= new HashMap<>();


            List<HzEPLManageRecord> addEBOMList = new ArrayList<>();//EBOM 新增
            List<HzEPLManageRecord> addChildEBOMList = new ArrayList<>();//EBOM新增子
            List<HzEPLManageRecord> updateEBOMList = new ArrayList<>();//EBOM更新

            List<HzPbomLineRecord> addPBOMList = new ArrayList<>();//PBOM 新增
            List<HzPbomLineRecord> addChildPBOMList = new ArrayList<>();//PBOM 新增子
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

                List<HzPbomLineRecord> pbomInParentList=new ArrayList<HzPbomLineRecord>();

                List<HzEPLManageRecord>  ebomRecord= hzEbomRecordDAO.findBaseEbomById(reqDTO.getLineId(), projectId);

                if (ebomRecord!=null&&ebomRecord.size()>0){
                    //设置新增的子属性
                    thisHzEbom=insertRecord;
                    setNewHzChildEbomUids(ebomRecord.get(0),addChildEBOMList,addChildPBOMList,pbomInParentList,insertRecord,projectId,"");
                }
                pbomMap.put(parent.getPuid(),pbomInParentList);

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

                    List<HzPbomLineRecord> hzPbomLineRecords = pbomMap.get(parent.geteBomPuid());
                    if (ListUtil.isNotEmpty(hzPbomLineRecords)){
                        HzPbomLineRecord thisPbom=insertRecord;
                        for (HzPbomLineRecord pbomline :hzPbomLineRecords){
                            String chiLineIndex = insertRecord.getLineIndex()+pbomline.getLineIndex();

                            String pbomSortNum=pbomline.getSortNum();
                            String sortNo = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,thisPbom.getSortNum());
                            if(sortNo == null){
                                pbomSortNum = String.valueOf(Double.parseDouble(thisPbom.getSortNum())+100);
                            }else {
                                pbomSortNum = HzBomSysFactory.generateBomSortNum(thisPbom.getSortNum(),sortNo);
                            }
                            thisPbom=pbomline;
                            pbomline.setLineIndex(chiLineIndex);
                            pbomline.setSortNum(pbomSortNum);
                        }
                    }

                }
            }


            configTransactionTemplate.execute(new TransactionCallback<Void>() {
                @Override
                public Void doInTransaction(TransactionStatus status) {
                    if(ListUtil.isNotEmpty(addEBOMList)){
                        hzEbomRecordDAO.insertList(addEBOMList,ChangeTableNameEnum.HZ_EBOM.getTableName());
                        if (ListUtil.isNotEmpty(addChildEBOMList)){
                            hzEbomRecordDAO.insertList(addChildEBOMList,ChangeTableNameEnum.HZ_EBOM.getTableName());
                        }
                    }

                    if(ListUtil.isNotEmpty(updateEBOMList)){
                        hzEbomRecordDAO.updateListByPuids(updateEBOMList);
                    }
                    if(ListUtil.isNotEmpty(addPBOMList)){
                        hzPbomRecordDAO.insertList(addPBOMList);
                        if (ListUtil.isNotEmpty(addChildPBOMList)){
                            hzPbomRecordDAO.insertList(addChildPBOMList);
                        }
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

/**
 * 递归设置新子ebom的UID等
 * @param oldRecordEbom 原父ebom的实体类
 * @param addChildEBOMList 添加的ebom集合
 * @param projectId 项目的的UID
 */
    /**
     * 递归设置新子ebom的UID等
     * @param oldRecordEbom 原父ebom的实体类
     * @param addChildEBOMList 要添加的子ebom集合
     * @param addChildPBOMList 要添加的子pbom集合
     * @param pbomInParentList 单个父对应的所有子pbom
     * @param insertRecord 父Ebom
     * @param projectId 项目号
     * @param pbomlineIndex pbom的查找编号(未累加前)
     */
    private void  setNewHzChildEbomUids(HzEPLManageRecord oldRecordEbom,List<HzEPLManageRecord> addChildEBOMList,List<HzPbomLineRecord> addChildPBOMList,List<HzPbomLineRecord> pbomInParentList,HzEPLManageRecord insertRecord ,String projectId,String pbomlineIndex){
        String ebomPuid= insertRecord.getPuid();
        String ebomlineIndex=insertRecord.getLineIndex();
        List<HzEPLManageRecord> recordEbomList=null;
        if (oldRecordEbom!=null){
            HzBOMQuery hzBOMQuery = new HzBOMQuery();
            hzBOMQuery.setParentId(oldRecordEbom.getPuid());
            hzBOMQuery.setProjectId(projectId);
            recordEbomList =hzEbomRecordDAO.findNextLevelRecordByParentId(hzBOMQuery);
        }
       if (recordEbomList!=null&&recordEbomList.size()>0){
            //设置是否父层
           insertRecord.setIsHas(1);

           for (HzEPLManageRecord chiRecord:recordEbomList) {
               if(oldRecordEbom.getPuid().equals(chiRecord.getParentUid())){
                   //生成新的子Ebomline
                   HzEPLManageRecord newChiRecord=(HzEPLManageRecord)chiRecord.clone();
                  String newEbomlineEnd=newChiRecord.getLineIndex().split("\\.")[newChiRecord.getLineIndex().split("\\.").length-1];
                  String newebomlineIndex= ebomlineIndex+"."+newEbomlineEnd;
                   String newEbomPuid=UUID.randomUUID().toString();
                   String sortNum=insertRecord.getSortNum();
                   String sortNo = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,thisHzEbom.getSortNum());
                   if(sortNo == null){
                       sortNum = String.valueOf(Double.parseDouble(thisHzEbom.getSortNum())+100);
                   }else {
                       sortNum = HzBomSysFactory.generateBomSortNum(thisHzEbom.getSortNum(),sortNo);
                   }
                   thisHzEbom=newChiRecord;
                   newChiRecord.setParentUid(ebomPuid);
                   newChiRecord.setPuid(newEbomPuid);
                   newChiRecord.setLineIndex(newebomlineIndex);
                   newChiRecord.setSortNum(sortNum);
                   addChildEBOMList.add(newChiRecord);

                   //获得新的子Pbomline
                   String newpbomlineIndex=null;
                   if (pbomlineIndex!=null){
                       HzPbomLineRecord oldHzPbomByEbom = hzPbomRecordDAO.getHzPbomByEbomPuid(chiRecord.getPuid(), projectId);
                       if (oldHzPbomByEbom!=null){
                           HzPbomLineRecord newChiPbom=(HzPbomLineRecord)oldHzPbomByEbom.clone();
                           String newPbomlineEnd=newChiPbom.getLineIndex().split("\\.")[newChiPbom.getLineIndex().split("\\.").length-1];
                           newpbomlineIndex= pbomlineIndex+"."+newPbomlineEnd;
                           newChiPbom.setPuid(UUID.randomUUID().toString());
                           newChiPbom.setParentUid(ebomPuid);
                           newChiPbom.seteBomPuid(newEbomPuid);
                           newChiPbom.setLineIndex(newpbomlineIndex);
                           addChildPBOMList.add(newChiPbom);
                           pbomInParentList.add(newChiPbom);
                       }
                   }

                   //递归获得所有子
                   setNewHzChildEbomUids(chiRecord,addChildEBOMList,addChildPBOMList,pbomInParentList,newChiRecord,projectId,newpbomlineIndex);

               }

           }
       }

    };

    HzEPLManageRecord thisHzEbom;
    HzPbomLineRecord thisHzPbom;

    /**
     * 递归设置新子ebom的UID等2Y层
     * @param oldRecordEbom 原父ebom的实体
     * @param addChildEBOMList 要添加的子ebom集合
     * @param addChildPBOMList 要添加的子pbom集合
     * @param insertRecord 父Ebom
     * @param pbomInParent 父Pbom
     * @param projectId 项目号
     */
    private void  setNewHzChildEbomUids2Y(HzEPLManageRecord oldRecordEbom,List<HzEPLManageRecord> addChildEBOMList,List<HzPbomLineRecord> addChildPBOMList,HzEPLManageRecord insertRecord ,HzPbomLineRecord pbomInParent,String projectId){
        String ebomPuid= insertRecord.getPuid();
        String ebomlineIndex=insertRecord.getLineIndex();
        List<HzEPLManageRecord> recordEbomList=null;
        if (oldRecordEbom!=null){
            HzBOMQuery hzBOMQuery = new HzBOMQuery();
            hzBOMQuery.setParentId(oldRecordEbom.getPuid());
            hzBOMQuery.setProjectId(projectId);
            recordEbomList =hzEbomRecordDAO.findNextLevelRecordByParentId(hzBOMQuery);
        }
        if (recordEbomList!=null&&recordEbomList.size()>0){
            //设置是否父层
            insertRecord.setIsHas(1);

            for (HzEPLManageRecord chiRecord:recordEbomList) {
                if(oldRecordEbom.getPuid().equals(chiRecord.getParentUid())){
                    //生成新的子Ebomline
                    HzEPLManageRecord newChiRecord=(HzEPLManageRecord)chiRecord.clone();
                    String newEbomlineEnd=newChiRecord.getLineIndex().split("\\.")[newChiRecord.getLineIndex().split("\\.").length-1];
                    String newebomlineIndex= ebomlineIndex+"."+newEbomlineEnd;
                    String newEbomPuid=UUID.randomUUID().toString();
                    String sortNum=insertRecord.getSortNum();
                    String sortNo = hzEbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,thisHzEbom.getSortNum());
                    if(sortNo == null){
                        sortNum = String.valueOf(Double.parseDouble(thisHzEbom.getSortNum())+100);
                    }else {
                        sortNum = HzBomSysFactory.generateBomSortNum(thisHzEbom.getSortNum(),sortNo);
                    }
                    thisHzEbom=newChiRecord;
                    newChiRecord.setParentUid(ebomPuid);
                    newChiRecord.setPuid(newEbomPuid);
                    newChiRecord.setLineIndex(newebomlineIndex);
                    newChiRecord.setSortNum(sortNum);
                    addChildEBOMList.add(newChiRecord);

                    HzPbomLineRecord newChiPbom=null;
                    //获得新的子Pbomline
                    String newpbomlineIndex=null;
                    if (pbomInParent!=null){
                        HzPbomLineRecord oldHzPbomByEbom = hzPbomRecordDAO.getHzPbomByEbomPuid(chiRecord.getPuid(), projectId);
                        if (oldHzPbomByEbom!=null){
                            newChiPbom=(HzPbomLineRecord)oldHzPbomByEbom.clone();
                            String newPbomlineEnd=newChiPbom.getLineIndex().split("\\.")[newChiPbom.getLineIndex().split("\\.").length-1];
                            newpbomlineIndex= pbomInParent.getLineIndex()+"."+newPbomlineEnd;
                            String chiLineIndex = pbomInParent.getLineIndex()+"."+newPbomlineEnd;;
                            String pbomSortNum=pbomInParent.getSortNum();
                            String pbomSortNo = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectId,thisHzPbom.getSortNum());
                            if(pbomSortNo == null){
                                pbomSortNum = String.valueOf(Double.parseDouble(thisHzPbom.getSortNum())+100);
                            }else {
                                pbomSortNum = HzBomSysFactory.generateBomSortNum(thisHzPbom.getSortNum(),pbomSortNo);
                            }
                            thisHzPbom=newChiPbom;

                            newChiPbom.setPuid(UUID.randomUUID().toString());
                            newChiPbom.setParentUid(ebomPuid);
                            newChiPbom.seteBomPuid(newEbomPuid);
                            newChiPbom.setLineIndex(newpbomlineIndex);
                            newChiPbom.setSortNum(pbomSortNum);

                            addChildPBOMList.add(newChiPbom);
                        }
                    }

                    //递归获得所有子
                    setNewHzChildEbomUids2Y(chiRecord,addChildEBOMList,addChildPBOMList,newChiRecord,newChiPbom,projectId);

                }

            }
        }

    };
}
