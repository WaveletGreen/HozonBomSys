
package webservice.maindatas;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * 发送数据的接口层，调用函数即可
 */
@WebService(name = "ZPP_TC_SAP_001", targetNamespace = "urn:sap-com:document:sap:rfc:functions")
public interface ZPPTCSAP001 {

    /**
     * @param output 输出参数
     * @param input  输入参数
     */
    @WebMethod(operationName = "ZPP_TC_SAP_001", action = "urn:sap-com:document:sap:rfc:functions:ZPP_TC_SAP_001:ZPP_TC_SAP_001Request")
    @RequestWrapper(localName = "ZPP_TC_SAP_001", targetNamespace = "urn:sap-com:document:sap:rfc:functions", className = "webservice.sap.ZPPTCSAP001_Type")
    @ResponseWrapper(localName = "ZPP_TC_SAP_001Response", targetNamespace = "urn:sap-com:document:sap:rfc:functions", className = "webservice.sap.ZPPTCSAP001Response")
    public void zppTCSAP001(
            @WebParam(name = "INPUT", targetNamespace = "", mode = WebParam.Mode.INOUT) Holder<TABLEOFZPPTCI001> input,
            @WebParam(name = "OUTPUT", targetNamespace = "", mode = WebParam.Mode.INOUT) Holder<TABLEOFZPPTCO001> output);

}
