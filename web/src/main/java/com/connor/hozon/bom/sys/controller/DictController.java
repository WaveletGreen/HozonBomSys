package com.connor.hozon.bom.sys.controller;

import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.common.base.controller.GenericController;
import com.connor.hozon.bom.common.base.service.GenericService;
import com.connor.hozon.bom.common.util.dict.DictCache;
import com.connor.hozon.bom.sys.entity.Dict;
import com.connor.hozon.bom.sys.entity.QueryDict;
import com.connor.hozon.bom.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 类描述：
 * @auther linzf
 * @create 2017/10/9 0009
 */
@Controller
@RequestMapping("/dict")
public class DictController extends GenericController<Dict, QueryDict> {

    @Autowired
    private DictService dictService;

    @Override
    protected GenericService getService() {
        return dictService;
    }

    /**
     * 功能描述：将字典数据初始化到前端js中
     *
     * @return
     */
    @RequestMapping(value = "/loadDict", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> loadDict() {
        Map<String, Object> result = new HashMap<String, Object>();
        QueryDict queryDict = new QueryDict();
//		queryDict.setIsLoad("1");
        List<Dict> dictList = dictService.query(queryDict);
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put("data", dictList);
        return result;
    }

    /**
     * 功能描述：重新加载数据字典的数据到内存中
     *
     * @return
     */
    @RequestMapping(value = "/reload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> reload() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Dict> dictList = dictService.query(null);
        DictCache.reload(dictList);
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG, "重新加载数据字典成功！");
        return result;
    }


}
