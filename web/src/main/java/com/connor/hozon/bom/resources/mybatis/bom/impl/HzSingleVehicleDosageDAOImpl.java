package com.connor.hozon.bom.resources.mybatis.bom.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.bom.resources.mybatis.bom.HzSingleVehicleDosageDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzSingleVehicleDosage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/10/16
 * @Description:
 */
@Deprecated
@Service("hzSingleVehicleDosageDAO")
public class HzSingleVehicleDosageDAOImpl extends BaseSQLUtil implements HzSingleVehicleDosageDAO {

    @Override
    public int insertList(List<HzSingleVehicleDosage> list) {
        try {
            if (ListUtils.isNotEmpty(list)) {
                int size = list.size();
                //分批更新数据 一次1000条
                int i = 0;
                int cout = 0;
                synchronized (this){
                    if (size > 1000) {
                        for (i = 0; i < size / 1000; i++) {
                            List<HzSingleVehicleDosage> list1 = new ArrayList<>();
                            for (int j = 0; j < 1000; j++) {
                                list1.add(list.get(cout));
                                cout++;
                            }

                            super.insert("HzSingleVehicleDosageDAOImpl_insertList",list1);

                        }
                    }
                    if (i * 1000 < size) {
                        List<HzSingleVehicleDosage> list1 = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list1.add(list.get(cout));
                            cout++;
                        }

                        super.insert("HzSingleVehicleDosageDAOImpl_insertList",list1);

                    }
                }

            }
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public int updateList(List<HzSingleVehicleDosage> hzSingleVehicleDosages) {
        return 0;
    }

    @Override
    public List<HzSingleVehicleDosage> findSingleVehicleByBomPuid(String eBomPuid, String projectId) {
        Map<String,Object> map  = new HashMap<>();
        map.put("eBomPuid",eBomPuid);
        map.put("projectId",projectId);
        return super.findForList("HzSingleVehicleDosageDAOImpl_findSingleVehicleByBomPuid",map);
    }
}
