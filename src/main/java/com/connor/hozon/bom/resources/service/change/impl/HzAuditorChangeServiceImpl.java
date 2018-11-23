package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.service.change.HzAuditorChangeService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.change.HzAuditorChangeRecord;
import sql.pojo.change.HzChangeOrderRecord;

import java.util.ArrayList;
import java.util.List;

@Service("HzAuditorChangeService")
public class HzAuditorChangeServiceImpl implements HzAuditorChangeService {
    @Autowired
    private HzAuditorChangeDAO hzAuditorChangeDAO;

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    @Override
    public List<HzChangeOrderRespDTO> findChangeOrderList(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record) {
        List<HzChangeOrderRespDTO> auditorList = new ArrayList<>();
        try{
            //从HZ_AUDITOR_CHANGE_RECORD表中查找任务条数
            List<HzAuditorChangeRecord> infos =  hzAuditorChangeDAO.findAuditorList(record);

            if(ListUtil.isNotEmpty(infos)){
                for(int i=0;i<infos.size();i++){
                    HzChangeOrderRecord temp = hzChangeOrderDAO.findHzChangeOrderRecordById(infos.get(i).getOrderId());

                    if(temp.getFromTc()==0){
                        //变更单从BOM系统创建
                        //根据返回的OrderId到ChangeOrder表中查数据
                        HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordById(query,infos.get(i).getOrderId());
                        if(rec!=null){
                            HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
                            respDTO.setProjectId(rec.getProjectId());
                            respDTO.setChangeNo(rec.getChangeNo());//changeNo
                            respDTO.setId(rec.getId());
                            respDTO.setOriginator(rec.getOriginator());//流程发起人
                            respDTO.setDeptName(rec.getDeptName());//部门-deptName
                            respDTO.setChangeType(rec.getChangeType());//变更类型
                            respDTO.setOriginTime(DateUtil.formatTimestampDate(rec.getOriginTime()));//originTime
                            respDTO.setProjectName(rec.getProjectName());//项目名
                            auditorList.add(respDTO);
                        }
                    }else{
                        //变更单从TC同步
                        //重写个服务
                        //根据返回的OrderId到ChangeOrder表中查数据
                        HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordByIdTC(query,infos.get(i).getOrderId());
                        if(rec!=null){
                            HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
                            respDTO.setProjectId(rec.getProjectId());
                            respDTO.setChangeNo(rec.getChangeNo());//changeNo
                            respDTO.setId(rec.getId());
                            //respDTO.setOriginator(rec.getOriginator());//流程发起人
                            //respDTO.setDeptName(rec.getDeptName());//部门-deptName
                            respDTO.setOriginator(rec.getCreateNameTC());//流程发起人
                            respDTO.setDeptName(rec.getDeptNameTC());//部门-deptName
                            respDTO.setChangeType(rec.getChangeType());//变更类型
                            respDTO.setOriginTime(DateUtil.formatTimestampDate(rec.getOriginTime()));//originTime
                            respDTO.setProjectName(rec.getProjectName());//项目名
                            auditorList.add(respDTO);
                        }
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
    public List<HzChangeOrderRespDTO> findChangeOrderList2(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record) {
        List<HzChangeOrderRespDTO> auditorList = new ArrayList<>();
        try{
            List<HzAuditorChangeRecord> infos =  hzAuditorChangeDAO.findAuditorList2(record);

            /*if(ListUtil.isNotEmpty(infos)){
                for(int i=0;i<infos.size();i++){
                    //根据返回的OrderId到ChangeOrder表中查数据
                    HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordById(query,infos.get(i).getOrderId());
                    if(rec!=null){
                        HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
                        respDTO.setProjectId(rec.getProjectId());
                        respDTO.setChangeNo(rec.getChangeNo());//changeNo
                        respDTO.setId(rec.getId());
                        respDTO.setOriginator(rec.getOriginator());
                        respDTO.setDeptName(rec.getDeptName());//部门-deptName
                        respDTO.setChangeType(rec.getChangeType());//变更类型
                        respDTO.setOriginTime(DateUtil.formatTimestampDate(rec.getOriginTime()));//originTime
                        respDTO.setProjectName(rec.getProjectName());
                        auditorList.add(respDTO);
                    }

                }
                return auditorList;
            }*/
            if(ListUtil.isNotEmpty(infos)){
                for(int i=0;i<infos.size();i++){
                    HzChangeOrderRecord temp = hzChangeOrderDAO.findHzChangeOrderRecordById(infos.get(i).getOrderId());

                    if(temp.getFromTc()==0){//变更单从BOM系统创建
                        //根据返回的OrderId到ChangeOrder表中查数据
                        HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordById(query,infos.get(i).getOrderId());
                        if(rec!=null){
                            HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
                            respDTO.setProjectId(rec.getProjectId());
                            respDTO.setChangeNo(rec.getChangeNo());//changeNo
                            respDTO.setId(rec.getId());
                            respDTO.setOriginator(rec.getOriginator());//流程发起人
                            respDTO.setDeptName(rec.getDeptName());//部门-deptName
                            respDTO.setChangeType(rec.getChangeType());//变更类型
                            respDTO.setOriginTime(DateUtil.formatTimestampDate(rec.getOriginTime()));//originTime
                            respDTO.setProjectName(rec.getProjectName());//项目名
                            auditorList.add(respDTO);
                        }
                    }else{
                        //变更单从TC同步
                        //重写个服务
                        //根据返回的OrderId到ChangeOrder表中查数据
                        HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordByIdTC(query,infos.get(i).getOrderId());
                        if(rec!=null){
                            HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
                            respDTO.setProjectId(rec.getProjectId());
                            respDTO.setChangeNo(rec.getChangeNo());//changeNo
                            respDTO.setId(rec.getId());
                            //respDTO.setOriginator(rec.getOriginator());//流程发起人
                            //respDTO.setDeptName(rec.getDeptName());//部门-deptName
                            respDTO.setOriginator(rec.getCreateNameTC());//流程发起人
                            respDTO.setDeptName(rec.getDeptNameTC());//部门-deptName
                            respDTO.setChangeType(rec.getChangeType());//变更类型
                            respDTO.setOriginTime(DateUtil.formatTimestampDate(rec.getOriginTime()));//originTime
                            respDTO.setProjectName(rec.getProjectName());//项目名
                            auditorList.add(respDTO);
                        }
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
