package sql.pojo.bom;

public class HzBomLineRecord {
	private String puid;

	private String parentUid;

	private String bomDigifaxId;
	private String lineIndex;
	private String linePuid;
	private String lineID;
	private int isHas;
	private byte[] bomLineBlock;
	private int is2Y;
	private int isPart;
	private int orderNum;
	private String pBomOfWhichDept;
	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid == null ? null : puid.trim();
	}

	public String getParentUid() {
		return parentUid;
	}

	public void setParentUid(String parentUid) {
		this.parentUid = parentUid == null ? null : parentUid.trim();
	}

	public String getBomDigifaxId() {
		return bomDigifaxId;
	}

	public void setBomDigifaxId(String bomDigifaxId) {
		this.bomDigifaxId = bomDigifaxId == null ? null : bomDigifaxId.trim();
	}

	public byte[] getBomLineBlock() {
		return bomLineBlock;
	}

	public void setBomLineBlock(byte[] bomLineBlock) {
		this.bomLineBlock = bomLineBlock;
	}

	public String getIndex() {
		return lineIndex;
	}

	public void setIndex(String lineIndex) {
		this.lineIndex = lineIndex;
	}

	public String getLineIndex() {
		return lineIndex;
	}

	public void setLineIndex(String lineIndex) {
		this.lineIndex = lineIndex;
	}

	public int getIsHas() {
		return isHas;
	}

	public void setIsHas(int isHas) {
		this.isHas = isHas;
	}

	public void setHasChildren(boolean isHas) {
		this.setIsHas(isHas ? 1 : 0);
	}

	public String getLinePuid() {
		return linePuid;
	}

	public void setLinePuid(String linePuid) {
		this.linePuid = linePuid;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public int getIs2Y() {
		return is2Y;
	}

	public void setIs2Y(int is2y) {
		is2Y = is2y;
	}

	public int getIsPart() {
		return isPart;
	}

	public void setIsPart(int isPart) {
		this.isPart = isPart;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getpBomOfWhichDept() {
		return pBomOfWhichDept;
	}

	public void setpBomOfWhichDept(String pBomOfWhichDept) {
		this.pBomOfWhichDept = pBomOfWhichDept;
	}
}