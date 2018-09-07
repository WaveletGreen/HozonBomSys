package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDTO;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0RecordDao;
import com.connor.hozon.bom.bomSystem.helper.StringHelper;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/6
 */
@Service("hzCfg0Service")
public class HzCfg0Service {
    private HzCfg0RecordDao hzCfg0RecordDao;

    public HzCfg0Service(HzCfg0RecordDao hzCfg0RecordDao) {
        this.hzCfg0RecordDao = hzCfg0RecordDao;
    }

    public HzCfg0Record doGetByProjectPuid(String projectPuid) {
        return hzCfg0RecordDao.selectByPrimaryKey(projectPuid);
    }

    public List<HzCfg0Record> doLoadCfgListByProjectPuid(String projectPuid, QueryBase queryBase) {
        return hzCfg0RecordDao.selectListByProjectPuid(projectPuid, queryBase);
    }

    public List<HzCfg0Record> doLoadAddedCfgListByProjectPuid(String projectPuid) {
        return hzCfg0RecordDao.selectAddedCfgListByProjectPuid(projectPuid);
    }

    public List<HzMaterielFeatureBean> doSelectMaterielFeatureByProjectPuid(String projectPuid) {
        return hzCfg0RecordDao.selectMaterielFeatureByProjectPuid(projectPuid);
    }

    public boolean doInsertOne(HzCfg0Record record) {
        return hzCfg0RecordDao.insert(record) > 0 ? true : false;
    }

    public boolean doInsertAddCfg(HzCfg0Record record) {
        return hzCfg0RecordDao.insertAddCfg(record) > 0 ? true : false;
    }

    public HzCfg0Record doSelectOneByPuid(String puid) {
        return hzCfg0RecordDao.selectByPrimaryKey(puid);
    }

    public HzCfg0Record doSelectOneAddedCfgByPuid(String puid) {
        return hzCfg0RecordDao.selectOneAddedCfgByPuid(puid);
    }

    public boolean doUpdate(HzCfg0Record record) {
        return hzCfg0RecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    public boolean doUpdateByBatch(Map<String, Object> record) {
        return hzCfg0RecordDao.setIsSent(record) > 0 ? true : false;
    }

    public boolean doUpdateAddedCfg(HzCfg0Record record) {
        return hzCfg0RecordDao.updateAddedCfgByPrimaryKey(record) > 0 ? true : false;
    }

    public boolean doDeleteByPuid(HzCfg0Record record) {
        return false;
    }

    public boolean doDeleteAddedCfgByList(List<HzCfg0Record> records) {
        return hzCfg0RecordDao.deleteAddedCfgByList(records) > 0 ? true : false;
    }

    public boolean doDeleteCfgByList(List<HzCfg0Record> records) {
        return hzCfg0RecordDao.deleteCfgByList(records) > 0 ? true : false;
    }


    public boolean doDeleteAddedCfgById(HzCfg0Record record) {
        return hzCfg0RecordDao.deleteAddCfgByPrimaryKey(record.getPuid()) > 0 ? true : false;
    }

    /***
     * 根据table，进行查询并拼接成相关性值
     * @param projectPuid 项目puid
     * @param _list 存储结果的list
     * @param index 序号，使用封装类进行引用从而可以修改引用数据
     * @param _table 取数据的表：HZ_CFG0_RECORD是原数据，HZ_CFG0_ADD_CFG_RECORD是添加的数据
     * @return 返回当前最后一个筛选结果的的正序顺序
     */
    public int doLoadRelevance(String projectPuid, List<HzRelevanceBean> _list, int index, String _table) {
        List<HzCfg0Record> records = null;
        List<HzCfg0Record> needToUpdate = new ArrayList<>();
        if ("HZ_CFG0_RECORD".equals(_table)) {
            records = doLoadCfgListByProjectPuid(projectPuid, new HzFeatureQueryDTO());
        } else if ("HZ_CFG0_ADD_CFG_RECORD".equals(_table)) {
            records = doLoadAddedCfgListByProjectPuid(projectPuid);
        }
        for (HzCfg0Record record : records) {
            HzRelevanceBean bean = new HzRelevanceBean();
            bean.set_table(_table);
            bean.setIndex(++index);
            bean.setPuid(record.getPuid());
            bean.setRelevance(record.getpCfg0FamilyName() + "-" + record.getpCfg0ObjectId());
            bean.setRelevanceDesc((record.getpCfg0FamilyDesc() == null ? "" : record.getpCfg0FamilyDesc()) + "-" + (record.getpCfg0Desc() == null ? "" : record.getpCfg0Desc()));
            if (record.getpCfg0Relevance() == null) {
                record.setpCfg0Relevance("$ROOT." + record.getpCfg0FamilyName() + " = '" + record.getpCfg0ObjectId() + "'");
                needToUpdate.add(record);
            }
            bean.setRelevanceCode(record.getpCfg0Relevance());
            _list.add(bean);
        }
        updateByTableName(needToUpdate, _table);
        return index;
    }

    public List<HzCfg0Record> doLoadListByPuids(List<String> list) {
        Map<String, Object> _map = new HashMap<>();
        _map.put("whichTable", "HZ_CFG0_RECORD");
        _map.put("list", list);
        return hzCfg0RecordDao.selectCfg0ListByPuids(_map);
    }

    public void updateByTableName(List<HzCfg0Record> list, String table) {
        switch (table) {
            case "HZ_CFG0_RECORD":
                list.forEach(_l -> hzCfg0RecordDao.updateByPrimaryKey(_l));
                break;
            case "HZ_CFG0_ADD_CFG_RECORD":
                list.forEach(_l -> hzCfg0RecordDao.updateAddedCfgByPrimaryKey(_l));
                break;
        }
    }

    public boolean preCheck(HzCfg0Record record) {
        //存在puid的可以同名更新
        if (StringHelper.checkString(record.getPuid())) {
            return true;
        }
        record.setWhichTable("HZ_CFG0_RECORD");
        List<HzCfg0Record> list;
        list = hzCfg0RecordDao.selectByCodeAndDesc(record);
        if (list == null || list.isEmpty()) {
            record.setWhichTable("HZ_CFG0_ADD_CFG_RECORD");
            list = hzCfg0RecordDao.selectByCodeAndDesc(record);
            if (list == null || list.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 项目上的特性总数是多少，针对项目而非整个合众公司
     *
     * @param dto
     * @return
     */
    public int tellMeHowManyOfThose(HzFeatureQueryDTO dto) {
        return hzCfg0RecordDao.tellMeHowManyOfThose(dto);
    }

    /**
     * 设置已经在流程中
     *
     * @param cfgs 特性值集合
     * @return
     */
    public int doSetToProcess(List<HzCfg0Record> cfgs) {
        Map<String, Object> params = new HashMap<>();
        params.put("cfgIsInProcess", "1");
        params.put("list", cfgs);
        return hzCfg0RecordDao.setToProcess(params);
    }

    public List<HzCfg0Record> doLoadByCondition(HzFeatureQueryDTO queryBase) {
        return hzCfg0RecordDao.selectByCondition(queryBase);
    }
}
