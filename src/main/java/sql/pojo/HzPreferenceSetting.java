package sql.pojo;

public class HzPreferenceSetting {
	private String puid;

	private String settingName;

	private String bomMainRecordPuid;

	private byte[] preferencesettingblock;

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public String getSettingName() {
		return settingName;
	}

	public void setSettingName(String settingName) {
		this.settingName = settingName;
	}

	public String getBomMainRecordPuid() {
		return bomMainRecordPuid;
	}

	public void setBomMainRecordPuid(String bomMainRecordPuid) {
		this.bomMainRecordPuid = bomMainRecordPuid == null ? null : bomMainRecordPuid.trim();
	}

	public byte[] getPreferencesettingblock() {
		return preferencesettingblock;
	}

	public void setPreferencesettingblock(byte[] preferencesettingblock) {
		this.preferencesettingblock = preferencesettingblock;
	}
}