package com.connor.hozon.bom.resources.util;

/**
 * Created by haozt on 2018/5/22
 */
public class Result {
    private boolean success = true;
    private String errMsg = null;
    private Object data = null;
    private Object obj = null;

    public Result() {
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result(boolean success, String errMsg) {
        this.success = success;
        this.errMsg = errMsg;
    }

    public Result(boolean success, String errMsg, Object data) {
        this.success = success;
        this.errMsg = errMsg;
        this.data = data;
    }

    public Result(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public static Result build(){
        return new Result();
    }

    public static Result build(Object data){
        return new Result(data);
    }

    public static Result build(boolean success, String errMsg){
        return new Result(success,errMsg);
    }


    public static Result build(boolean success, String errMsg,Object obj){
        return new Result(success,errMsg,obj);
    }

    public static Result build(boolean success,Object obj){
        return new Result(success,obj);
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
