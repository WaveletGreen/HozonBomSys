package com.connor.hozon.resources.service.bom.impl;

import cn.net.connor.hozon.dao.dao.depository.project.HzProjectLibsDao;
import cn.net.connor.hozon.dao.dao.interaction.FeatureBomLineRelationHistoryDao;
import cn.net.connor.hozon.dao.dao.interaction.HzConfigBomColorDao;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzMbomLineRecord;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzSingleVehiclesBomRecord;
import cn.net.connor.hozon.dao.pojo.depository.project.HzProjectLibs;
import cn.net.connor.hozon.dao.pojo.interaction.HzSingleVehicleBomLineBean;
import cn.net.connor.hozon.dao.pojo.interaction.HzSingleVehicles;
import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.dao.interaction.HzSingleVehicleBomLineDao;
import com.connor.hozon.dao.interaction.HzSingleVehiclesDao;
import com.connor.hozon.service.interaction.FeatureBomLineRelationHistoryService;
import com.connor.hozon.resources.domain.dto.response.HzSingleVehiclesBomRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.resources.domain.model.HzSingleVehiclesFactory;
import com.connor.hozon.resources.domain.query.HzMbomTreeQuery;
import com.connor.hozon.resources.domain.query.HzSingleVehiclesBomByPageQuery;
import cn.net.connor.hozon.services.common.enumtype.MbomTableNameEnum;
import com.connor.hozon.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.resources.mybatis.bom.HzSingleVehiclesBomDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.bom.HzSingleVehiclesBomServices;
import com.connor.hozon.resources.service.bom.HzSingleVehiclesServices;
import integration.service.integrate.impl.SynBomServiceImpl;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:
 */
@Service("hzSingleVehiclesBomServices")
public class HzSingleVehiclesBomServicesImpl implements HzSingleVehiclesBomServices {

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzMbomRecordDAO hzMbomRecordDAO;

    @Autowired
    private HzSingleVehicleBomLineDao hzSingleVehicleBomLineDao;

    @Autowired
    private HzProjectLibsDao hzProjectLibsDao;

    @Autowired
    private HzSingleVehiclesDao hzSingleVehiclesDao;

    @Autowired
    private HzSingleVehiclesBomDAO hzSingleVehiclesBomDAO;

    @Autowired
    private SynBomServiceImpl synBomService;


    @Value("${singleVehicleDebug}")
    boolean singleVehicleDebug;

    @Autowired
    HzConfigBomColorDao hzConfigBomColorDao;

    @Autowired
    FeatureBomLineRelationHistoryService featureBomLineRelationHistoryService;
    @Autowired
    private FeatureBomLineRelationHistoryDao featureBomLineRelationHistoryDao;

