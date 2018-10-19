package sql.pojo;

/**
 * 从TC导入BOM+配置时用的首选项，BOM系统已从TC中脱离，不再需要首选项作为每一个项目的BOM表头，因此废除
 */
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