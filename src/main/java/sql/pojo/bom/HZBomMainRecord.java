package sql.pojo.bom;

import java.util.Date;

public class HZBomMainRecord {
	private String puid;

	private Date postDate;

	private String poster;

	private String bomDigifax;
	private String bomOrgPuid;

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid == null ? null : puid.trim();
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getBomDigifax() {
		return bomDigifax;
	}

	public void setBomDigifax(String bomDigifax) {
		this.bomDigifax = bomDigifax;
	}

	public String getBomOrgPuid() {
		return bomOrgPuid;
	}

	public void setBomOrgPuid(String bomOrgPuid) {
		this.bomOrgPuid = bomOrgPuid;
	}

}