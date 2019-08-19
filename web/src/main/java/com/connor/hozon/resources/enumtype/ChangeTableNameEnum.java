package com.connor.hozon.resources.enumtype;

import com.connor.hozon.resources.domain.constant.ChangeConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.method.P;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:设变相关的数据库表名
 */
public enum  ChangeTableNameEnum {
    /**
     * EBOM
     */
    HZ_EBOM_AFTER("HZ_EBOM_REOCRD_AFTER_CHANGE"),
    HZ_EBOM("HZ_BOM_LINE_RECORD"),
    HZ_EBOM_BEFORE("HZ_EBOM_REOCRD_BEFORE_CHANGE"),

    /**
     * PBOM
     */
    HZ_PBOM("HZ_PBOM_RECORD"),
    HZ_PBOM_AFTER("HZ_PBOM_REOCRD_AFTER_CHANGE"),
    HZ_PBOM_BEFORE("HZ_PBOM_REOCRD_BEFORE_CHANGE"),

    /**
     * MBOM
     */
    HZ_MBOM_AFTER("HZ_MBOM_REOCRD_AFTER_CHANGE"),
    HZ_MBOM("HZ_MBOM_RECORD"),
    HZ_MBOM_BEFORE("HZ_MBOM_REOCRD_BEFORE_CHANGE"),

    /**
     * MBOM -->白车身财务
     */
    HZ_MBOM_FINANCE("HZ_MBOM_OF_FINANCE"),
    HZ_MBOM_FINANCE_AFTER("HZ_MBOM_FINANCE_AFTER_CHANGE"),
    HZ_MBOM_FINANCE_BRFORE("HZ_MBOM_FINANCE_BEFORE_CHANGE"),

    /**
     * MBOM-->白车身生产
     */
    HZ_MBOM_PRODUCT("HZ_MBOM_OF_PRODUCT"),
    HZ_MBOM_PRODUCT_AFTER("HZ_MBOM_PRODUCT_AFTER_CHANGE"),
    HZ_MBOM_PRODUCT_BEFORE("HZ_MBOM_PRODUCT_BEFORE_CHANGE"),


    /**
     * 物料
     */
    HZ_MATERIEL("HZ_MATERIEL_RECORD"),
    HZ_MATERIEL_BEFORE("HZ_MATERIEL_BEFORE_CHANGE"),
    HZ_MATERIEL_AFTER("HZ_MATERIEL_AFTER_CHANGE"),

    /**
     * 工艺路线
     */
    HZ_WORK_PROCEDURE("HZ_WORK_PROCEDURE"),
    HZ_WORK_PROCEDURE_AFTER("HZ_WORK_PROCEDURE_AFTER"),
    HZ_WORK_PROCEDURE_BEFORE("HZ_WORK_PROCEDURE_BEFORE"),


    //特性变更表
    HZ_CFG0_AFTER_CHANGE_RECORD("HZ_CFG0_AFTER_CHANGE_RECORD"),

    //配色方案变更表
    HZ_CMCR_AFTER_CHANGE("HZ_CMCR_AFTER_CHANGE"),

    //衍生物料变更表
    HZ_DM_BASIC_CHANGE("HZ_DM_BASIC_CHANGE"),

    //全配置变更表
    HZ_FULL_CFG_MAIN_RECORD_CHANGE("HZ_FULL_CFG_MAIN_RECORD_CHANGE"),

    //相关性变更表单
    HZ_RELEVANCE_BASIC_CHANGE("HZ_RELEVANCE_BASIC_CHANGE"),

    ;

    /**
     * 数据库表名
     */
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    ChangeTableNameEnum(String tableName){
        this.tableName = tableName;
    }

