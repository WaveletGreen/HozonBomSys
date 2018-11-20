package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzApplicantChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.service.change.HzApplicationChangeService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.change.HzApplicantChangeRecord;
import sql.pojo.change.HzChangeOrderRecord;

import java.util.ArrayList;
import java.util.List;

@Service("HzApplicationChangeService")
public class HzApplicationChangeServiceImpl implements HzApplicationChangeService {
    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;
    @Autowired
    private HzApplicantChangeDAO hzApplicantChangeDAO;
    @Override
    public List<HzChangeOrderRespDTO> findChangeOrderList(HzChangeOrderByPageQuery query, HzApplicantChangeRecord record) {
        List<HzChangeOrderRespDTO> auditorList = new ArrayList<>();
        try{
            List<HzApplicantChangeRecord> infos =  hzApplicantChangeDAO.findAuditorList(record);

            if(ListUtil.isNotEmpty(infos)){
                for(int i=0;i<infos.size();i++){
                    //根据返回的OrderId到ChangeOrder表中查数据
                    HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordById(query,infos.get(i).getOrderId());
                    if(rec!=null){
                        HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
                        respDTO.setProjectId(rec.getProjectId());//?
                        respDTO.setChangeNo(rec.getChangeNo());//changeNo
                        respDTO.setId(rec.getId());
                        respDTO.setOriginator(rec.getOriginator());
                        respDTO.setDeptName(rec.getDeptName());//部门-deptName
                        respDTO.setChangeType(rec.getChangeType());//变更类型
                        respDTO.setOriginTime(DateUtil.formatTimestampDate(rec.getOriginTime()));//originTime
                        //respDTO.setLaunchTime(rec.getLaunchTime());
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
}
