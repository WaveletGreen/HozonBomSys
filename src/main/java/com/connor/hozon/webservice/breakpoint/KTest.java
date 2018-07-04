package com.connor.hozon.webservice.breakpoint;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

/**
 * Created by K on 2018/7/3.
 */
public class KTest {
    public static void main(String[] args){
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8081/kk/ws/user?wsdl");
        try {
            Object[] objects;
            objects = client.invoke("getTCBPI", "");
            //输出调用结果
            TCBPI bean= (TCBPI) objects[0];
            System.out.println(bean.getP_CDATE());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
