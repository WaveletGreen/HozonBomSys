package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.resources.dto.request.EditEWOImpactDeptReqDTO;
import com.connor.hozon.bom.resources.dto.request.EditImpactDeptEmpReqDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactDeptDAO;
import com.connor.hozon.bom.resources.query.HzEWOImpactDeptQuery;
import com.connor.hozon.bom.resources.service.change.HzEWOImpactDeptService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.change.HzEWOImpactDept;

import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
@Service("HzEWOImpactDeptService")
public class HzEWOImpactDeptServiceImpl implements HzEWOImpactDeptService {

    @Autowired
    private HzEWOImpactDeptDAO hzEWOImpactDeptDAO;

    @Override
    public OperateResultMessageRespDTO saveImpactDept(EditEWOImpactDeptReqDTO reqDTO) {
        try{
            //查询 无则新增 有则选择性更新状态
            String[] deptIds = reqDTO.getDeptIds().split(",");
            HzEWOImpactDeptQuery query = new HzEWOImpactDeptQuery();
            query.setEwoNo(reqDTO.getEwoNo());
            query.setProjectId(reqDTO.getProjectId());
            List<HzEWOImpactDept> depts = hzEWOImpactDeptDAO.findEWOImpactDeptList(query);
            Set<HzEWOImpactDept> set = new HashSet<>();
            if(ListUtil.isNotEmpty(depts)){
             set = new HashSet<>(depts);
            }
            List<HzEWOImpactDept> insertList = new ArrayList<>();

            List<HzEWOImpactDept> updateList = new ArrayList<>();

            for(String deptId : deptIds){
                for(HzEWOImpactDept hzEWOImpactDept :set){
                    if(hzEWOImpactDept.getDeptId().equals(Long.valueOf(deptId))){
                        set.remove(hzEWOImpactDept);
                        break;
                    }
                }
                query.setDeptId(Long.valueOf(deptId));
                List<HzEWOImpactDept> deptList = hzEWOImpactDeptDAO.findEWOImpactDeptList(query);
                HzEWOImpactDept dept = new HzEWOImpactDept();
                dept.setEwoNo(reqDTO.getEwoNo());
                dept.setCheckFlag(1);
                dept.setProjectId(reqDTO.getProjectId());
                dept.setDeptId(Long.valueOf(deptId));
                if(ListUtil.isEmpty(deptList)){
                    insertList.add(dept);
                }else {
                    HzEWOImpactDept dept1 = deptList.get(0);
                    if(Integer.valueOf(0).equals(dept1.getCheckFlag())){
                        updateList.add(dept);
                    }
                }
            }
            if(set.size()>0){
                for(HzEWOImpactDept dept :set){
                    dept.setCheckFlag(0);
                    updateList.add(dept);
                }
            }

            if(ListUtil.isNotEmpty(insertList)){
                hzEWOImpactDeptDAO.insertList(insertList);
            }
            if(ListUtil.isNotEmpty(updateList)){
                hzEWOImpactDeptDAO.updateList(updateList);
            }

        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getSuccessResult();
    }

    @Override
    public OperateResultMessageRespDTO saveImpactDeptEmp(EditImpactDeptEmpReqDTO reqDTO) {
        return null;
    }
}
