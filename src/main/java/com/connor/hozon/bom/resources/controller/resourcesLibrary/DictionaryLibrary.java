package com.connor.hozon.bom.resources.controller.resourcesLibrary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/28
 * Time: 21:10
 */

@Controller
@RequestMapping(value = "dictionary")
public class DictionaryLibrary {

    /**
     * 跳转到字典库的添加页面
     * @return
     */
    @RequestMapping(value = "getAddDictionaryToPage")
    public String getAddDictionaryLibrary(){
        return "resourcesLibrary/dictionaryLibrary/addDictionaryLibrary";
    }

    @RequestMapping(value = "getUpdateDictionaryToPage")
    public String getUpdateDictionaryLibrary(){
        return "resourcesLibrary/dictionaryLibrary/updateDictionaryLibrary";
    }
}
