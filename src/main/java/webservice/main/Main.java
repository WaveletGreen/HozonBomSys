package webservice.main;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/***
 * 测试验证通过Webservice需要加密的WSDL文件
 */
public class Main {

    static {
        java.net.Authenticator.setDefault(new java.net.Authenticator() {
            @Override
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication("intf_common",
                        "Hz123789".toCharArray());
            }
        });
    }


    public static void main(String[] args) {
        URL url;
        InputStream is = null;
        StringBuffer sb = new StringBuffer();
        URLConnection urlConn;
        HttpURLConnection htcon;
        try {
            url = new URL("http://10.0.6.190:8000/sap/bc/srt/wsdl/flv_10002A111AD1/bndg_url/sap/bc/srt/rfc/sap/zpp_tc_sap_001/300/zpp_tc_sap_001/zpp_tc_sap_001?sap-client=300");
            urlConn = url.openConnection();
            /**
             * 注释掉添加串，报401  aW50Zl9jb21tb246SHoxMjM3ODk
             */
            urlConn.setRequestProperty("Authorization", "Basic " +  "aW50Zl9jb21tb246SHoxMjM3ODk=");

            htcon = (HttpURLConnection) urlConn;
            is = htcon.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];

            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            System.out.println(url.getAuthority());
            System.out.println("sb: " + sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
