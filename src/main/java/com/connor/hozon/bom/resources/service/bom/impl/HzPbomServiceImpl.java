package com.connor.hozon.bom.resources.service.bom.impl;

import com.connor.hozon.bom.resources.dto.request.BatchInsertHzPbomMaintainRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineMaintainRespDTO;
import com.connor.hozon.bom.resources.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomMaintainRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzPbomRecordDAOImpl;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzPbomLineMaintainRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.bom.HzPbomMaintainRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by haozt on 2018/5/24
 */
@Service("HzPbomService")
public class HzPbomServiceImpl implements HzPbomService {
    @Autowired
    private HzPbomMaintainRecordDAO recordDAO;

    @Autowired
    private HzPbomRecordDAO hzPbomRecordDAO;
    @Override
    public List<HzPbomLineMaintainRespDTO> getHzPbomMaintainRecord() {
        List<HzPbomLineMaintainRespDTO> responseDTOS = new ArrayList<>();
        List<HzPbomLineMaintainRecord> records = recordDAO.getPBomLineMaintainRecord();
        if(ListUtil.isEmpty(records)){
            return null;
        }
        for(HzPbomLineMaintainRecord record :records){
            HzPbomLineMaintainRespDTO responseDTO = new HzPbomLineMaintainRespDTO();
            //层级
            String lineIndex = record.getLineIndex();
            Integer is2Y = record.getIs2Y();
            Integer hasChildren = record.getIsHas();
            String[] strings = getLevelAndRank(lineIndex,is2Y,hasChildren);
            responseDTO.setLevel(strings[0]);
            responseDTO.setOrderNum(record.getOrderNum());
            responseDTO.setChange(record.getChange()==null?"":record.getChange());
            responseDTO.setWasterProduct(record.getWasterProduct()==null?"":record.getWasterProduct());
            responseDTO.setTools(record.getTools()==null?"":record.getTools());
            responseDTO.setChangeNum(record.getChangeNum()==null?"":record.getChangeNum());
            //这里需要转换一下，数据库存储毫秒值  暂时不知道页面显示为分钟还是小时 待定
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

    @Override
    public int insertPbomLineMaintainRecords(List<BatchInsertHzPbomMaintainRecordReqDTO> recordInsertBatchReqDTO) {

       List<HzPbomMaintainRecord> records = new ArrayList<>();
       for(BatchInsertHzPbomMaintainRecordReqDTO recordReqDTO:recordInsertBatchReqDTO){
           HzPbomMaintainRecord record = new HzPbomMaintainRecord();
           record.setChange(recordReqDTO.getChange());
           record.setChangeNum(recordReqDTO.getChangeNum());
           //这里需要注意一下  到时候转换成毫秒值存到数据库中
           record.setLaborHour(recordReqDTO.getLaborHour());
           record.setMachineMaterial(recordReqDTO.getMachineMaterial());
           record.setProcessRoute(recordReqDTO.getProcessRoute());
           record.setRhythm(recordReqDTO.getRhythm());
           record.setSolderJoint(recordReqDTO.getSolderJoint());
           record.setSparePart(recordReqDTO.getSparePart());
           record.setSparePartNum(recordReqDTO.getSparePartNum());
           record.setStandardPart(recordReqDTO.getStandardPart());
           record.setTools(recordReqDTO.getTools());
           record.setWasterProduct(recordReqDTO.getWasterProduct());
           record.setpBomPuid(recordReqDTO.getpBomPuid());
           record.setPuid(UUID.randomUUID().toString());
           records.add(record);
       }

        return recordDAO.insertList(records);
    }

    @Override
    public List<HzPbomLineRespDTO> getHzPbomLineRecord() {
        List<HzPbomLineRecord> records = hzPbomRecordDAO.getPbomRecord();
        if(ListUtil.isEmpty(records)){
            return null;
        }
        try{
            List<HzPbomLineRespDTO> respDTOS = new ArrayList<>();
            for(HzPbomLineRecord record:records){
                HzPbomLineRespDTO respDTO = new HzPbomLineRespDTO();
                Integer is2Y = record.getIs2Y();
                Integer hasChildren =  record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex,is2Y,hasChildren);
                respDTO.setLevel(strings[0]==null?"":strings[0]);
                respDTO.setRank(strings[1] ==null ?"":strings[1]);
                respDTO.setLineId(record.getLineId()==null?"":record.getLineId());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept()==null?"":record.getpBomOfWhichDept());
                respDTO.setGroupNum("/");//这个暂时没有
                respDTO.setItemType("/");//这个暂时没有
                respDTO.setItemResource(record.getItemResource()==null?"":record.getItemResource());
                respDTO.setResource(record.getResource()==null?"/":record.getResource());
                Integer type = record.getType();
                Integer buyUnit = record.getBuyUnit();
                Integer colorPart = record.getColorPart();
                if(Integer.valueOf(0).equals(type)){
                    respDTO.setType("Y");
                }else if(Integer.valueOf(1).equals(type)){
                    respDTO.setType("N");
                }else{
                    respDTO.setType("/");
                }
                if(Integer.valueOf(0).equals(buyUnit)){
                    respDTO.setBuyUnit("Y");
                }else if(Integer.valueOf(1).equals(buyUnit)){
                    respDTO.setBuyUnit("N");
                }else{
                    respDTO.setBuyUnit("/");
                }
                if(Integer.valueOf(0).equals(colorPart)){
                    respDTO.setColorPart("Y");
                }else if(Integer.valueOf(1).equals(colorPart)){
                    respDTO.setColorPart("N");
                }else{
                    respDTO.setColorPart("/");
                }
                respDTO.setProductLine(record.getProductLine()==null?"":record.getProductLine());
                respDTO.setWorkShop1(record.getWorkShop1()==null?"":record.getWorkShop1());
                respDTO.setWorkShop2(record.getWorkShop2()==null?"":record.getWorkShop2());
                respDTO.setMouldType(record.getMouldType()==null?"":record.getMouldType());
                respDTO.setOuterPart(record.getOuterPart()==null?"":record.getOuterPart());
                respDTOS.add(respDTO);
            }
            return  respDTOS;
        }catch (Exception e){
            e.printStackTrace();
//            throw new RuntimeException(e.getMessage(),e);
        }
        return null;
    }

    /**
     * 获取bom系统的层级和级别
     * @param lineIndex
     * @param is2Y
     * @param hasChildren
     * @return String[0] 层级  String[1]级别
     */
    public String[] getLevelAndRank(String lineIndex,Integer is2Y,Integer hasChildren){
        int level = (lineIndex.split("\\.")).length;
        String line="";
        int rank = 0;
        if(null != is2Y && is2Y.equals(1)){
            line = "2Y";
            rank =1;
        }else if(null!= is2Y && is2Y.equals(0)){
            if(hasChildren!=null && hasChildren.equals(1)){
                line =level+"Y";
                rank = level;
            }else if(hasChildren!= null && hasChildren.equals(0)){
                line = String.valueOf(level);
                rank = level;
            }else{
                line ="/";//错误数据
            }
        }else{
            line ="/";
        }
        return new String[]{line,String.valueOf(rank)};
    }
}
