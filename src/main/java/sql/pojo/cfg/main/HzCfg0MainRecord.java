/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.main;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 配置管理主数据，即主配置对象
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
public class HzCfg0MainRecord {
    private String puid;

    private String pItemName;

    private Date postDate;

    private Object poster;

    private String pCfg0OrgPuid;

    private String pCfg0OfWhichProject;

    private String pCfg0OfWhichProjectPuid;

    private Date pCfg0LastModDate;

    private Object pCfg0OrgPoster;

    @Getter
    @Setter
    private Integer featureSynDicFlag;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpItemName() {
        return pItemName;
    }

    public void setpItemName(String pItemName) {
        this.pItemName = pItemName == null ? null : pItemName.trim();
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Object getPoster() {
        return poster;
    }

    public void setPoster(Object poster) {
        this.poster = poster;
    }

    public String getpCfg0OrgPuid() {
        return pCfg0OrgPuid;
    }

    public void setpCfg0OrgPuid(String pCfg0OrgPuid) {
        this.pCfg0OrgPuid = pCfg0OrgPuid == null ? null : pCfg0OrgPuid.trim();
    }

    public String getpCfg0OfWhichProject() {
        return pCfg0OfWhichProject;
    }

    public void setpCfg0OfWhichProject(String pCfg0OfWhichProject) {
        this.pCfg0OfWhichProject = pCfg0OfWhichProject == null ? null : pCfg0OfWhichProject.trim();
    }

    public String getpCfg0OfWhichProjectPuid() {
        return pCfg0OfWhichProjectPuid;
    }

    public void setpCfg0OfWhichProjectPuid(String pCfg0OfWhichProjectPuid) {
        this.pCfg0OfWhichProjectPuid = pCfg0OfWhichProjectPuid == null ? null : pCfg0OfWhichProjectPuid.trim();
    }

    public Date getpCfg0LastModDate() {
        return pCfg0LastModDate;
    }

    public void setpCfg0LastModDate(Date pCfg0LastModDate) {
        this.pCfg0LastModDate = pCfg0LastModDate;
    }

    public Object getpCfg0OrgPoster() {
        return pCfg0OrgPoster;
    }

    public void setpCfg0OrgPoster(Object pCfg0OrgPoster) {
        this.pCfg0OrgPoster = pCfg0OrgPoster;
    }
}