package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzChangeOrderReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzChangeOrderFactory;
import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.change.HzChangeService;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.change.HzChangeOrderRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:
 */
@Service("hzChangeService")
public class HzChangeServiceImpl implements HzChangeService {

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;
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
                respDTOS.add(HzChangeOrderFactory.changeOrderRecordToRespDTO(record));
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
}
