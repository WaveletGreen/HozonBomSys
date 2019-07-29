package sql.pojo.interaction;

import lombok.Data;
import sql.pojo.accessories.HzAccessoriesLibs;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/19 12:10
 * @Modified By: 配置+2Y+颜色对应的bean，超级BOM计算用
 */

@Data
public class HzConfigBomColorBean implements Serializable {
    private static final long serialVersionUID = 4184226510690323042L;
    /**
     * 2Y层UID
     */
    private String bomLineUid;
    /**
     * 颜色代码
     */
    private String colorCode;

    /**
     * 颜色UID
     */
    private String colorUid;
    /**
     * 颜色名称
     */
    private String colorName;
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

    /**
     * 油漆物料集合 @Author haozt
     */
    private String materielCodes;

    /**
     * 油漆物料和油漆物料名称 @Author haozt
     */
    private List<HzAccessoriesLibs> materielList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HzConfigBomColorBean bean = (HzConfigBomColorBean) o;
        return Objects.equals(colorCode, bean.colorCode) &&
                Objects.equals(colorUid, bean.colorUid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(colorCode, colorUid);
    }
}