    /**
     * 获取MBOM变更相关表名
     * @param type
     * @param str M MA MB
     * @return
     */
    public static String getMbomTableName(Integer type,String str){

        if(null == type){
            if(ChangeConstants.MBOM_CHANGE.equals(str)){
                return ChangeTableNameEnum.HZ_MBOM.tableName;
            }else if(ChangeConstants.MBOM_AFTER_CHANGE.equals(str)){
                return ChangeTableNameEnum.HZ_MBOM_AFTER.tableName;
            }else if(ChangeConstants.MBOM_BEFORE_CHANGE.equals(str)){
                return ChangeTableNameEnum.HZ_MBOM_BEFORE.tableName;
            }else {
                return null;
            }
        }

        if(ChangeConstants.MBOM_CHANGE.equals(str)){
            switch (type){
                case 1:return ChangeTableNameEnum.HZ_MBOM_PRODUCT.tableName;
                case 6:return ChangeTableNameEnum.HZ_MBOM_FINANCE.tableName;
                default:return ChangeTableNameEnum.HZ_MBOM.tableName;
            }
        }else if(ChangeConstants.MBOM_AFTER_CHANGE.equals(str)){
            switch (type){
                case 1:return ChangeTableNameEnum.HZ_MBOM_PRODUCT_AFTER.tableName;
                case 6:return ChangeTableNameEnum.HZ_MBOM_FINANCE_AFTER.tableName;
                default:return ChangeTableNameEnum.HZ_MBOM_AFTER.tableName;
            }
        }else if(ChangeConstants.MBOM_BEFORE_CHANGE.equals(str)){
            switch (type){
                case 1:return ChangeTableNameEnum.HZ_MBOM_PRODUCT_BEFORE.tableName;
                case 6:return ChangeTableNameEnum.HZ_MBOM_FINANCE_BRFORE.tableName;
                default:return ChangeTableNameEnum.HZ_MBOM_BEFORE.tableName;
            }
        }else {
            return null;
        }
    }


    /**
     * 获取变更前表名/当前页面显示数据表
     * @param tableName 变更后的表名
     * @param type 类型 传B或者null B表示获取变更前表
     * @desc 根据变更数据库表名 获取和它相同结构的表名
     *        如：HZ_EBOM_REOCRD_AFTER_CHANGE 可以获取HZ_BOM_LINE_RECORD/HZ_EBOM_REOCRD_BEFORE_CHANGE
     * @return
     */
    public static String getTableName(String tableName,String type) {
        if(StringUtils.isBlank(tableName)){
            return null;
        }
        if(StringUtils.isBlank(type)){
          if(ChangeTableNameEnum.HZ_EBOM_AFTER.tableName.equals(tableName)){
              return ChangeTableNameEnum.HZ_EBOM.tableName;
          }else if(ChangeTableNameEnum.HZ_PBOM_AFTER.tableName.equals(tableName)){
              return ChangeTableNameEnum.HZ_PBOM.tableName;
          }else if(ChangeTableNameEnum.HZ_MBOM_AFTER.tableName.equals(tableName)){
              return ChangeTableNameEnum.HZ_MBOM.tableName;
          }else if(ChangeTableNameEnum.HZ_MBOM_FINANCE_AFTER.tableName.equals(tableName)){
              return ChangeTableNameEnum.HZ_MBOM_FINANCE.tableName;
          }else if(ChangeTableNameEnum.HZ_MBOM_PRODUCT_AFTER.tableName.equals(tableName)){
              return ChangeTableNameEnum.HZ_MBOM_PRODUCT.tableName;
          }else if(ChangeTableNameEnum.HZ_MATERIEL_AFTER.tableName.equals(tableName)){
              return ChangeTableNameEnum.HZ_MATERIEL.tableName;
          }else if(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.tableName.equals(tableName)){
              return ChangeTableNameEnum.HZ_WORK_PROCEDURE.tableName;
          }
        }else {
            if(ChangeTableNameEnum.HZ_EBOM_AFTER.tableName.equals(tableName)){
                return ChangeTableNameEnum.HZ_EBOM_BEFORE.tableName;
            }else if(ChangeTableNameEnum.HZ_PBOM_AFTER.tableName.equals(tableName)){
                return ChangeTableNameEnum.HZ_PBOM_BEFORE.tableName;
            }else if(ChangeTableNameEnum.HZ_MBOM_AFTER.tableName.equals(tableName)){
                return ChangeTableNameEnum.HZ_MBOM_BEFORE.tableName;
            }else if(ChangeTableNameEnum.HZ_MBOM_FINANCE_AFTER.tableName.equals(tableName)){
                return ChangeTableNameEnum.HZ_MBOM_FINANCE_BRFORE.tableName;
            }else if(ChangeTableNameEnum.HZ_MBOM_PRODUCT_AFTER.tableName.equals(tableName)){
                return ChangeTableNameEnum.HZ_MBOM_PRODUCT_BEFORE.tableName;
            }else if(ChangeTableNameEnum.HZ_MATERIEL_AFTER.tableName.equals(tableName)){
                return ChangeTableNameEnum.HZ_MATERIEL_BEFORE.tableName;
            }else if(ChangeTableNameEnum.HZ_WORK_PROCEDURE_AFTER.tableName.equals(tableName)){
                return ChangeTableNameEnum.HZ_WORK_PROCEDURE_BEFORE.tableName;
            }
        }
        return null;
    }
}
