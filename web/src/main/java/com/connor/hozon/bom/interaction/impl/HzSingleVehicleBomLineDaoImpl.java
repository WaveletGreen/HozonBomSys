package com.connor.hozon.bom.interaction.impl;

import cn.net.connor.hozon.dao.dao.interaction.HzConfigBomColorDao;
import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomColorBean;
import cn.net.connor.hozon.dao.pojo.interaction.HzSingleVehicleBomLineBean;
import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.interaction.dao.HzSingleVehicleBomLineDao;
import cn.net.connor.hozon.dao.query.interaction.HzSingleVehicleBLQuery;
import com.connor.hozon.bom.sys.exception.HzBomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/25 14:53
 * @Modified By:
 */
@Configuration
public class HzSingleVehicleBomLineDaoImpl extends BasicDaoImpl<HzSingleVehicleBomLineBean> implements HzSingleVehicleBomLineDao {

    private final static HzSingleVehicleBomLineBean BEAN = new HzSingleVehicleBomLineBean();

    private HzConfigBomColorDao hzConfigBomColorDao;

    @Autowired
    public void setHzConfigBomColorDao(HzConfigBomColorDao hzConfigBomColorDao) {
        this.hzConfigBomColorDao = hzConfigBomColorDao;
    }

    public HzSingleVehicleBomLineDaoImpl() {
        clz = HzSingleVehicleBomLineDao.class;
        clzName = clz.getCanonicalName();
    }

    private List<HzSingleVehicleBomLineBean> selectListByInquirer(HzSingleVehicleBLQuery inquirer) {
        return baseSQLUtil.executeQueryByPass(BEAN, inquirer, clzName + ".selectListByInquirer");
    }


    /**
     * 查询油漆车身
     * @param inquirer
     * @return
     */
    private List<HzSingleVehicleBomLineBean> selectPaintList(HzSingleVehicleBLQuery inquirer) {
        return baseSQLUtil.executeQueryByPass(BEAN, inquirer, clzName + ".selectPaintList");
    }

    /**
     * 查询非油漆车身
     * @param inquirer
     * @return
     */
    private List<HzSingleVehicleBomLineBean> selectOthersList(HzSingleVehicleBLQuery inquirer) {

        return baseSQLUtil.executeQueryByPass(BEAN, inquirer, clzName + ".selectOthersList");
    }


    /**
     * 查询全配置一级清单的实心打点图
     * @param inquirer
     * @return
     */
    private List<HzSingleVehicleBomLineBean> selectSolidPointGraphFromFullConfig(HzSingleVehicleBLQuery inquirer){
        return baseSQLUtil.executeQueryByPass(BEAN, inquirer, clzName + ".selectSolidPointGraphFromFullConfig");
    }





    /**
     * 查询
     *
     * @param projectUid 项目UID
     * @param dmbId 单车详细信息主配置
     * @return
     */
    @Override
    public List<HzSingleVehicleBomLineBean> selectByProjectUidWithSv(String projectUid, Long dmbId) {
        HzSingleVehicleBLQuery inquirer = new HzSingleVehicleBLQuery();
        inquirer.setDmbId(dmbId);
        inquirer.setProjectUid(projectUid);
        inquirer.setIsNotNull(true);
        return selectListByInquirer(inquirer);
    }

    /**
     * 全配置清单哪里的实心打点图 包括其颜色件以及颜色件id 颜色件代码
     * @param projectId 项目id
     * @param dmbId 单车信息
     * @return
     */
    @Override
    public List<HzSingleVehicleBomLineBean> selectFullConfigColorSet(String projectId, Long dmbId) {
        //查询油漆车身颜色件信息
        HzSingleVehicleBLQuery inquirer = new HzSingleVehicleBLQuery();
        inquirer.setDmbId(dmbId);
        inquirer.setProjectUid(projectId);
        inquirer.setIsNotNull(true);

        List<HzSingleVehicleBomLineBean> paintBeans = selectPaintList(inquirer);
        if(ListUtils.isNotEmpty(paintBeans)){
            List<HzConfigBomColorBean> list = hzConfigBomColorDao.selectPaintBomLinePuidFormConfig(projectId);//查询油漆车身BOM行主键
            if(ListUtils.isNotEmpty(list)){
                String  bomPuid = list.get(0).getBomLineUid();
                paintBeans.forEach(paintBean->{
                    paintBean.setBomLineUid(bomPuid);
                });
            }else {
                throw new HzBomException("未找到油漆车身的BOM信息！");
            }
        }

        //查询其他2Y层颜色件信息
        List<HzSingleVehicleBomLineBean> otherBeans = selectOthersList(inquirer);
        if(ListUtils.isNotEmpty(paintBeans)){
            otherBeans.addAll(paintBeans);
        }
        //查询全部的实心打点图
        List<HzSingleVehicleBomLineBean> solidPoints = selectSolidPointGraphFromFullConfig(inquirer);
        //将以上的查询信息进行整合
        if(ListUtils.isNotEmpty(solidPoints)){
            List<HzSingleVehicleBomLineBean> resultList = new ArrayList<>();
            if(ListUtils.isEmpty(otherBeans)){
                return solidPoints;
            }
            solidPoints.forEach(solidPoint ->{
                HzSingleVehicleBomLineBean bomLineBean = new HzSingleVehicleBomLineBean();
                bomLineBean.setBomLineUid(solidPoint.getBomLineUid());
                otherBeans.forEach(otherBean ->{
                    if(solidPoint.getBomLineUid().equals(otherBean.getBomLineUid())){
                        bomLineBean.setColorCode(otherBean.getColorCode());
                        bomLineBean.setColorUid(otherBean.getColorUid());
                    }
                });
                resultList.add(bomLineBean);
            });
            return resultList;
        }
        return null;
    }
}
