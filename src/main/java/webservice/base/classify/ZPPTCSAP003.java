
package webservice.base.classify;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * 
 */
@WebService(name = "ZPP_TC_SAP_003", targetNamespace = "urn:sap-com:document:sap:rfc:functions")
public interface ZPPTCSAP003 {

	/**
	 * 
	 * @param output
	 * @param input
	 */
	@WebMethod(operationName = "ZPP_TC_SAP_003", action = "urn:sap-com:document:sap:rfc:functions:ZPP_TC_SAP_003:ZPP_TC_SAP_003Request")
	@RequestWrapper(localName = "ZPP_TC_SAP_003", targetNamespace = "urn:sap-com:document:sap:rfc:functions", className = "webservice.base.classify.ZPPTCSAP003_Type")
	@ResponseWrapper(localName = "ZPP_TC_SAP_003Response", targetNamespace = "urn:sap-com:document:sap:rfc:functions", className = "webservice.base.classify.ZPPTCSAP003Response")
	public void zppTCSAP003(
            @WebParam(name = "INPUT", targetNamespace = "", mode = WebParam.Mode.INOUT) Holder<TABLEOFZPPTCI003> input,
            @WebParam(name = "OUTPUT", targetNamespace = "", mode = WebParam.Mode.INOUT) Holder<TABLEOFZPPTCO003> output);

}
