package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEWOImpactReferenceQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactReferenceDAO;
import com.connor.hozon.bom.resources.service.change.HzEWOImpactReferenceService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOImpactReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: haozt
 * @Date: 2018/8/15
 * @Description:
 */
@Service("hzEWOImpactReferenceService")
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
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public WriteResultRespDTO editHzEWOImpactReference(HzEWOImpactReference reference) {
        try{
            List<HzEWOImpactReference> insertList = new ArrayList<>();
            List<HzEWOImpactReference> updateList = new ArrayList<>();
            String [] impactAnalysisIds = reference.getImpactAnalysisIds().split(",");
            HzEWOImpactReferenceQuery query = new HzEWOImpactReferenceQuery();
            query.setEwoNo(reference.getEwoNo());
            query.setProjectId(reference.getProjectId());
            List<HzEWOImpactReference> list = hzEWOImpactReferenceDAO.findImpactReferences(query);

            Set<HzEWOImpactReference> set = new HashSet<>();
            if(ListUtil.isNotEmpty(list)){
                list.forEach(reference1 -> {
                    if(reference1.getId()!=null){
                        set.add(reference1);
                    }
                });
            }

            for(String id :impactAnalysisIds){
                if(set.size()>0){
                    for(HzEWOImpactReference s:set){
                        if(s.getImpactAnalysisId().equals(Long.valueOf(id))){
                            set.remove(s);
                            break;
                        }
                    }
                }
                query.setImpactAnalysisId(Long.valueOf(id));
                HzEWOImpactReference impactReference = hzEWOImpactReferenceDAO.findHzImpactReference(query);
                HzEWOImpactReference hzEWOImpactReference = new HzEWOImpactReference();
                hzEWOImpactReference.setImpactAnalysisId(Long.valueOf(id));
                hzEWOImpactReference.setChecked(1);
                hzEWOImpactReference.setEwoNo(reference.getEwoNo());
                hzEWOImpactReference.setProjectId(reference.getProjectId());
                if(impactReference == null){
                    insertList.add(hzEWOImpactReference);
                }else {
                    if(Integer.valueOf(0).equals(impactReference.getChecked())){
                        hzEWOImpactReference.setId(impactReference.getId());
                        updateList.add(hzEWOImpactReference);
                    }
                }
            }
            if(set.size()>0){
                for(HzEWOImpactReference s :set){
                    s.setChecked(0);
                    updateList.add(s);

                }
            }
            if(ListUtil.isNotEmpty(insertList)){
                hzEWOImpactReferenceDAO.insertList(insertList);
            }
            if(ListUtil.isNotEmpty(updateList)){
                hzEWOImpactReferenceDAO.updateList(updateList);
            }

        }catch (Exception e){
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
    }
}
