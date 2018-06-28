
package webservice.processRoute;

import javax.xml.bind.annotation.*;

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
 *         &lt;element name="INPUT" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZPP_TCI006"/>
 *         &lt;element name="OUTPUT" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZPP_TCO006"/>
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
@XmlRootElement(name = "ZPP_TC_SAP_006Response")
public class ZPPTCSAP006Response {

	@XmlElement(name = "INPUT", required = true)
	protected TABLEOFZPPTCI006 input;
	@XmlElement(name = "OUTPUT", required = true)
	protected TABLEOFZPPTCO006 output;

	/**
	 * Gets the value of the input property.
	 * 
	 * @return possible object is {@link TABLEOFZPPTCI006 }
	 * 
	 */
	public TABLEOFZPPTCI006 getINPUT() {
		return input;
	}

	/**
	 * Sets the value of the input property.
	 * 
	 * @param value
	 *            allowed object is {@link TABLEOFZPPTCI006 }
	 * 
	 */
	public void setINPUT(TABLEOFZPPTCI006 value) {
		this.input = value;
	}

	/**
	 * Gets the value of the output property.
	 * 
	 * @return possible object is {@link TABLEOFZPPTCO006 }
	 * 
	 */
	public TABLEOFZPPTCO006 getOUTPUT() {
		return output;
	}

	/**
	 * Sets the value of the output property.
	 * 
	 * @param value
	 *            allowed object is {@link TABLEOFZPPTCO006 }
	 * 
	 */
	public void setOUTPUT(TABLEOFZPPTCO006 value) {
		this.output = value;
	}

}
