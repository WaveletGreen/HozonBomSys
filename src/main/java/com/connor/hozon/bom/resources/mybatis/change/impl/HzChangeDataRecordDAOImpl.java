package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.mybatis.change.HzChangeDataRecordDAO;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.change.HzChangeDataRecord;

import java.util.ArrayList;
import java.util.List;

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
            if (ListUtil.isNotEmpty(records)) {
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
}
