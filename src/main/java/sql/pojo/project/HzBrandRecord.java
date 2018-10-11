package sql.pojo.project;

import com.connor.hozon.bom.bomSystem.service.project.IProject;
import lombok.Data;

import java.util.Date;

/**
 * 品牌
 */
@Data
public class HzBrandRecord implements IProject{
    /**
     * puid
     */
    private String puid;
    /**
     * 品牌代号
     */
    private String pBrandCode;
    /**
     * 品牌名称
     */
    private String pBrandName;
    /**
     * 创建时间
     */
    private Date pBrandCreateDate;
    /**
     * 最后一次修改时间
     */
    private Date pBrandLastModDate;
    /**
     * 品牌备注
     */
    private String pBrandComment;
    /**
     * 最后修改人
     */
    private String pBrandLastModifier;


    @Override
    public String getCode() {
        return this.pBrandCode;
    }
}