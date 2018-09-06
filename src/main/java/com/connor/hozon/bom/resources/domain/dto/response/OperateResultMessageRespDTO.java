package com.connor.hozon.bom.resources.domain.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/6/25
 * @Description:
 */
public class OperateResultMessageRespDTO {

    public static final String SUCCESS_MSG ="操作成功！";
    public static final String FAILED_MSG ="操作失败,请稍后重试！";
    public static final String NOT_PRIVILEGE = "对不起，您当前没有操作权限！";
    public static final String ILLGAL_ARGUMENT = "非法参数！";
    public static final String FILE_SIZE_OVER_FLOW ="文件过大！";
    public static final String FILE_IS_NOT_EXCEL ="传入的文件格式不是excel！";

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

    public static OperateResultMessageRespDTO getFailPrivilege(){
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        operateResultMessageRespDTO.setErrMsg(NOT_PRIVILEGE);
        operateResultMessageRespDTO.setErrCode(FAILED_CODE);
        return operateResultMessageRespDTO;
    }
    public static OperateResultMessageRespDTO IllgalArgument(){
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        operateResultMessageRespDTO.setErrMsg(ILLGAL_ARGUMENT);
        operateResultMessageRespDTO.setErrCode(FAILED_CODE);
        return operateResultMessageRespDTO;
    }

    public static OperateResultMessageRespDTO fileSizeOverFlow(){
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        operateResultMessageRespDTO.setErrMsg(FILE_SIZE_OVER_FLOW);
        operateResultMessageRespDTO.setErrCode(FAILED_CODE);
        return operateResultMessageRespDTO;
    }

    public static OperateResultMessageRespDTO fileIsNotExcel(){
        OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
        operateResultMessageRespDTO.setErrMsg(FILE_IS_NOT_EXCEL);
        operateResultMessageRespDTO.setErrCode(FAILED_CODE);
        return operateResultMessageRespDTO;
    }
}
