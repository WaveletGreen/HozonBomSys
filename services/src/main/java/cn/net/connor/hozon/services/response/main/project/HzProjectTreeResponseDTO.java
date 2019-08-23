/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.response.main.project;

import java.io.Serializable;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 项目树结构构建对象
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public class HzProjectTreeResponseDTO implements Serializable {
    private static final long serialVersionUID = -4752146749796440330L;
    private String puid;
    private String pPuid;
    private String name;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpPuid() {
        return pPuid;
    }

    public void setpPuid(String pPuid) {
        this.pPuid = pPuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
