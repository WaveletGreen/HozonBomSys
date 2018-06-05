package com.connor.hozon.bom.resources.service.epl.impl;

import com.connor.hozon.bom.resources.dto.request.FindHzEPLRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEplMangeRecordDAO;
import com.connor.hozon.bom.resources.mybatis.epl.impl.HzEplManageRecordDAOImpl;
import com.connor.hozon.bom.resources.service.bom.impl.HzPbomServiceImpl;
import com.connor.hozon.bom.resources.service.epl.HzEPLManageRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozt on 2018/06/05
 */
@Service("HzEPLManageRecordService")
public class HzEPLManageRecordServiceImpl implements HzEPLManageRecordService {

    @Autowired
    private HzEplMangeRecordDAO hzEplMangeRecordDAO;

    public  static void main(String[] args){
    }

    @Override
    public List<HzEPLRecordRespDTO> getHzEPLRecord(FindHzEPLRecordReqDTO recordReqDTO) {
        List<HzEPLRecordRespDTO> recordRespDTOS = new ArrayList<>();
        List<HzEPLManageRecord> records =  hzEplMangeRecordDAO.getHzEplManageRecord();
        if(ListUtil.isEmpty(records)){
            return null;
        }
        try{
            for(HzEPLManageRecord record:records){
                HzEPLRecordRespDTO recordRespDTO = new HzEPLRecordRespDTO();
                recordRespDTO.setPuid(record.getpPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                //strings[0] 层级 strings[1] 级别
                String[] strings = HzPbomServiceImpl.getLevelAndRank(lineIndex,is2Y,hasChildren);
                recordRespDTO.setRank(strings[1]==null?"":strings[1]);
                recordRespDTO.setLevel(strings[0]==null?"":strings[0]);
                recordRespDTO.setLineId(record.getLineId()==null?"":record.getLineId());
                recordRespDTO.setpBomOfWhichDept(record.getpBomOfWhichDept()==null?"":record.getpBomOfWhichDept());
                recordRespDTO.setGroupNum("暂时不知道怎么解析");
                recordRespDTO.setNameZh("中文名称");
                recordRespDTO.setNameEn("英文名称");
                Integer pState = record.getpState();
                if(pState == null){
                    recordRespDTO.setpState("/");
                }else if(pState==(new Integer(0))){
                    recordRespDTO.setpState("A");
                }else if(pState==new Integer(1)){
                    recordRespDTO.setpState("U");
                }else if(pState==new Integer(2)){
                    recordRespDTO.setpState("D");
                }
                recordRespDTO.setpUnit(record.getpUnit()==null?"":record.getpUnit());
                recordRespDTO.setpRentLow(record.getpRentLow()==null?"":record.getpRentLow());
                recordRespDTO.setpRentHigh(record.getpRentHigh()==null?"":record.getpRentHigh());
                recordRespDTO.setpPictureNo(record.getpPictureNo()==null?"":record.getpPictureNo());
                recordRespDTO.setpInstallPictureNo(record.getpInstallPictureNo()==null?"":record.getpInstallPictureNo());
                recordRespDTO.setpMap(record.getpMap()==null?"":record.getpMap());
                recordRespDTO.setpMaterialHigh(record.getpMaterialHigh()==null?"":record.getpMaterialHigh());
                recordRespDTO.setpMaterial1(record.getpMaterial1()==null?"":record.getpMaterial1());
                recordRespDTO.setpMaterial2(record.getpMaterial2()==null?"":record.getpMaterial2());
                recordRespDTO.setpMaterial3(record.getpMaterial3()==null?"":record.getpMaterial3());
                recordRespDTO.setpDensity(record.getpDensity()==null?"":record.getpDensity());
                recordRespDTO.setpMaterialStandard(record.getpMaterialStandard()==null?"":record.getpMaterialStandard());
                recordRespDTO.setpSurfaceManage(record.getpSurfaceManage()==null?"":record.getpSurfaceManage());
                recordRespDTO.setpTextureNo(record.getpTextureNo()==null?"":record.getpTextureNo());
                recordRespDTO.setpMadeArt(record.getpMadeArt()==null?"":record.getpMadeArt());
                recordRespDTO.setpSymmetric(record.getpSymmetric()==null?"":record.getpSymmetric());
                recordRespDTO.setpIsRulePart(record.getpIsRulePart()==null?"":record.getpIsRulePart());
                recordRespDTO.setpRulePartNo(record.getpRulePartNo()==null?"":record.getpRulePartNo());
                recordRespDTO.setpCasketPart(record.getpCasketPart()==null?"":record.getpCasketPart());
                recordRespDTO.setpDevelopType(record.getpDevelopType()==null?"":record.getpDevelopType());
                recordRespDTO.setpDataVersion(record.getpDataVersion()==null?"":record.getpDataVersion());
                recordRespDTO.setpTargetHeight(record.getpTargetHeight()==null?"":record.getpTargetHeight());
                recordRespDTO.setpEstimateHeight(record.getpEstimateHeight()==null?"":record.getpEstimateHeight());
                recordRespDTO.setpActualHeight(record.getpActualHeight()==null?"":record.getpActualHeight());
                recordRespDTO.setpFixture(record.getpFixture()==null?"":record.getpFixture());
                recordRespDTO.setpFixtureSpec(record.getpFixtureSpec()==null?"":record.getpFixtureSpec());
                recordRespDTO.setpFixtureLevel(record.getpFixtureLevel()==null?"":record.getpFixtureLevel());
                recordRespDTO.setpTorque(record.getpTorque()==null?"":record.getpTorque());
                recordRespDTO.setpMajorDept(record.getpMajorDept()==null?"":record.getpMajorDept());
                recordRespDTO.setpDutyEngineer(record.getpDutyEngineer()==null?"":record.getpDutyEngineer());
                recordRespDTO.setpSupplier(record.getpSupplier()==null?"":record.getpSupplier());
                recordRespDTO.setpSupplierNo(record.getpSupplierNo()==null?"":record.getpSupplierNo());
                recordRespDTO.setpBuyEngineer(record.getpBuyEngineer()==null?"":record.getpBuyEngineer());
                recordRespDTO.setpRemark(record.getpRemark()==null?"":record.getpRemark());
                recordRespDTO.setpItemClassification(record.getpItemClassification()==null?"":record.getpItemClassification());
                recordRespDTO.setpItemResource(record.getpItemResource()==null?"":record.getpItemResource());
                Integer supplyState = record.getpSupplyState();
                if(supplyState == null){
                    recordRespDTO.setpSupplyState("/");
                }else{
                    recordRespDTO.setpSupplyState(supplyState.equals(new Integer(0))?"Y":"N");
                }

                recordRespDTO.setResource(record.getResource()==null?"":record.getResource());
                Integer type = record.getType();
                Integer buyUnit = record.getBuyUnit();
                Integer colorPart = record.getColorPart();
                if(Integer.valueOf(0).equals(type)){
                    recordRespDTO.setType("Y");
                }else if(Integer.valueOf(1).equals(type)){
                    recordRespDTO.setType("N");
                }else{
                    recordRespDTO.setType("/");
                }
                if(Integer.valueOf(0).equals(buyUnit)){
                    recordRespDTO.setBuyUnit("Y");
                }else if(Integer.valueOf(1).equals(buyUnit)){
                    recordRespDTO.setBuyUnit("N");
                }else{
                    recordRespDTO.setBuyUnit("/");
                }
                if(Integer.valueOf(0).equals(colorPart)){
                    recordRespDTO.setColorPart("Y");
                }else if(Integer.valueOf(1).equals(colorPart)){
                    recordRespDTO.setColorPart("N");
                }else{
                    recordRespDTO.setColorPart("/");
                }
                recordRespDTO.setProductLine(record.getProductLine()==null?"":record.getProductLine());
                recordRespDTO.setWorkShop1(record.getWorkShop1()==null?"":record.getWorkShop1());
                recordRespDTO.setWorkShop2(record.getWorkShop2()==null?"":record.getWorkShop2());
                recordRespDTO.setMouldType(record.getMouldType()==null?"":record.getMouldType());
                recordRespDTO.setOuterPart(record.getOuterPart()==null?"":record.getOuterPart());
                recordRespDTO.setStation("工位");
                recordRespDTO.setChange(record.getChange()==null?"":record.getChange());
                recordRespDTO.setWasterProduct(record.getWasterProduct()==null?"":record.getWasterProduct());
                recordRespDTO.setTools(record.getTools()==null?"":record.getTools());
                recordRespDTO.setChangeNum(record.getChangeNum()==null?"":record.getChangeNum());
                //这里需要转换一下，数据库存储毫秒值  暂时不知道页面显示为分钟还是小时 待定
                recordRespDTO.setLaborHour(record.getLaborHour()==null?"":record.getLaborHour());
                recordRespDTO.setMachineMaterial(record.getMachineMaterial()==null?"":record.getMachineMaterial());
                recordRespDTO.setStandardPart(record.getStandardPart()==null?"":record.getStandardPart());
                recordRespDTO.setSparePartNum(record.getSparePartNum()==null?"":record.getSparePartNum());
                recordRespDTO.setSolderJoint(record.getSolderJoint()==null?"":record.getSolderJoint());
                recordRespDTO.setRhythm(record.getRhythm()==null?"":record.getRhythm());
                recordRespDTO.setSparePart(record.getSparePart()==null?"":record.getSparePart());
                recordRespDTO.setProcessRoute(record.getProcessRoute()==null?"":record.getProcessRoute());
                recordRespDTOS.add(recordRespDTO);
            }
            return recordRespDTOS;
        }catch (Exception e){
            return null;
        }

    }
}
