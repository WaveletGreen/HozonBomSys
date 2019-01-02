package com.connor.hozon.bom.resources.schedule;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzBOMScheduleTaskDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzBOMScheduleTaskDAOImpl;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesBomServices;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sql.pojo.bom.HzBOMScheduleTask;
import sql.pojo.interaction.HzSingleVehicles;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    private HzBOMScheduleTaskDAO hzBOMScheduleTaskDAO;

    private HzMbomService hzMbomService;

    private HzSingleVehiclesServices hzSingleVehiclesServices;

    private HzSingleVehiclesBomServices hzSingleVehiclesBomServices;

    @Autowired
    public void setHzBOMScheduleTaskDAO(HzBOMScheduleTaskDAO hzBOMScheduleTaskDAO) {
        this.hzBOMScheduleTaskDAO = hzBOMScheduleTaskDAO;
    }

    @Autowired
    public void setHzMbomService(HzMbomService hzMbomService) {
        this.hzMbomService = hzMbomService;
    }

    @Autowired
    public void setHzSingleVehicles(HzSingleVehiclesServices hzSingleVehiclesServices) {
        this.hzSingleVehiclesServices = hzSingleVehiclesServices;
    }

    @Autowired
    public void setHzSingleVehiclesBomServices(HzSingleVehiclesBomServices hzSingleVehiclesBomServices) {
        this.hzSingleVehiclesBomServices = hzSingleVehiclesBomServices;
    }

    @Scheduled(cron = "${bom.task_time}")
    public void cronJob(){
        //查询任务
        List<HzBOMScheduleTask> tasks = hzBOMScheduleTaskDAO.getNoSynchronizedBOMScheduleTask();
        if(ListUtil.isNotEmpty(tasks)){
            for(HzBOMScheduleTask task : tasks){
                Long orderId = task.getOrderId();
                List<HzBOMScheduleTask> bomScheduleTasks = hzBOMScheduleTaskDAO.getBOMScheduleTask(orderId);
                //配置物料特性表发生变更 同步MBOM+单车BOM清单
                //PBOM发生变更 同步MBOM
                //MBOM发生变更 同步单车清单数据
                if(ListUtil.isNotEmpty(bomScheduleTasks)){
                    HzBOMScheduleTask scheduleTask = moreTasksTransOneTask(bomScheduleTasks);
                    if(Integer.valueOf(1).equals(scheduleTask.getConfigFeatureChanged())){
                        WriteResultRespDTO mbomRespDTO = hzMbomService.refreshHzMbom(scheduleTask.getProjectId());
                        WriteResultRespDTO singleRespDTO = hzSingleVehiclesServices.refreshSingleVehicle(scheduleTask.getProjectId());
                    }
                    if(Integer.valueOf(1).equals(scheduleTask.getPbomChanged())){
                        WriteResultRespDTO mbomRespDTO = hzMbomService.refreshHzMbom(scheduleTask.getProjectId());
                    }
                    if(Integer.valueOf(1).equals(scheduleTask.getMbomChanged())){
                        WriteResultRespDTO singleRespDTO = hzSingleVehiclesBomServices.analysisSingleVehicles(scheduleTask.getProjectId());
                    }

                }
            }
        }
        System.out.println(DateUtil.formatTimestampDate(new Date()) +" >>BOM在解算....");
    }


    private HzBOMScheduleTask moreTasksTransOneTask(List<HzBOMScheduleTask> list){
        HzBOMScheduleTask task = new HzBOMScheduleTask();
        if(ListUtil.isNotEmpty(list)){
            for(HzBOMScheduleTask bomScheduleTask : list){
                if(Integer.valueOf(1).equals(bomScheduleTask.getConfigFeatureChanged())){
                    task.setConfigFeatureChanged(1);
                }else if(Integer.valueOf(1).equals(bomScheduleTask.getPbomChanged())){
                    task.setPbomChanged(1);
                }else if(Integer.valueOf(1).equals(bomScheduleTask.getMbomChanged())){
                    task.setMbomChanged(1);
                }
            }
        }
        return task;
    }
}
