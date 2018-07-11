package com.connor.hozon.bom.resources.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/6/25
 * @Description:
 */
public class OperateResultMessageRespDTO {

    public static final String SUCCESS_MSG ="操作成功！";
    public static final String FAILED_MSG ="操作失败,请稍后重试！";
    public static final Long SUCCESS_CODE = 1000L;
    public static final Long FAILED_CODE = 1001L;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 错误代码
     */
    private Long errCode;

    /**
     * 其他参数
     */
    private String otherParam;

    public static boolean isSuccess(OperateResultMessageRespDTO dto) {

        if(dto != null && Long.valueOf(1000L).equals(dto.getErrCode())) {

            return true;
        }
        return false;
    }

    public  String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Long getErrCode() {
        return errCode;
    }

    public void setErrCode(Long errCode) {
        this.errCode = errCode;
    }

    public String getOtherParam() {
        return otherParam;
    }

    public void setOtherParam(String otherParam) {
        this.otherParam = otherParam;
    }

    public static OperateResultMessageRespDTO getSuccessResult(){
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        respDTO.setErrMsg(SUCCESS_MSG);
        respDTO.setErrCode(SUCCESS_CODE);
        return respDTO;
    }

    public static OperateResultMessageRespDTO getFailResult(){
        OperateResultMessageRespDTO respDTO = new OperateResultMessageRespDTO();
        respDTO.setErrMsg(FAILED_MSG);
        respDTO.setErrCode(FAILED_CODE);
        return respDTO;
    }
}
