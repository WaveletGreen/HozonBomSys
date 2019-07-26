
package integration.base.bom;

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
 *         &lt;element name="P_PACKNO" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_ITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="P_ACTIONID" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_AENNR" type="{urn:sap-com:document:sap:rfc:functions}char12"/>
 *         &lt;element name="P_AETXT" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_PCNNO" type="{urn:sap-com:document:sap:rfc:functions}char72"/>
 *         &lt;element name="P_WERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="P_USE" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_MATNR" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_BASEQ" type="{urn:sap-com:document:sap:rfc:functions}char23"/>
 *         &lt;element name="P_SORTF" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="P_MATNR_C" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_MENGE" type="{urn:sap-com:document:sap:rfc:functions}char23"/>
 *         &lt;element name="P_MEINS" type="{urn:sap-com:document:sap:rfc:functions}unit3"/>
 *         &lt;element name="P_LOCATION" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="P_KNBLK" type="{urn:sap-com:document:sap:rfc:functions}char30"/>
 *         &lt;element name="P_SUBPR" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_ZPWZ" type="{urn:sap-com:document:sap:rfc:functions}char8"/>
 *         &lt;element name="P_WORKS" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="P_STATION" type="{urn:sap-com:document:sap:rfc:functions}char10"/>
 *         &lt;element name="P_GUID" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
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
@XmlType(name = "ZPP_TCI005", propOrder = { "ppackno", "pitem", "pactionid", "paennr", "paetxt", "ppcnno", "pwerks",
		"puse", "pmatnr", "pbaseq", "psortf", "pmatnrc", "pmenge", "pmeins", "plocation", "pknblk", "psubpr", "pzpwz",
		"pworks", "pstation", "pguid", "preserve1", "preserve2", "preserve3", "preserve4", "preserve5" })
public class ZPPTCI005 {

	@XmlElement(name = "P_PACKNO", required = true)
	protected String ppackno;
	@XmlElement(name = "P_ITEM", required = true)
	protected String pitem;
	@XmlElement(name = "P_ACTIONID", required = true)
	protected String pactionid;
	@XmlElement(name = "P_AENNR", required = true)
	protected String paennr;
	@XmlElement(name = "P_AETXT", required = true)
	protected String paetxt;
	@XmlElement(name = "P_PCNNO", required = true)
	protected String ppcnno;
	@XmlElement(name = "P_WERKS", required = true)
	protected String pwerks;
	@XmlElement(name = "P_USE", required = true)
	protected String puse;
	@XmlElement(name = "P_MATNR", required = true)
	protected String pmatnr;
	@XmlElement(name = "P_BASEQ", required = true)
	protected String pbaseq;
	@XmlElement(name = "P_SORTF", required = true)
	protected String psortf;
	@XmlElement(name = "P_MATNR_C", required = true)
	protected String pmatnrc;
	@XmlElement(name = "P_MENGE", required = true)
	protected String pmenge;
	@XmlElement(name = "P_MEINS", required = true)
	protected String pmeins;
	@XmlElement(name = "P_LOCATION", required = true)
	protected String plocation;
	@XmlElement(name = "P_KNBLK", required = true)
	protected String pknblk;
	@XmlElement(name = "P_SUBPR", required = true)
	protected String psubpr;
	@XmlElement(name = "P_ZPWZ", required = true)
	protected String pzpwz;
	@XmlElement(name = "P_WORKS", required = true)
	protected String pworks;
	@XmlElement(name = "P_STATION", required = true)
	protected String pstation;
	@XmlElement(name = "P_GUID", required = true)
	protected String pguid;
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
	 * Gets the value of the paennr property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPAENNR() {
		return paennr;
	}

	/**
	 * Sets the value of the paennr property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPAENNR(String value) {
		this.paennr = value;
	}

	/**
	 * Gets the value of the paetxt property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPAETXT() {
		return paetxt;
	}

	/**
	 * Sets the value of the paetxt property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPAETXT(String value) {
		this.paetxt = value;
	}

	/**
	 * Gets the value of the ppcnno property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPPCNNO() {
		return ppcnno;
	}

	/**
	 * Sets the value of the ppcnno property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPPCNNO(String value) {
		this.ppcnno = value;
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
	 * Gets the value of the puse property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPUSE() {
		return puse;
	}

	/**
	 * Sets the value of the puse property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPUSE(String value) {
		this.puse = value;
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
	 * Gets the value of the pbaseq property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPBASEQ() {
		return pbaseq;
	}

	/**
	 * Sets the value of the pbaseq property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPBASEQ(String value) {
		this.pbaseq = value;
	}

	/**
	 * Gets the value of the psortf property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPSORTF() {
		return psortf;
	}

	/**
	 * Sets the value of the psortf property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPSORTF(String value) {
		this.psortf = value;
	}

	/**
	 * Gets the value of the pmatnrc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMATNRC() {
		return pmatnrc;
	}

	/**
	 * Sets the value of the pmatnrc property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMATNRC(String value) {
		this.pmatnrc = value;
	}

	/**
	 * Gets the value of the pmenge property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMENGE() {
		return pmenge;
	}

	/**
	 * Sets the value of the pmenge property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMENGE(String value) {
		this.pmenge = value;
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
	 * Gets the value of the plocation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPLOCATION() {
		return plocation;
	}

	/**
	 * Sets the value of the plocation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPLOCATION(String value) {
		this.plocation = value;
	}

	/**
	 * Gets the value of the pknblk property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPKNBLK() {
		return pknblk;
	}

	/**
	 * Sets the value of the pknblk property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPKNBLK(String value) {
		this.pknblk = value;
	}

	/**
	 * Gets the value of the psubpr property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPSUBPR() {
		return psubpr;
	}

	/**
	 * Sets the value of the psubpr property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPSUBPR(String value) {
		this.psubpr = value;
	}

	/**
	 * Gets the value of the pzpwz property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPZPWZ() {
		return pzpwz;
	}

	/**
	 * Sets the value of the pzpwz property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPZPWZ(String value) {
		this.pzpwz = value;
	}

	/**
	 * Gets the value of the pworks property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPWORKS() {
		return pworks;
	}

	/**
	 * Sets the value of the pworks property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPWORKS(String value) {
		this.pworks = value;
	}

	/**
	 * Gets the value of the pstation property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPSTATION() {
		return pstation;
	}

	/**
	 * Sets the value of the pstation property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPSTATION(String value) {
		this.pstation = value;
	}

	/**
	 * Gets the value of the pguid property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPGUID() {
		return pguid;
	}

	/**
	 * Sets the value of the pguid property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPGUID(String value) {
		this.pguid = value;
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
