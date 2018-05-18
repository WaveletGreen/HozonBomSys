package sql.mapper;

import sql.pojo.HzPreferenceSetting;

public interface HzPreferenceSettingMapper {

	int deleteByPrimaryKey(Long puid);

	int insert(HzPreferenceSetting record);

	int insertSelective(HzPreferenceSetting record);

	HzPreferenceSetting selectByPrimaryKey(Long puid);

	HzPreferenceSetting selectSettingByNameWithMainRecord(HzPreferenceSetting puid);

	int updateByPrimaryKeySelective(HzPreferenceSetting record);

	int updateByPrimaryKeyWithBLOBs(HzPreferenceSetting record);

	int updateByPrimaryKey(HzPreferenceSetting record);
}