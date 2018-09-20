package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzColorModelDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.relevance.HzRelevanceBasicDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.relevance.HzRelevanceRelationDao;
import com.connor.hozon.bom.bomSystem.dto.HzFeatureQueryDTO;
import com.connor.hozon.bom.bomSystem.dto.relevance.HzRelevanceQueryDTO;
import com.connor.hozon.bom.bomSystem.dto.relevance.HzRelevanceQueryResultBean;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0ModelService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0Record;
import sql.pojo.cfg.HzCfg0Relevance;
import sql.pojo.cfg.HzColorModel2;
import sql.pojo.cfg.relevance.HzRelevanceBasic;
import sql.pojo.cfg.relevance.HzRelevanceRelation;

import java.util.*;

import static javax.swing.UIManager.get;

@Service("hzRelevanceService2")
public class HzRelevanceService2 {

    //特性服务
    @Autowired
    private HzCfg0Service hzCfg0Service;
    //配色方案服务
    @Autowired
    private HzColorModelDao hzColorModelDao;
    //相关性主表Dao
    @Autowired
    private HzRelevanceBasicDao hzRelevanceBasicDao;
    //相关性关系表Dao
    @Autowired
    private HzRelevanceRelationDao hzRelevanceRelationDao;

    /**
     *过滤特性为“车身颜色/HZCSYS”和特性为“内外饰/HZNWS”
     * @param projectPuid
     * @return
     */
    public JSONObject addRelevance(String projectPuid) {
        JSONObject response = new JSONObject();
        JSONArray datas = new JSONArray();
        Long index = 1L;

        //查看相关性主表和相关性关联表是否有数据，有则删除
        hzRelevanceBasicDao.deleteByProjectUid(projectPuid);

        //相关性list
        List<HzRelevanceBasic> hzRelevanceBasics = new ArrayList<HzRelevanceBasic>();

        //搜索全部特性值，并经过P_CFG0_OBJECT_ID 升序排序
        QueryBase queryBase = new QueryBase();
        queryBase.setSort("P_CFG0_OBJECT_ID");
         List<HzCfg0Record> hzCfg0Records = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, new HzFeatureQueryDTO());

        //查询该项目下所有配色方案
        List<HzColorModel2> hzColorModel2s = hzColorModelDao.selectByProjectPuid(projectPuid);

