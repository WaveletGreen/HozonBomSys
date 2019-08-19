/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.schedule;

import cn.net.connor.hozon.dao.dao.depository.project.HzProjectLibsDao;
import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.exception.HzBomException;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzBOMScheduleResultDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzBOMScheduleTaskDAO;
import com.connor.hozon.service.bom.bom.HzMbomService;
import com.connor.hozon.service.bom.bom.HzSingleVehiclesBomServices;
import com.connor.hozon.service.bom.bom.HzSingleVehiclesServices;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBOMScheduleResult;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBOMScheduleTask;
import cn.net.connor.hozon.dao.pojo.depository.project.HzProjectLibs;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2019/1/2
 * @Description: BOM解算 定时数据同步 单线程
 *                后期如果需求变化（如多个项目数据解算 等）
 *                可改为多线程执行 需要重新配置
 */
@Component
public class BOMTaskJobs {

    private static final Logger logger = LoggerFactory.getLogger(BOMTaskJobs.class);

    private HzBOMScheduleTaskDAO hzBOMScheduleTaskDAO;

    private HzMbomService hzMbomService;

    private HzSingleVehiclesServices hzSingleVehiclesServices;

    private HzSingleVehiclesBomServices hzSingleVehiclesBomServices;

    private HzProjectLibsDao hzProjectLibsDao;

    private HzBOMScheduleResultDAO hzBOMScheduleResultDAO;

    private TransactionTemplate configTransactionTemplate;

    @Autowired
    public void setHzBOMScheduleTaskDAO(HzBOMScheduleTaskDAO hzBOMScheduleTaskDAO) {
        this.hzBOMScheduleTaskDAO = hzBOMScheduleTaskDAO;
    }

    @Autowired
    public void setHzMbomService(HzMbomService hzMbomService) {
        this.hzMbomService = hzMbomService;
    }

    @Autowired
    public void setHzSingleVehiclesBomServices(HzSingleVehiclesBomServices hzSingleVehiclesBomServices) {
        this.hzSingleVehiclesBomServices = hzSingleVehiclesBomServices;
    }

    @Autowired
    public void setHzProjectLibsDao(HzProjectLibsDao hzProjectLibsDao) {
        this.hzProjectLibsDao = hzProjectLibsDao;
    }

    @Autowired
    public void setHzSingleVehiclesServices(HzSingleVehiclesServices hzSingleVehiclesServices) {
        this.hzSingleVehiclesServices = hzSingleVehiclesServices;
    }

    @Autowired
    public void setHzBOMScheduleResultDAO(HzBOMScheduleResultDAO hzBOMScheduleResultDAO) {
        this.hzBOMScheduleResultDAO = hzBOMScheduleResultDAO;
    }

    @Autowired
    public void setConfigTransactionTemplate(TransactionTemplate configTransactionTemplate) {
        this.configTransactionTemplate = configTransactionTemplate;
    }

