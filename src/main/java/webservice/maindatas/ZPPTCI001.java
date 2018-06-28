
package webservice.maindatas;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 具体的输入参数java bean
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZPP_TCI001", propOrder = { "guid", "zitem", "zwerks", "zactionid", "zmatnr", "zbismt", "zmaktx",
		"zmaktxe", "zmeins", "zmtart", "zmatkl", "zbeskz", "zvin", "zcolor", "zbrgew", "zlabel", "zrules", "zimpot",
		"zdelete", "zmrpc", "zsjbs", "reserve01", "reserve02", "reserve03", "reserve04", "reserve05" })
public class ZPPTCI001 {
    /**
     * 数据包号
	 */
	@XmlElement(name = "GUID", required = true)
	protected String guid;
	/**
	 * 行号
	 */
	@XmlElement(name = "ZITEM", required = true)
	protected String zitem;
	/**
	 * 工厂
	 */
	@XmlElement(name = "ZWERKS", required = true)
	protected String zwerks;
	/**
	 * 动作描述代码
	 */
	@XmlElement(name = "ZACTIONID", required = true)
	protected String zactionid;
	/**
	 * 物料编码
	 */
	@XmlElement(name = "ZMATNR", required = true)
	protected String zmatnr;
	/**
	 * 旧物料编码
	 */
	@XmlElement(name = "ZBISMT", required = true)
	protected String zbismt;
	/**
	 * 物料中文描述
	 */
	@XmlElement(name = "ZMAKTX", required = true)
	protected String zmaktx;
	/**
	 * 物料英文描述
	 */
	@XmlElement(name = "ZMAKTX_E", required = true)
	protected String zmaktxe;
	/**
	 * 基本计量单位
	 */
	@XmlElement(name = "ZMEINS", required = true)
	protected String zmeins;
	/**
	 * 物料类型
	 */
	@XmlElement(name = "ZMTART", required = true)
	protected String zmtart;
	/**
	 * 虚拟件标示
	 */
	@XmlElement(name = "ZMATKL", required = true)
	protected String zmatkl;
	/**
	 * 采购类型
	 */
	@XmlElement(name = "ZBESKZ", required = true)
	protected String zbeskz;
	/**
	 * VIN前置号
	 */
	@XmlElement(name = "ZVIN", required = true)
	protected String zvin;
	/**
	 * 颜色件标识
	 */
	@XmlElement(name = "ZCOLOR", required = true)
	protected String zcolor;
	/**
	 * 毛重
	 */
	@XmlElement(name = "ZBRGEW", required = true)
	protected String zbrgew;
	/**
	 * 内外饰标识
	 */
	@XmlElement(name = "ZLABEL", required = true)
	protected String zlabel;
	/**
	 * 3C件标识
	 */
	@XmlElement(name = "ZRULES", required = true)
	protected String zrules;
	/**
	 * 零件重要度
	 */
	@XmlElement(name = "ZIMPOT", required = true)
	protected String zimpot;
	/**
	 * 删除标示
	 */
	@XmlElement(name = "ZDELETE", required = true)
	protected String zdelete;
	/**
	 * MRP控制者
	 */
	@XmlElement(name = "ZMRPC", required = true)
	protected String zmrpc;
	/**
	 * 散件标示
	 */
	@XmlElement(name = "ZSJBS", required = true)
	protected String zsjbs;
	/**
	 * 备件+原材料双属性标示
	 */
	@XmlElement(name = "RESERVE01", required = true)
	protected String reserve01;
	/**
	 * 预留字段
	 */
	@XmlElement(name = "RESERVE02", required = true)
	protected String reserve02;
	/**
	 * 预留字段
	 */
	@XmlElement(name = "RESERVE03", required = true)
	protected String reserve03;
	/**
	 * 预留字段
	 */
	@XmlElement(name = "RESERVE04", required = true)
	protected String reserve04;
	/**
	 * 预留字段
	 */
	@XmlElement(name = "RESERVE05", required = true)
	protected String reserve05;

