/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.interaction;

import com.connor.hozon.bom.bomSystem.helper.PropertiesHelper;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.iservice.interaction.IHzCraftService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzPbomReqDTO;
import com.connor.hozon.bom.resources.domain.query.HzPbomTreeQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.exception.HzBomException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.epl.HzEPLRecord;

import java.io.IOException;
import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 工艺合件
 * 作为子：筛选所有需要进行合成的源，同时找到所有源的父层的查找编号比源大的兄弟层，将兄弟层的查找编号进行重新排序
 * 作为父：查找已合成源为基准，递归查找源下的所有子层，作为合成结果，合成结果挂载到合成目标下，对合成结果的每一份进行复制，
 * 将重新构建树形结构
 * @Date: Created in 2018/9/28 14:45
 * @Modified By:
 */
@Configuration
public class HzCraftService implements IHzCraftService {

    HzPbomRecordDAO hzPbomRecordDAO;

    private HzEPLDAO hzEPLDAO;

    private TransactionTemplate configTransactionTemplate;

    /**
     * 预设随机数产生的精度后移位
     */
    public static double RANDOM_SETTING = 0.1;
    /**
     * 从配置文件中来的精度
     */
    private static double THE_SETTING_FROM_PROPERTIES = 0.1;
    /**
     * 目标挂载对象零件代码
     */
    private List<String> targetPartCodes = new ArrayList<>();
    /**
     * 项目UID
     */
    private String projectUid;
    /**
     * 需要删除的子层
     */
    private List<HzPbomLineRecord> theChildrenNeedToDelete = new ArrayList<>();
    /**
     * 该集合下的所有子都需要进行查找编号的重新编排，同一个父，出现了多个元素进行合成，需要重新编排
     */
    private Map<String, Map<String, HzPbomLineRecord>> toChildrenNeedToUpdateItsLineIndex = new LinkedHashMap<>();
    /**
     * 重设查找编号的子层
     */
    private Map<String, Map<String, HzPbomLineRecord>> orderIsResetChildren = new LinkedHashMap<>();
    /**
     * 同父层下的所有子层
     */
    private Map<String, Map<String, HzPbomLineRecord>> myWavelet = new LinkedHashMap<>();
    /**
     * 祖父层的所有的子（父层兄弟层）需要进行重新编排的PBOM
     */
    private Map<String, Map<String, HzPbomLineRecord>> myLovelyWavelet = new LinkedHashMap<>();
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(HzCraftService.class);
    /**
     * 查询参数
     */
    private Map<String, Object> param = new HashMap<>();
    /**
     * 原始排序号，实则需要排序后的第一位查找编号，用于重新对受影响的子层进行查找编号进行重新编排
     */
    private Map<String, List<Integer>> orgOrder = new LinkedHashMap<>();
    /**
     * 对于相同id的零件号，其所在的BOM位置是不一样的，因此需要对不同位置的BOM进行新UID和原始UID做对应，为子进行重新查找到原来的父做索引
     */
    private Map<String, String> theKeyToAllSameParent = new LinkedHashMap<>();
    /**
     * 最大的查找编号
     */
    private Map<String, LocalCraft> theMaxInorder = new LinkedHashMap<>();
    /**
     * 调试阶段
     */
    public static boolean CRAFT_DEBUG = false;
    /**
     * 插入测试，true则不进行插入
     */
    private boolean craftIsInfluenceToDB = false;
    /**
     * 一份父层的属性结构图
     */
    private Map<String, Map<String, List<HzPbomLineRecord>>> allParentTree = new LinkedHashMap<>();
    /**
     * 合成目标位置的BOM行记录数据
     */
    private Map<String, LocalCraftTarget> targetTreeMap = new LinkedHashMap<>();
    /**
     * 产生随机数
     */
    private final static Random RAND = new Random();

    /**
     * 一级精度
     */
    private final static int ACCURACY_LVL1 = 100;
    /**
     * 二级精度
     */
    private final static int ACCURACY_LVL2 = 1500;
    /**
     * 三级精度
     */
    private final static int ACCURACY_LVL3 = 10000;

    @Autowired
    public void setHzPbomRecordDAO(HzPbomRecordDAO hzPbomRecordDAO) {
        this.hzPbomRecordDAO = hzPbomRecordDAO;
    }
    @Autowired
    public void setHzEPLDAO(HzEPLDAO hzEPLDAO) {
        this.hzEPLDAO = hzEPLDAO;
    }
    @Autowired
    public void setConfigTransactionTemplate(TransactionTemplate configTransactionTemplate) {
        this.configTransactionTemplate = configTransactionTemplate;
    }

