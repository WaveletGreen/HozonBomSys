package com.connor.hozon.bom.resources.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.connor.hozon.bom.resources.util.Result;
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

    protected PrintWriter getPrintWriter(HttpServletResponse response) {
        if (response == null)
            return null;

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer;
    }

    /**
     * JSON格式数据返回
     * @param writer
     */
    protected void toJSONResponse(Object responseObj, PrintWriter writer) {
        if (writer == null || responseObj == null)
            return;

        try {
            writer.write(JSON.toJSONString(responseObj, SerializerFeature.DisableCircularReferenceDetect));
        } finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     * JSON格式数据返回
     * @param responseObj
     * @param response
     */
    protected void toJSONResponse(Object responseObj, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setDateHeader("Expires", 0);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = getPrintWriter(response);
        toJSONResponse(responseObj, writer);
    }
}
