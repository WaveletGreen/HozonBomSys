package sql.mapper.cfg.i;

import sql.pojo.cfg.HzCfg0ColorSet;

import java.util.List;

public interface HzCfg0ColorSetMapper {
    int insert(HzCfg0ColorSet record);

    List<HzCfg0ColorSet> selectAll();

    HzCfg0ColorSet selectByPrimaryId(HzCfg0ColorSet entity);

    int deleteByBatch(List<HzCfg0ColorSet> sets);

    int updateByPrimaryKey(HzCfg0ColorSet record);
}