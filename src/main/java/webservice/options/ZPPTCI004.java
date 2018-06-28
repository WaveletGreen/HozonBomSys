
package webservice.options;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ZPP_TCI004 complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ZPP_TCI004">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZPACKNO" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="ZACTIONID" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="ZKNNAM" type="{urn:sap-com:document:sap:rfc:functions}char30"/>
 *         &lt;element name="ZKNKTX" type="{urn:sap-com:document:sap:rfc:functions}char30"/>
 *         &lt;element name="ZKNART" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="ZKNSTA" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="ZKNEDT" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="ZKNADT" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="ZKNNAMCODE" type="{urn:sap-com:document:sap:rfc:functions}char300"/>
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
@XmlType(name = "ZPP_TCI004", propOrder = { "zpackno", "zitem", "zactionid", "zknnam", "zknktx", "zknart", "zknsta",
		"zknedt", "zknadt", "zknnamcode", "reserve01", "reserve02", "reserve03", "reserve04", "reserve05" })
public class ZPPTCI004 {

	@XmlElement(name = "ZPACKNO", required = true)
	protected String zpackno;
	@XmlElement(name = "ZITEM", required = true)
	protected String zitem;
	@XmlElement(name = "ZACTIONID", required = true)
	protected String zactionid;
	@XmlElement(name = "ZKNNAM", required = true)
	protected String zknnam;
	@XmlElement(name = "ZKNKTX", required = true)
	protected String zknktx;
	@XmlElement(name = "ZKNART", required = true)
	protected String zknart;
	@XmlElement(name = "ZKNSTA", required = true)
	protected String zknsta;
	@XmlElement(name = "ZKNEDT", required = true)
	protected String zknedt;
	@XmlElement(name = "ZKNADT", required = true)
	protected String zknadt;
	@XmlElement(name = "ZKNNAMCODE", required = true)
	protected String zknnamcode;
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
	 * Gets the value of the zknnam property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZKNNAM() {
		return zknnam;
	}

	/**
	 * Sets the value of the zknnam property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZKNNAM(String value) {
		this.zknnam = value;
	}

	/**
	 * Gets the value of the zknktx property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZKNKTX() {
		return zknktx;
	}

	/**
	 * Sets the value of the zknktx property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZKNKTX(String value) {
		this.zknktx = value;
	}

	/**
	 * Gets the value of the zknart property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZKNART() {
		return zknart;
	}

	/**
	 * Sets the value of the zknart property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZKNART(String value) {
		this.zknart = value;
	}

	/**
	 * Gets the value of the zknsta property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZKNSTA() {
		return zknsta;
	}

	/**
	 * Sets the value of the zknsta property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZKNSTA(String value) {
		this.zknsta = value;
	}

	/**
	 * Gets the value of the zknedt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZKNEDT() {
		return zknedt;
	}

	/**
	 * Sets the value of the zknedt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZKNEDT(String value) {
		this.zknedt = value;
	}

	/**
	 * Gets the value of the zknadt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZKNADT() {
		return zknadt;
	}

	/**
	 * Sets the value of the zknadt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZKNADT(String value) {
		this.zknadt = value;
	}

	/**
	 * Gets the value of the zknnamcode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZKNNAMCODE() {
		return zknnamcode;
	}

	/**
	 * Sets the value of the zknnamcode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZKNNAMCODE(String value) {
		this.zknnamcode = value;
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
