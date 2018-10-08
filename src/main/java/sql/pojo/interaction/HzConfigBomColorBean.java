package sql.pojo.interaction;

import lombok.Data;

import java.util.Objects;

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
     * 颜色UID
     */
    private String colorUid;
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
