
package webservice.processRoute;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

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
 *         &lt;element name="ZPACKNO" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="ZACTIONID" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="ZWERKS" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="ZMATNR" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZBASED" type="{urn:sap-com:document:sap:rfc:functions}char23"/>
 *         &lt;element name="ZDATE" type="{urn:sap-com:document:sap:rfc:functions}date10"/>
 *         &lt;element name="ZUSE" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="ZSTA" type="{urn:sap-com:document:sap:rfc:functions}char3"/>
 *         &lt;element name="ZNUMBER" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="ZWORK" type="{urn:sap-com:document:sap:rfc:functions}char8"/>
 *         &lt;element name="ZCON" type="{urn:sap-com:document:sap:rfc:functions}char4"/>
 *         &lt;element name="ZROUT" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZDATA1" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="ZDATA2" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="ZDATA3" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="ZDATA4" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="ZDATA5" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
 *         &lt;element name="ZDATA6" type="{urn:sap-com:document:sap:rfc:functions}quantum9.3"/>
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
@XmlType(name = "ZPP_TCI006", propOrder = { "zpackno", "zitem", "zactionid", "zwerks", "zmatnr", "zbased", "zdate",
		"zuse", "zsta", "znumber", "zwork", "zcon", "zrout", "zdata1", "zdata2", "zdata3", "zdata4", "zdata5", "zdata6",
		"reserve01", "reserve02", "reserve03", "reserve04", "reserve05" })
public class ZPPTCI006 {

	@XmlElement(name = "ZPACKNO", required = true)
	protected String zpackno;
	@XmlElement(name = "ZITEM", required = true)
	protected String zitem;
	@XmlElement(name = "ZACTIONID", required = true)
	protected String zactionid;
	@XmlElement(name = "ZWERKS", required = true)
	protected String zwerks;
	@XmlElement(name = "ZMATNR", required = true)
	protected String zmatnr;
	@XmlElement(name = "ZBASED", required = true)
	protected String zbased;
	@XmlElement(name = "ZDATE", required = true)
	protected String zdate;
	@XmlElement(name = "ZUSE", required = true)
	protected String zuse;
	@XmlElement(name = "ZSTA", required = true)
	protected String zsta;
	@XmlElement(name = "ZNUMBER", required = true)
	protected String znumber;
	@XmlElement(name = "ZWORK", required = true)
	protected String zwork;
	@XmlElement(name = "ZCON", required = true)
	protected String zcon;
	@XmlElement(name = "ZROUT", required = true)
	protected String zrout;
	@XmlElement(name = "ZDATA1", required = true)
	protected BigDecimal zdata1;
	@XmlElement(name = "ZDATA2", required = true)
	protected BigDecimal zdata2;
	@XmlElement(name = "ZDATA3", required = true)
	protected BigDecimal zdata3;
	@XmlElement(name = "ZDATA4", required = true)
	protected BigDecimal zdata4;
	@XmlElement(name = "ZDATA5", required = true)
	protected BigDecimal zdata5;
	@XmlElement(name = "ZDATA6", required = true)
	protected BigDecimal zdata6;
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
	 * Gets the value of the zbased property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZBASED() {
		return zbased;
	}

	/**
	 * Sets the value of the zbased property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZBASED(String value) {
		this.zbased = value;
	}

	/**
	 * Gets the value of the zdate property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZDATE() {
		return zdate;
	}

	/**
	 * Sets the value of the zdate property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZDATE(String value) {
		this.zdate = value;
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
	 * Gets the value of the zsta property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZSTA() {
		return zsta;
	}

	/**
	 * Sets the value of the zsta property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZSTA(String value) {
		this.zsta = value;
	}

	/**
	 * Gets the value of the znumber property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZNUMBER() {
		return znumber;
	}

	/**
	 * Sets the value of the znumber property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZNUMBER(String value) {
		this.znumber = value;
	}

	/**
	 * Gets the value of the zwork property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZWORK() {
		return zwork;
	}

	/**
	 * Sets the value of the zwork property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZWORK(String value) {
		this.zwork = value;
	}

	/**
	 * Gets the value of the zcon property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZCON() {
		return zcon;
	}

	/**
	 * Sets the value of the zcon property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZCON(String value) {
		this.zcon = value;
	}

	/**
	 * Gets the value of the zrout property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getZROUT() {
		return zrout;
	}

	/**
	 * Sets the value of the zrout property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setZROUT(String value) {
		this.zrout = value;
	}

	/**
	 * Gets the value of the zdata1 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getZDATA1() {
		return zdata1;
	}

	/**
	 * Sets the value of the zdata1 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setZDATA1(BigDecimal value) {
		this.zdata1 = value;
	}

	/**
	 * Gets the value of the zdata2 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getZDATA2() {
		return zdata2;
	}

	/**
	 * Sets the value of the zdata2 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setZDATA2(BigDecimal value) {
		this.zdata2 = value;
	}

	/**
	 * Gets the value of the zdata3 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getZDATA3() {
		return zdata3;
	}

	/**
	 * Sets the value of the zdata3 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setZDATA3(BigDecimal value) {
		this.zdata3 = value;
	}

	/**
	 * Gets the value of the zdata4 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getZDATA4() {
		return zdata4;
	}

	/**
	 * Sets the value of the zdata4 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setZDATA4(BigDecimal value) {
		this.zdata4 = value;
	}

	/**
	 * Gets the value of the zdata5 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getZDATA5() {
		return zdata5;
	}

	/**
	 * Sets the value of the zdata5 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setZDATA5(BigDecimal value) {
		this.zdata5 = value;
	}

	/**
	 * Gets the value of the zdata6 property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getZDATA6() {
		return zdata6;
	}

	/**
	 * Sets the value of the zdata6 property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setZDATA6(BigDecimal value) {
		this.zdata6 = value;
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
