package com.connor.hozon.resources.service.change.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.resources.domain.dto.response.HzChangeListRespDTO;
import com.connor.hozon.resources.domain.query.HzChangeListByPageQuery;
import com.connor.hozon.resources.mybatis.change.HzChangeListDAO;
import com.connor.hozon.resources.service.change.HzChangeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeListRecord;

import java.util.ArrayList;
import java.util.List;

@Service("hzChangeListService")
public class HzChangeListServiceImpl implements HzChangeListService {
    @Autowired
    private HzChangeListDAO hzChangeListDAO;

    @Override
    public List<HzChangeListRespDTO> findChangeList(HzChangeListByPageQuery query) {
        List<HzChangeListRespDTO> changeList = new ArrayList<>();
        try{
            //根据变更表单从HZ_CHANGE_LIST_RECORD表中查找引用数据
            List<HzChangeListRecord> infos =  hzChangeListDAO.findChangeList(query.getFormId());

            if(ListUtils.isNotEmpty(infos)){
                for(int i=0;i<infos.size();i++){
                     HzChangeListRespDTO respDTO = new HzChangeListRespDTO();
                     respDTO.setId(infos.get(i).getId());
                     respDTO.setFormId(infos.get(i).getFormId());
                     respDTO.setItemId(infos.get(i).getItemId());
                     respDTO.setItemRevision(infos.get(i).getItemRevision());

                    changeList.add(respDTO);
                }
                return changeList;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
