package com.connor.hozon.bom.resources.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: haozt
 * Date: 2018/5/22
 * Time: 9:30
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseController {

    /**
     * 验证失败时返回信息
     * @param result
     * @param response
     */
    public void validateErrorMessages(BindingResult result, HttpServletResponse response){
        if(result.hasErrors()){
            String msg = result.getFieldErrors().get(0).getDefaultMessage();//返回错误信息的第一个字段
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, msg), response);
            return;
        }
    }

    protected PrintWriter getWriter(HttpServletResponse response) {
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
     * @param returnResult
     * @param response
     */
    protected void writeAjaxResponse(String returnResult, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setDateHeader("Expires", 0); // Proxies.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = getWriter(response);
        if (writer == null || returnResult == null)
            return;

        try {
            writer.write(returnResult);
        } finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     * JSON格式数据返回
     * @param writer
     */
    protected void writeAjaxJSONResponse(Object responseObj, PrintWriter writer) {
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
     * @param cirReferDetect
     */
    protected void writeAjaxJSONResponse(Object responseObj, HttpServletResponse response, boolean cirReferDetect) {
        PrintWriter writer = getWriter(response);
        if (writer == null || responseObj == null)
            return;

        try {
            if (!cirReferDetect) {
                writeAjaxJSONResponse(responseObj, writer);
                return;
            }
            writer.write(JSON.toJSONString(responseObj));
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    /**
     * JSON格式数据返回
     * @param responseObj
     * @param writer
     * @param dateFormat
     */
    protected void writeAjaxJSONResponseWithDateFormat(Object responseObj, PrintWriter writer, String dateFormat) {
        if (writer == null || responseObj == null || dateFormat == null) {
            return;
        }

        try {
            writer.write(JSON.toJSONStringWithDateFormat(responseObj, dateFormat,
                    SerializerFeature.DisableCircularReferenceDetect));
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
    protected void writeAjaxJSONResponse(Object responseObj, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setDateHeader("Expires", 0); // Proxies.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = getWriter(response);
        writeAjaxJSONResponse(responseObj, writer);
    }

    protected void writeAjaxJSONResponse(Object responseObj, HttpServletResponse response,HttpServletRequest request) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setDateHeader("Expires", 0); // Proxies.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = getWriter(response);
        writeAjaxJSONResponse(responseObj, writer);
    }



    /**
     *JSON格式数据返回 带日期格式
     * @param responseObj
     * @param response
     * @param dateFormat
     */
    protected void writeAjaxJSONResponseWithDateFormat(Object responseObj, HttpServletResponse response, String dateFormat) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setDateHeader("Expires", 0); // Proxies.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = getWriter(response);
        if (dateFormat != null)
            writeAjaxJSONResponseWithDateFormat(responseObj, writer, dateFormat);
        else
            writeAjaxJSONResponse(responseObj, writer);
    }
}
