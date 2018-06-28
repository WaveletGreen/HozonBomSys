
package webservice.cfg;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.1.3-hudson-390-
 * Generated source version: 2.0
 * <p>
 * An example of how this class may be used:
 * 
 * <pre>
* ZPP_TC_SAP_002 service = new ZPP_TC_SAP_002();
* ZPPTCSAP002 portType = service.getZPPTCSAP002();
* portType.zppTCSAP002(...);
 * </pre>
 * </p>
 * 
 */
@WebServiceClient(name = "ZPP_TC_SAP_002", targetNamespace = "urn:sap-com:document:sap:rfc:functions", wsdlLocation = "file:/H:/eclipseworkplace/untitled1/src/main/resources/SAPInfo/zpp_tc_sap_002.wsdl")
public class ZPPTCSAP002_Service extends Service {

	private final static URL ZPPTCSAP002_WSDL_LOCATION;
	private final static Logger logger = Logger.getLogger(webservice.cfg.ZPPTCSAP002_Service.class.getName());

	static {
		URL url = null;
		try {
			URL baseUrl;
			baseUrl = webservice.cfg.ZPPTCSAP002_Service.class.getResource(".");
			url = new URL(baseUrl,
					"file:/H:/eclipseworkplace/untitled1/src/main/resources/SAPInfo/zpp_tc_sap_002.wsdl");
		} catch (MalformedURLException e) {
			logger.warning(
					"Failed to create URL for the wsdl Location: 'file:/H:/eclipseworkplace/untitled1/src/main/resources/SAPInfo/zpp_tc_sap_002.wsdl', retrying as a local file");
			logger.warning(e.getMessage());
		}
		ZPPTCSAP002_WSDL_LOCATION = url;
	}

	public ZPPTCSAP002_Service(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public ZPPTCSAP002_Service() {
		super(ZPPTCSAP002_WSDL_LOCATION, new QName("urn:sap-com:document:sap:rfc:functions", "ZPP_TC_SAP_002"));
	}

	/**
	 * 
	 * @return returns ZPPTCSAP002
	 */
	@WebEndpoint(name = "ZPP_TC_SAP_002")
	public ZPPTCSAP002 getZPPTCSAP002() {
		return super.getPort(new QName("urn:sap-com:document:sap:rfc:functions", "ZPP_TC_SAP_002"), ZPPTCSAP002.class);
	}

	/**
	 * 
	 * @return returns ZPPTCSAP002
	 */
	@WebEndpoint(name = "ZPP_TC_SAP_002_soap12")
	public ZPPTCSAP002 getZPPTCSAP002Soap12() {
		return super.getPort(new QName("urn:sap-com:document:sap:rfc:functions", "ZPP_TC_SAP_002_soap12"),
				ZPPTCSAP002.class);
	}

}
