package sql.mapper.cfg.i;

import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzCfg0BomLineOfModel;

import java.util.List;

public interface HzCfg0BomLineOfModelMapper {
	List<HzCfg0BomLineOfModel> selectByModelMainId(@Param("modelId") String modelId);
}