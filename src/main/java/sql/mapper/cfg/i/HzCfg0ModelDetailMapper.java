package sql.mapper.cfg.i;

import sql.pojo.cfg.HzCfg0ModelDetail;

public interface HzCfg0ModelDetailMapper {

    int insert(HzCfg0ModelDetail record);

    int update(HzCfg0ModelDetail record);

    HzCfg0ModelDetail selectByModelId(HzCfg0ModelDetail record);

}