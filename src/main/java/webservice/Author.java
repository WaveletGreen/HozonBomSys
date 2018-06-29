package webservice;

import webservice.base.helper.PropertyLoader;
import webservice.service.i.ICommon;

import javax.xml.ws.Holder;
import java.net.PasswordAuthentication;
import java.util.Properties;

public class Author {
    private final static PropertyLoader LOADER = new PropertyLoader();
    private final static Properties properties = LOADER.getProperties();
    private static PasswordAuthentication author;

    static {
        java.net.Authenticator.setDefault(new java.net.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                author = new PasswordAuthentication(properties.getProperty("username"),
                        properties.getProperty("password").toCharArray());
                return author;
            }
        });
    }

    public Author() {
    }

    /**
     * 验证输入参数是否正确
     *
     * @return
     */
    public boolean validateInput(Holder<? extends ICommon> input) {
        if (input.value == null || input.value.getItem() == null || input.value.getItem().size() < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证输出参数是否正确
     *
     * @return
     */
    public boolean validateOutput(Holder<? extends ICommon> output) {
        if (output.value == null || output.value.getItem() == null || output.value.getItem().size() < 0) {
            return false;
        } else {
            return true;
        }
    }
}
