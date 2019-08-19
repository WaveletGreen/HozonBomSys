package com.connor.hozon.resources.service.change.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.resources.domain.dto.request.EditHzChangeOrderReqDTO;
import com.connor.hozon.resources.domain.dto.response.HzChangeOrderRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.model.HzChangeOrderFactory;
import com.connor.hozon.resources.domain.query.HzChangeDataQuery;
import com.connor.hozon.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.change.HzChangeOrderService;
import com.connor.hozon.resources.util.PrivilegeUtil;
import com.connor.hozon.resources.util.StringUtil;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeDataRecord;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:
 */
@Service("hzChangeOrderService")
public class HzChangeOrderServiceImpl implements HzChangeOrderService {

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    @Autowired
    private HzChangeDataRecordDAO hzChangeDataRecordDAO;
    @Override
    public WriteResultRespDTO insertChangeOrderRecord(EditHzChangeOrderReqDTO reqDTO) {
        if(StringUtil.isEmpty(reqDTO.getProjectId())){
            return WriteResultRespDTO.IllgalArgument();
        }
        try {
            HzChangeOrderRecord record = HzChangeOrderFactory.reqDTOChangeRecord(reqDTO);
            int i = hzChangeOrderDAO.insert(record);
            if(i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

    @Override
    public WriteResultRespDTO updateChangeOrderRecord(EditHzChangeOrderReqDTO reqDTO) {
        if(reqDTO.getId() == null || reqDTO.getId()<=0){
            return WriteResultRespDTO.getFailResult();
        }
        try {
            //只有表单创建者 才可以修改表单信息
            HzChangeOrderRecord orderRecord = hzChangeOrderDAO.findHzChangeOrderRecordById(reqDTO.getId());

            WriteResultRespDTO resultRespDTO = new WriteResultRespDTO();
            if(orderRecord == null){
                resultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                resultRespDTO.setErrMsg("该变更表单信息不存在！");
                return resultRespDTO;
            }
            User user = UserInfo.getUser();
            if(!PrivilegeUtil.hasAdministratorPrivilege(user)){
                if(Integer.valueOf(0).equals(orderRecord.getFromTc())){
                    if(!orderRecord.getCreateId().equals(user.getId())){
                        resultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                        resultRespDTO.setErrMsg("TC端创建表单需要系统管理员权限，BOM端创建表单需要创建人或者系统管理员权限！");
                        return resultRespDTO;
                    }
                }
            }
            HzChangeOrderRecord record = HzChangeOrderFactory.reqDTOChangeRecord(reqDTO);
            int i = hzChangeOrderDAO.update(record);
            if(i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

    @Override
    public WriteResultRespDTO deleteChangeOrderById(Long id) {
        try {
            if(id == null || id<=0){
                return WriteResultRespDTO.IllgalArgument();
            }
            //变更表单关联数据后不允许删除
            HzChangeDataQuery query = new HzChangeDataQuery();
            query.setOrderId(id);
            List<HzChangeDataRecord> records = hzChangeDataRecordDAO.getChangeDataTableName(query);
            if(ListUtils.isNotEmpty(records)){
                WriteResultRespDTO respDTO = new WriteResultRespDTO();
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                respDTO.setErrMsg("表单中已存在关联数据,不允许删除!");
                return respDTO;
            }
            int i = hzChangeOrderDAO.deleteById(id);
            return i>0?WriteResultRespDTO.getSuccessResult():WriteResultRespDTO.getFailResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO changeNoExist(String changeNo) {
        try {
            boolean b = hzChangeOrderDAO.changeNoExist(changeNo);
            WriteResultRespDTO resultRespDTO = new WriteResultRespDTO();
            if(b){
                resultRespDTO.setErrMsg("当前变更单号已存在！");
                resultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return resultRespDTO;
            }
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }

        return WriteResultRespDTO.getSuccessResult();
    }

    @Override
    public Page<HzChangeOrderRespDTO> getHzChangeOrderPage(HzChangeOrderByPageQuery query) {
        try {
            List<HzChangeOrderRespDTO> respDTOS = new ArrayList<>();
            Page<HzChangeOrderRecord> page = hzChangeOrderDAO.findHzChangeOrderRecordByPage(query);
            if (page == null || page.getResult() == null || page.getResult().size() == 0) {
                return new Page<>(page.getPageNumber(), page.getPageSize(), 0);
            }
            List<HzChangeOrderRecord> records = page.getResult();
            for(HzChangeOrderRecord record :records){
                if(Integer.valueOf(1).equals(record.getFromTc())){
                    HzChangeOrderRespDTO respDTO = HzChangeOrderFactory.changeOrderRecordToRespDTO(record);
                    respDTO.setDeptName(record.getDeptNameTC());
                    respDTO.setCreateName(record.getCreateNameTC());
                    respDTOS.add(respDTO);
                }else {
//                    respDTOS.add(getHzChangeOrderRecordById(record.getId()));
                    respDTOS.add(HzChangeOrderFactory.changeOrderRecordToRespDTO(record));
                }
            }
            return new Page<>(page.getPageNumber(), page.getPageSize(), page.getTotalCount(), respDTOS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Page<>(query.getPage(), query.getPageSize(), 0);
        }
    }

    @Override
    public HzChangeOrderRespDTO getHzChangeOrderRecordById(Long id) {
        try {
           HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordById(id);
           if(record != null){
               return HzChangeOrderFactory.changeOrderRecordToRespDTO(record);
           }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public HzChangeOrderRespDTO getHzChangeOrderRespDTOById(Long id, Long auditId) {
        try {
            HzChangeOrderRecord record = hzChangeOrderDAO.findHzChangeOrderRecordDTOById(id,auditId);
            if(record != null){
                return HzChangeOrderFactory.changeOrderRecordToRespDTO(record);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public boolean changeOrderRelatedChangeData(Long orderId) {
        HzChangeDataQuery query = new HzChangeDataQuery();
        query.setOrderId(orderId);
        List<HzChangeDataRecord> list = hzChangeDataRecordDAO.getChangeDataTableName(query);
        if(ListUtils.isNotEmpty(list)){
            return true;
        }
        return false;
    }
}
