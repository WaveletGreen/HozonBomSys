
package webservice.base.masterMaterial;

import webservice.service.i.ICommon;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * 输入参数层
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TABLE_OF_ZPP_TCI001", propOrder = { "item" })
public class TABLEOFZPPTCI001 implements ICommon{

	protected List<ZPPTCI001> item;

	/**
	 * Gets the value of the item property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the item property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getItem().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link ZPPTCI001
	 * }
	 * 
	 * 
	 */
	public List<ZPPTCI001> getItem() {
		if (item == null) {
			item = new ArrayList<ZPPTCI001>();
		}
		return this.item;
	}

}
