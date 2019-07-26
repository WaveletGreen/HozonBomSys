
package integration.base.productAttributes;

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
 *         &lt;element name="INPUT" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZPP_TCI007"/>
 *         &lt;element name="OUTPUT" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZPP_TCO007"/>
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
@XmlRootElement(name = "ZPP_TC_SAP_007Response")
public class ZPPTCSAP007Response {

	@XmlElement(name = "INPUT", required = true)
	protected TABLEOFZPPTCI007 input;
	@XmlElement(name = "OUTPUT", required = true)
	protected TABLEOFZPPTCO007 output;

	/**
	 * Gets the value of the input property.
	 * 
	 * @return possible object is {@link TABLEOFZPPTCI007 }
	 * 
	 */
	public TABLEOFZPPTCI007 getINPUT() {
		return input;
	}

	/**
	 * Sets the value of the input property.
	 * 
	 * @param value
	 *            allowed object is {@link TABLEOFZPPTCI007 }
	 * 
	 */
	public void setINPUT(TABLEOFZPPTCI007 value) {
		this.input = value;
	}

	/**
	 * Gets the value of the output property.
	 * 
	 * @return possible object is {@link TABLEOFZPPTCO007 }
	 * 
	 */
	public TABLEOFZPPTCO007 getOUTPUT() {
		return output;
	}

	/**
	 * Sets the value of the output property.
	 * 
	 * @param value
	 *            allowed object is {@link TABLEOFZPPTCO007 }
	 * 
	 */
	public void setOUTPUT(TABLEOFZPPTCO007 value) {
		this.output = value;
	}

}
