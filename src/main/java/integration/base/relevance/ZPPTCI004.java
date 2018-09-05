
package integration.base.relevance;

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
 *         &lt;element name="P_PACKNO" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_ITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="P_ACTIONID" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_KNNAM" type="{urn:sap-com:document:sap:rfc:functions}char30"/>
 *         &lt;element name="P_KNKTX" type="{urn:sap-com:document:sap:rfc:functions}char30"/>
 *         &lt;element name="P_KNART" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="P_KNSTA" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_KNEDT" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="P_KNADT" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="P_KNNAMCODE" type="{urn:sap-com:document:sap:rfc:functions}char300"/>
 *         &lt;element name="P_RESERVE1" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *         &lt;element name="P_RESERVE2" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *         &lt;element name="P_RESERVE3" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *         &lt;element name="P_RESERVE4" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *         &lt;element name="P_RESERVE5" type="{urn:sap-com:document:sap:rfc:functions}char50"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZPP_TCI004", propOrder = { "ppackno", "pitem", "pactionid", "pknnam", "pknktx", "pknart", "pknsta",
		"pknedt", "pknadt", "pknnamcode", "preserve1", "preserve2", "preserve3", "preserve4", "preserve5" })
public class ZPPTCI004 {

	@XmlElement(name = "P_PACKNO", required = true)
	protected String ppackno;
	@XmlElement(name = "P_ITEM", required = true)
	protected String pitem;
	@XmlElement(name = "P_ACTIONID", required = true)
	protected String pactionid;
	@XmlElement(name = "P_KNNAM", required = true)
	protected String pknnam;
	@XmlElement(name = "P_KNKTX", required = true)
	protected String pknktx;
	@XmlElement(name = "P_KNART", required = true)
	protected String pknart;
	@XmlElement(name = "P_KNSTA", required = true)
	protected String pknsta;
	@XmlElement(name = "P_KNEDT", required = true)
	protected String pknedt;
	@XmlElement(name = "P_KNADT", required = true)
	protected String pknadt;
	@XmlElement(name = "P_KNNAMCODE", required = true)
	protected String pknnamcode;
	@XmlElement(name = "P_RESERVE1", required = true)
	protected String preserve1;
	@XmlElement(name = "P_RESERVE2", required = true)
	protected String preserve2;
	@XmlElement(name = "P_RESERVE3", required = true)
	protected String preserve3;
	@XmlElement(name = "P_RESERVE4", required = true)
	protected String preserve4;
	@XmlElement(name = "P_RESERVE5", required = true)
	protected String preserve5;

	/**
	 * Gets the value of the ppackno property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPPACKNO() {
		return ppackno;
	}

	/**
	 * Sets the value of the ppackno property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPPACKNO(String value) {
		this.ppackno = value;
	}

	/**
	 * Gets the value of the pitem property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPITEM() {
		return pitem;
	}

	/**
	 * Sets the value of the pitem property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPITEM(String value) {
		this.pitem = value;
	}

	/**
	 * Gets the value of the pactionid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPACTIONID() {
		return pactionid;
	}

	/**
	 * Sets the value of the pactionid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPACTIONID(String value) {
		this.pactionid = value;
	}

	/**
	 * Gets the value of the pknnam property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPKNNAM() {
		return pknnam;
	}

	/**
	 * Sets the value of the pknnam property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPKNNAM(String value) {
		this.pknnam = value;
	}

	/**
	 * Gets the value of the pknktx property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPKNKTX() {
		return pknktx;
	}

	/**
	 * Sets the value of the pknktx property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPKNKTX(String value) {
		this.pknktx = value;
	}

	/**
	 * Gets the value of the pknart property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPKNART() {
		return pknart;
	}

	/**
	 * Sets the value of the pknart property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPKNART(String value) {
		this.pknart = value;
	}

	/**
	 * Gets the value of the pknsta property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPKNSTA() {
		return pknsta;
	}

	/**
	 * Sets the value of the pknsta property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPKNSTA(String value) {
		this.pknsta = value;
	}

	/**
	 * Gets the value of the pknedt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPKNEDT() {
		return pknedt;
	}

	/**
	 * Sets the value of the pknedt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPKNEDT(String value) {
		this.pknedt = value;
	}

	/**
	 * Gets the value of the pknadt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPKNADT() {
		return pknadt;
	}

	/**
	 * Sets the value of the pknadt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPKNADT(String value) {
		this.pknadt = value;
	}

	/**
	 * Gets the value of the pknnamcode property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPKNNAMCODE() {
		return pknnamcode;
	}

	/**
	 * Sets the value of the pknnamcode property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPKNNAMCODE(String value) {
		this.pknnamcode = value;
	}

	/**
	 * Gets the value of the preserve1 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPRESERVE1() {
		return preserve1;
	}

	/**
	 * Sets the value of the preserve1 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPRESERVE1(String value) {
		this.preserve1 = value;
	}

	/**
	 * Gets the value of the preserve2 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPRESERVE2() {
		return preserve2;
	}

	/**
	 * Sets the value of the preserve2 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPRESERVE2(String value) {
		this.preserve2 = value;
	}

	/**
	 * Gets the value of the preserve3 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPRESERVE3() {
		return preserve3;
	}

	/**
	 * Sets the value of the preserve3 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPRESERVE3(String value) {
		this.preserve3 = value;
	}

	/**
	 * Gets the value of the preserve4 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPRESERVE4() {
		return preserve4;
	}

	/**
	 * Sets the value of the preserve4 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPRESERVE4(String value) {
		this.preserve4 = value;
	}

	/**
	 * Gets the value of the preserve5 property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPRESERVE5() {
		return preserve5;
	}

	/**
	 * Sets the value of the preserve5 property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPRESERVE5(String value) {
		this.preserve5 = value;
	}

}
