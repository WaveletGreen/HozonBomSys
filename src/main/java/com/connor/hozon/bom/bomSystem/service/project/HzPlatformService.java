package com.connor.hozon.bom.bomSystem.service.project;

import com.connor.hozon.bom.bomSystem.dao.project.HzPlatformRecordDao;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.project.HzPlatformRecord;

import java.util.List;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/*************************************************************************************************************************
 *                                  Author: Fancyears·Maylos·Mayways
 * Date: 2018/6/1 11:30
 *
 * Description:
 *
 * ***********************************************************************************************************************/
@Service("hzPlatformService")
public class HzPlatformService {
    @Autowired
    HzPlatformRecordDao hzPlatformRecordDao;

    public HzPlatformRecord doGetByPuid(String puid) {
        return hzPlatformRecordDao.selectByPrimaryKey(puid);
    }

    public boolean doUpdate(HzPlatformRecord record) {
        return hzPlatformRecordDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    public boolean doInsertOne(HzPlatformRecord record) {
        return hzPlatformRecordDao.insert(record) > 0 ? true : false;
    }

    public boolean doDeleteByPuid(String puid) {
        return hzPlatformRecordDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    public List<HzPlatformRecord> doGetAllPlatform() {
        return hzPlatformRecordDao.selectAll();
    }

    /**
     * 验证平台是否符合要求
     * <p>
     * 只验证平台代码和平台名称
     *
     * @param platform 平台对象
     * @return
     */
    public boolean validate(HzPlatformRecord platform) {
        //平台代号和名称不能为空
        if (null == platform.getpPlatformCode() || null == platform.getpPlatformName() || "".equals(platform.getpPlatformCode()) || "".equals(platform.getpPlatformName())) {
            return false;
        }
        return true;
    }

    /**
     * 验证平台是否符合要求
     * <p>
     * 只验证平台代码和平台名称
     *
     * @param platform 平台对象
     * @return
     */
    public boolean modifyValidate(HzPlatformRecord platform) {
        //平台代号和名称不能为空
        if (validate(platform) || null == platform.getPuid() || "".equals(platform.getPuid())) {
            return false;
        }
        return true;
    }

    /**
     * 根据平台代号查找平台数据
     *
     * @param platformCode
     * @return
     */
    public HzPlatformRecord doGetByPlatformCode(String platformCode) {
        return hzPlatformRecordDao.selectByPlatformCode(platformCode);
    }

    /**
     * 查重平台编号
     *
     * @param platform 平台对象
     * @return
     */
    public JSONObject doValidateCodeWithPuid(IProject platform) {
        JSONObject result = new JSONObject();
        result.put("valid", true);
        HzPlatformRecord _platform;
        //有puid，则时更新，没有则为新增
        if (checkString(platform.getPuid())) {
            _platform = doGetByPuid(platform.getPuid());
            //根据puid查出来的同名，代表自身，则验证通过
            if (_platform.getCode().trim().equals(platform.getCode().trim())) {
                result.put("valid", true);
            } else if ((_platform = doGetByPlatformCode(platform.getCode().trim())) != null) {
                //不是自身，更换了代号，检查是否有同代号的，有同代号则不允许验证通过
                result.put("valid", false);
            }
        } else if ((_platform = doGetByPlatformCode(platform.getCode().trim())) != null) {
            result.put("valid", false);
        }
        return result;
    }
}
