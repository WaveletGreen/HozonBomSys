
package webservice.productAttributes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ZPP_TCI007 complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ZPP_TCI007">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZPACKNO" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="ZACTIONID" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="ZMATNR" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZWERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="ZATNAM" type="{urn:sap-com:document:sap:rfc:functions}char30"/>
 *         &lt;element name="ZATWRT" type="{urn:sap-com:document:sap:rfc:functions}char70"/>
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
@XmlType(name = "ZPP_TCI007", propOrder = { "zpackno", "zitem", "zactionid", "zmatnr", "zwerks", "zatnam", "zatwrt",
		"reserve01", "reserve02", "reserve03", "reserve04", "reserve05" })
public class ZPPTCI007 {

	@XmlElement(name = "ZPACKNO", required = true)
	protected String zpackno;
	@XmlElement(name = "ZITEM", required = true)
	protected String zitem;
	@XmlElement(name = "ZACTIONID", required = true)
	protected String zactionid;
	@XmlElement(name = "ZMATNR", required = true)
	protected String zmatnr;
	@XmlElement(name = "ZWERKS", required = true)
	protected String zwerks;
	@XmlElement(name = "ZATNAM", required = true)
	protected String zatnam;
	@XmlElement(name = "ZATWRT", required = true)
	protected String zatwrt;
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
	 * Gets the value of the zatnam property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZATNAM() {
		return zatnam;
	}

	/**
	 * Sets the value of the zatnam property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZATNAM(String value) {
		this.zatnam = value;
	}

	/**
	 * Gets the value of the zatwrt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZATWRT() {
		return zatwrt;
	}

	/**
	 * Sets the value of the zatwrt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZATWRT(String value) {
		this.zatwrt = value;
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
