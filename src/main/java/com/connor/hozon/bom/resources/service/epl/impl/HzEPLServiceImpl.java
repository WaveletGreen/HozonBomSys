package com.connor.hozon.bom.resources.service.epl.impl;

import com.connor.hozon.bom.resources.domain.dto.request.EditHzEPLReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEplRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzEPLFactory;
import com.connor.hozon.bom.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEPLQuery;
import com.connor.hozon.bom.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.epl.HzEPLService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.epl.HzEPLRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description:
 */
@Service("hzEPLService")
public class HzEPLServiceImpl implements HzEPLService {
    @Autowired
    private HzEPLDAO hzEPLDAO;

    @Override
    public WriteResultRespDTO addPartToEPL(EditHzEPLReqDTO reqDTO) {
        try {
            HzEPLQuery query = new HzEPLQuery();
            query.setPartId(reqDTO.getPartId());
            query.setProjectId(reqDTO.getProjectId());
            boolean repeat = hzEPLDAO.partIdRepeat(query);
            if(repeat){
                return WriteResultRespDTO.failResultRespDTO("当前添加的零件号已存在！");
            }
            int i = hzEPLDAO.insert(HzEPLFactory.eplReqDTOToRecord(reqDTO));
            if(i<=0){
                return WriteResultRespDTO.getFailResult();
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO updatePartFromEPLRecord(EditHzEPLReqDTO reqDTO) {
        try {
            //判断一下重复 前端也会判断
            HzEPLQuery query = new HzEPLQuery();
            query.setId(reqDTO.getId());
            query.setPartId(reqDTO.getPartId());
            query.setProjectId(reqDTO.getProjectId());
            boolean repeat = hzEPLDAO.partIdRepeat(query);
            if(repeat){
                return WriteResultRespDTO.failResultRespDTO("零件号已存在！");
            }
            int i = hzEPLDAO.update(HzEPLFactory.eplReqDTOToRecord(reqDTO));
            if(i<=0){
                return WriteResultRespDTO.getFailResult();
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public WriteResultRespDTO deletePartFromEPLByIds(String ids) {
        try {
            int i = hzEPLDAO.delete(ids);
            if(i<=0){
                return WriteResultRespDTO.getSuccessResult();
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }

    @Override
    public Page<HzEplRespDTO> getEPLRecordByPage(HzEPLByPageQuery query) {
        try {
            Page<HzEPLRecord> page  = hzEPLDAO.getEplRecordByPage(query);
            if (page == null || ListUtil.isEmpty(page.getResult())) {
                return new Page<>(page.getPageNumber(), page.getPageSize(), 0);
            }
            int num = (query.getPage() - 1) * query.getPageSize()+1;
            List<HzEPLRecord> records = page.getResult();
            List<HzEplRespDTO>  list =  new ArrayList();
            for(HzEPLRecord record : records){
                HzEplRespDTO respDTO = HzEPLFactory.eplRecordToRespDTO(record);
                respDTO.setNo(num++);
                list.add(respDTO);
            }
            return new Page<>(page.getPageNumber(), page.getPageSize(), page.getTotalCount(), list);
        }catch (Exception e){
            e.printStackTrace();
            return new Page<>(query.getPage(), query.getPageSize(), 0);
        }
    }

    @Override
    public HzEplRespDTO getEplById(Long id) {
        HzEPLQuery query = new HzEPLQuery();
        query.setId(id);
        try {
            HzEPLRecord record = hzEPLDAO.getEPLRecordById(query);
            if(null != record){
                HzEplRespDTO respDTO  = HzEPLFactory.eplRecordToRespDTO(record);
                return respDTO;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
