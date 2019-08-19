package com.connor.hozon.resources.domain.constant;

/**
 * @Author: haozt
 * @Date: 2018/12/29
 * @Description: 变更相关常量
 */
public class ChangeConstants {

    public final static int DELETED = 0;//已删除
    public final static int EFFECT_STATUS = 1;//已生效
    public final static int DRAFT_STATUS = 2;//草稿状态
    public final static int DELETE_STATUS = 4;//删除状态
    public final static int VERIFY_STATUS = 5;//审核中


    public final static String MBOM_BEFORE_CHANGE ="MB";//MBOM 变更前
    public final static String MBOM_AFTER_CHANGE ="MA";//MBOM 变更后
    public final static String MBOM_CHANGE ="M";//MBOM 变更
}