    @Override
    public WriteResultRespDTO analysisSingleVehicles(String projectId) {
        /**
         * 解算单车BOM逻辑
         * 1.必须先有超级MBOM，单车BOM信息来自于超级MBOM
         * 2.将所有项目全部找出，需解算产生所有项目的单车BOM
         * 3.获取配置信息，所有单车所选配的2Y层结构，带颜色
         * 4.将所有的2Y层信息 带出其子层信息 组合成为一个单车BOM
         * 5.配置定时器任务，每天0点准时进行单车BOM解算
         */

        //找全部项目
//        List<HzProjectLibs> projectLibs = hzProjectLibsDao.selectAllProject();

        //给单车清单表添加数据
//        hzSingleVehiclesServices.refreshSingleVehicle(projectId);
        try {
            //单车信息
            List<HzSingleVehicles> hzSingleVehicles = hzSingleVehiclesDao.selectByProjectUid(projectId);
            List<HzSingleVehiclesBomRecord> hzSingleVehiclesBomRecords = new ArrayList<>();
            if (ListUtils.isNotEmpty(hzSingleVehicles)) {
                for (HzSingleVehicles vehicles : hzSingleVehicles) {
                    if (null == vehicles.getId()) {//不存在次单车时 continue
                        continue;
                    }
                    //单车所包含的所有2Y层 带颜色信息
                    List<HzSingleVehicleBomLineBean> hzSingleVehicleBomLineBeans = hzSingleVehicleBomLineDao.selectFullConfigColorSet(projectId, vehicles.getSvlDmbId());
                    if (ListUtils.isNotEmpty(hzSingleVehicleBomLineBeans)) {//不为空则添加，为空继续
                        for (HzSingleVehicleBomLineBean bean : hzSingleVehicleBomLineBeans) {
                            //获取单车全部BOM信息
                            HzMbomTreeQuery hzMbomTreeQuery = new HzMbomTreeQuery();
                            hzMbomTreeQuery.setTableName(MbomTableNameEnum.tableName(0));
                            hzMbomTreeQuery.setProjectId(projectId);
                            if (StringUtils.isNotBlank(bean.getColorUid()) && !"-".equals(bean.getColorUid())) {
                                hzMbomTreeQuery.setColorId(bean.getColorUid());
                            }
                            hzMbomTreeQuery.setPuid(bean.getBomLineUid());
                            hzMbomTreeQuery.setStatus(1);
                            List<HzMbomLineRecord> records = hzMbomRecordDAO.getHzMbomTree(hzMbomTreeQuery);
                            if (ListUtils.isNotEmpty(records)) {
                                for (HzMbomLineRecord record : records) {
                                    hzSingleVehiclesBomRecords.add(HzSingleVehiclesFactory.mbomRecordToSingleVehiclesBom(record, vehicles.getId()));
                                }
                            }
                        }
                    }
                }

            }

            StringBuffer stringBuffer = new StringBuffer();
            //插入数据
            //todo  需要把数据传给SAP  单车BOM的 单车BOM不存在编辑数据 方便点 同步数据的时候 就直接先删后增
            boolean sapInsert = true;
            if (ListUtils.isNotEmpty(hzSingleVehiclesBomRecords)) {
                if (!singleVehicleDebug) {
                    List<String> puids = hzSingleVehiclesBomDAO.getAllPuidByProjectId(projectId);
                    if (ListUtils.isNotEmpty(puids)) {
                        try {
                            JSONObject object = synBomService.deleteByUids(projectId, puids);// 通知SAP进行删除
                            Object fail = object.get("fail");
                            if (fail instanceof List) {
                                List failList = (List) fail;
                                if (ListUtils.isNotEmpty(failList)) {
                                    stringBuffer.append("数据可能有未符合SAP系统的规范!<br>");
                                    for (int j = 0; j < failList.size(); j++) {
                                        String str = "零件号" + (String) ((JSONObject) failList.get(j)).get("itemId");
                                        str += (String) ((JSONObject) failList.get(j)).get("msg");
                                        stringBuffer.append(str + "<br>");
                                    }
                                }
                            }
                            //删除成功后通知SAP进行新增操作
                        } catch (Exception e) {
                            e.printStackTrace();
                            //删除失败的话 就不给SAP新增数据了 通知接口责任人 进行接口调试
                            sapInsert = false;
                        }

                    }
                }
                int i = hzSingleVehiclesBomDAO.deleteByProjectId(projectId);
                if (i != -1) {
                    if (hzSingleVehiclesBomDAO.insertList(hzSingleVehiclesBomRecords) > 0 && sapInsert) {
                        List<String> list = hzSingleVehiclesBomDAO.getAllPuidByProjectId(projectId);
                        if (ListUtils.isNotEmpty(list)) {
                            //传输SAP
                            try {
                                JSONObject object = synBomService.addByUids(projectId, list);//将传输的失败结果返回给页面操作者
                                Object fail = object.get("fail");
                                if (fail instanceof List) {
                                    List failList = (List) fail;
                                    if (ListUtils.isNotEmpty(failList)) {
                                        stringBuffer.append("数据同步成功，但是未能全部传输到SAP系统!<br>");
                                        for (int j = 0; j < failList.size(); j++) {
                                            String str = (String) ((JSONObject) failList.get(j)).get("itemId");
                                            str += (String) ((JSONObject) failList.get(j)).get("msg");
                                            stringBuffer.append(str + "<br>");
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                stringBuffer.append("与SAP数据传输失败,无法连接!<br>");
                            }

                        }
                    }
                }
            }
            if (StringUtils.isNotEmpty(stringBuffer.toString())) {
                WriteResultRespDTO respDTO = new WriteResultRespDTO();
                respDTO.setErrMsg(stringBuffer.toString());
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return respDTO;
            } else {
                //保存当前项目下的所有特性值与2Y的关系
                featureBomLineRelationHistoryService.saveHistoryRelation(projectId);
                hzSingleVehiclesServices.postCheck(hzSingleVehicles, projectId);
            }
            return WriteResultRespDTO.getSuccessResult();
        } catch (Exception e) {
            e.printStackTrace();
            return WriteResultRespDTO.getFailResult();
        }
    }


    /**
     * todo 配定时任务 进行定时调用
     */
    @Override
    public void analysisAllSingleVehicles() {
        List<HzProjectLibs> projectLibs = hzProjectLibsDao.selectAllProject();
        if (ListUtils.isNotEmpty(projectLibs)) {
            projectLibs.forEach(hzProjectLibs -> {
                analysisSingleVehicles(hzProjectLibs.getPuid());
            });
        }
    }

    @Override
    public Page<HzSingleVehiclesBomRespDTO> getHzSingleVehiclesBomByPage(HzSingleVehiclesBomByPageQuery query) {
        try {
            query = HzBomSysFactory.bomQueryLevelTrans(query);
            Page<HzSingleVehiclesBomRecord> recordPage;
            if (Integer.valueOf(1).equals(query.getShowBomStructure())) {
                recordPage = hzSingleVehiclesBomDAO.getHzSingleVehiclesBomTreeByPage(query);
            } else {
                recordPage = hzSingleVehiclesBomDAO.getHzSingleVehiclesBomByPage(query);
            }
            if (recordPage == null || recordPage.getResult() == null) {
                return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), 0);
            }
            List<HzSingleVehiclesBomRecord> records = recordPage.getResult();
            List<HzSingleVehiclesBomRespDTO> respDTOS = new ArrayList<>();
            if (ListUtils.isNotEmpty(records)) {
                int num = (recordPage.getPageNumber() - 1) * recordPage.getPageSize();
                for (HzSingleVehiclesBomRecord record : records) {
                    HzSingleVehiclesBomRespDTO respDTO = HzSingleVehiclesFactory.hzSingleVehiclesBomRecordToRespDTO(record);
                    respDTO.setNo(++num);
                    respDTOS.add(respDTO);
                }
            }
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), respDTOS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
