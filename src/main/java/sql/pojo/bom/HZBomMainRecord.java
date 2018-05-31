package sql.pojo.bom;

import java.util.Date;

public class HZBomMainRecord {
	private String puid;

	private Date postDate;

	private String poster;

	private String bomDigifax;
	private String bomOrgPuid;
	private String pCfg0OfWhichProject;
	private String pCfg0OfWhichProjectPuid;
	private Date pCfg0LastModDate;
	private String pCfg0OrgPoster;

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

	public String getpCfg0OfWhichProject() {
		return pCfg0OfWhichProject;
	}

	public void setpCfg0OfWhichProject(String pCfg0OfWhichProject) {
		this.pCfg0OfWhichProject = pCfg0OfWhichProject;
	}

	public String getpCfg0OfWhichProjectPuid() {
		return pCfg0OfWhichProjectPuid;
	}

	public void setpCfg0OfWhichProjectPuid(String pCfg0OfWhichProjectPuid) {
		this.pCfg0OfWhichProjectPuid = pCfg0OfWhichProjectPuid;
	}

	public Date getpCfg0LastModDate() {
		return pCfg0LastModDate;
	}

	public void setpCfg0LastModDate(Date pCfg0LastModDate) {
		this.pCfg0LastModDate = pCfg0LastModDate;
	}

	public String getpCfg0OrgPoster() {
		return pCfg0OrgPoster;
	}

	public void setpCfg0OrgPoster(String pCfg0OrgPoster) {
		this.pCfg0OrgPoster = pCfg0OrgPoster;
	}

}