
package webservice.base.bom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ZPP_TCI005 complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ZPP_TCI005">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZPACKNO" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="ZACTIONID" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="ZAENNR" type="{urn:sap-com:document:sap:rfc:functions}char12"/>
 *         &lt;element name="ZAETXT" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZPCNNO" type="{urn:sap-com:document:sap:rfc:functions}char72"/>
 *         &lt;element name="ZWERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="ZUSE" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="ZMATNR" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZBASEQ" type="{urn:sap-com:document:sap:rfc:functions}char23"/>
 *         &lt;element name="ZSORTF" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="ZMATNR_C" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZMENGE" type="{urn:sap-com:document:sap:rfc:functions}char23"/>
 *         &lt;element name="ZMEINS" type="{urn:sap-com:document:sap:rfc:functions}unit3"/>
 *         &lt;element name="ZLOCATION" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="ZKNBLK" type="{urn:sap-com:document:sap:rfc:functions}char30"/>
 *         &lt;element name="ZSUBPR" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="ZZPWZ" type="{urn:sap-com:document:sap:rfc:functions}char8"/>
 *         &lt;element name="ZWORKS" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="ZSTATION" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="ZGUID" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="RESERVE01" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *         &lt;element name="RESERVE02" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *         &lt;element name="RESERVE03" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *         &lt;element name="RESERVE04" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *         &lt;element name="RESERVE05" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZPP_TCI005", propOrder = { "zpackno", "zitem", "zactionid", "zaennr", "zaetxt", "zpcnno", "zwerks",
		"zuse", "zmatnr", "zbaseq", "zsortf", "zmatnrc", "zmenge", "zmeins", "zlocation", "zknblk", "zsubpr", "zzpwz",
		"zworks", "zstation", "zguid", "reserve01", "reserve02", "reserve03", "reserve04", "reserve05" })
public class ZPPTCI005 {

	@XmlElement(name = "ZPACKNO", required = true)
	protected String zpackno;
	@XmlElement(name = "ZITEM", required = true)
	protected String zitem;
	@XmlElement(name = "ZACTIONID", required = true)
	protected String zactionid;
	@XmlElement(name = "ZAENNR", required = true)
	protected String zaennr;
	@XmlElement(name = "ZAETXT", required = true)
	protected String zaetxt;
	@XmlElement(name = "ZPCNNO", required = true)
	protected String zpcnno;
	@XmlElement(name = "ZWERKS", required = true)
	protected String zwerks;
	@XmlElement(name = "ZUSE", required = true)
	protected String zuse;
	@XmlElement(name = "ZMATNR", required = true)
	protected String zmatnr;
	@XmlElement(name = "ZBASEQ", required = true)
	protected String zbaseq;
	@XmlElement(name = "ZSORTF", required = true)
	protected String zsortf;
	@XmlElement(name = "ZMATNR_C", required = true)
	protected String zmatnrc;
	@XmlElement(name = "ZMENGE", required = true)
	protected String zmenge;
	@XmlElement(name = "ZMEINS", required = true)
	protected String zmeins;
	@XmlElement(name = "ZLOCATION", required = true)
	protected String zlocation;
	@XmlElement(name = "ZKNBLK", required = true)
	protected String zknblk;
	@XmlElement(name = "ZSUBPR", required = true)
	protected String zsubpr;
	@XmlElement(name = "ZZPWZ", required = true)
	protected String zzpwz;
	@XmlElement(name = "ZWORKS", required = true)
	protected String zworks;
	@XmlElement(name = "ZSTATION", required = true)
	protected String zstation;
	@XmlElement(name = "ZGUID", required = true)
	protected String zguid;
	@XmlElement(name = "RESERVE01", required = true)
	protected String reserve01;
	@XmlElement(name = "RESERVE02", required = true)
	protected String reserve02;
	@XmlElement(name = "RESERVE03", required = true)
	protected String reserve03;
	@XmlElement(name = "RESERVE04", required = true)
	protected String reserve04;
	@XmlElement(name = "RESERVE05", required = true)
	protected String reserve05;

	/**
	 * Gets the value of the zpackno property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZPACKNO() {
		return zpackno;
	}

	/**
	 * Sets the value of the zpackno property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZPACKNO(String value) {
		this.zpackno = value;
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
	 * Gets the value of the zaennr property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZAENNR() {
		return zaennr;
	}

	/**
	 * Sets the value of the zaennr property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZAENNR(String value) {
		this.zaennr = value;
	}

	/**
	 * Gets the value of the zaetxt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZAETXT() {
		return zaetxt;
	}

	/**
	 * Sets the value of the zaetxt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZAETXT(String value) {
		this.zaetxt = value;
	}

	/**
	 * Gets the value of the zpcnno property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZPCNNO() {
		return zpcnno;
	}

	/**
	 * Sets the value of the zpcnno property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZPCNNO(String value) {
		this.zpcnno = value;
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
	 * Gets the value of the zuse property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZUSE() {
		return zuse;
	}

	/**
	 * Sets the value of the zuse property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZUSE(String value) {
		this.zuse = value;
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
	 * Gets the value of the zbaseq property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZBASEQ() {
		return zbaseq;
	}

	/**
	 * Sets the value of the zbaseq property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZBASEQ(String value) {
		this.zbaseq = value;
	}

	/**
	 * Gets the value of the zsortf property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZSORTF() {
		return zsortf;
	}

	/**
	 * Sets the value of the zsortf property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZSORTF(String value) {
		this.zsortf = value;
	}

	/**
	 * Gets the value of the zmatnrc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZMATNRC() {
		return zmatnrc;
	}

	/**
	 * Sets the value of the zmatnrc property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZMATNRC(String value) {
		this.zmatnrc = value;
	}

	/**
	 * Gets the value of the zmenge property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZMENGE() {
		return zmenge;
	}

	/**
	 * Sets the value of the zmenge property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZMENGE(String value) {
		this.zmenge = value;
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
	 * Gets the value of the zlocation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZLOCATION() {
		return zlocation;
	}

	/**
	 * Sets the value of the zlocation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZLOCATION(String value) {
		this.zlocation = value;
	}

	/**
	 * Gets the value of the zknblk property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZKNBLK() {
		return zknblk;
	}

	/**
	 * Sets the value of the zknblk property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZKNBLK(String value) {
		this.zknblk = value;
	}

	/**
	 * Gets the value of the zsubpr property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZSUBPR() {
		return zsubpr;
	}

	/**
	 * Sets the value of the zsubpr property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZSUBPR(String value) {
		this.zsubpr = value;
	}

	/**
	 * Gets the value of the zzpwz property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZZPWZ() {
		return zzpwz;
	}

	/**
	 * Sets the value of the zzpwz property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZZPWZ(String value) {
		this.zzpwz = value;
	}

	/**
	 * Gets the value of the zworks property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZWORKS() {
		return zworks;
	}

	/**
	 * Sets the value of the zworks property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZWORKS(String value) {
		this.zworks = value;
	}

	/**
	 * Gets the value of the zstation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZSTATION() {
		return zstation;
	}

	/**
	 * Sets the value of the zstation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZSTATION(String value) {
		this.zstation = value;
	}

	/**
	 * Gets the value of the zguid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZGUID() {
		return zguid;
	}

	/**
	 * Sets the value of the zguid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZGUID(String value) {
		this.zguid = value;
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
