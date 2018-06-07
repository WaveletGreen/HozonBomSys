package com.connor.hozon.bom.resources.service.bom.impl;

import com.connor.hozon.bom.resources.dto.request.FindHzEbomRecordReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozt on 2018/06/06
 */
@Service("HzEbomService")
public class HzEbomServiceImpl implements HzEbomService {

    @Autowired
    private HzEbomRecordDAO recordDAO;

    @Override
    public List<HzEbomRespDTO> getHzEbomList(FindHzEbomRecordReqDTO reqDTO) {
        List<HzEPLManageRecord> records = recordDAO.getHzEbomList(reqDTO);
        if(ListUtil.isEmpty(records)){
            return null;
        }
        List<HzEbomRespDTO> respDTOS = new ArrayList<>();
        try {
            for(HzEPLManageRecord record:records){
                HzEbomRespDTO respDTO = new HzEbomRespDTO();
                respDTO.setPuid(record.getpPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                //strings[0] 层级 strings[1] 级别
                String[] strings = HzPbomServiceImpl.getLevelAndRank(lineIndex,is2Y,hasChildren);
                respDTO.setRank(strings[1]==null?"":strings[1]);
                respDTO.setLevel(strings[0]==null?"":strings[0]);
                respDTO.setLineId(record.getLineId()==null?"":record.getLineId());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept()==null?"":record.getpBomOfWhichDept());
                respDTO.setGroupNum("暂时不知道怎么解析");
                respDTO.setNameZh("中文名称");
                respDTO.setNameEn("英文名称");
                respDTO.setpUnit(record.getpUnit()==null?"":record.getpUnit());
                respDTO.setpRentLow(record.getpRentLow()==null?"":record.getpRentLow());
                respDTO.setpRentHigh(record.getpRentHigh()==null?"":record.getpRentHigh());
                respDTO.setpPictureNo(record.getpPictureNo()==null?"":record.getpPictureNo());
                respDTO.setpInstallPictureNo(record.getpInstallPictureNo()==null?"":record.getpInstallPictureNo());
                respDTO.setpMap(record.getpMap()==null?"":record.getpMap());
                respDTO.setpMaterialHigh(record.getpMaterialHigh()==null?"":record.getpMaterialHigh());
                respDTO.setpMaterial1(record.getpMaterial1()==null?"":record.getpMaterial1());
                respDTO.setpMaterial2(record.getpMaterial2()==null?"":record.getpMaterial2());
                respDTO.setpMaterial3(record.getpMaterial3()==null?"":record.getpMaterial3());
                respDTO.setpDensity(record.getpDensity()==null?"":record.getpDensity());
                respDTO.setpMaterialStandard(record.getpMaterialStandard()==null?"":record.getpMaterialStandard());
                respDTO.setpSurfaceManage(record.getpSurfaceManage()==null?"":record.getpSurfaceManage());
                respDTO.setpTextureNo(record.getpTextureNo()==null?"":record.getpTextureNo());
                respDTO.setpMadeArt(record.getpMadeArt()==null?"":record.getpMadeArt());
                respDTO.setpSymmetric(record.getpSymmetric()==null?"":record.getpSymmetric());
                respDTO.setpIsRulePart(record.getpIsRulePart()==null?"":record.getpIsRulePart());
                respDTO.setpRulePartNo(record.getpRulePartNo()==null?"":record.getpRulePartNo());
                respDTO.setpCasketPart(record.getpCasketPart()==null?"":record.getpCasketPart());
                respDTO.setpDevelopType(record.getpDevelopType()==null?"":record.getpDevelopType());
                respDTO.setpDataVersion(record.getpDataVersion()==null?"":record.getpDataVersion());
                respDTO.setpTargetHeight(record.getpTargetHeight()==null?"":record.getpTargetHeight());
                respDTO.setpEstimateHeight(record.getpEstimateHeight()==null?"":record.getpEstimateHeight());
                respDTO.setpActualHeight(record.getpActualHeight()==null?"":record.getpActualHeight());
                respDTO.setpFixture(record.getpFixture()==null?"":record.getpFixture());
                respDTO.setpFixtureSpec(record.getpFixtureSpec()==null?"":record.getpFixtureSpec());
                respDTO.setpFixtureLevel(record.getpFixtureLevel()==null?"":record.getpFixtureLevel());
                respDTO.setpTorque(record.getpTorque()==null?"":record.getpTorque());
                respDTO.setpMajorDept(record.getpMajorDept()==null?"":record.getpMajorDept());
                respDTO.setpDutyEngineer(record.getpDutyEngineer()==null?"":record.getpDutyEngineer());
                respDTO.setpSupplier(record.getpSupplier()==null?"":record.getpSupplier());
                respDTO.setpSupplierNo(record.getpSupplierNo()==null?"":record.getpSupplierNo());
                respDTO.setpBuyEngineer(record.getpBuyEngineer()==null?"":record.getpBuyEngineer());
                respDTO.setpRemark(record.getpRemark()==null?"":record.getpRemark());
                respDTO.setpItemClassification(record.getpItemClassification()==null?"":record.getpItemClassification());
                respDTO.setpItemResource(record.getpItemResource()==null?"":record.getpItemResource());
                respDTO.setpImportance(record.getpImportance()==null?"":record.getpImportance());
                Integer supplyState = record.getpSupplyState();
                if(supplyState == null){
                    respDTO.setpSupplyState("/");
                }else{
                    respDTO.setpSupplyState(supplyState.equals(new Integer(0))?"Y":"N");
                }
                respDTOS.add(respDTO);
            }
            return respDTOS;
        }catch (Exception e){
            return null;
        }
    }
}
