package com.connor.hozon.bom.resources.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/6/25
 * @Description:
 */
public class OperateResultMessageRespDTO {
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 错误代码
     */
    private Long errCode;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
