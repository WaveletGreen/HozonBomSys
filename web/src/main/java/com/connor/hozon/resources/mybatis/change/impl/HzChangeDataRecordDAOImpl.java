package com.connor.hozon.resources.mybatis.change.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.resources.domain.query.HzChangeDataQuery;
import com.connor.hozon.resources.mybatis.change.HzChangeDataRecordDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeDataRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:
 */
@Service("hzChangeDataRecordDAO")
public class HzChangeDataRecordDAOImpl extends BaseSQLUtil implements HzChangeDataRecordDAO {
    @Override
    public int insert(HzChangeDataRecord record) {
        return super.insert("HzChangeDataRecordDAOImpl_insert",record);
    }

    @Override
    public int insertList(List<HzChangeDataRecord> records) {
        try {
            if (ListUtils.isNotEmpty(records)) {
                int size = records.size();
                //分批更新数据 一次1000条
                int i = 0;
                int cout = 0;
                synchronized (this){
                    if (size > 1000) {
                        for (i = 0; i < size / 1000; i++) {
                            List<HzChangeDataRecord> list1 = new ArrayList<>();
                            for (int j = 0; j < 1000; j++) {
                                list1.add(records.get(cout));
                                cout++;
                            }
                            super.insert("HzChangeDataRecordDAOImpl_insertList",list1);
                        }
                    }
                    if (i * 1000 < size) {
                        List<HzChangeDataRecord> list1 = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list1.add(records.get(cout));
                            cout++;
                        }
                        super.insert("HzChangeDataRecordDAOImpl_insertList",list1);

                    }
                }
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<HzChangeDataRecord> getChangeDataTableName(HzChangeDataQuery query) {

        return super.findForList("HzChangeDataRecordDAOImpl_getChangeDataTableName",query);
    }

    @Override
    public List<String> getChangeDataPuids(HzChangeDataQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("tableName",query.getTableName());
        map.put("orderId",query.getOrderId());
        return super.findForList("HzChangeDataRecordDAOImpl_getChangeDataPuids",map);
    }

    @Override
    public int deleteByOrderIdAndTableName(HzChangeDataRecord hzChangeDataRecord){
        return super.executeDelete(hzChangeDataRecord,"HzChangeDataRecordDAOImpl_deleteByOrderIdAndTableName");
    }
}