        //遍历特性值
        for (HzCfg0Record hzCfg0Record : hzCfg0Records) {
            //车身颜色不参与相关性
            if ((!"HZCSYS".equals(hzCfg0Record.getpCfg0FamilyName()))&&!"HZNSYS".equals(hzCfg0Record.getpCfg0FamilyName())){
                //将同一特性的配色方案分组
                List<HzColorModel2> hzColorModel2List = new ArrayList<HzColorModel2>();
                for (HzColorModel2 hzColorModel2 : hzColorModel2s) {
                    if (hzCfg0Record.getpCfg0FamilyName().equals(hzColorModel2.getpOptionfamilyName())) {
                        hzColorModel2List.add(hzColorModel2);
                    }
                }
                //将同特性下不同颜色的配色方案放入Map
                Map<String, List<HzColorModel2>> colorMap = new HashMap<String, List<HzColorModel2>>();
                for (HzColorModel2 hzColorModel2 : hzColorModel2List) {
                    if ("-".equals(hzColorModel2.getColorCode())) {
                        continue;
                    }
                    String colorcode = hzColorModel2.getColorCode();
                    if (colorMap.get(colorcode) == null) {
                        colorMap.put(colorcode, new ArrayList<HzColorModel2>());
                    }
                    List<HzColorModel2> hzColorModel2List1 = colorMap.get(colorcode);
                    hzColorModel2List1.add(hzColorModel2);
                }

                Set<String> keys = colorMap.keySet();
                //生成相关性
                for (String key : keys) {
                    //拼接相关性
                    String relevance = hzCfg0Record.getpCfg0FamilyName() + "-" + hzCfg0Record.getpCfg0ObjectId() + "-" + key;
                    //拼接相关性描述
                    String relevanceDesc = hzCfg0Record.getpCfg0FamilyDesc() + "-" + colorMap.get(key).get(0).getColorName();
                    //拼接相关性代码
                    String relevanceCode = "$ROOT." + hzCfg0Record.getpCfg0FamilyName() + " = '" + hzCfg0Record.getpCfg0ObjectId() + "' AND ";
                    List<HzColorModel2> hzColorModel2s1 = colorMap.get(key);
                    int size = hzColorModel2s1.size();
                    if (size == 1) {
                        relevanceCode += "$ROOT.HZCSYS = '" + key + "'";
                    } else if (size > 1) {
                        relevanceCode += "( ";
                        for (int i = 0; i < size; i++) {
                            HzColorModel2 hzColorModel2 = hzColorModel2s1.get(i);
                            relevanceCode = relevanceCode + "$ROOT.HZCSYS = '" + hzColorModel2.getpModelShell() + "' ";
                            if (size - i > 1) {
                                relevanceCode += "OR ";
                            }
                        }
                        relevanceCode += " )";
                    }

                    /**
                     *后端添加
                     * 1.添加相关性主表
                     * 2.添加相关性关联表
                     */

                    //添加相关性主表
                    HzRelevanceBasic hzRelevanceBasic = new HzRelevanceBasic();
                    //颜色code
                    hzRelevanceBasic.setRbColorCode(key);
                    //颜色id
                    hzRelevanceBasic.setRbColorUid(colorMap.get(key).get(0).getpColorUid());
                    //特性code
                    hzRelevanceBasic.setRbFeatureCode(hzCfg0Record.getpCfg0FamilyName());
                    //特性id
                    hzRelevanceBasic.setRbFeatureUid(hzCfg0Record.getpCfg0FamilyPuid());
                    //特性值code
                    hzRelevanceBasic.setRbFeatureValueCode(hzCfg0Record.getpCfg0ObjectId());
                    //特性值id
                    hzRelevanceBasic.setRbFeatureValueUid(hzCfg0Record.getPuid());
                    //相关性
                    hzRelevanceBasic.setRbRelevance(relevance);
                    //相关性描述
                    hzRelevanceBasic.setRbRelevanceDesc(relevanceDesc);
                    //相关性代码
                    hzRelevanceBasic.setRbRelevanceCode(relevanceCode);
                    //项目代码
                    hzRelevanceBasic.setRbProjectUid(projectPuid);
                    //状态
                    hzRelevanceBasic.setRelevanceStatus(0);
                    //向相关性主表增加数据
                    Long relevanceUid = hzRelevanceBasicDao.insertBasic(hzRelevanceBasic);


                    //添加相关性关联表
                    List<HzColorModel2> hzColorModel2List1 = colorMap.get(key);
                    for (HzColorModel2 hzColorModel2 : hzColorModel2List1) {
                        HzRelevanceRelation hzRelevanceRelation = new HzRelevanceRelation();
                        //特性id
                        hzRelevanceRelation.setRrCfgFamilyUid(hzCfg0Record.getpCfg0FamilyPuid());
                        //特性值id
                        hzRelevanceRelation.setRrCfg0Uid(hzCfg0Record.getPuid());
                        //颜色id
                        hzRelevanceRelation.setRrColorUid(hzColorModel2.getpColorUid());
                        //相关性id
                        hzRelevanceRelation.setRrRelevanceId(relevanceUid);
                        //配色方案的id
                        hzRelevanceRelation.setRrColorModelUid(hzColorModel2.getModelUid());
                        hzRelevanceRelationDao.insertOne(hzRelevanceRelation);
                    }


                    //前端显示
                    JSONObject data = new JSONObject();
                    data.put("index", index);
                    index++;
                    data.put("relevance", hzCfg0Record.getpCfg0FamilyName() + "-" + hzCfg0Record.getpCfg0ObjectId() + "-" + key);
                    data.put("relevanceDesc", hzCfg0Record.getpCfg0FamilyDesc() + "-" + colorMap.get(key).get(0).getColorName());
                    data.put("relevanceCode", relevanceCode);
                    datas.add(data);
                }
            }
        }
        response.put("totalCount", index - 1);
        response.put("result", datas);
        return response;
    }

    /**
     * 查询相关性
     *
     * @param dto
     * @return
     */
    public JSONObject queryRelevance(HzRelevanceQueryDTO dto) {
        JSONObject result = new JSONObject();
        dto.setSort(HzRelevanceQueryDTO.relectSortToDB(dto.getSort()));
        List<HzRelevanceQueryResultBean> beans = hzRelevanceBasicDao.selectByPage(dto);
        Integer totalCount = hzRelevanceBasicDao.tellMeHowManyOfIt(dto);
        result.put("result", beans);
        result.put("totalCount", totalCount);
        return result;
    }
}
