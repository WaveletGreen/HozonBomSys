
package integration.base.masterMaterial;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ZPP_TCI001 complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ZPP_TCI001">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="P_UID" type="{urn:sap-com:document:sap:rfc:functions}byte16"/>
 *         &lt;element name="P_ITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="P_WERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="P_ACTIONID" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_MATNR" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_BISMT" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_MAKTX" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_MAKTX_E" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_MEINS" type="{urn:sap-com:document:sap:rfc:functions}unit3"/>
 *         &lt;element name="P_MTART" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="P_MATKL" type="{urn:sap-com:document:sap:rfc:functions}char9"/>
 *         &lt;element name="P_BESKZ" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_VIN" type="{urn:sap-com:document:sap:rfc:functions}char18"/>
 *         &lt;element name="P_COLOR" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="P_BRGEW" type="{urn:sap-com:document:sap:rfc:functions}char23"/>
 *         &lt;element name="P_LABEL" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_RULES" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_IMPOT" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_DELETE" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_MRPC" type="{urn:sap-com:document:sap:rfc:functions}char3"/>
 *         &lt;element name="P_SJBS" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
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
@XmlType(name = "ZPP_TCI001", propOrder = { "puid", "pitem", "pwerks", "pactionid", "pmatnr", "pbismt", "pmaktx",
		"pmaktxe", "pmeins", "pmtart", "pmatkl", "pbeskz", "pvin", "pcolor", "pbrgew", "plabel", "prules", "pimpot",
		"pdelete", "pmrpc", "psjbs", "preserve1", "preserve2", "preserve3", "preserve4", "preserve5" })
public class ZPPTCI001 {

	@XmlElement(name = "P_UID", required = true)
	protected String puid;
	@XmlElement(name = "P_ITEM", required = true)
	protected String pitem;
	@XmlElement(name = "P_WERKS", required = true)
	protected String pwerks;
	@XmlElement(name = "P_ACTIONID", required = true)
	protected String pactionid;
	@XmlElement(name = "P_MATNR", required = true)
	protected String pmatnr;
	@XmlElement(name = "P_BISMT", required = true)
	protected String pbismt;
	@XmlElement(name = "P_MAKTX", required = true)
	protected String pmaktx;
	@XmlElement(name = "P_MAKTX_E", required = true)
	protected String pmaktxe;
	@XmlElement(name = "P_MEINS", required = true)
	protected String pmeins;
	@XmlElement(name = "P_MTART", required = true)
	protected String pmtart;
	@XmlElement(name = "P_MATKL", required = true)
	protected String pmatkl;
	@XmlElement(name = "P_BESKZ", required = true)
	protected String pbeskz;
	@XmlElement(name = "P_VIN", required = true)
	protected String pvin;
	@XmlElement(name = "P_COLOR", required = true)
	protected String pcolor;
	@XmlElement(name = "P_BRGEW", required = true)
	protected String pbrgew;
	@XmlElement(name = "P_LABEL", required = true)
	protected String plabel;
	@XmlElement(name = "P_RULES", required = true)
	protected String prules;
	@XmlElement(name = "P_IMPOT", required = true)
	protected String pimpot;
	@XmlElement(name = "P_DELETE", required = true)
	protected String pdelete;
	@XmlElement(name = "P_MRPC", required = true)
	protected String pmrpc;
	@XmlElement(name = "P_SJBS", required = true)
	protected String psjbs;
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
	 * Gets the value of the puid property.
	 * 
	 * @return possible object is byte[]
	 */
	public String getPUID() {
		return puid;
	}

	/**
	 * Sets the value of the puid property.
	 * 
	 * @param value
	 *            allowed object is byte[]
	 */
	public void setPUID(String value) {
		this.puid = value;
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
	 * Gets the value of the pwerks property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPWERKS() {
		return pwerks;
	}

	/**
	 * Sets the value of the pwerks property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPWERKS(String value) {
		this.pwerks = value;
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
	 * Gets the value of the pbismt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPBISMT() {
		return pbismt;
	}

	/**
	 * Sets the value of the pbismt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPBISMT(String value) {
		this.pbismt = value;
	}

	/**
	 * Gets the value of the pmaktx property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMAKTX() {
		return pmaktx;
	}

	/**
	 * Sets the value of the pmaktx property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMAKTX(String value) {
		this.pmaktx = value;
	}

	/**
	 * Gets the value of the pmaktxe property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMAKTXE() {
		return pmaktxe;
	}

	/**
	 * Sets the value of the pmaktxe property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMAKTXE(String value) {
		this.pmaktxe = value;
	}

	/**
	 * Gets the value of the pmeins property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMEINS() {
		return pmeins;
	}

	/**
	 * Sets the value of the pmeins property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMEINS(String value) {
		this.pmeins = value;
	}

	/**
	 * Gets the value of the pmtart property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMTART() {
		return pmtart;
	}

	/**
	 * Sets the value of the pmtart property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMTART(String value) {
		this.pmtart = value;
	}

	/**
	 * Gets the value of the pmatkl property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMATKL() {
		return pmatkl;
	}

	/**
	 * Sets the value of the pmatkl property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMATKL(String value) {
		this.pmatkl = value;
	}

	/**
	 * Gets the value of the pbeskz property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPBESKZ() {
		return pbeskz;
	}

	/**
	 * Sets the value of the pbeskz property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPBESKZ(String value) {
		this.pbeskz = value;
	}

	/**
	 * Gets the value of the pvin property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPVIN() {
		return pvin;
	}

	/**
	 * Sets the value of the pvin property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPVIN(String value) {
		this.pvin = value;
	}

	/**
	 * Gets the value of the pcolor property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPCOLOR() {
		return pcolor;
	}

	/**
	 * Sets the value of the pcolor property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPCOLOR(String value) {
		this.pcolor = value;
	}

	/**
	 * Gets the value of the pbrgew property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPBRGEW() {
		return pbrgew;
	}

	/**
	 * Sets the value of the pbrgew property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPBRGEW(String value) {
		this.pbrgew = value;
	}

	/**
	 * Gets the value of the plabel property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPLABEL() {
		return plabel;
	}

	/**
	 * Sets the value of the plabel property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPLABEL(String value) {
		this.plabel = value;
	}

	/**
	 * Gets the value of the prules property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPRULES() {
		return prules;
	}

	/**
	 * Sets the value of the prules property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPRULES(String value) {
		this.prules = value;
	}

	/**
	 * Gets the value of the pimpot property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPIMPOT() {
		return pimpot;
	}

	/**
	 * Sets the value of the pimpot property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPIMPOT(String value) {
		this.pimpot = value;
	}

	/**
	 * Gets the value of the pdelete property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPDELETE() {
		return pdelete;
	}

	/**
	 * Sets the value of the pdelete property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPDELETE(String value) {
		this.pdelete = value;
	}

	/**
	 * Gets the value of the pmrpc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMRPC() {
		return pmrpc;
	}

	/**
	 * Sets the value of the pmrpc property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMRPC(String value) {
		this.pmrpc = value;
	}

	/**
	 * Gets the value of the psjbs property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPSJBS() {
		return psjbs;
	}

	/**
	 * Sets the value of the psjbs property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPSJBS(String value) {
		this.psjbs = value;
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
