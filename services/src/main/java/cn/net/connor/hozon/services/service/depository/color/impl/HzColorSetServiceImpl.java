/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.depository.color.impl;

import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.common.util.DateStringUtils;
import cn.net.connor.hozon.common.util.UUIDHelper;
import cn.net.connor.hozon.dao.dao.depository.color.HzCfg0ColorSetDao;
import cn.net.connor.hozon.dao.pojo.depository.color.HzCfg0ColorSet;
import cn.net.connor.hozon.dao.pojo.depository.color.HzColorSetQuery;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.services.service.depository.color.HzColorSetService;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static cn.net.connor.hozon.common.util.StringUtils.checkString;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
@Transactional
public class HzColorSetServiceImpl implements HzColorSetService {
    @Autowired
    private HzCfg0ColorSetDao hzCfg0ColorSetDao;

    public Map<String, Object> selectAll(HzColorSetQuery query) {
        Date now = new Date();
        Map<String, Object> result = new HashMap<>();
        Map<String,String> queryMap = new HashMap<>();
        query.setSort(HzColorSetQuery.reflectToDBField(query.getSort()));
        List<HzCfg0ColorSet> colorSet = hzCfg0ColorSetDao.selectAll(query);
        List<HzCfg0ColorSet> toUpdate = new ArrayList<>();
        for (HzCfg0ColorSet set : colorSet) {
            if (set.getpColorAbolishDate() == null) {
                continue;
            }
            if (now.after(set.getpColorAbolishDate())) {
                toUpdate.add(set);
            }
        }
        //更新废止的颜色信息状态，设置为不可用状态
        toUpdate.forEach(set -> {
            hzCfg0ColorSetDao.updateStatusByPrimaryKey(set);
        });
        result.put("totalCount", hzCfg0ColorSetDao.tellMeHowManyOfIt(query));
        result.put("result", colorSet);
        return result;
    }

    /**
     * 根据分页条件获取颜色
     *
     * @param queryBase
     * @return
     */
    public List<HzCfg0ColorSet> doGetAll(QueryBase queryBase) {
        return hzCfg0ColorSetDao.selectAll(queryBase);
    }

    /**
     * 获取全部颜色
     *
     * @return
     */
    public List<HzCfg0ColorSet> doGetAll() {
        return hzCfg0ColorSetDao.selectAll(new HzColorSetQuery());
    }

    /**
     * @param entity 颜色
     * @return boolean
     * Description: 根据puid检查是否有颜色信息
     * Date: 2018/5/21 17:08
     */
    public HzCfg0ColorSet getById(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.selectByPrimaryKey(entity);
    }

    /**
     * @param entity 颜色对象
     * @return boolean 只更新1个
     * Description: 执行更细颜色信息，只更新1个
     * Date: 2018/5/21 17:08
     */
    public boolean doUpdate(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.updateByPrimaryKey(entity) == 1 ? true : false;
    }

    /**
     * @param entity 颜色信息的集合
     * @return boolean 是否删除成功
     * Description: 执行批量删除颜色信息
     * Date: 2018/5/21 17:07
     */
    public boolean doDeleteByList(List<HzCfg0ColorSet> entity) {
        return hzCfg0ColorSetDao.deleteByBatch(entity) > 0 ? true : false;
    }

    /**
     * @param entity 颜色对象
     * @return boolean 是否添加成功
     * Description: 添加一个颜色信息
     * Date: 2018/5/21 17:07
     */
    public boolean doAddOne(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.insert(entity) > 0 ? true : false;
    }

    /**
     * 根据代码获取1个颜色对象
     *
     * @param entity
     * @return
     */
    public HzCfg0ColorSet doGetByColorCode(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.selectByColorCode(entity);
    }

    /**
     * 根据主键更新状态
     *
     * @param entity
     * @return
     */
    public boolean doUpdateStatusByPk(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.updateStatusByPrimaryKey(entity) > 0 ? true : false;
    }

    /**
     * 批量逻辑删除
     *
     * @param entity
     * @return
     */
    public boolean doLogicDeleteByBatch(List<HzCfg0ColorSet> entity) {
        return hzCfg0ColorSetDao.logicDeleteByBatch(entity) > 0 ? true : false;
    }

    @Override
    public JSONObject delete(List<HzCfg0ColorSet> set) {
        JSONObject result = new JSONObject();
        StringBuilder sb = new StringBuilder();
        set.forEach(_set ->
                sb.append(_set.getpColorName() + ", ")
        );
        if (doDeleteByList(set)) {
            result.put("status", true);
            result.put("msg", "删除颜色信息:" + sb + "成功");
        } else {
            result.put("status", false);
            result.put("msg", "删除颜色信息:" + sb + "失败");
        }
        return result;
    }

    @Override
    public JSONObject validateCodeWithId(HzCfg0ColorSet set) {
        JSONObject result = new JSONObject();
        result.put("valid", true);
        HzCfg0ColorSet _set = null;
        //有puid，则时更新，没有则为新增
        if (checkString(set.getPuid())) {
            _set = getById(set);
            //根据puid查出来的同名，代表自身，则验证通过
            if (_set.getpColorCode().equals(set.getpColorCode())) {
                result.put("valid", true);
            } else if ((_set = doGetByColorCode(set)) != null) {
                //不是自身，更换了代号，检查是否有同代号的，有同代号则不允许验证通过
                result.put("valid", false);
            }
        } else if ((_set = doGetByColorCode(set)) != null) {
            result.put("valid", false);
        }
        return result;
    }

    @Override
    public JSONObject add(HzCfg0ColorSet set) {
        JSONObject result = new JSONObject();
        Date now = new Date();
        User user = UserInfo.getUser();
        boolean resultFromDB;
        if (set.getPuid() == null || "".equals(set.getPuid())) {
            set.setPuid(UUIDHelper.generateUpperUid());
        }
        /*try {

            set.setpColorEffectedDate(DateStringUtils.stringToDate2(set.getStrColorEffectedDate()));
            if (set.getpColorAbolishDate() == null) {
                set.setpColorAbolishDate(new Date());
            }
            if (set.getpColorEffectedDate().after(set.getpColorAbolishDate())) {
                set.setpColorStatus(0);
            } else {
                set.setpColorStatus(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("从字符串解析时间失败", e);
        }*/
        //生效时间由审核完成只有更新
        set.setpColorModifyDate(now);
        //失效时间设置为9999
        set.setpColorAbolishDate(DateStringUtils.forever());
        //创建时间由数据库默认值定义
        //  set.setpColorCreateDate(now);
        set.setpColorModifier(user.getUsername());
        set.setpColorStatus(0);

        while (true) {
            if (getById(set) == null) {
                resultFromDB = doAddOne(set);
                break;
            } else {
                set.setPuid(UUIDHelper.generateUpperUid());
            }
        }
        if (resultFromDB) {
            result.put("status", resultFromDB);
            result.put("msg", "添加颜色信息:" + set.getpColorName() + "成功");
        } else {
            result.put("status", resultFromDB);
            result.put("msg", "添加颜色信息:" + set.getpColorName() + "失败,请联系系统管理员");
        }
        return result;
    }
}
