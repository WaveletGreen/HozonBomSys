package com.connor.hozon.bom.resources.service.bom.impl;

import com.connor.hozon.bom.bomSystem.dao.project.HzProjectLibsDao;
import com.connor.hozon.bom.interaction.dao.HzSingleVehicleBomLineDao;
import com.connor.hozon.bom.interaction.dao.HzSingleVehiclesDao;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesBomRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzSingleVehiclesFactory;
import com.connor.hozon.bom.resources.domain.query.HzMbomTreeQuery;
import com.connor.hozon.bom.resources.domain.query.HzSingleVehiclesBomByPageQuery;
import com.connor.hozon.bom.resources.enumtype.MbomTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzSingleVehiclesBomDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesBomServices;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzSingleVehiclesBomRecord;
import sql.pojo.interaction.HzSingleVehicleBomLineBean;
import sql.pojo.interaction.HzSingleVehicles;
import sql.pojo.project.HzProjectLibs;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:
 */
@Service("HzSingleVehiclesBomServices")
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

    @Override
    public void analysisSingleVehicles(String projectId) {
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
        hzSingleVehiclesServices.refreshSingleVehicle(projectId);
        //单车信息
        List<HzSingleVehicles> hzSingleVehicles = hzSingleVehiclesDao.selectByProjectUid(projectId);

        List<HzSingleVehiclesBomRecord> hzSingleVehiclesBomRecords = new ArrayList<>();
        if(ListUtil.isNotEmpty(hzSingleVehicles)){
            for(HzSingleVehicles vehicles:hzSingleVehicles){
                if(null == vehicles.getId()){//不存在次单车时 continue
                    continue;
                }
                //单车所包含的所有2Y层 带颜色信息
                List<HzSingleVehicleBomLineBean> hzSingleVehicleBomLineBeans = hzSingleVehicleBomLineDao.selectByProjectUidWithSv(projectId,vehicles.getSvlDmbId());
                if(ListUtil.isNotEmpty(hzSingleVehicleBomLineBeans)){//不为空则添加，为空继续
                    for(HzSingleVehicleBomLineBean bean:hzSingleVehicleBomLineBeans){
                        //获取单车全部BOM信息
                        HzMbomTreeQuery hzMbomTreeQuery = new HzMbomTreeQuery();
                        hzMbomTreeQuery.setTableName(MbomTableNameEnum.tableName(0));
                        hzMbomTreeQuery.setProjectId(projectId);
                        if(!StringUtil.isEmpty(bean.getColorUid()) && !"-".equals(bean.getColorUid())){
                            hzMbomTreeQuery.setColorId(bean.getColorUid());
                        }
                        hzMbomTreeQuery.setPuid(bean.getBomLineUid());
                        List<HzMbomLineRecord> records = hzMbomRecordDAO.getHzMbomTree(hzMbomTreeQuery);
                        if(ListUtil.isNotEmpty(records)){
                            for(HzMbomLineRecord record :records){
                                hzSingleVehiclesBomRecords.add(HzSingleVehiclesFactory.mbomRecordToSingleVehiclesBom(record,vehicles.getId()));
                            }
                        }
                    }
                }
            }

        }

        //插入数据
        if(ListUtil.isNotEmpty(hzSingleVehiclesBomRecords)){
            int i = hzSingleVehiclesBomDAO.deleteByProjectId(projectId);
            if(i!=-1){
                hzSingleVehiclesBomDAO.insertList(hzSingleVehiclesBomRecords);
            }
        }
    }

    /**
     * 配定时任务 进行定时调用
     */
    @Override
    public void analysisAllSingleVehicles() {
        List<HzProjectLibs> projectLibs  =  hzProjectLibsDao.selectAllProject();
        if(ListUtil.isNotEmpty(projectLibs)){
            projectLibs.forEach(hzProjectLibs -> {
                analysisSingleVehicles(hzProjectLibs.getPuid());
            });
        }
    }

    @Override
    public Page<HzSingleVehiclesBomRespDTO> getHzSingleVehiclesBomByPage(HzSingleVehiclesBomByPageQuery query) {
        try{
            String level = query.getLevel();
            if (level != null && level != "") {
                if (level.trim().toUpperCase().endsWith("Y")) {
                    int length = Integer.valueOf(level.replace("Y", ""));
                    query.setIsHas(1);
                    query.setLineIndex(String.valueOf(length - 1));
                } else {
                    query.setIsHas(0);
                    int length = Integer.valueOf(level.trim());
                    query.setLineIndex(String.valueOf(length));
                }
            }
            Page<HzSingleVehiclesBomRecord> recordPage = hzSingleVehiclesBomDAO.getHzSingleVehiclesBomByPage(query);
            List<HzSingleVehiclesBomRecord> records = recordPage.getResult();
            List<HzSingleVehiclesBomRespDTO> respDTOS = new ArrayList<>();
            if(ListUtil.isNotEmpty(records)){
                int num = (recordPage.getPageNumber() - 1) * recordPage.getPageSize();
                for(HzSingleVehiclesBomRecord record :records){
                    HzSingleVehiclesBomRespDTO respDTO = HzSingleVehiclesFactory.hzSingleVehiclesBomRecordToRespDTO(record);
                    respDTO.setNo(++num);
                    respDTOS.add(respDTO);
                }
            }
            return new Page<>(recordPage.getPageNumber(), recordPage.getPageSize(), recordPage.getTotalCount(), respDTOS);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
