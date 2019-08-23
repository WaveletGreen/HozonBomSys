package cn.net.connor.hozon.services.response;

import cn.net.connor.hozon.services.response.bom.sparePart.SparePartDataResponseDTO;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.List;

/**
 * 基础的分页查询结果
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/21 9:48
 * @Modified By:
 */
@Data
public class BasePageResponseDTO<T> implements Serializable {
    /**
     * 查询满足结果的总数
     */
    protected int totalCount;
    /**
     * 查询结果
     */
    protected List<T> result;
}
