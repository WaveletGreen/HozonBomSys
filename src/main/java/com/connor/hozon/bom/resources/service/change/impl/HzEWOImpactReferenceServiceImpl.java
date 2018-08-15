package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactReferenceDAO;
import com.connor.hozon.bom.resources.query.HzEWOImpactReferenceQuery;
import com.connor.hozon.bom.resources.service.change.HzEWOImpactReferenceService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.change.HzEWOImpactReference;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/15
 * @Description:
 */
@Service("HzEWOImpactReferenceService")
public class HzEWOImpactReferenceServiceImpl implements HzEWOImpactReferenceService {
    @Autowired
    private HzEWOImpactReferenceDAO hzEWOImpactReferenceDAO;
    @Override
    public List<HzEWOImpactReference> getHzEWOImpactReferences(HzEWOImpactReferenceQuery query) {
        try {
            List<HzEWOImpactReference> references = new ArrayList<>();
            List<HzEWOImpactReference> list = hzEWOImpactReferenceDAO.findImpactReferences(query);
            if(ListUtil.isNotEmpty(list)){
                list.forEach(hzEWOImpactReference -> {
                    HzEWOImpactReference reference = hzEWOImpactReference;
                    if(reference.getChecked() == null){
                        reference.setChecked(0);
                    }
                    references.add(reference);
                });
            }
            return references;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public OperateResultMessageRespDTO editHzEWOImpactReference(HzEWOImpactReference reference) {
        try{
            List<HzEWOImpactReference> insertList = new ArrayList<>();
            List<HzEWOImpactReference> updateList = new ArrayList<>();
            String [] impactAnalysisIds = reference.getImpactAnalysisIds().split(",");
            HzEWOImpactReferenceQuery query = new HzEWOImpactReferenceQuery();
            query.setEwoNo(reference.getEwoNo());
            query.setProjectId(reference.getProjectId());
            for(String id :impactAnalysisIds){
                query.setImpactAnalysisId(Long.valueOf(id));
                HzEWOImpactReference impactReference = hzEWOImpactReferenceDAO.findHzImpactReference(query);
                HzEWOImpactReference hzEWOImpactReference = reference;
                hzEWOImpactReference.setImpactAnalysisId(Long.valueOf(id));
                hzEWOImpactReference.setChecked(1);
                if(impactReference == null){
                    insertList.add(hzEWOImpactReference);
                }else {
                    updateList.add(hzEWOImpactReference);
                }
            }
            hzEWOImpactReferenceDAO.insertList(insertList);
            hzEWOImpactReferenceDAO.updateList(updateList);
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getSuccessResult();
    }
}
