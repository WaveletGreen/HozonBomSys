
package webservice.maindatas;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * 服务层，用于获取WSDL文件路径
 */
@WebServiceClient(name = "ZPP_TC_SAP_001", targetNamespace = "urn:sap-com:document:sap:rfc:functions", wsdlLocation = "file:/H:/eclipseworkplace/untitled1/src/main/resources/SAPInfo/zpp_tc_sap_001.wsdl")
public class ZPPTCSAP001_Service extends Service {

    private final static URL ZPPTCSAP001_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(webservice.maindatas.ZPPTCSAP001_Service.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = webservice.maindatas.ZPPTCSAP001_Service.class.getResource("../");
            System.out.println(baseUrl);
            url = new URL(baseUrl,
                    "../SAPInfo/zpp_tc_sap_001.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location:" + url + ", retrying as a local file");
            logger.warning(e.getMessage());
        }
        ZPPTCSAP001_WSDL_LOCATION = url;
    }

    public ZPPTCSAP001_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ZPPTCSAP001_Service() {
        super(ZPPTCSAP001_WSDL_LOCATION, new QName("urn:sap-com:document:sap:rfc:functions", "ZPP_TC_SAP_001"));
    }

    /**
     * @return returns ZPPTCSAP001
     */
    @WebEndpoint(name = "ZPP_TC_SAP_001")
    public ZPPTCSAP001 getZPPTCSAP001() {
        return super.getPort(new QName("urn:sap-com:document:sap:rfc:functions", "ZPP_TC_SAP_001"), ZPPTCSAP001.class);
    }

    /**
     * @return returns ZPPTCSAP001
     */
    @WebEndpoint(name = "ZPP_TC_SAP_001_soap12")
    public ZPPTCSAP001 getZPPTCSAP001Soap12() {
        return super.getPort(new QName("urn:sap-com:document:sap:rfc:functions", "ZPP_TC_SAP_001_soap12"),
                ZPPTCSAP001.class);
    }

}
