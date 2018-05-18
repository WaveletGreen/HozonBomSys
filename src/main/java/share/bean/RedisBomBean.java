package share.bean;

import java.io.Serializable;
import java.util.List;

public class RedisBomBean implements Serializable {
	private static final long serialVersionUID = -3076449422968032318L;
	/** PUID */
	private String uid;
	/** parent's PUID */
	private String parentUid;
	private boolean hasChildren;
	private String index;
	// private Map<String, String> comProperties;
	private List<String> pSets;
	private List<String> pValues;

	public RedisBomBean() {
		super();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getParentUid() {
		return parentUid;
	}

	public void setParentUid(String parentUid) {
		this.parentUid = parentUid;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public List<String> getpSets() {
		return pSets;
	}

	public void setpSets(List<String> pSets) {
		this.pSets = pSets;
	}

	public List<String> getpValues() {
		return pValues;
	}

	public void setpValues(List<String> pValues) {
		this.pValues = pValues;
	}

	// public Map<String, String> getComProperties() {
	// return comProperties;
	// }
	//
	// public void setComProperties(Map<String, String> comProperties) {
	// this.comProperties = comProperties;
	// }

}
