/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.data.impl;

import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzChangeOrderFactory;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzAuditorChangeDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.service.change.data.HzAuditorChangeService;
import com.connor.hozon.bom.resources.util.DateUtil;
import cn.net.connor.hozon.common.util.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.change.change.HzAuditorChangeRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class HzAuditorChangeServiceImpl implements HzAuditorChangeService {
    @Autowired
    private HzAuditorChangeDAO hzAuditorChangeDAO;

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    @Override
    public List<HzChangeOrderRespDTO> findChangeOrderList(HzChangeOrderByPageQuery query, HzAuditorChangeRecord record) {
        List<HzChangeOrderRespDTO> auditorList = new ArrayList<>();
        try{
            //从HZ_AUDITOR_CHANGE_RECORD表中查找任务条数:AUDIT_RESULT is null
            List<HzAuditorChangeRecord> infos =  hzAuditorChangeDAO.findAuditorList(record);

            if(ListUtils.isNotEmpty(infos)){
                for(int i=0;i<infos.size();i++){
                    HzChangeOrderRecord temp = hzChangeOrderDAO.findHzChangeOrderRecordById(infos.get(i).getOrderId());

                    if(temp.getFromTc()==0){
                        //变更单从BOM系统创建
                        //根据返回的OrderId到ChangeOrder表中查数据
                        HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordById(query,infos.get(i).getOrderId());
                        if(rec!=null){
                            HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
                            respDTO.setSource("BOM");
                            respDTO.setProjectId(rec.getProjectId());
                            respDTO.setChangeNo(rec.getChangeNo());//changeNo
                            respDTO.setId(rec.getId());
                            respDTO.setOriginator(rec.getOriginator());//流程发起人
                            respDTO.setDeptName(rec.getDeptName());//部门-deptName
                            respDTO.setChangeType(rec.getChangeType());//变更类型
                            respDTO.setOriginTime(DateUtil.formatTimestampDate(rec.getOriginTime()));//originTime
                            respDTO.setProjectName(rec.getProjectName());//项目名
                            respDTO.setState(rec.getState());//变更单状态
                            respDTO.setIsFromTc(rec.getFromTc());//是否来着TC数据，用于“待办事项”中接口人手动完成任务
                            auditorList.add(respDTO);
                        }
                    }else{
                        //变更单从TC同步
                        //根据返回的OrderId到ChangeOrder表中查数据
                        HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordByIdTC(query,infos.get(i).getOrderId());
                        if(rec!=null){
                            HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
                            respDTO.setSource("TC");
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
                            respDTO.setState(rec.getState());
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

            /*if(ListUtils.isNotEmpty(infos)){
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
            if(ListUtils.isNotEmpty(infos)){
                for(int i=0;i<infos.size();i++){
                    HzChangeOrderRecord temp = hzChangeOrderDAO.findHzChangeOrderRecordById(infos.get(i).getOrderId());

                    //根据返回的OrderId到ChangeOrder表中查数据
                    HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordById(query,infos.get(i).getOrderId());

                    if(temp.getFromTc()==0){//变更单从BOM系统创建
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
                            respDTO.setSource("BOM");
                            respDTO.setState(rec.getState());//变更单状态
                            //审批时间
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //respDTO.setAuditTime(formatter.format(infos.get(i).getAuditTime()));
                            if(infos.get(i).getAuditTime()!=null)
                                respDTO.setAuditTime(formatter.format(infos.get(i).getAuditTime()));
                            else
                                respDTO.setAuditTime("");

                            respDTO.setAuditId(infos.get(i).getId());//关联HzAuditorChangeRecord-ID用于“已处理事项”查看评估意见
                            auditorList.add(respDTO);
                        }
                    }else{
                        //变更单从TC同步
                        //重写个服务
                        //根据返回的OrderId到ChangeOrder表中查数据
                        //HzChangeOrderRecord rec = hzChangeOrderDAO.findHzChangeOrderRecordByIdTC(query,infos.get(i).getOrderId());
                        if(rec==null){
                            rec = hzChangeOrderDAO.findHzChangeOrderRecordByIdTC(query,infos.get(i).getOrderId());
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
                            respDTO.setSource("TC");
                            respDTO.setState(rec.getState());//变更单状态
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            //respDTO.setAuditTime(formatter.format(infos.get(i).getAuditTime()));
                            if(infos.get(i).getAuditTime()!=null)
                                respDTO.setAuditTime(formatter.format(infos.get(i).getAuditTime()));
                            else
                                respDTO.setAuditTime("");
                            respDTO.setAuditId(infos.get(i).getId());
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
    public Page<HzChangeOrderRespDTO> getHzChangeOrderPageUn(HzChangeOrderByPageQuery query, HzAuditorChangeRecord chrecord) {
        try {
            List<HzChangeOrderRespDTO> respDTOS = new ArrayList<>();
            Page<HzChangeOrderRecord> page = hzChangeOrderDAO.findHzChangeOrderRecordByPageUn(query);
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

    @Override
    public Page<HzChangeOrderRespDTO> getHzChangeOrderPagePr(HzChangeOrderByPageQuery query, HzAuditorChangeRecord chrecord) {
        try {
            List<HzChangeOrderRespDTO> respDTOS = new ArrayList<>();
            Page<HzChangeOrderRecord> page = hzChangeOrderDAO.findHzChangeOrderRecordByPagePr(query);
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
