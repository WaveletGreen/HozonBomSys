package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzEPLReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEplRespDTO;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.sys.entity.User;
import sql.pojo.epl.HzEPLRecord;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description:
 */
public class HzEPLFactory {
    public static HzEPLRecord eplReqDTOToRecord(EditHzEPLReqDTO reqDTO){
        HzEPLRecord record = new HzEPLRecord();
        record.setActualWeight(reqDTO.getActualWeight());
        record.setBuyEngineer(reqDTO.getBuyEngineer());
        record.setBwgBoxPart(reqDTO.getBwgBoxPart());
        record.setDataVersion(reqDTO.getDataVersion());
        record.setDensity(reqDTO.getDensity());
        record.setFastener(reqDTO.getFastener());
        record.setFastenerLevel(reqDTO.getFastenerLevel());
        record.setDevelopType(reqDTO.getDevelopType());
        record.setFastenerStandard(reqDTO.getFastenerStandard());
        record.setDutyEngineer(reqDTO.getDutyEngineer());
        record.setFeatureWeight(reqDTO.getFeatureWeight());
        record.setId(reqDTO.getId());
        record.setImportance(reqDTO.getImportance());
        record.setInOutSideFlag(reqDTO.getInOutSideFlag());
        record.setIs3cpart(reqDTO.getIs3cpart());
        record.setManuProcess(reqDTO.getManuProcess());
        record.setMaterial1(reqDTO.getMaterial1());
        record.setMaterial2(reqDTO.getMaterial2());
        record.setMaterial3(reqDTO.getMaterial3());
        record.setMaterialHigh(reqDTO.getMaterialHigh());
        record.setMaterialStandard(reqDTO.getMaterialStandard());
        record.setPartClass(reqDTO.getPartClass());
        record.setPartEnName(reqDTO.getPartEnName());
        record.setPartId(reqDTO.getPartId());
        record.setPartName(reqDTO.getPartName());
        record.setPartOfWhichDept(reqDTO.getPartOfWhichDept());
        record.setPartResource(reqDTO.getPartResource());
        record.setPictureNo(reqDTO.getPictureNo());
        record.setPictureSheet(reqDTO.getPictureSheet());
        record.setProjectId(reqDTO.getProjectId());
        record.setRegulationCode(reqDTO.getRegulationCode());
        record.setRegulationFlag(reqDTO.getRegulationFlag());
        record.setRemark(reqDTO.getRemark());
        record.setStatus(1);//先写出已生效
        record.setSupply(reqDTO.getSupply());
        record.setSupplyCode(reqDTO.getSupplyCode());
        record.setSurfaceTreat(reqDTO.getSurfaceTreat());
        record.setSymmetry(reqDTO.getSymmetry());
        record.setTargetWeight(reqDTO.getTargetWeight());
        record.setTextureColorNum(reqDTO.getTextureColorNum());
        record.setTorque(reqDTO.getTorque());
        record.setUnit(reqDTO.getUnit());
        User user = UserInfo.getUser();
        record.setCreateId(Long.valueOf(user.getId()));
        record.setUpdateId(Long.valueOf(user.getId()));
        return record;
    }
    
    public static HzEplRespDTO eplRecordToRespDTO(HzEPLRecord record){
        HzEplRespDTO respDTO =  new HzEplRespDTO();
        respDTO.setActualWeight(record.getActualWeight());
        respDTO.setBuyEngineer(record.getBuyEngineer());
        respDTO.setBwgBoxPart(record.getBwgBoxPart());
        respDTO.setDataVersion(record.getDataVersion());
        respDTO.setDensity(record.getDensity());
        respDTO.setFastener(record.getFastener());
        respDTO.setFastenerLevel(record.getFastenerLevel());
        respDTO.setDevelopType(record.getDevelopType());
        respDTO.setFastenerStandard(record.getFastenerStandard());
        respDTO.setDutyEngineer(record.getDutyEngineer());
        respDTO.setFeatureWeight(record.getFeatureWeight());
        respDTO.setId(record.getId());
        respDTO.setImportance(record.getImportance());
        respDTO.setInOutSideFlag(record.getInOutSideFlag());
        respDTO.setIs3cpart(record.getIs3cpart());
        respDTO.setManuProcess(record.getManuProcess());
        respDTO.setMaterial1(record.getMaterial1());
        respDTO.setMaterial2(record.getMaterial2());
        respDTO.setMaterial3(record.getMaterial3());
        respDTO.setMaterialHigh(record.getMaterialHigh());
        respDTO.setMaterialStandard(record.getMaterialStandard());
        respDTO.setPartClass(record.getPartClass());
        respDTO.setPartEnName(record.getPartEnName());
        respDTO.setPartId(record.getPartId());
        respDTO.setPartName(record.getPartName());
        respDTO.setPartOfWhichDept(record.getPartOfWhichDept());
        respDTO.setPartResource(record.getPartResource());
        respDTO.setPictureNo(record.getPictureNo());
        respDTO.setPictureSheet(record.getPictureSheet());
        respDTO.setProjectId(record.getProjectId());
        respDTO.setRegulationCode(record.getRegulationCode());
        respDTO.setRegulationFlag(record.getRegulationFlag());
        respDTO.setRemark(record.getRemark());
        respDTO.setStatus(record.getStatus());
        respDTO.setSupply(record.getSupply());
        respDTO.setSupplyCode(record.getSupplyCode());
        respDTO.setSurfaceTreat(record.getSurfaceTreat());
        respDTO.setSymmetry(record.getSymmetry());
        respDTO.setTargetWeight(record.getTargetWeight());
        respDTO.setTextureColorNum(record.getTextureColorNum());
        respDTO.setTorque(record.getTorque());
        respDTO.setUnit(record.getUnit());
        respDTO.setEffectTime(DateUtil.formatDefaultDate(record.getEffectTime()));
        return respDTO;
    }
}
