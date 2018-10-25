package com.connor.hozon.bom.resources.domain.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/6/25
 * @Description:
 */
public class WriteResultRespDTO {

    public static final String SUCCESS_MSG ="操作成功！";
    public static final String FAILED_MSG ="操作失败,请稍后重试！";
    public static final String NOT_PRIVILEGE = "对不起，您当前没有操作权限！";
    public static final String ILLGAL_ARGUMENT = "非法参数！";
    public static final String FILE_SIZE_OVER_FLOW ="文件过大！";
    public static final String FILE_IS_NOT_EXCEL ="传入的文件格式不是excel！";
    public static final String CANT_DELETE = "不能删除基础工艺对象";
    public static final String FILE_FORMAT_ERROR="文件列信息不全,不进行导入操作！";
    public static final String FILE_ERROR = "文件格式错误！";

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


    public static boolean isSuccess(WriteResultRespDTO dto) {

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

    public static WriteResultRespDTO getSuccessResult(){
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        respDTO.setErrMsg(SUCCESS_MSG);
        respDTO.setErrCode(SUCCESS_CODE);
        return respDTO;
    }

    public static WriteResultRespDTO getFailResult(){
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        respDTO.setErrMsg(FAILED_MSG);
        respDTO.setErrCode(FAILED_CODE);
        return respDTO;
    }

    public static WriteResultRespDTO getFailPrivilege(){
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        writeResultRespDTO.setErrMsg(NOT_PRIVILEGE);
        writeResultRespDTO.setErrCode(FAILED_CODE);
        return writeResultRespDTO;
    }
    public static WriteResultRespDTO IllgalArgument(){
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        writeResultRespDTO.setErrMsg(ILLGAL_ARGUMENT);
        writeResultRespDTO.setErrCode(FAILED_CODE);
        return writeResultRespDTO;
    }

    public static WriteResultRespDTO fileSizeOverFlow(){
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        writeResultRespDTO.setErrMsg(FILE_SIZE_OVER_FLOW);
        writeResultRespDTO.setErrCode(FAILED_CODE);
        return writeResultRespDTO;
    }

    public static WriteResultRespDTO fileIsNotExcel(){
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        writeResultRespDTO.setErrMsg(FILE_IS_NOT_EXCEL);
        writeResultRespDTO.setErrCode(FAILED_CODE);
        return writeResultRespDTO;
    }

    public static WriteResultRespDTO cantDelete(){
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        writeResultRespDTO.setErrMsg(CANT_DELETE);
        writeResultRespDTO.setErrCode(FAILED_CODE);
        return writeResultRespDTO;
    }

    public static WriteResultRespDTO fileFormatError(){
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        writeResultRespDTO.setErrMsg(FILE_FORMAT_ERROR);
        writeResultRespDTO.setErrCode(FAILED_CODE);
        return writeResultRespDTO;
    }

    public static WriteResultRespDTO fileError(){
        WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
        writeResultRespDTO.setErrMsg(FILE_ERROR);
        writeResultRespDTO.setErrCode(FAILED_CODE);
        return writeResultRespDTO;
    }
}
