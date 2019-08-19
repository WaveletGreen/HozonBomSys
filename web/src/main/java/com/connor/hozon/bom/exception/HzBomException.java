package com.connor.hozon.bom.exception;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;


/**
 * @Author: haozt
 * @Date: 2018/11/28
 * @Description:
 */
public class HzBomException extends RuntimeException {
    private Long errCode;

    private String errMsg;

    public HzBomException(Throwable e) {
        super(e.getMessage());
        this.errMsg = e.getMessage();
    }


    public HzBomException(String errMsg,Throwable e) {
        super(e.getMessage(),e);
        this.errMsg = e.getMessage();
    }

    public HzBomException(WriteResultRespDTO respDTO){
        this.errCode = respDTO.getErrCode();
        this.errMsg = respDTO.getErrMsg();
    }

    public HzBomException(Long errCode, String errMsg, Throwable e) {
        super(errMsg,e);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public HzBomException(Long errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public HzBomException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public Long getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

}
