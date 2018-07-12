
package integration.base.classify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ZPP_TCI003 complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ZPP_TCI003">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="P_PACKNO" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_ITEM" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_ACTIONID" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_MATNR" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_ATNAM" type="{urn:sap-com:document:sap:rfc:functions}char30"/>
 *         &lt;element name="P_ATWRT" type="{urn:sap-com:document:sap:rfc:functions}char70"/>
 *         &lt;element name="P_CLASS" type="{urn:sap-com:document:sap:rfc:functions}char18"/>
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
@XmlType(name = "ZPP_TCI003", propOrder = { "ppackno", "pitem", "pactionid", "pmatnr", "patnam", "patwrt", "pclass",
		"preserve1", "preserve2", "preserve3", "preserve4", "preserve5" })
public class ZPPTCI003 {

	@XmlElement(name = "P_PACKNO", required = true)
	protected String ppackno;
	@XmlElement(name = "P_ITEM", required = true)
	protected String pitem;
	@XmlElement(name = "P_ACTIONID", required = true)
	protected String pactionid;
	@XmlElement(name = "P_MATNR", required = true)
	protected String pmatnr;
	@XmlElement(name = "P_ATNAM", required = true)
	protected String patnam;
	@XmlElement(name = "P_ATWRT", required = true)
	protected String patwrt;
	@XmlElement(name = "P_CLASS", required = true)
	protected String pclass;
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
	 * Gets the value of the pmatnr property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMATNR() {
		return pmatnr;
	}

	/**
	 * Sets the value of the pmatnr property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMATNR(String value) {
		this.pmatnr = value;
	}

	/**
	 * Gets the value of the patnam property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPATNAM() {
		return patnam;
	}

	/**
	 * Sets the value of the patnam property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPATNAM(String value) {
		this.patnam = value;
	}

	/**
	 * Gets the value of the patwrt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPATWRT() {
		return patwrt;
	}

	/**
	 * Sets the value of the patwrt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPATWRT(String value) {
		this.patwrt = value;
	}

	/**
	 * Gets the value of the pclass property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPCLASS() {
		return pclass;
	}

	/**
	 * Sets the value of the pclass property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPCLASS(String value) {
		this.pclass = value;
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
