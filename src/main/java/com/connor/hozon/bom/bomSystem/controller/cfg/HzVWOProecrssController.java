package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0Record;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/9 11:13
 * @Modified By:
 */
@Controller
@RequestMapping("/vwoProcess")
public class HzVWOProecrssController {

    @Autowired
    HzCfg0Service hzCfg0Service;

    private Logger logger = LoggerFactory.getLogger(HzVWOProecrssController.class);

    @RequestMapping("/featureGetIntoVWO")
    @ResponseBody
    public boolean featureGetIntoVWO(@RequestBody List<HzCfg0Record> beans) {
        if (beans != null && beans.size() > 0) {
            List<String> puids = new ArrayList<>();
            beans.forEach(bean -> puids.add(bean.getPuid()));
            List<HzCfg0Record> lists = hzCfg0Service.doLoadListByPuids(puids);
            List<HzCfg0Record> localParams = lists.stream().filter(l -> l != null).collect(Collectors.toList());
            if (beans.size() != localParams.size()) {
                logger.error("搜索出的特性值总数与发起VWO流程的特性值的总数不一致，请检查数据核对数据是否被删除");
                return false;
            } else {
                //hzCfg0Service.doSetToProcess(localParams);
                System.out.println("总数一致");
            }
            return true;
        } else {
            return false;
        }
    }
}
