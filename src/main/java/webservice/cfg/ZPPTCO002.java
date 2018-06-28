
package webservice.cfg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ZPP_TCO002 complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="ZPP_TCO002">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZPACKNO" type="{urn:sap-com:document:sap:rfc:functions}char40"/>
 *         &lt;element name="ZITEM" type="{urn:sap-com:document:sap:rfc:functions}char6"/>
 *         &lt;element name="TYPE" type="{urn:sap-com:document:sap:rfc:functions}char1"/>
 *         &lt;element name="MESSAGE" type="{urn:sap-com:document:sap:rfc:functions}char220"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ZPP_TCO002", propOrder = { "zpackno", "zitem", "type", "message" })
public class ZPPTCO002 {

	@XmlElement(name = "ZPACKNO", required = true)
	protected String zpackno;
	@XmlElement(name = "ZITEM", required = true)
	protected String zitem;
	@XmlElement(name = "TYPE", required = true)
	protected String type;
	@XmlElement(name = "MESSAGE", required = true)
	protected String message;

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
	 * Gets the value of the type property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTYPE() {
		return type;
	}

	/**
	 * Sets the value of the type property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setTYPE(String value) {
		this.type = value;
	}

	/**
	 * Gets the value of the message property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMESSAGE() {
		return message;
	}

	/**
	 * Sets the value of the message property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMESSAGE(String value) {
		this.message = value;
	}

}
