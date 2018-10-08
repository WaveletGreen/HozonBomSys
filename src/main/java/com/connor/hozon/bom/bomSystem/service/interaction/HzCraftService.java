package com.connor.hozon.bom.bomSystem.service.interaction;

import com.connor.hozon.bom.bomSystem.helper.PropertiesHelper;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.iservice.interaction.IHzCraftService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzPbomReqDTO;
import com.connor.hozon.bom.resources.domain.query.HzPbomTreeQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.sys.entity.User;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.bom.HzPbomLineRecord;

import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 工艺合件
 * 作为子：筛选所有需要进行合成的源，同时找到所有源的父层的查找编号比源大的兄弟层，将兄弟层的查找编号进行重新排序
 * 作为父：查找已合成源为基准，递归查找源下的所有子层，作为合成结果，合成结果挂载到合成目标下，对合成结果的每一份进行复制，
 * 将重新构建树形结构
 * @Date: Created in 2018/9/28 14:45
 * @Modified By:
 */
@Configuration
public class HzCraftService implements IHzCraftService {
    /**
     * 预设随机数产生的精度后移位
     */
    public static final double RANDOM_SETTING = 0.1;
    /**
     * 目标挂载对象零件代码
     */
    private List<String> targetPartCodes = new ArrayList<>();
    /**
     * 项目UID
     */
    private String projectUid;
    @Autowired
    HzPbomRecordDAO hzPbomRecordDAO;
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

    private Map<String, List<Integer>> orgOrder = new LinkedHashMap<>();

    private Map<String, String> theKeyToAllSameParent = new LinkedHashMap<>();
    /**
     * 最大的查找编号
     */
    private Map<String, LocalCraft> theMaxInorder = new LinkedHashMap<>();
    public static boolean isDebug = true;
    private boolean isInsertDebug = true;
    /**
     * 一份父层的属性结构图
     */
    private Map<String, Map<String, List<HzPbomLineRecord>>> allParentTree = new LinkedHashMap<>();

    private Map<String, LocalCraftTarget> targetTreeMap = new LinkedHashMap<>();

    /**
     * 自动生成工艺合件
     *
     * @param projectUid    项目UID
     * @param parentUids    合成源父层
     * @param childrenUids  合成源子层
     * @param targetUids    挂载目标UID
     * @param collectedData 新件数据
     * @return
     */
    @Override
    public boolean autoCraft(String projectUid, List<String> parentUids, List<String> childrenUids, List<String> targetUids, Map<String, String> collectedData) {

        HzPbomLineRecord pbom = craftNewPart(collectedData);
        clearCoach();
        this.targetPartCodes.clear();
        this.projectUid = projectUid;
        param.put("projectId", projectUid);
        try {
            PropertiesHelper helper = new PropertiesHelper();
            Properties properties = helper.load();
            isDebug = Boolean.valueOf(properties.getProperty("isCraft"));
            isInsertDebug = Boolean.valueOf(properties.getProperty("isCraftInsert"));
            craftChildren(childrenUids, pbom, myWavelet);
            craftChildren(parentUids, pbom, myLovelyWavelet);
            craftParentTree(parentUids);
            craftTarget(targetUids);
            craftResetOrder();
            //构建新的树形结构
            craftBuildNewTree(theMaxInorder, pbom);
            //设置合成源的父层是否isHas，是否为Y
            craftSrcParent();
            craftInsert(targetTreeMap);
            craftTargetsIsHas(targetUids);
            craftDelete();
            craftUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            clearCoach();
        }
        return true;
    }

