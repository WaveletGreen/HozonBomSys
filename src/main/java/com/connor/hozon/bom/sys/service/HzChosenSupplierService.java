package com.connor.hozon.bom.sys.service;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.model.HzChosenSupplier;
import com.connor.hozon.bom.resources.mybatis.quotemsg.HzChosenSupplierDAO;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HzChosenSupplierService {

    @Autowired
    private HzChosenSupplierDAO hzChosenSupplierDAO;

    public JSONObject load(HzChosenSupplier hzChosenSupplier) {
        JSONObject result = new JSONObject();
        List<HzChosenSupplier> data = new ArrayList<>();

        List<HzChosenSupplier> totalCount = hzChosenSupplierDAO.selectProjectId(hzChosenSupplier.getProjectPuid());
        if("ALL".equals(hzChosenSupplier.getLimit())){
            hzChosenSupplier.setLimit(String.valueOf(totalCount.size()));
        }
        List<HzChosenSupplier> hzChosenSuppliers = hzChosenSupplierDAO.selectPage(hzChosenSupplier);

        result.put("result",hzChosenSuppliers);
        result.put("totalCount",totalCount.size());
        return result;
    }

    public JSONObject add(HzChosenSupplier hzChosenSupplier) {
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg","添加数据成功");

        Date date = new Date();
        User user = UserInfo.getUser();
        hzChosenSupplier.setCsCreateDate(date);
        hzChosenSupplier.setCsUpdateDate(date);
//        hzChosenSupplier.setCsCreator(user.getId());

        if(hzChosenSupplierDAO.insert(hzChosenSupplier)<=0?true:false){
            result.put("status",false);
            result.put("msg","添加数据失败");
            return result;
        }
        return result;
    }

    public HzChosenSupplier selectById(Long id) {
        return hzChosenSupplierDAO.selectById(id);
    }

    public JSONObject update(HzChosenSupplier hzChosenSupplier){
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg","修改成功");

        if(hzChosenSupplierDAO.update(hzChosenSupplier)<=0?true:false){
            result.put("status",false);
            result.put("msg","修改失败");
            return result;
        }
        return result;
    }
}
