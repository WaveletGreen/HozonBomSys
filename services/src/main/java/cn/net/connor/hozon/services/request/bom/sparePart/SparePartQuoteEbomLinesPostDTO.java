package cn.net.connor.hozon.services.request.bom.sparePart;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/20 14:10
 * @Modified By:
 */
@Data
public class SparePartQuoteEbomLinesPostDTO implements Serializable {
    private static final long serialVersionUID = 3137014969775931910L;
    /**
     * EBOM的主键
     */
    private List<String> ids;
    /**
     * 项目ID
     */
    private String projectId;
}
