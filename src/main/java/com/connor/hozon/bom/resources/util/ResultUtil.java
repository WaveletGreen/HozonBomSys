package com.connor.hozon.bom.resources.util;

/**
 * Created by haozt on 2018/5/22
 */
public class ResultUtil {
    private boolean success = true;
    private String errMsg = null;
    private Object data = null;
    private Object obj = null;

    public ResultUtil() {
    }

    public ResultUtil(Object data) {
        this.data = data;
    }

    public ResultUtil(boolean success, String errMsg) {
        this.success = success;
        this.errMsg = errMsg;
    }

    public ResultUtil(boolean success, String errMsg, Object data) {
        this.success = success;
        this.errMsg = errMsg;
        this.data = data;
    }

    public ResultUtil(Object data, Object obj) {
        this.data = data;
        this.obj = obj;
    }


    public static ResultUtil result(){
        return new ResultUtil();
    }

    public static ResultUtil result(Object data){
        return new ResultUtil(data);
    }

    public static ResultUtil result(boolean success, String errMsg){
        return new ResultUtil(success,errMsg);
    }

    public static ResultUtil result(Object data, Object obj){
        return new ResultUtil(data,obj);
    }



    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
