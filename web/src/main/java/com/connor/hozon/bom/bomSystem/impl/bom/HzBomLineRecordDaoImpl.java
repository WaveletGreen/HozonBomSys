/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzBomLineRecordDao;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import cn.net.connor.hozon.common.util.ListUtils;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomLineRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzBomLineRecordDao")
public class HzBomLineRecordDaoImpl extends BaseSQLUtil implements HzBomLineRecordDao {
    /**
     * 更新颜色件信息  1是颜色件 0不是
     * @param puid
     * @param isColorPart
     * @return
     */
    public int updateColorPart(String puid,Integer isColorPart){
        Map<String,Object> map  = new HashMap<>();
        map.put("puid",puid);
        map.put("colorPart",isColorPart);
        return super.update("HzBomLineRecordDaoImpl_updateColorPart",map);
    }

    /**
     * 找出一条BOMLine 对象 走变更查询单一条记录
     *
     * @param map 过滤器
     * @return
     * @author haozt
     */
    public HzBomLineRecord findBomLineByPuid(Map map) {
        if (null ==  map.get("tableName")|| map.get("tableName").equals("")) {
            map.put("tableName", ChangeTableNameEnum.HZ_EBOM.getTableName());
        }
        return super.findForObject("HzBomLineRecordDaoImpl_findBomLineByPuid", map);
    }

    /**
     * 找出一条BOMLine 对象 与变更无关
     *
     * @param map 需要传入的参数是<strong>projectId----项目ID</strong> <span style='color:red;'>puid---BOMLine主键</span>
     * @return
     * @author haozt
     */
    public HzBomLineRecord findBomLine(Map map) {
        return super.findForObject("HzBomLineRecordDaoImpl_findBomLine", map);
    }


    /**
     * EBOM 批量更新 根据零件号来更新
     * @param records
     * @return
     */
    public int updateList(List<HzBomLineRecord> records) {
        try {
            if (ListUtils.isNotEmpty(records)) {
                int size = records.size();
                //分批更新数据 一次1000条
                int i = 0;
                int cout = 0;
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzBomLineRecord> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        super.update("HzEbomRecordDAOImpl_updateList",list);
                    }
                }
                if (i * 1000 < size) {
                    List<HzBomLineRecord> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    super.update("HzEbomRecordDAOImpl_updateList",list);
                }

            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量更新
     * 走变更 根据puid更新
     * @param records
     * @return
     */
    @Deprecated
    public int updateBatch(List<HzBomLineRecord> records) {
        try {
            if (ListUtils.isNotEmpty(records)) {
                int size = records.size();
                //分批更新数据 一次1000条
                int i = 0;
                int cout = 0;
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzBomLineRecord> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        super.update("HzEbomRecordDAOImpl_updateBatch", list);
                    }
                }
                if (i * 1000 < size) {
                    List<HzBomLineRecord> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    super.update("HzEbomRecordDAOImpl_updateBatch", list);
                }

            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<HzBomLineRecord> selectByPuids(List<String> withCfgPuids) {
        return executeQueryByPass(new HzBomLineRecord(), withCfgPuids,"HzBomLineRecordDaoImpl_selectByPuids");
    }
}
