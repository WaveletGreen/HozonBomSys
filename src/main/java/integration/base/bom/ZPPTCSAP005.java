
package integration.base.bom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="INPUT" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZPP_TCI005"/>
 *         &lt;element name="OUTPUT" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZPP_TCO005"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "input", "output" })
@XmlRootElement(name = "ZPP_TC_SAP_005")
public class ZPPTCSAP005 {

	@XmlElement(name = "INPUT", required = true)
	protected TABLEOFZPPTCI005 input;
	@XmlElement(name = "OUTPUT", required = true)
	protected TABLEOFZPPTCO005 output;

	/**
	 * Gets the value of the input property.
	 * 
	 * @return possible object is {@link TABLEOFZPPTCI005 }
	 * 
	 */
	public TABLEOFZPPTCI005 getINPUT() {
		return input;
	}

	/**
	 * Sets the value of the input property.
	 * 
	 * @param value
	 *            allowed object is {@link TABLEOFZPPTCI005 }
	 * 
	 */
	public void setINPUT(TABLEOFZPPTCI005 value) {
		this.input = value;
	}

	/**
	 * Gets the value of the output property.
	 * 
	 * @return possible object is {@link TABLEOFZPPTCO005 }
	 * 
	 */
	public TABLEOFZPPTCO005 getOUTPUT() {
		return output;
	}

	/**
	 * Sets the value of the output property.
	 * 
	 * @param value
	 *            allowed object is {@link TABLEOFZPPTCO005 }
	 * 
	 */
	public void setOUTPUT(TABLEOFZPPTCO005 value) {
		this.output = value;
	}

}
