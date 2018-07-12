
package integration.base.relevance;

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
 *         &lt;element name="INPUT" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZPP_TCI004"/>
 *         &lt;element name="OUTPUT" type="{urn:sap-com:document:sap:rfc:functions}TABLE_OF_ZPP_TCO004"/>
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
@XmlRootElement(name = "ZPP_TC_SAP_004Response")
public class ZPPTCSAP004Response {

	@XmlElement(name = "INPUT", required = true)
	protected TABLEOFZPPTCI004 input;
	@XmlElement(name = "OUTPUT", required = true)
	protected TABLEOFZPPTCO004 output;

	/**
	 * Gets the value of the input property.
	 * 
	 * @return possible object is {@link TABLEOFZPPTCI004 }
	 * 
	 */
	public TABLEOFZPPTCI004 getINPUT() {
		return input;
	}

	/**
	 * Sets the value of the input property.
	 * 
	 * @param value
	 *            allowed object is {@link TABLEOFZPPTCI004 }
	 * 
	 */
	public void setINPUT(TABLEOFZPPTCI004 value) {
		this.input = value;
	}

	/**
	 * Gets the value of the output property.
	 * 
	 * @return possible object is {@link TABLEOFZPPTCO004 }
	 * 
	 */
	public TABLEOFZPPTCO004 getOUTPUT() {
		return output;
	}

	/**
	 * Sets the value of the output property.
	 * 
	 * @param value
	 *            allowed object is {@link TABLEOFZPPTCO004 }
	 * 
	 */
	public void setOUTPUT(TABLEOFZPPTCO004 value) {
		this.output = value;
	}

}
