package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/7/23
 * @Description:
 */
public class UpdateUserPasswordReqDTO extends BaseDTO {
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
