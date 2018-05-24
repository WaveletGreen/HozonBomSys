package com.connor.hozon.bom.resources.service.bom.impl;

import com.connor.hozon.bom.resources.dto.response.HzPbomLineMaintainResponseDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomMaintainRecordDAO;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzPbomLineMaintainRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozt on 2018/5/24
 */
@Service("HzPbomService")
public class HzPbomServiceImpl implements HzPbomService {
    @Autowired
    private HzPbomMaintainRecordDAO recordDAO;

    @Override
    public List<HzPbomLineMaintainResponseDTO> getHzPbomMaintainRecord() {
        List<HzPbomLineMaintainResponseDTO> responseDTOS = new ArrayList<>();
        List<HzPbomLineMaintainRecord> records = recordDAO.getPBomLineMaintainRecord();
        if(ListUtil.isEmpty(records)){
            return null;
        }
        for(HzPbomLineMaintainRecord record :records){
            HzPbomLineMaintainResponseDTO responseDTO = new HzPbomLineMaintainResponseDTO();
            //层级
            String lineIndex = record.getLineIndex();
            String line = "";
            Integer is2Y = record.getIs2Y();
            Integer hasChildren = record.getIsHas();
            int level = (lineIndex.split("\\.")).length;
            if(null != is2Y &&is2Y.equals(1)){
               line = "2Y";
            }else if(null!= is2Y && is2Y.equals(0)){
                if(hasChildren!=null && hasChildren.equals(1)){
                    line =level+"Y";
                }else if(hasChildren!= null && hasChildren.equals(0)){
                    line = String.valueOf(level);
                }else{
                    line ="1";//错误数据
                }
            }else{
                line ="1";
            }

            responseDTO.setLevel(line);
            responseDTO.setOrderNum(record.getOrderNum());
            responseDTO.setChange(record.getChange()==null?"":record.getChange());
            responseDTO.setWasterProduct(record.getWasterProduct()==null?"":record.getWasterProduct());
            responseDTO.setTools(record.getTools()==null?"":record.getTools());
            responseDTO.setChangeNum(record.getChangeNum()==null?"":record.getChangeNum());
            responseDTO.setLaborHour(record.getLaborHour()==null?"":record.getLaborHour());
            responseDTO.setMachineMaterial(record.getMachineMaterial()==null?"":record.getMachineMaterial());
            responseDTO.setBomDigifaxId(record.getBomDigifaxId()==null?"":record.getBomDigifaxId());
            responseDTO.setLineId(record.getLineID()==null?"":record.getLineID());//零件号
            responseDTO.setpBomPuid(record.getpPuid());
            responseDTO.setStandardPart(record.getStandardPart()==null?"":record.getStandardPart());
            responseDTO.setSparePartNum(record.getSparePartNum()==null?"":record.getSparePartNum());
            responseDTO.setSolderJoint(record.getSolderJoint()==null?"":record.getSolderJoint());
            responseDTO.setRhythm(record.getRhythm()==null?"":record.getRhythm());
            responseDTO.setSparePart(record.getSparePart()==null?"":record.getSparePart());
            responseDTO.setpBomOfWhichDept(record.getpBomOfWhichDept()==null?"":record.getpBomOfWhichDept());
            responseDTO.setProcessRoute(record.getProcessRoute()==null?"":record.getProcessRoute());
            responseDTOS.add(responseDTO);
        }

        return responseDTOS;
    }

}
