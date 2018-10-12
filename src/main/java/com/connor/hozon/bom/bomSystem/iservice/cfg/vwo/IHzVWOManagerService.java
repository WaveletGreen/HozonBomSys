package com.connor.hozon.bom.bomSystem.iservice.cfg.vwo;

import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.vwo.HzVwoInfo;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/10 13:28
 * @Modified By:
 */
@Configuration
public interface IHzVWOManagerService {
    /**
     * 特性进入vwo流程
     * @param projectUid 项目UID
     * @param beans 一组特性列表
     * @return 操作消息
     */
     JSONObject featureGetIntoVWO( String projectUid, List<HzCfg0Record> beans);

    /**
     * 产生一个最新的Vwo实体类对象
     * @param user 申请Vwo的用户
     * @param projectUid 项目UID
     * @param result 操作消息在生成器里同样有赋值
     * @return 操作消息
     */
    HzVwoInfo generateVwoEntity(User user, String projectUid, JSONObject result);

}
