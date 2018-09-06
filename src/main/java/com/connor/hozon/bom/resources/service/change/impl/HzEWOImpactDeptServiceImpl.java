package com.connor.hozon.bom.resources.service.change.impl;


import com.connor.hozon.bom.resources.domain.dto.request.EditEWOImpactDeptReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.EditImpactDeptEmpReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEWOImpactDeptEmpRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEWOImpactDeptRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEWOImpactDeptQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactDeptDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactDeptEmpDAO;
import com.connor.hozon.bom.resources.service.change.HzEWOImpactDeptService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.dao.OrgGroupDao;
import com.connor.hozon.bom.sys.dao.UserDao;
import com.connor.hozon.bom.sys.entity.OrgGroup;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.change.HzEWOAllImpactDept;
import sql.pojo.change.HzEWOImpactDept;
import sql.pojo.change.HzEWOImpactDeptEmp;

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

    @Autowired
    private HzEWOImpactDeptEmpDAO hzEWOImpactDeptEmpDAO;

    @Autowired
    private OrgGroupDao orgGroupDao;

    @Autowired
    private UserDao userDao;

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
        try{
            List<String> deptIds = Arrays.asList(reqDTO.getDeptIds().split(","));
            List<String> userIds = Arrays.asList(reqDTO.getUserIds().split(","));
            if(ListUtil.isEmpty(userIds) || ListUtil.isEmpty(deptIds) || deptIds.size() != userIds.size()){
                return OperateResultMessageRespDTO.IllgalArgument();
            }
            HzEWOImpactDeptQuery query = new HzEWOImpactDeptQuery();
            query.setProjectId(reqDTO.getProjectId());
            query.setEwoNo(reqDTO.getEwoNo());
            List<HzEWOImpactDeptEmp> insertList = new ArrayList<>();
            List<HzEWOImpactDeptEmp> updateList = new ArrayList<>();
            List<HzEWOImpactDeptEmp> list = hzEWOImpactDeptEmpDAO.findImpactDeptList(query);
            for(int i= 0 ;i<userIds.size();i++){
                if(ListUtil.isNotEmpty(list)){
                    for(HzEWOImpactDeptEmp ewoImpactDeptEmp :list){
                        if(ewoImpactDeptEmp.getImpactDeptId().equals(deptIds.get(i))){
                            list.remove(ewoImpactDeptEmp);
                        }
                    }
                }
                query.setDeptId(Long.valueOf(deptIds.get(i)));
                HzEWOImpactDeptEmp emp = new HzEWOImpactDeptEmp();
                emp.setEwoNo(reqDTO.getEwoNo());
                emp.setImpactDeptId(Long.valueOf(deptIds.get(i)));
                emp.setProjectId(reqDTO.getProjectId());
                emp.setValidFlag(1);
                emp.setUserId(Long.valueOf(userIds.get(i)));
                List<HzEWOImpactDeptEmp> impactDeptList = hzEWOImpactDeptEmpDAO.findImpactDeptList(query);
                if(ListUtil.isEmpty(impactDeptList)){
                    insertList.add(emp);
                }else {
                    if(impactDeptList.get(0).getValidFlag().equals(0)){
                        impactDeptList.get(0).setValidFlag(1);
                        updateList.add(impactDeptList.get(0));
                    }
                }
            }

            if(ListUtil.isNotEmpty(insertList)){
                hzEWOImpactDeptEmpDAO.insertList(insertList);
            }
            if(ListUtil.isNotEmpty(updateList)){
                hzEWOImpactDeptEmpDAO.updateList(updateList);
            }
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public List<HzEWOImpactDeptRespDTO> getAllImpactDept(HzEWOImpactDeptQuery query) {
        try{
            List<HzEWOImpactDept> depts = hzEWOImpactDeptDAO.findEWOImpactDeptList(query);
            List<OrgGroup> list = orgGroupDao.queryAllOrgGroup();
            List<HzEWOImpactDeptRespDTO> respDTOS = new ArrayList<>();
            list.forEach(l->{
            HzEWOImpactDeptRespDTO respDTO = new HzEWOImpactDeptRespDTO();
            respDTO.setDeptId(l.getGroupId());
            respDTO.setDeptName(l.getName());
            respDTO.setChecked(0);
            if(ListUtil.isNotEmpty(depts)){
              for(HzEWOImpactDept dept:depts){
                  if(dept.getDeptId().equals(l.getGroupId())){
                      if(dept.getCheckFlag().equals(1)){
                          respDTO.setChecked(1);
                          respDTO.setId(dept.getId());
                      }
                      break;
                  }
              }
            }
            respDTOS.add(respDTO);
            });

           return respDTOS;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<HzEWOImpactDeptEmpRespDTO> getAllImpactDeptEmp(HzEWOImpactDeptQuery query) {
        try {
            List<HzEWOImpactDeptEmpRespDTO>  respDTOS = new ArrayList<>();
            List<HzEWOImpactDeptEmp> list = hzEWOImpactDeptEmpDAO.findImpactDeptList(query);
            List<HzEWOAllImpactDept> allDept = hzEWOImpactDeptDAO.findEWOAllImpactDept();
            allDept.forEach(hzEWOAllImpactDept -> {
                HzEWOImpactDeptEmpRespDTO respDTO = new HzEWOImpactDeptEmpRespDTO();
                respDTO.setDeptId(hzEWOAllImpactDept.getId());
                respDTO.setDeptName(hzEWOAllImpactDept.getDeptName());
                respDTO.setChecked(0);
                if(ListUtil.isNotEmpty(list)){
                    for(HzEWOImpactDeptEmp emp:list){
                        if(emp.getImpactDeptId().equals(hzEWOAllImpactDept.getId())){
                            respDTO.setUserId(emp.getUserId());
                            respDTO.setChecked(1);
                            User user = userDao.findUserById(emp.getUserId());
                            if(user!=null){
                                respDTO.setUserName(user.getUserName());
                                respDTO.setLogin(user.getUsername());
                            }
                            break;
                        }
                    }
                }
                respDTOS.add(respDTO);
            });
            return respDTOS;
        }catch (Exception e){
            return  null;
        }
    }
}
