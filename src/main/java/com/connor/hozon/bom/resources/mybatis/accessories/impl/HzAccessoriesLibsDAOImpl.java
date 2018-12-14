package com.connor.hozon.bom.resources.mybatis.accessories.impl;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzAccessoriesLibsDTO;
import com.connor.hozon.bom.resources.domain.query.HzAccessoriesLibsPageQuery;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesLibsDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.accessories.HzAccessoriesLibs;
import sql.pojo.accessories.HzImportAccessoriesLibs;
import sql.pojo.bom.HzImportEbomRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/27
 * Time: 15:19
 */
@Service("hzAccessoriesLibsDAO")
public class HzAccessoriesLibsDAOImpl  extends BaseSQLUtil implements HzAccessoriesLibsDAO {
    @Override
    public int insert(HzAccessoriesLibs accessoriesLibs) {
        return super.insert("HzAccessoriesLibsDAOImpl_insert",accessoriesLibs);
    }

    @Override
    public int importList(List<HzImportAccessoriesLibs> records) {
        int size = records.size();
        //分批插入数据 一次1000条
        int i = 0;
        int cout = 0;
        try {
            synchronized (this){
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzImportAccessoriesLibs> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        super.insert("HzAccessoriesLibsDAOImpl_importList",list);
                    }
                }
                if (i * 1000 < size) {
                    List<HzImportAccessoriesLibs> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    super.insert("HzAccessoriesLibsDAOImpl_importList",list);
                }
            }
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(HzAccessoriesLibs hzAccessoriesLibs) {
        return super.update("HzAccessoriesLibsDAOImpl_update",hzAccessoriesLibs);
    }

    @Override
    public int deleteList(List<DeleteHzAccessoriesLibsDTO> libs) {
        return super.delete("HzAccessoriesLibsDAOImpl_deleteList",libs);
    }

    @Override
    public List<HzAccessoriesLibs> getHzAccessoriesLibs(String puid) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        return super.findForList("HzAccessoriesLibsDAOImpl_getHzAccessoriesLibs",puid);
    }

    @Override
    public Page<HzAccessoriesLibs> getHzAccessoriesByPage(HzAccessoriesLibsPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        Map map = new HashMap();
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzAccessoriesLibsDAOImpl_getHzAccessoriesByPage","HzAccessoriesLibsDAOImpl_getHzAccessoriesTotalCount",request);
    }

    @Override
    public int selectHzAccessoriesLibsByCount(String pMaterielCode) {
        return (Integer)super.findForObject("HzAccessoriesLibsDAOImpl_findByCodeOrCount",pMaterielCode);
    }

    @Override
    public HzAccessoriesLibs getHzAccessoriesLibsByCode(String pMaterielCode) {
        return (HzAccessoriesLibs)super.findForObject("HzAccessoriesLibsDAOImpl_findByCode",pMaterielCode);
    }

    @Override
    public List<String> queryAccessoriesListByMaterielCode(List<String> materielCodeList) {
        return super.executeQueryByPass(new String(), materielCodeList, "HzAccessoriesLibsDAOImpl_queryAccessoriesListByMaterielCode");
    }

    @Override
    public HzAccessoriesLibs queryAccessoriesByMaterielCode(String materielCode) {
        HzAccessoriesLibs hzAccessoriesLibs = new HzAccessoriesLibs();
        hzAccessoriesLibs.setpMaterielCode(materielCode);
        return super.executeQueryById(hzAccessoriesLibs, "HzAccessoriesLibsDAOImpl_queryAccessoriesByMaterielCode");
    }

    public List<HzAccessoriesLibs> queryAccessoriesByMaterielCodes(List<String> materielCodes){
        Map<String,Object> map = new HashMap<>();
        map.put("materielCodes",materielCodes);
        return super.findForList("HzAccessoriesLibsDAOImpl_queryAccessoriesByMaterielCodes",map);
    }
}
