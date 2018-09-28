package com.connor.hozon.bom.bomSystem.service.interaction;

import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.iservice.interaction.IHzCraftService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.query.HzPbomTreeQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.bom.HzPbomLineRecord;

import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/28 14:45
 * @Modified By:
 */
@Configuration
public class HzCraftService implements IHzCraftService {
    /**
     * 目标挂载对象零件代码
     */
    private List<String> targetPartCodes;
    /**
     * 项目UID
     */
    private String projectUid;
    @Autowired
    HzPbomRecordDAO hzPbomRecordDAO;
    /**
     * 父层树形结构集合
     */
    private Map<String, List<HzPbomLineRecord>> mapParentTree = new HashMap<>();

    /**
     * 需要删除的子层
     */
    private List<HzPbomLineRecord> theChildrenNeedToDelete = new ArrayList<>();
    /**
     * 该集合下的所有子都需要进行查找编号的重新编排，同一个父，出现了多个元素进行合成，需要重新编排
     */
    private Map<String, Map<String, HzPbomLineRecord>> toChildrenNeedToUpdateItsLineIndex = new LinkedHashMap<>();
    /**
     * 同父层下的所有子层
     */
    private Map<String, Map<String, HzPbomLineRecord>> myWavelet = new LinkedHashMap<>();

    private Map<String, Map<String, HzPbomLineRecord>> myLovelyWavelet = new LinkedHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(HzCraftService.class);
    private Map<String, Object> param = new HashMap<>();

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
        for (int i = 0; i < parentUids.size(); i++) {
            HzPbomLineRecord parent = hzPbomRecordDAO.getHzPbomByEbomPuid(parentUids.get(i), projectUid);
            if (myLovelyWavelet.containsKey(parent.getLineId())) {
                myLovelyWavelet.get(parent.getLineId()).put(parent.getPuid(), parent);
            } else {
                Map<String, HzPbomLineRecord> map = new LinkedHashMap<>();
                map.put(parent.getPuid(), parent);
                myLovelyWavelet.put(parent.getLineId(), map);
            }
        }
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
    public void craftChildren(List<String> childrenUids, HzPbomLineRecord part) {
        for (int i = 0; i < childrenUids.size(); i++) {
            HzPbomLineRecord line = hzPbomRecordDAO.getHzPbomByEbomPuid(childrenUids.get(i), projectUid);
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
                        Map<String, HzPbomLineRecord> tempToUpdate = new LinkedHashMap<>();
                        //找到相同父下的所有非合成源的子
                        for (HzPbomLineRecord isWithsChild : isWithsChildren) {
                            //余认为零件号与查找编号相等则为同一个BOM
                            if (values.containsKey(isWithsChild.getLineIndex()) && values.get(isWithsChild.getLineIndex()).getLineId().equals(isWithsChild.getLineId())) {
                                logger.warn("新郎新娘可以接吻了");
                                theChildrenNeedToDelete.add(isWithsChild);
                                isNeedToUpdate = true;
                            } else {
                                tempToUpdate.put(isWithsChild.getLineIndex(), isWithsChild);
                            }
                        }
                        if (isNeedToUpdate) {
                            toChildrenNeedToUpdateItsLineIndex.put(allCodeIsWithToParentCode.get(i1).getPuid(), tempToUpdate);
                        } else {
                            logger.warn("虽然PBOM中PUID为" + allCodeIsWithToParentCode.get(i1).getPuid() +
                                    "的所有子层与合成源父层的子数量一致，但是没有找到合成源的父层" + parent.getLineId() + "(" +
                                    parent.getPuid() + ")与合成源的查找编号一致的BOM行，认定不是同一个BOM，或者BOM错误，又或是BOM已被更改"
                            );
                        }
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
        this.projectUid = projectUid;
        param.put("projectId", projectUid);
        try {
            craftChildren(childrenUids, pbom);
            craftChildren(parentUids, pbom);
            craftParents(parentUids, pbom);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
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
}