    /**
     * 自动生成工艺合件，执行过程有可能产生异常
     * @param projectUid    项目UID
     * @param parentUids    合成源父层
     * @param childrenUids  合成源子层
     * @param targetUids    挂载目标UID
     * @param collectedData 新件数据
     * @return 成功进行合成则返回true，反之返回false
     */
    @Override
    public boolean autoCraft(String projectUid, List<String> parentUids, List<String> childrenUids, List<String> targetUids, Map<String, String> collectedData) {
        //创建新的pBom对象，即挂载的新件
        HzPbomLineRecord pbom = craftNewPart(collectedData);
        //清空上一次产生的缓存
        clearCoach();
        this.targetPartCodes.clear();
        this.projectUid = projectUid;
        param.put("projectId", projectUid);

        //事务保护
        configTransactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                try {
                    //从配置文件中获取设置信息
                    preSetFromProperties();
                    //子层数据整理
                    craftChildren(childrenUids, pbom, myWavelet);
                    //父层数据整理
                    craftChildren(parentUids, pbom, myLovelyWavelet);
                    //获取所有的父层
                    craftParentTree(parentUids);
                    //挂载目标点的的查找编号和排序号的整理
                    craftTarget(targetUids);
                    //重新整理受影响的子层的查找编号
                    craftResetOrder();
                    //构建新的树形结构
                    craftBuildNewTree(theMaxInorder, pbom);
                    //设置合成源的父层是否isHas，是否为Y
                    craftSrcParent();
                    //插入新的合件树
                    craftInsert(targetTreeMap);
                    //整理受影响父的节点isHas
                    craftTargetsIsHas(targetUids);
                    //删除合成源点
                    craftDelete();
                    //对受影响的子层进行查找编号更新
                    craftUpdate();
                    //新生成的零件号添加到EPL 2018/12/26 by haozt
                    HzEPLRecord hzEPLRecord = new HzEPLRecord();
                    hzEPLRecord.setPartId(pbom.getLineId());
                    hzEPLRecord.setPartName(pbom.getpBomLinePartName());
                    hzEPLRecord.setPartResource(pbom.getpBomLinePartResource());
                    hzEPLDAO.insert(hzEPLRecord);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new HzBomException("工艺合件合成失败！"+ e);
                } finally {
                    //清除缓存
                    clearCoach();
                }
                return null;
            }
        });
        return true;
    }

    /**
     * 获取当前挂载对象的零件号
     *
     * @return
     */
    @Override
    public List<String> getTargetPartCodes() {
        return this.targetPartCodes;
    }

    /**
     * 创建一个PBOM对象，作为新件
     *
     * @param data
     * @return
     */
    public HzPbomLineRecord craftNewPart(Map<String, String> data) {
        HzPbomLineRecord record = new HzPbomLineRecord();
        User user = UserInfo.getUser();
        Date now = new Date();
        /**从前端搜索过来的数据***/
        record.setLineId(data.get("lineId"));
        record.setpBomOfWhichDept(data.get("pBomOfWhichDept"));
        record.setpBomLinePartName(data.get("pBomLinePartName"));
        record.setpBomLinePartEnName(data.get("pBomLinePartEnName"));
        record.setpBomLinePartClass(data.get("pBomLinePartClass"));
        record.setpBomLinePartResource(data.get("pBomLinePartResource"));
        record.setResource(data.get("resource"));
        record.setType(checkString(data.get("type")) ? Integer.parseInt(data.get("type")) : null);
        record.setWorkShop1(data.get("workShop1"));
        record.setWorkShop2(data.get("workShop2"));
        record.setProductLine(data.get("productLine"));
        record.setMouldType(data.get("mouldType"));
        record.setOuterPart(data.get("outerPart"));
//        record.setColorPart(checkString(data.get("colorPart")) ? Integer.valueOf(data.get("colorPart")) : null);
        record.setStation(data.get("station"));
        /**自动数据**/
        record.setCreateName(user.getUsername());
        record.setUpdateName(user.getUsername());
        record.setCreateTime(now);
        record.setUpdateTime(now);
        /**规则数据*/
        ///不为2Y
        record.setIs2Y(0);
        //有子层
        record.setIsHas(1);
        //不是零件级
        record.setIsPart(0);
        //不是部门级
        record.setIsDept(0);
        //可用状态 草稿状态
        record.setStatus(2);
        //不是颜色件
        record.setColorPart(0);
        //设置为工艺合件
        record.setIsNewPart(2);
        ///lineIndex根据挂载位置进行规则进行设置
        //sortNum根据挂载位置进行规则进行设置
        //工艺合件不来源于EBOM，设置来源UID为自身UID
        // 自身主键和来源主键根据挂载位置决定
        // 数模层的数据也来源于挂载位置
        return record;
    }

    /**
     * 从配置文件中获取配置设置
     */
    private void preSetFromProperties() throws IOException {
        PropertiesHelper helper = new PropertiesHelper();
        Properties properties = helper.load();
        CRAFT_DEBUG = Boolean.valueOf(properties.getProperty("CRAFT_DEBUG"));
        craftIsInfluenceToDB = Boolean.valueOf(properties.getProperty("CRAFT_IS_INFLUENCE_TO_DB"));
        RANDOM_SETTING = THE_SETTING_FROM_PROPERTIES = Double.parseDouble(properties.getProperty("CRAFT_RANDOM_SETTING"));
    }

    /**
     * 将合成源子层挂载到新件下
     *
     * @param childrenUids
     * @param part
     * @return
     */
    public void craftChildren(List<String> childrenUids, HzPbomLineRecord part, Map<String, Map<String, HzPbomLineRecord>> myWavelet) throws Exception {
        for (int i = 0; i < childrenUids.size(); i++) {
            HzPbomLineRecord line = hzPbomRecordDAO.getHzPbomByEbomPuid(childrenUids.get(i), projectUid);
            if (line == null) {
                throw new Exception("没有找到合成的零件");
            }
            if (myWavelet.containsKey(line.getParentUid())) {
                myWavelet.get(line.getParentUid()).put(line.getLineIndex(), line);
            } else {
                Map<String, HzPbomLineRecord> wavelet = new LinkedHashMap<>();
                wavelet.put(line.getLineIndex(), line);
                myWavelet.put(line.getParentUid(), wavelet);
            }
        }
        Iterator<Map.Entry<String, Map<String, HzPbomLineRecord>>> iterator = myWavelet.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            Map<String, HzPbomLineRecord> values = (Map<String, HzPbomLineRecord>) entry.getValue();
            //父层
            HzPbomLineRecord parent = hzPbomRecordDAO.getHzPbomByEbomPuid((String) entry.getKey(), projectUid);
            if (parent != null && checkString(parent.getLineId())) {
                //获取名下子一层
                List<HzPbomLineRecord> ohMyChildren = hzPbomRecordDAO.getFirstLevelBomByParentId(parent.geteBomPuid(), projectUid);
                //父层没有子，不进行剪切操作
                if (ohMyChildren == null || ohMyChildren.isEmpty()) {
                    logger.warn("Oops," + parent.getLineId() + "(PUID:" + parent.getPuid() + ")下的子删除或剪切了");
                    continue;
                }
                //找到所有的父行
                param.put("lineId", parent.getLineId());
                List<HzPbomLineRecord> allCodeIsWithToParentCode = hzPbomRecordDAO.findPbom(param);

                if (allCodeIsWithToParentCode != null && !allCodeIsWithToParentCode.isEmpty()) {
                    for (int i1 = 0; i1 < allCodeIsWithToParentCode.size(); i1++) {

                        /**父行下的所有子一层，子一层的lineId和查找编号必须匹配当前子层的lineId和查找编号，
                         * 如果是这样，则认为是操作了当前行，则需要将当前父下该子行移除，然后调整当前父下的所有子行的查找编号*/
                        List<HzPbomLineRecord> isWithsChildren = hzPbomRecordDAO.getFirstLevelBomByParentId(allCodeIsWithToParentCode.get(i1).geteBomPuid(), projectUid);
                        if (isWithsChildren == null || isWithsChildren.isEmpty()) {
                            continue;
                        }
                        if (isWithsChildren.size() != ohMyChildren.size()) {
                            logger.warn("PBOM BOM行的PUID为" + allCodeIsWithToParentCode.get(i1).getPuid() +
                                    "的子层数量与合成源EBOM的父层的子数量不一致，认定不是同一个PBOM，或者PBOM是错的，需要更正PBOM");
                            continue;
                        }
                        boolean isNeedToUpdate = false;
                        //找到相同父下的所有非合成源的子
                        for (HzPbomLineRecord isWithsChild : isWithsChildren) {
                            //余认为零件号与查找编号相等则为同一个BOM
                            if (values.containsKey(isWithsChild.getLineIndex()) && values.get(isWithsChild.getLineIndex()).getLineId().equals(isWithsChild.getLineId())) {
                                logger.warn("新郎新娘可以接吻了");
                                theChildrenNeedToDelete.add(isWithsChild);
                                isNeedToUpdate = true;
                            } else {
                                String ld = isWithsChild.getLineIndex();
                                boolean isIt = false;
                                String localIndex = "";
                                for (Map.Entry<String, HzPbomLineRecord> et : values.entrySet()) {
                                    localIndex = et.getKey();
                                    if (compare(ld, localIndex) > 0) {
                                        isIt = true;
                                    }
                                }
                                if (isIt) {
                                    String ebomUid = allCodeIsWithToParentCode.get(i1).geteBomPuid();
                                    String myUid = allCodeIsWithToParentCode.get(i1).getPuid();
                                    if (this.myWavelet.containsKey(ebomUid) && this.myWavelet.get(ebomUid).containsKey(isWithsChild.getLineIndex())) {
                                        if (toChildrenNeedToUpdateItsLineIndex.containsKey(myUid)) {
                                            toChildrenNeedToUpdateItsLineIndex.get(allCodeIsWithToParentCode.get(i1).getPuid()).remove(isWithsChild.getLineIndex());
                                        }
//                                        this.myWavelet.get(allCodeIsWithToParentCode.get(i1).geteBomPuid()).remove(isWithsChild.getLineIndex());
                                        continue;
                                    }
                                    if (this.myLovelyWavelet.containsKey(ebomUid) && this.myLovelyWavelet.get(ebomUid).containsKey(isWithsChild.getLineIndex())) {
//                                        this.myLovelyWavelet.get(allCodeIsWithToParentCode.get(i1).geteBomPuid()).remove(isWithsChild.getLineIndex());
                                        if (toChildrenNeedToUpdateItsLineIndex.containsKey(myUid)) {
                                            toChildrenNeedToUpdateItsLineIndex.get(ebomUid).remove(isWithsChild.getLineIndex());
                                        }
                                        continue;
                                    }

                                    if (toChildrenNeedToUpdateItsLineIndex.containsKey(myUid)) {
//                                        if (toChildrenNeedToUpdateItsLineIndex.get(allCodeIsWithToParentCode.get(i1).getPuid()).containsKey(isWithsChild.getLineIndex())) {
//                                            toChildrenNeedToUpdateItsLineIndex.get(allCodeIsWithToParentCode.get(i1).getPuid()).remove(isWithsChild.getLineIndex());
//                                        } else {
                                        toChildrenNeedToUpdateItsLineIndex.get(myUid).put(isWithsChild.getLineIndex(), isWithsChild);
//                                        }
                                    } else {
                                        Map<String, HzPbomLineRecord> tempToUpdate = new LinkedHashMap<>();
                                        tempToUpdate.put(isWithsChild.getLineIndex(), isWithsChild);
                                        toChildrenNeedToUpdateItsLineIndex.put(myUid, tempToUpdate);
                                        if (!theKeyToAllSameParent.containsKey(myUid)) {
                                            theKeyToAllSameParent.put(myUid, parent.geteBomPuid());
                                        }
                                    }
                                }
                            }
                        }
//                        if (isNeedToUpdate) {
//
//                        } else {
//                            logger.warn("虽然PBOM中PUID为" + allCodeIsWithToParentCode.get(i1).getPuid() +
//                                    "的所有子层与合成源父层的子数量一致，但是没有找到合成源的父层" + parent.getLineId() + "(" +
//                                    parent.getPuid() + ")与合成源的查找编号一致的BOM行，认定不是同一个BOM，或者BOM错误，又或是BOM已被更改"
//                            );
//                        }
                    }
                } else {
                    logger.warn("卧槽，没找找到我爸爸");
                }
            }
        }
