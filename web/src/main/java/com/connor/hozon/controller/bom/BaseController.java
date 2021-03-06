/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.bom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.connor.hozon.resources.util.Result;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: haozt
 * Date: 2018/5/22
 */
public abstract class BaseController {
    /**
     * JSON 格式数据返回
     * @param obj 返回数据
     * @param response
     */
    protected void toJSONResponse(Object obj,HttpServletResponse response){
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            writer.write(JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect));
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
