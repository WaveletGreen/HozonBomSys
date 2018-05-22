package sql.pojo.cfg;

public class HzCfg0ModelRecord {
	private String puid;

	private String objectName;

	private String objectDesc;

	private String pCfg0ModelOfMainRecord;

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid == null ? null : puid.trim();
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectDesc() {
		return objectDesc;
	}

	public void setObjectDesc(String objectDesc) {
		this.objectDesc = objectDesc;
	}

	public String getpCfg0ModelOfMainRecord() {
		return pCfg0ModelOfMainRecord;
	}

	public void setpCfg0ModelOfMainRecord(String pCfg0ModelOfMainRecord) {
		this.pCfg0ModelOfMainRecord = pCfg0ModelOfMainRecord;
	}

}