package com.connor.hozon.bom.resources.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by haozt on 2018/5/22
 */
public class Result {
    public static ResultMessage build() {
        return new ResultMessage();
    }

    public static ResultMessage build(Object data) {
        return new ResultMessage(data);
    }
    public static MoreResultMessage build(Object data,Object externalObject ) {
        return new MoreResultMessage(data,externalObject );
    }

    public static ResultMessage build(boolean success, String errMsg) {
        return new ResultMessage(success, errMsg);
    }

    public static ResultMessage build(boolean success, int errCode,String errMsg) {
        return new ResultMessage(success, errCode,errMsg);
    }

    public static ResultMessage build(boolean success, String errMsg, Object data) {
        return new ResultMessage(success, errMsg, data);
    }

    public static class ResultMessage {

        private boolean success = true;
        private String errMsg = null;
        private int errCode = 0;
        private Object data = null;

        public ResultMessage() {

        }

        public ResultMessage(Object data) {
            this.data = data;
        }

        public ResultMessage(boolean success, String errMsg) {
            this.success = success;
            this.errMsg = errMsg;
        }

        public ResultMessage(boolean success, int errCode,String errMsg) {
            this.success = success;
            this.errMsg = errMsg;
            this.errCode = errCode;
        }

        public ResultMessage(boolean success, String errMsg, Object data) {
            this.success = success;
            this.errMsg = errMsg;
            this.data = data;
        }

        public Integer getErrCode() {
            return errCode;
        }

        public boolean isSuccess() {
            return success;
        }

        public ResultMessage setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public ResultMessage setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        public Object getData() {
            return data;
        }

        public ResultMessage setData(Object data) {
            this.data = data;
            return this;
        }

    }

    public static class MoreResultMessage {

        private boolean success = true;
        private String errMsg = null;
        private Object data = null;

        private Object externalObject = null;

        public Object getExternalObject() {
            return externalObject;
        }

        public void setExternalObject(Object externalObject) {
            this.externalObject = externalObject;
        }


        public MoreResultMessage(Object data,Object externalObject) {
            this.data = data;
            this.externalObject = externalObject;
        }

        public boolean isSuccess() {
            return success;
        }

        public MoreResultMessage setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public MoreResultMessage setErrMsg(String errMsg) {
            this.errMsg = errMsg;
            return this;
        }

        public Object getData() {
            return data;
        }

        public MoreResultMessage setData(Object data) {
            this.data = data;
            return this;
        }

    }

}
