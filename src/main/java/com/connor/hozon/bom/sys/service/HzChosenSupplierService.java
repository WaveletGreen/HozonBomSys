package com.connor.hozon.bom.sys.service;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.model.HzChosenSupplier;
import com.connor.hozon.bom.resources.mybatis.quotemsg.HzChosenSupplierDAO;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HzChosenSupplierService {

    @Autowired
    private HzChosenSupplierDAO hzChosenSupplierDAO;

    public Map<String, Object> load(HzChosenSupplier hzChosenSupplier) {
        Map<String, Object> result = new HashMap<>();
        List<HzChosenSupplier> data = new ArrayList<>();

        List<HzChosenSupplier> totalCount = null;
        List<HzChosenSupplier> hzChosenSuppliers = null;

        User user = UserInfo.getUser();
        if(PrivilegeUtil.hasAdministratorPrivilege(user)){
            totalCount = hzChosenSupplierDAO.selectProjectId(hzChosenSupplier.getProjectPuid());
            if("ALL".equals(hzChosenSupplier.getLimit())){
                hzChosenSupplier.setLimit(String.valueOf(totalCount.size()));
            }
            hzChosenSuppliers = hzChosenSupplierDAO.selectPage(hzChosenSupplier);
        }else {
            HzChosenSupplier hzChosenSupplierQuery = new HzChosenSupplier();
            hzChosenSupplierQuery.setProjectPuid(hzChosenSupplier.getProjectPuid());
            hzChosenSupplierQuery.setCsCreator(user.getLogin());
            totalCount = hzChosenSupplierDAO.selectCreator(hzChosenSupplierQuery);

            hzChosenSupplier.setCsCreator(user.getLogin());
            hzChosenSuppliers = hzChosenSupplierDAO.selectPage(hzChosenSupplier);
        }
//       后端统计
//        if(hzChosenSuppliers!=null&&hzChosenSuppliers.size()>0){
//            HzChosenSupplier hzChosenSupplierCount = new HzChosenSupplier();
//            hzChosenSupplierCount.setParts(0.0);
//            hzChosenSupplierCount.setSingleCarPrice(0.0);
//            hzChosenSupplierCount.setMoldsCostNotRevenue(0.0);
//            hzChosenSupplierCount.setMoldsCostHasRevenue(0.0);
//            hzChosenSupplierCount.setGaugeCost(0.0);
//            hzChosenSupplierCount.setExploitationCost(0.0);
//            for(HzChosenSupplier hzChosenSupplier1 : hzChosenSuppliers){
//                hzChosenSupplierCount.setItemId("总计");
//                hzChosenSupplierCount.setParts(hzChosenSupplier1.getParts()==null?0.0:hzChosenSupplier1.getParts()+hzChosenSupplierCount.getParts());
//                hzChosenSupplierCount.setSingleCarPrice(hzChosenSupplier1.getSingleCarPrice()==null?0.0:hzChosenSupplier1.getSingleCarPrice()+hzChosenSupplierCount.getSingleCarPrice());
//                hzChosenSupplierCount.setMoldsCostNotRevenue(hzChosenSupplier1.getMoldsCostNotRevenue()==null?0.0:hzChosenSupplier1.getMoldsCostNotRevenue()+hzChosenSupplierCount.getMoldsCostNotRevenue());
//                hzChosenSupplierCount.setMoldsCostHasRevenue(hzChosenSupplier1.getMoldsCostHasRevenue()==null?0.0:hzChosenSupplier1.getMoldsCostHasRevenue()+hzChosenSupplierCount.getMoldsCostHasRevenue());
//                hzChosenSupplierCount.setGaugeCost(hzChosenSupplier1.getGaugeCost()==null?0.0:hzChosenSupplier1.getGaugeCost()+hzChosenSupplierCount.getGaugeCost());
//                hzChosenSupplierCount.setExploitationCost(hzChosenSupplier1.getExploitationCost()==null?0.0:hzChosenSupplier1.getExploitationCost()+hzChosenSupplierCount.getExploitationCost());
//            }
//            hzChosenSuppliers.add(hzChosenSupplierCount);
//        }
        result.put("result",hzChosenSuppliers);
        result.put("totalCount",totalCount==null?0:(totalCount.size()));
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
        hzChosenSupplier.setCsCreator(user.getLogin());

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

    public Map<String, Object> update(HzChosenSupplier hzChosenSupplier){
        Map<String, Object> result = new HashMap<>();
        result.put("status",true);
        result.put("msg","修改成功");

        if(hzChosenSupplierDAO.update(hzChosenSupplier)<=0?true:false){
            result.put("status",false);
            result.put("msg","修改失败");
            return result;
        }
        return result;
    }

    public JSONObject delete(String ids) {
        JSONObject result = new JSONObject();
        result.put("status",true);
        result.put("msg","删除成功");

        String[] idsArr = ids.split(",");
        int deleteFlag = hzChosenSupplierDAO.delete(idsArr);
        if(deleteFlag<=0){
            result.put("status",false);
            result.put("msg","删除失败");
        }
        return result;
    }
}
