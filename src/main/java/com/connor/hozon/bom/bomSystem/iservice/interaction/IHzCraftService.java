package com.connor.hozon.bom.bomSystem.iservice.interaction;

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
