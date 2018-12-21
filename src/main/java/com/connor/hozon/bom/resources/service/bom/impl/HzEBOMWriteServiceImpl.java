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
import com.connor.hozon.bom.resources.domain.query.HzEBOMQuery;
import com.connor.hozon.bom.resources.domain.query.HzEPLQuery;
import com.connor.hozon.bom.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.bom.resources.service.bom.HzEBOMWriteService;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.exception.HzBomException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sql.pojo.bom.HZBomMainRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;
import sql.pojo.epl.HzEPLRecord;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/12/20
 * @Description:
 */
@Service("hzEBOMWriteService")
public class HzEBOMWriteServiceImpl implements HzEBOMWriteService {

    @Autowired
    private HzEPLDAO hzEPLDAO;

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzBomMainRecordDao hzBomMainRecordDao;

    @Autowired
    private HzMbomService hzMbomService;
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

            HZBomMainRecord projectRecord = hzBomMainRecordDao.selectByProjectPuid(projectId);
            if(projectRecord == null){
                return WriteResultRespDTO.failResultRespDTO("当前项目不存在！");
            }
            String bomDigifaxId =projectRecord.getPuid();

            String lineNo ="" ;//查找编号
            if (StringUtils.isNotBlank(reqDTO.getLineNo())) {
                lineNo = reqDTO.getLineNo().replaceFirst("^0*", "");
            }
            if(StringUtils.isBlank(parentId)){
                //当前BOM数据是2Y层BOM
                HzEPLManageRecord record = HzEbomRecordFactory.addEbomTOEbomRecord(reqDTO,hzEPLRecord.getId(),1,1,bomDigifaxId);

                Double maxOrderNum  = hzEbomRecordDAO.findMaxBomOrderNum(projectId);
                if(StringUtils.isNotBlank(lineNo)){//找合适的位置 大于当前lineNo的最小lineNo
                    String lineIndex = lineNo+"."+lineNo;
                    record.setLineIndex(lineIndex);
                    boolean repeat = hzEbomRecordDAO.lineIndexRepeat(projectId,lineIndex);
                    if(repeat){
                        return WriteResultRespDTO.failResultRespDTO("重复的查找编号输入！");
                    }
                    if(maxOrderNum == null){
                        record.setSortNum("100");
                    }else {
                        HzEBOMQuery hzEBOMQuery= new HzEBOMQuery();
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

                List<String> catPatrs  = hzMbomService.loadingCarPartType();
                HzPbomLineRecord pbomRecord = null;
                if(catPatrs.contains(hzEPLRecord.getPartResource())){
                    pbomRecord = HzPbomRecordFactory.editEbomToPbomRecord(hzEPLRecord,record);
                }
                hzEbomRecordDAO.insert(record);
                hzPbomRecordDAO.insert(pbomRecord);

            }else {
                //当前BOM数据 要添加到某个父层下面 为了保持结构的一致性 给所有相同零件号（eplId）下都添加该结构
            }

        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return WriteResultRespDTO.getFailResult();
        }

        return WriteResultRespDTO.getSuccessResult();
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
        return null;
    }
}