    @Scheduled(cron = "${bom.task_time}")
    public void cronJob(){
        //查询任务
        List<HzBOMScheduleTask> tasks = hzBOMScheduleTaskDAO.getNoSynchronizedBOMScheduleTask();

        if(ListUtils.isNotEmpty(tasks)){
            for(HzBOMScheduleTask task : tasks){
                Long orderId = task.getOrderId();
                try {
                    List<HzBOMScheduleTask> bomScheduleTasks = hzBOMScheduleTaskDAO.getBOMScheduleTask(orderId);
                    //配置物料特性表发生变更 同步MBOM+单车BOM清单
                    //PBOM发生变更 同步MBOM
                    //MBOM发生变更 同步单车清单数据
                    HzBOMScheduleResult  result = new HzBOMScheduleResult();// 记录解算结果
                    if(ListUtils.isNotEmpty(bomScheduleTasks)){
                        StringBuffer stringBuffer = new StringBuffer();
                        HzBOMScheduleTask scheduleTask = moreTasksTransOneTask(bomScheduleTasks);
                        HzProjectLibs projectLibs = hzProjectLibsDao.selectByPrimaryKey(scheduleTask.getProjectId());
                        if(projectLibs == null){
                            logger.error("项目不存在！"+scheduleTask.getProjectId());
                            continue;
                        }
                        String projectCode = projectLibs.getpProjectCode();
                        if(Integer.valueOf(1).equals(scheduleTask.getConfigFeatureChanged())){
                            WriteResultRespDTO mbomRespDTO = hzMbomService.refreshHzMbom(scheduleTask.getProjectId());
                            if(!WriteResultRespDTO.isSuccess(mbomRespDTO)){
                                stringBuffer.append(projectCode+"项目-MBOM解算结果:"+mbomRespDTO.getErrMsg()+"<br>");
                            }
                            WriteResultRespDTO singleRespDTO = hzSingleVehiclesServices.refreshSingleVehicle(scheduleTask.getProjectId());
                            if(!WriteResultRespDTO.isSuccess(singleRespDTO)){
                                stringBuffer.append(projectCode+"项目-单车列表解算结果:"+singleRespDTO.getErrMsg()+"<br>");
                            }
                        }
                        if(Integer.valueOf(1).equals(scheduleTask.getPbomChanged())){
                            WriteResultRespDTO mbomRespDTO = hzMbomService.refreshHzMbom(scheduleTask.getProjectId());
                            if(!WriteResultRespDTO.isSuccess(mbomRespDTO)){
                                stringBuffer.append(projectCode+"项目-MBOM解算结果:"+mbomRespDTO.getErrMsg()+"<br>");
                            }
                        }
                        if(Integer.valueOf(1).equals(scheduleTask.getMbomChanged())){
                            WriteResultRespDTO singleRespDTO = hzSingleVehiclesBomServices.analysisSingleVehicles(scheduleTask.getProjectId());
                            if(!WriteResultRespDTO.isSuccess(singleRespDTO)){
                                stringBuffer.append(projectCode+"项目-单车BOM解算结果:"+singleRespDTO.getErrMsg()+"<br>");
                            }
                        }
                        result.setOrderId(orderId);
                        configTransactionTemplate.execute(new TransactionCallback<Void>() {
                            @Override
                            public Void doInTransaction(TransactionStatus status) {
                                try {
                                    if(StringUtils.isEmpty(stringBuffer.toString())){
                                        result.setResult("success");
                                        HzBOMScheduleTask hzBOMScheduleTask = new HzBOMScheduleTask();
                                        hzBOMScheduleTask.setOrderId(orderId);
                                        hzBOMScheduleTask.setHasSynchronized(1);
                                        hzBOMScheduleTaskDAO.updateByOrderId(hzBOMScheduleTask);
                                    }else {
                                        result.setResult(stringBuffer.toString());
                                    }
                                    hzBOMScheduleResultDAO.insert(result);
                                }catch (Exception e){
                                    e.printStackTrace();
                                    throw new HzBomException("BOM解算结果记录失败!",e);
                                }
                                return null;
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    logger.error("BOM定时解算失败！"+e);
                }
            }

        }
    }

    /**
     * BOM定时器同步 由多条记录转换为1条记录
     * @param list 同一个变更表单的变更记录
     * @return
     */
    private HzBOMScheduleTask moreTasksTransOneTask(List<HzBOMScheduleTask> list){
        HzBOMScheduleTask task = new HzBOMScheduleTask();
        if(ListUtils.isNotEmpty(list)){
            String projectId = list.get(0).getProjectId();
            for(HzBOMScheduleTask bomScheduleTask : list){
                if(Integer.valueOf(1).equals(bomScheduleTask.getConfigFeatureChanged())){
                    task.setConfigFeatureChanged(1);
                }else if(Integer.valueOf(1).equals(bomScheduleTask.getPbomChanged())){
                    task.setPbomChanged(1);
                }else if(Integer.valueOf(1).equals(bomScheduleTask.getMbomChanged())){
                    task.setMbomChanged(1);
                }
            }
            task.setProjectId(projectId);
        }
        return task;
    }
}
