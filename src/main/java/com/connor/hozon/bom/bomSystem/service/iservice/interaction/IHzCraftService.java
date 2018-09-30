package com.connor.hozon.bom.bomSystem.service.iservice.interaction;

import org.springframework.stereotype.Service;
import sql.pojo.bom.HzPbomLineRecord;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/28 14:44
 * @Modified By:
 */
@Service("hzCraftService")
public interface IHzCraftService {
    /**
     * 创建一个PBOM对象，作为新件
     *
     * @param data
     * @return
     */
    HzPbomLineRecord craftNewPart(Map<String, String> data);

    /**
     * 将合成源父层挂载在新件下
     *
     * @param parentUids
     * @param part
     * @return
     */
    List<HzPbomLineRecord> craftParents(List<String> parentUids, HzPbomLineRecord part) throws CloneNotSupportedException;

    /**
     * 将合成源子层挂载到新件下
     *
     * @param childrenUids
     * @param part
     * @return
     */
    void craftChildren(List<String> childrenUids, HzPbomLineRecord part,Map<String, Map<String, HzPbomLineRecord>> myWavelet) throws Exception;

    /**
     * 将合成的新件挂载到目标件下
     *
     * @param targetUids
     * @param part
     * @return
     */
    List<HzPbomLineRecord> craftAssignToTarget(List<String> targetUids, HzPbomLineRecord part);

    /**
     * 将受影响的件进行更新
     *
     * @param parts
     * @return
     */
    boolean doSaveToDb(List<HzPbomLineRecord> parts);

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
    boolean autoCraft(String projectUid, List<String> parentUids, List<String> childrenUids, List<String> targetUids, Map<String, String> collectedData);

    /**
     * 获取当前挂载对象的零件号
     * @return
     */
    List<String> getTargetPartCodes();
}
