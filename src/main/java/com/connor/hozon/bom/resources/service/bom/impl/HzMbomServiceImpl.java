package com.connor.hozon.bom.resources.service.bom.impl;

import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzMbomLineRecord;
import sql.redis.SerializeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.connor.hozon.bom.resources.service.bom.impl.HzPbomServiceImpl.getLevelAndRank;

/**
 * @Author: haozt
 * @Date: 2018/6/20
 * @Description:
 */
@Service("HzMbomService")
public class HzMbomServiceImpl implements HzMbomService {
    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;


    @Override
    public Page<HzMbomRecordRespDTO> fingHzMbomForPage(FindForPageReqDTO reqDTO) {
        try {
            Page<HzMbomLineRecord> recordPage =hzMbomRecordDAO.findMbomForPage(reqDTO);
            int num = (reqDTO.getPage()-1)*reqDTO.getLimit();
            if(recordPage == null || recordPage.getResult() == null){
                return  new Page<>(reqDTO.getPage(),reqDTO.getLimit(),0);
            }
            List<HzMbomLineRecord> lineRecords = recordPage.getResult();
            List<HzMbomRecordRespDTO> respDTOList = new ArrayList<>();
            for(HzMbomLineRecord record:lineRecords){
                HzMbomRecordRespDTO respDTO= new HzMbomRecordRespDTO();
                respDTO.setNo(++num);
                respDTO.setPuid(record.getPuid());
                respDTO.setEbomPuid(record.getPuid());
                Integer is2Y = record.getIs2Y();
                Integer hasChildren = record.getIsHas();
                String lineIndex = record.getLineIndex();
                String[] strings = getLevelAndRank(lineIndex, is2Y, hasChildren);
                respDTO.setLevel(strings[0]);//层级
                respDTO.setLineId(record.getLineID());
                respDTO.setpBomOfWhichDept(record.getpBomOfWhichDept());
                byte[] bytes = record.getBomLineBlock();
                Object obj = SerializeUtil.unserialize(bytes);
                Object name = null;
                if (obj instanceof LinkedHashMap) {
                    if (((LinkedHashMap) obj).size() > 0) {
                        name =((LinkedHashMap) obj).get("object_name");
                    }
                }
                respDTO.setObject_name((String) name);
                respDTO.setSparePart(record.getSparePart());
                respDTO.setSparePartNum(record.getSparePartNum());
                respDTO.setLaborHour(record.getLaborHour());
                respDTO.setRhythm(record.getRhythm());
                respDTO.setSolderJoint(record.getSolderJoint());
                respDTO.setMachineMaterial(record.getMachineMaterial());
                respDTO.setStandardPart(record.getStandardPart());
                respDTO.setTools(record.getTools());
                respDTO.setWasterProduct(record.getWasterProduct());
                respDTO.setChange(record.getChange());
                respDTO.setChangeNum(record.getChangeNum());
                respDTOList.add(respDTO);
            }
            return new Page<>(reqDTO.getPage(),reqDTO.getLimit(),recordPage.getTotalCount(),respDTOList);
        }catch (Exception e){
            return null;
        }
    }
}