    /**
     * 更新查找编号
     */
    private void craftUpdate() {
        toChildrenNeedToUpdateItsLineIndex.entrySet().iterator();
        for (String key : toChildrenNeedToUpdateItsLineIndex.keySet()) {
            for (String _key : toChildrenNeedToUpdateItsLineIndex.get(key).keySet()) {
                if (isInsertDebug) {
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
        if (!isInsertDebug) {
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
            if (!isInsertDebug) {
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
            if (!isInsertDebug) {
                if (hzPbomRecordDAO.insertList(craftTarget.getChildrenTree()) != 1) {
                    logger.error("批量插入数据失败");
                    break;
                }
            }
        }
    }

    /**
     * 设置检查合成源切除之后是否还有子，没有子的话需要设置isHas为0
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
     * 构建新树
     *
     * @param theMaxInorder
     * @param pbom
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
            String localSortNum = hzPbomRecordDAO.findMinOrderNumWhichGreaterThanThisOrderNum(projectUid, mySortNum);
            mymy = Double.parseDouble(mySortNum);
            //你比我大
            youryour = Double.parseDouble(localSortNum);
            double _temp = mymy;
            //需要插入到数据库中的数据
            LocalCraftTarget target = new LocalCraftTarget();
            //设置识别UID
            target.setUid(craft.getUid());
            //生成首位排序号
            _temp = generateRandom(_temp, youryour);
            //查找编号重新排
            lineIndex = 10;
            pLineIndex = 10;
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
            //设置层级
            //克隆一份出来
            target.getChildrenTree().add(pbom.clone());
            //循环挂载所有的子
            for (Map.Entry<String, Map<String, List<HzPbomLineRecord>>> etr : allParentTree.entrySet()) {
                if (etr.getValue() != null && !etr.getValue().isEmpty()) {
                    for (Map.Entry<String, List<HzPbomLineRecord>> values : etr.getValue().entrySet()) {
                        Map<String, String> newEbomUid = new LinkedHashMap<>();
                        Map<String, String> newEbomUidReverse = new LinkedHashMap<>();
                        Map<String, HzPbomLineRecord> parent = new LinkedHashMap<>();
                        Map<String, Integer> newEbomUidLineIndex = new LinkedHashMap<>();
                        for (int i = 0; i < values.getValue().size(); i++) {
                            HzPbomLineRecord record = values.getValue().get(i);
                            //重新生成排序号
                            _temp = generateRandom(_temp, youryour);
                            values.getValue().get(i).setSortNum(String.valueOf(_temp));
                            //如果有子层
                            if (record.getIsHas() == 1) {
                                String firstNodeEUID = UUIDHelper.generateUpperUid();
                                //缓存新老UID对应关系
                                newEbomUid.put(record.geteBomPuid(), firstNodeEUID);
                                newEbomUidReverse.put(firstNodeEUID, record.geteBomPuid());
                                //找到了上层的父
                                if (newEbomUid.containsKey(record.getParentUid())) {
                                    //设置源的UID
                                    values.getValue().get(i).setPuid(firstNodeEUID);
                                    //设置父层UID
                                    values.getValue().get(i).setParentUid(newEbomUid.get(record.getParentUid()));
                                    //存储历史puid与新的UID对应关系
                                    newEbomUid.put(values.getValue().get(i).geteBomPuid(), values.getValue().get(i).getPuid());
                                    //再设置新的eBom UID
                                    values.getValue().get(i).seteBomPuid(values.getValue().get(i).getPuid());
                                    //设置查找编号
                                    if (newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                        int _lineIndex = newEbomUidLineIndex.get(record.getParentUid());
                                        if (record.getParentUid() == null) {
                                            System.out.println();
                                        }
                                        String parentLineIndex = parent.get(record.getParentUid()).getLineIndex();
                                        values.getValue().get(i).setLineIndex(parentLineIndex + "." + _lineIndex);
                                        //存储最新的编号
                                        _lineIndex += 10;
                                        newEbomUidLineIndex.put(record.getParentUid(), _lineIndex);
                                    } else {
                                        if (parent.containsKey(record.getParentUid())) {
                                            if (!newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                                newEbomUidLineIndex.put(record.getParentUid(), 10);
                                            }
                                            values.getValue().get(i).setLineIndex(parent.get(record.getParentUid()).getLineIndex() + "." + newEbomUidLineIndex.get(record.getParentUid()));
                                            newEbomUidLineIndex.put(record.getParentUid(), newEbomUidLineIndex.get(record.getParentUid()) + 10);
                                        } else {
                                            values.getValue().get(i).setLineIndex(pbom.getLineIndex() + "." + pLineIndex);
                                            pLineIndex += 10;
                                            newEbomUidLineIndex.put(record.geteBomPuid(), pLineIndex);
                                        }
                                    }
                                    parent.put(values.getValue().get(i).getPuid(), values.getValue().get(i));
                                }
                                //永远没有找到父层，那一定是第一层
                                else {
                                    //设置源的UID
                                    values.getValue().get(i).setPuid(firstNodeEUID);
                                    //设置父层UID
                                    values.getValue().get(i).setParentUid(pbom.geteBomPuid());
                                    //存储历史puid与新的UID对应关系
                                    newEbomUid.put(values.getValue().get(i).geteBomPuid(), values.getValue().get(i).getPuid());
                                    //再设置新的eBom UID
                                    values.getValue().get(i).seteBomPuid(values.getValue().get(i).getPuid());
                                    //设置查找编号
                                    if (newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                        int _lineIndex = newEbomUidLineIndex.get(record.getParentUid());
                                        if (record.getParentUid() == null) {
                                            System.out.println();
                                        }
                                        String parentLineIndex = parent.get(record.getParentUid()).getLineIndex();
                                        values.getValue().get(i).setLineIndex(parentLineIndex + "." + _lineIndex);
                                        //存储最新的编号
                                        newEbomUidLineIndex.put(record.getParentUid(), _lineIndex);
                                    } else {
                                        if (parent.containsKey(record.getParentUid())) {
                                            if (!newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                                newEbomUidLineIndex.put(record.getParentUid(), 10);
                                            }
                                            values.getValue().get(i).setLineIndex(parent.get(record.getParentUid()).getLineIndex() + "." + newEbomUidLineIndex.get(record.getParentUid()));
                                            newEbomUidLineIndex.put(record.getParentUid(), newEbomUidLineIndex.get(record.getParentUid()) + 10);
                                        } else {
                                            values.getValue().get(i).setLineIndex(pbom.getLineIndex() + "." + lineIndex);
//                                            pLineIndex += 10;
                                            newEbomUidLineIndex.put(record.geteBomPuid(), 10);
                                        }
                                    }
                                    //缓存原对象
                                    parent.put(values.getValue().get(i).getPuid(), values.getValue().get(i));

                                }
                            } else {
                                if (newEbomUid.containsKey(record.getParentUid())) {
                                    String uid = UUIDHelper.generateUpperUid();
                                    newEbomUid.put(record.geteBomPuid(), uid);
                                    newEbomUidReverse.put(uid, record.geteBomPuid());
                                    //设置源的UID
                                    values.getValue().get(i).setPuid(uid);
                                    //设置父层UID
                                    values.getValue().get(i).setParentUid(newEbomUid.get(record.getParentUid()));
                                    //存储历史puid与新的UID对应关系
                                    newEbomUid.put(values.getValue().get(i).geteBomPuid(), values.getValue().get(i).getPuid());
                                    //再设置新的eBom UID
                                    values.getValue().get(i).seteBomPuid(values.getValue().get(i).getPuid());
                                    //设置查找编号
                                    if (newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                        int _lineIndex = newEbomUidLineIndex.get(record.getParentUid());
                                        if (record.getParentUid() == null || parent.get(record.getParentUid()) == null) {
                                            System.out.println();
                                        }
                                        String parentLineIndex = parent.get(record.getParentUid()).getLineIndex();
                                        values.getValue().get(i).setLineIndex(parentLineIndex + "." + _lineIndex);
                                        //存储最新的编号
                                        _lineIndex += 10;
                                        newEbomUidLineIndex.put(record.getParentUid(), _lineIndex);
                                    } else {
                                        if (parent.containsKey(record.getParentUid())) {
                                            if (!newEbomUidLineIndex.containsKey(record.getParentUid())) {
                                                newEbomUidLineIndex.put(record.getParentUid(), 10);
                                            }
                                            values.getValue().get(i).setLineIndex(parent.get(record.getParentUid()).getLineIndex() + "." + newEbomUidLineIndex.get(record.getParentUid()));
                                            newEbomUidLineIndex.put(record.getParentUid(), newEbomUidLineIndex.get(record.getParentUid()) + 10);
                                        } else {
                                            values.getValue().get(i).setLineIndex(pbom.getLineIndex() + "." + pLineIndex);
                                            pLineIndex += 10;
                                            newEbomUidLineIndex.put(record.geteBomPuid(), pLineIndex);
                                        }
                                    }
                                    //缓存原对象
                                    parent.put(values.getValue().get(i).getPuid(), values.getValue().get(i));
                                }
                                //永远没有找到父层，那一定是第一层
                                else {
                                    logger.error("没有子层的合成源找不到父层UID，但它本身的父层一定是存在的");
                                }
                            }
//                            values.getValue().get(i).setSortNum(String.valueOf(_temp));
//                            values.getValue().get(i).setPuid(UUIDHelper.generateUpperUid());
//                            if (isFirst) {
//                                values.getValue().get(i).setLineIndex(craft.getLine().getLineIndex() + "." + craft.getIndex());
//                                isFirst = false;
//                            }

//                            if (isFirst) {
//                                values.getValue().get(i).setParentUid(etr.getKey());
//                            } else {
//                                for (int j = i; j > 0; j--) {
//                                    if (values.getValue().get(j).getParentUid().equals(values.getValue().get(j).geteBomPuid())) {
////                                        if (isFirst) {
////                                            //更新第一个对象的UID和ParentUID
////                                            String newUid = UUIDHelper.generateUpperUid();
////                                            newEbomUid.put(values.getValue().get(i).geteBomPuid(), newUid);
////                                            values.getValue().get(i).seteBomPuid(newUid);
////                                            //自身的UID重新生成
////                                            values.getValue().get(i).setPuid(UUIDHelper.generateUpperUid());
////                                        } else if (newEbomUid.containsKey(values.getValue().get(i).getParentUid())) {
////                                            values.getValue().get(i).setParentUid(newEbomUid.get(values.getValue().get(i).getParentUid()));
////                                            values.getValue().get(i).seteBomPuid(newEbomUid.get(values.getValue().get(i).geteBomPuid()));
////
////                                        } else {
////                                            //装填新的UID
////
////                                        }
//                                        //如果有子层
//                                        if (values.getValue().get(j).getIsHas() == 1) {
//
//                                        }
//                                    }
//                                }
//
//                            }
                            //克隆一份出来
                            target.getChildrenTree().add(values.getValue().get(i).clone());
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
                                _temp = generateRandom(_temp, youryour);
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
     * 目标节点
     *
     * @param targetUids
     */
    private void craftTarget(List<String> targetUids) {
        for (int i = 0; i < targetUids.size(); i++) {
            LocalCraft craft = new LocalCraft();
            String tar = targetUids.get(i);
            String str;
            HzPbomLineRecord record = hzPbomRecordDAO.getHzPbomByEbomPuid(tar, projectUid);
            List<HzPbomLineRecord> isWithsChildren = hzPbomRecordDAO.getFirstLevelBomByParentId(record.geteBomPuid(), projectUid);
            int max = 0;
            String sortNum = null;
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
            }
            //没有子层，则查找编号为首位
            craft.setUid(UUIDHelper.generateUpperUid());
            craft.setIndex(max);
            craft.setLine(record);
            craft.setSortNum(sortNum);
            theMaxInorder.put(tar, craft);
        }
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
     * 创建一个PBOM对象，作为新件
     *
     * @param data
     * @return
     */
    @Override
    public HzPbomLineRecord craftNewPart(Map<String, String> data) {
        User user = UserInfo.getUser();
        Date now = new Date();
        HzPbomLineRecord record = new HzPbomLineRecord();
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
        record.setColorPart(checkString(data.get("colorPart")) ? Integer.valueOf(data.get("colorPart")) : null);
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
        //可用状态
        record.setStatus(1);
        //不是颜色件
        record.setColorPart(0);

        //设置为工艺合件
        record.setIsNewPart(2);

        ///lineIndex根据挂载位置进行规则进行设置
        //sortNum根据挂载位置进行规则进行设置
        //工艺合件不来源于EBOM，设置来源UID为自身UID
        // 自身主键和来源主键根据挂载位置决定
        // 数模层的数据也来源于挂载位置

        if (false) {
            if (hzPbomRecordDAO.insertList(Collections.singletonList(record)) <= 0) {
                logger.error("插入PBOM数据失败");
                return record;
            }
        }
        return record;
    }

    /**
     * 将合成源父层挂载在新件下，每一个合成源的parent uid都要设置为新件的数据
     *
     * @param parentUids
     * @param part
     * @return
     */
    @Override
    public List<HzPbomLineRecord> craftParents(List<String> parentUids, HzPbomLineRecord part) throws CloneNotSupportedException {
        return null;
    }

    /**
     * 将合成源子层挂载到新件下
     *
     * @param childrenUids
     * @param part
     * @return
     */
    @Override
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
     * 将合成的新件挂载到目标件下
     *
     * @param targetUids
     * @param part
     * @return
     */
    @Override
    public List<HzPbomLineRecord> craftAssignToTarget(List<String> targetUids, HzPbomLineRecord part) {
        return null;
    }

    /**
     * 将受影响的件进行更新
     *
     * @param parts
     * @return
     */
    @Override
    public boolean doSaveToDb(List<HzPbomLineRecord> parts) {
        return false;
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
            throw new Exception("查找编号错误");
        }
        return 0;
    }

    /**
     * 对于子层，重设查找编号
     */
    private void craftResetOrder() {
        sortorder();
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
            if (theKeyToAllSameParent.containsKey(key)) {
                index = orgOrder.get(theKeyToAllSameParent.get(key)).get(0);
            } else {
                continue;
            }
            if (value != null && !value.isEmpty()) {
                for (Map.Entry<String, HzPbomLineRecord> _entry : value.entrySet()) {
                    HzPbomLineRecord _value = _entry.getValue();
                    String lineIndex;
                    if (!orderIsResetChildren.containsKey(key)) {
                        Map<String, HzPbomLineRecord> map = new LinkedHashMap<>();
                        orderIsResetChildren.put(key, map);
                    }
                    lineIndex = _value.getLineIndex();
                    lineIndex = lineIndex.substring(0, lineIndex.lastIndexOf(".")) + "." + index;
                    _value.setLineIndex(lineIndex);
                    orderIsResetChildren.get(key).put(lineIndex, _value);
                    index += 10;
                }
            }
        }

        if (!isDebug) {
            toChildrenNeedToUpdateItsLineIndex.clear();
        }

    }

    private void sortorder() {
        getSortSrc(myWavelet);
        getSortSrc(myLovelyWavelet);
        sortIt();
        logger.warn("获取排序源成功");
    }

    /**
     * 排序
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

    public double generateRandom(Double d1, Double d2) {
        Random rand = new Random();
        double d = rand.nextDouble() * RANDOM_SETTING;
        while (d1 + d >= d2) {
            d = rand.nextDouble() * RANDOM_SETTING;
        }
        return d + d1;
    }


    @Data
    class LocalCraft {
        private String uid;
        private Integer index;
        private String sortNum;
        private HzPbomLineRecord line;
        private HzPbomLineRecord assignPoint;
    }

    @Data
    class LocalCraftTarget {
        private String uid;
        private List<HzPbomLineRecord> childrenTree = new ArrayList<>();
    }

}

