
package integration.base.processRoute;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ZPP_TCI006 complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ZPP_TCI006">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="P_PACKNO" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_ITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="P_ACTIONID" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_WERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="P_MATNR" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_BASED" type="{urn:sap-com:document:sap:rfc:functions}char23"/>
 *         &lt;element name="P_DATE" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="P_USE" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_STA" type="{urn:sap-com:document:sap:rfc:functions}char3"/>
 *         &lt;element name="P_NUMBER" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="P_WORK" type="{urn:sap-com:document:sap:rfc:functions}char8"/>
 *         &lt;element name="P_CON" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="P_ROUT" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="P_DATA1" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="P_DATA2" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="P_DATA3" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="P_DATA4" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="P_DATA5" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="P_DATA6" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
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
@XmlType(name = "ZPP_TCI006", propOrder = { "ppackno", "pitem", "pactionid", "pwerks", "pmatnr", "pbased", "pdate",
		"puse", "psta", "pnumber", "pwork", "pcon", "prout", "pdata1", "pdata2", "pdata3", "pdata4", "pdata5", "pdata6",
		"preserve1", "preserve2", "preserve3", "preserve4", "preserve5" })
public class ZPPTCI006 {

	@XmlElement(name = "P_PACKNO", required = true)
	protected String ppackno;
	@XmlElement(name = "P_ITEM", required = true)
	protected String pitem;
	@XmlElement(name = "P_ACTIONID", required = true)
	protected String pactionid;
	@XmlElement(name = "P_WERKS", required = true)
	protected String pwerks;
	@XmlElement(name = "P_MATNR", required = true)
	protected String pmatnr;
	@XmlElement(name = "P_BASED", required = true)
	protected String pbased;
	@XmlElement(name = "P_DATE", required = true)
	protected String pdate;
	@XmlElement(name = "P_USE", required = true)
	protected String puse;
	@XmlElement(name = "P_STA", required = true)
	protected String psta;
	@XmlElement(name = "P_NUMBER", required = true)
	protected String pnumber;
	@XmlElement(name = "P_WORK", required = true)
	protected String pwork;
	@XmlElement(name = "P_CON", required = true)
	protected String pcon;
	@XmlElement(name = "P_ROUT", required = true)
	protected String prout;
	@XmlElement(name = "P_DATA1", required = true)
	protected BigDecimal pdata1;
	@XmlElement(name = "P_DATA2", required = true)
	protected BigDecimal pdata2;
	@XmlElement(name = "P_DATA3", required = true)
	protected BigDecimal pdata3;
	@XmlElement(name = "P_DATA4", required = true)
	protected BigDecimal pdata4;
	@XmlElement(name = "P_DATA5", required = true)
	protected BigDecimal pdata5;
	@XmlElement(name = "P_DATA6", required = true)
	protected BigDecimal pdata6;
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
	 * Gets the value of the pbased property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPBASED() {
		return pbased;
	}

	/**
	 * Sets the value of the pbased property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPBASED(String value) {
		this.pbased = value;
	}

	/**
	 * Gets the value of the pdate property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPDATE() {
		return pdate;
	}

	/**
	 * Sets the value of the pdate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPDATE(String value) {
		this.pdate = value;
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
	 * Gets the value of the psta property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPSTA() {
		return psta;
	}

	/**
	 * Sets the value of the psta property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPSTA(String value) {
		this.psta = value;
	}

	/**
	 * Gets the value of the pnumber property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPNUMBER() {
		return pnumber;
	}

	/**
	 * Sets the value of the pnumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPNUMBER(String value) {
		this.pnumber = value;
	}

	/**
	 * Gets the value of the pwork property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPWORK() {
		return pwork;
	}

	/**
	 * Sets the value of the pwork property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPWORK(String value) {
		this.pwork = value;
	}

	/**
	 * Gets the value of the pcon property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPCON() {
		return pcon;
	}

	/**
	 * Sets the value of the pcon property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPCON(String value) {
		this.pcon = value;
	}

	/**
	 * Gets the value of the prout property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPROUT() {
		return prout;
	}

	/**
	 * Sets the value of the prout property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPROUT(String value) {
		this.prout = value;
	}

	/**
	 * Gets the value of the pdata1 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getPDATA1() {
		return pdata1;
	}

	/**
	 * Sets the value of the pdata1 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setPDATA1(BigDecimal value) {
		this.pdata1 = value;
	}

	/**
	 * Gets the value of the pdata2 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getPDATA2() {
		return pdata2;
	}

	/**
	 * Sets the value of the pdata2 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setPDATA2(BigDecimal value) {
		this.pdata2 = value;
	}

	/**
	 * Gets the value of the pdata3 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getPDATA3() {
		return pdata3;
	}

	/**
	 * Sets the value of the pdata3 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setPDATA3(BigDecimal value) {
		this.pdata3 = value;
	}

	/**
	 * Gets the value of the pdata4 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getPDATA4() {
		return pdata4;
	}

	/**
	 * Sets the value of the pdata4 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setPDATA4(BigDecimal value) {
		this.pdata4 = value;
	}

	/**
	 * Gets the value of the pdata5 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getPDATA5() {
		return pdata5;
	}

	/**
	 * Sets the value of the pdata5 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setPDATA5(BigDecimal value) {
		this.pdata5 = value;
	}

	/**
	 * Gets the value of the pdata6 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getPDATA6() {
		return pdata6;
	}

	/**
	 * Sets the value of the pdata6 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setPDATA6(BigDecimal value) {
		this.pdata6 = value;
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
