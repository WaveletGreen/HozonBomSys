package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.dao.bom.HzMBomToERPDao;
import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynBomService;
import com.connor.hozon.bom.resources.dto.request.UpdateHzMaterielReqDTO;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.bom.HzMBomToERPBean;
import sql.pojo.project.HzMaterielRecord;
import webservice.base.bom.ZPPTCO005;
import webservice.base.helper.UUIDHelper;
import webservice.logic.ReflectBom;
import webservice.option.ActionFlagOption;
import webservice.service.impl.bom5.TransBomService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class SynBomService implements ISynBomService {
    /**
     * 用来获取传输MBOM的数据
     */
    @Autowired
    HzMBomToERPDao hzMBomToERPDao;
    /**
     * 发送服务
     */
    @Autowired
    TransBomService transBomService;

    /**
     * 执行更新的时候传，有可能时传多条，传多条也演变成传单挑数据传输，先找到父层，再找到子层，父+子一条一条传即可
     *
     * @param dtos
     * @return
     */
    @Override
    public JSONObject updateByUids(List<UpdateHzMaterielReqDTO> dtos) {
        return null;
    }

    /**
     * 删除时候传
     *
     * @param dtos
     * @return
     */
    @Override
    public JSONObject deleteByUids(List<UpdateHzMaterielReqDTO> dtos) {
        return null;
    }

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    @Override
        public JSONObject synAllByProjectUid(String projectPuid) {
        //每次都清空缓存
        transBomService.setClearInputEachTime(true);
        StringBuilder sbs = new StringBuilder();
        StringBuilder sbf = new StringBuilder();
        sbs.append("更新成功项:<br/>");
        sbf.append("更新失败项:<br/>");
        JSONObject result = new JSONObject();

        boolean hasFail = false;

        List<HzMBomToERPBean> beanListIs2Y = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 1);
        List<HzMBomToERPBean> beanListIsnot2Y = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 0);

        List<String> isNot2YUids = new ArrayList<>();
        List<String> is2YUids = new ArrayList<>();

        /**
         * 筛选puid
         */
        beanListIs2Y.forEach(l -> is2YUids.add(l.getPuid()));
        beanListIsnot2Y.forEach(l -> isNot2YUids.add(l.getPuid()));
        /**
         * 再次获取数据
         */
        beanListIs2Y = hzMBomToERPDao.selectByUidOfBatch(projectPuid, is2YUids);
        beanListIsnot2Y = hzMBomToERPDao.selectByUidOfBatch(projectPuid, isNot2YUids);

        Map<String, HzMBomToERPBean> mapOf2YUid = new HashMap<>();
        Map<String, HzMBomToERPBean> mapOfIsnot2YUid = new HashMap<>();
        //数据包号计数器
        int index = 0;
        //没有找到父层结构的，不发送，告诉用户哪个发送失败
        List<HzMBomToERPBean> mapOfFailure = new ArrayList<>();
        //行号，记录该行的位置
        Map<String, HzMBomToERPBean> lineNumofBean = new HashMap<>();
        //存储数据包号,包含行号数据
        Map<String, Map<String, HzMBomToERPBean>> mapOfPackNo = new HashMap<>();

        //2Y层进缓存
        beanListIs2Y.forEach(bean -> {
            mapOf2YUid.put(bean.getBomUid(), bean);
        });

        //包号
        String packNum = UUIDHelper.generateUpperUid();

        for (HzMBomToERPBean bean : beanListIsnot2Y) {
            HzMBomToERPBean parent = mapOf2YUid.get(bean.getParentUID());
            if (parent != null) {
                ReflectBom reflectBom = new ReflectBom(bean);
                reflectBom.setHeadOfBomLine(parent.getBomLineId());
                //设置数据包号
                reflectBom.setPackNo(String.valueOf(index));
                //设置行号
                reflectBom.setLineNum(bean.getPuid().substring(0, 5));
                //第一次发送是传所有，都是新增
                reflectBom.setActionFlag(ActionFlagOption.ADD);
                //添加进缓存
                transBomService.getInput().getItem().add(reflectBom.getZpptci005());
                //将行号和bean进行绑定
                lineNumofBean.put(String.valueOf(index), bean);
                index++;
                //300的整数倍放置1个包号
                if ((index % 300) == 0) {
                    //经行号集合与数据包号绑定
                    mapOfPackNo.put(packNum, lineNumofBean);
                    //重新生成包号
                    packNum = UUIDHelper.generateUpperUid();
                }
            } else {
                mapOfFailure.add(bean);
            }
        }

        transBomService.execute();
        List<ZPPTCO005> resultPool = transBomService.getOut().getItem();

        for (ZPPTCO005 zpptco005 : resultPool) {
            if ("S".equalsIgnoreCase(zpptco005.getTYPE())) {
                sbs.append("&emsp;&emsp;&emsp;&emsp;" + lineNumofBean.get(zpptco005.getZITEM()).getBomLineId() + "<br/>");
            } else {
                sbf.append("&emsp;&emsp;&emsp;&emsp;" + lineNumofBean.get(zpptco005.getZITEM()).getBomLineId() + "(" + zpptco005.getMESSAGE() + ")<br/>");
                hasFail = true;
            }
        }
        if (hasFail) {
            result.put("msg", sbs.append("<br/>" + sbf).toString());
        } else {
            result.put("msg", sbs.toString());
        }
        result.put("status", true);
        return result;
    }


    /**
     * 执行操作
     *
     * @param sorted
     * @param option
     * @return
     */
    private JSONObject execute(List<HzMaterielRecord> sorted, ActionFlagOption option) {
        transBomService.setClearInputEachTime(true);
        StringBuilder sbs = new StringBuilder();
        StringBuilder sbf = new StringBuilder();
        JSONObject result = new JSONObject();

        Map<String, HzMaterielRecord> _mapCoach = new HashMap<>();

        sbs.append("更新成功项:<br/>");
        sbf.append("更新失败项:<br/>");

        boolean hasFail = false;

//        for (HzMaterielRecord record : sorted) {
//            ReflectMateriel reflectMateriel = new ReflectMateriel(record);
//            /////////////////////////////////////////////////////手动设置一些必填参数////////////////////////////////////////////////////
//            //设置包号
//            String packNo = UUIDHelper.generateUpperUid();
//            reflectMateriel.setPackNo(packNo);
//            //设置行号
//            reflectMateriel.setLineNum(packNo.substring(0, 5));
//
//            //更新操作
//            reflectMateriel.setActionFlagOption(option);
//            if (!_factoryCoach.containsKey(record.getpFactoryPuid())) {
//                HzFactory factory = hzFactoryDAO.findFactory(record.getpFactoryPuid(), null);
//                _factoryCoach.put(record.getpFactoryPuid(), factory.getpFactoryCode());
//            }
//            //设置工厂
//            reflectMateriel.setFactory(_factoryCoach.get(record.getpFactoryPuid()));
//            reflectMateriel.setMRPAndPurchase(record.getpMrpController(), record.getResource(), "默认公告");
//            /////////////////////////////////////////////////////手动设置一些必填参数////////////////////////////////////////////////////
//            transMasterMaterialService.getInput().getItem().add(reflectMateriel.getMm().getZpptci001());
//            //加入缓存
//            _mapCoach.put(packNo, record);
//        }
//        transMasterMaterialService.execute();
//        List<ZPPTCO001> resultPool = transMasterMaterialService.getOut().getItem();
//
//        for (ZPPTCO001 zpptco001 : resultPool) {
//            if ("S".equalsIgnoreCase(zpptco001.getTYPE())) {
//                sbs.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(zpptco001.getGUID()).getpMaterielCode() + "<br/>");
//            } else {
//                sbf.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(zpptco001.getGUID()).getpMaterielCode() + "(" + zpptco001.getMESSAGE() + ")<br/>");
//                hasFail = true;
//            }
//        }
//        if (hasFail) {
//            result.put("msg", sbs.append("<br/>" + sbf).toString());
//        } else {
//            result.put("msg", sbs.toString());
//        }
//        result.put("status", true);
        return result;
    }
}
