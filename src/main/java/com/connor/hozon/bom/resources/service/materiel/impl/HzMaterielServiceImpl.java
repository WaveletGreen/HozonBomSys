package com.connor.hozon.bom.resources.service.materiel.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzMaterielReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzMaterielFactory;
import com.connor.hozon.bom.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.materiel.HzMaterielService;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;

import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/7/3
 * @Description:
 */
@Service("HzMaterielService")
public class HzMaterielServiceImpl implements HzMaterielService {
    @Autowired
    private HzMaterielDAO hzMaterielDAO;

    @Autowired
    private HzFactoryDAO hzFactoryDAO;

    @Override
    public WriteResultRespDTO editHzMateriel(EditHzMaterielReqDTO editHzMaterielReqDTO) {
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        if(null == editHzMaterielReqDTO.getProjectId() || editHzMaterielReqDTO.getProjectId().equals("")){
            respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            respDTO.setErrMsg("请选择项目！");
            return respDTO;
        }
        try{
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return WriteResultRespDTO.getFailPrivilege();
            }
            HzMaterielRecord record = new HzMaterielRecord();
            User user = UserInfo.getUser();
            if(!editHzMaterielReqDTO.getFactoryCode().equals("") && null != editHzMaterielReqDTO.getFactoryCode()){
                HzFactory hzFactory = hzFactoryDAO.findFactory("", editHzMaterielReqDTO.getFactoryCode());
                if(hzFactory == null){
                    String puid = UUID.randomUUID().toString();
                    hzFactory =  new HzFactory();
                    hzFactory.setPuid(puid);
                    hzFactory.setpFactoryCode(editHzMaterielReqDTO.getFactoryCode());
                    hzFactory.setpUpdateName(user.getUserName());
                    hzFactory.setpCreateName(user.getUserName());
                    int i = hzFactoryDAO.insert(hzFactory);
                    if(i<0){
                        return WriteResultRespDTO.getFailResult();
                    }
                    record.setpFactoryPuid(puid);
                }else{
                    record.setpFactoryPuid(hzFactory.getPuid());
                }
            }
            record.setpCreateName(user.getUserName());
            record.setpVinPerNo(editHzMaterielReqDTO.getpVinPerNo());
            record.setpSpareMaterial(editHzMaterielReqDTO.getpSpareMaterial());
            String loosePartFlag = editHzMaterielReqDTO.getpLoosePartFlag();
            if("N".equals(loosePartFlag.toUpperCase())){
                record.setpLoosePartFlag(0);
            }else if("Y".equals(loosePartFlag.toUpperCase())){
                record.setpLoosePartFlag(1);
            }else{
                record.setpLoosePartFlag(null);
            }
            record.setpMrpController(editHzMaterielReqDTO.getpMrpController());
            record.setpUpdateName(user.getUserName());
            record.setPuid(editHzMaterielReqDTO.getPuid());
            record.setpBasicUnitMeasure(editHzMaterielReqDTO.getpBasicUnitMeasure());
            if("Y".equals(editHzMaterielReqDTO.getpInventedPart().toUpperCase())){
                record.setpInventedPart(1);
            }else if("N".equals(editHzMaterielReqDTO.getpInventedPart().toUpperCase())){
                record.setpInventedPart(0);
            }else {
                record.setpInventedPart(null);
            }
            record.setResource(editHzMaterielReqDTO.getResource());
            if("Y".equals(editHzMaterielReqDTO.getpColorPart().toUpperCase())){
                record.setpColorPart(1);
            }else if("N".equals(editHzMaterielReqDTO.getpColorPart().toUpperCase())){
                record.setpColorPart(0);
            }else {
                record.setpColorPart(null);
            }
            record.setpHeight(editHzMaterielReqDTO.getpHeight());

            if("内饰件".equals(editHzMaterielReqDTO.getpInOutSideFlag())){
                record.setpInOutSideFlag(1);
            }else if("外饰件".equals(editHzMaterielReqDTO.getpInOutSideFlag())){
                record.setpInOutSideFlag(0);
            }else {
                record.setpInOutSideFlag(null);
            }

            if("Y".equals(editHzMaterielReqDTO.getP3cPartFlag().toUpperCase())){
                record.setP3cPartFlag(1);
            }else if("N".equals(editHzMaterielReqDTO.getP3cPartFlag().toUpperCase())){
                record.setP3cPartFlag(0);
            }else {
                record.setP3cPartFlag(null);
            }
            record.setpPartImportantDegree(editHzMaterielReqDTO.getpPartImportantDegree());
            int i = hzMaterielDAO.update(record);
            if(i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return WriteResultRespDTO.getFailResult();
        }

        return WriteResultRespDTO.getFailResult();
    }

    @Override
    public WriteResultRespDTO deleteHzMateriel(String puid) {
        try{
            WriteResultRespDTO respDTO = new WriteResultRespDTO();
            if(StringUtil.isEmpty(puid)){
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                respDTO.setErrMsg("请选择一条需要删除的数据！");
                return respDTO;
            }
            boolean b = PrivilegeUtil.writePrivilege();
            if(!b){
                return WriteResultRespDTO.getFailPrivilege();
            }
            int i = hzMaterielDAO.delete(puid);
            if(i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        }catch (Exception e){
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

    @Override
    public Page<HzMaterielRespDTO> findHzMaterielForPage(HzMaterielByPageQuery query) {
        //先找出对应的物料类型，添加到物料类型表，将其数据自动带出
        /**
         * 物料类型  严格按照注释来读写数据
         * （11 整车超级物料主数据
         * 21 整车衍生物料主数据
         * 31 虚拟件物料主数据
         * 41半成品物料主数据
         * 51 生产性外购物料主数据
         * 61自制备件物料主数据）
         */
        try{
            Page<HzMaterielRecord> page = hzMaterielDAO.findHzMaterielForPage(query);
            int num = (page.getPageNumber()-1)*page.getPageSize();
            if(page == null || page.getResult() == null){
                return new Page<>(page.getPageNumber(),page.getPageSize(),0);
            }

            List<HzMaterielRecord> recordList = page.getResult();
            List<HzMaterielRespDTO> respDTOS = new ArrayList<>();
            for(HzMaterielRecord record:recordList){
                HzMaterielRespDTO respDTO = HzMaterielFactory.hzMaterielRecordToRespDTO(record);
                respDTO.setNo(++num);
                respDTOS.add(respDTO);
            }
            return new Page<>(page.getPageNumber(),page.getPageSize(),page.getTotalCount(),respDTOS);
        }catch (Exception e){
            return new Page<>(0,0,0);
        }
    }


    @Override
    public HzMaterielRespDTO getHzMateriel(HzMaterielQuery query) {
        try{
            HzMaterielRecord record = hzMaterielDAO.getHzMaterielRecord(query);
            if(record!= null){
               return HzMaterielFactory.hzMaterielRecordToRespDTO(record);
            }
        }catch (Exception e){
            return null;
        }
        return  null;
    }
}