//            for (int i = 0; i < childrenUids.size(); i++) {
//                //根据ebom uid找到当前的bom行
//                HzPbomLineRecord line = hzPbomRecordDAO.getHzPbomByEbomPuid(childrenUids.get(i), projectUid);
//
//
//                if (line != null && checkString(line.getParentUid())) {
//                    //找pbom bom行的父行
//
//                }
//            }
        logger.info("完成查找需要更新子层的父层");
    }

    /**
     * 构建父层树
     *
     * @param parentUids
     */
    private void craftParentTree(List<String> parentUids) {
        HzPbomTreeQuery query = new HzPbomTreeQuery();
        query.setProjectId(projectUid);
        for (int i = 0; i < parentUids.size(); i++) {
            //递归查询所有的子
            query.setPuid(parentUids.get(i));
            List<HzPbomLineRecord> parensTree = hzPbomRecordDAO.getHzPbomTree(query);
            //保存到缓存中
            Map<String, List<HzPbomLineRecord>> map = new LinkedHashMap<>();
            map.put(parentUids.get(i), parensTree);
            allParentTree.put(parentUids.get(i), map);
        }
    }

    /**
     * 目标节点
     *
     * @param targetUids
     */
    private void craftTarget(List<String> targetUids) {
        HzPbomTreeQuery query = new HzPbomTreeQuery();
        for (int i = 0; i < targetUids.size(); i++) {
            LocalCraft craft = new LocalCraft();
            String tar = targetUids.get(i);
            String str;
            //目标节点
            HzPbomLineRecord record = hzPbomRecordDAO.getHzPbomByEbomPuid(tar, projectUid);
            //第一层子层，用于设置新件的查找编号
            List<HzPbomLineRecord> isWithsChildren = hzPbomRecordDAO.getFirstLevelBomByParentId(record.geteBomPuid(), projectUid);
            int max = 0;
            String sortNum = null;
            //有子层，从第一层子层中获取醉倒查找编号，应获取最大的排序号
            if (isWithsChildren != null) {
                for (int i1 = 0; i1 < isWithsChildren.size(); i1++) {
                    str = isWithsChildren.get(i1).getLineIndex();
                    int temp = Integer.parseInt(str.substring(str.lastIndexOf(".") + 1));
//                    max = temp > max ? temp : max;
                    if (temp > max) {
                        max = temp;
                        sortNum = isWithsChildren.get(i1).getSortNum();
                    }

                }
            } else {
                sortNum = record.getSortNum();
            }
            query.setProjectId(projectUid);
            query.setPuid(record.geteBomPuid());
            //有超过2层的后代层，则找到最大的后代层的排序号
            List<HzPbomLineRecord> list = hzPbomRecordDAO.getHzPbomTree(query);
            //排序
            if (list != null && !list.isEmpty()) {
                //排序后进行排序号的获取
                list.sort((o1, o2) -> {
                    double d1 = Double.parseDouble(o1.getSortNum());
                    double d2 = Double.parseDouble(o2.getSortNum());
                    return d1 - d2 > 0 ? 1 : d1 - d2 == 0 ? 0 : -1;
                });
                //最后一位的排序号时下限值
                sortNum = list.get(list.size() - 1).getSortNum();
            }
            craft.setUid(UUIDHelper.generateUpperUid());
            //没有子层，则查找编号为首位
            craft.setIndex(max);
            craft.setLine(record);
            craft.setSortNum(sortNum);
            theMaxInorder.put(tar, craft);
        }
    }

    /**
     * 更新查找编号
     */
    private void craftUpdate() {
        toChildrenNeedToUpdateItsLineIndex.entrySet().iterator();
        for (String key : toChildrenNeedToUpdateItsLineIndex.keySet()) {
            for (String _key : toChildrenNeedToUpdateItsLineIndex.get(key).keySet()) {
                if (craftIsInfluenceToDB) {
                    HzPbomLineRecord record = toChildrenNeedToUpdateItsLineIndex.get(key).get(_key);
                    HzPbomLineRecord toUpdate = new HzPbomLineRecord();
                    toUpdate.seteBomPuid(record.geteBomPuid());
                    toUpdate.setLineIndex(record.getLineIndex());
                    if (hzPbomRecordDAO.update(toUpdate) <= 0) {
                        logger.error("更新需要重新排序的子层" + record.getLineId() + "查找编号失败");
                    }
                }
            }
        }
    }

    /**
     * 删除合成源
     */
    private void craftDelete() {
        List<DeleteHzPbomReqDTO> listOfDto = new ArrayList<>();
        for (int i = 0; i < theChildrenNeedToDelete.size(); i++) {
            HzPbomLineRecord record = theChildrenNeedToDelete.get(i);
            DeleteHzPbomReqDTO dto = new DeleteHzPbomReqDTO();
            dto.seteBomPuid(record.geteBomPuid());
            dto.setProjectId(projectUid);
            dto.setPuids(record.getPuid());
            listOfDto.add(dto);
        }
        //执行插入
        if (craftIsInfluenceToDB) {
            if (hzPbomRecordDAO.deleteList(listOfDto) <= 0) {
                logger.error("删除合成源数据失败");
            }
        }
    }

    /**
     * 清除缓存
     */
    private void clearCoach() {
        this.myWavelet.clear();
        this.myLovelyWavelet.clear();
        this.toChildrenNeedToUpdateItsLineIndex.clear();
        this.theChildrenNeedToDelete.clear();
        this.orderIsResetChildren.clear();
        this.orgOrder.clear();
        this.theKeyToAllSameParent.clear();
        this.allParentTree.clear();
        this.theMaxInorder.clear();
        this.targetTreeMap.clear();
    }

    /**
     * 目标节点需要设置isHas为1
     *
     * @param targetUids
     */
    private void craftTargetsIsHas(List<String> targetUids) {
        HzPbomLineRecord record = new HzPbomLineRecord();
        for (int i = 0; i < targetUids.size(); i++) {
            record.setIsHas(1);
            record.seteBomPuid(targetUids.get(i));
            if (craftIsInfluenceToDB) {
                if (hzPbomRecordDAO.update(record) <= 0) {
                    logger.error("更新是否有子层数据失败");
                }
            }

        }
    }

    /**
     * 将新件批量插入到数据库
     *
     * @param targetTreeMap
     */
    private void craftInsert(Map<String, LocalCraftTarget> targetTreeMap) {
        for (Map.Entry<String, LocalCraftTarget> entry : targetTreeMap.entrySet()) {
            LocalCraftTarget craftTarget = entry.getValue();
            if (craftIsInfluenceToDB) {
                if (hzPbomRecordDAO.insertList(craftTarget.getChildrenTree()) != 1) {
                    logger.error("批量插入数据失败");
                    break;
                }
            }
        }
    }

    /**
     * 设置检查合成源切除之后是否还有子，没有子的话需要设置isHas为0
     * <p>
     * 其实可以从craftBuildNewTree中将受影响的父层进行筛选出来，只是逻辑将更加复杂
     */
    private void craftSrcParent() {
        Map<String, List<HzPbomLineRecord>> coach = new LinkedHashMap<>();
        Map<String, Integer> temp = new LinkedHashMap<>();
        for (int i = 0; i < theChildrenNeedToDelete.size(); i++) {
            HzPbomLineRecord record = theChildrenNeedToDelete.get(i);
            if (!coach.containsKey(record.getParentUid())) {
                List<HzPbomLineRecord> list = new ArrayList<>();
                list.add(record);
                coach.put(record.getParentUid(), list);
            } else {
                coach.get(record.getParentUid()).add(record);
            }
        }
        //循环找出需要删除的子层
        Iterator<Map.Entry<String, List<HzPbomLineRecord>>> it = coach.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<HzPbomLineRecord>> entry = it.next();
            String parentUid = entry.getKey();
            List<HzPbomLineRecord> values = entry.getValue();
            //寻找父层单条
            HzPbomLineRecord line = hzPbomRecordDAO.getHzPbomByEbomPuid(parentUid, projectUid);
            if (line != null) {
                //找到所有同名的父层
                param.put("lineId", line.getLineId());
                List<HzPbomLineRecord> allCodeIsWithToParentCode = hzPbomRecordDAO.findPbom(param);
                //循环对比需要删除的子层
                for (int i1 = 0; i1 < allCodeIsWithToParentCode.size(); i1++) {
                    List<HzPbomLineRecord> isWithsChildren = hzPbomRecordDAO.getFirstLevelBomByParentId(allCodeIsWithToParentCode.get(i1).geteBomPuid(), projectUid);
                    Iterator<HzPbomLineRecord> ito = isWithsChildren.iterator();
                    while (ito.hasNext()) {
                        HzPbomLineRecord next = ito.next();
                        for (int i = 0; i < values.size(); i++) {
                            if (next.getLineId().equals(values.get(i).getLineId())) {
                                ito.remove();
                                break;
                            }
                        }

                    }
                    if (isWithsChildren.size() <= 0) {
                        temp.put(allCodeIsWithToParentCode.get(i1).geteBomPuid(), 0);
                    }
                }
            } else {
                logger.error("没有找到父层");
            }
        }
        temp.forEach((key, value) -> {
            HzPbomLineRecord record = new HzPbomLineRecord();
            record.seteBomPuid(key);
            record.setIsHas(value);
            if (hzPbomRecordDAO.update(record) <= 0) {
                logger.error("更新isHas数据为0操作失败");
            }
        });
