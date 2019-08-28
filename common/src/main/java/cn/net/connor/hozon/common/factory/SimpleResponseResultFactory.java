/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.factory;


import cn.net.connor.hozon.common.setting.CommonSetting;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 消息生成工厂，用于生成一些简单的消息返回给前端
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/26 13:48
 * @Modified By:
 */
public class SimpleResponseResultFactory {
    /**
     * 生产错误消息
     *
     * @param message 消息，如果没有消息则传null，一般都需要服务器生产一下错误消息到前端显示
     * @return
     */
    public static JSONObject createErrorResult(String message) {
        return createResult(false, CommonSetting.ERROR_FIELD, message);
    }


    /**
     * 生产成功数据消息
     *
     * @param jsonArray 需要传输给前端的数据，如果没有数据则传null，传输批量数据到前端
     * @return
     */
    public static JSONObject createSuccessResult(JSONArray jsonArray) {
        return createResult(true, CommonSetting.RESULT_DATA, jsonArray);
    }

    /**
     * 生产成功数据消息
     *
     * @param message 需要传输给前端的数据，如果没有数据则传null，传输一条String到前端
     * @return
     */
    public static JSONObject createSuccessResult(String message) {
        return createResult(true, CommonSetting.RESULT_DATA, message);
    }

    /**
     * @param status
     * @param field
     * @param data
     * @return
     */
    private static JSONObject createResult(boolean status, String field, Object data) {
        JSONObject result = new JSONObject();
        result.put(CommonSetting.STATUS_FIELD, status);//消息状态默认为false

        if (data != null) {
            result.put(field, data);
        }
        return result;
    }
}
