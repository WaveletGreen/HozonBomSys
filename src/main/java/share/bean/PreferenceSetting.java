package share.bean;

import java.io.Serializable;

public class PreferenceSetting implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8988011687523006271L;
	/**
	 * 首选项集合
	 */
	private String[] preferences;
	/**
	 * 首选项名
	 */
	private String preferenceName;
	/** 本地化名 */
	private String[] preferenceLocal;

	public PreferenceSetting() {
	}

	public String[] getPreferences() {
		return preferences;
	}

	public void setPreferences(String[] preferences) {
		this.preferences = preferences;
	}

	public String getPreferenceName() {
		return preferenceName;
	}

	public void setPreferenceName(String preferenceName) {
		this.preferenceName = preferenceName;
	}

	public String[] getPreferenceLocal() {
		return preferenceLocal;
	}

	public void setPreferenceLocal(String[] preferenceLocal) {
		this.preferenceLocal = preferenceLocal;
	}

}