	/**
	 * Gets the value of the guid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getGUID() {
		return guid;
	}

	/**
	 * Sets the value of the guid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setGUID(String value) {
		this.guid = value;
	}

	/**
	 * Gets the value of the zitem property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZITEM() {
		return zitem;
	}

	/**
	 * Sets the value of the zitem property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZITEM(String value) {
		this.zitem = value;
	}

	/**
	 * Gets the value of the zwerks property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZWERKS() {
		return zwerks;
	}

	/**
	 * Sets the value of the zwerks property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZWERKS(String value) {
		this.zwerks = value;
	}

	/**
	 * Gets the value of the zactionid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZACTIONID() {
		return zactionid;
	}

	/**
	 * Sets the value of the zactionid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZACTIONID(String value) {
		this.zactionid = value;
	}

	/**
	 * Gets the value of the zmatnr property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZMATNR() {
		return zmatnr;
	}

	/**
	 * Sets the value of the zmatnr property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZMATNR(String value) {
		this.zmatnr = value;
	}

	/**
	 * Gets the value of the zbismt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZBISMT() {
		return zbismt;
	}

	/**
	 * Sets the value of the zbismt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZBISMT(String value) {
		this.zbismt = value;
	}

	/**
	 * Gets the value of the zmaktx property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZMAKTX() {
		return zmaktx;
	}

	/**
	 * Sets the value of the zmaktx property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZMAKTX(String value) {
		this.zmaktx = value;
	}

	/**
	 * Gets the value of the zmaktxe property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZMAKTXE() {
		return zmaktxe;
	}

	/**
	 * Sets the value of the zmaktxe property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZMAKTXE(String value) {
		this.zmaktxe = value;
	}

	/**
	 * Gets the value of the zmeins property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZMEINS() {
		return zmeins;
	}

	/**
	 * Sets the value of the zmeins property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZMEINS(String value) {
		this.zmeins = value;
	}

	/**
	 * Gets the value of the zmtart property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZMTART() {
		return zmtart;
	}

	/**
	 * Sets the value of the zmtart property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZMTART(String value) {
		this.zmtart = value;
	}

	/**
	 * Gets the value of the zmatkl property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZMATKL() {
		return zmatkl;
	}

	/**
	 * Sets the value of the zmatkl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZMATKL(String value) {
		this.zmatkl = value;
	}

	/**
	 * Gets the value of the zbeskz property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZBESKZ() {
		return zbeskz;
	}

	/**
	 * Sets the value of the zbeskz property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZBESKZ(String value) {
		this.zbeskz = value;
	}

	/**
	 * Gets the value of the zvin property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZVIN() {
		return zvin;
	}

	/**
	 * Sets the value of the zvin property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZVIN(String value) {
		this.zvin = value;
	}

	/**
	 * Gets the value of the zcolor property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZCOLOR() {
		return zcolor;
	}

	/**
	 * Sets the value of the zcolor property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZCOLOR(String value) {
		this.zcolor = value;
	}

	/**
	 * Gets the value of the zbrgew property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZBRGEW() {
		return zbrgew;
	}

	/**
	 * Sets the value of the zbrgew property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZBRGEW(String value) {
		this.zbrgew = value;
	}

	/**
	 * Gets the value of the zlabel property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZLABEL() {
		return zlabel;
	}

	/**
	 * Sets the value of the zlabel property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZLABEL(String value) {
		this.zlabel = value;
	}

	/**
	 * Gets the value of the zrules property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZRULES() {
		return zrules;
	}

	/**
	 * Sets the value of the zrules property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZRULES(String value) {
		this.zrules = value;
	}

	/**
	 * Gets the value of the zimpot property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZIMPOT() {
		return zimpot;
	}

	/**
	 * Sets the value of the zimpot property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZIMPOT(String value) {
		this.zimpot = value;
	}

	/**
	 * Gets the value of the zdelete property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZDELETE() {
		return zdelete;
	}

	/**
	 * Sets the value of the zdelete property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZDELETE(String value) {
		this.zdelete = value;
	}

	/**
	 * Gets the value of the zmrpc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZMRPC() {
		return zmrpc;
	}

	/**
	 * Sets the value of the zmrpc property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZMRPC(String value) {
		this.zmrpc = value;
	}

	/**
	 * Gets the value of the zsjbs property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZSJBS() {
		return zsjbs;
	}

	/**
	 * Sets the value of the zsjbs property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZSJBS(String value) {
		this.zsjbs = value;
	}

	/**
	 * Gets the value of the reserve01 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRESERVE01() {
		return reserve01;
	}

	/**
	 * Sets the value of the reserve01 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRESERVE01(String value) {
		this.reserve01 = value;
	}

	/**
	 * Gets the value of the reserve02 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRESERVE02() {
		return reserve02;
	}

	/**
	 * Sets the value of the reserve02 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRESERVE02(String value) {
		this.reserve02 = value;
	}

	/**
	 * Gets the value of the reserve03 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRESERVE03() {
		return reserve03;
	}

	/**
	 * Sets the value of the reserve03 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRESERVE03(String value) {
		this.reserve03 = value;
	}

	/**
	 * Gets the value of the reserve04 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRESERVE04() {
		return reserve04;
	}

	/**
	 * Sets the value of the reserve04 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRESERVE04(String value) {
		this.reserve04 = value;
	}

	/**
	 * Gets the value of the reserve05 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRESERVE05() {
		return reserve05;
	}

	/**
	 * Sets the value of the reserve05 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRESERVE05(String value) {
		this.reserve05 = value;
	}

}
