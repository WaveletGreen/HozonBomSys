package com.connor.hozon.bom.resources.dto.request;

/**
 * @Author: haozt
 * @Date: 2018/7/23
 * @Description:
 */
public class UpdateUserPasswordReqDTO {
    private String oldPassWord;

    private String newPassWord;

    public String getOldPassWord() {
        return oldPassWord;
    }

    public void setOldPassWord(String oldPassWord) {
        this.oldPassWord = oldPassWord;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }
}