//            if (!coach.containsKey(theChildrenNeedToDelete.get(i).getParentUid())) {
//                coach.put(theChildrenNeedToDelete.get(i).getParentUid(), theChildrenNeedToDelete.get(i).getParentUid());
//            } else {
//
//            }
//            HzPbomLineRecord line = hzPbomRecordDAO.getHzPbomByEbomPuid(theChildrenNeedToDelete.get(i).getParentUid(), projectUid);
//            param.put("lineId", line.getLineId());
//            //同号的父层节点
//            List<HzPbomLineRecord> allCodeIsWithToParentCode = hzPbomRecordDAO.findPbom(param);
//            for (int i1 = 0; i1 < allCodeIsWithToParentCode.size(); i1++) {
//                List<HzPbomLineRecord> isWithsChildren = hzPbomRecordDAO.getFirstLevelBomByParentId(allCodeIsWithToParentCode.get(i1).geteBomPuid(), projectUid);
//                Iterator<HzPbomLineRecord> it = isWithsChildren.iterator();
//                while (it.hasNext()) {
//                    HzPbomLineRecord next = it.next();
//                    if (next.getLineId().equals(theChildrenNeedToDelete.get(i).getLineId())) {
//                        it.remove();
//                    }
//                }
//                temp.put(allCodeIsWithToParentCode.get(i1).geteBomPuid() + "|" + allCodeIsWithToParentCode.get(i1).getLineIndex(), isWithsChildren);
//            }
//        }
        System.out.println();
    }

    /**
     * 构建新树，是让人犯罪的一块逻辑
     *
     * @param theMaxInorder 本地记录，记录了需要挂载新件的目标挂载点、排序号、查找编号和挂载点的UID
     * @param pbom          新件，只包含前端收集到的基本数据，不包括UID，排序号的查找编号等信息
     */
    private void craftBuildNewTree(Map<String, LocalCraft> theMaxInorder, HzPbomLineRecord pbom) throws CloneNotSupportedException {
        double mymy;
        double youryour;
        int lineIndex;
        int pLineIndex;
        int topLineIndex;
        for (Map.Entry<String, LocalCraft> entry : theMaxInorder.entrySet()) {
            LocalCraft craft = entry.getValue();
            //有子层的情况下取子层最大的一个sortNum，反之取当前目标的sortNum
            String mySortNum = craft.getSortNum() == null ? craft.getLine().getSortNum() : craft.getSortNum();
            //比当前排序号编号大的正统排序号，是首次导入时的临近的排序号，100进制
            String localSortNum = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectUid, mySortNum);
            mymy = Double.parseDouble(mySortNum);
            if (localSortNum != null) {
                youryour = Double.parseDouble(localSortNum);
            } else {
                youryour = mymy + 100;
            }
            double _temp = mymy;
            //需要插入到数据库中的数据
            LocalCraftTarget target = new LocalCraftTarget();
            //设置识别UID
            target.setUid(craft.getUid());
            //生成首位排序号
            _temp = GENERATE_RANDOM_DB(_temp, youryour);
            //查找编号重新排
            lineIndex = 10;
            pLineIndex = 10;
            //没有查汇总编号，则是挂载点一开始就没有子层
            topLineIndex = craft.getIndex() == 0 ? 10 : craft.getIndex() + 10;
            //设置数模层
            pbom.setBomDigifaxId(craft.getLine().getBomDigifaxId());
            //新件设置一份单独的PUID
            pbom.setPuid(UUIDHelper.generateUpperUid());
            //设置自身的eUId
            pbom.seteBomPuid(pbom.getPuid());
            //父层的UID即为挂载目标
            pbom.setParentUid(craft.getLine().geteBomPuid());
            //设置第一位排序号
            pbom.setSortNum(String.valueOf(_temp));
            //设置查找编号
            pbom.setLineIndex(craft.getLine().getLineIndex() + "." + topLineIndex);
            //克隆一份出来
            target.getChildrenTree().add(pbom.clone());
            //循环挂载所有的子
            for (Map.Entry<String, Map<String, List<HzPbomLineRecord>>> etr : allParentTree.entrySet()) {
                if (etr.getValue() != null && !etr.getValue().isEmpty()) {
                    for (Map.Entry<String, List<HzPbomLineRecord>> values : etr.getValue().entrySet()) {
                        //临时变量
                        Map<String, String> newEbomUid = new LinkedHashMap<>();
                        Map<String, String> newEbomUidReverse = new LinkedHashMap<>();
                        Map<String, HzPbomLineRecord> parent = new LinkedHashMap<>();
                        Map<String, Integer> newEbomUidLineIndex = new LinkedHashMap<>();
                        //
                        for (int i = 0; i < values.getValue().size(); i++) {
                            //
                            HzPbomLineRecord record = values.getValue().get(i);
                            //重新生成排序号
                            _temp = GENERATE_RANDOM_DB(_temp, youryour);
                            record.setSortNum(String.valueOf(_temp));
                            //如果有子层，实际上不能根据该字段判断是否有子层，因为不能判断其UID是否被其他的PBOM引用了parentUid
                            if (record.getIsHas() == 1) {
                                //新生成的UID
                                String firstNodeEUID = UUIDHelper.generateUpperUid();
                                //缓存新老UID对应关系
                                newEbomUid.put(record.geteBomPuid(), firstNodeEUID);
                                //反转体数据存储
                                newEbomUidReverse.put(firstNodeEUID, record.geteBomPuid());
                                //找到了上层的父
                                if (newEbomUid.containsKey(record.getParentUid())) {
                                    //设置源的UID
                                    record.setPuid(firstNodeEUID);
                                    //设置父层UID
                                    record.setParentUid(newEbomUid.get(record.getParentUid()));
                                    //存储历史puid与新的UID对应关系
                                    newEbomUid.put(record.geteBomPuid(), record.getPuid());
                                    //再设置新的eBom UID
                                    record.seteBomPuid(record.getPuid());
                                    //反转数据存储了父层的最新的查找编号
                                    if (newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                        //重置查找编号
                                        int _lineIndex = newEbomUidLineIndex.get(record.getParentUid());
                                        if (record.getParentUid() == null) {
                                            System.out.println();
                                        }
                                        //父层的查找编号+父层的最大子查找编号
                                        String parentLineIndex = parent.get(record.getParentUid()).getLineIndex();
                                        record.setLineIndex(parentLineIndex + "." + _lineIndex);
                                        //存储最新的编号
                                        _lineIndex += 10;
                                        newEbomUidLineIndex.put(record.getParentUid(), _lineIndex);
                                    }
                                    //反转数据没有存储父层最新的查找编号，则需要获取最新的查找编号
                                    else {
                                        if (parent.containsKey(record.getParentUid())) {
                                            //不是直接子一层
                                            if (!newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                                newEbomUidLineIndex.put(record.getParentUid(), 10);
                                            }
                                            record.setLineIndex(parent.get(record.getParentUid()).getLineIndex() + "." + newEbomUidLineIndex.get(record.getParentUid()));
                                            newEbomUidLineIndex.put(record.getParentUid(), newEbomUidLineIndex.get(record.getParentUid()) + 10);
                                        } else {
                                            //是新父层，而且是挂载点直接子一层，即获取到最新的父层对应的子层查找编号
                                            record.setLineIndex(pbom.getLineIndex() + "." + pLineIndex);
                                            //这里是挂载点的直接子一层查找编号
                                            pLineIndex += 10;
                                            newEbomUidLineIndex.put(record.geteBomPuid(), pLineIndex);
                                        }
                                    }
                                    parent.put(record.getPuid(), values.getValue().get(i));
                                }
                                //永远没有找到父层，那一定是第一层
                                else {
                                    //设置源的UID
                                    record.setPuid(firstNodeEUID);
                                    //设置父层UID
                                    record.setParentUid(pbom.geteBomPuid());
                                    //存储历史puid与新的UID对应关系
                                    newEbomUid.put(record.geteBomPuid(), record.getPuid());
                                    //再设置新的eBom UID
                                    record.seteBomPuid(record.getPuid());
                                    //设置查找编号
                                    if (newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                        //其实我也不知道这个是什么东西
                                        int _lineIndex = newEbomUidLineIndex.get(record.getParentUid());
                                        if (record.getParentUid() == null) {
                                            System.out.println();
                                        }
                                        String parentLineIndex = parent.get(record.getParentUid()).getLineIndex();
                                        record.setLineIndex(parentLineIndex + "." + _lineIndex);
                                        //存储最新的编号
                                        newEbomUidLineIndex.put(record.getParentUid(), _lineIndex);
                                    }
                                    //反转数据没有对应关系
                                    else {

                                        if (parent.containsKey(record.getParentUid())) {
                                            //不是直接子一层
                                            if (!newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                                newEbomUidLineIndex.put(record.getParentUid(), 10);
                                            }
                                            record.setLineIndex(parent.get(record.getParentUid()).getLineIndex() + "." + newEbomUidLineIndex.get(record.getParentUid()));
                                            newEbomUidLineIndex.put(record.getParentUid(), newEbomUidLineIndex.get(record.getParentUid()) + 10);
                                        } else {
                                            //这里是首次直接子一层进来，将存储挂载点的最大查找编号
                                            record.setLineIndex(pbom.getLineIndex() + "." + lineIndex);
//                                            pLineIndex += 10;
                                            newEbomUidLineIndex.put(record.geteBomPuid(), 10);
                                        }
                                    }
                                    //缓存原对象
                                    parent.put(record.getPuid(), values.getValue().get(i));

                                }
                            }
                            //直接零件层
                            else {
                                if (newEbomUid.containsKey(record.getParentUid())) {
                                    String uid = UUIDHelper.generateUpperUid();
                                    newEbomUid.put(record.geteBomPuid(), uid);
                                    newEbomUidReverse.put(uid, record.geteBomPuid());
                                    //设置源的UID
                                    record.setPuid(uid);
                                    //设置父层UID
                                    record.setParentUid(newEbomUid.get(record.getParentUid()));
                                    //存储历史puid与新的UID对应关系
                                    newEbomUid.put(record.geteBomPuid(), record.getPuid());
                                    //再设置新的eBom UID
                                    record.seteBomPuid(record.getPuid());
                                    //设置查找编号
                                    if (newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                        int _lineIndex = newEbomUidLineIndex.get(record.getParentUid());
                                        if (record.getParentUid() == null || parent.get(record.getParentUid()) == null) {
                                            System.out.println();
                                        }
                                        String parentLineIndex = parent.get(record.getParentUid()).getLineIndex();
                                        record.setLineIndex(parentLineIndex + "." + _lineIndex);
                                        //存储最新的编号
                                        _lineIndex += 10;
                                        newEbomUidLineIndex.put(record.getParentUid(), _lineIndex);
                                    } else {
                                        if (parent.containsKey(record.getParentUid())) {
                                            if (!newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                                newEbomUidLineIndex.put(record.getParentUid(), 10);
                                            }
                                            record.setLineIndex(parent.get(record.getParentUid()).getLineIndex() + "." + newEbomUidLineIndex.get(record.getParentUid()));
                                            newEbomUidLineIndex.put(record.getParentUid(), newEbomUidLineIndex.get(record.getParentUid()) + 10);
                                        } else {
                                            record.setLineIndex(pbom.getLineIndex() + "." + pLineIndex);
                                            pLineIndex += 10;
                                            newEbomUidLineIndex.put(record.geteBomPuid(), pLineIndex);
                                        }
                                    }
                                    //缓存原对象
                                    parent.put(record.getPuid(), values.getValue().get(i));
                                }
                                //永远没有找到父层，那一定是第一层
                                else {
                                    logger.error("没有子层的合成源找不到父层UID，但它本身的父层一定是存在的");
                                }
                            }
                            //克隆一份出来
                            HzPbomLineRecord record1=record.clone();
                            record1.setStatus(1);
                            target.getChildrenTree().add(record1);
                        }
                    }
                }
                lineIndex += 10;
            }
            //不是父层的层级进行调整层级
            if (!myWavelet.isEmpty()) {
                for (Map.Entry<String, Map<String, HzPbomLineRecord>> childrenEntry : myWavelet.entrySet()) {
                    if (childrenEntry != null) {
                        String parent = childrenEntry.getKey();
                        Map<String, HzPbomLineRecord> values = childrenEntry.getValue();
                        if (values != null && !values.isEmpty()) {
                            for (Map.Entry<String, HzPbomLineRecord> ebony : values.entrySet()) {
                                //查找编号进位10
                                HzPbomLineRecord record = ebony.getValue();
                                String uid = UUIDHelper.generateUpperUid();
                                _temp = GENERATE_RANDOM_DB(_temp, youryour);
                                //设置排序号
                                record.setSortNum(String.valueOf(_temp));
                                //设置主键
                                record.setPuid(uid);
                                //设置
                                record.seteBomPuid(uid);
                                //设置父层UID
                                record.setParentUid(pbom.geteBomPuid());
                                //设置查找编号
                                record.setLineIndex(pbom.getLineIndex() + "." + (lineIndex));
                                //克隆一份
                                target.getChildrenTree().add(record.clone());
                                //挂载点最大查找编号自增10
                                lineIndex += 10;
                            }
                        }
                    }

                }
            }
            targetTreeMap.put(target.getUid(), target);
            targetPartCodes.add(craft.getLine().getLineId());
        }
        logger.warn("重新设置排序号");
    }

    /**
     * 比较查找编号
     *
     * @param src
     * @param tar
     * @return
     * @throws Exception
     */
    public int compare(String src, String tar) throws Exception {
        if (src.contains(".") && tar.contains(".")) {
            String[] source = src.split("[.]");
            String[] target = tar.split("[.]");
            int len1 = source.length;
            int len2 = target.length;
            if (len1 != len2) {
                throw new Exception("比较的查找编号长度不相等");
            } else {
                int i = 0;
                while (i < len1) {
                    int srci = Integer.parseInt(source[i]);
                    int tari = Integer.parseInt(target[i]);
                    if (srci != tari) {
                        return srci - tari;
                    }
                    i++;
                }
            }
        } else {
            throw new Exception("查找编号错误，查找编号必须为包含'.'的数字串");
        }
        return 0;
    }

    /**
     * 对于子层，重设查找编号
     */
    private void craftResetOrder() {
        //先进行排序
        sortOrder();
        doReset();
    }

    /**
     * 重置查找编号
     */
    private void doReset() {
        for (Map.Entry<String, Map<String, HzPbomLineRecord>> entry : toChildrenNeedToUpdateItsLineIndex.entrySet()) {
            Map<String, HzPbomLineRecord> value = entry.getValue();
            String key = entry.getKey();
            int index = -1;
            //进行循环的数据的映射key需要包含在theKeyToAllSameParent中，否则找不到子
            if (theKeyToAllSameParent.containsKey(key)) {
                //第一位为需要切除的pBom的查找编号末尾数值，因此只需要将其进行排序号之后获取第一位即可
                index = orgOrder.get(theKeyToAllSameParent.get(key)).get(0);
            } else {
                logger.warn("没有找到有相同父的映射key，则没有找到排序好后的开始影响的pBom的查找编号");
                continue;
            }
            ///
            if (value != null && !value.isEmpty()) {
                //进行循环，对查找编号进行重置
                for (Map.Entry<String, HzPbomLineRecord> _entry : value.entrySet()) {
                    HzPbomLineRecord _value = _entry.getValue();
                    String lineIndex;
                    if (!orderIsResetChildren.containsKey(key)) {
                        Map<String, HzPbomLineRecord> map = new LinkedHashMap<>();
                        orderIsResetChildren.put(key, map);
                    }
                    lineIndex = _value.getLineIndex();
                    //替换末尾的号
                    lineIndex = lineIndex.substring(0, lineIndex.lastIndexOf(".")) + "." + index;
                    //重新设置查找编号
                    _value.setLineIndex(lineIndex);
                    //缓存
                    orderIsResetChildren.get(key).put(lineIndex, _value);
                    //后面的查找编号进行递增10
                    index += 10;
                }
            } else {
                logger.warn("需要重置查找编号的数据量map为空，key=" + key);
            }
        }
        logger.warn("重置受影响的子BOM查找编号成功");
    }

    /**
     * 开始对对查找编号进行排序，分别对子层和父层进行排序
     */
    private void sortOrder() {
        getSortSrc(myWavelet);
        getSortSrc(myLovelyWavelet);
        sortIt();
        logger.warn("获取排序源成功");
    }

    /**
     * 集合排序
     */
    private void sortIt() {
        if (!orgOrder.isEmpty()) {
            orgOrder.forEach((key, value) -> {
                if (value != null && !value.isEmpty()) {
                    Collections.sort(value);
                }
            });
        }
    }

    /**
     * 获取排序源
     *
     * @param myLovelyWavelet
     */
    private void getSortSrc(Map<String, Map<String, HzPbomLineRecord>> myLovelyWavelet) {
        if (!myLovelyWavelet.isEmpty()) {
            myLovelyWavelet.forEach((key, value) -> {
                if (value != null && !value.isEmpty())
                    value.forEach((_key, _value) -> {
                        if (!orgOrder.containsKey(key)) {
                            List<Integer> list = new ArrayList<>();
                            orgOrder.put(key, list);
                        }
                        orgOrder.get(key).add(Integer.parseInt(_value.getLineIndex().substring(_value.getLineIndex().lastIndexOf(".") + 1)));
                    });
            });
        }
    }

    /**
     * 判断是否为整数
     *
     * @param s
     * @return
     */
    public final boolean isNumeric(String s) {
        if (s != null && !"".equals(s.trim()))
            return s.matches("^[0-9]*$");
        else
            return false;
    }

    /**
     * 产生区域随机小数
     *
     * @param d1 下限区域
     * @param d2 上限区域
     * @return 处于上下限区域范围内的小数
     */
    public static double GENERATE_RANDOM_DB(Double d1, Double d2) {
        //重置精度
        RANDOM_SETTING = THE_SETTING_FROM_PROPERTIES;
        double d = RAND.nextDouble() * RANDOM_SETTING;
        int count = 0;
        while (d1 + d >= d2) {
            d = RAND.nextDouble() * RANDOM_SETTING;
            count++;
            //提高1位精度
            if (count >= ACCURACY_LVL1 && count < ACCURACY_LVL2) {
                RANDOM_SETTING *= 0.1;
                if (count == ACCURACY_LVL1) {
                    logger.warn("生成排序号精度不足，提升至Level1");
                }
            }
            //再提高1位精度
            else if (count >= ACCURACY_LVL2 && count < ACCURACY_LVL3) {
                RANDOM_SETTING *= 0.1;
                if (count == ACCURACY_LVL2) {
                    logger.warn("生成排序号精度不足，提升至Level2");
                }
            }
            //最高精度
            else if (count >= ACCURACY_LVL3) {
                RANDOM_SETTING *= 0.001;
                if (count == ACCURACY_LVL3) {
                    logger.warn("生成排序号精度不足，提升至Level3");
                }
            }
        }
        return d + d1;
    }

    @Data
    class LocalCraft {
        /**
         * BOMLine主键
         */
        private String uid;
        /**
         * 最大的查找编号
         */
        private Integer index;
        /**
         * 最大的排序号
         */
        private String sortNum;
        /**
         * pBOMLine
         */
        private HzPbomLineRecord line;

    }

    @Data
    class LocalCraftTarget {
        /**
         * 主键
         */
        private String uid;
        /**
         * 所有的子
         */
        private List<HzPbomLineRecord> childrenTree = new ArrayList<>();
    }
}

