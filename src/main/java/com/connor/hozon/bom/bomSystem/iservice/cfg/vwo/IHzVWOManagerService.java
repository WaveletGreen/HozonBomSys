package com.connor.hozon.bom.bomSystem.iservice.cfg.vwo;

import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.resources.controller.change.vwo.VWOUserGroupDTO;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import sql.pojo.cfg.cfg0.HzCfg0Record;
import sql.pojo.cfg.vwo.*;

import java.util.List;
import java.util.Map;

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
     *
     * @param projectUid 项目UID
     * @param beans      一组特性列表
     * @return 操作消息
     */
    JSONObject featureGetIntoVWO(String projectUid, List<HzCfg0Record> beans);

    /**
     * 产生一个最新的Vwo实体类对象
     *
     * @param user       申请Vwo的用户
     * @param projectUid 项目UID
     * @param result     操作消息在生成器里同样有赋值
     * @return 操作消息
     */
    HzVwoInfo generateVwoEntity(User user, String projectUid, JSONObject result);

    /**
     * 获取VWO详情表单
     * @param id VWO主键
     * @param vwoType VWO类型
     * @param model spring model
     * @return
     */
    boolean getVwoFromList(Long id, Integer vwoType, Model model);

    /**
     * 保存影响人员
     * @param influenceUser
     * @return
     */
    JSONObject saveInfluenceUser(HzVwoInfluenceUser influenceUser);

    /**
     * 获取人员和部门信息
     * @return
     */
    List<VWOUserGroupDTO> getUserAndGroupOrg();

    /**
     * 保存影响部门
     * @param dept
     * @return
     */
    JSONObject saveInfluenceDept(HzVwoInfluenceDept dept);

    /**
     * 保存通知变更人员
     * @param change
     * @return
     */
    JSONObject saveInformChanger(HzVwoInformChanges change);

    /**
     * 获取通知变更人员
     * @param vwo
     * @param queryBase
     * @return
     */
    Map<String,Object> getInformChangers(Long vwo, QueryBase queryBase);

    /**
     * 分页查询VWO表单数量
     * @param projectUid
     * @param queryBase
     * @return
     */
    Map<String,Object> queryByBase(String projectUid, QueryBase queryBase);

    /**
     * 获取用户详情
     * @param dto
     * @return
     */
    Map<String,Object> getUserDetail(VWOUserGroupDTO dto);

    /**
     * 忘了
     * @param changes
     * @return
     */
    JSONObject deleteVwoInfoChange(List<HzVwoInformChanges> changes);

    /**
     * 保存基本数据
     * @param info
     * @return
     */
    JSONObject saveBasic(HzVwoInfo info);

    /**
     * 根据VWO ID获取一组分发与实施对象，可能没有
     * @param vwo
     * @return
     */
    Map<String, Object> getExecuteInfo(Long vwo);

    /**
     * 保存分发与实施数据
     * @param execute
     */
    JSONObject saveExecuteInfo(HzVwoExecute execute);

    JSONObject deleteExecuteInfo(List<HzVwoExecute> executes);
}
