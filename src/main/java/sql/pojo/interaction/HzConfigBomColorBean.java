package sql.pojo.interaction;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/19 12:10
 * @Modified By:
 */

@Data
public class HzConfigBomColorBean {
    /**
     * 2Y层UID
     */
    private String bomLineUid;
    /**
     * 颜色代码
     */
    private String colorCode;
    /**
     * 配色模型UID
     */
    private String colorModelUid;
    /**
     * 配色模型描述
     */
    private String colorModelDesc;

    /**
     * 特性值代码
     */
    private String featureValueCode;
    /**
     * 特性代码
     */
    private String featureCode;

}
