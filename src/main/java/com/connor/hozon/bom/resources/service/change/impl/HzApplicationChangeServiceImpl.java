package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzChangeOrderFactory;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzApplicantChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.change.HzApplicationChangeService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.change.HzApplicantChangeRecord;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeOrderRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("HzApplicationChangeService")
public class HzApplicationChangeServiceImpl implements HzApplicationChangeService {
    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;
    @Autowired
    private HzApplicantChangeDAO hzApplicantChangeDAO;
    @Autowired
    private HzAuditorChangeDAO hzAuditorChangeDAO;
    @Override
    public List<HzChangeOrderRespDTO> findChangeOrderList(HzChangeOrderByPageQuery query, HzApplicantChangeRecord record) {
        List<HzChangeOrderRespDTO> auditorList = new ArrayList<>();
        try{
            List<HzApplicantChangeRecord> infos =  hzApplicantChangeDAO.findApplicantionList(record);
            if(ListUtil.isNotEmpty(infos)){
                for(int i=0;i<infos.size();i++){
                    //根据返回的OrderId到ChangeOrder表中查数据
                    HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordById(query,infos.get(i).getOrderId());
                    if(rec!=null){
                        HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
                        respDTO.setProjectId(rec.getProjectId());//
                        respDTO.setChangeNo(rec.getChangeNo());//changeNo
                        respDTO.setId(rec.getId());
                        respDTO.setOriginator(rec.getOriginator());//发起人
                        respDTO.setDeptName(rec.getDeptName());//部门-deptName
                        respDTO.setChangeType(rec.getChangeType());//变更类型
                        respDTO.setOriginTime(DateUtil.formatTimestampDate(rec.getOriginTime()));//发起时间
                        respDTO.setProjectName(rec.getProjectName());
                        respDTO.setSource("BOM");
                        respDTO.setState(rec.getState());//变更单状态
                        respDTO.setAuditId(infos.get(i).getAuditRecordId());//改成auditRecordId-关联审核表ID查看审批意见
                        //审批时间
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        List<HzAuditorChangeRecord> records = hzAuditorChangeDAO.findAuditorListById(infos.get(i).getAuditRecordId());
                        if(records.get(0).getAuditTime()!=null)
                            respDTO.setAuditTime(formatter.format(records.get(0).getAuditTime()));
                        else
                            respDTO.setAuditTime("");

                        auditorList.add(respDTO);
                    }
                }
                return auditorList;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public Page<HzChangeOrderRespDTO> getHzChangeOrderPage(HzChangeOrderByPageQuery query, HzApplicantChangeRecord rec) {
        try {
            List<HzChangeOrderRespDTO> respDTOS = new ArrayList<>();
            Page<HzChangeOrderRecord> page = hzChangeOrderDAO.findHzChangeOrderRecordByPageId(query);
            if (page == null || page.getResult() == null || page.getResult().size() == 0) {
                return new Page<>(page.getPageNumber(), page.getPageSize(), 0);
            }
            List<HzChangeOrderRecord> records = page.getResult();
            for(HzChangeOrderRecord record :records){
                HzChangeOrderRespDTO respDTO = HzChangeOrderFactory.changeOrderRecordToRespDTO(record);
                respDTOS.add(respDTO);
            }
            return new Page<>(page.getPageNumber(), page.getPageSize(), page.getTotalCount(), respDTOS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Page<>(query.getPage(), query.getPageSize(), 0);
        }
    }

}
