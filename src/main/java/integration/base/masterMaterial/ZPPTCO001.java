
package integration.base.masterMaterial;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ZPP_TCO001 complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ZPP_TCO001">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="P_GUID" type="{urn:sap-com:document:sap:rfc:functions}byte16"/>
 *         &lt;element name="P_ZITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="P_TYPE" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="P_MESSAGE" type="{urn:sap-com:document:sap:rfc:functions}char220"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZPP_TCO001", propOrder = { "pguid", "pzitem", "ptype", "pmessage" })
public class ZPPTCO001 {

	@XmlElement(name = "P_GUID", required = true)
	protected String pguid;
	@XmlElement(name = "P_ZITEM", required = true)
	protected String pzitem;
	@XmlElement(name = "P_TYPE", required = true)
	protected String ptype;
	@XmlElement(name = "P_MESSAGE", required = true)
	protected String pmessage;

	/**
	 * Gets the value of the pguid property.
	 * 
	 * @return possible object is byte[]
	 */
	public String getPGUID() {
		return pguid;
	}

	/**
	 * Sets the value of the pguid property.
	 * 
	 * @param value
	 *            allowed object is byte[]
	 */
	public void setPGUID(String value) {
		this.pguid = value;
	}

	/**
	 * Gets the value of the pzitem property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPZITEM() {
		return pzitem;
	}

	/**
	 * Sets the value of the pzitem property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPZITEM(String value) {
		this.pzitem = value;
	}

	/**
	 * Gets the value of the ptype property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPTYPE() {
		return ptype;
	}

	/**
	 * Sets the value of the ptype property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPTYPE(String value) {
		this.ptype = value;
	}

	/**
	 * Gets the value of the pmessage property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getPMESSAGE() {
		return pmessage;
	}

	/**
	 * Sets the value of the pmessage property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setPMESSAGE(String value) {
		this.pmessage = value;
	}

}
